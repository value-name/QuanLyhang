CREATE DATABASE	QUANLY;
GO

USE QUANLY;
GO


CREATE TABLE NguoiDung (
    MaNguoiDung NVARCHAR(50) PRIMARY KEY,
    TenNguoiDung NVARCHAR(100),
    Email NVARCHAR(100),
    SoDienThoai NVARCHAR(15),
    DiaChi NVARCHAR(255),
    LoaiNguoiDung NVARCHAR(50) CHECK (LoaiNguoiDung IN ('admin', 'nhan vien'))
);

-- Tạo bảng TaiKhoan
CREATE TABLE TaiKhoan (
    MaTaiKhoan NVARCHAR(50) PRIMARY KEY,
    TenDangNhap NVARCHAR(100),
    MatKhau NVARCHAR(100),
    Email NVARCHAR(100),
    MaNguoiDung NVARCHAR(50),
    TrangThai NVARCHAR(50) CHECK (TrangThai IN ('hoat dong', 'khong hoat dong')),
    NgayTao DATETIME,
    LoaiTaiKhoan NVARCHAR(50) CHECK (LoaiTaiKhoan IN ('admin', 'nhan vien')),
    FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung)
);

-- Trigger để đồng bộ hóa Email giữa bảng NguoiDung và TaiKhoan
CREATE TRIGGER trg_UpdateTaiKhoanEmail
ON NguoiDung
AFTER UPDATE
AS
BEGIN
    -- Cập nhật Email trong bảng TaiKhoan khi bảng NguoiDung thay đổi
    UPDATE TaiKhoan
    SET Email = i.Email
    FROM TaiKhoan t
    INNER JOIN inserted i ON t.MaNguoiDung = i.MaNguoiDung
    WHERE t.MaNguoiDung = i.MaNguoiDung;
END;


CREATE TABLE DanhMucSanPham (
    MaDanhMuc NVARCHAR(50) PRIMARY KEY,
    TenDanhMuc NVARCHAR(100),
    MoTa NVARCHAR(255)
);

-- Tạo bảng SanPham
CREATE TABLE SanPham (
    MaSanPham NVARCHAR(50) PRIMARY KEY,
    TenSanPham NVARCHAR(100),
    GiaBan MONEY,
    SoLuongTon INT,
    MoTaSanPham NVARCHAR(255),
    MaDanhMuc NVARCHAR(50),
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMucSanPham(MaDanhMuc)
);

-- Tạo bảng SanPhamHetHan
CREATE TABLE SanPhamHetHan (
    MaSanPhamHetHan NVARCHAR(50) PRIMARY KEY,
    MaSanPham NVARCHAR(50),
    SoLuongHetHan INT,
    ThoiGianCapNhat DATETIME,
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);

-- Tạo bảng DonHang
CREATE TABLE DonHang (
    MaDonHang NVARCHAR(50) PRIMARY KEY,
    NgayGiaoDich DATETIME,
    TongTien MONEY,
    TrangThaiDonHang NVARCHAR(50),
    PhuongThucThanhToan NVARCHAR(50)
);

-- Tạo bảng ChiTietDonHang
CREATE TABLE ChiTietDonHang (
    MaChiTietDonHang NVARCHAR(50) PRIMARY KEY,
    MaDonHang NVARCHAR(50),
    MaSanPham NVARCHAR(50),
    SoLuong INT,
    DonGia MONEY,
    TongTien MONEY,
    TrangThaiDonHang NVARCHAR(50),
    PhuongThucThanhToan NVARCHAR(50),
    FOREIGN KEY (MaDonHang) REFERENCES DonHang(MaDonHang),
    FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSanPham)
);

