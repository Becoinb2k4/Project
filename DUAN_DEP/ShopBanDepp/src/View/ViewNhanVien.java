/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.NhanVien;
import Model.taiKhoanNV;
import Service.NhanVienService;
import Service.taiKhoanNVService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author
 */
public class ViewNhanVien extends javax.swing.JFrame {

    private HomForm1 form1 = new HomForm1();
    private NhanVienService nvService = new NhanVienService();
    private DefaultTableModel mol = new DefaultTableModel();
    taiKhoanNVService tkService = new taiKhoanNVService();
    private int index = -1;
    boolean check = false;
    boolean check2 = false;

    /**
     * Creates new form ViewNhanVien
     */
    public ViewNhanVien() {
        initComponents();
        setLocationRelativeTo(null);
        fillTable();
        checkUserRole();
    }

    private void checkUserRole() {

        String taiKhoan = form1.getTaiKhoan();

        if (!taiKhoan.equalsIgnoreCase("admin")) {
            btnThem.setVisible(false);
            btnSua.setVisible(false);
            btnAddTK.setVisible(false);
            btnLamMoi.setVisible(false);
            txtMa_NV.setVisible(false);
            txtLuong_NV.setVisible(false);
            txtDiaChi_NV.setVisible(false);
            txtTaiKhoan.setVisible(false);
            tblNhanVien.getColumnModel().getColumn(0).setMinWidth(0);
            tblNhanVien.getColumnModel().getColumn(0).setMaxWidth(0);
            tblNhanVien.getColumnModel().getColumn(0).setPreferredWidth(0);

            tblNhanVien.getColumnModel().getColumn(6).setMinWidth(0);
            tblNhanVien.getColumnModel().getColumn(6).setMaxWidth(0);
            tblNhanVien.getColumnModel().getColumn(6).setPreferredWidth(0);

            tblNhanVien.getColumnModel().getColumn(7).setMinWidth(0);
            tblNhanVien.getColumnModel().getColumn(7).setMaxWidth(0);
            tblNhanVien.getColumnModel().getColumn(7).setPreferredWidth(0);

            tblNhanVien.getColumnModel().getColumn(9).setMinWidth(0);
            tblNhanVien.getColumnModel().getColumn(9).setMaxWidth(0);
            tblNhanVien.getColumnModel().getColumn(9).setPreferredWidth(0);
        }
    }

    public void fillTable() {
        mol = (DefaultTableModel) tblNhanVien.getModel();
        mol.setRowCount(0);
        for (NhanVien nhanVien : nvService.getAllNV()) {
            mol.addRow(nhanVien.todataRow());
        }
    }

    boolean checkFormThem() {
        if (txtMa_NV.getText().trim().isEmpty()
                || txtTen_NV.getText().trim().isEmpty()
                || txtDiaChi_NV.getText().trim().isEmpty()
                || txtEmail_NV.getText().trim().isEmpty()
                || txtLuong_NV.getText().trim().isEmpty()
                || txtSDT_NV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa điền đủ thông tin!");
            return false;
        }
        for (NhanVien object : nvService.getAllNV()) {
            if (object.getTkNV().equals(txtTaiKhoan.getText())) {
                JOptionPane.showMessageDialog(this, "Đã tồn tại tài khoản! Mời thêm tài khoản mới!");
                return false;
            }
        }
        return true;
    }

