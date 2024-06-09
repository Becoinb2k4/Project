/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.NhanVien;
import Model.taiKhoanNV;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class NhanVienService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<NhanVien> getAllNV() {
        sql = "select * from NhanVien ";
        List<NhanVien> list = new ArrayList<>();
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int Them(NhanVien nv) {
        sql = "insert into NhanVien\n"
                + "values(?,?,?,?,?,?,?,?,?,?)";
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, nv.getMaNV());
            ps.setObject(2, nv.getTenNV());
            ps.setObject(3, nv.getSdt());
            ps.setObject(4, nv.getEmail());
            ps.setObject(5, nv.getGioiTinh());
            ps.setObject(6, nv.getChucVu());
            ps.setObject(7, nv.getLuongCB());
            ps.setObject(8, nv.getDiaChi());
            ps.setObject(9, nv.getTrangThai());
            ps.setObject(10, nv.getTkNV());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int sua(NhanVien nv) {
        sql = "update NhanVien\n"
                + "set TenNV =?, SDT= ?,Email=?,GioiTinh=?,ChucVu=?,\n"
                + "LuongCB =?,DiaChi=?,TrangThai=?,TKNV=?\n"
                + "where MaNV =?;\n"
                + "update TaiKhoan\n"
                + "set TenNV_SuDung =?\n"
                + "where TKNV =?";
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, nv.getTenNV());
            ps.setObject(2, nv.getSdt());
            ps.setObject(3, nv.getEmail());
            ps.setObject(4, nv.getGioiTinh());
            ps.setObject(5, nv.getChucVu());
            ps.setObject(6, nv.getLuongCB());
            ps.setObject(7, nv.getDiaChi());
            ps.setObject(8, nv.getTrangThai());
            ps.setObject(9, nv.getTkNV());
            ps.setObject(10, nv.getMaNV());
            ps.setObject(11, nv.getTenNV());
            ps.setObject(12, nv.getTkNV());

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public NhanVien getNhanVienByMa(String ma){
        NhanVien nv = null; 
        for(NhanVien a: getAllNV()){
            if(a.getMaNV().equals(ma)){
                return a;
            }
        }
        return nv;
    }

}
