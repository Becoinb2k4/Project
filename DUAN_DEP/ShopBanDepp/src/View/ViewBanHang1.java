/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

//import Model.SanPham;
//import Model.hoaDon;
//import Model.khachHang;
import Model.ChatLieu;
import Model.DanhMuc;
import Model.HoaDon;
import Model.KhuyenMai;
import Model.MauSac;
import Model.SanPham;
import Model.ThongKe;
import Model.khachHang;
import Service.ChatLieuService;
import Service.DanhMucService;
import Service.HoaDonService;
import Service.KhuyenMaiService;
import Service.MauSacSevice;
import Service.SanPhamService;
import Service.ThongKeService;
import Service.khachHangService;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author admin
 */
public class ViewBanHang1 extends javax.swing.JFrame implements Runnable, ThreadFactory {

    SanPhamService spService = new SanPhamService();
//    int soTrang;
//    int stt = spService.getAll().size();

    boolean check = false;
    DefaultTableModel mol = new DefaultTableModel();
    HoaDonService hdService = new HoaDonService();
    DanhMucService dmService = new DanhMucService();
    khachHangService khService = new khachHangService();
    List<HoaDonCho> listHoaDonCho = new ArrayList<>();
    MauSacSevice msService = new MauSacSevice();
    ChatLieuService clService = new ChatLieuService();
    List<SanPham> listSP = spService.getAll();
    KhuyenMaiService kmservice = new KhuyenMaiService();
    ThongKeService thongkeService = new ThongKeService();

    private Executor executor = Executors.newSingleThreadExecutor(this);

