package QLNguoiDung;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */


import javax.swing.*;
import java.sql.*;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class SuaNguoiDungFrame extends javax.swing.JFrame {

    private JTextField txtMaNguoiDung, txtTenNguoiDung, txtEmail, txtSoDienThoai, txtDiaChi, txtLoaiNguoiDung;
    private String maNguoiDung;
    private NguoiDungFrame parentFrame;

    /**
     * Creates new form SuaNguoiDungFrame
     */
    public SuaNguoiDungFrame(String maNguoiDung, String tenNguoiDung, String email, String soDienThoai, String diaChi, String loaiNguoiDung, NguoiDungFrame parent) {
        initComponents();
        this.maNguoiDung = maNguoiDung;
        this.parentFrame = parent;
        setTitle("Sửa Người Dùng");
        setSize(400, 300);
        setLayout(new java.awt.GridLayout(7, 2));

        add(new JLabel("Mã Người Dùng:"));
        txtMaNguoiDung = new JTextField(maNguoiDung);
        txtMaNguoiDung.setEditable(false); // Không cho phép chỉnh sửa mã người dùng
        add(txtMaNguoiDung);

        add(new JLabel("Họ Tên:"));
        txtTenNguoiDung = new JTextField(tenNguoiDung);
        add(txtTenNguoiDung);

        add(new JLabel("Email:"));
        txtEmail = new JTextField(email);
        add(txtEmail);

        add(new JLabel("Số Điện Thoại:"));
        txtSoDienThoai = new JTextField(soDienThoai);
        add(txtSoDienThoai);

        add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField(diaChi);
        add(txtDiaChi);

        add(new JLabel("Loại người dùng:"));
        txtLoaiNguoiDung = new JTextField(loaiNguoiDung);
        add(txtLoaiNguoiDung);

        JButton btnLuu = new JButton("Lưu");
        add(btnLuu);

        btnLuu.addActionListener(e -> luuNguoiDung());

        // Đặt hành động khi nhấn X là đóng cửa sổ này mà không thoát ứng dụng
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void luuNguoiDung() {
        String maNguoiDung = txtMaNguoiDung.getText().trim();
        String tenNguoiDung = txtTenNguoiDung.getText().trim();
        String email = txtEmail.getText().trim();
        String soDienThoai = txtSoDienThoai.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String loaiNguoiDung = txtLoaiNguoiDung.getText().trim();

        // Kiểm tra tính hợp lệ của dữ liệu nhập vào
        if (!validateInput(maNguoiDung, tenNguoiDung, email, soDienThoai, diaChi, loaiNguoiDung)) {
            return;
        }

        // Kiểm tra xem MaNguoiDung đã tồn tại chưa
        String checkSql = "SELECT COUNT(*) FROM NguoiDung WHERE MaNguoiDung = ? AND MaNguoiDung <> ?";
        try (Connection conn = parentFrame.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            conn.setAutoCommit(false); // Tắt chế độ AutoCommit để bắt đầu giao dịch

            // Kiểm tra xem mã người dùng mới đã tồn tại chưa
            checkStmt.setString(1, maNguoiDung);
            checkStmt.setString(2, this.maNguoiDung);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Mã người dùng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cập nhật bảng TaiKhoan để thay đổi MaNguoiDung cũ thành mới
            String sqlTaiKhoan = "UPDATE TaiKhoan SET MaNguoiDung=? WHERE MaNguoiDung=?";
            try (PreparedStatement psTaiKhoan = conn.prepareStatement(sqlTaiKhoan)) {
                psTaiKhoan.setString(1, maNguoiDung);
                psTaiKhoan.setString(2, this.maNguoiDung);
                psTaiKhoan.executeUpdate();
            }

            // Cập nhật bảng NguoiDung
            String sqlNguoiDung = "UPDATE NguoiDung SET MaNguoiDung=?, TenNguoiDung=?, Email=?, SoDienThoai=?, DiaChi=?, LoaiNguoiDung=? WHERE MaNguoiDung=?";
            try (PreparedStatement psNguoiDung = conn.prepareStatement(sqlNguoiDung)) {
                psNguoiDung.setString(1, maNguoiDung);
                psNguoiDung.setString(2, tenNguoiDung);
                psNguoiDung.setString(3, email);
                psNguoiDung.setString(4, soDienThoai);
                psNguoiDung.setString(5, diaChi);
                psNguoiDung.setString(6, loaiNguoiDung);
                psNguoiDung.setString(7, this.maNguoiDung);
                psNguoiDung.executeUpdate();
            }

            conn.commit(); // Cam kết giao dịch nếu tất cả các câu lệnh đều thành công
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            parentFrame.refreshTable(); // Cập nhật lại bảng trong cửa sổ cha
            dispose(); // Đóng cửa sổ hiện tại
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = parentFrame.getConnection()) {
                conn.rollback(); // Lùi giao dịch nếu có lỗi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin người dùng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput(String maNguoiDung, String tenNguoiDung, String email, String soDienThoai, String diaChi, String loaiNguoiDung) {
        if (maNguoiDung.isEmpty() || tenNguoiDung.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || diaChi.isEmpty() || loaiNguoiDung.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!Pattern.matches("\\d{10}", soDienThoai)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!Pattern.matches("^[\\w-.]+@[\\w-]+\\.[\\w-.]+$", email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!loaiNguoiDung.equalsIgnoreCase("admin") && !loaiNguoiDung.equalsIgnoreCase("nhan vien")) {
            JOptionPane.showMessageDialog(this, "Loại người dùng phải là 'admin' hoặc 'nhan vien'!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
