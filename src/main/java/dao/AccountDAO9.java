package dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HexFormat;
import java.util.Optional;
import model.Account9;
import model.Cinema9;
import model.Customer9;
import model.Employee9;
import model.Manager9;
import model.SalesEmployee9;

/** Kiểm tra đăng nhập (tiền điều kiện của hai mô-đun). */
public class AccountDAO9 extends DAO9 {

    public AccountDAO9() {
        super();
    }

    /**
     * Trả về tài khoản khách hàng, quản lí hoặc nhân viên bán hàng nếu đăng nhập hợp lệ.
     * Mật khẩu được so sánh dưới dạng băm SHA-256.
     */
    public Optional<Account9> checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT a.id, a.username, a.role, a.status, "
                + "e.employeeCode, e.fullName AS empName, e.cinemaId, c.name AS cinemaName, "
                + "cu.customerCode, cu.fullName AS cusName, cu.phoneNumber, cu.email, cu.membershipStatus, "
                + "(m.id IS NOT NULL) AS isManager, (s.id IS NOT NULL) AS isSalesEmployee "
                + "FROM tblAccount9 a "
                + "LEFT JOIN tblEmployee9 e ON e.id = a.id "
                + "LEFT JOIN tblCinema9 c ON c.id = e.cinemaId "
                + "LEFT JOIN tblManager9 m ON m.id = a.id "
                + "LEFT JOIN tblSalesEmployee9 s ON s.id = a.id "
                + "LEFT JOIN tblCustomer9 cu ON cu.id = a.id "
                + "WHERE a.username = ? AND a.authenticationData = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, sha256(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                Account9 account;
                String role = rs.getString("role");
                if (Account9.ROLE_MANAGER.equals(role) && rs.getBoolean("isManager")) {
                    account = readEmployee(rs, new Manager9());
                } else if (Account9.ROLE_SALES.equals(role) && rs.getBoolean("isSalesEmployee")) {
                    account = readEmployee(rs, new SalesEmployee9());
                } else if (Account9.ROLE_CUSTOMER.equals(role) && rs.getString("customerCode") != null) {
                    Customer9 c = new Customer9();
                    c.setCustomerCode(rs.getString("customerCode"));
                    c.setFullName(rs.getString("cusName"));
                    c.setPhoneNumber(rs.getString("phoneNumber"));
                    c.setEmail(rs.getString("email"));
                    c.setMembershipStatus(rs.getString("membershipStatus"));
                    account = c;
                } else {
                    return Optional.empty();
                }
                account.setId(rs.getLong("id"));
                account.setUsername(rs.getString("username"));
                account.setRole(role);
                account.setStatus(rs.getString("status"));
                return Optional.of(account);
            }
        }
    }

    private static Employee9 readEmployee(ResultSet rs, Employee9 employee) throws SQLException {
        employee.setEmployeeCode(rs.getString("employeeCode"));
        employee.setFullName(rs.getString("empName"));
        Cinema9 cinema = new Cinema9();
        cinema.setId(rs.getLong("cinemaId"));
        cinema.setName(rs.getString("cinemaName"));
        employee.setCinema(cinema);
        return employee;
    }

    private static String sha256(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(text.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