-- Dữ liệu mẫu cho bảng DanhMucSanPham
INSERT INTO DanhMucSanPham (MaDanhMuc, TenDanhMuc, MoTa)
VALUES 
    (N'DM01', N'Nước uống', N'Nước uống giải khát'),
    (N'DM02', N'Bánh kẹo', N'Các loại bánh và kẹo'),
    (N'DM03', N'Mì gói', N'Mì ăn liền các loại'),
    (N'DM04', N'Sữa', N'Các loại sữa và sản phẩm từ sữa'),
    (N'DM05', N'Thực phẩm đông lạnh', N'Sản phẩm đông lạnh tiện lợi'),
    (N'DM06', N'Gia vị', N'Các loại gia vị nấu ăn'),
    (N'DM07', N'Trái cây', N'Trái cây tươi và sạch'),
    (N'DM08', N'Rau củ', N'Rau củ quả tươi'),
    (N'DM09', N'Thực phẩm khô', N'Sản phẩm thực phẩm khô đóng gói'),
    (N'DM10', N'Thực phẩm chức năng', N'Hỗ trợ sức khỏe và dinh dưỡng'),
    (N'DM11', N'Hải sản', N'Hải sản tươi sống và đông lạnh'),
    (N'DM12', N'Thịt', N'Thịt tươi và đông lạnh'),
    (N'DM13', N'Đồ hộp', N'Thực phẩm đóng hộp các loại'),
    (N'DM14', N'Đồ ăn vặt', N'Các loại snack, bim bim'),
    (N'DM15', N'Ngũ cốc', N'Ngũ cốc dinh dưỡng các loại'),
    (N'DM16', N'Nước chấm', N'Nước mắm, xì dầu và tương ớt'),
    (N'DM17', N'Phụ gia thực phẩm', N'Bột ngọt, hương liệu'),
    (N'DM18', N'Đồ gia dụng', N'Sản phẩm dùng trong gia đình'),
    (N'DM19', N'Đồ nấu ăn', N'Dụng cụ nấu ăn tiện ích'),
    (N'DM20', N'Bột và gia công', N'Các loại bột nấu ăn'),
    (N'DM21', N'Đồ uống có cồn', N'Bia, rượu các loại'),
    (N'DM22', N'Đồ uống năng lượng', N'Nước tăng lực'),
    (N'DM23', N'Chăm sóc cá nhân', N'Sản phẩm chăm sóc cá nhân'),
    (N'DM24', N'Đồ dùng văn phòng', N'Dụng cụ văn phòng phẩm'),
    (N'DM25', N'Mỹ phẩm', N'Sản phẩm làm đẹp'),
    (N'DM26', N'Vệ sinh nhà cửa', N'Sản phẩm tẩy rửa và vệ sinh'),
    (N'DM27', N'Đồ chơi', N'Đồ chơi trẻ em các loại'),
    (N'DM28', N'Quần áo', N'Quần áo và phụ kiện'),
    (N'DM29', N'Dụng cụ thể thao', N'Dụng cụ và thiết bị thể thao'),
    (N'DM30', N'Đồ điện tử', N'Thiết bị điện tử và phụ kiện');

