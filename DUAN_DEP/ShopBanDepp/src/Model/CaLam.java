/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 
 */
public class CaLam {

    private String Ca;
    private String MaNV;
    private String TenNV;
    private String GioBatDau;
    private String GioKetThuc;
    private String TKNV;

    public CaLam() {
    }

    public CaLam(String Ca, String MaNV, String TenNV, String GioBatDau, String GioKetThuc, String TKNV) {
        this.Ca = Ca;
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.GioBatDau = GioBatDau;
        this.GioKetThuc = GioKetThuc;
   
        this.TKNV = TKNV;
    }

    public String getCa() {
        return Ca;
    }

    public void setCa(String Ca) {
        this.Ca = Ca;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getGioBatDau() {
        return GioBatDau;
    }

    public void setGioBatDau(String GioBatDau) {
        this.GioBatDau = GioBatDau;
    }

    public String getGioKetThuc() {
        return GioKetThuc;
    }

    public void setGioKetThuc(String GioKetThuc) {
        this.GioKetThuc = GioKetThuc;
    }

    public String getTKNV() {
        return TKNV;
    }

    public void setTKNV(String TKNV) {
        this.TKNV = TKNV;
    }
    
    
}