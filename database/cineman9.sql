-- CSDL CineMan9: 17 bảng theo Mục 3.2 của báo cáo, kèm dữ liệu mẫu cho hai mô-đun.
DROP DATABASE IF EXISTS cineman9;
CREATE DATABASE cineman9 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cineman9;

-- ===== Tài khoản và thành viên =====
CREATE TABLE tblCinema9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(24) NOT NULL,
    description VARCHAR(255) NULL
);

CREATE TABLE tblAccount9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    authenticationData VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    CONSTRAINT uqAccount9Username UNIQUE (username)
);

CREATE TABLE tblEmployee9 (
    tblAccount9id INT PRIMARY KEY,
    employeeCode VARCHAR(30) NOT NULL,
    fullName VARCHAR(100) NOT NULL,
    tblCinema9id INT NOT NULL,
    CONSTRAINT uqEmployee9Code UNIQUE (employeeCode),
    CONSTRAINT fkEmployee9Account FOREIGN KEY (tblAccount9id) REFERENCES tblAccount9(id),
    CONSTRAINT fkEmployee9Cinema FOREIGN KEY (tblCinema9id) REFERENCES tblCinema9(id)
);

CREATE TABLE tblManager9 (
    tblEmployee9id INT PRIMARY KEY,
    CONSTRAINT fkManager9Employee FOREIGN KEY (tblEmployee9id) REFERENCES tblEmployee9(tblAccount9id)
);

CREATE TABLE tblSalesEmployee9 (
    tblEmployee9id INT PRIMARY KEY,
    CONSTRAINT fkSalesEmployee9Employee FOREIGN KEY (tblEmployee9id) REFERENCES tblEmployee9(tblAccount9id)
);

CREATE TABLE tblCustomer9 (
    tblAccount9id INT PRIMARY KEY,
    customerCode VARCHAR(30) NOT NULL,
    fullName VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(24) NOT NULL,
    email VARCHAR(254) NULL,
    membershipStatus VARCHAR(30) NOT NULL,
    CONSTRAINT uqCustomer9Code UNIQUE (customerCode),
    CONSTRAINT fkCustomer9Account FOREIGN KEY (tblAccount9id) REFERENCES tblAccount9(id)
);

CREATE TABLE tblMembershipCard9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cardNumber VARCHAR(30) NOT NULL,
    issueDate DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    discountRate DECIMAL(5,4) NOT NULL,
    tblCustomer9id INT NOT NULL,
    tblSalesEmployee9id INT NOT NULL,
    CONSTRAINT uqMembershipCard9Number UNIQUE (cardNumber),
    CONSTRAINT uqMembershipCard9Customer UNIQUE (tblCustomer9id),
    CONSTRAINT ckMembershipCard9Rate CHECK (discountRate >= 0 AND discountRate <= 1),
    CONSTRAINT fkMembershipCard9Customer FOREIGN KEY (tblCustomer9id) REFERENCES tblCustomer9(tblAccount9id),
    CONSTRAINT fkMembershipCard9Issuer FOREIGN KEY (tblSalesEmployee9id) REFERENCES tblSalesEmployee9(tblEmployee9id)
);

-- ===== Rạp, phim và lịch =====
CREATE TABLE tblScreeningRoom9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roomName VARCHAR(80) NOT NULL,
    usageStatus VARCHAR(30) NOT NULL,
    description VARCHAR(255) NULL,
    tblCinema9id INT NOT NULL,
    CONSTRAINT uqScreeningRoom9Name UNIQUE (tblCinema9id, roomName),
    CONSTRAINT fkScreeningRoom9Cinema FOREIGN KEY (tblCinema9id) REFERENCES tblCinema9(id)
);

CREATE TABLE tblSeatType9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    typeName VARCHAR(150) NOT NULL,
    surcharge DECIMAL(12,2) NOT NULL,
    description VARCHAR(255) NULL,
    CONSTRAINT ckSeatType9Surcharge CHECK (surcharge >= 0)
);

