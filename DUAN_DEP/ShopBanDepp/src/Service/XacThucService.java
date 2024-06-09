/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.TaiKhoanNV_QMK;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import javax.mail.MessagingException;
import untility.EmailSender;



/**
 *
 * @author 
 */
public class XacThucService {
    TaiKhoanNV_QMKService TaikhoanService;
    EmailSender emailSender;
    public XacThucService(){
        TaikhoanService = new  TaiKhoanNV_QMKService();
        emailSender = new EmailSender();
            
    }
     public boolean checkUesrandEmail (String TKNV , String email) throws SQLException{
        List<TaiKhoanNV_QMK> taiKhoans = TaikhoanService.layDSTaiKhoan();  
        for(TaiKhoanNV_QMK taiKhoan : taiKhoans){
            if(taiKhoan.getTKNV().equalsIgnoreCase(TKNV) && taiKhoan.getEmail().equals(email)){
                return true;
            }
        }
                return false;
    }
     public boolean checkUsernametotai(String TKNV) throws SQLException{
        List<TaiKhoanNV_QMK> taiKhoans = TaikhoanService.layDSTaiKhoan();
        for(TaiKhoanNV_QMK taiKhoan : taiKhoans){
            if(taiKhoan.getTKNV().equalsIgnoreCase(TKNV)){
                return true;
            }
        }
                return false;
    }
     public String taoPassmoi(){
        Random random = new Random();
        int number ;
        String result = "";
        for(int i = 0 ; i < 5 ; i++){
        number = random.nextInt(9);
        result += number;
    }
        String MatKhau = "NV" + result;
        return MatKhau;
    }
     public boolean capNhatPass(String username, String passUpdate) throws SQLException{
        return TaikhoanService.capNhapPass(username, passUpdate);
    }
    public void guiMail(String TKNV , String email) throws SQLException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        ZonedDateTime now = ZonedDateTime.now();
        String time = dtf.format(now);
        try {
            TaiKhoanNV_QMK taiKhoan = TaikhoanService.layTTTaiKhoanByUsername(TKNV);
            String title = "=--Yêu Cầu Đổi Mật Khẩu--=";
            String content = "| -BOSS DƯƠNG- |<br>"
                    + "bạn yêu cầu cấp lại mật khẩu của tài khoản: " + taiKhoan.getTKNV()+"Lúc: " + time + "<br>"
                    + "mât khẩu mới của bạn là :  " + taiKhoan.getMatKhau() + "<br>"
                    + "===========================================================================================<br>"
                    + "Lưu ý: Không để lộ hay cung cấp mật khẩu cho người khác!!!!!"
                    ;
            emailSender.guiMail(email, title, content);
        } catch (MessagingException ex) {
        }
    }
     
    
    
}
