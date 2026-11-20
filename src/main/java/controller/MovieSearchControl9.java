package controller;

import dao.MovieDAO9;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import model.Movie9;

/** Lớp điều khiển mô-đun Tìm kiếm thông tin phim. */
public class MovieSearchControl9 {
    protected final MovieDAO9 movieDAO;

    public MovieSearchControl9() {
        this.movieDAO = new MovieDAO9();
    }

    /**
     * Tìm phim có title chứa từ khóa (đã bỏ khoảng trắng đầu cuối).
     *
     * @throws IllegalArgumentException nếu từ khóa rỗng
     */
    public List<Movie9> searchMovies(String keyword) throws SQLException {
        String kw = keyword == null ? "" : keyword.trim();
        if (kw.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập tên phim cần tìm.");
        }
        return movieDAO.findMoviesByTitle(kw);
    }

    /** Tải chi tiết phim đã chọn; rỗng nếu phim đã bị gỡ khỏi hệ thống. */
    public Optional<Movie9> loadMovieDetails(long movieId) throws SQLException {
        return movieDAO.getMovieDetails(movieId);
    }
}
