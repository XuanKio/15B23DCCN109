package controller;

import dao.MovieDAO9;
import dao.ScreeningCinemaSeatDAO9;
import dao.ScreeningDAO9;
import dao.ScreeningRoomDAO9;
import dao.TimeSlotDAO9;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Manager9;
import model.Movie9;
import model.Screening9;
import model.ScreeningRoom9;
import model.TimeSlot9;

/** Lớp điều khiển mô-đun Lên lịch chiếu. */
public class ScheduleScreeningControl9 {
    protected final TimeSlotDAO9 timeSlotDAO;
    protected final ScreeningRoomDAO9 screeningRoomDAO;
    protected final MovieDAO9 movieDAO;
    protected final ScreeningDAO9 screeningDAO;
    protected final ScreeningCinemaSeatDAO9 screeningCinemaSeatDAO;

    public ScheduleScreeningControl9() {
        this.timeSlotDAO = new TimeSlotDAO9();
        this.screeningRoomDAO = new ScreeningRoomDAO9();
        this.movieDAO = new MovieDAO9();
        this.screeningDAO = new ScreeningDAO9();
        this.screeningCinemaSeatDAO = new ScreeningCinemaSeatDAO9();
    }

    /** Danh sách khung giờ để chọn. */
    public List<TimeSlot9> loadTimeSlots() throws SQLException {
        return timeSlotDAO.getTimeSlots();
    }

    /** Đọc một khung giờ, báo lỗi nếu không tồn tại. */
    public TimeSlot9 getTimeSlot(long timeSlotId) throws SQLException {
        return timeSlotDAO.getTimeSlot(timeSlotId)
                .orElseThrow(() -> new IllegalArgumentException("Khung giờ đã chọn không tồn tại."));
    }

    /**
     * Phòng thuộc rạp của quản lí, đang sử dụng được, có ghế dùng được và không có suất chồng lấn
     * trong ngày và khung giờ đã chọn.
     */
    public List<ScreeningRoom9> loadAvailableRooms(LocalDate date, long timeSlotId, Manager9 manager)
            throws SQLException {
        checkDate(date);
        TimeSlot9 timeSlot = getTimeSlot(timeSlotId);
        List<ScreeningRoom9> rooms = screeningRoomDAO.findUsableRooms(manager.getCinema().getId());
        Set<Long> busyRoomIds = new HashSet<>();
        for (Screening9 s : screeningDAO.findOverlappingScreenings(date, timeSlot)) {
            busyRoomIds.add(s.getRoom().getId());
        }
        List<ScreeningRoom9> available = new ArrayList<>();
        for (ScreeningRoom9 room : rooms) {
            if (!busyRoomIds.contains(room.getId())) {
                available.add(room);
            }
        }
        return available;
    }

