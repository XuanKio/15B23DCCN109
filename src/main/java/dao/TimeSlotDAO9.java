package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.TimeSlot9;

/** Đọc bảng tblTimeSlot9. */
public class TimeSlotDAO9 extends DAO9 {

    public TimeSlotDAO9() {
        super();
    }

    /** Tất cả khung giờ, sắp theo giờ bắt đầu. */
    public List<TimeSlot9> getTimeSlots() throws SQLException {
        String sql = "SELECT id, name, startTime, endTime FROM tblTimeSlot9 ORDER BY startTime";
        List<TimeSlot9> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(toTimeSlot(rs));
            }
        }
        return list;
    }

    /** Đọc một khung giờ theo id. */
    public Optional<TimeSlot9> getTimeSlot(long timeSlotId) throws SQLException {
        try (Connection con = getConnection()) {
            return getTimeSlot(con, timeSlotId);
        }
    }

    /** Đọc lại khung giờ trong giao dịch đang mở. */
    public Optional<TimeSlot9> getTimeSlot(Connection con, long timeSlotId) throws SQLException {
        String sql = "SELECT id, name, startTime, endTime FROM tblTimeSlot9 WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, timeSlotId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(toTimeSlot(rs)) : Optional.empty();
            }
        }
    }

    private static TimeSlot9 toTimeSlot(ResultSet rs) throws SQLException {
        TimeSlot9 t = new TimeSlot9();
        t.setId(rs.getLong("id"));
        t.setName(rs.getString("name"));
        t.setStartTime(rs.getTime("startTime").toLocalTime());
        t.setEndTime(rs.getTime("endTime").toLocalTime());
        return t;
    }
}
