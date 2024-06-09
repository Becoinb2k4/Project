/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Interface.InF_KhuyenMai;
import Model.KhuyenMai;
import View.RandomStringGenerator;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class KhuyenMaiService implements InF_KhuyenMai {

    Connection con = null;
    PreparedStatement ps = null;
    String sql = null;
    ResultSet rs = null;
    Statement stmt = null;

    public List<KhuyenMai> getAll() {
        sql = "select * from khuyenmai";
        List<KhuyenMai> list = new ArrayList<>();
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai(rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getString(6),
                        rs.getString(5),
                        rs.getString(7));

                list.add(khuyenMai);

            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int add(KhuyenMai khuyenMai) {
        String randomMaKM = RandomStringGenerator.generateRandomString(10);
        khuyenMai.setMaKM(randomMaKM);
        sql = "INSERT INTO khuyenmai(MaKM, TenKM, NgayBatDau, NgayKetThuc, Mota, MucKhuyenMai,TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, khuyenMai.getMaKM());
            ps.setObject(2, khuyenMai.getTenKM());
            ps.setObject(3, khuyenMai.getNgayBatDau());
            ps.setObject(4, khuyenMai.getNgayKetThuc());
            ps.setObject(5, khuyenMai.getMoTa());
            ps.setObject(6, khuyenMai.getMucKhuyenMai());
            ps.setObject(7, khuyenMai.getTrangThai());
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(KhuyenMai khuyenMai, String MaKM) {
        sql = "UPDATE khuyenmai SET MaKM=?, TenKM=?, NgayBatDau=?, NgayKetThuc=?, MucKhuyenMai=?, Mota=?, TrangThai=? WHERE MaKM=?";
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, khuyenMai.getMaKM());
            ps.setObject(2, khuyenMai.getTenKM());
            ps.setObject(3, new java.sql.Date(khuyenMai.getNgayBatDau().getTime()));
            ps.setObject(4, new java.sql.Date(khuyenMai.getNgayKetThuc().getTime()));
            ps.setObject(5, khuyenMai.getMucKhuyenMai());
            ps.setObject(6, khuyenMai.getMoTa());
            ps.setObject(7, khuyenMai.getTrangThai());
            ps.setObject(8, MaKM);
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<KhuyenMai> getByName(String tenKM) {
        sql = "SELECT * FROM khuyenmai WHERE TenKM LIKE ?";
        List<KhuyenMai> list = new ArrayList<>();
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tenKM + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                KhuyenMai khuyenMai = new KhuyenMai(rs.getString(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getString(6),
                        rs.getString(5),
                        rs.getString(7));

                list.add(khuyenMai);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
