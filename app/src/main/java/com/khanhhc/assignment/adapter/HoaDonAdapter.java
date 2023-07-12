package com.khanhhc.assignment.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.HoaDonDAO;
import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.fragment.ChiTietHoaDonFragment;
import com.khanhhc.assignment.fragment.HoadonFragment;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.NguoiDung;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.RecyclerHolder> {
    Activity context;
    ArrayList<HoaDon> listHD;
    HoaDonDAO dao;
    ArrayList<SanPham> listSP;
    public static String mahoadon =null;
    public static String ngay = null;

    public HoaDonAdapter(Activity context, ArrayList<HoaDon> listHD) {
        this.context = context;
        this.listHD = listHD;
    }


    @NonNull
    @Override
    public HoaDonAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_hoadon, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull final HoaDonAdapter.RecyclerHolder holder, final int position) {

            listSP = new ArrayList<SanPham>();
            holder.tvMaHD.setText("Mã hóa đơn: "+listHD.get(position).getMahoadon());
            holder.tvThoiGian.setText("Thời gian: "+listHD.get(position).getNgay());
            holder.tvSoBan.setText("Số bàn: "+listHD.get(position).getSoban());

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_add_hoadon);
                        dao = new HoaDonDAO(context);

                        final EditText edtSoBan = dialog.findViewById(R.id.edtSoBan);
                        final Button btnHuy = dialog.findViewById(R.id.btnHuy);
                        final Button btnLuu = dialog.findViewById(R.id.btnLuu);

                        edtSoBan.setText(listHD.get(position).getSoban());

                        btnHuy.setText("CANCEL");
                        btnLuu.setText("UPDATE");

                        btnLuu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String mahoadon = listHD.get(position).getMahoadon();
                                String ngay = listHD.get(position).getNgay();
                                String soBan = edtSoBan.getText().toString();
                                String maKhachHang = listHD.get(position).getMakhachhang();

                                HoaDon item = new HoaDon(mahoadon, ngay, soBan, maKhachHang, null);
                                dao.update(item);
                                listHD = dao.getAllHD();
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        btnHuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else {
                        Toast.makeText(context, "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                    }

                    return false;
                }
            });

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
