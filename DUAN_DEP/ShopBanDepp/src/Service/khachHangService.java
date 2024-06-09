/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.khachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class khachHangService {

    Connection con = null;
    PreparedStatement ps = null;
    String sql = null;
    ResultSet rs = null;

    public List<khachHang> getAllkh() {
        sql = "select*from KhachHang";
        List<khachHang> kh = new ArrayList<>();
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                khachHang kHang = new khachHang(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7));
                kh.add(kHang);
            }
            return kh;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public int themKh(khachHang kh) {

        sql = "insert into KhachHang values (?,?,?,?,?,?,?)";
        try {
            con = DBconnect1.getConnection();

            ps = con.prepareCall(sql);

            ps.setObject(1, kh.getMaKH());
            ps.setObject(2, kh.getTenKh());
            ps.setObject(3, kh.getGioiTinh());
            ps.setObject(4, kh.getSdt());
            ps.setObject(5, kh.getEmail());
            ps.setObject(6, kh.getLoaiKH());
            ps.setObject(7, kh.getTongChiTieu());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public khachHang getKhachHangByMa(String ma) {
        for (khachHang kh : getAllkh()) {
            if (kh.getMaKH().equals(ma)) {
                {
                    return kh;
                }
            }
        }
        return null ;
    }
    public int suaKhachHangByKhachHang(khachHang kh){
        sql = "update KhachHang\n"
                + "set HoTen= ?, "
                + "GioiTinh=?,"
                + "SDT=?,"
                + "Email=?\n"
                + "where MaKH = ?;\n"
              ;
         try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, kh.getTenKh());
            ps.setObject(2, kh.getGioiTinh());
            ps.setObject(3, kh.getSdt());
            ps.setObject(4, kh.getEmail());
            ps.setObject(5, kh.getMaKH());
            return ps.executeUpdate();
         }catch(Exception e){
             e.printStackTrace();
             return 0;
         }
    }
}
