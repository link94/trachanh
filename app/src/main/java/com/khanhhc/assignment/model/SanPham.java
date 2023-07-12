package com.khanhhc.assignment.model;


public class SanPham {
    private String masanpham;
    private String tensanpham;
    private String giatien;
    private String mota;
    private String maloai;
    private byte[] hinhanh;


    public SanPham(String masanpham, String tensanpham, String giatien, String mota, String maloai, byte[] hinhanh) {
        this.masanpham = masanpham;
        this.tensanpham = tensanpham;
        this.giatien = giatien;
        this.mota = mota;
        this.maloai = maloai;
        this.hinhanh = hinhanh;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getMaloai() {
        return maloai;
    }

    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }
}
