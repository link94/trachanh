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
import com.khanhhc.assignment.dao.ChiTietHoaDonDAO;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.fragment.ChiTietHoaDonFragment;
import com.khanhhc.assignment.fragment.SanPhamFragment;
import com.khanhhc.assignment.model.ChiTietHoaDon;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class MenuAdapterForDialog extends RecyclerView.Adapter<MenuAdapterForDialog.RecyclerHolder> {
    Activity context;
    ArrayList<SanPham> listSP;
    ChiTietHoaDonDAO chiTietHoaDonDAO;

    String masanpham;
    String tensanpham;
    String gia;
    String soluong;

    public MenuAdapterForDialog(Activity context, ArrayList<SanPham> listSP) {
        this.context = context;
        this.listSP = listSP;
    }


    @NonNull
    @Override
    public MenuAdapterForDialog.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_menu, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapterForDialog.RecyclerHolder holder, final int position) {
            byte[] hinhanh = listSP.get(position).getHinhanh();
            holder.tvTenSanPham.setText(listSP.get(position).getTensanpham());
            holder.tvMoTa.setText(listSP.get(position).getMota());
            holder.tvGiaTien.setText(listSP.get(position).getGiatien());
            try{
                holder.iv_Menu.setImageBitmap(SanPhamFragment.convertCompressedByteArrayToBitmap(hinhanh));
            }catch (Exception e){

            }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_soluong);
                final EditText edtSoLuong = dialog.findViewById(R.id.edtSoLuong);
                final Button btnOke = dialog.findViewById(R.id.btnOK);
                final Button btnCancel = dialog.findViewById(R.id.btnCancel);

                btnOke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soluong = edtSoLuong.getText().toString();
                        masanpham = listSP.get(position).getMasanpham();
                        tensanpham = listSP.get(position).getTensanpham();
                        gia = listSP.get(position).getGiatien();
                        chiTietHoaDonDAO = new ChiTietHoaDonDAO(context);
                        ChiTietHoaDon item = new ChiTietHoaDon(null, HoaDonAdapter.mahoadon, masanpham, soluong, tensanpham, gia);
                        chiTietHoaDonDAO.insert(item);
                        Toast.makeText(context, "Đã lấy dữ liệu của món" +tensanpham, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();



            }
        });
    }

    @Override
    public int getItemCount() {
            return listSP.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        CardView menu_item, hoadon_item, nguoidung_item;
        TextView tvTenSanPham, tvMoTa, tvGiaTien;//text view layout item sanpham

        ImageView iv_Menu;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
                menu_item = itemView.findViewById(R.id.menu_item);
                tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
                tvMoTa = itemView.findViewById(R.id.tvMoTa);
                tvGiaTien = itemView.findViewById(R.id.tvGiaTien);
                iv_Menu = itemView.findViewById(R.id.iv_Menu);
        }
    }

    public int convertImage(String imgName){
        int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        return resId;
    }
}
