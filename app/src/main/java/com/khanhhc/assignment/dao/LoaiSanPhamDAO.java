package com.khanhhc.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.LoaiSanPham;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class LoaiSanPhamDAO {
    private SQLiteDatabase database;

    public LoaiSanPhamDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //insert
    public long insert(LoaiSanPham item){
        ContentValues values = new ContentValues();
        values.put("maloai", item.getMaloai());
        values.put("tenloai", item.getTenloai());

        return database.insert("loaisanpham", null, values);
    }

    public int update(LoaiSanPham item){
        ContentValues values = new ContentValues();
        values.put("maloai", item.getMaloai());
        values.put("tenloai", item.getTenloai());

        return database.update("loaisanpham", values, "maloai = ?", new String[]{item.getMaloai()+""});
    }

    public int delete(String item){
        return database.delete("loaisanpham", "maloai = ?", new String[]{item});
    }

    // get nhieu tham so
    public ArrayList<LoaiSanPham> get(String sql, String...selectionArgs){
        ArrayList<LoaiSanPham> list = new ArrayList<LoaiSanPham>();
        Cursor c = database.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            String maLoai = c.getString(c.getColumnIndex("maloai"));
            String tenLoai = c.getString(c.getColumnIndex("tenloai"));
            LoaiSanPham item = new LoaiSanPham(maLoai, tenLoai);
            list.add(item);
        }
        return list;
    }

    public ArrayList<LoaiSanPham> getAllLoaiSP(){
        String sql = "SELECT * FROM loaisanpham;";
        return get(sql);
    }
}
