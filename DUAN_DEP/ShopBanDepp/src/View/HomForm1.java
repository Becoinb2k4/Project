/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.HoaDon;
import Service.CaLamService;
import Service.HoaDonService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.Border;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 
 */
public class HomForm1 extends javax.swing.JFrame {

    CaLamService caLam = new CaLamService();
    public static String USER = "sa";
    public static String PASSWORD = "123";
    public static String URL = "jdbc:sqlserver://localhost:1433;databaseName=qlbandep;trustServerCertificate=true;";

    private Connection conn;
    DefaultTableModel mol = new DefaultTableModel();
    HoaDonService hoaDonService = new HoaDonService();
    boolean check = false;

    public HomForm1() {

            initComponents();
            date();
            time();setLocationRelativeTo(null);
            ToanCuc tt = new ToanCuc();
            lblUser.setText(tt.getUserTKNV());
            fillForm(lblUser.getText());
            fillTable();
            fillTableSP(spDaban());
            
            

    }
//    public boolean isNumber(String str) {
//        for (int i = 0; i < str.length(); i++) {
//            if (!Character.isDigit(str.charAt(i))) {
//                return false;
//            }
//        }
//        return true;
//    }


   

    public List<HoaDon> spDaban() {
        List<HoaDon> list = hoaDonService.getAllHD();
        List<HoaDon> listspban = new ArrayList<>();
        Map<String, Integer> soluongtong = new HashMap<>();
        Map<String, Integer> tientong = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String masp = list.get(i).getMaSP();
            int soluong = Integer.parseInt(list.get(i).getSoLuong());
            int tien = Integer.parseInt(list.get(i).getTongTien2());

            if (soluongtong.containsKey(masp)) {
                // Nếu mã sản phẩm đã tồn tại trong HashMap, cộng số lượng và tổng giá mới vào
                int totalQuantity = soluongtong.get(masp);
                soluongtong.put(masp, totalQuantity + soluong);

                int totalPrice = tientong.get(masp);
                tientong.put(masp, totalPrice + tien);
            } else {
                // Nếu mã sản phẩm chưa tồn tại, thêm mới với số lượng và giá là tổng số lượng và tổng giá
                soluongtong.put(masp, soluong);
                tientong.put(masp, tien);
            }
        }
        for (Map.Entry<String, Integer> entry : soluongtong.entrySet()) {
            String maSP = entry.getKey();

            int soluong = entry.getValue();
            int tongtien = tientong.get(maSP);
            HoaDon hd = new HoaDon(maSP, String.valueOf(soluong), String.valueOf(tongtien));
            listspban.add(hd);
        }
        for (HoaDon hoaDon : hoaDonService.getAllHD()) {
            for (HoaDon hoaDon1 : listspban) {
                if (hoaDon.getMaSP().equalsIgnoreCase(hoaDon1.getMaSP())) {
                    hoaDon1.setDonGia(hoaDon.getDonGia2());
                    hoaDon1.setTenSP(hoaDon.getTenSP());
                    hoaDon1.setNgayTao(hoaDon.getNgayTao());
                    hoaDon1.setMaNV(hoaDon.getMaNV());
                }
            }
        }

