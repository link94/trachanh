package com.khanhhc.assignment.model;

public class ChiTietHoaDon {
    private String machitiethoadon;
    private String mahoadon;
    private String masanpham;
    private String soluong;
    private String tensanpham;
    private String giatien;
    private double thanhTien;

    public ChiTietHoaDon(String machitiethoadon, String mahoadon, String masanpham, String soluong, String tensanpham, String giatien) {
        this.machitiethoadon = machitiethoadon;
        this.mahoadon = mahoadon;
        this.masanpham = masanpham;
        this.soluong = soluong;
        this.tensanpham = tensanpham;
        this.giatien = giatien;
    }

    public ChiTietHoaDon() {
    }

    public String getMachitiethoadon() {
        return machitiethoadon;
    }

    public void setMachitiethoadon(String machitiethoadon) {
        this.machitiethoadon = machitiethoadon;
    }

    public String getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(String mahoadon) {
        this.mahoadon = mahoadon;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }


    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
