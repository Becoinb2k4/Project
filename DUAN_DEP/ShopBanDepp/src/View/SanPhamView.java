/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.ChatLieu;
import Model.DanhMuc;
import Model.HoaDon;
import Model.MauSac;
import Model.SanPham;
import Service.ChatLieuService;
import Service.DanhMucService;
import Service.MauSacSevice;
import Service.SanPhamService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 
 */
public class SanPhamView extends javax.swing.JFrame {

    DefaultTableModel mol = new DefaultTableModel();
    SanPhamService spService = new SanPhamService();
    DanhMucService dmService = new DanhMucService();
    MauSacSevice msService = new MauSacSevice();
    ChatLieuService clService = new ChatLieuService();
    int soTrang;
    int stt = spService.getAll().size();
    boolean check = false;

    public SanPhamView() {
        initComponents();
        fillTableSP(spService.getAllSP());
        updateTrang();
        updateDanhMucCombobox();
        updateCLCombobox();
        updateMauSacCombobox();
        tblSanPham.setAutoCreateRowSorter(true);

    }

    void find() {
        String keyword = txtTimKiem.getText().trim();
        mol = (DefaultTableModel) tblSanPham.getModel();
        mol.setRowCount(0);
        for (SanPham sanPham : spService.getAll()) {
            if (sanPham.getMaSP().equalsIgnoreCase(keyword) || sanPham.getTenSP().equalsIgnoreCase(keyword)) {
                mol.addRow(sanPham.todataRow());

            }
        }

    }

    void updateTrang() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (stt % 5 != 0) {
            for (int i = 1; i < stt / 5 + 2; i++) {
                model.addElement(String.valueOf(i));
            }
        } else {
            for (int i = 1; i < stt / 5 + 1; i++) {
                model.addElement(String.valueOf(i));
            }
        }

