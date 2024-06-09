/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 
 */
public class khachHang {
    private String maKH;
    private String tenKh;
    private String gioiTinh;
    private String sdt;
    private String email;
    private String loaiKH;
    private String tongChiTieu;

    public khachHang() {
    }

    public khachHang(String maKH, String tenKh, String gioiTinh,String sdt, String email, String loaiKH, String tongChiTieu) {
        this.maKH = maKH;
        this.tenKh = tenKh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.email = email;
        this.loaiKH = loaiKH;
        this.tongChiTieu = tongChiTieu;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoaiKH() {
        return loaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        this.loaiKH = loaiKH;
    }

    public String getTongChiTieu() {
        return tongChiTieu;
    }

    public void setTongChiTieu(String tongChiTieu) {
        this.tongChiTieu = tongChiTieu;
    }
    
    public Object [] todataRow(){
        return  new Object[]{
            this.maKH,this.tenKh,this.gioiTinh,this.sdt,this.email,this.loaiKH
        };
    }
}