CREATE TABLE tblCinemaSeat9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    seatCode VARCHAR(30) NOT NULL,
    rowLabel VARCHAR(12) NOT NULL,
    seatNumber INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    tblScreeningRoom9id INT NOT NULL,
    tblSeatType9id INT NOT NULL,
    CONSTRAINT uqCinemaSeat9Code UNIQUE (tblScreeningRoom9id, seatCode),
    CONSTRAINT uqCinemaSeat9Position UNIQUE (tblScreeningRoom9id, rowLabel, seatNumber),
    CONSTRAINT fkCinemaSeat9Room FOREIGN KEY (tblScreeningRoom9id) REFERENCES tblScreeningRoom9(id),
    CONSTRAINT fkCinemaSeat9Type FOREIGN KEY (tblSeatType9id) REFERENCES tblSeatType9(id)
);

CREATE TABLE tblMovie9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    director VARCHAR(150) NOT NULL,
    genre VARCHAR(80) NOT NULL,
    duration INT NOT NULL,
    description VARCHAR(1000) NULL,
    showingStatus VARCHAR(30) NOT NULL,
    actors VARCHAR(500) NULL,
    poster VARCHAR(500) NULL,
    releaseDate DATE NULL,
    language VARCHAR(60) NULL,
    baseTicketPrice DECIMAL(12,2) NULL,
    CONSTRAINT ckMovie9Duration CHECK (duration > 0),
    CONSTRAINT ckMovie9Price CHECK (baseTicketPrice IS NULL OR baseTicketPrice >= 0)
);

CREATE TABLE tblTimeSlot9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    CONSTRAINT ckTimeSlot9Range CHECK (startTime < endTime)
);

CREATE TABLE tblScreening9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    screeningDate DATE NOT NULL,
    ticketPrice DECIMAL(12,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    tblMovie9id INT NOT NULL,
    tblScreeningRoom9id INT NOT NULL,
    tblTimeSlot9id INT NOT NULL,
    tblManager9id INT NOT NULL,
    CONSTRAINT ckScreening9Price CHECK (ticketPrice >= 0),
    CONSTRAINT fkScreening9Movie FOREIGN KEY (tblMovie9id) REFERENCES tblMovie9(id),
    CONSTRAINT fkScreening9Room FOREIGN KEY (tblScreeningRoom9id) REFERENCES tblScreeningRoom9(id),
    CONSTRAINT fkScreening9TimeSlot FOREIGN KEY (tblTimeSlot9id) REFERENCES tblTimeSlot9(id),
    CONSTRAINT fkScreening9Manager FOREIGN KEY (tblManager9id) REFERENCES tblManager9(tblEmployee9id)
);

CREATE TABLE tblScreeningCinemaSeat9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    holdExpireTime DATETIME NULL,
    status VARCHAR(30) NOT NULL,
    tblScreening9id INT NOT NULL,
    tblCinemaSeat9id INT NOT NULL,
    CONSTRAINT uqScreeningCinemaSeat9 UNIQUE (tblScreening9id, tblCinemaSeat9id),
    CONSTRAINT fkScreeningCinemaSeat9Screening FOREIGN KEY (tblScreening9id) REFERENCES tblScreening9(id),
    CONSTRAINT fkScreeningCinemaSeat9Seat FOREIGN KEY (tblCinemaSeat9id) REFERENCES tblCinemaSeat9(id)
);

-- ===== Vé và giao dịch =====
CREATE TABLE tblInvoice9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoiceNumber VARCHAR(30) NOT NULL,
    saleTime DATETIME NOT NULL,
    saleChannel VARCHAR(30) NOT NULL,
    tblCustomer9id INT NOT NULL,
    tblSalesEmployee9id INT NULL,
    tblMembershipCard9id INT NULL,
    CONSTRAINT uqInvoice9Number UNIQUE (invoiceNumber),
    CONSTRAINT fkInvoice9Customer FOREIGN KEY (tblCustomer9id) REFERENCES tblCustomer9(tblAccount9id),
    CONSTRAINT fkInvoice9Seller FOREIGN KEY (tblSalesEmployee9id) REFERENCES tblSalesEmployee9(tblEmployee9id),
    CONSTRAINT fkInvoice9Card FOREIGN KEY (tblMembershipCard9id) REFERENCES tblMembershipCard9(id)
);

