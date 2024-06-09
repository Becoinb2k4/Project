/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.CaLam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class CaLamService {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;

    public List<CaLam> findNV(String TKNV) {
       //sql = " select 
        sql = "SELECT n.MaNV, n.TenNV, ca.Ca, ca.GioBatDau, ca.GioKetThuc\n"
                + "FROM NhanVien n\n"
                + "JOIN CaLam ca ON n.MaNV = ca.MaNV\n"
                + "JOIN TaiKhoan t ON n.TKNV = t.TKNV\n"
                + "WHERE t.TKNV = ?;";
        List<CaLam> listsp = new ArrayList<>();
        try {
            con = DBconnect1.getConnection();
            ps = con.prepareStatement(sql);
            ps.setObject(1, TKNV);
            rs = ps.executeQuery();
            while (rs.next()) {
                CaLam sp = new CaLam(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)       
                );

                listsp.add(sp);
            }
            return listsp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
