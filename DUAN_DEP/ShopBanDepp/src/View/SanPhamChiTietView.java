/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.ChatLieu;
import Model.DanhMuc;
import Model.MauSac;
import Model.NhaSanXuat;
import Model.SanPham;
import Service.ChatLieuService;
import Service.DanhMucService;
import Service.MauSacSevice;
import Service.NhaSanXuatSevice;
import Service.SanPhamService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author 
 */
public class SanPhamChiTietView extends javax.swing.JFrame {

    SanPhamService spService = new SanPhamService();
    DanhMucService dmService = new DanhMucService();
    NhaSanXuatSevice nsxService = new NhaSanXuatSevice();
    MauSacSevice msService = new MauSacSevice();
    ChatLieuService clService = new ChatLieuService();
    DefaultTableModel mol = new DefaultTableModel();
    int soTrang;
    int stt = spService.getAll().size();
    boolean check = false;

    public SanPhamChiTietView() {
        initComponents();

    }

    void fillTable(List<SanPham> list) {
        mol = (DefaultTableModel) tblSPCT.getModel();
        mol.setRowCount(0);

        for (SanPham sanPham : list) {
            mol.addRow(sanPham.todataRowSPCT());
        }
    }

    private void updateMauSacCombobox() {
        List<MauSac> mauSacList = msService.getAllMS();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (MauSac mauSac : mauSacList) {
            model.addElement(mauSac.getTenMS());
        }
        cbMau.setModel(model);
    }

    private void updateDanhMucCombobox() {
        // Gọi phương thức để lấy danh sách danh mục mới từ cơ sở dữ liệu hoặc nơi bạn lưu trữ chúng
        List<DanhMuc> danhMucList = dmService.getAllDM();
        // Tạo một DefaultComboBoxModel<String> mới
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        // Thêm các mục từ danh sách DanhMuc vào model với toString()
        for (DanhMuc danhMuc : danhMucList) {
            model.addElement(danhMuc.getTenDM());
        }
        // Gán model cho JComboBox
        cbDanhMuc.setModel(model);
    }

    private void updateNSXCombobox() {
        List<NhaSanXuat> nsxList = nsxService.getAllNSX();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (NhaSanXuat nsx : nsxList) {
            model.addElement(nsx.getTenNSX());
        }
        cbNSX.setModel(model);
    }

    private void updateCLCombobox() {
        List<ChatLieu> clList = clService.getAllChatLieu();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (ChatLieu cl : clList) {
            model.addElement(cl.getTenCL());
        }
        cbChatLieu.setModel(model);
    }

