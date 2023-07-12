package com.khanhhc.assignment.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.model.ChiTietHoaDon;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.RecyclerHolder> {
    Activity context;
    ArrayList<ChiTietHoaDon> listCTHD;

    public CartAdapter(Activity context, ArrayList<ChiTietHoaDon> listCTHD) {
        this.context = context;
        this.listCTHD = listCTHD;
    }


    @NonNull
    @Override
    public CartAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_chitiethoadon, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.RecyclerHolder holder, final int position) {
            holder.tvTenSanPham.setText(listCTHD.get(position).getTensanpham());
            holder.tvSoluong.setText(listCTHD.get(position).getSoluong());
            holder.tvGiatien.setText(listCTHD.get(position).getGiatien());

    }

    @Override
    public int getItemCount() {
            return listCTHD.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        CardView chitiethoadon_item;
        TextView tvTenSanPham, tvSoluong, tvGiatien;//text view layout item sanpham

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
                chitiethoadon_item = itemView.findViewById(R.id.chitiethoadon_item);
                tvSoluong = itemView.findViewById(R.id.tvSoLuong);
                tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
                tvGiatien = itemView.findViewById(R.id.tvGiaTien);
        }
    }

}
