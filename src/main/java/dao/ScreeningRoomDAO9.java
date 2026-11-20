package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Cinema9;
import model.CinemaSeat9;
import model.ScreeningRoom9;

/** Đọc bảng tblScreeningRoom9. Sức chứa được đếm từ các ghế vật lí của phòng. */
public class ScreeningRoomDAO9 extends DAO9 {
    private static final String SELECT = "SELECT r.id, r.roomName, r.usageStatus, r.description, r.tblCinema9id, "
            + "(SELECT COUNT(*) FROM tblCinemaSeat9 s WHERE s.tblScreeningRoom9id = r.id) AS capacity, "
            + "(SELECT COUNT(*) FROM tblCinemaSeat9 s WHERE s.tblScreeningRoom9id = r.id AND s.status = ?) AS usableSeats "
            + "FROM tblScreeningRoom9 r ";

    public ScreeningRoomDAO9() {
        super();
    }

    /** Các phòng của rạp đang sử dụng được và có ít nhất một ghế dùng được. */
    public List<ScreeningRoom9> findUsableRooms(long cinemaId) throws SQLException {
        String sql = "SELECT * FROM (" + SELECT + "WHERE r.tblCinema9id = ? AND r.usageStatus = ?) x "
                + "WHERE x.usableSeats > 0 ORDER BY x.roomName";
        List<ScreeningRoom9> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, CinemaSeat9.USABLE);
            ps.setLong(2, cinemaId);
            ps.setString(3, ScreeningRoom9.IN_USE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(toRoom(rs));
                }
            }
        }
        return list;
    }

    /** Đọc một phòng theo id. */
    public Optional<ScreeningRoom9> getRoom(long roomId) throws SQLException {
        try (Connection con = getConnection()) {
            return read(con, roomId, "");
        }
    }

    /**
     * Khóa bản ghi phòng trong giao dịch đang mở (SELECT ... FOR UPDATE), để hai lần
     * xác nhận lịch cho cùng phòng không thể chạy chồng nhau.
     */
    public Optional<ScreeningRoom9> lockRoom(Connection con, long roomId) throws SQLException {
        return read(con, roomId, " FOR UPDATE");
    }

    private Optional<ScreeningRoom9> read(Connection con, long roomId, String lock) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(SELECT + "WHERE r.id = ?" + lock)) {
            ps.setString(1, CinemaSeat9.USABLE);
            ps.setLong(2, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(toRoom(rs)) : Optional.empty();
            }
        }
    }

    private static ScreeningRoom9 toRoom(ResultSet rs) throws SQLException {
        ScreeningRoom9 r = new ScreeningRoom9();
        r.setId(rs.getLong("id"));
        r.setRoomName(rs.getString("roomName"));
        r.setUsageStatus(rs.getString("usageStatus"));
        r.setDescription(rs.getString("description"));
        Cinema9 cinema = new Cinema9();
        cinema.setId(rs.getLong("tblCinema9id"));
        r.setCinema(cinema);
        r.setCapacity(rs.getInt("capacity"));
        r.setUsableSeats(rs.getInt("usableSeats"));
        return r;
    }
}
