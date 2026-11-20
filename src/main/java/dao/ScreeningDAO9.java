package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.ScreeningRoom9;
import model.Screening9;
import model.TimeSlot9;

/** Đọc và ghi bảng tblScreening9. */
public class ScreeningDAO9 extends DAO9 {

    public ScreeningDAO9() {
        super();
    }

    /**
     * Các suất cùng ngày có khoảng giờ chồng lấn với khung giờ đã chọn, ở mọi phòng.
     * Hai khoảng [a, b) và [c, d) chồng nhau khi a &lt; d và c &lt; b, nên hai khung tiếp giáp không xung đột.
     */
    public List<Screening9> findOverlappingScreenings(LocalDate date, TimeSlot9 timeSlot) throws SQLException {
        try (Connection con = getConnection()) {
            return findOverlappingScreenings(con, date, timeSlot, null);
        }
    }

    /** Kiểm tra xung đột trong giao dịch đang mở, chỉ với một phòng nếu roomId khác null. */
    public List<Screening9> findOverlappingScreenings(Connection con, LocalDate date, TimeSlot9 timeSlot, Long roomId)
            throws SQLException {
        String sql = "SELECT s.id, s.tblScreeningRoom9id FROM tblScreening9 s JOIN tblTimeSlot9 t ON t.id = s.tblTimeSlot9id "
                + "WHERE s.screeningDate = ? AND t.startTime < ? AND ? < t.endTime"
                + (roomId == null ? "" : " AND s.tblScreeningRoom9id = ?");
        List<Screening9> list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            ps.setTime(2, Time.valueOf(timeSlot.getEndTime()));
            ps.setTime(3, Time.valueOf(timeSlot.getStartTime()));
            if (roomId != null) {
                ps.setLong(4, roomId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Screening9 s = new Screening9();
                    s.setId(rs.getLong("id"));
                    ScreeningRoom9 room = new ScreeningRoom9();
                    room.setId(rs.getLong("tblScreeningRoom9id"));
                    s.setRoom(room);
                    s.setScreeningDate(date);
                    list.add(s);
                }
            }
        }
        return list;
    }

    /** Ghi lịch chiếu bằng kết nối của giao dịch do lớp điều khiển quản lí. */
    public Screening9 saveScreening(Connection con, Screening9 screening) throws SQLException {
        String sql = "INSERT INTO tblScreening9 (screeningDate, ticketPrice, status, tblMovie9id, tblScreeningRoom9id, tblTimeSlot9id, tblManager9id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(screening.getScreeningDate()));
            ps.setBigDecimal(2, screening.getTicketPrice());
            ps.setString(3, screening.getStatus());
            ps.setLong(4, screening.getMovie().getId());
            ps.setLong(5, screening.getRoom().getId());
            ps.setLong(6, screening.getTimeSlot().getId());
            ps.setLong(7, screening.getCreatedBy().getId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("Không nhận được mã lịch chiếu vừa ghi");
                }
                screening.setId(keys.getLong(1));
            }
        }
        return screening;
    }
}