-- Dữ liệu mẫu cho bảng SanPham
INSERT INTO SanPham (MaSanPham, TenSanPham, GiaBan, SoLuongTon, MoTaSanPham, MaDanhMuc)
VALUES 
(N'SP01', N'Coca Cola', 10000, 100, N'Nước ngọt có ga', N'DM01'),
    (N'SP02', N'Oreo', 15000, 50, N'Bánh quy kẹp kem', N'DM02'),
    (N'SP03', N'Mì Hảo Hảo', 5000, 200, N'Mì ăn liền vị tôm chua cay', N'DM03'),
    (N'SP04', N'Vinamilk', 25000, 75, N'Sữa tươi tiệt trùng', N'DM04'),
    (N'SP05', N'Cá viên đông lạnh', 30000, 60, N'Cá viên đông lạnh chế biến sẵn', N'DM05'),
    (N'SP06', N'Bột nêm Knorr', 15000, 120, N'Gia vị nêm thức ăn', N'DM06'),
    (N'SP07', N'Táo Mỹ', 40000, 40, N'Táo nhập khẩu từ Mỹ', N'DM07'),
    (N'SP08', N'Rau muống', 10000, 90, N'Rau muống tươi', N'DM08'),
    (N'SP09', N'Bún khô', 20000, 100, N'Sản phẩm bún khô đóng gói', N'DM09'),
    (N'SP10', N'Vitamin C', 50000, 30, N'Viên uống bổ sung vitamin C', N'DM10'),
    (N'SP11', N'Tôm sú', 150000, 20, N'Tôm sú đông lạnh', N'DM11'),
    (N'SP12', N'Thịt bò Úc', 200000, 15, N'Thịt bò nhập khẩu', N'DM12'),
    (N'SP13', N'Cá hộp', 25000, 80, N'Cá đóng hộp sẵn', N'DM13'),
    (N'SP14', N'Snack Poca', 12000, 150, N'Snack khoai tây vị phô mai', N'DM14'),
    (N'SP15', N'Ngũ cốc Kelloggs', 60000, 40, N'Ngũ cốc ăn sáng', N'DM15'),
    (N'SP16', N'Nước mắm Nam Ngư', 20000, 300, N'Nước mắm truyền thống', N'DM16'),
    (N'SP17', N'Bột ngọt Ajinomoto', 18000, 250, N'Gia vị bột ngọt', N'DM17'),
    (N'SP18', N'Chảo chống dính', 150000, 10, N'Dụng cụ nấu ăn tiện lợi', N'DM19'),
    (N'SP19', N'Bột mì', 20000, 70, N'Bột mì đa dụng', N'DM20'),
    (N'SP20', N'Tiger Beer', 17000, 200, N'Bia Tiger lon 330ml', N'DM21'),
    (N'SP21', N'Red Bull', 10000, 180, N'Nước tăng lực lon', N'DM22'),
    (N'SP22', N'Dầu gội Clear', 95000, 60, N'Dầu gội ngăn gàu', N'DM23'),
    (N'SP23', N'Bút bi Thiên Long', 5000, 500, N'Dụng cụ văn phòng phẩm', N'DM24'),
    (N'SP24', N'Son môi MAC', 550000, 20, N'Son môi cao cấp', N'DM25'),
    (N'SP25', N'Nước lau sàn Sunlight', 25000, 90, N'Nước lau sàn', N'DM26'),
    (N'SP26', N'Xếp hình Lego', 800000, 15, N'Đồ chơi xếp hình', N'DM27'),
    (N'SP27', N'Áo thun cotton', 120000, 50, N'Áo thun chất liệu cotton', N'DM28'),
    (N'SP28', N'Bóng đá', 300000, 20, N'Dụng cụ chơi thể thao', N'DM29'),
    (N'SP29', N'Tai nghe Bluetooth', 500000, 30, N'Tai nghe không dây', N'DM30'),
    (N'SP30', N'Nước khoáng Lavie', 8000, 300, N'Nước khoáng thiên nhiên', N'DM01');

	-- Dữ liệu mẫu cho bảng NguoiDung
INSERT INTO NguoiDung (MaNguoiDung, TenNguoiDung, Email, SoDienThoai, DiaChi, LoaiNguoiDung)
VALUES 
    (N'ND01', N'Nguyễn Văn A', N'tk01@gmail.com', N'0938471280', N'Hà Nội', N'admin'),
    (N'ND02', N'Nguyễn Văn B', N'tk02@gmail.com', N'0987654321', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND03', N'Nguyễn Văn C', N'tk03@gmail.com', N'0987654322', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND04', N'Nguyễn Văn D', N'tk04@gmail.com', N'0987654323', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND05', N'Nguyễn Văn E', N'tk05@gmail.com', N'0987654324', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND06', N'Nguyễn Văn F', N'tk06@gmail.com', N'0987654325', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND07', N'Nguyễn Văn G', N'tk07@gmail.com', N'0987654326', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND08', N'Nguyễn Văn H', N'tk08@gmail.com', N'0987654327', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND09', N'Nguyễn Văn I', N'tk09@gmail.com', N'0987654328', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND10', N'Nguyễn Văn K', N'tk10@gmail.com', N'0987654391', N'Hồ Chí Minh', N'nhan vien'),
    (N'ND11', N'Nguyễn Văn L', N'tk11@gmail.com', N'0987654310', N'Hồ Chí Minh', N'nhan vien');

