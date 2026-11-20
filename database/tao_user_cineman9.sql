-- Tạo tài khoản MySQL riêng cho ứng dụng (chạy bằng tài khoản root, ví dụ trong MySQL Workbench).
CREATE USER IF NOT EXISTS 'cineman9'@'localhost' IDENTIFIED BY 'cineman9';
CREATE USER IF NOT EXISTS 'cineman9'@'127.0.0.1' IDENTIFIED BY 'cineman9';
GRANT ALL PRIVILEGES ON cineman9.* TO 'cineman9'@'localhost';
GRANT ALL PRIVILEGES ON cineman9.* TO 'cineman9'@'127.0.0.1';
FLUSH PRIVILEGES;