CREATE TABLE tblTicket9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ticketNumber VARCHAR(30) NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    tblScreeningCinemaSeat9id INT NOT NULL,
    tblInvoice9id INT NOT NULL,
    tblMembershipCard9id INT NULL,
    CONSTRAINT uqTicket9Number UNIQUE (ticketNumber),
    CONSTRAINT uqTicket9Seat UNIQUE (tblScreeningCinemaSeat9id),
    CONSTRAINT ckTicket9Price CHECK (price >= 0),
    CONSTRAINT fkTicket9Seat FOREIGN KEY (tblScreeningCinemaSeat9id) REFERENCES tblScreeningCinemaSeat9(id),
    CONSTRAINT fkTicket9Invoice FOREIGN KEY (tblInvoice9id) REFERENCES tblInvoice9(id),
    CONSTRAINT fkTicket9Card FOREIGN KEY (tblMembershipCard9id) REFERENCES tblMembershipCard9(id)
);

CREATE TABLE tblPayment9 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paymentTime DATETIME NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    paymentMethod VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    transactionReference VARCHAR(150) NULL,
    tblInvoice9id INT NOT NULL,
    CONSTRAINT ckPayment9Amount CHECK (amount >= 0),
    CONSTRAINT fkPayment9Invoice FOREIGN KEY (tblInvoice9id) REFERENCES tblInvoice9(id)
);

-- ===== Dữ liệu mẫu =====
INSERT INTO tblCinema9 (id, name, address, phoneNumber, description) VALUES
(1, 'CineMan Hà Đông', '96A Trần Phú, Hà Đông, Hà Nội', '024 3354 1234', 'Rạp chính của hệ thống CineMan');

