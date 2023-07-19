package com.khanhhc.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.ChiTietHoaDon;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

import static android.service.controls.ControlsProviderService.TAG;

public class HoaDonDAO {
    private SQLiteDatabase database;

    public HoaDonDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //insert
    public long insert(HoaDon item){
        ContentValues values = new ContentValues();
        values.put("mahoadon", item.getMahoadon());
        values.put("ngay",item.getNgay());
        values.put("soban",item.getSoban());
        values.put("makhachhang",item.getMakhachhang());
        values.put("trangthai", item.getTrangthai());

        return database.insert("hoadon", null, values);
    }

    public int update(HoaDon item){
        ContentValues values = new ContentValues();
        values.put("mahoadon", item.getMahoadon());
        values.put("ngay",item.getNgay());
        values.put("soban",item.getSoban());
        values.put("makhachhang",item.getMakhachhang());
        values.put("trangthai", item.getTrangthai());

        return database.update("hoadon", values, "mahoadon = ?", new String[]{item.getMahoadon()});
    }

    public int delete(String mahoadon){
        return database.delete("hoadon", "mahoadon = ?", new String[]{mahoadon});
    }

    // get nhieu tham so
    public ArrayList<HoaDon> get(String sql, String...selectionArgs){
        ArrayList<HoaDon> list = new ArrayList<HoaDon>();
        Cursor c = database.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            String maHoaDon = c.getString(c.getColumnIndex("mahoadon"));
            String ngay = c.getString(c.getColumnIndex("ngay"));
            String soBan = c.getString(c.getColumnIndex("soban"));
            String makhachhang = c.getString(c.getColumnIndex("makhachhang"));
            String trangThai = c.getString(c.getColumnIndex("trangthai"));
            HoaDon item = new HoaDon(maHoaDon, ngay, soBan, makhachhang, trangThai);
            list.add(item);
        }
        return list;
    }

    //get 1 hoa don
    public HoaDon getHD(String mahoadon) {
        HoaDon hoaDon = null;
        String sql = "SELECT * FROM hoadon WHERE mahoadon = ?";
        String[] selectionArgs = { mahoadon };
        Cursor c = database.rawQuery(sql, selectionArgs);
        if (c.moveToFirst()) {
            String maHoaDon = c.getString(c.getColumnIndex("mahoadon"));
            String ngay = c.getString(c.getColumnIndex("ngay"));
            String soBan = c.getString(c.getColumnIndex("soban"));
            String makhachhang = c.getString(c.getColumnIndex("makhachhang"));
            String trangThai = c.getString(c.getColumnIndex("trangthai"));
            hoaDon = new HoaDon(maHoaDon, ngay, soBan, makhachhang, trangThai);
        }
        return hoaDon;
    }


    //get all menu theo loai san pham
    public ArrayList<HoaDon> getAllHD(){
        String sql = "SELECT *FROM hoadon;";
        return get(sql);
    }

    public ArrayList<HoaDon> getAllTrangThai(String mahoadon){
        String sql = "SELECT * FROM chitiethoadon\n" +
                "    inner join hoadon on chitiethoadon.mahoadon = hoadon.mahoadon\n" +
                "    where chitiethoadon.mahoadon = ?";
        return get(sql, new String[]{mahoadon});
    }

    public ArrayList<HoaDon> getAllHoaDonDaThanhToan(String ngaybd, String ngayKT){
        ArrayList<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * from hoadon WHERE ngay BETWEEN '"+ngaybd+"' AND '"+ngayKT+"' and trangthai = 'DTT';";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        try{
            while (cursor.isAfterLast() == false){
                String mahoadon = cursor.getString(0);
                String ngay = cursor.getString(1);
                String soban = cursor.getString(2);
                String trangthai = cursor.getString(3);
                String makhachhang = cursor.getString(4);
                HoaDon item = new HoaDon(mahoadon, ngay, soban, trangthai, makhachhang);
                list.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG, "getAllHoaDonDaThanhToan: ");
        }
        return list;
    }


}
