package com.khanhhc.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.KhachHang;
import com.khanhhc.assignment.model.NguoiDung;

import java.util.ArrayList;

import static android.service.controls.ControlsProviderService.TAG;

public class KhachHangDAO {
    private SQLiteDatabase database;
    public KhachHangDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

//    //insert
//    public long insert(NguoiDung item){
//        ContentValues values = new ContentValues();
//        values.put("manguoidung", item.getManguoidung());
//        values.put("tendangnhap", item.getTendangnhap());
//        values.put("matkhau", item.getMatkhau());
//        values.put("tennguoidung", item.getTennguoidung());
//        values.put("sodienthoai", item.getSodienthoai());
//        values.put("email", item.getEmail());
//        values.put("gioitinh", item.getGioitinh());
//        values.put("diachi", item.getDiachi());
//        values.put("chucvu", item.getChucvu());
//
//        return database.insert("nguoidung", null, values);
//    }
//
//    //update
//    public int update(NguoiDung item){
//        ContentValues values = new ContentValues();
//        values.put("manguoidung", item.getManguoidung());
//        values.put("tendangnhap", item.getTendangnhap());
//        values.put("matkhau", item.getMatkhau());
//        values.put("tennguoidung", item.getTennguoidung());
//        values.put("sodienthoai", item.getSodienthoai());
//        values.put("email", item.getEmail());
//        values.put("gioitinh", item.getGioitinh());
//        values.put("diachi", item.getDiachi());
//        values.put("chucvu", item.getChucvu());
//
//        return database.update("nguoidung", values, "manguoidung = ?", new String[]{item.getManguoidung()});
//    }
//    public int updatePass(NguoiDung item){
//        ContentValues values = new ContentValues();
//        values.put("matkhau", item.getMatkhau());
//
//        return database.update("nguoidung", values, "manguoidung = ?", new String[]{item.getManguoidung()});
//    }

    // get nhieu tham so
//    public ArrayList<NguoiDung> get(String sql, String...selectionArgs){
//        ArrayList<NguoiDung> list = new ArrayList<NguoiDung>();
//        Cursor c = database.rawQuery(sql, selectionArgs);
//        while (c.moveToNext()){
//            String maNguoidung = c.getString(c.getColumnIndex("manguoidung"));
//            String tenDangNhap = c.getString(c.getColumnIndex("tendangnhap"));
//            String matKhau = c.getString(c.getColumnIndex("matkhau"));
//            String tennguoidung = c.getString(c.getColumnIndex("tennguoidung"));
//            String sodienthoai = c.getString(c.getColumnIndex("sodienthoai"));
//            String email = c.getString(c.getColumnIndex("email"));
//            String gioiTinh = c.getString(c.getColumnIndex("gioitinh"));
//            String diaChi = c.getString(c.getColumnIndex("diachi"));
//            String chucvu = c.getString(c.getColumnIndex("chucvu"));
//            NguoiDung item = new NguoiDung(maNguoidung, tenDangNhap, matKhau, tennguoidung, sodienthoai, email, gioiTinh, diaChi, chucvu);
//            list.add(item);
//        }
//        return list;
//    }
//
//    public static boolean checkLogin(Context context, String id, String password){
//        DbHelper helper = new DbHelper(context);
//        //tao co so du lieu sqlite
//        SQLiteDatabase db = helper.getReadableDatabase();
//        String sql = "SELECT * FROM nguoidung WHERE tendangnhap LIKE '"+id+"' AND matkhau LIKE '"+password+"'";
//
//        Cursor cs = db.rawQuery(sql , null);
//        if(cs.getCount() <= 0){
//            return false;
//        }
//        cs.close();
//        return true;
//    }
//
//    public ArrayList<NguoiDung> getAllND(){
//        String sql = "SELECT * FROM nguoidung";
//        return get(sql);
//    }
    public ArrayList<KhachHang> getAllKH(){
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khachhang;";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        try{
            while (cursor.isAfterLast() == false){
                int makhachhang = cursor.getInt(0);
                String ten = cursor.getString(1);
                String diachi = cursor.getString(2);
                String email = cursor.getString(3);
                String sdt = cursor.getString(4);
                KhachHang item = new KhachHang(makhachhang, ten, diachi, email, sdt);
                list.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }
        return list;
    }
}
