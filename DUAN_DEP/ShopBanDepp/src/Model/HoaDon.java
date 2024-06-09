/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 
 */
public class HoaDon {
    private String maHD;
    private String maHDCT;
    private String maKH;
    private String tenKH;
    private String maSP;
    private String tenSP;
    private String donGia;
    private String soLuong;
    private String ghiChu;
    private String ngayTao;
    private String tongTien;
    private String maNV;

    public HoaDon() {
    }

    public HoaDon(String maHD, String maHDCT, String maKH, String tenKH, String maSP, String tenSP, String donGia, String soLuong, String ghiChu, String ngayTao, String tongTien,String maNV) {
        this.maHD = maHD;
        this.maHDCT = maHDCT;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.maNV = maNV;
    }

    public HoaDon(String maHD, String maHDCT, String tenKH, String maSP, String tenSP, String donGia, String soLuong, String ghiChu) {
        this.maHD = maHD;
        this.maHDCT = maHDCT;
        this.tenKH = tenKH;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.ghiChu = ghiChu;
    }

    public HoaDon(String maHD, String ngayTao, String tongTien,String maNV) {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.maNV = maNV;
    }

    public HoaDon(String maSP, String soLuong, String tongTien) {
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public String getTongTien() {
        return tongTien;
    }

    public String getTongTien2() {
        return tongTien.substring(0, tongTien.length()-5);
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

   

   
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(String maHDCT) {
        this.maHDCT = maHDCT;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonGia2() {
        return donGia.substring(0, donGia.length()-5);
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    public Object[] toDataRowHD(){
        return new Object[]{
            this.maHD,this.ngayTao,this.getTongTien2(),this.getMaNV()
        };
    }
    public Object[] toDataRowHDCT(){
        return new Object[]{
            this.maHDCT,this.maHD,this.tenKH,this.maSP,this.tenSP,this.getDonGia2(),this.soLuong,this.ghiChu
        };
    }
    
    public int tongTienInt(){
        return Integer.parseInt(tongTien);
    }
}
