package com.khanhhc.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.SanPham;

import java.sql.Blob;
import java.util.ArrayList;

import static android.service.controls.ControlsProviderService.TAG;

public class MenuDAO {
    private SQLiteDatabase database;

    public MenuDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //insert
    public long insert(SanPham item){
        ContentValues values = new ContentValues();
        values.put("masanpham", item.getMasanpham());
        values.put("tensanpham",item.getTensanpham());
        values.put("giatien",item.getGiatien());
        values.put("mota",item.getMota());
        values.put("hinhanh", item.getHinhanh());
        values.put("maloai",item.getMaloai());

        return database.insert("sanpham", null, values);
    }

    public int update(SanPham item){
        ContentValues values = new ContentValues();
        values.put("masanpham", item.getMasanpham());
        values.put("tensanpham",item.getTensanpham());
        values.put("giatien",item.getGiatien());
        values.put("mota",item.getMota());
        values.put("hinhanh", item.getHinhanh());
        values.put("maloai",item.getMaloai());

        return database.update("sanpham", values, "masanpham = ?", new String[]{item.getMasanpham()});
    }

    public int delete(String masanpham){
        return database.delete("sanpham", "masanpham = ?", new String[]{masanpham});
    }

    // get nhieu tham so
    public ArrayList<SanPham> get(String sql, String...selectionArgs){
        ArrayList<SanPham> list = new ArrayList<SanPham>();
        Cursor c = database.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            String maSanPham = c.getString(c.getColumnIndex("masanpham"));
            String tenSanPham = c.getString(c.getColumnIndex("tensanpham"));
            String giatien = c.getString(c.getColumnIndex("giatien"));
            String mota = c.getString(c.getColumnIndex("mota"));
            String maloai = c.getString(c.getColumnIndex("maloai"));
            byte[] hinhanh = c.getBlob(c.getColumnIndex("hinhanh"));
            SanPham item = new SanPham(maSanPham, tenSanPham, giatien, mota, maloai, hinhanh);
            list.add(item);
        }
        return list;
    }
    //get all menu theo loai san pham
    public ArrayList<SanPham> getAllMenu(){
        String sql = "SELECT *FROM sanpham;";
        return get(sql);
    }

    public ArrayList<SanPham> getAllMenuSpiner(String maloai){
        String sql = "SELECT *FROM sanpham WHERE maloai = ?;";
        return get(sql, new String[] {maloai});
    }

    public String getAllTenSP(){
        String tenSP = null;
        String sql = "SELECT tensanpham FROM sanpham;";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

            while (cursor.isAfterLast() == false){
                tenSP = cursor.getString(0);
                cursor.moveToNext();
            }
            cursor.close();
        return tenSP;

    }

    public ArrayList<SanPham> getTopSP(String from, String to){
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "select sanpham.*, sum(chitiethoadon.soluong) as 'soluong' from chitiethoadon \n" +
                "INNER join sanpham on sanpham.masanpham = chitiethoadon.masanpham\n" +
                "INNER join hoadon on hoadon.mahoadon = chitiethoadon.mahoadon\n" +
                "where ngay BETWEEN '"+from+"' AND '"+to+"' AND trangthai = 'DTT' GROUP BY sanpham.masanpham " +
                "ORDER BY soluong desc\n" +
                "LIMIT 3";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();

        try{
            while (cursor.isAfterLast() == false){
                String masanpham = cursor.getString(0);
                String ten = cursor.getString(1);
                String giatien = cursor.getString(2);
                String mota = cursor.getString(3);
                byte[] hinhanh = cursor.getBlob(4);
                String maloai = cursor.getString(5);
                String soluong = cursor.getString(6);// cot nay la tong so luong san pham ban chay
                SanPham item = new SanPham(masanpham, ten, giatien, mota, maloai, hinhanh);
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
