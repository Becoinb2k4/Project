/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.ThongKe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ThongKeService {
     Connection con = null;
    PreparedStatement ps = null;
    String sql = null;
    ResultSet rs = null;

    public List<ThongKe> getAllTK() {
        sql = "select NgayTao,TongTien from ThongKe order by NgayTao";
        List<ThongKe> list = new ArrayList<>();
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ThongKe tk = new ThongKe(
                        rs.getString(1),
                        rs.getString(2));
                list.add(tk);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    
    
    
    
    

    
    
    public int addThongKe(ThongKe tk) {
        sql = "insert into thongke (ngaytao,tongtien) values (?,?)";
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, tk.getNgayTao());
            ps.setObject(2, tk.getTongTien());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateThongKe(ThongKe tk) {
        sql = "update thongke set tongtien = ? where ngaytao = ?";
        try {
            con = DBconnect1.getConnection();

            ps = con.prepareStatement(sql);
            ps.setObject(1, tk.getTongTien());
            ps.setObject(2, tk.getNgayTao());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
}