    /** Đọc một phòng (để hiển thị trên các màn hình sau). */
    public ScreeningRoom9 getRoom(long roomId) throws SQLException {
        return screeningRoomDAO.getRoom(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Phòng đã chọn không tồn tại."));
    }

    /** Danh sách phim đang chiếu để chọn. */
    public List<Movie9> loadShowingMovies() throws SQLException {
        return movieDAO.getShowingMovies();
    }

    /** Đọc một phim (để hiển thị trên màn hình xác nhận). */
    public Movie9 getMovie(long movieId) throws SQLException {
        return movieDAO.getMovieDetails(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Phim đã chọn không còn tồn tại."));
    }

    /** Phim hợp lệ khi đang chiếu, có giá cơ sở không âm và thời lượng vừa khung giờ. */
    public boolean validateMovie(Movie9 movie, TimeSlot9 timeSlot) {
        return movie != null && timeSlot != null
                && Movie9.SHOWING.equals(movie.getShowingStatus())
                && movie.getBaseTicketPrice() != null
                && movie.getBaseTicketPrice().compareTo(BigDecimal.ZERO) >= 0
                && movie.getDuration() > 0
                && movie.getDuration() <= timeSlot.getDurationMinutes();
    }

    /**
     * Kiểm tra lại toàn bộ điều kiện rồi ghi Screening9 và các ScreeningCinemaSeat9 trong cùng một giao dịch.
     * Bản ghi phòng bị khóa trong giao dịch để hai lần xác nhận cho cùng phòng không chạy chồng nhau.
     *
     * @throws IllegalStateException nếu dữ liệu đã thay đổi và không còn hợp lệ
     */
    public Screening9 confirmScreening(LocalDate date, long timeSlotId, long roomId, long movieId, Manager9 manager)
            throws SQLException {
        if (manager == null || manager.getCinema() == null) {
            throw new IllegalStateException("Chỉ nhân viên quản lí đã đăng nhập mới được lập lịch.");
        }
        checkDate(date);
        try (Connection con = screeningDAO.getConnection()) {
            con.setAutoCommit(false);
            try {
                ScreeningRoom9 room = screeningRoomDAO.lockRoom(con, roomId)
                        .orElseThrow(() -> new IllegalStateException("Phòng không còn tồn tại."));
                if (room.getCinema().getId() != manager.getCinema().getId()) {
                    throw new IllegalStateException("Phòng không thuộc rạp của quản lí.");
                }
                if (!ScreeningRoom9.IN_USE.equals(room.getUsageStatus()) || room.getUsableSeats() == 0) {
                    throw new IllegalStateException("Phòng đã ngừng sử dụng, vui lòng chọn phòng khác.");
                }
                TimeSlot9 timeSlot = timeSlotDAO.getTimeSlot(con, timeSlotId)
                        .orElseThrow(() -> new IllegalStateException("Khung giờ không còn tồn tại."));
                Movie9 movie = movieDAO.getMovieDetails(con, movieId)
                        .orElseThrow(() -> new IllegalStateException("Phim không còn tồn tại."));
                if (!validateMovie(movie, timeSlot)) {
                    throw new IllegalStateException("Phim không còn phù hợp với khung giờ hoặc chưa có giá cơ sở.");
                }
                if (!screeningDAO.findOverlappingScreenings(con, date, timeSlot, roomId).isEmpty()) {
                    throw new IllegalStateException("Phòng vừa được xếp suất khác trong khung giờ này.");
                }

                Screening9 screening = new Screening9();
                screening.setScreeningDate(date);
                screening.setTicketPrice(movie.getBaseTicketPrice());
                screening.setStatus(Screening9.SCHEDULED);
                screening.setMovie(movie);
                screening.setRoom(room);
                screening.setTimeSlot(timeSlot);
                screening.setCreatedBy(manager);
                screeningDAO.saveScreening(con, screening);
                screeningCinemaSeatDAO.createScreeningSeats(con, screening);
                con.commit();
                return screening;
            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        }
    }

    /**
     * Kiểm tra các trường bắt buộc và giá cơ sở rồi lưu phim mới.
     *
     * @throws IllegalArgumentException nếu dữ liệu nhập không hợp lệ
     */
    public Movie9 addMovie(Movie9 movieData) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (isBlank(movieData.getTitle())) {
            errors.add("Tên phim không được để trống.");
        }
        if (isBlank(movieData.getDirector())) {
            errors.add("Đạo diễn không được để trống.");
        }
        if (isBlank(movieData.getGenre())) {
            errors.add("Thể loại không được để trống.");
        }
        if (movieData.getDuration() <= 0 || movieData.getDuration() > 600) {
            errors.add("Thời lượng phải từ 1 đến 600 phút.");
        }
        if (!List.of(Movie9.SHOWING, Movie9.UPCOMING, Movie9.STOPPED).contains(movieData.getShowingStatus())) {
            errors.add("Trạng thái không hợp lệ.");
        }
        if (movieData.getBaseTicketPrice() == null || movieData.getBaseTicketPrice().signum() < 0) {
            errors.add("Giá cơ sở phải là số không âm.");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", errors));
        }
        movieData.setTitle(movieData.getTitle().trim());
        movieData.setDirector(movieData.getDirector().trim());
        movieData.setGenre(movieData.getGenre().trim());
        return movieDAO.saveMovie(movieData);
    }

    private static void checkDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Vui lòng chọn ngày chiếu.");
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày chiếu không được ở trong quá khứ.");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