-- Dữ liệu mẫu cho bảng TaiKhoan
INSERT INTO TaiKhoan (MaTaiKhoan, TenDangNhap, MatKhau, Email, MaNguoiDung, TrangThai, NgayTao, LoaiTaiKhoan)
VALUES 
    (N'TK01', N'admin', N'admin', N'tk01@gmail.com', N'ND01', N'hoat dong', GETDATE(), N'admin'),
    (N'TK02', N'user', N'user', N'tk02@gmail.com', N'ND02', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK03', N'user1', N'user', N'tk03@gmail.com', N'ND03', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK04', N'user2', N'user', N'tk04@gmail.com', N'ND04', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK05', N'user3', N'user', N'tk05@gmail.com', N'ND05', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK06', N'user4', N'user', N'tk06@gmail.com', N'ND06', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK07', N'user5', N'user', N'tk07@gmail.com', N'ND07', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK08', N'user6', N'user', N'tk08@gmail.com', N'ND08', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK09', N'user7', N'user', N'tk09@gmail.com', N'ND09', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK10', N'user8', N'user', N'tk10@gmail.com', N'ND10', N'hoat dong', GETDATE(), N'nhan vien'),
    (N'TK11', N'user9', N'user', N'tk11@gmail.com', N'ND11', N'hoat dong', GETDATE(), N'nhan vien');


-- Dữ liệu mẫu cho bảng SanPhamHetHan
INSERT INTO SanPhamHetHan (MaSanPhamHetHan, MaSanPham, SoLuongHetHan, ThoiGianCapNhat)
VALUES 
    (N'SPHH01', N'SP01', 10, GETDATE()),
    (N'SPHH02', N'SP03', 15, GETDATE()),
    (N'SPHH03', N'SP05', 5, GETDATE()),
    (N'SPHH04', N'SP09', 20, GETDATE()),
    (N'SPHH05', N'SP13', 8, GETDATE()),
    (N'SPHH06', N'SP15', 12, GETDATE()),
    (N'SPHH07', N'SP20', 25, GETDATE()),
    (N'SPHH08', N'SP21', 30, GETDATE()),
    (N'SPHH09', N'SP22', 7, GETDATE()),
    (N'SPHH10', N'SP27', 3, GETDATE());


