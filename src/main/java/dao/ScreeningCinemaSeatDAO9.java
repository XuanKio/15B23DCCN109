package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.CinemaSeat9;
import model.Screening9;
import model.ScreeningCinemaSeat9;

/** Ghi bảng tblScreeningCinemaSeat9. */
public class ScreeningCinemaSeatDAO9 extends DAO9 {

    public ScreeningCinemaSeatDAO9() {
        super();
    }

    /**
     * Tạo một ghế theo suất cho mỗi ghế vật lí của phòng. Ghế dùng được bắt đầu ở trạng thái trống,
     * ghế bảo trì ở trạng thái không khả dụng; holdExpireTime để trống. Dùng chung giao dịch với saveScreening.
     *
     * @return số ghế đã tạo
     */
    public int createScreeningSeats(Connection con, Screening9 screening) throws SQLException {
        String sql = "INSERT INTO tblScreeningCinemaSeat9 (holdExpireTime, status, tblScreening9id, tblCinemaSeat9id) "
                + "SELECT NULL, IF(s.status = ?, ?, ?), ?, s.id FROM tblCinemaSeat9 s WHERE s.tblScreeningRoom9id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, CinemaSeat9.USABLE);
            ps.setString(2, ScreeningCinemaSeat9.EMPTY);
            ps.setString(3, ScreeningCinemaSeat9.UNAVAILABLE);
            ps.setLong(4, screening.getId());
            ps.setLong(5, screening.getRoom().getId());
            int created = ps.executeUpdate();
            if (created == 0) {
                throw new SQLException("Phòng không có ghế nào để tạo ghế theo suất");
            }
            return created;
        }
    }
}
