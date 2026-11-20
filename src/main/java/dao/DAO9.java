package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Lớp cha của các DAO: mở kết nối JDBC tới CSDL cineman9.
 * Thông tin kết nối đọc từ file db.properties trong classpath.
 */
public class DAO9 {
    private static final Properties CONFIG = loadConfig();

    public DAO9() {
        super();
    }

    private static Properties loadConfig() {
        Properties p = new Properties();
        try (InputStream in = DAO9.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                        "Thiếu file db.properties. Hãy sao chép db.properties.example thành db.properties và điền mật khẩu MySQL.");
            }
            p.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Không đọc được cấu hình CSDL", e);
        }
        return p;
    }

    /** Mở một kết nối mới. Lớp gọi chịu trách nhiệm đóng kết nối. */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONFIG.getProperty("db.url"),
                CONFIG.getProperty("db.user"), CONFIG.getProperty("db.password"));
    }
}
