/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 
 */
public class TaiKhoanNV_QMK {
    private String TKNV;
    private String tenNV;
    private String matKhau;
    private String Email;

    public TaiKhoanNV_QMK() {
    }

    public TaiKhoanNV_QMK(String TKNV, String tenNV, String matKhau, String Email) {
        this.TKNV = TKNV;
        this.tenNV = tenNV;
        this.matKhau = matKhau;
        this.Email = Email;
    }

    public String getTKNV() {
        return TKNV;
    }

    public void setTKNV(String TKNV) {
        this.TKNV = TKNV;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

}
