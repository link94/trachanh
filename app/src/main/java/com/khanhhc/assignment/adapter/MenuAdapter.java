package com.khanhhc.assignment.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.khanhhc.assignment.R;
import com.khanhhc.assignment.UpdateMenu;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.fragment.SanPhamFragment;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.NguoiDung;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.RecyclerHolder> {
    Activity context;
    ArrayList<SanPham> listSP;
    MenuDAO dao;

    public MenuAdapter(Activity context, ArrayList<SanPham> listSP) {
        this.context = context;
        this.listSP = listSP;
    }


    @NonNull
    @Override
    public MenuAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_menu, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.RecyclerHolder holder, final int position) {
            holder.tvTenSanPham.setText(listSP.get(position).getTensanpham());
            holder.tvMoTa.setText(listSP.get(position).getMota());
            holder.tvGiaTien.setText(listSP.get(position).getGiatien());
            byte[] hinhanh = listSP.get(position).getHinhanh();
            try{
                holder.iv_Menu.setImageBitmap(SanPhamFragment.convertCompressedByteArrayToBitmap(hinhanh));
            }catch (Exception e){

            }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                    // TRONG ADAPTER KHONG THE CHON HINH ANH TRONG MAY DUOC VI KHONG CAP QUYEN TRUY CAP DUOC
                    // NEN PHAI TAO RA UPDATE.CLASS EXTENDS DIALOG FRAGMENT  DE CAP QUYEN ROI GOI NGUOC LAI
                    Bundle bundle = new Bundle();
                    bundle.putString("masanpham", listSP.get(position).getMasanpham());
                    bundle.putString("tensanpham", listSP.get(position).getTensanpham());
                    bundle.putString("mota", listSP.get(position).getMota());
                    bundle.putString("giatien", listSP.get(position).getGiatien());
                    bundle.putString("maloai", listSP.get(position).getMaloai());
                    bundle.putString("hinhanh", listSP.get(position).getHinhanh().toString());
                    UpdateMenu updateMenu = new UpdateMenu();
                    updateMenu.setArguments(bundle);
                    updateMenu.show(((AppCompatActivity)context).getFragmentManager(), updateMenu.getTag());
                }else{
                    Toast.makeText(context, "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                }



                /*
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_menu);
                final EditText edtTenSanPham = dialog.findViewById(R.id.edtTenSanPham);
                final EditText edtMoTa = dialog.findViewById(R.id.edtMoTa);
                final EditText edtGiaTien = dialog.findViewById(R.id.edtGiaTien);
                final Button btnLuu = dialog.findViewById(R.id.btnLuu);
                final Button btnHuy = dialog.findViewById(R.id.btnHuy);
                final ImageView iv_addmenu = dialog.findViewById(R.id.iv_addmenu);
                edtTenSanPham.setText(listSP.get(position).getTensanpham());
                edtMoTa.setText(listSP.get(position).getMota());
                edtGiaTien.setText(listSP.get(position).getGiatien());

                iv_addmenu.setImageBitmap(SanPhamFragment.convertCompressedByteArrayToBitmap(listSP.get(position).getHinhanh()));


                iv_addmenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maSanPham = listSP.get(position).getMasanpham();
                        String tenSanPham = edtTenSanPham.getText().toString();
                        String moTa = edtMoTa.getText().toString();
                        String giaTien = edtGiaTien.getText().toString();
                        String maloai = listSP.get(position).getMaloai();
                        byte[] hinhanh = listSP.get(position).getHinhanh();
                        dao = new MenuDAO(context);
                        SanPham item = new SanPham(maSanPham, tenSanPham, giaTien, moTa,maloai, hinhanh);
                        dao.update(item);
                        listSP = dao.getAllMenu();
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

                dialog.show(); */
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
            return listSP.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        CardView menu_item;
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