-- Mật khẩu của các tài khoản mẫu đều là 123456 (lưu dạng SHA-256).
INSERT INTO tblAccount9 (id, username, authenticationData, role, status) VALUES
(1, 'quanli01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'MANAGER', 'Hoạt động'),
(2, 'banhang01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'SALES', 'Hoạt động'),
(3, 'khachhang01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'CUSTOMER', 'Hoạt động');

INSERT INTO tblEmployee9 (tblAccount9id, employeeCode, fullName, tblCinema9id) VALUES
(1, 'NV001', 'Trần Thu Hà', 1),
(2, 'NV002', 'Lê Quang Huy', 1);
INSERT INTO tblManager9 (tblEmployee9id) VALUES (1);
INSERT INTO tblSalesEmployee9 (tblEmployee9id) VALUES (2);
INSERT INTO tblCustomer9 (tblAccount9id, customerCode, fullName, phoneNumber, email, membershipStatus) VALUES
(3, 'KH001', 'Nguyễn Văn An', '0912345678', 'an.nguyen@example.com', 'Thành viên');

INSERT INTO tblSeatType9 (id, typeName, surcharge, description) VALUES
(1, 'Ghế thường', 0, NULL),
(2, 'Ghế VIP', 20000, 'Hàng giữa phòng');

INSERT INTO tblScreeningRoom9 (id, roomName, usageStatus, description, tblCinema9id) VALUES
(1, 'Phòng 01', 'Đang sử dụng', NULL, 1),
(2, 'Phòng 02', 'Đang sử dụng', NULL, 1),
(3, 'Phòng 03', 'Đang sử dụng', NULL, 1),
(4, 'Phòng 04', 'Bảo trì', 'Đang sửa hệ thống âm thanh', 1);

-- Sinh ghế: Phòng 01 có 10x10 = 100 ghế, Phòng 02 có 8x10, Phòng 03 có 8x10, Phòng 04 có 6x10.
-- Hàng E, F là ghế VIP. Ghế J10 của Phòng 01 đang bảo trì.
CREATE TEMPORARY TABLE tmpRow (r VARCHAR(2), idx INT);
INSERT INTO tmpRow VALUES ('A',1),('B',2),('C',3),('D',4),('E',5),('F',6),('G',7),('H',8),('I',9),('J',10);
CREATE TEMPORARY TABLE tmpNum (n INT);
INSERT INTO tmpNum VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10);
INSERT INTO tblCinemaSeat9 (seatCode, rowLabel, seatNumber, status, tblScreeningRoom9id, tblSeatType9id)
SELECT CONCAT(r.r, n.n), r.r, n.n, 'Dùng được', room.id, IF(r.r IN ('E','F'), 2, 1)
FROM tblScreeningRoom9 room
JOIN tmpRow r ON r.idx <= CASE room.id WHEN 1 THEN 10 WHEN 4 THEN 6 ELSE 8 END
JOIN tmpNum n
ORDER BY room.id, r.idx, n.n;
UPDATE tblCinemaSeat9 SET status = 'Bảo trì' WHERE tblScreeningRoom9id = 1 AND seatCode = 'J10';
DROP TEMPORARY TABLE tmpRow;
DROP TEMPORARY TABLE tmpNum;

INSERT INTO tblMovie9 (id, title, director, genre, duration, description, showingStatus, actors, poster, releaseDate, language, baseTicketPrice) VALUES
(1, 'Bình minh', 'Nguyễn Minh An', 'Tâm lí', 110, 'Một nhóm bạn trở về quê hương.', 'Đang chiếu', 'Lê Hoa, Trần Nam', NULL, '2026-08-15', 'Tiếng Việt', 85000),
(2, 'Bình minh trên biển', 'Phạm Quốc Bảo', 'Phiêu lưu', 105, 'Chuyến ra khơi của những ngư dân trẻ.', 'Đang chiếu', NULL, NULL, '2026-09-01', 'Tiếng Việt', 80000),
(3, 'Hành trình xanh', 'Đỗ Thanh Hải', 'Gia đình', 100, 'Hai anh em đạp xe xuyên Việt.', 'Đang chiếu', NULL, NULL, '2026-07-20', 'Tiếng Việt', 75000),
(4, 'Mùa thu Hà Nội', 'Vũ Hồng Nhung', 'Lãng mạn', 95, 'Câu chuyện tình trên phố cổ.', 'Ngừng chiếu', NULL, NULL, '2025-10-10', 'Tiếng Việt', 70000),
(5, 'Bình minh phương Bắc', 'Hoàng Đức Long', 'Hành động', 125, 'Đội cứu hộ vùng núi phía Bắc.', 'Sắp chiếu', NULL, NULL, '2026-12-24', 'Tiếng Việt', NULL);

INSERT INTO tblTimeSlot9 (id, name, startTime, endTime) VALUES
(1, 'Sáng', '09:00:00', '11:00:00'),
(2, 'Trưa', '11:30:00', '13:30:00'),
(3, 'Chiều', '14:00:00', '16:00:00'),
(4, 'Chiều muộn', '16:00:00', '18:00:00'),
(5, 'Tối', '18:00:00', '20:00:00'),
(6, 'Tối muộn', '19:00:00', '21:00:00'),
(7, 'Khuya', '20:30:00', '22:30:00');

-- Phòng 02 đã có suất 18:00–20:00 ngày 25/11/2026 nên không còn rảnh trong khung giờ này
-- (và cũng bận với khung 19:00–21:00 vì chồng lấn).
INSERT INTO tblScreening9 (id, screeningDate, ticketPrice, status, tblMovie9id, tblScreeningRoom9id, tblTimeSlot9id, tblManager9id) VALUES
(1, '2026-11-25', 80000, 'Đã lập lịch', 2, 2, 5, 1);
INSERT INTO tblScreeningCinemaSeat9 (holdExpireTime, status, tblScreening9id, tblCinemaSeat9id)
SELECT NULL, 'Trống', 1, s.id FROM tblCinemaSeat9 s WHERE s.tblScreeningRoom9id = 2;