    public ViewBanHang1() {
        initComponents();
        setLocationRelativeTo(null);
        defaultTable1();
        defaultTable2();
        fillTableSP(listSP);

        updateDanhMucCombobox();
        updateCLCombobox();
        updateMauSacCombobox();
        tblBangSP.setAutoCreateRowSorter(true);
        lblCheckTienKH.setText("");
        lblGiamGia.setText("");
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

    void fillTableGioHang(List<SanPham> list) {
        mol = (DefaultTableModel) tblGioHang.getModel();
        mol.setRowCount(0);
        for (SanPham sanPham : list) {
            mol.addRow(sanPham.toDataRowGioHang());
        }
    }

   
    void fillTableSP(List<SanPham> list) {
        mol = (DefaultTableModel) tblBangSP.getModel();
        mol.setRowCount(0);
        for (SanPham sanPham : list) {
            if (!sanPham.getSoLuong().equals("0")) {
                mol.addRow(sanPham.todataRowBanHang());
            }
        }

    }


    public void defaultTable1() {
        mol = (DefaultTableModel) tblHoadoncho.getModel();
        mol.setRowCount(0);
    }

    public void defaultTable2() {
        mol = (DefaultTableModel) tblGioHang.getModel();
        mol.setRowCount(0);
    }

    public boolean checkHDCho() {
        mol = (DefaultTableModel) tblHoadoncho.getModel();

        if (!txtTenKH.getText().trim().equalsIgnoreCase("Khách mua lẻ")) {
            for (int i = 0; i < tblHoadoncho.getRowCount(); i++) {
                if (tblHoadoncho.getValueAt(i, 2).toString().equalsIgnoreCase(txtTenKH.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "đã có hóa đơn chờ của khách này");
                    return false;
                }
            }
        }

        return true;
    }

    public void taoHoaDonCho() {
        String maHD;
        LocalDate today = LocalDate.now();
        String tenKH;
        mol = (DefaultTableModel) tblHoadoncho.getModel();
        if (tblHoadoncho.getModel().getRowCount() == 0) {
            if (txtTenKH.getText().trim().isEmpty()) {
                tenKH = "Khách mua lẻ";
                String ma;
                if (hdService.getAllHDGoc().size() == 0) {
                    ma = "HD000";
                } else {
                    ma = hdService.getAllHDGoc().get(hdService.getAllHDGoc().size() - 1).getMaHD();
                }

                String t = ma.substring(2, ma.length());
                int so = Integer.parseInt(t) + 1;
                if (String.valueOf(so).length() == 1) {
                    maHD = ma.substring(0, 4) + String.valueOf(so);
                } else if (String.valueOf(so).length() == 2) {
                    maHD = ma.substring(0, 3) + String.valueOf(so);
                } else {
                    maHD = ma.substring(0, 2) + String.valueOf(so);
                }
                mol.addRow(new Object[]{
                    maHD, today, tenKH
                });
            } else {
                tenKH = txtTenKH.getText();
                String ma = hdService.getAllHDGoc().get(hdService.getAllHDGoc().size() - 1).getMaHD();
                String t = ma.substring(2, ma.length());
                int so = Integer.parseInt(t) + 1;
                if (String.valueOf(so).length() == 1) {
                    maHD = ma.substring(0, 4) + String.valueOf(so);
                } else if (String.valueOf(so).length() == 2) {
                    maHD = ma.substring(0, 3) + String.valueOf(so);
                } else {
                    maHD = ma.substring(0, 2) + String.valueOf(so);
                }
                mol.addRow(new Object[]{
                    maHD, today, tenKH
                });
            }
        } else {
            if (checkHDCho()) {
                int i = tblHoadoncho.getModel().getRowCount();
                tenKH = txtTenKH.getText().trim();
                if (i < 1) {
                    String ma = hdService.getAllHDGoc().get(hdService.getAllHDGoc().size() - 1).getMaHD();
                    String t = ma.substring(2, ma.length());
                    int so = Integer.parseInt(t) + 1;
                    if (String.valueOf(so).length() == 1) {
                        maHD = ma.substring(0, 4) + String.valueOf(so);
                    } else if (String.valueOf(so).length() == 2) {
                        maHD = ma.substring(0, 3) + String.valueOf(so);
                    } else {
                        maHD = ma.substring(0, 2) + String.valueOf(so);
                    }
                    mol.addRow(new Object[]{
                        maHD, today, tenKH
                    });

                } else {
                    String ma = tblHoadoncho.getValueAt(i - 1, 0).toString();
                    String t = ma.substring(2, ma.length());
                    int so = Integer.parseInt(t) + 1;
                    if (String.valueOf(so).length() == 1) {
                        maHD = ma.substring(0, 4) + String.valueOf(so);
                    } else if (String.valueOf(so).length() == 2) {
                        maHD = ma.substring(0, 3) + String.valueOf(so);
                    } else {
                        maHD = ma.substring(0, 2) + String.valueOf(so);
                    }
                    mol.addRow(new Object[]{
                        maHD, today, tenKH
                    });

                }
            }
        }

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
        mol = (DefaultTableModel) tblBangSP.getModel();
        mol.setRowCount(0);
        for (SanPham sanPham : listSP) {
            if (sanPham.getMaSP().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))
                    || sanPham.getTenSP().toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT))) {
                mol.addRow(sanPham.todataRowBanHang());

            }
           
        }

    }

    public void tinhTongTienDonHang(String maHD) {
        int tongTien = 0; 
        for (HoaDonCho hd : listHoaDonCho) {
            if (hd.getMaHD().equalsIgnoreCase(maHD)) {
                tongTien = tongTien + Integer.parseInt(hd.getTongTien());
            }
        }
        lblTongTien.setText(String.valueOf(tongTien));
        String txgg = txtGiamGia.getText().trim();
        if (txgg.equalsIgnoreCase("Nhập mã ...")
                || txgg.equalsIgnoreCase("")) {
            lblThanhToan.setText(String.valueOf(tongTien));
        } else {
            int giamgia = Integer.parseInt(lblGiamGia.getText().trim());
            lblThanhToan.setText(String.valueOf(tongTien - giamgia));
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        BackBH = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelDonHang = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        btnChonKH = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txtFindSDT = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtxtxArea = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        btnHuyHD = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        cbLoaiThanhToan = new javax.swing.JComboBox<>();
        txtTienKH = new javax.swing.JTextField();
        lblMaHD = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        lblThanhToan = new javax.swing.JLabel();
        lblTienThua = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblCheckTienKH = new javax.swing.JLabel();
        txtGiamGia = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        lblGiamGia = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoadoncho = new javax.swing.JTable();
        panelWebcam = new javax.swing.JPanel();
        panelCam = new javax.swing.JPanel();
        btnTaoMaHD = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblBangSP = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnThemSPvaoGio = new javax.swing.JButton();
        cbDanhMuc = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbKichThuoc = new javax.swing.JComboBox<>();
        cbChatLieu = new javax.swing.JComboBox<>();
        cbMau = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        btnTim = new javax.swing.JButton();
        btnXoaLoc = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1188, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 102, 0));

        BackBH.setBackground(new java.awt.Color(183, 151, 104));
        BackBH.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        BackBH.setForeground(new java.awt.Color(255, 255, 255));
        BackBH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/back.png"))); // NOI18N
        BackBH.setText("BACK");
        BackBH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackBHMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackBHMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BackBHMouseExited(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("BÁN HÀNG");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/895580.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(BackBH, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1337, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(BackBH))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelDonHang.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn hàng"));

        jLabel3.setText("Mã khách hàng :");

        jLabel4.setText("Tên khách hàng :");

        txtMaKH.setEditable(false);
        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });

        txtTenKH.setEditable(false);

        btnChonKH.setBackground(new java.awt.Color(204, 102, 0));
        btnChonKH.setForeground(new java.awt.Color(255, 255, 255));
        btnChonKH.setText("Chọn");
        btnChonKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChonKHMouseClicked(evt);
            }
        });
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 102, 0));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Clear");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        jLabel20.setText("Tìm theo SDT");

        txtFindSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFindSDTActionPerformed(evt);
            }
        });
        txtFindSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFindSDTKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(btnChonKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(52, 52, 52))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel20))
                .addGap(46, 46, 46)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenKH)
                    .addComponent(txtFindSDT))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFindSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChonKH)
                    .addComponent(jButton4)))
        );

        jLabel5.setText("Mã hóa đơn :");

        jLabel6.setText("Tổng tiền");

        jLabel7.setText("Giảm giá");

        jLabel8.setText("Thanh toán");

        jLabel9.setText("Tiền khách đưa");

        jLabel10.setText("Tiền thừa trả khách");

        jLabel11.setText("Hình thức thanh toán");

        jLabel12.setText("Hình thức giao hàng");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        txtxtxArea.setViewportView(txtGhiChu);

        jLabel13.setText("Ghi chú:");

        btnHuyHD.setBackground(new java.awt.Color(204, 102, 0));
        btnHuyHD.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnHuyHD.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/huy hoa don.png"))); // NOI18N
        btnHuyHD.setText("Hủy hóa đơn");
        btnHuyHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyHDMouseClicked(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(204, 102, 0));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/6445237.png"))); // NOI18N
        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });

        cbLoaiThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));
        cbLoaiThanhToan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbLoaiThanhToanItemStateChanged(evt);
            }
        });

        txtTienKH.setText("Nhập ...");
        txtTienKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTienKHMouseClicked(evt);
            }
        });
        txtTienKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKHKeyReleased(evt);
            }
        });

        lblTongTien.setText("0");

        lblThanhToan.setText("0");

        lblTienThua.setText("0");

        jLabel19.setText("Bán tại cửa hàng");

        jLabel15.setText("VNĐ");

        jLabel17.setText("VNĐ");

        jLabel18.setText("VNĐ");

        jLabel22.setText("VNĐ");

        lblCheckTienKH.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblCheckTienKH.setForeground(new java.awt.Color(255, 0, 0));
        lblCheckTienKH.setText("jLabel20");

        txtGiamGia.setText("Nhập mã ...");
        txtGiamGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtGiamGiaMouseClicked(evt);
            }
        });
        txtGiamGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiamGiaKeyReleased(evt);
            }
        });

        jLabel25.setText("VNĐ");

        lblGiamGia.setText("jLabel16");

        javax.swing.GroupLayout panelDonHangLayout = new javax.swing.GroupLayout(panelDonHang);
        panelDonHang.setLayout(panelDonHangLayout);
        panelDonHangLayout.setHorizontalGroup(
            panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDonHangLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDonHangLayout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(panelDonHangLayout.createSequentialGroup()
                        .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHuyHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelDonHangLayout.createSequentialGroup()
                                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelDonHangLayout.createSequentialGroup()
                                        .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel12))
                                        .addGap(59, 59, 59)
                                        .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(cbLoaiThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDonHangLayout.createSequentialGroup()
                                        .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel8))
                                        .addGap(92, 92, 92)
                                        .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCheckTienKH)
                                            .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(panelDonHangLayout.createSequentialGroup()
                                                    .addGap(6, 6, 6)
                                                    .addComponent(lblTienThua)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel22))
                                                .addGroup(panelDonHangLayout.createSequentialGroup()
                                                    .addComponent(txtTienKH, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel18))
                                                .addComponent(txtGiamGia, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDonHangLayout.createSequentialGroup()
                                                    .addComponent(lblGiamGia)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel25))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDonHangLayout.createSequentialGroup()
                                                    .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblMaHD)
                                                        .addComponent(lblThanhToan)
                                                        .addComponent(lblTongTien))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)))))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDonHangLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txtxtxArea, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelDonHangLayout.setVerticalGroup(
            panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDonHangLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblMaHD))
                .addGap(18, 18, 18)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGiamGia)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblThanhToan)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtTienKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(8, 8, 8)
                .addComponent(lblCheckTienKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblTienThua)
                    .addComponent(jLabel22))
                .addGap(18, 18, 18)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cbLoaiThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDonHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDonHangLayout.createSequentialGroup()
                        .addComponent(txtxtxArea, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDonHangLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel13)
                        .addGap(86, 86, 86)))
                .addComponent(btnHuyHD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn chờ"));

        tblHoadoncho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Ngày tạo", "Khách hàng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoadoncho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoadonchoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoadoncho);

        panelWebcam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelWebcam.add(panelCam, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 70, 210));

        btnTaoMaHD.setBackground(new java.awt.Color(204, 102, 0));
        btnTaoMaHD.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoMaHD.setText("Tạo");
        btnTaoMaHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTaoMaHDMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTaoMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 989, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addComponent(panelWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTaoMaHD)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ hàng"));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Đơn giá", "Số lượng", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblGioHang);

        btnXoa.setBackground(new java.awt.Color(204, 102, 0));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa sản phẩm");
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaMouseClicked(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(204, 102, 0));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa số lượng");
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSuaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnXoa)
                .addGap(61, 61, 61)
                .addComponent(btnSua)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
        });

        tblBangSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Đơn giá", "Màu sắc", "Chất liệu", "Kích cỡ", "Số lượng tồn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangSPMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblBangSP);

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

        btnThemSPvaoGio.setBackground(new java.awt.Color(204, 102, 0));
        btnThemSPvaoGio.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSPvaoGio.setText("Thêm vào giỏ");
        btnThemSPvaoGio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSPvaoGioMouseClicked(evt);
            }
        });

        cbDanhMuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbDanhMuc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbDanhMucItemStateChanged(evt);
            }
        });

        jLabel1.setText("Danh mục");

        cbKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        cbKichThuoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbKichThuocItemStateChanged(evt);
            }
        });

        cbChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbChatLieu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbChatLieuItemStateChanged(evt);
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

        jLabel24.setText("Kích cỡ");

        btnTim.setBackground(new java.awt.Color(204, 102, 0));
        btnTim.setForeground(new java.awt.Color(255, 255, 255));
        btnTim.setText("Tìm");
        btnTim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimMouseClicked(evt);
            }
        });
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        btnXoaLoc.setBackground(new java.awt.Color(204, 102, 0));
        btnXoaLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaLoc.setText("Reset");
        btnXoaLoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXoaLocMouseClicked(evt);
            }
        });
        btnXoaLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnThemSPvaoGio, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(cbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(cbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(cbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTim)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoaLoc)))
                        .addGap(211, 244, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbKichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel1)
                    .addComponent(btnThemSPvaoGio)
                    .addComponent(btnTim)
                    .addComponent(btnXoaLoc))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(984, 984, 984)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDonHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDonHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void BackBHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackBHMouseClicked
        HomForm1 home = new HomForm1();
        home.setVisible(true);
        home.pack();
        home.setLocationRelativeTo(null);
        home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.dispose();
    }//GEN-LAST:event_BackBHMouseClicked

    private void btnChonKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChonKHMouseClicked
        KhachHang1 kh = new KhachHang1();
        kh.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                khachHang khach = kh.getKhachHang();
                if (khach == null) {
                } else {
                    txtMaKH.setText(khach.getMaKH());
                    txtTenKH.setText(khach.getTenKh());
                }
            }
        });
        kh.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        kh.setSize(650, 750);
        kh.setLocationRelativeTo(this);
        kh.setVisible(true);

    }//GEN-LAST:event_btnChonKHMouseClicked

    private void btnTaoMaHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaoMaHDMouseClicked

        if (txtTenKH.getText().trim().isEmpty()) {
            txtTenKH.setText("Khách mua lẻ");
        }
        taoHoaDonCho();

    }//GEN-LAST:event_btnTaoMaHDMouseClicked

    private void BackBHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackBHMouseEntered
        BackBH.setLayout(new BorderLayout(10, 10));
        BackBH.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_BackBHMouseEntered

    private void BackBHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackBHMouseExited
        BackBH.setLayout(null);
        BackBH.setBorder(null);
    }//GEN-LAST:event_BackBHMouseExited

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtFindSDT.setText("");
    }//GEN-LAST:event_jButton4MouseClicked

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void btnThemSPvaoGioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSPvaoGioMouseClicked
        int indexBangSP = tblBangSP.getSelectedRow();
        if (indexBangSP == -1) {
            JOptionPane.showMessageDialog(this, "chọn một sản phẩm thêm");
            return;
        }
        String maSPlist = tblBangSP.getValueAt(indexBangSP, 0).toString();
        int indexs = 0;
        for (int i = 0; i < listSP.size(); i++) {
            if (listSP.get(i).getMaSP().equals(maSPlist)) {
                indexs = i;
                break;
            }
        }
        int indexHD = tblHoadoncho.getSelectedRow();

        if (indexHD == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn chờ để thêm sản phẩm");
            return;
        }
        String maSP = tblBangSP.getValueAt(indexBangSP, 0).toString();
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            if (tblGioHang.getValueAt(i, 0).toString().equalsIgnoreCase(maSP)) {
                JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại trong giỏ hàng chỉ cần sửa lại số lượng");
                return;
            }
        }
        String input = JOptionPane.showInputDialog(this, "Mời nhập số lượng");
        if (input == null || input.equals("")) {
            JOptionPane.showMessageDialog(this, "Số lượng nhập không được trống");
            return;
        }
        if (!isNumber(input)) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
            
            return;
        }

        int soLuongMua = Integer.parseInt(input.toString());
        int soLuongTon = Integer.parseInt(listSP.get(indexs).getSoLuong());
        if (soLuongMua > soLuongTon) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn kho không đủ");
            return;
        } else if (soLuongMua < 1) {
            JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
            return;
        }
        String maHD = tblHoadoncho.getValueAt(indexHD, 0).toString();
        String tenSP = tblBangSP.getValueAt(indexBangSP, 1).toString();
        String donGia = tblBangSP.getValueAt(indexBangSP, 2).toString();
        SanPham sanPham = new SanPham(maSP, tenSP, donGia, input);
        int tongGiaTien = sanPham.getTongGiaTien();
        mol = (DefaultTableModel) tblGioHang.getModel();

        mol.addRow(new Object[]{
            maSP, tenSP, donGia, input, tongGiaTien
        });
        // trừ số lượng đã nhập trên bảng sản phẩm
        String tongTien = String.valueOf(tongGiaTien);
        listSP.get(indexs).setSoLuong(String.valueOf(soLuongTon - soLuongMua));
        HoaDonCho hdCho = new HoaDonCho(txtMaKH.getText().trim(), txtTenKH.getText(), maHD, maSP, tenSP, donGia, input, tongTien);
        listHoaDonCho.add(hdCho);
        tinhTongTienDonHang(maHD);
        fillTableSP(listSP);
    }//GEN-LAST:event_btnThemSPvaoGioMouseClicked

    private void tblBangSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangSPMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBangSPMouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel6MouseClicked

    private void btnSuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseClicked
        int indexGH = tblGioHang.getSelectedRow();
        int indexHD = tblHoadoncho.getSelectedRow();
        if (indexGH == -1) {
            JOptionPane.showMessageDialog(this, "Mời chọn cần sửa trong giỏ");
            return;
        }
        String soLuongGH = tblGioHang.getValueAt(indexGH, 3).toString();
        String input = JOptionPane.showInputDialog(this, "Mời nhập số lượng");
        if (input == null || input.equals("")) {
            JOptionPane.showMessageDialog(this, "Số lượng không được trống");
            return;
        }
        if (!isNumber(input)) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ");
            return;
        }
        if (input.equals("0")) {
            JOptionPane.showMessageDialog(this, "Số lượng nhập phải lớn hơn 0");
            return;
        }
        String maHD = tblHoadoncho.getValueAt(indexHD, 0).toString();
        String maSP = tblGioHang.getValueAt(indexGH, 0).toString();
        // tìm số lượng tồn của sản phẩm
        String soLuongTon = null;
        for (SanPham sp : listSP) {
            if (sp.getMaSP().equalsIgnoreCase(maSP)) {
                soLuongTon = sp.getSoLuong();
                break;
            }
        }
        if (Integer.parseInt(soLuongTon) + Integer.parseInt(soLuongGH) < Integer.parseInt(input)) {
            JOptionPane.showMessageDialog(this, "Số lượng tồn kho không đủ!");
            return;
        }
        for (SanPham sp : listSP) {
            if (sp.getMaSP().equals(maSP)) {
                sp.setSoLuong(String.valueOf(Integer.parseInt(soLuongTon) + Integer.parseInt(soLuongGH) - Integer.parseInt(input)));
            }
        }
        for (HoaDonCho hd : listHoaDonCho) {
            if (hd.getMaHD().equalsIgnoreCase(maHD)
                    && hd.getMaSP().equals(maSP)) {
                hd.setSoLuong(input);
            }
        }
        fillTableSP(listSP);
        mol = (DefaultTableModel) tblGioHang.getModel();
        mol.setRowCount(0);
        for (HoaDonCho hd : listHoaDonCho) {
            if (hd.getMaHD().equalsIgnoreCase(maHD)) {
                mol.addRow(hd.todataRow());
            }
        }
        tinhTongTienDonHang(maHD);
    }//GEN-LAST:event_btnSuaMouseClicked

    private void txtTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTimKiemMouseClicked
        txtTimKiem.setText("");
        List<HoaDonCho> list = new ArrayList<>();
    }//GEN-LAST:event_txtTimKiemMouseClicked

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        find();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cbDanhMucItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbDanhMucItemStateChanged
        mol = (DefaultTableModel) tblBangSP.getModel();
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
            fillTableSP(list);
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbDanhMucItemStateChanged

    private void tblHoadonchoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoadonchoMouseClicked
        int index = tblHoadoncho.getSelectedRow();
        txtTenKH.setText(tblHoadoncho.getValueAt(index, 2).toString());
        if (txtTenKH.getText().equalsIgnoreCase("Khách mua lẻ")) {
            txtMaKH.setText("");
        } else {
            for (khachHang object : khService.getAllkh()) {
                if (object.getTenKh().equalsIgnoreCase(txtTenKH.getText().trim())) {
                    txtMaKH.setText(object.getMaKH());
                    break;
                }
            }
        }
        String maHD = tblHoadoncho.getValueAt(index, 0).toString();
        lblMaHD.setText(maHD);
        mol = (DefaultTableModel) tblGioHang.getModel();
        mol.setRowCount(0);
        for (HoaDonCho hoaDonCho : listHoaDonCho) {
            if (hoaDonCho.getMaHD().equalsIgnoreCase(maHD)) {
                mol.addRow(hoaDonCho.todataRow());
            }
        }

        tinhTongTienDonHang(maHD);
    }//GEN-LAST:event_tblHoadonchoMouseClicked

    private void btnXoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseClicked
        int indexGH = tblGioHang.getSelectedRow();
        int indexHD = tblHoadoncho.getSelectedRow();
        if (indexGH == -1) {
            JOptionPane.showMessageDialog(this, "Mời chọn sản phẩm cần xóa trong giỏ!");
            return;
        }
        int choice = JOptionPane.showOptionDialog(
                null,
                "Bạn có muốn xóa không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Có", "Không"},
                "Có"
        );
        if (choice == JOptionPane.YES_OPTION) {
            String maHD = tblHoadoncho.getValueAt(indexHD, 0).toString();
            String maSP = tblGioHang.getValueAt(indexGH, 0).toString();
            for (HoaDonCho hd : listHoaDonCho) {
                if (hd.getMaHD().equalsIgnoreCase(maHD)
                        && hd.getMaSP().equals(maSP)) {
                    listHoaDonCho.remove(hd);
                    break;
                }
            }
            int soLuongXoa = Integer.parseInt(tblGioHang.getValueAt(indexGH, 3).toString());
            String soLuongTon = null;
            for (SanPham sp : listSP) {
                if (sp.getMaSP().equalsIgnoreCase(maSP)) {
                    soLuongTon = sp.getSoLuong();
                    int sl = Integer.parseInt(soLuongTon) + soLuongXoa;
                    sp.setSoLuong(String.valueOf(sl));
                    break;
                }
            }

            mol = (DefaultTableModel) tblGioHang.getModel();
            mol.removeRow(indexGH);
            tinhTongTienDonHang(maHD);
            fillTableSP(listSP);
        }


    }//GEN-LAST:event_btnXoaMouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        fillTableSP(listSP);
    }//GEN-LAST:event_jPanel7MouseClicked

    private void btnTimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimMouseClicked
        mol = (DefaultTableModel) tblBangSP.getModel();
        mol.setRowCount(0);
        String dm = cbDanhMuc.getSelectedItem().toString();
        String ms = cbMau.getSelectedItem().toString();
        String cl = cbChatLieu.getSelectedItem().toString();
        String maDM = null;
        String mauSac = null;
        String chatLieu = null;
        String kichThuoc = cbKichThuoc.getSelectedItem().toString();
        for (DanhMuc object : dmService.getAllDM()) {
            if (object.getTenDM().equals(dm)) {
                maDM = object.getMaDM();
                break;
            }
        }
        for (MauSac object : msService.getAllMS()) {
            if (object.getTenMS().equals(ms)) {
                mauSac = object.getTenMS();
                break;
            }
        }
        for (ChatLieu object : clService.getAllChatLieu()) {
            if (object.getTenCL().equals(cl)) {
                chatLieu = object.getTenCL();
                break;
            }
        }
        List<SanPham> list = new ArrayList<>();
        for (SanPham object : spService.getAll()) {

            if (object.getMaDM().equalsIgnoreCase(maDM)
                    && object.getMauSac().equalsIgnoreCase(mauSac)
                    && object.getChatLieu().equalsIgnoreCase(chatLieu)
                    && object.getKichThuoc().equalsIgnoreCase(kichThuoc)) {

                list.add(object);
            }
        }
        fillTableSP(list);
    }//GEN-LAST:event_btnTimMouseClicked

    private void cbKichThuocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbKichThuocItemStateChanged
        mol = (DefaultTableModel) tblBangSP.getModel();
        if (!check) {
            mol.setRowCount(0);
            String kichThuoc = cbKichThuoc.getSelectedItem().toString();
            List<SanPham> list = new ArrayList<>();
            for (SanPham object : spService.getAll()) {

                if (object.getKichThuoc().equalsIgnoreCase(kichThuoc)) {
                    list.add(object);
                }
            }
            fillTableSP(list);
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbKichThuocItemStateChanged

    private void cbChatLieuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbChatLieuItemStateChanged
        mol = (DefaultTableModel) tblBangSP.getModel();
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
            fillTableSP(list);
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbChatLieuItemStateChanged

    private void cbMauItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbMauItemStateChanged
        mol = (DefaultTableModel) tblBangSP.getModel();
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
            fillTableSP(list);
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbMauItemStateChanged

    private void txtTienKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTienKHMouseClicked
        if (txtTienKH.getText().equalsIgnoreCase("Nhập ...")) {
            txtTienKH.setText("");
        }

    }//GEN-LAST:event_txtTienKHMouseClicked

    private void txtTienKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKHKeyReleased
        if (!txtTienKH.getText().trim().isEmpty() && isNumber(txtTienKH.getText())) {
            lblCheckTienKH.setText("");
            int tienKH = Integer.parseInt(txtTienKH.getText().trim());
            int tienThanhToan = Integer.parseInt(lblThanhToan.getText().trim());
            int tienthua = tienKH - tienThanhToan;
            lblTienThua.setText(String.valueOf(tienthua));
        } else {
            lblCheckTienKH.setText("Mời nhập số");
        }
    }//GEN-LAST:event_txtTienKHKeyReleased

    private void btnHuyHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyHDMouseClicked
        txtMaKH.setText("");
        txtTenKH.setText("");
        lblTongTien.setText("0");
        lblThanhToan.setText("0");
        txtTienKH.setText("Nhập ...");
        lblTienThua.setText("0");
        txtGhiChu.setText("");
        txtGiamGia.setText("");
        int index = tblHoadoncho.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "chọn hóa đơn chờ hủy");
            return;
        }

        String maHDC = tblHoadoncho.getValueAt(index, 0).toString();
        for (HoaDonCho hdc : listHoaDonCho) {
            if (hdc.getMaHD().equals(maHDC)) {
                for (SanPham sp : listSP) {
                    if (hdc.getMaSP().equalsIgnoreCase(sp.getMaSP())) {
                        String slHDC = hdc.getSoLuong();
                        String soLuongTon = sp.getSoLuong();
                        sp.setSoLuong(String.valueOf(Integer.parseInt(slHDC) + Integer.parseInt(soLuongTon)));
                    }
                }
            }
        }
        // xóa tất cả các object trong listHoaDonCho có điều kiện là t.maHD.equalsIgnoreCase(lblMaHD.getText()) có mã hóa đơn lấy từ ô lblHD
        listHoaDonCho.removeIf((t) -> t.maHD.equalsIgnoreCase(lblMaHD.getText()));
        lblMaHD.setText("");
        mol = (DefaultTableModel) tblHoadoncho.getModel();
        mol.removeRow(index);
        mol = (DefaultTableModel) tblGioHang.getModel();
        mol.setRowCount(0);
        fillTableSP(listSP);
    }//GEN-LAST:event_btnHuyHDMouseClicked

    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        // Kiểm tra những điều kiện cần và đủ để thực hiện thanh toán
        int index = tblHoadoncho.getSelectedRow();

        if (index == -1) {
            JOptionPane.showMessageDialog(this, "chọn hóa đơn chờ cần thanh toán");
            return;
        }
        if (lblThanhToan.getText().equalsIgnoreCase("0")) {
            JOptionPane.showMessageDialog(this, "Hóa đơn chưa có sản phẩm nào cả");
            return;
        }
        if (Integer.parseInt(lblTienThua.getText()) < 0
                || txtTienKH.getText().equalsIgnoreCase("Nhập")
                || txtTienKH.getText().equals("")
                || !isNumber(txtTienKH.getText())) {
            JOptionPane.showMessageDialog(this, "Mời nhập tiền khách đưa");
            return;
        }
        //gọi Joptionpane đưa ra lựa chọn!
        if (JOptionPane.showConfirmDialog(this, "Bạn có muốn hoàn tất", "Xác nhận", JOptionPane.YES_OPTION) < 1) {
            // bắt đầu thực hiện thanh toán
            String maHDC = tblHoadoncho.getValueAt(index, 0).toString();
            String maKH = txtMaKH.getText().trim();
            String tongTien = lblThanhToan.getText().trim();
            String tenKH = txtTenKH.getText().trim();
            HomForm1 hf = new HomForm1();
            HoaDon hd = new HoaDon(maHDC, "", maKH, "", "", "", "", "", "", "", tongTien, hf.getMaNV());
            // add vào bảng hóa đơn
            hdService.addHoaDon(hd);
            //add vào bảng hóa đơn chi tiết
            String maHDCT = null;
            for (HoaDonCho hdc : listHoaDonCho) {
                if (hdc.getMaHD().equalsIgnoreCase(maHDC)) {
                    String k = hdService.getAllHD().get(hdService.getAllHD().size() - 1).getMaHDCT();
                    int so = Integer.parseInt(k.substring(2, k.length()));
                    if (String.valueOf(so).length() == 1) {
                        so++;
                        if (String.valueOf(so).length() == 2) {
                            maHDCT = "CT0" + String.valueOf(so);
                        } else {
                            maHDCT = "CT00" + String.valueOf(so);
                        }
                    } else if (String.valueOf(so).length() == 2) {
                        so++;
                        if (String.valueOf(so).length() == 3) {
                            maHDCT = "CT" + String.valueOf(so);
                        } else {
                            maHDCT = "CT0" + String.valueOf(so);
                        }
                    }
                    if (String.valueOf(so).length() >= 3) {
                        so++;
                        maHDCT = "CT" + String.valueOf(so);
                    }
                    HoaDon hdct = new HoaDon(
                            maHDC,
                            maHDCT,
                            maKH,
                            tenKH,
                            hdc.getMaSP(),
                            hdc.getTenSP(),
                            hdc.getDonGia(),
                            hdc.getSoLuong(),
                            txtGhiChu.getText().trim().isEmpty() ? "Không có ghi chú" : txtGhiChu.getText().trim(),
                            "",
                            "", "");
                    hdService.addHoaDonCT(hdct);
                }
            }
            LocalDate today = LocalDate.now();
            ThongKe ntk = new ThongKe(today.toString(), tongTien);
            boolean checkhd = true;
            for (ThongKe tk : thongkeService.getAllTK()) {
                if (tk.getNgayTao().equals(today.toString())) {
                    int tienHd = Integer.parseInt(tongTien);
                    int tienTK = Integer.parseInt(tk.getTongTien2()) + tienHd;
                    ntk.setTongTien(String.valueOf(tienTK));
                    thongkeService.updateThongKe(ntk);
                    checkhd = !checkhd;
                    break;
                }
            }
            if (checkhd) {
                thongkeService.addThongKe(ntk);
            }

            // Thực hiện clear form và 2 bảng giỏ hàng và xóa dòng hóa đơn chờ
            txtGiamGia.setText("");
            txtMaKH.setText("");
            txtTenKH.setText("");
            lblTongTien.setText("0");
            lblThanhToan.setText("0");
            txtTienKH.setText("Nhập ...");
            lblTienThua.setText("0");
            txtGhiChu.setText("");
            listHoaDonCho.removeIf((t) -> t.maHD.equalsIgnoreCase(lblMaHD.getText())); // xóa tất cả các đối tượng có mã hóa đơn là lblMaHD.getText()
            lblMaHD.setText("");
            mol = (DefaultTableModel) tblHoadoncho.getModel();
            mol.removeRow(index);
            mol = (DefaultTableModel) tblGioHang.getModel();
            mol.setRowCount(0);
            // sau khi đã add xong 2 bảng Hóa đơn và Hóa đơn CT thì bắt đầu thay đổi số lượng sản phẩm trong sql
            // nếu có sản phẩm số lượng trở về 0 thì thay đổi trạng thái
            // chạy vòng lặp với listSP đã thay đổi
            for (SanPham sanPham : listSP) {
                spService.updateSoluongSPCT(sanPham); // thay đổi số lượng sản phẩm trong bảng ChiTietSanPham
                if (sanPham.getSoLuong().equalsIgnoreCase("0")) {
                    spService.updateTrangThaiSP(sanPham); // Thay đổi trạng thái 
                }
            }
            JOptionPane.showMessageDialog(this, "Thanh toán thành công");
            XuatHoaDon xd = new XuatHoaDon();
            xd.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {

                }
            });
            xd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            xd.fillTable(maHDC);
            xd.setLocationRelativeTo(this);
            xd.setVisible(true);
        }


    }//GEN-LAST:event_btnThanhToanMouseClicked

    private void cbLoaiThanhToanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbLoaiThanhToanItemStateChanged
        if (!check) {
            if (cbLoaiThanhToan.getSelectedItem().toString().equals("Chuyển khoản")) {
                txtTienKH.setText(lblThanhToan.getText());
            }
            check = !check;
        } else {
            check = !check;
        }
    }//GEN-LAST:event_cbLoaiThanhToanItemStateChanged

    private void txtFindSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFindSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFindSDTActionPerformed

    private void txtFindSDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFindSDTKeyReleased
        String keyword = txtFindSDT.getText().trim();
        for (khachHang kh : khService.getAllkh()) {
            if (kh.getSdt().equalsIgnoreCase(keyword)) {
                txtTenKH.setText(kh.getTenKh());
                txtMaKH.setText(kh.getMaKH());
                break;
            }
        }
    }//GEN-LAST:event_txtFindSDTKeyReleased

    private void txtGiamGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtGiamGiaMouseClicked
        txtGiamGia.setText("");
    }//GEN-LAST:event_txtGiamGiaMouseClicked

    private void txtGiamGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiamGiaKeyReleased
        String t = txtGiamGia.getText();
        boolean k = false;
        if (!t.equals("") && t.length() == 10) {
            for (KhuyenMai km : kmservice.getAll()) {
                if (km.getMaKM().equalsIgnoreCase(t)
                        && km.getTrangThai().equalsIgnoreCase("Còn hạn")) {
                    lblGiamGia.setText(km.getMucKhuyenMai());
                    tinhTongTienDonHang(lblMaHD.getText().trim());
                    break;
                } else {
                    k = true;
                }
            }
        }
        if (k) {
            JOptionPane.showMessageDialog(this, "Mã không tồn tại hoặc đã hết hạn");
            lblGiamGia.setText("");
        }
    }//GEN-LAST:event_txtGiamGiaKeyReleased

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnXoaLocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaLocMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaLocMouseClicked

    private void btnXoaLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLocActionPerformed
        // TODO add your handling code here:
        fillTableSP(listSP);
        txtTimKiem.setText("");
    }//GEN-LAST:event_btnXoaLocActionPerformed

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
            java.util.logging.Logger.getLogger(ViewBanHang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewBanHang1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewBanHang1().setVisible(true);
            }
        });
    }



    @Override
    public void run() {
        do {

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
            Result maSP = null;
            BufferedImage image = null;

            
            if (image != null) {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    maSP = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException ex) {

                }
                if (maSP != null) {

                    int indexs = 0;
                    for (int i = 0; i < listSP.size(); i++) {
                        if (listSP.get(i).getMaSP().equals(maSP.getText())) {
                            indexs = i;
                            break;
                        }
                    }
                    int indexHD = tblHoadoncho.getSelectedRow();

                    if (indexHD == -1) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn chờ để thêm sản phẩm");
                        continue; // dùng lệnh continue tránh dùng return vì đang chạy trong hàm run, dùng return sẽ dừng hàm run và không thể dùng quét mã
                    }
                    int kk = 0; // tạo ra biến kk để check, nếu kk thay đổi thì continue chạy tiếp run nhưng không chạy đoạn trong if(kk==0), vẫn là tránh thoát hàm run()
                    for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                        if (tblGioHang.getValueAt(i, 0).toString().equalsIgnoreCase(maSP.getText())) {
                            JOptionPane.showMessageDialog(this, "Sản phẩm đã trong giỏ hàng, sửa lại số lượng");
                            kk++;
                            continue;
                        }
                    }
                    if (kk == 0) {
                        String input = JOptionPane.showInputDialog(this, "Mời nhập số lượng");
                        if (input == null || input.equals("")) {
                            continue;
                        }
                        if (!isNumber(input)) {
                            JOptionPane.showMessageDialog(this, "Mời nhập số!");
                            continue;
                        }

                        int soLuongMua = Integer.parseInt(input);
                        int soLuongTon = Integer.parseInt(listSP.get(indexs).getSoLuong());
                        if (soLuongMua > soLuongTon) {
                            JOptionPane.showMessageDialog(this, "Số lượng tồn kho không đủ");
                            continue;
                        } else if (soLuongMua < 1) {
                            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ, vui lòng nhập lại");
                            continue;
                        } else {
                            String maHD = tblHoadoncho.getValueAt(indexHD, 0).toString();
                            String tenSP = null;
                            String donGia = null;
                            for (SanPham sp : listSP) {
                                if (sp.getMaSP().equals(maSP.getText())) {
                                    tenSP = sp.getTenSP();
                                    donGia = sp.getDonGia2();
                                    break;
                                }
                            }
                            SanPham sanPham = new SanPham(maSP.getText(), tenSP, donGia, input);
                            int tongGiaTien = sanPham.getTongGiaTien();
                            mol = (DefaultTableModel) tblGioHang.getModel();

                            mol.addRow(new Object[]{
                                maSP, tenSP, donGia, input, tongGiaTien
                            });
                            // trừ số lượng đã nhập trên bảng sản phẩm
                            String tongTien = String.valueOf(tongGiaTien);
                            listSP.get(indexs).setSoLuong(String.valueOf(soLuongTon - soLuongMua));
                            HoaDonCho hdCho = new HoaDonCho(txtMaKH.getText().trim(), txtTenKH.getText(), maHD, maSP.getText(), tenSP, donGia, input, tongTien);
                            listHoaDonCho.add(hdCho);
                            tinhTongTienDonHang(maHD);
                            fillTableSP(listSP);
                        }
                    }

                }
            }

        } while (true);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackBH;
    private javax.swing.JButton btnChonKH;
    private javax.swing.JButton btnHuyHD;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaoMaHD;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemSPvaoGio;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaLoc;
    private javax.swing.JComboBox<String> cbChatLieu;
    private javax.swing.JComboBox<String> cbDanhMuc;
    private javax.swing.JComboBox<String> cbKichThuoc;
    private javax.swing.JComboBox<String> cbLoaiThanhToan;
    private javax.swing.JComboBox<String> cbMau;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lblCheckTienKH;
    private javax.swing.JLabel lblGiamGia;
    private javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienThua;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JPanel panelCam;
    private javax.swing.JPanel panelDonHang;
    private javax.swing.JPanel panelWebcam;
    private javax.swing.JTable tblBangSP;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoadoncho;
    private javax.swing.JTextField txtFindSDT;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGiamGia;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienKH;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JScrollPane txtxtxArea;
    // End of variables declaration//GEN-END:variables

    private static class HoaDonCho {

        private String maKH;
        private String tenKH;
        private String maHD;
        private String maSP;
        private String tenSP;
        private String donGia;
        private String soLuong;
        private String tongTien;

        public HoaDonCho() {
        }

        public HoaDonCho(String maKH, String tenKH, String maHD, String maSP, String tenSP, String donGia, String soLuong, String tongTien) {
            this.maKH = maKH;
            this.tenKH = tenKH;
            this.maHD = maHD;
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.donGia = donGia;
            this.soLuong = soLuong;
            this.tongTien = tongTien;
        }

        public String getMaKH() {
            return maKH;
        }

        public void setMaKH(String maKH) {
            this.maKH = maKH;
        }

        public String getTenKH() {
            return tenKH;
        }

        public void setTenKH(String tenKH) {
            this.tenKH = tenKH;
        }

        public String getMaHD() {
            return maHD;
        }

        public void setMaHD(String maHD) {
            this.maHD = maHD;
        }

        public String getMaSP() {
            return maSP;
        }

        public void setMaSP(String maSP) {
            this.maSP = maSP;
        }

        public String getTenSP() {
            return tenSP;
        }

        public void setTenSP(String tenSP) {
            this.tenSP = tenSP;
        }

        public String getDonGia() {
            return donGia;
        }

        public void setDonGia(String donGia) {
            this.donGia = donGia;
        }

        public String getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(String soLuong) {
            this.soLuong = soLuong;
        }

        public String getTongTien() {
            int tong = Integer.parseInt(soLuong) * Integer.parseInt(donGia);
            return String.valueOf(tong);
        }

        public void setTongTien(String tongTien) {
            this.tongTien = tongTien;
        }

        public Object[] todataRow() {
            return new Object[]{
                this.maSP, this.tenSP, this.donGia, this.soLuong, this.getTongTien()
            };
        }
    }

}

