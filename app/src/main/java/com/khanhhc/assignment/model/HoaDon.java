package com.khanhhc.assignment.model;

public class HoaDon {
    private String mahoadon;
    private String ngay;
    private String soban;
    private String makhachhang;
    private  String trangthai;

    public HoaDon(String mahoadon, String ngay, String soban, String makhachhang, String trangthai) {
        this.mahoadon = mahoadon;
        this.ngay = ngay;
        this.soban = soban;
        this.makhachhang = makhachhang;
        this.trangthai = trangthai;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getSoban() {
        return soban;
    }

    public void setSoban(String soban) {
        this.soban = soban;
    }

    public String getMakhachhang() {
        return makhachhang;
    }

    public void setMakhachhang(String makhachhang) {
        this.makhachhang = makhachhang;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
