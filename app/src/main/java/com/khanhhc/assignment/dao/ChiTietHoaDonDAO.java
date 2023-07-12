package com.khanhhc.assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.ChiTietHoaDon;
import com.khanhhc.assignment.model.HoaDon;

import java.util.ArrayList;

import static android.service.controls.ControlsProviderService.TAG;

public class ChiTietHoaDonDAO {
    private SQLiteDatabase database;

    public ChiTietHoaDonDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //insert
    public long insert(ChiTietHoaDon item){
        ContentValues values = new ContentValues();
        values.put("machitiethoadon", item.getMachitiethoadon());
        values.put("mahoadon",item.getMahoadon());
        values.put("masanpham",item.getMasanpham());
        values.put("soluong",item.getSoluong());

        return database.insert("chitiethoadon", null, values);
    }

    public int update(ChiTietHoaDon item){
        ContentValues values = new ContentValues();
        values.put("machitiethoadon", item.getMachitiethoadon());
        values.put("mahoadon",item.getMahoadon());
        values.put("masanpham",item.getMasanpham());
        values.put("soluong",item.getSoluong());

        return database.update("chitiethoadon", values, "machitiethoadon = ?", new String[]{item.getMachitiethoadon()});
    }

    public int delete(String machitiethoadon){
        return database.delete("chitiethoadon", "machitiethoadon = ?", new String[]{machitiethoadon});
    }

    // get nhieu tham so
    public ArrayList<ChiTietHoaDon> get(String sql, String...selectionArgs){
        ArrayList<ChiTietHoaDon> list = new ArrayList<ChiTietHoaDon>();
        Cursor c = database.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            String maChiTietHoaDon = c.getString(c.getColumnIndex("machitiethoadon"));
            String maHoaDon = c.getString(c.getColumnIndex("mahoadon"));
            String maSanPham = c.getString(c.getColumnIndex("masanpham"));
            String soLuong = c.getString(c.getColumnIndex("soluong"));
            String tenSanPham = c.getString(c.getColumnIndex("tensanpham"));
            String giaTien = c.getString(c.getColumnIndex("giatien"));
            ChiTietHoaDon item = new ChiTietHoaDon(maChiTietHoaDon, maHoaDon, maSanPham, soLuong, tenSanPham, giaTien);
            list.add(item);
        }
        return list;
    }

    public ArrayList<ChiTietHoaDon> getAllCTHD(String mahoadon){
        String sql = "SELECT chitiethoadon.*, hoadon.*, sanpham.* from chitiethoadon " +
                "inner join hoadon on chitiethoadon.mahoadon = hoadon.mahoadon " +
                "inner join sanpham on chitiethoadon.masanpham = sanpham.masanpham " +
                "WHERE chitiethoadon.mahoadon = ?;";
        return get(sql, new String[]{mahoadon});
    }

//    public ArrayList<ChiTietHoaDon> getThanhTien(String mahoadon){
//        //String sql = "SELECT * FROM chitiethoadon WHERE mahoadon = ?;";
//        String sql = "SELECT chitiethoadon.masanpham, chitiethoadon.mahoadon, sanpham.tensanpham, sanpham.giatien, chitiethoadon.soluong, (sanpham.giatien * chitiethoadon.soluong) as 'thanhtien' from chitiethoadon \n" +
//                "inner join sanpham on sanpham.masanpham = chitiethoadon.masanpham\n" +
//                "inner join hoadon on hoadon.mahoadon = chitiethoadon.mahoadon\n" +
//                "WHERE chitiethoadon.mahoadon = ?";
//        return get(sql, new String[]{mahoadon});
//    }


    public ArrayList<ChiTietHoaDon> getAll(String mahoadon){
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT chitiethoadon.*, hoadon.*, sanpham.*, sum(sanpham.giatien * chitiethoadon.soluong) " +
                "from chitiethoadon " +
                "inner join hoadon on chitiethoadon.mahoadon = hoadon.mahoadon " +
                "inner join sanpham on chitiethoadon.masanpham = sanpham.masanpham " +
                "WHERE chitiethoadon.mahoadon = '"+mahoadon+"' GROUP BY chitiethoadon.mahoadon;";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        try{
            while (cursor.isAfterLast() == false){
                ChiTietHoaDon item = new ChiTietHoaDon();
                item.setMachitiethoadon(cursor.getString(0));
                item.setMahoadon(cursor.getString(1));
                item.setMasanpham(cursor.getString(2));
                item.setSoluong(cursor.getString(3));
                item.setTensanpham(cursor.getString(10));
                item.setGiatien(cursor.getString(11));
                item.setThanhTien(cursor.getDouble(15));
                list.add(item);
                cursor.moveToNext();
            }
            cursor.close();
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }
        return list;
    }

    public double getDoanhThu(String tuNgay, String denNgay){
        double doanhThu = 0;
        String sql = "SELECT sum(sanpham.giatien * chitiethoadon.soluong) FROM chitiethoadon\n" +
                "inner join hoadon on chitiethoadon.mahoadon = hoadon.mahoadon\n" +
                "inner join sanpham on sanpham.masanpham = chitiethoadon.masanpham\n" +
                "where ngay BETWEEN '"+tuNgay+"' AND '"+denNgay+"' AND trangthai = 'DTT';";

        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            doanhThu = cursor.getDouble(0);
            cursor.moveToNext();
        }
        cursor.close();
        return doanhThu;
    }

}
