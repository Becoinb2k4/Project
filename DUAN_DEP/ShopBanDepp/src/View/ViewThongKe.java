/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Model.HoaDon;
import Model.SanPham;
import Model.ThongKe;
import Service.HoaDonService;
import Service.SanPhamService;
import Service.ThongKeService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 
 */
public class ViewThongKe extends javax.swing.JFrame {

    DefaultTableModel mol = new DefaultTableModel();
    HoaDonService hdService = new HoaDonService();
    SanPhamService spService = new SanPhamService();
    ThongKeService tkService = new ThongKeService();
    List<HoaDon> listTK = new ArrayList<>();

    public ViewThongKe() {
        initComponents();
        fillTableDTSP();
        fillTableSPHet();
       
        tblDTSP.setAutoCreateRowSorter(true);

    }

    public void fillTableSPHet() {
        mol = (DefaultTableModel) tblSapHet.getModel();
        mol.setRowCount(0);
        List<SanPham> list = spService.getAll();
        Collections.sort(list, new Comparator<SanPham>() {
            @Override
            public int compare(SanPham o1, SanPham o2) {
                return Integer.compare(o1.soluongInt(), o2.soluongInt());
            }
        });
        for (SanPham sp : list) {
            if (Integer.parseInt(sp.getSoLuong()) < 6) {
                mol.addRow(new Object[]{
                    sp.getMaSP(), sp.getTenSP(), sp.getSoLuong()
                });
            }
        }
    }

    public List<HoaDon> fillTableDTSP() {
        mol = (DefaultTableModel) tblDTSP.getModel();
        mol.setRowCount(0);
        List<HoaDon> list = hdService.getAllHD();
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
            listTK.add(hd);
        }

        for (HoaDon hoaDon : listTK) {
            for (HoaDon hoaDon1 : list) {
                if (hoaDon.getMaSP().equals(hoaDon1.getMaSP())) {
                    hoaDon.setTenSP(hoaDon1.getTenSP());
                    hoaDon.setNgayTao(hoaDon1.getNgayTao());
                }
            }
        }

        Collections.sort(listTK, new Comparator<HoaDon>() {
            @Override
            public int compare(HoaDon o1, HoaDon o2) {
                return Integer.compare(o2.tongTienInt(), o1.tongTienInt());
            }
        });
        for (HoaDon hoaDon : listTK) {
            mol.addRow(new Object[]{
                hoaDon.getMaSP(), hoaDon.getTenSP(), hoaDon.getSoLuong(), hoaDon.getTongTien()
            });
        }
        return listTK;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        BackThongKe = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDTSP = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSapHet = new javax.swing.JTable();
        btnThongKeSP = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(204, 102, 0));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Thống kê");

        BackThongKe.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        BackThongKe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        BackThongKe.setText("Back");
        BackThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackThongKeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                BackThongKeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BackThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(301, 301, 301)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(357, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm đã bán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblDTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng đã bán", "Doanh thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblDTSP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sản phẩm sắp hết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblSapHet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng tồn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblSapHet);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        btnThongKeSP.setBackground(new java.awt.Color(204, 102, 0));
        btnThongKeSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThongKeSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKeSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/thống kê.png"))); // NOI18N
        btnThongKeSP.setText("Thống kê sản phẩm đã bán");
        btnThongKeSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThongKeSPMouseClicked(evt);
            }
        });
        btnThongKeSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThongKeSP)
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(btnThongKeSP)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackThongKeMouseClicked
        HomForm1 form1 = new HomForm1();
        form1.setVisible(true);
        form1.pack();
        form1.setLocationRelativeTo(null);
        form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_BackThongKeMouseClicked

    private void BackThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackThongKeMouseEntered
        BackThongKe.setLayout(new BorderLayout(10, 10));
        BackThongKe.setBorder(BorderFactory.createEtchedBorder(Color.black, Color.black));
    }//GEN-LAST:event_BackThongKeMouseEntered

    private void BackThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackThongKeMouseExited
        BackThongKe.setLayout(null);
        BackThongKe.setBorder(null);
    }//GEN-LAST:event_BackThongKeMouseExited

    private void btnThongKeSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeSPMouseClicked
        bieuDo bd = new bieuDo();
        bd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mol =(DefaultTableModel) tblDTSP.getModel();
        mol.setRowCount(0);

        bd.setLocationRelativeTo(this);
        bd.setVisible(true);
    }//GEN-LAST:event_btnThongKeSPMouseClicked

    private void btnThongKeSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnThongKeSPActionPerformed

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
            java.util.logging.Logger.getLogger(ViewThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewThongKe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewThongKe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackThongKe;
    private javax.swing.JButton btnThongKeSP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDTSP;
    private javax.swing.JTable tblSapHet;
    // End of variables declaration//GEN-END:variables
}