    boolean checkFormSua() {
        if (txtMa_NV.getText().trim().isEmpty()
                || txtTen_NV.getText().trim().isEmpty()
                || txtDiaChi_NV.getText().trim().isEmpty()
                || txtEmail_NV.getText().trim().isEmpty()
                || txtLuong_NV.getText().trim().isEmpty()
                || txtSDT_NV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa điền đủ thông tin!");
            return false;
        }
        boolean a = false;
        for (NhanVien object : nvService.getAllNV()) {
            if (object.getMaNV().equalsIgnoreCase(txtMa_NV.getText())) {
                a = true;
            }
        }
        if (a == false) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã nhân viên phù hợp!");
            return false;
        }
        return true;
    }

    public void fillForm(String ma) {
        NhanVien nv = nvService.getNhanVienByMa(ma);
        if (nv.getGioiTinh().equalsIgnoreCase("Nam")) {
            rdNam_NV.setSelected(true);
        } else {
            rdNu_NV.setSelected(true);

        }
        txtMa_NV.setText(nv.getMaNV());
        txtDiaChi_NV.setText(nv.getDiaChi());
        txtEmail_NV.setText(nv.getEmail());
        txtLuong_NV.setText(nv.getLuongCB2());
        txtSDT_NV.setText(nv.getSdt());
        txtTen_NV.setText(nv.getTenNV());
        if (nv.getChucVu().equalsIgnoreCase("Nhân viên")) {
            cbChucVu.setSelectedItem("Nhân viên");
        } else {
            cbChucVu.setSelectedItem("Quản lý");
        }

        if (nv.getTrangThai().equalsIgnoreCase("Đang làm việc")) {
            rdDangLam_NV.setSelected(true);
        } else {
            rdNghiViec_NV.setSelected(true);
        }
        txtTaiKhoan.setText(nv.getTkNV());
    }

    public NhanVien readForm() {
        String maNv, tenNV, sdt, email, gioiTinh, chucVu, luongCB, diaChi, trangThai, tkNV;
        maNv = txtMa_NV.getText();
        tenNV = txtTen_NV.getText();
        sdt = txtSDT_NV.getText();
        email = txtEmail_NV.getText();
        gioiTinh = rdNam_NV.isSelected() ? "Nam" : "Nữ";
        chucVu = cbChucVu.getSelectedItem().equals("Nhân viên") ? "Nhân viên" : "Quản lý";
        luongCB = txtLuong_NV.getText();
        diaChi = txtDiaChi_NV.getText();
        trangThai = rdDangLam_NV.isSelected() ? "Đang làm việc" : "Nghỉ việc";
        tkNV = txtTaiKhoan.getText();
        return new NhanVien(maNv, tenNV, sdt, email, gioiTinh, chucVu, luongCB, diaChi, trangThai, tkNV);
    }

    void find(String keyword, String maOrTen) {
        List<NhanVien> list = nvService.getAllNV();
        mol.setRowCount(0);
        if (maOrTen.equalsIgnoreCase("manv")) {
            for (NhanVien nv : list) {
                if (nv.getMaNV().equalsIgnoreCase(keyword)) {
                    mol.addRow(nv.todataRow());
                }
            }
        } else {
            for (NhanVien nv : list) {
                if (nv.getTenNV().equalsIgnoreCase(keyword)) {
                    mol.addRow(nv.todataRow());
                }
            }
        }
    }

    public boolean validateTypeDataNV() {
        int a = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < txtLuong_NV.getText().length(); i++) {
            if (!Character.isDigit(txtLuong_NV.getText().charAt(i))) {
                a++;
            }
        }
        if (a > 0) {
            sb.append("Lương cơ bản sai định dạng");
            JOptionPane.showMessageDialog(this, sb);
            return false;
        }if (txtSDT_NV.getText().length() != 10) {
            sb.append("Số điện thoại phải là 10 chữ số");
            JOptionPane.showMessageDialog(this, sb);
            return false;
        } if (txtSDT_NV.getText().charAt(0) != '0') {
            sb.append("Số điện thoại phải bắt đầu bằng số 0");
            JOptionPane.showMessageDialog(this, sb);
            return false;

        } 
        if (true) {
            a = 0;
            for (int i = 1; i < txtSDT_NV.getText().length(); i++) {
                if (!Character.isDigit(txtSDT_NV.getText().charAt(i))) {
                    a++;
                }
            }
            if (a > 0) {
                sb.append("Số điện thoại phải là số");
                JOptionPane.showMessageDialog(this, sb);
                return false;
            }

        }
        
        try {

            String email = txtEmail_NV.getText();
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

   

    public boolean checkEmail() {
        try {

            String email = txtEmail_NV.getText();
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

        buttonGroupGioiTinh = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        BackNV = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMa_NV = new javax.swing.JTextField();
        txtTen_NV = new javax.swing.JTextField();
        txtDiaChi_NV = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtSDT_NV = new javax.swing.JTextField();
        txtEmail_NV = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        cbChucVu = new javax.swing.JComboBox<>();
        rdNam_NV = new javax.swing.JRadioButton();
        rdNu_NV = new javax.swing.JRadioButton();
        rdDangLam_NV = new javax.swing.JRadioButton();
        rdNghiViec_NV = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        txtLuong_NV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnAddTK = new javax.swing.JButton();
        txtTaiKhoan = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbxLoc_GioiTinh_NV1 = new javax.swing.JComboBox<>();
        cbxLoc_VaiTro_NV1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        txtTim_Ma_NV = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtTim_Ten_NV = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(204, 102, 0));

        BackNV.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        BackNV.setForeground(new java.awt.Color(255, 255, 255));
        BackNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BackNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/back.png"))); // NOI18N
        BackNV.setText("Back");
        BackNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackNVMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackNVMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BackNVMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackNV, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1083, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(BackNV, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thiết lập thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N

        jLabel1.setText("Mã nhân viên");

        jLabel2.setText("Họ tên");

        jLabel4.setText("Chức vụ");

        jLabel5.setText("Địa chỉ");

        txtMa_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMa_NVActionPerformed(evt);
            }
        });

        jLabel6.setText("Điện thoại");

        jLabel8.setText("Email");

        jLabel9.setText("Giới tính");

        jLabel10.setText("Trạng thái");

        txtEmail_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmail_NVActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm ");
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThemMouseEntered(evt);
            }
        });
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaMouseClicked(evt);
            }
        });
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseClicked(evt);
            }
        });

        cbChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lý", "Nhân viên" }));

        buttonGroupGioiTinh.add(rdNam_NV);
        rdNam_NV.setSelected(true);
        rdNam_NV.setText("Nam");

        buttonGroupGioiTinh.add(rdNu_NV);
        rdNu_NV.setText("Nữ");

        buttonGroup1.add(rdDangLam_NV);
        rdDangLam_NV.setSelected(true);
        rdDangLam_NV.setText("Đang làm việc");

        buttonGroup1.add(rdNghiViec_NV);
        rdNghiViec_NV.setText("Nghỉ việc");

        jLabel7.setText("Lương cơ bản");

        txtLuong_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuong_NVActionPerformed(evt);
            }
        });

        jLabel3.setText("Tài khoản");

        btnAddTK.setText("+");
        btnAddTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddTKMouseClicked(evt);
            }
        });
        btnAddTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTKActionPerformed(evt);
            }
        });

        txtTaiKhoan.setText("Tài khoản ...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(276, 276, 276))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtMa_NV, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTen_NV))
                                .addGap(72, 72, 72))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addGap(55, 55, 55)
                                .addComponent(btnSua)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLamMoi))
                            .addComponent(txtLuong_NV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                            .addComponent(txtDiaChi_NV, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(72, 72, 72)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(76, 76, 76))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail_NV, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                            .addComponent(txtSDT_NV)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdDangLam_NV)
                                    .addComponent(rdNam_NV)
                                    .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rdNu_NV)
                                    .addComponent(btnAddTK)
                                    .addComponent(rdNghiViec_NV))))
                        .addGap(96, 96, 96))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMa_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtSDT_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtTen_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtEmail_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdNam_NV)
                            .addComponent(rdNu_NV))
                        .addGap(2, 2, 2)))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(jLabel7)
                        .addComponent(txtLuong_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdDangLam_NV)
                        .addComponent(rdNghiViec_NV)))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiaChi_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(btnAddTK)
                    .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnLamMoi))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lọc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        jLabel13.setText("Lọc  giới tính");

        jLabel14.setText("Lọc vai trò");

        cbxLoc_GioiTinh_NV1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cbxLoc_GioiTinh_NV1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxLoc_GioiTinh_NV1ItemStateChanged(evt);
            }
        });
        cbxLoc_GioiTinh_NV1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxLoc_GioiTinh_NV1ActionPerformed(evt);
            }
        });

        cbxLoc_VaiTro_NV1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quản lý", "Nhân viên" }));
        cbxLoc_VaiTro_NV1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxLoc_VaiTro_NV1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxLoc_GioiTinh_NV1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxLoc_VaiTro_NV1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxLoc_GioiTinh_NV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxLoc_VaiTro_NV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 14))); // NOI18N

        txtTim_Ma_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTim_Ma_NVActionPerformed(evt);
            }
        });
        txtTim_Ma_NV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTim_Ma_NVKeyReleased(evt);
            }
        });

        jLabel15.setText("Mã NV");

        jLabel16.setText("Tên NV");

        txtTim_Ten_NV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTim_Ten_NVActionPerformed(evt);
            }
        });
        txtTim_Ten_NV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTim_Ten_NVKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addGap(54, 54, 54)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTim_Ma_NV, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addComponent(txtTim_Ten_NV))
                .addContainerGap(175, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTim_Ma_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtTim_Ten_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTabbedPane1.setAutoscrolls(true);
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ tên", "SDT", "Email", "Giới tính", "Chức vụ", "Lương cơ bản", "Địa chỉ", "Trạng thái", "Tài khoản"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        jTabbedPane1.addTab("Thông tin nhân viên", jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTabbedPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(17, 17, 17))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackNVMouseClicked
        HomForm1 form1 = new HomForm1();
        form1.setVisible(true);
        form1.pack();
        form1.setLocationRelativeTo(null);
        form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_BackNVMouseClicked

    private void BackNVMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackNVMouseEntered
        BackNV.setLayout(new BorderLayout(10, 10));
        BackNV.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_BackNVMouseEntered

    private void BackNVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackNVMouseExited
        BackNV.setLayout(null);
        BackNV.setBorder(null);
    }//GEN-LAST:event_BackNVMouseExited

    private void txtMa_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMa_NVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMa_NVActionPerformed

    private void txtEmail_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmail_NVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmail_NVActionPerformed

    private void btnThemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseClicked
        NhanVien nv = readForm();
