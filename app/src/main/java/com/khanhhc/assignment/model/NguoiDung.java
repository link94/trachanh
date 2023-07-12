package com.khanhhc.assignment.model;

public class NguoiDung {
    private String manguoidung;
    private String tendangnhap;
    private String matkhau;
    private String tennguoidung;
    private String sodienthoai;
    private String email;
    private String gioitinh;
    private String diachi;
    private String chucvu;

    public NguoiDung(String manguoidung, String tendangnhap, String matkhau, String tennguoidung, String sodienthoai, String email, String gioitinh, String diachi, String chucvu) {
        this.manguoidung = manguoidung;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.tennguoidung = tennguoidung;
        this.sodienthoai = sodienthoai;
        this.email = email;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.chucvu = chucvu;
    }

    public NguoiDung(String manguoidung, String matkhau) {
        this.manguoidung = manguoidung;
        this.matkhau = matkhau;
    }

    public String getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(String manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }
}