        cbTrang.setModel(model);
    }

    void fillTableSP(List<SanPham> list) {
        mol = (DefaultTableModel) tblSanPham.getModel();
        mol.setRowCount(0);
        for (int i = 0; i < 5; i++) {
            mol.addRow(list.get(i).todataRow());
        }

    }

    private void updateCLCombobox() {
        List<ChatLieu> clList = clService.getAllChatLieu();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (ChatLieu cl : clList) {
            model.addElement(cl.getTenCL());
        }
        cbChatLieu.setModel(model);
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

    public void trangThaiRD() {
        List<SanPham> list = new ArrayList<>();
        if (rdCon.isSelected()) {
            for (SanPham sanPham : spService.getAll()) {
                if (sanPham.getTrangThai().equals("1")) {
                    list.add(sanPham);
                }
            }
        }
        if (rdHet.isSelected()) {
            for (SanPham sanPham : spService.getAll()) {
                if (sanPham.getTrangThai().equals("0")) {
                    list.add(sanPham);
                }
            }
        }
        fillTableSP(list);

    }

    public String chonDuongDan() {
        JFileChooser fileChooser = new JFileChooser();
        String filePath = null;
        // Thiết lập chỉ hiển thị hộp thoại lưu file
        fileChooser.setDialogTitle("Chọn đường dẫn để lưu file");

        // Hiển thị hộp thoại chọn đường dẫn lưu file
        int userSelection = fileChooser.showSaveDialog(null);

        // Kiểm tra nếu người dùng đã chọn một đường dẫn
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn mà người dùng đã chọn
            File selectedFile = fileChooser.getSelectedFile();

            // Kiểm tra xem đường dẫn đã được chọn chưa
            if (selectedFile != null) {
                filePath = selectedFile.getAbsolutePath();
            }
        }

        return filePath;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupTimMa = new javax.swing.ButtonGroup();
        gruopTrangThai = new javax.swing.ButtonGroup();
        buttonGroupThuocTinhSanPham = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        BackSP = new javax.swing.JLabel();
        rdHet = new javax.swing.JRadioButton();
        rdCon = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        btnChiTiet = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        cbTrang = new javax.swing.JComboBox<>();
        txtTimKiem = new javax.swing.JTextField();
        cbMau = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cbChatLieu = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        cbKichThuoc = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbDanhMuc = new javax.swing.JComboBox<>();
        btnExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sản phẩm");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mã danh mục", "Mã nhà sản xuất", "Đơn giá", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        jPanel1.setBackground(new java.awt.Color(204, 102, 0));

        BackSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BackSP.setForeground(new java.awt.Color(255, 255, 255));
        BackSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BackSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/back.png"))); // NOI18N
        BackSP.setText("BACK");
        BackSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackSPMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackSPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BackSPMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(BackSP)
                .addContainerGap(1014, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(BackSP)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        gruopTrangThai.add(rdHet);
        rdHet.setText("Hết hàng");
        rdHet.setBorderPainted(true);
        rdHet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdHetMouseClicked(evt);
            }
        });

        gruopTrangThai.add(rdCon);
        rdCon.setText("Còn hàng");
        rdCon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdConMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setText("Trạng thái");

        btnChiTiet.setBackground(new java.awt.Color(204, 102, 0));
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi Tiết Sản phẩm");
        btnChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChiTietMouseClicked(evt);
            }
        });
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });

        jLabel16.setText("Trang số :");

        btnBack.setText("<<|");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
        });

        btnNext.setText("|>>");
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNextMouseClicked(evt);
            }
        });

        cbTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        cbTrang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTrangItemStateChanged(evt);
            }
        });
        cbTrang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbTrangMouseClicked(evt);
            }
        });
        cbTrang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTrangKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbTrangKeyReleased(evt);
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

        cbMau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbMau.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbMauItemStateChanged(evt);
            }
        });

        jLabel21.setText("Màu sắc");

        jLabel23.setText("Chất liệu");

        cbChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbChatLieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbChatLieuItemStateChanged(evt);
            }
        });

        jLabel24.setText("Kích cỡ");

        cbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        cbKichThuoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbKichThuocItemStateChanged(evt);
            }
        });

        jLabel3.setText("Danh mục");

        cbDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item4" }));
        cbDanhMuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDanhMucItemStateChanged(evt);
            }
        });
        cbDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDanhMucActionPerformed(evt);
            }
        });

        btnExcel.setBackground(new java.awt.Color(204, 102, 0));
        btnExcel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel.setText("Xuất file Excel");
        btnExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExcelMouseClicked(evt);
            }
        });
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel23)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(32, 32, 32)
                                        .addComponent(rdCon)
                                        .addGap(53, 53, 53)
                                        .addComponent(rdHet))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(47, 47, 47)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(cbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                                        .addComponent(btnChiTiet)
                                        .addGap(34, 34, 34))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnExcel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(rdCon)
                    .addComponent(rdHet)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChiTiet)
                    .addComponent(cbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(btnBack)
                    .addComponent(btnNext)
                    .addComponent(cbTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackSPMouseClicked
        HomForm1 form1 = new HomForm1();
        form1.setVisible(true);
        form1.pack();
        form1.setLocationRelativeTo(null);
        form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_BackSPMouseClicked

    private void BackSPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackSPMouseEntered
        BackSP.setLayout(new BorderLayout(10, 10));
        BackSP.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_BackSPMouseEntered

    private void BackSPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackSPMouseExited
        BackSP.setLayout(null);
        BackSP.setBorder(null);
    }//GEN-LAST:event_BackSPMouseExited

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked

    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void rdConMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdConMouseClicked
        trangThaiRD();
    }//GEN-LAST:event_rdConMouseClicked

    private void rdHetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdHetMouseClicked
        trangThaiRD();
    }//GEN-LAST:event_rdHetMouseClicked

    private void btnChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChiTietMouseClicked
        int index = tblSanPham.getSelectedRow();
        if (index < 0) {
            SanPhamChiTietView tt = new SanPhamChiTietView();
            tt.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    fillTableSP(spService.getAllSP());
                    cbTrang.setSelectedItem("1");
                }
            });
            tt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tt.setSize(1200, 650);
            tt.setLocationRelativeTo(this);
            tt.setVisible(true);
        } else {
            String maSP = tblSanPham.getValueAt(index, 0).toString();
            SanPhamChiTietView tt = new SanPhamChiTietView();
            tt.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    fillTableSP(spService.getAllSP());
                    cbTrang.setSelectedItem("1");
                }
            });
            tt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tt.setSize(1200, 650);
            tt.fillFormCTSP(maSP);
            tt.setLocationRelativeTo(this);
            tt.setVisible(true);
        }

    }//GEN-LAST:event_btnChiTietMouseClicked

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChiTietActionPerformed

    private void btnNextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseClicked
        soTrang = Integer.parseInt(cbTrang.getSelectedItem().toString());
        if (soTrang == stt / 5 + 1) {
            soTrang = 1;
        } else {
            soTrang++;
        }
        cbTrang.setSelectedItem(String.valueOf(soTrang));
    }//GEN-LAST:event_btnNextMouseClicked

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        soTrang = Integer.parseInt(cbTrang.getSelectedItem().toString());
        if (soTrang == 1) {
            soTrang = stt / 5 + 1;
        } else {
            soTrang--;
        }
        cbTrang.setSelectedItem(String.valueOf(soTrang));
    }//GEN-LAST:event_btnBackMouseClicked

    private void cbTrangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTrangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTrangKeyReleased

    private void cbTrangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTrangKeyPressed

    }//GEN-LAST:event_cbTrangKeyPressed

    private void cbTrangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbTrangMouseClicked

    }//GEN-LAST:event_cbTrangMouseClicked

    private void cbTrangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTrangItemStateChanged
        // vì event này chạy 2 lần nên sẽ tạo ra biến check
        if (!check) {
            soTrang = Integer.parseInt(cbTrang.getSelectedItem().toString());
            List<SanPham> list = spService.getAll();
            if (soTrang == stt / 5 + 1) {
                mol.setRowCount(0);
                for (int i = (soTrang - 1) * 5; i < stt; i++) {
                    mol.addRow(list.get(i).todataRow());
                }
            } else {
                mol.setRowCount(0);
                for (int i = (soTrang - 1) * 5; i < soTrang * 5; i++) {
                    mol.addRow(list.get(i).todataRow());
                }
            }
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbTrangItemStateChanged

    private void txtTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemMouseClicked
        txtTimKiem.setText("");
    }//GEN-LAST:event_txtTimKiemMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        find();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cbMauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMauItemStateChanged
        mol = (DefaultTableModel) tblSanPham.getModel();
        if (!check) {
            mol.setRowCount(0);
            String ms = cbMau.getSelectedItem().toString();
            String mauSac = null;
            for (MauSac object : msService.getAllMS()) {
                if (object.getTenMS().equals(ms)) {
                    mauSac = object.getTenMS();
                    break;
                }
            }
            List<SanPham> list = new ArrayList<>();
            for (SanPham object : spService.getAll()) {
                if (object.getMauSac().equalsIgnoreCase(mauSac)) {

                    list.add(object);
                }
            }
            if (!list.isEmpty()) {
                fillTableSP(list);
            }

            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbMauItemStateChanged

    private void cbChatLieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbChatLieuItemStateChanged
        mol = (DefaultTableModel) tblSanPham.getModel();
        if (!check) {
            mol.setRowCount(0);
            String cl = cbChatLieu.getSelectedItem().toString();
            String chatLieu = null;
            for (ChatLieu object : clService.getAllChatLieu()) {
                if (object.getTenCL().equals(cl)) {
                    chatLieu = object.getTenCL();
                    break;
                }
            }
            List<SanPham> list = new ArrayList<>();
            for (SanPham object : spService.getAll()) {
                if (object.getChatLieu().equalsIgnoreCase(chatLieu)) {
                    list.add(object);
                }
            }
            if (!list.isEmpty()) {
                fillTableSP(list);
            }
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbChatLieuItemStateChanged

    private void cbKichThuocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbKichThuocItemStateChanged
        mol = (DefaultTableModel) tblSanPham.getModel();
        if (!check) {
            mol.setRowCount(0);
            String kichThuoc = cbKichThuoc.getSelectedItem().toString();
            List<SanPham> list = new ArrayList<>();
            for (SanPham object : spService.getAll()) {

                if (object.getKichThuoc().equalsIgnoreCase(kichThuoc)) {
                    list.add(object);
                }
            }
            if (!list.isEmpty()) {
                fillTableSP(list);
            }
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbKichThuocItemStateChanged

    private void cbDanhMucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDanhMucItemStateChanged
        mol = (DefaultTableModel) tblSanPham.getModel();
        if (!check) {
            mol.setRowCount(0);
            String dm = cbDanhMuc.getSelectedItem().toString();
            String maDM = null;
            for (DanhMuc object : dmService.getAllDM()) {
                if (object.getTenDM().equals(dm)) {
                    maDM = object.getMaDM();
                    break;
                }
            }
            List<SanPham> list = new ArrayList<>();
            for (SanPham object : spService.getAll()) {

                if (object.getMaDM().equalsIgnoreCase(maDM)) {

                    list.add(object);
                }
            }
            if (!list.isEmpty()) {
                fillTableSP(list);
            }
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbDanhMucItemStateChanged

    private void btnExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseClicked

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sách sản phẩm");
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 6000);
            sheet.setColumnWidth(3, 3000);
            sheet.setColumnWidth(4, 3000);
            sheet.setColumnWidth(5, 6000);
            sheet.setColumnWidth(6, 3000);
            sheet.setColumnWidth(7, 6000);
            sheet.setColumnWidth(8, 6000);
            sheet.setColumnWidth(9, 6000);
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Danh sách sản phẩm");

            row = sheet.createRow(1);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Mã SPCT");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã sản phẩm");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Tên sản phẩm");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Chất liệu");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Kích thước");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Màu sắc");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Số lượng");
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Nhà sản xuất");
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Xuất xứ");
            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Đơn giá");
            int i = 0;
            for (SanPham cell1 : spService.getAll()) {
                row = sheet.createRow(2 + i);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(cell1.getMaSPCT());
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(cell1.getMaSP());
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(cell1.getTenSP());
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(cell1.getChatLieu());
                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(cell1.getKichThuoc());
                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(cell1.getMauSac());
                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(cell1.getSoLuong());
                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(cell1.getNhaSX());
                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(cell1.getXuatXu());
                cell = row.createCell(9, CellType.STRING);
                cell.setCellValue(cell1.getDonGia2());
                i++;
            }

            File file = new File(chonDuongDan() + ".xlsx");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                workbook.write(fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
            JOptionPane.showMessageDialog(this, "Đã xuất file");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }//GEN-LAST:event_btnExcelMouseClicked

    private void cbDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDanhMucActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcelActionPerformed

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
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SanPhamView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SanPhamView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackSP;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnNext;
    private javax.swing.ButtonGroup buttonGroupThuocTinhSanPham;
    private javax.swing.JComboBox<String> cbChatLieu;
    private javax.swing.JComboBox<String> cbDanhMuc;
    private javax.swing.JComboBox<String> cbKichThuoc;
    private javax.swing.JComboBox<String> cbMau;
    private javax.swing.JComboBox<String> cbTrang;
    private javax.swing.ButtonGroup groupTimMa;
    private javax.swing.ButtonGroup gruopTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdCon;
    private javax.swing.JRadioButton rdHet;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