//        fillTable();
//        if (checkFormThem()) {
//            if (nvService.Them(nv) > 0) {
//                JOptionPane.showMessageDialog(this, "Thêm thành công!");
//                mol.setRowCount(0);
//                mol.addRow(nv.todataRow());
//                //fillTable();
//            } else {
//                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
//            }
//        }

        if (checkFormThem()) {
            if (validateTypeDataNV()) {
                if (checkEmail()) {
                    if (nvService.Them(nv) > 0) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công!");
                        fillTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                    }
                }

            }

        }
    }//GEN-LAST:event_btnThemMouseClicked

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        txtMa_NV.setText("");
        txtDiaChi_NV.setText("");
        txtEmail_NV.setText("");
        txtLuong_NV.setText("");
        txtSDT_NV.setText("");
        txtTen_NV.setText("");
        txtTaiKhoan.setText("Tài khoản");
    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        String ma = tblNhanVien.getValueAt(tblNhanVien.getSelectedRow(), 0).toString();
        this.fillForm(ma);
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void txtLuong_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuong_NVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLuong_NVActionPerformed

    private void btnSuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseClicked
        NhanVien nv = readForm();
        if (checkFormSua()) {
            if (validateTypeDataNV()) {
                if (nvService.sua(nv) > 0) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                    fillTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại!");
                }
            }

        }
    }//GEN-LAST:event_btnSuaMouseClicked

    private void btnAddTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddTKMouseClicked
        TaiKhoanView tt = new TaiKhoanView();
        tt.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                TaiKhoanView tt = new TaiKhoanView();
                if (tt.getTitle().equalsIgnoreCase("Thêm thành công")) {
                    taiKhoanNV tk = tkService.getAllTK().get(tkService.getAllTK().size() - 1);
                    txtTaiKhoan.setText(tk.getTkNV());
                    txtTen_NV.setText(tk.getTenNV());
                }
            }
        });

        tt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tt.setSize(330, 280);

        tt.setLocationRelativeTo(this);
        tt.setVisible(true);
    }//GEN-LAST:event_btnAddTKMouseClicked

    private void cbxLoc_GioiTinh_NV1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxLoc_GioiTinh_NV1ItemStateChanged
        if (!check) {
            mol.setRowCount(0);
            for (NhanVien object : nvService.getAllNV()) {
                if (object.getGioiTinh().equalsIgnoreCase(cbxLoc_GioiTinh_NV1.getSelectedItem().toString().trim())) {
                    mol.addRow(object.todataRow());
                }
            }
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbxLoc_GioiTinh_NV1ItemStateChanged

    private void cbxLoc_VaiTro_NV1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxLoc_VaiTro_NV1ItemStateChanged
        if (!check2) {
            mol.setRowCount(0);
            for (NhanVien object : nvService.getAllNV()) {
                if (object.getChucVu().equalsIgnoreCase(cbxLoc_VaiTro_NV1.getSelectedItem().toString().trim())) {
                    mol.addRow(object.todataRow());
                }
            }
            check2 = !check2;
        } else {
            check2 = !check2;
        }
    }//GEN-LAST:event_cbxLoc_VaiTro_NV1ItemStateChanged

    private void cbxLoc_GioiTinh_NV1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxLoc_GioiTinh_NV1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxLoc_GioiTinh_NV1ActionPerformed

    private void txtTim_Ten_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTim_Ten_NVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTim_Ten_NVActionPerformed

    private void txtTim_Ma_NVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTim_Ma_NVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTim_Ma_NVActionPerformed

    private void txtTim_Ma_NVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTim_Ma_NVKeyReleased
        find(txtTim_Ma_NV.getText().trim(), "manv");
    }//GEN-LAST:event_txtTim_Ma_NVKeyReleased

    private void txtTim_Ten_NVKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTim_Ten_NVKeyReleased
        find(txtTim_Ten_NV.getText().trim(), "");
    }//GEN-LAST:event_txtTim_Ten_NVKeyReleased

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnAddTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddTKActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackNV;
    private javax.swing.JButton btnAddTK;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroupGioiTinh;
    private javax.swing.JComboBox<String> cbChucVu;
    private javax.swing.JComboBox<String> cbxLoc_GioiTinh_NV1;
    private javax.swing.JComboBox<String> cbxLoc_VaiTro_NV1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdDangLam_NV;
    private javax.swing.JRadioButton rdNam_NV;
    private javax.swing.JRadioButton rdNghiViec_NV;
    private javax.swing.JRadioButton rdNu_NV;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi_NV;
    private javax.swing.JTextField txtEmail_NV;
    private javax.swing.JTextField txtLuong_NV;
    private javax.swing.JTextField txtMa_NV;
    private javax.swing.JTextField txtSDT_NV;
    private javax.swing.JTextField txtTaiKhoan;
    private javax.swing.JTextField txtTen_NV;
    private javax.swing.JTextField txtTim_Ma_NV;
    private javax.swing.JTextField txtTim_Ten_NV;
    // End of variables declaration//GEN-END:variables
}
