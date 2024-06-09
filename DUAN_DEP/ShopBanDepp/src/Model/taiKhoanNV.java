/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 
 */
public class taiKhoanNV {
    private String tkNV;
    private String tenNV;
    private String matKhau;

    public taiKhoanNV() {
    }

    public taiKhoanNV(String tkNV, String tenNV, String matKhau) {
        this.tkNV = tkNV;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
    }

    public String getTkNV() {
        return tkNV;
    }

    public void setTkNV(String tkNV) {
        this.tkNV = tkNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
}
