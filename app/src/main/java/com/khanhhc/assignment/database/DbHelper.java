package com.khanhhc.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static String maNguoiDung = null;
    public static String tenDangNhap = null;
    public static String matKhau = null;
    public static String tenNguoiDung = null;
    public static String phone = null;
    public static String email = null;
    public static String diaChi = null;
    public static String sex = null;
    public static String chucvu = null;

    public DbHelper(@Nullable Context context) {
        super(context, "quanlycafe", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE loaisanpham (" +
                "maloai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenloai TEXT);";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE sanpham (" +
                "masanpham INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tensanpham TEXT," +
                "giatien TEXT," +
                "mota TEXT," +
                "hinhanh BLOB," +
                "maloai INTEGER REFERENCES loaisanpham(maloai));";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE khachhang (" +
                "makhachhang INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenkhachhang TEXT," +
                "diachi TEXT," +
                "email TEXT," +
                "sodienthoai TEXT);";
        sqLiteDatabase.execSQL(sql);

        //
        sql = "CREATE TABLE hoadon (" +
                "mahoadon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ngay TEXT," +
                "soban TEXT," +
                "trangthai TEXT," +
                "makhachhang INTEGER REFERENCES khachhang(makhachhang));";
        sqLiteDatabase.execSQL(sql);
        //
        sql = "CREATE TABLE chitiethoadon (" +
                "machitiethoadon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mahoadon INTEGER," +
                "masanpham INTEGER," +
                "soluong INTEGER," +
                "CONSTRAINT FK_PM_CTHD foreign key(masanpham) references sanpham(masanpham)," +
                "CONSTRAINT FK_PM_CTHD foreign key(mahoadon) references hoadon(mahoadon));";
        sqLiteDatabase.execSQL(sql);
        //
        sql = "CREATE TABLE nguoidung(" +
                "manguoidung INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tendangnhap TEXT," +
                "matkhau TEXT," +
                "tennguoidung TEXT," +
                "sodienthoai TEXT," +
                "email TEXT," +
                "gioitinh TEXT," +
                "diachi TEXT," +
                "chucvu TEXT);";
        sqLiteDatabase.execSQL(sql);

        // nhap du lieu cung vao database

        // nhap bang loai san pham
        sql = "INSERT INTO loaisanpham VALUES (null, 'Tất cả sản phẩm')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO loaisanpham VALUES (null, 'CAFE')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO loaisanpham VALUES (null, 'Trà Chanh')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO loaisanpham VALUES (null, 'Trà Đào')";
        sqLiteDatabase.execSQL(sql);

        // nhap bang sanpham
        sql = "INSERT INTO sanpham VALUES (null, 'CHOCOLATE', '45', 'Socola nóng','null', 2)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO sanpham VALUES (null, 'SNOW PINKY', '48', 'Chanh tuyết + dâu','null', 3)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO sanpham VALUES (null, 'LATTE', '50', 'latte','null', 2)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO sanpham VALUES (null, 'Trà Chanh Truyền Thống', '20', 'Chanh + trà','null', 3)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO sanpham VALUES (null, 'Trà Đào', '30', 'Đào + trà','null', 4)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO sanpham VALUES (null, 'Trà Đào Cam Sả', '35', 'Đào + tra + cam + Sả̀','null', 4)";
        sqLiteDatabase.execSQL(sql);

        // nhap du lieu bang khach hang
        sql = "INSERT INTO khachhang VALUES (null, 'Nguyễn Ngọc Hải', '41 Phố Vọng', 'nguyenngochai@gmail.com', '0123456789')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO khachhang VALUES (null, 'Nguyễn Đoàn Khánh', '50 Triều Khúc', 'nguyendoankhanh@gmail.com', '0123456777')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO khachhang VALUES (null, 'Vũ Nhật Linh', '91 Hoàng Mai', 'vunhatlinh04092001@gmail.com', '0375020889')";
        sqLiteDatabase.execSQL(sql);

        // nhap bang hoadon
        sql = "INSERT INTO hoadon VALUES (null, '2023/07/18', '1', null,1)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO hoadon VALUES (null, '2023/07/18', '2', null, 2)";
        sqLiteDatabase.execSQL(sql);

        // nhap bang chi tiet hoa don
        sql = "INSERT INTO chitiethoadon VALUES (null, 1, 2, 2)";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO chitiethoadon VALUES (null, 2, 1, 3)";
        sqLiteDatabase.execSQL(sql);

        // nhap bang nguoi dung
        sql = "INSERT INTO nguoidung VALUES (null, 'admin', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin', 'admin')";
        sqLiteDatabase.execSQL(sql);
        sql = "INSERT INTO nguoidung VALUES (null, 'linh.vn', '123', 'Vu Nhat Linh', '0375020889', 'vunhatlinh04092001@gmail.com', 'Nam', '91 Hoang Mai', 'user')";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE khachhang;");
        sqLiteDatabase.execSQL("DROP TABLE loaisanpham;");
        sqLiteDatabase.execSQL("DROP TABLE sanpham;");
        sqLiteDatabase.execSQL("DROP TABLE hoadon;");
        sqLiteDatabase.execSQL("DROP TABLE chitiethoadon;");
        sqLiteDatabase.execSQL("DROP TABLE nguoidung;");

        onCreate(sqLiteDatabase);
    }
}
