/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

//import Model.khachHang;
import Model.SanPham;
import Model.khachHang;
import Service.khachHangService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class KhachHang1 extends javax.swing.JFrame {

    DefaultTableModel mol = new DefaultTableModel();
    khachHangService khService = new khachHangService();

    public KhachHang1() {
        initComponents();
        setLocationRelativeTo(null);
        fillTable(khService.getAllkh());
        setResizable(false);
    }

    public void fillTable(List<khachHang> list) {
        mol = (DefaultTableModel) tblKhachHang.getModel();
        mol.setRowCount(0);
        for (khachHang hang : list) {
            mol.addRow(hang.todataRow());
        }
    }

    public void clearForm() {

        txtTenKH.setText("");

        txtSDT.setText("");
        txtEmail.setText("");
        rdNam.setSelected(true);

    }

    public boolean check() {
        if (txtEmail.getText().trim().isEmpty()
                || txtSDT.getText().trim().isEmpty()
                || txtTenKH.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thông tin khách hàng chưa điền đủ!");
            return false;
        }

        return true;
    }

    public khachHang getData() {
        String maKh, tenKh, gender, sdt, email, loaiKh, Tongchitieu;
        khachHang kh = khService.getAllkh().get(khService.getAllkh().size() - 1);
        int so = Integer.parseInt(kh.getMaKH().substring(2, kh.getMaKH().length())) + 1;
        if (String.valueOf(so).length() == 1) {
            maKh = kh.getMaKH().substring(0, 4) + String.valueOf(so);
        } else if (String.valueOf(so).length() == 2) {
            maKh = kh.getMaKH().substring(0, 3) + String.valueOf(so);
        } else {
            maKh = kh.getMaKH().substring(0, 2) + String.valueOf(so);
        }
        tenKh = txtTenKH.getText().trim();
        gender = rdNam.isSelected() ? "Nam" : "Nữ";
        sdt = txtSDT.getText().trim();
        email = txtEmail.getText().trim();
        loaiKh = "Thường";
        Tongchitieu = "0";
        return new khachHang(maKh, tenKh, gender, sdt, email, loaiKh, Tongchitieu);
    }

    public khachHang getKhachHang() {
        int index = tblKhachHang.getSelectedRow();
        if (index == -1) {
            return null;
        }
        khachHang kh = khService.getAllkh().get(index);
        return kh;
    }

    void find() {
        String keyword = txtTimKiem.getText().trim();
        List<khachHang> list = khService.getAllkh();

        mol.setRowCount(0);
        for (khachHang kh : list) {
//             if (sanPham.getMaSP().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))
//                    || sanPham.getTenSP().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) 
            if (kh.getTenKh().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))
                    || kh.getMaKH().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
                mol.addRow(kh.todataRow());
            }
//            if (kh.getTenKh().equalsIgnoreCase(keyword) || kh.getMaKH().equalsIgnoreCase(keyword)) {
//                mol.addRow(kh.todataRow());
//                
//            }
        }

    }

    public boolean checkEmail() {
        try {

            String email = txtEmail.getText();
            String checkMail = "^([a-zA-Z0-9]+\\.)*[a-zA-Z0-9]+@([a-z]+\\.)+[a-z]{2,4}$";
            if (!email.matches(checkMail)) {
                JOptionPane.showMessageDialog(this, "Email chưa đúng định dạng Example@gmail.com",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGGioiTinh = new javax.swing.ButtonGroup();
        btnGHoatDong = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnSua = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        rdNam = new javax.swing.JRadioButton();
        rdNu = new javax.swing.JRadioButton();
        txtSDT = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnKhachHang = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        BackKH = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.setBackground(new java.awt.Color(204, 102, 0));
        jTabbedPane2.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã KH", "Tên KH", "Giới tính", "SDT", "Email", "Thành viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblKhachHang);

        jLabel1.setText("Tìm kiếm");

        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnSua.setText("Sửa thông tin");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSua)
                .addGap(401, 401, 401))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSua)
                .addGap(304, 304, 304))
        );

        jTabbedPane2.addTab("Danh sách khách hàng", jPanel3);

        jLabel3.setText("Tên Khách hàng");

        jLabel4.setText("Giới tính");

        jLabel5.setText("SDT");

        jLabel6.setText("Email");

        buttonGroup1.add(rdNam);
        rdNam.setText("Nam");

        buttonGroup1.add(rdNu);
        rdNu.setText("Nữ");

        btnKhachHang.setBackground(new java.awt.Color(204, 102, 0));
        btnKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachHang.setText("Thêm");
        btnKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKhachHangMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(208, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdNam)
                                .addGap(70, 70, 70)
                                .addComponent(rdNu))
                            .addComponent(txtTenKH)
                            .addComponent(txtSDT)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(190, 190, 190))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnKhachHang)
                        .addGap(414, 414, 414))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rdNam)
                    .addComponent(rdNu))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(89, 89, 89)
                .addComponent(btnKhachHang)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Thêm  thông tin khách hàng", jPanel1);

        jPanel5.setBackground(new java.awt.Color(204, 102, 0));

        BackKH.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        BackKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BackKH.setText("Back");
        BackKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackKHMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackKHMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BackKHMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(BackKH, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(BackKH, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 594, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void BackKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackKHMouseClicked
        HomForm1 form1 = new HomForm1();
        form1.setVisible(true);
        form1.pack();
        form1.setLocationRelativeTo(null);
        form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_BackKHMouseClicked

    private void BackKHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackKHMouseEntered
        BackKH.setLayout(new BorderLayout(10, 10));
        BackKH.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_BackKHMouseEntered

    private void BackKHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackKHMouseExited
        BackKH.setLayout(null);
        BackKH.setBorder(null);
    }//GEN-LAST:event_BackKHMouseExited

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        find();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhachHangMouseClicked
        if (check()) {
            if (validateThemKhachHang()) {
                if (checkEmail()) {
                    khachHang kh = getData();
                    if (khService.themKh(kh) > 0) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công!");
                        fillTable(khService.getAllkh());
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                    }
                }

            }

        }
    }//GEN-LAST:event_btnKhachHangMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        try {
            String maKH = (String) tblKhachHang.getValueAt(tblKhachHang.getSelectedRow(), 0);
        if (tblKhachHang.getSelectedRow() >= 0) {
            SuaKhachHang skh = new SuaKhachHang();
            skh.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosed(WindowEvent e){
                    fillTable(khService.getAllkh());
                }
            });
            skh.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            skh.setSize(700, 280);
            skh.setLocationRelativeTo(null);
            skh.setVisible(true);
            skh.fillKhachHangToForm(khService.getKhachHangByMa(maKH));
            fillTable(khService.getAllkh());

        } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn khách hàng cần sửa");
        }
      


    }//GEN-LAST:event_btnSuaActionPerformed
    public boolean validateThemKhachHang() {
        int a = 0;
        String thongBao = new String();
        for (int i = 0; i < txtSDT.getText().length(); i++) {
            if (!Character.isDigit(txtSDT.getText().charAt(i))) {
                a++;
            }

        }
        if (a > 0) {
            thongBao = "Số điện thoại sai định dạng";
            JOptionPane.showMessageDialog(this, thongBao);
            return false;
        } else if (txtSDT.getText().length() != 10) {
            thongBao = ("Số điện thoại phải là 10 chữ số");
            JOptionPane.showMessageDialog(this, thongBao);
            return false;
        } else if (txtSDT.getText().charAt(0) != '0') {
            thongBao = ("Số điện thoại phải bắt đầu bằng số 0");
            JOptionPane.showMessageDialog(this, thongBao);
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhachHang1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackKH;
    private javax.swing.ButtonGroup btnGGioiTinh;
    private javax.swing.ButtonGroup btnGHoatDong;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnSua;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JRadioButton rdNam;
    private javax.swing.JRadioButton rdNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
