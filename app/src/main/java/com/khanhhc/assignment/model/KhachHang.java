package com.khanhhc.assignment.model;

public class KhachHang {
    private int makhachhang;
    private String tenkhachhang;
    private String diachi;
    private String email;
    private String sodienthoai;

    public KhachHang(int makhachhang, String tenkhachhang, String diachi, String email, String sodienthoai) {
        this.makhachhang = makhachhang;
        this.tenkhachhang = tenkhachhang;
        this.diachi = diachi;
        this.email = email;
        this.sodienthoai = sodienthoai;
    }

    public int getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(int makhachhang) {
        this.makhachhang = makhachhang;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }
}