-- Dữ liệu mẫu cho bảng DonHang
INSERT INTO DonHang (MaDonHang, NgayGiaoDich, TongTien, TrangThaiDonHang, PhuongThucThanhToan)
VALUES 
    (N'DH01', '2024-12-20 10:00:00', 20000, N'Đã giao', N'Tiền mặt'),
    (N'DH02', '2024-12-21 14:30:00', 15000, N'Đang xử lý', N'Chuyển khoản'),
    (N'DH03', '2024-12-22 09:00:00', 25000, N'Đã giao', N'Tiền mặt'),
    (N'DH04', '2024-12-23 16:45:00', 12000, N'Đang xử lý', N'Chuyển khoản'),
    (N'DH05', '2024-12-24 08:00:00', 30000, N'Chưa xử lý', N'Tiền mặt'),
    (N'DH06', '2024-12-25 12:00:00', 20000, N'Đã giao', N'Chuyển khoản'),
    (N'DH07', '2024-12-26 14:00:00', 18000, N'Đang xử lý', N'Tiền mặt'),
    (N'DH08', '2024-12-27 10:00:00', 22000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'DH09', '2024-12-28 09:30:00', 15000, N'Đang xử lý', N'Tiền mặt'),
    (N'DH10', '2024-12-29 11:45:00', 25000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'DH11', '2024-12-30 08:20:00', 30000, N'Đã giao', N'Tiền mặt'),
    (N'DH12', '2024-12-31 15:00:00', 35000, N'Đang xử lý', N'Chuyển khoản'),
    (N'DH13', '2023-01-01 10:00:00', 27000, N'Chưa xử lý', N'Tiền mặt'),
    (N'DH14', '2023-01-02 14:30:00', 18000, N'Đã giao', N'Chuyển khoản'),
    (N'DH15', '2023-01-03 13:00:00', 22000, N'Đang xử lý', N'Tiền mặt'),
    (N'DH16', '2023-01-04 17:30:00', 24000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'DH17', '2023-01-05 09:15:00', 15000, N'Đã giao', N'Tiền mặt'),
    (N'DH18', '2023-01-06 16:00:00', 19000, N'Đang xử lý', N'Chuyển khoản'),
    (N'DH19', '2023-01-07 10:30:00', 26000, N'Chưa xử lý', N'Tiền mặt'),
    (N'DH20', '2023-01-08 11:50:00', 23000, N'Đã giao', N'Chuyển khoản'),
    (N'DH21', '2023-01-09 12:15:00', 20000, N'Đang xử lý', N'Tiền mặt'),
    (N'DH22', '2023-01-10 13:00:00', 28000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'DH23', '2023-01-11 15:30:00', 15000, N'Đã giao', N'Tiền mặt'),
    (N'DH24', '2023-01-12 09:10:00', 22000, N'Đang xử lý', N'Chuyển khoản'),
    (N'DH25', '2023-01-13 17:45:00', 25000, N'Chưa xử lý', N'Tiền mặt'),
    (N'DH26', '2023-01-14 10:00:00', 24000, N'Đã giao', N'Chuyển khoản'),
    (N'DH27', '2023-01-15 14:20:00', 27000, N'Đang xử lý', N'Tiền mặt'),
    (N'DH28', '2023-01-16 09:00:00', 30000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'DH29', '2023-01-17 12:30:00', 32000, N'Đã giao', N'Tiền mặt'),
    (N'DH30', '2023-01-18 16:45:00', 20000, N'Đang xử lý', N'Chuyển khoản');

-- Dữ liệu mẫu cho bảng ChiTietDonHang với 30 chi tiết đơn hàng
INSERT INTO ChiTietDonHang (MaChiTietDonHang, MaDonHang, MaSanPham, SoLuong, DonGia, TongTien, TrangThaiDonHang, PhuongThucThanhToan)
VALUES 
    (N'CTDH01', N'DH01', N'SP01', 2, 10000, 20000, N'Đã giao', N'Tiền mặt'),
    (N'CTDH02', N'DH02', N'SP02', 1, 15000, 15000, N'Đang xử lý', N'Chuyển khoản'),
    (N'CTDH03', N'DH03', N'SP03', 3, 8000, 24000, N'Đã giao', N'Tiền mặt'),
    (N'CTDH04', N'DH04', N'SP04', 1, 12000, 12000, N'Đang xử lý', N'Chuyển khoản'),
    (N'CTDH05', N'DH05', N'SP05', 2, 15000, 30000, N'Chưa xử lý', N'Tiền mặt'),
    (N'CTDH06', N'DH06', N'SP06', 5, 10000, 50000, N'Đã giao', N'Chuyển khoản'),
    (N'CTDH07', N'DH07', N'SP07', 1, 18000, 18000, N'Đang xử lý', N'Tiền mặt'),
    (N'CTDH08', N'DH08', N'SP08', 3, 7500, 22500, N'Chưa xử lý', N'Chuyển khoản'),
    (N'CTDH09', N'DH09', N'SP09', 2, 7000, 14000, N'Đang xử lý', N'Tiền mặt'),
    (N'CTDH10', N'DH10', N'SP10', 3, 8000, 24000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'CTDH11', N'DH11', N'SP11', 2, 17500, 35000, N'Đã giao', N'Tiền mặt'),
    (N'CTDH12', N'DH12', N'SP12', 2, 20000, 40000, N'Đang xử lý', N'Chuyển khoản'),
    (N'CTDH13', N'DH13', N'SP13', 5, 11000, 55000, N'Chưa xử lý', N'Tiền mặt'),
    (N'CTDH14', N'DH14', N'SP14', 3, 10000, 30000, N'Đã giao', N'Chuyển khoản'),
    (N'CTDH15', N'DH15', N'SP15', 1, 12000, 12000, N'Đang xử lý', N'Tiền mặt'),
    (N'CTDH16', N'DH16', N'SP16', 2, 12500, 25000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'CTDH17', N'DH17', N'SP17', 3, 9000, 27000, N'Đã giao', N'Tiền mặt'),
    (N'CTDH18', N'DH18', N'SP18', 4, 9500, 38000, N'Đang xử lý', N'Chuyển khoản'),
    (N'CTDH19', N'DH19', N'SP19', 2, 13000, 26000, N'Chưa xử lý', N'Tiền mặt'),
    (N'CTDH20', N'DH20', N'SP20', 1, 23000, 23000, N'Đã giao', N'Chuyển khoản'),
    (N'CTDH21', N'DH21', N'SP21', 2, 12000, 24000, N'Đang xử lý', N'Tiền mặt'),
    (N'CTDH22', N'DH22', N'SP22', 3, 9333, 28000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'CTDH23', N'DH23', N'SP23', 1, 15000, 15000, N'Đã giao', N'Tiền mặt'),
    (N'CTDH24', N'DH24', N'SP24', 3, 7300, 21900, N'Đang xử lý', N'Chuyển khoản'),
    (N'CTDH25', N'DH25', N'SP25', 2, 12500, 25000, N'Chưa xử lý', N'Tiền mặt'),
    (N'CTDH26', N'DH26', N'SP26', 3, 8000, 24000, N'Đã giao', N'Chuyển khoản'),
    (N'CTDH27', N'DH27', N'SP27', 4, 7500, 30000, N'Đang xử lý', N'Tiền mặt'),
    (N'CTDH28', N'DH28', N'SP28', 2, 15000, 30000, N'Chưa xử lý', N'Chuyển khoản'),
    (N'CTDH29', N'DH29', N'SP29', 2, 16000, 32000, N'Đã giao', N'Tiền mặt'),
    (N'CTDH30', N'DH30', N'SP30', 1, 20000, 20000, N'Đang xử lý', N'Chuyển khoản');

