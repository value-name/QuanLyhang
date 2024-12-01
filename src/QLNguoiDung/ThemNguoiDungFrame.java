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
public class ThemNguoiDungFrame extends javax.swing.JFrame {

    private JTextField txtMaNguoiDung, txtTenNguoiDung, txtEmail, txtSoDienThoai, txtDiaChi, txtLoaiNguoiDung;
    private NguoiDungFrame parentFrame;

    /**
     * Creates new form ThemNguoiDungFrame
     */
    public ThemNguoiDungFrame(NguoiDungFrame parent) {
        initComponents();
        this.parentFrame = parent;
        setTitle("Thêm Người Dùng");
        setSize(400, 300);
        setLayout(new java.awt.GridLayout(7, 2));

        add(new JLabel("Mã Người Dùng:"));
        txtMaNguoiDung = new JTextField();
        add(txtMaNguoiDung);

        add(new JLabel("Họ Tên:"));
        txtTenNguoiDung = new JTextField();
        add(txtTenNguoiDung);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Số Điện Thoại:"));
        txtSoDienThoai = new JTextField();
        add(txtSoDienThoai);

        add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        add(txtDiaChi);

        add(new JLabel("Loại người dùng:"));
        txtLoaiNguoiDung = new JTextField();
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

        if (!validateInput(maNguoiDung, tenNguoiDung, email, soDienThoai, diaChi, loaiNguoiDung)) {
            return;
        }

        String sql = "INSERT INTO NguoiDung (MaNguoiDung, TenNguoiDung, Email, SoDienThoai, DiaChi,  LoaiNguoiDung) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = parentFrame.getConnection(); PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM NguoiDung WHERE MaNguoiDung = ?")) {

            checkStmt.setString(1, maNguoiDung);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Mã người dùng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, maNguoiDung);
                ps.setString(2, tenNguoiDung);
                ps.setString(3, email);
                ps.setString(4, soDienThoai);
                ps.setString(5, diaChi);
                ps.setString(6, loaiNguoiDung);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Thêm người dùng thành công!");
                parentFrame.refreshTable();
                dispose();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm người dùng: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateInput(String maNguoiDung, String tenNguoiDung, String email, String soDienThoai, String diaChi, String loaiNguoiDung) {
        if (maNguoiDung.isEmpty() || tenNguoiDung.isEmpty() || diaChi.isEmpty() || soDienThoai.isEmpty() || email.isEmpty() || loaiNguoiDung.isEmpty()) {
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
