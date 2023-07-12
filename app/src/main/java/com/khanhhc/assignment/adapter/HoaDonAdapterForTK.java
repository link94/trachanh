package com.khanhhc.assignment.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.HoaDonDAO;
import com.khanhhc.assignment.fragment.ChiTietHoaDonFragment;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class HoaDonAdapterForTK extends RecyclerView.Adapter<HoaDonAdapterForTK.RecyclerHolder> {
    Activity context;
    ArrayList<HoaDon> listHD;
    HoaDonDAO dao;
    ArrayList<SanPham> listSP;
    public static String mahoadon =null;
    public static String ngay = null;

    public HoaDonAdapterForTK(Activity context, ArrayList<HoaDon> listHD) {
        this.context = context;
        this.listHD = listHD;
    }


    @NonNull
    @Override
    public HoaDonAdapterForTK.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_hoadon, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull final HoaDonAdapterForTK.RecyclerHolder holder, final int position) {

            listSP = new ArrayList<SanPham>();
            holder.tvMaHD.setText("Mã hóa đơn: "+listHD.get(position).getMahoadon());
            holder.tvThoiGian.setText("Thời gian: "+listHD.get(position).getNgay());
            holder.tvSoBan.setText("Số bàn: "+listHD.get(position).getSoban());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mahoadon = listHD.get(position).getMahoadon();
                    ngay = listHD.get(position).getNgay();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new ChiTietHoaDonFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, myFragment).addToBackStack(null).commit();
                }
            });
    }

    @Override
    public int getItemCount() {
            return listHD.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        CardView  hoadon_item;
        TextView tvMaHD, tvThoiGian, tvSoBan;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
                hoadon_item = itemView.findViewById(R.id.hoadon_item);
                tvMaHD = itemView.findViewById(R.id.tvMaHD);
                tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
                tvSoBan = itemView.findViewById(R.id.tvSoBan);
        }
    }

}