-- Tạo Trigger đồng bộ hóa LoaiNguoiDung với LoaiTaiKhoan
IF EXISTS (SELECT * FROM sys.triggers WHERE name = 'Trigger_Update_LoaiNguoiDung')
BEGIN
    DROP TRIGGER Trigger_Update_LoaiNguoiDung;
END
GO

CREATE TRIGGER Trigger_Update_LoaiNguoiDung
ON NguoiDung
AFTER UPDATE
AS
BEGIN
    -- Kiểm tra nếu cột LoaiNguoiDung bị thay đổi
    IF (UPDATE(LoaiNguoiDung))
    BEGIN
        -- Cập nhật LoaiTaiKhoan trong bảng TaiKhoan khi LoaiNguoiDung thay đổi
        UPDATE TaiKhoan
        SET LoaiTaiKhoan = INSERTED.LoaiNguoiDung
        FROM TaiKhoan
        INNER JOIN INSERTED
        ON TaiKhoan.MaNguoiDung = INSERTED.MaNguoiDung;
    END
END
GO


	-- Lấy dữ liệu từ bảng DonHang và thêm cột TổngTiềnTatCaHoaDon
SELECT *, 
       (SELECT SUM(TongTien) FROM DonHang) AS TongTienTatCaHoaDon
FROM DonHang;


	-- Lấy dữ liệu từ bảng NguoiDung
SELECT * FROM NguoiDung;

-- Lấy dữ liệu từ bảng DanhMucSanPham
SELECT * FROM DanhMucSanPham;

-- Lấy dữ liệu từ bảng SanPham
SELECT * FROM SanPham;

-- Lấy dữ liệu từ bảng TonKho
SELECT * FROM SanPhamHetHan;

-- Lấy dữ liệu từ bảng DonHang
SELECT * FROM DonHang;

-- Lấy dữ liệu từ bảng ChiTietDonHang
SELECT * FROM ChiTietDonHang;

-- Lấy dữ liệu từ bảng TaiKhoan
SELECT * FROM TaiKhoan;