    public void fillFormCTSP(String maSP) {
        updateCLCombobox();
        updateDanhMucCombobox();
        updateMauSacCombobox();
        updateNSXCombobox();
        List<SanPham> list = spService.findSP(maSP);
        SanPham sp = list.get(0);
        txtMaCTSP.setText(sp.getMaSPCT());
        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSP());
        txtSoLuong.setText(sp.getSoLuong());
        txtXuatXu.setText(sp.getXuatXu());
        txtMoTa.setText(sp.getMoTa());
        cbChatLieu.setSelectedItem(sp.getChatLieu());
        cbKichThuoc.setSelectedItem(sp.getKichThuoc());
        cbMau.setSelectedItem(sp.getMauSac());
        cbNSX.setSelectedItem(sp.getNhaSX());
        txtDonGia.setText(sp.getDonGia2());
        String tenDM = null;
        for (DanhMuc dm : dmService.getAllDM()) {
            if (dm.getMaDM().equalsIgnoreCase(sp.getMaDM())) {
                tenDM = dm.getTenDM();
                break;
            }
        }
        cbDanhMuc.setSelectedItem(tenDM);
        fillTable(list);
    }

    boolean checkDataOnForm() {
        if (txtMaSP.getText().trim().isEmpty()
                || txtMaSP.getText().trim().isEmpty()
                || txtTenSP.getText().trim().isEmpty()
                || txtXuatXu.getText().trim().isEmpty()
                || txtSoLuong.getText().trim().isEmpty()
                || txtMaCTSP.getText().trim().isEmpty()
                || txtMoTa.getText().trim().isEmpty()
                || txtDonGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa điền đủ");
            return false;
        }
        if (Integer.parseInt(txtSoLuong.getText().trim()) < 1) {
            JOptionPane.showMessageDialog(this, "Số lượng không được âm");
            return false;
        }
        if (Integer.parseInt(txtDonGia.getText().trim()) < 1) {
            JOptionPane.showMessageDialog(this, "giá không được âm");
            return false;
        }
        for (SanPham object : spService.getAll()) {
            for (DanhMuc danhMuc : dmService.getAllDM()) {
                if (object.getTenSP().equalsIgnoreCase(txtTenSP.getText().trim())
                        && object.getChatLieu().equalsIgnoreCase(cbChatLieu.getSelectedItem().toString())
                        && object.getKichThuoc().equalsIgnoreCase(cbKichThuoc.getSelectedItem().toString())
                        && object.getMauSac().equalsIgnoreCase(cbMau.getSelectedItem().toString())
                        && object.getNhaSX().equalsIgnoreCase(cbNSX.getSelectedItem().toString())
                        && object.getXuatXu().equalsIgnoreCase(txtXuatXu.getText().trim())
                        && danhMuc.getTenDM().equalsIgnoreCase(cbDanhMuc.getSelectedItem().toString())) {
                    JOptionPane.showMessageDialog(this, "sản phẩm đã tồn tại");
                    return false;
                }
            }
        }
        return true;
    }

    boolean checkDataOnForm2() {
        if (txtMaSP.getText().trim().isEmpty()
                || txtMaSP.getText().trim().isEmpty()
                || txtTenSP.getText().trim().isEmpty()
                || txtXuatXu.getText().trim().isEmpty()
                || txtSoLuong.getText().trim().isEmpty()
                || txtMaCTSP.getText().trim().isEmpty()
                || txtMoTa.getText().trim().isEmpty()
                || txtDonGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn chưa điền đủ thông tin");
            return false;
        }
        if (Integer.parseInt(txtSoLuong.getText().trim()) < 1) {
            JOptionPane.showMessageDialog(this, "Số lượng không được âm");
            return false;
        }
        if (Integer.parseInt(txtDonGia.getText().trim()) < 1) {
            JOptionPane.showMessageDialog(this, "Đơn giá không được âm");
            return false;
        }
        for (SanPham object : spService.getAll()) {
            for (DanhMuc danhMuc : dmService.getAllDM()) {
                if (object.getTenSP().equalsIgnoreCase(txtTenSP.getText().trim())
                        && object.getChatLieu().equalsIgnoreCase(cbChatLieu.getSelectedItem().toString())
                        && object.getKichThuoc().equalsIgnoreCase(cbKichThuoc.getSelectedItem().toString())
                        && object.getMauSac().equalsIgnoreCase(cbMau.getSelectedItem().toString())
                        && object.getNhaSX().equalsIgnoreCase(cbNSX.getSelectedItem().toString())
                        && object.getXuatXu().equalsIgnoreCase(txtXuatXu.getText().trim())
                        && danhMuc.getTenDM().equalsIgnoreCase(cbDanhMuc.getSelectedItem().toString())
                        && object.getDonGia2().equalsIgnoreCase(txtDonGia.getText())) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm sửa đã tồn tain  hãy sửa lại");
                    return false;
                }
            }
        }
        return true;
    }

    public SanPham getDataOnForm2() {
        String maSP, tenSP, maDM = null, maNSX = null, donGia, trangThai, maSPCT, chatLieu, kichThuoc, mauSac, soLuong, nhaSX, xuatXu, moTa;
        SanPham sp = spService.getAll().get(spService.getAll().size() - 1);
        maSP = txtMaSP.getText();
        maSPCT = txtMaCTSP.getText();
        tenSP = txtTenSP.getText();
        soLuong = txtSoLuong.getText().trim();
        xuatXu = txtXuatXu.getText().trim();
        moTa = txtMoTa.getText().trim();
        chatLieu = cbChatLieu.getSelectedItem().toString();
        donGia = txtDonGia.getText().trim();
        if (Integer.parseInt(txtSoLuong.getText().trim()) > 0) {
            trangThai = "1";
        } else {
            trangThai = "0";
        }
        kichThuoc = cbKichThuoc.getSelectedItem().toString();
        mauSac = cbMau.getSelectedItem().toString();
        nhaSX = cbNSX.getSelectedItem().toString();
        for (DanhMuc object : dmService.getAllDM()) {
            if (object.getTenDM().equalsIgnoreCase(cbDanhMuc.getSelectedItem().toString())) {
                maDM = object.getMaDM();
                break;
            }
        }
        for (NhaSanXuat object : nsxService.getAllNSX()) {
            if (object.getTenNSX().equalsIgnoreCase(cbNSX.getSelectedItem().toString())) {
                maNSX = object.getMaNSX();
                break;
            }
        }
        return new SanPham(maSP, tenSP, maDM, maNSX, donGia, trangThai, maSPCT, chatLieu, kichThuoc, mauSac, soLuong, nhaSX, xuatXu, moTa);
    }

    public SanPham getDataOnForm() {
        String maSP, tenSP, maDM = null, maNSX = null, donGia, trangThai, maSPCT, chatLieu, kichThuoc, mauSac, soLuong, nhaSX, xuatXu, moTa;
        SanPham sp = spService.getAll().get(spService.getAll().size() - 1);
        int so = Integer.parseInt(sp.getMaSP().substring(2, sp.getMaSP().length())) + 1;
        if (String.valueOf(so).length() == 1) {
            maSP = sp.getMaSP().substring(0, 4) + String.valueOf(so);
            maSPCT = "CT" + maSP;
        } else if (String.valueOf(so).length() == 2) {
            maSP = sp.getMaSP().substring(0, 3) + String.valueOf(so);
            maSPCT = "CT" + maSP;
        } else {
            maSP = sp.getMaSP().substring(0, 2) + String.valueOf(so);
            maSPCT = "CT" + maSP;
        }
        tenSP = txtTenSP.getText();
        soLuong = txtSoLuong.getText().trim();
        xuatXu = txtXuatXu.getText().trim();
        moTa = txtMoTa.getText().trim();
        chatLieu = cbChatLieu.getSelectedItem().toString();
        donGia = txtDonGia.getText().trim();
        if (Integer.parseInt(txtSoLuong.getText().trim()) > 0) {
            trangThai = "1";
        } else {
            trangThai = "0";
        }
        kichThuoc = cbKichThuoc.getSelectedItem().toString();
        mauSac = cbMau.getSelectedItem().toString();
        nhaSX = cbNSX.getSelectedItem().toString();
        for (DanhMuc object : dmService.getAllDM()) {
            if (object.getTenDM().equalsIgnoreCase(cbDanhMuc.getSelectedItem().toString())) {
                maDM = object.getMaDM();
                break;
            }
        }
        for (NhaSanXuat object : nsxService.getAllNSX()) {
            if (object.getTenNSX().equalsIgnoreCase(cbNSX.getSelectedItem().toString())) {
                maNSX = object.getMaNSX();
                break;
            }
        }
        return new SanPham(maSP, tenSP, maDM, maNSX, donGia, trangThai, maSPCT, chatLieu, kichThuoc, mauSac, soLuong, nhaSX, xuatXu, moTa);
    }

    public boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    void find() {
        String keyword = txtTimKiem.getText().trim();
        List<SanPham> list = spService.getAll();

        mol.setRowCount(0);
        for (SanPham sanPham : list) {
            if (sanPham.getMaSP().equalsIgnoreCase(keyword) || sanPham.getTenSP().equalsIgnoreCase(keyword)) {
                mol.addRow(sanPham.todataRowSPCT());

            }
        }

    }

    void fillForm(SanPham sp) {
        sp = getDataOnForm();
        txtMaCTSP.setText(sp.getMaSPCT());
        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSP());
        txtSoLuong.setText(sp.getSoLuong());
        txtXuatXu.setText(sp.getXuatXu());
        txtMoTa.setText(sp.getMoTa());
        cbChatLieu.setSelectedItem(sp.getChatLieu());
        cbKichThuoc.setSelectedItem(sp.getKichThuoc());
        cbMau.setSelectedItem(sp.getMauSac());
        cbNSX.setSelectedItem(sp.getNhaSX());
        txtDonGia.setText(sp.getDonGia());
        String tenDM = null;
        for (DanhMuc dm : dmService.getAllDM()) {
            if (dm.getMaDM().equalsIgnoreCase(sp.getMaDM())) {
                tenDM = dm.getTenDM();
                break;
            }
        }
        cbDanhMuc.setSelectedItem(tenDM);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMaCTSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtMaSP = new javax.swing.JTextField();
        cbChatLieu = new javax.swing.JComboBox<>();
        cbKichThuoc = new javax.swing.JComboBox<>();
        cbMau = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        cbNSX = new javax.swing.JComboBox<>();
        txtXuatXu = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThemSP = new javax.swing.JButton();
        btnSuaSP = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cbDanhMuc = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSPCT = new javax.swing.JTable();
        btnThemCL = new javax.swing.JButton();
        btnThemKichThuoc = new javax.swing.JButton();
        btnThemMau = new javax.swing.JButton();
        btnThemNSX = new javax.swing.JButton();
        btnThemDM = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chi Tiết Sản Phẩm");

        jPanel1.setBackground(new java.awt.Color(204, 102, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CHI TIẾT SẢN PHẨM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(871, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(25, 25, 25))
        );

        jLabel3.setText("Mã CTSP");

        jLabel4.setText("Mã Sản Phẩm");

        jLabel5.setText("Tên Sản Phẩm");

        jLabel6.setText("Chất liệu");

        jLabel7.setText("Kích thước");

        jLabel8.setText("Màu");

        txtMaCTSP.setEditable(false);

        txtMaSP.setEditable(false);

        cbChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vải", "Da" }));

        cbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43" }));

        cbMau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vàng sọc đỏ", "Xanh nõn chuối", "Đỏ xanh nhạt" }));

        jLabel9.setText("Số lượng");

        jLabel10.setText("Nhà Sản Xuất");

        jLabel12.setText("Xuất xứ");

        jLabel13.setText("Mô tả");

        cbNSX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Skechers", "Adidas", "Asic", "Converse", "Nike", "New Balance", "Puma", "Reebok" }));

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane1.setViewportView(txtMoTa);

        btnThemSP.setBackground(new java.awt.Color(204, 102, 0));
        btnThemSP.setText("Thêm");
        btnThemSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSPMouseClicked(evt);
            }
        });
        btnThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSPActionPerformed(evt);
            }
        });

        btnSuaSP.setBackground(new java.awt.Color(204, 102, 0));
        btnSuaSP.setText("Sửa");
        btnSuaSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaSPMouseClicked(evt);
            }
        });

        jLabel11.setText("Danh mục");

        cbDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dép da", "Dép cao su", "Dép lười" }));

        jLabel14.setText("Đơn giá");

        jLabel15.setText("VNđ");

        tblSPCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SPCT", "Mã SP", "Tên SP", "Chất liệu", "Kích thước", "Màu", "Số lượng", "NSX", "Xuất xứ", "Đơn giá", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSPCTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblSPCT);

        btnThemCL.setBackground(new java.awt.Color(204, 102, 0));
        btnThemCL.setText("Thêm CL");
        btnThemCL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemCLMouseClicked(evt);
            }
        });
        btnThemCL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCLActionPerformed(evt);
            }
        });

        btnThemKichThuoc.setBackground(new java.awt.Color(204, 102, 0));
        btnThemKichThuoc.setText("Thêm KT");
        btnThemKichThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemKichThuocMouseClicked(evt);
            }
        });

        btnThemMau.setBackground(new java.awt.Color(204, 102, 0));
        btnThemMau.setText("Thêm Mùa");
        btnThemMau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemMauMouseClicked(evt);
            }
        });

        btnThemNSX.setBackground(new java.awt.Color(204, 102, 0));
        btnThemNSX.setText("Thêm NSX");
        btnThemNSX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemNSXMouseClicked(evt);
            }
        });

        btnThemDM.setBackground(new java.awt.Color(204, 102, 0));
        btnThemDM.setText("Thêm DM");
        btnThemDM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemDMMouseClicked(evt);
            }
        });

        txtTimKiem.setText("Tìm kiếm tên hoặc mã sản phẩm ...");
        txtTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTimKiemMouseClicked(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 152, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtMaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbKichThuoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbChatLieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbMau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnThemMau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnThemKichThuoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnThemCL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addComponent(jLabel5)
                            .addComponent(txtTimKiem))
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnSuaSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThemSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbNSX, javax.swing.GroupLayout.Alignment.LEADING, 0, 113, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(btnThemNSX)))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel14))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbDanhMuc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDonGia)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(btnThemDM, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemDM))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(btnThemNSX))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtXuatXu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(btnThemCL))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemKichThuoc)
                            .addComponent(btnThemSP))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemMau)
                            .addComponent(btnSuaSP)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPMouseClicked
        SanPham sp = getDataOnForm();
        fillForm(sp);
        if (checkDataOnForm()) {

                if (spService.addSP(sp) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    mol.setRowCount(0);
                    mol.addRow(sp.todataRowSPCT());
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại");
                }

        }


    }//GEN-LAST:event_btnThemSPMouseClicked

    private void btnSuaSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaSPMouseClicked
        SanPham sp = getDataOnForm2();
        if (checkDataOnForm2()) {
            if (spService.suaSP(sp) > 0) {
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                mol.setRowCount(0);
                mol.addRow(sp.todataRowSPCT());
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!");
            }
        }


    }//GEN-LAST:event_btnSuaSPMouseClicked

    private void btnThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemSPActionPerformed

    private void btnThemCLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemCLMouseClicked
   String cl = JOptionPane.showInputDialog(this, "Nhập tên chất liệu:", "Nhập", HEIGHT);
        if (cl != null) {
            if (isNumber(cl)) {
                JOptionPane.showMessageDialog(this, "Bạn đang nhập số! Mời nhập lại!");
            } else {
                if (clService.AddChatLieu(cl) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    updateCLCombobox();
                    cbChatLieu.setSelectedItem(cl);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }
            }
        }
    }//GEN-LAST:event_btnThemCLMouseClicked

    private void btnThemKichThuocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemKichThuocMouseClicked
  String kt = JOptionPane.showInputDialog(this, "Nhập kích thước:", "Nhập", HEIGHT);
        if (kt != null) {
            if (isNumber(kt)) {
                if (Integer.parseInt(kt) < 30 || Integer.parseInt(kt) > 50) {
                    JOptionPane.showMessageDialog(this, "Nhập lại kích thước từ 30 đến 50!");
                } else {
                    cbKichThuoc.addItem(kt);
                    cbKichThuoc.setSelectedItem(kt);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Không là số! Mời nhập lại!");
            }
        }
    }//GEN-LAST:event_btnThemKichThuocMouseClicked

    private void btnThemMauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMauMouseClicked
    String ms = JOptionPane.showInputDialog(this, "Nhập Màu sắc:", "Nhập", HEIGHT);
        if (ms != null) {
            if (isNumber(ms)) {
                JOptionPane.showMessageDialog(this, "Bạn đang nhập số! Mời nhập lại!");
            } else {
                if (msService.AddMS(ms) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    updateMauSacCombobox();
                    cbMau.setSelectedItem(ms);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }
            }
        }
    }//GEN-LAST:event_btnThemMauMouseClicked

    private void btnThemNSXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemNSXMouseClicked
  String nsx = JOptionPane.showInputDialog(this, "Nhập nhà sản xuất:", "Nhập", HEIGHT);
        if (nsx != null) {
            if (isNumber(nsx)) {
                JOptionPane.showMessageDialog(this, "Bạn đang nhập số! Mời nhập lại!");
            } else {
                if (nsxService.AddNSX(nsx) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    updateNSXCombobox();
                    cbNSX.setSelectedItem(nsx);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }
            }
        }
    }//GEN-LAST:event_btnThemNSXMouseClicked

    private void btnThemDMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemDMMouseClicked
  String dm = JOptionPane.showInputDialog(this, "Nhập nhà sản xuất:", "Nhập", HEIGHT);
        if (dm != null) {
            if (isNumber(dm)) {
                JOptionPane.showMessageDialog(this, "Bạn đang nhập số! Mời nhập lại!");
            } else {
                if (dmService.AddDM(dm) > 0) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    updateDanhMucCombobox();
                    cbDanhMuc.setSelectedItem(dm);
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }
            }
        }
    }//GEN-LAST:event_btnThemDMMouseClicked

    private void tblSPCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPCTMouseClicked

    }//GEN-LAST:event_tblSPCTMouseClicked

    private void txtTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemMouseClicked
        if (txtTimKiem.getText().trim().equals("Tìm kiếm tên hoặc mã sản phẩm ...")) {
            txtTimKiem.setText("");
        }

    }//GEN-LAST:event_txtTimKiemMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        find();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnThemCLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemCLActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamChiTietView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamChiTietView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSuaSP;
    private javax.swing.JButton btnThemCL;
    private javax.swing.JButton btnThemDM;
    private javax.swing.JButton btnThemKichThuoc;
    private javax.swing.JButton btnThemMau;
    private javax.swing.JButton btnThemNSX;
    private javax.swing.JButton btnThemSP;
    private javax.swing.JComboBox<String> cbChatLieu;
    private javax.swing.JComboBox<String> cbDanhMuc;
    private javax.swing.JComboBox<String> cbKichThuoc;
    private javax.swing.JComboBox<String> cbMau;
    private javax.swing.JComboBox<String> cbNSX;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblSPCT;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaCTSP;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtXuatXu;
    // End of variables declaration//GEN-END:variables
}