        return listspban;
    }
    public void hienThiavatar() {
        //String imagePath = "C:\\Users\\ADMIN\\Downloads\\avt\\" + getMaNV() + ".png";
        String imagePath = "C:\\Users\\ADMIN\\Downloads\\avt.png";
        //link
        // Kiểm tra xem tệp hình ảnh có tồn tại không
        File file = new File(imagePath);
        if (!file.exists()) {
            return;
        }
        ImageIcon imageIcon = new ImageIcon(imagePath);
        lblIconNV.setText("");
        lblIconNV.setIcon(imageIcon);
    }

    public void fillTable() {
        LocalDate today = LocalDate.now();
        mol = (DefaultTableModel) tblHoaDon.getModel();
        mol.setRowCount(0);
        for (HoaDon hd : hoaDonService.getAllHDGoc()) {
            if (hd.getNgayTao().equalsIgnoreCase(today.toString())
                    && hd.getMaNV().equalsIgnoreCase(lblMaNV.getText().trim())) {
                mol.addRow(new Object[]{
                    hd.getMaHD(), hd.getTongTien2()
                });
            }
        }
    }

    public void fillTableSP(List<HoaDon> list) {
        LocalDate today = LocalDate.now();
        mol = (DefaultTableModel) tblSPdaban.getModel();
        mol.setRowCount(0);
        for (HoaDon hd : list) {
            if (hd.getNgayTao().equalsIgnoreCase(today.toString())
                    && hd.getMaNV().equalsIgnoreCase(lblMaNV.getText().trim())) {
                mol.addRow(new Object[]{
                    hd.getMaSP(), hd.getTenSP(), hd.getDonGia(), hd.getSoLuong(), hd.getTongTien()
                });
            }
        }
    }

    public void fillFormGiaoCa() {
        int tongtien = 0;
        int i = 0;
        LocalDate today = LocalDate.now();
        for (HoaDon hd : hoaDonService.getAllHDGoc()) {
            if (hd.getNgayTao().equalsIgnoreCase(today.toString())
                    && hd.getMaNV().equalsIgnoreCase(lblMaNV.getText().trim())) {
                int t = Integer.parseInt(hd.getTongTien2());
                tongtien = tongtien + t;
                i++;
            }

        }
        lblTongtienHoaDon.setText(String.valueOf(tongtien));
        lblSohoaDon.setText(String.valueOf(i));
    }

    public void fillForm(String TKNV) {
   
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            ResultSet rs;
            String sql = "SELECT n.MaNV, n.TenNV, ca.Ca, ca.GioBatDau, ca.GioKetThuc\n"
                    + "FROM NhanVien n\n"
                    + "JOIN CaLam ca ON n.MaNV = ca.MaNV\n"
                    + "JOIN TaiKhoan t ON n.TKNV = t.TKNV\n"
                    + "WHERE t.TKNV = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ToanCuc tt = new ToanCuc();
            lblUser.setText(tt.getUserTKNV());
            ps.setString(1, lblUser.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                lblMaNV.setText(rs.getString("MaNV"));
                lblTenNV.setText(rs.getString("TenNV"));
                return;
            } else {
                int choice = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng nhập", "Bạn chưa đăng nhập", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    ViewLogin2 login = new ViewLogin2();
                    login.setVisible(true);
                    login.pack();
                    login.setLocationRelativeTo(null);
                    login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    this.dispose();
                } else if (choice == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi!");
            return;
        }
    }

    public String getMaNV() {
        return lblMaNV.getText();
    }

    public String getTaiKhoan() {
        return lblUser.getText();
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
            } else {

            }
        }

        int lastIndex = filePath.lastIndexOf("\\");
        String lastSubstring = filePath.substring(0, lastIndex);
        return lastSubstring;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        lblBanHang = new javax.swing.JLabel();
        lblHoaDon = new javax.swing.JLabel();
        lblSanPham = new javax.swing.JLabel();
        lblNhanVien = new javax.swing.JLabel();
        lblKhachHang = new javax.swing.JLabel();
        lblKhuyenMai = new javax.swing.JLabel();
        lblThongKe = new javax.swing.JLabel();
        lblTenNV = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblIconNV = new javax.swing.JLabel();
        lblThoat = new javax.swing.JLabel();
        panelBody = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        lblSohoaDon = new javax.swing.JLabel();
        lblTongtienHoaDon = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSPdaban = new javax.swing.JTable();
        lblDate = new javax.swing.JLabel();
        lblTime1 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMenu.setBackground(new java.awt.Color(204, 102, 0));

        lblBanHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBanHang.setForeground(new java.awt.Color(255, 255, 255));
        lblBanHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/895580.png"))); // NOI18N
        lblBanHang.setText("BÁN HÀNG");
        lblBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBanHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBanHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBanHangMouseExited(evt);
            }
        });

        lblHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        lblHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/hóa đơn.png"))); // NOI18N
        lblHoaDon.setText("HÓA ĐƠN");
        lblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHoaDonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHoaDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHoaDonMouseExited(evt);
            }
        });

        lblSanPham.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSanPham.setForeground(new java.awt.Color(255, 255, 255));
        lblSanPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sản phẩm.png"))); // NOI18N
        lblSanPham.setText("SẢN PHẨM");
        lblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSanPhamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSanPhamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSanPhamMouseExited(evt);
            }
        });

        lblNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        lblNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/nhân viên.png"))); // NOI18N
        lblNhanVien.setText("NHÂN VIÊN");
        lblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNhanVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblNhanVienMouseExited(evt);
            }
        });

        lblKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        lblKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/khách hàng.png"))); // NOI18N
        lblKhachHang.setText("KHÁCH HÀNG");
        lblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKhachHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblKhachHangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblKhachHangMouseExited(evt);
            }
        });

        lblKhuyenMai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblKhuyenMai.setForeground(new java.awt.Color(255, 255, 255));
        lblKhuyenMai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKhuyenMai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/khuyến mãi.png"))); // NOI18N
        lblKhuyenMai.setText("KHUYẾN MÃI");
        lblKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblKhuyenMaiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblKhuyenMaiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblKhuyenMaiMouseExited(evt);
            }
        });

        lblThongKe.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThongKe.setForeground(new java.awt.Color(255, 255, 255));
        lblThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thống kê.png"))); // NOI18N
        lblThongKe.setText("THỐNG KÊ");
        lblThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongKeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblThongKeMouseExited(evt);
            }
        });

        lblTenNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTenNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNV.setText("TÔ VĂN DƯƠNG");
        lblTenNV.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMaNV.setForeground(new java.awt.Color(255, 0, 51));
        lblMaNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaNV.setText("NV001");

        lblUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("ADMIN");
        lblUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblIconNV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        lblIconNV.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblThoat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblThoat.setForeground(new java.awt.Color(255, 255, 255));
        lblThoat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/toát.png"))); // NOI18N
        lblThoat.setText("THOÁT");
        lblThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThoatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                            .addComponent(lblKhuyenMai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblThongKe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblThoat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTenNV)
                            .addComponent(lblMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblIconNV, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblIconNV, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(lblMaNV)
                .addGap(38, 38, 38)
                .addComponent(lblTenNV)
                .addGap(44, 44, 44)
                .addComponent(lblUser)
                .addGap(18, 18, 18)
                .addComponent(lblBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblKhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );

        getContentPane().add(panelMenu, java.awt.BorderLayout.LINE_START);

        panelBody.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(204, 102, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn đã thanh toán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã hóa đơn", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongtienHoaDon)
                    .addComponent(lblSohoaDon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSohoaDon)
                        .addGap(51, 51, 51)
                        .addComponent(lblTongtienHoaDon)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

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

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm đã bán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        jPanel4.setDoubleBuffered(false);

        tblSPdaban.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblSPdaban);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 682, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcel)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btnExcel)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblDate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDate.setText("01/01/2023");

        lblTime1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTime1.setForeground(new java.awt.Color(255, 255, 255));
        lblTime1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime1.setText("Time :");

        lblTime.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTime.setText("10 : 56 : 37");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Date :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Welcome To SHop DEP");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTime1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDate)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTime1)
                            .addComponent(lblTime))
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelBody.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panelBody, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanHangMouseClicked
        ViewBanHang1 banHang1 = new ViewBanHang1();
        banHang1.setVisible(true);
        banHang1.pack();
        banHang1.setLocationRelativeTo(null);
        banHang1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblBanHangMouseClicked

    private void lblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoaDonMouseClicked
        ViewHoaDon don = new ViewHoaDon();
        don.setVisible(true);
        don.pack();
        don.setLocationRelativeTo(null);
        don.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblHoaDonMouseClicked

    private void lblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPhamMouseClicked
        SanPhamView pham = new SanPhamView();
        pham.setVisible(true);
        pham.pack();
        pham.setLocationRelativeTo(null);
        pham.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblSanPhamMouseClicked

    private void lblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanVienMouseClicked
        ViewNhanVien nhanVien = new ViewNhanVien();
        nhanVien.setVisible(true);
        nhanVien.pack();
        nhanVien.setLocationRelativeTo(null);
        nhanVien.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblNhanVienMouseClicked

    private void lblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhachHangMouseClicked
        KhachHang1 hang1 = new KhachHang1();
        hang1.setVisible(true);
        hang1.pack();
        hang1.setLocationRelativeTo(null);
        hang1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblKhachHangMouseClicked

    private void lblKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhuyenMaiMouseClicked
        ViewKhuyenMai1 khuyenMai1 = new ViewKhuyenMai1();
        khuyenMai1.setVisible(true);
        khuyenMai1.pack();
        khuyenMai1.setLocationRelativeTo(null);
        khuyenMai1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblKhuyenMaiMouseClicked

    private void lblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseClicked
         ViewThongKe ke = new ViewThongKe();
        ke.setVisible(true);
        ke.pack();
        ke.setLocationRelativeTo(null);
        ke.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblThongKeMouseClicked

    private void lblBanHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanHangMouseEntered
        lblBanHang.setLayout(new BorderLayout(10, 10));
        lblBanHang.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_lblBanHangMouseEntered

    private void lblBanHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBanHangMouseExited
        lblBanHang.setLayout(null);
        lblBanHang.setBorder(null);
    }//GEN-LAST:event_lblBanHangMouseExited

    private void lblKhuyenMaiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhuyenMaiMouseEntered
        lblKhuyenMai.setLayout(new BorderLayout(10, 10));
        lblKhuyenMai.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_lblKhuyenMaiMouseEntered

    private void lblKhuyenMaiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhuyenMaiMouseExited
        lblKhuyenMai.setLayout(null);
        lblKhuyenMai.setBorder(null);
    }//GEN-LAST:event_lblKhuyenMaiMouseExited

    private void lblSanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPhamMouseEntered
        lblSanPham.setLayout(new BorderLayout(10, 10));
        lblSanPham.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_lblSanPhamMouseEntered

    private void lblSanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSanPhamMouseExited
        lblSanPham.setLayout(null);
        lblSanPham.setBorder(null);
    }//GEN-LAST:event_lblSanPhamMouseExited

    private void lblHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoaDonMouseEntered
        lblHoaDon.setLayout(new BorderLayout(10, 10));
        lblHoaDon.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_lblHoaDonMouseEntered

    private void lblHoaDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHoaDonMouseExited
        lblHoaDon.setLayout(null);
        lblHoaDon.setBorder(null);
    }//GEN-LAST:event_lblHoaDonMouseExited

    private void lblNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanVienMouseEntered
        lblNhanVien.setLayout(new BorderLayout(10, 10));
        lblNhanVien.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_lblNhanVienMouseEntered

    private void lblNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNhanVienMouseExited
        lblNhanVien.setLayout(null);
        lblNhanVien.setBorder(null);
    }//GEN-LAST:event_lblNhanVienMouseExited

    private void lblKhachHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhachHangMouseEntered
        lblKhachHang.setLayout(new BorderLayout(10, 10));
        lblKhachHang.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_lblKhachHangMouseEntered

    private void lblKhachHangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblKhachHangMouseExited
        lblKhachHang.setLayout(null);
        lblKhachHang.setBorder(null);
    }//GEN-LAST:event_lblKhachHangMouseExited

    private void lblThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseEntered
        lblThongKe.setLayout(new BorderLayout(10, 10));
        lblThongKe.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_lblThongKeMouseEntered

    private void lblThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseExited
        lblThongKe.setLayout(null);
        lblThongKe.setBorder(null);
    }//GEN-LAST:event_lblThongKeMouseExited

    private void btnExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseClicked

        LocalDate today = LocalDate.now();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Giao ca");
            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 3000);
            XSSFRow row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Bảng giao ca");

            row = sheet.createRow(1);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Số hóa đơn: ");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Tổng tiền: ");

            row = sheet.createRow(2);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(lblSohoaDon.getText());
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(lblTongtienHoaDon.getText());

            row = sheet.createRow(4);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("Mã hóa đơn");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Tổng tiền");

            XSSFSheet sheet2 = workbook.createSheet("Sản phẩm đã bán");
            sheet2.setColumnWidth(0, 4000);
            sheet2.setColumnWidth(1, 6000);
            sheet2.setColumnWidth(3, 2500);
            sheet2.setColumnWidth(2, 4000);
            sheet2.setColumnWidth(4, 4000);
            XSSFRow row2 = null;
            Cell cell2 = null;
            row2 = sheet2.createRow(0);
            cell2 = row2.createCell(0, CellType.STRING);
            cell2.setCellValue("Sản phẩm đã bán");

            row2 = sheet2.createRow(1);
            cell2 = row2.createCell(0, CellType.STRING);
            cell2.setCellValue("Mã sản phẩm");
            cell2 = row2.createCell(1, CellType.STRING);
            cell2.setCellValue("Tên sản phẩm");
            cell2 = row2.createCell(2, CellType.STRING);
            cell2.setCellValue("Đơn giá");
            cell2 = row2.createCell(3, CellType.STRING);
            cell2.setCellValue("Số lượng");
            cell2 = row2.createCell(4, CellType.STRING);
            cell2.setCellValue("Tổng tiền");
            int i = 0;
            for (HoaDon cell1 : hoaDonService.getAllHD()) {
                if (cell1.getNgayTao().equalsIgnoreCase(today.toString())
                        && cell1.getMaNV().equalsIgnoreCase(lblMaNV.getText().trim())) {
                    row = sheet.createRow(5 + i);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(cell1.getMaHD()); // ma hoa don

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(cell1.getTongTien2()); // tong tien

                    i++;
                }
            }
            int j = 0;
            for (HoaDon cell1 : spDaban()) {

                if (cell1.getNgayTao().equalsIgnoreCase(today.toString())
                        && cell1.getMaNV().equalsIgnoreCase(lblMaNV.getText().trim())) {
                    row2 = sheet2.createRow(2 + j);
                    cell2 = row2.createCell(0, CellType.STRING);
                    cell2.setCellValue(cell1.getMaSP());

                    cell2 = row2.createCell(1, CellType.STRING);
                    cell2.setCellValue(cell1.getTenSP());

                    cell2 = row2.createCell(2, CellType.STRING);
                    cell2.setCellValue(cell1.getDonGia());
                    cell2 = row2.createCell(3, CellType.STRING);
                    cell2.setCellValue(cell1.getSoLuong());
                    cell2 = row2.createCell(4, CellType.STRING);
                    cell2.setCellValue(cell1.getTongTien());
                    j++;
                }
            }

            String name = "Giao ca ngày " + today.toString() + " " + lblMaNV.getText() + ".xlsx";
            File file = new File(chonDuongDan() + "\\" + name);
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
        check = !check;
    }//GEN-LAST:event_btnExcelMouseClicked

    private void lblThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMouseClicked
        ViewLogin2 ke = new ViewLogin2();
        ke.setVisible(true);
        ke.pack();
        ke.setLocationRelativeTo(null);
        ke.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_lblThoatMouseClicked

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
            java.util.logging.Logger.getLogger(HomForm1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomForm1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomForm1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomForm1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomForm1().setVisible(true);
            }
        });
    }

    void date() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        lblDate.setText(s.format(d));

    }

    void time() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
                lblTime.setText(s.format(d));
            }
        }
        ).start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBanHang;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblIconNV;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JLabel lblKhuyenMai;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNhanVien;
    private javax.swing.JLabel lblSanPham;
    private javax.swing.JLabel lblSohoaDon;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblThoat;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTime1;
    private javax.swing.JLabel lblTongtienHoaDon;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblSPdaban;
    // End of variables declaration//GEN-END:variables
}
