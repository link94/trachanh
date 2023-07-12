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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.LoaiSanPhamDAO;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.LoaiSanPham;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.RecyclerHolder> {
    Activity context;
    ArrayList<LoaiSanPham> listLSP;
    LoaiSanPhamDAO dao;

    public LoaiSanPhamAdapter(Activity context, ArrayList<LoaiSanPham> listLSP) {
        this.context = context;
        this.listLSP = listLSP;
    }


    @NonNull
    @Override
    public LoaiSanPhamAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_loaisanpham, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSanPhamAdapter.RecyclerHolder holder, final int position) {
            holder.tvTenLoai.setText(listLSP.get(position).getTenloai());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_add_loaisp);
                    final EditText edtLoaiSanPham = dialog.findViewById(R.id.edtLoaiSanPham);
                    final Button btnLuu = dialog.findViewById(R.id.btnLuu);
                    final Button btnHuy = dialog.findViewById(R.id.btnHuy);

                    edtLoaiSanPham.setText(listLSP.get(position).getTenloai());
                    btnLuu.setText("UPDATE");
                    btnHuy.setText("CANCEL");

                    btnLuu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String tenLoai = edtLoaiSanPham.getText().toString();
                            String maloai = listLSP.get(position).getMaloai();
                            dao = new LoaiSanPhamDAO(context);
                            LoaiSanPham item = new LoaiSanPham(maloai, tenLoai);
                            dao.update(item);
                            listLSP = dao.getAllLoaiSP();
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
    }

    @Override
    public int getItemCount() {
            return listLSP.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        CardView loaisanpham_item;
        TextView tvTenLoai;//text view layout item sanpham

        ImageView iv_Menu;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
                loaisanpham_item = itemView.findViewById(R.id.loaisanpham_item);
                tvTenLoai = itemView.findViewById(R.id.tvTenLoai);
        }
    }

}
