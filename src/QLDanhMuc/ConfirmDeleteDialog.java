package QLDanhMuc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmDeleteDialog extends JDialog {
    private JButton confirmButton, cancelButton;
    private boolean confirmed = false;

    public ConfirmDeleteDialog(JFrame parent) {
        super(parent, "Xác nhận xóa", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Bạn có chắc chắn muốn xóa danh mục này không?", SwingConstants.CENTER);
        add(messageLabel, BorderLayout.CENTER);

        // Tạo các nút
        JPanel buttonPanel = new JPanel(new FlowLayout());
        confirmButton = new JButton("Đồng ý");
        cancelButton = new JButton("Hủy");

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Hành động khi bấm nút Đồng ý
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;  // Đặt cờ xác nhận là true
                dispose();  // Đóng dialog
            }
        });

        // Hành động khi bấm nút Hủy
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Đóng dialog mà không thay đổi gì
            }
        });
    }

    // Phương thức để kiểm tra nếu người dùng đã xác nhận xóa
    public boolean isConfirmed() {
        return confirmed;
    }
}
