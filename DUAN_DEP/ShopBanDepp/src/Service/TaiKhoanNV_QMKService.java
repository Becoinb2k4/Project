/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.TaiKhoanNV_QMK;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class TaiKhoanNV_QMKService {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = null;
    
     public TaiKhoanNV_QMK layTTTaiKhoanByUsername(String TKNV) throws SQLException{
        Connection con1 = DBconnect1.getConnection(); // lấy kết lối dữ liệu
        String sql = "select tk.TKNV,tk.TenNV_SuDung,tk.MatKhau,nv.Email "
                + "from TaiKhoan tk join NhanVien nv on tk.TKNV = nv.TKNV "
                + "where tk.TKNV LIKE ?"; // khởi tao truy vâns
        PreparedStatement statement = con1.prepareStatement(sql);
        statement.setString(1, TKNV);
        ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String tenTK = rs.getString("TKNV");
                String matKhau = rs.getString("MatKhau");
                String hoTen = rs.getString("TenNV_SuDung");
                String email = rs.getString("Email");
                
                TaiKhoanNV_QMK taiKhoan = new TaiKhoanNV_QMK(tenTK, hoTen, matKhau, email);
                rs.close();
                statement.close();
                con1.close();
                
                return taiKhoan;
            }
        
        return null;
    }
     public static List<TaiKhoanNV_QMK> layDSTaiKhoan() throws SQLException{
        List<TaiKhoanNV_QMK> taiKhoans = new ArrayList();
        Connection con1 = DBconnect1.getConnection(); // lấy kết lối dữ liệu
        String query = "select tk.TKNV,tk.TenNV_SuDung,tk.MatKhau,nv.Email from TaiKhoan tk join NhanVien nv on tk.TKNV = nv.TKNV"; // khởi tao truy vâns
        Statement statement = con1.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while(rs.next()){
            String tenTaiKhoan = rs.getString("TKNV");
                String matKhau = rs.getString("MatKhau");
                String hoTen = rs.getString("TenNV_SuDung");
                String email = rs.getString("Email");
            
            TaiKhoanNV_QMK taiKhoan = new TaiKhoanNV_QMK(tenTaiKhoan, matKhau, hoTen, email);
            taiKhoans.add(taiKhoan);
        }
        return taiKhoans;
    }
    
    
    public boolean capNhapPass(String username, String passUpdate) throws SQLException{
        Connection con1 = DBconnect1.getConnection();
        String sql = "UPDATE TaiKhoan SET MatKhau = ? WHERE TKNV = ?";
        PreparedStatement statement = con1.prepareStatement(sql);
        statement.setString(1, passUpdate);
        statement.setString(2, username);
        int index = statement.executeUpdate();
        
        con1.close();
        statement.close();
        
        if(index == 0){
            return false;
        }else{
            return true;
        }   
    }
}
