# CineMan9 - Hệ thống quản lí rạp chiếu phim

Bài tập lớn môn Phân tích và Thiết kế hệ thống thông tin, đề số 15.
Sinh viên Hoàng Xuân Cường, mã sinh viên B23DCCN109.

Mã nguồn cài đặt hai mô-đun theo bản thiết kế trong báo cáo:

- **Tìm kiếm thông tin phim** (khách hàng): nhập tên phim, xem danh sách kết quả, xem chi tiết phim.
- **Lên lịch chiếu** (nhân viên quản lí): chọn ngày và khung giờ, chọn phòng còn trống, chọn phim đang chiếu
  hoặc thêm phim mới, xác nhận lịch. Hệ thống lưu suất chiếu và ghế theo suất trong cùng một giao dịch.

## Công nghệ

Java 17, JSP chạy trên Apache Tomcat 10.1 (Jakarta EE), MySQL 8 qua JDBC, build bằng Maven.

## Cấu trúc mã nguồn

Mã nguồn chia theo bốn gói như biểu đồ gói ở Mục 3.4.1:

```
src/main/
├── java/
│   ├── controller/   MovieSearchControl9, ScheduleScreeningControl9
│   ├── dao/          DAO9, MovieDAO9, TimeSlotDAO9, ScreeningRoomDAO9, ScreeningDAO9,
│   │                 ScreeningCinemaSeatDAO9, AccountDAO9 (đăng nhập)
│   └── model/        20 lớp thực thể (Movie9, Screening9, ScreeningRoom9, TimeSlot9, ...)
├── resources/
│   └── db.properties.example
└── webapp/           gói view
    ├── gdDangNhap9.jsp
    ├── khachhang/    gdChinhKH9, gdTimPhim9, gdChiTietPhim9
    ├── quanli/       gdChinhQL9, gdQLLichChieu9, gdChonThoiGian9, gdChonPhong9,
    │                 gdChonPhim9, gdThemPhim9, gdXacNhanLich9
    └── css/cineman9.css
database/
├── cineman9.sql             tạo 17 bảng và dữ liệu mẫu
└── tao_user_cineman9.sql    tạo tài khoản MySQL cho ứng dụng
```

Các trang JSP gọi lớp điều khiển, lớp điều khiển gọi DAO, DAO đọc hoặc ghi CSDL rồi trả về đối tượng thực thể.

## Cách chạy

Cần có JDK 17 trở lên, Maven và MySQL 8.

1. **Tạo CSDL (một lần):** chạy `database/cineman9.sql` rồi `database/tao_user_cineman9.sql` bằng tài khoản root của MySQL, chẳng hạn trong MySQL Workbench.
2. **Chạy:** bấm đúp `chay.bat`, hoặc gõ `mvn package cargo:run`. Maven tự tải Tomcat 10.1 (lần đầu hơi lâu), deploy ứng dụng và mở trình duyệt ở http://localhost:8080/CineMan9/. Muốn tắt thì đóng cửa sổ dòng lệnh.

File `src/main/resources/db.properties` được tạo tự động từ `db.properties.example`, dùng tài khoản MySQL `cineman9` / `cineman9`.
Nếu muốn deploy tay, chép `target/CineMan9.war` vào thư mục `webapps` của Tomcat 10.1.

Tài khoản mẫu (mật khẩu đều là `123456`):

| Tài khoản | Vai trò |
|---|---|
| `khachhang01` | Khách hàng |
| `banhang01` | Nhân viên bán hàng (chỉ đăng nhập, chức năng bán hàng chưa cài đặt) |
| `quanli01` | Nhân viên quản lí (rạp CineMan Hà Đông) |

Dữ liệu mẫu có sẵn một suất chiếu ở Phòng 02, ngày 25/11/2026, khung 18:00–20:00. Khi chọn ngày và khung giờ này (hoặc khung 19:00–21:00 bị chồng giờ), Phòng 02 sẽ không xuất hiện trong danh sách phòng trống.
