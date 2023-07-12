package com.khanhhc.assignment.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.KhachHangDAO;
import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.model.KhachHang;
import com.khanhhc.assignment.model.NguoiDung;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.RecyclerHolder> {
    Activity context;
    ArrayList<KhachHang> listKH;
    KhachHangDAO dao;
    String chucvu;

    public KhachHangAdapter(Activity context, ArrayList<KhachHang> listKH) {
        this.context = context;
        this.listKH = listKH;
    }


    @NonNull
    @Override
    public KhachHangAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_khachhang, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangAdapter.RecyclerHolder holder, final int position) {
            holder.tvHoTenKhachHang.setText(listKH.get(position).getTenkhachhang());
            holder.tvSDTKhachHang.setText(listKH.get(position).getSodienthoai());
    }

    @Override
    public int getItemCount() {
            return listKH.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        CardView  khachhang_item;
        TextView tvHoTenKhachHang, tvSDTKhachHang;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
                khachhang_item = itemView.findViewById(R.id.khachhang_item);
                tvHoTenKhachHang = itemView.findViewById(R.id.tvHoTenKhacHang);
                tvSDTKhachHang = itemView.findViewById(R.id.tvSDTKhachHang);
        }
    }

}
