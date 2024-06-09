/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class KhuyenMai {

    private String MaKM;
    private String TenKM;
    private Date NgayBatDau;
    private Date NgayKetThuc;
    private String MoTa;
    private String MucKhuyenMai;
    private String TrangThai;
    public KhuyenMai() {
    }

    public KhuyenMai(String MaKM, String TenKM, Date NgayBatDau, Date NgayKetThuc, String MoTa, String MucKhuyenMai, String TrangThai) {
        this.MaKM = MaKM;
        this.TenKM = TenKM;
        this.NgayBatDau = NgayBatDau;
        this.NgayKetThuc = NgayKetThuc;
        this.MoTa = MoTa;
        this.MucKhuyenMai = MucKhuyenMai;
        this.TrangThai = TrangThai;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String MaKM) {
        this.MaKM = MaKM;
    }

    public String getTenKM() {
        return TenKM;
    }

    public void setTenKM(String TenKM) {
        this.TenKM = TenKM;
    }

    public Date getNgayBatDau() {
        return NgayBatDau;
    }

    public void setNgayBatDau(Date NgayBatDau) {
        this.NgayBatDau = NgayBatDau;
    }

    public Date getNgayKetThuc() {
        return NgayKetThuc;
    }

    public void setNgayKetThuc(Date NgayKetThuc) {
        this.NgayKetThuc = NgayKetThuc;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public String getMucKhuyenMai() {
        return MucKhuyenMai;
    }

    public void setMucKhuyenMai(String MucKhuyenMai) {
        this.MucKhuyenMai = MucKhuyenMai;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    

   
   
    public Object[]toDataRow(){
        return new Object[]{
            this.MaKM,this.TenKM,this.NgayBatDau,this.NgayKetThuc,this.MucKhuyenMai,this.MoTa,this.TrangThai
        };
    
}
}
