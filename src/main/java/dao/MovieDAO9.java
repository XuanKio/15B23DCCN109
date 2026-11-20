package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Movie9;

/** Đọc và ghi bảng tblMovie9. */
public class MovieDAO9 extends DAO9 {
    private static final String COLUMNS = "id, title, director, genre, duration, description, showingStatus, "
            + "actors, poster, releaseDate, language, baseTicketPrice";

    public MovieDAO9() {
        super();
    }

    /** Tìm các phim có title chứa từ khóa. Ký tự % và _ trong từ khóa được coi là ký tự thường. */
    public List<Movie9> findMoviesByTitle(String keyword) throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM tblMovie9 WHERE title LIKE ? ESCAPE '!' ORDER BY title";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + escapeLike(keyword) + "%");
            return readList(ps);
        }
    }

    /** Lấy bản ghi phim theo id, rỗng nếu phim đã bị gỡ. */
    public Optional<Movie9> getMovieDetails(long movieId) throws SQLException {
        try (Connection con = getConnection()) {
            return getMovieDetails(con, movieId);
        }
    }

    /** Đọc lại phim trong giao dịch đang mở (dùng khi xác nhận lịch). */
    public Optional<Movie9> getMovieDetails(Connection con, long movieId) throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM tblMovie9 WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, movieId);
            List<Movie9> list = readList(ps);
            return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
        }
    }

    /** Các phim có showingStatus là đang chiếu. */
    public List<Movie9> getShowingMovies() throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM tblMovie9 WHERE showingStatus = ? ORDER BY title";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, Movie9.SHOWING);
            return readList(ps);
        }
    }

    /** Ghi phim mới và trả về phim đã có id. */
    public Movie9 saveMovie(Movie9 movie) throws SQLException {
        String sql = "INSERT INTO tblMovie9 (title, director, genre, duration, description, showingStatus, "
                + "actors, poster, releaseDate, language, baseTicketPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDirector());
            ps.setString(3, movie.getGenre());
            ps.setInt(4, movie.getDuration());
            ps.setString(5, movie.getDescription());
            ps.setString(6, movie.getShowingStatus());
            ps.setString(7, movie.getActors());
            ps.setString(8, movie.getPoster());
            if (movie.getReleaseDate() == null) {
                ps.setNull(9, Types.DATE);
            } else {
                ps.setDate(9, Date.valueOf(movie.getReleaseDate()));
            }
            ps.setString(10, movie.getLanguage());
            ps.setBigDecimal(11, movie.getBaseTicketPrice());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    movie.setId(keys.getLong(1));
                }
            }
            return movie;
        }
    }

    private List<Movie9> readList(PreparedStatement ps) throws SQLException {
        List<Movie9> list = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(toMovie(rs));
            }
        }
        return list;
    }

    private static Movie9 toMovie(ResultSet rs) throws SQLException {
        Movie9 m = new Movie9();
        m.setId(rs.getLong("id"));
        m.setTitle(rs.getString("title"));
        m.setDirector(rs.getString("director"));
        m.setGenre(rs.getString("genre"));
        m.setDuration(rs.getInt("duration"));
        m.setDescription(rs.getString("description"));
        m.setShowingStatus(rs.getString("showingStatus"));
        m.setActors(rs.getString("actors"));
        m.setPoster(rs.getString("poster"));
        Date release = rs.getDate("releaseDate");
        m.setReleaseDate(release == null ? null : release.toLocalDate());
        m.setLanguage(rs.getString("language"));
        m.setBaseTicketPrice(rs.getBigDecimal("baseTicketPrice"));
        return m;
    }

    private static String escapeLike(String s) {
        return s.replace("!", "!!").replace("%", "!%").replace("_", "!_");
    }
}
