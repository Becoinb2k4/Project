/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 
 */
public class SanPhamfull {

    private String MaSP;
    private String TenSP;
    private String Mota;
    private String DonGia;
    private String GiamGia;
    private String TrangThai;
    private String SoLuong;
    private String ChatLieu;
    private String KichThuoc;
    private String Mau;
    private String NhaSX;
    private String XuatXu;

    public SanPhamfull() {
    }

    public SanPhamfull(String MaSP, String TenSP, String Mota, String DonGia, String GiamGia, String TrangThai, String SoLuong, String ChatLieu, String KichThuoc, String Mau, String NhaSX, String XuatXu) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.Mota = Mota;
        this.DonGia = DonGia;
        this.GiamGia = GiamGia;
        this.TrangThai = TrangThai;
        this.SoLuong = SoLuong;
        this.ChatLieu = ChatLieu;
        this.KichThuoc = KichThuoc;
        this.Mau = Mau;
        this.NhaSX = NhaSX;
        this.XuatXu = XuatXu;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    public String getDonGia() {
        return DonGia;
    }

    public String getDonGia2() {
        return DonGia.substring(0, DonGia.length() - 5);
    }

    public void setDonGia(String DonGia) {
        this.DonGia = DonGia;
    }

    public String getGiamGia() {
        return GiamGia;
    }

    public void setGiamGia(String GiamGia) {
        this.GiamGia = GiamGia;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public String getTrangThai2() {
        return (Integer.parseInt(TrangThai)>0)?"Còn hàng":"Hết hàng";
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getChatLieu() {
        return ChatLieu;
    }

    public void setChatLieu(String ChatLieu) {
        this.ChatLieu = ChatLieu;
    }

    public String getKichThuoc() {
        return KichThuoc;
    }

    public void setKichThuoc(String KichThuoc) {
        this.KichThuoc = KichThuoc;
    }

    public String getMau() {
        return Mau;
    }

    public void setMau(String Mau) {
        this.Mau = Mau;
    }

    public String getNhaSX() {
        return NhaSX;
    }

    public void setNhaSX(String NhaSX) {
        this.NhaSX = NhaSX;
    }

    public String getXuatXu() {
        return XuatXu;
    }

    public void setXuatXu(String XuatXu) {
        this.XuatXu = XuatXu;
    }

    public Object[] toDataRowSP() {
        return new Object[]{
            this.MaSP, this.TenSP, this.Mota, this.getDonGia2(), this.GiamGia, this.getTrangThai2(), this.SoLuong,
            this.ChatLieu, this.KichThuoc, this.Mau, this.NhaSX, this.XuatXu
        };
    }
}
