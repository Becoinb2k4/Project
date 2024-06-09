/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author 
 */
public class ThongKe {
    private String ngayTao;
    private String tongTien;

    public ThongKe() {
    }

    public ThongKe(String ngayTao, String tongTien) {
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
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
    
}
