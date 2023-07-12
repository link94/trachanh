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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.NguoiDung;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.RecyclerHolder> {
    Activity context;
    ArrayList<NguoiDung> listND;
    NguoiDungDAO dao;
    String chucvu;

    public NguoiDungAdapter(Activity context, ArrayList<NguoiDung> listND) {
        this.context = context;
        this.listND = listND;
    }


    @NonNull
    @Override
    public NguoiDungAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.items_nguoidung, parent, false);
        return (new RecyclerHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungAdapter.RecyclerHolder holder, final int position) {
            holder.tvHoVaTenNguoiDung.setText(listND.get(position).getTennguoidung());
            holder.tvSDTNguoiDung.setText(listND.get(position).getSodienthoai());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_updatenguoidung);
                        dao = new NguoiDungDAO(context);

                        final String[] chucvuArr = { "Admin", "User"};
                        final EditText edtMaND = dialog.findViewById(R.id.edtMaND);
                        final EditText edtTenND = dialog.findViewById(R.id.edtTenND);
                        final EditText edtGioiTinhND = dialog.findViewById(R.id.edtGioiTinhND);
                        final EditText edtSdtND = dialog.findViewById(R.id.edtSdtND);
                        final EditText edtEmailND = dialog.findViewById(R.id.edtEmailND);
                        final EditText edtDiaChiND = dialog.findViewById(R.id.edtDiaChiND);
                        final Spinner spinnerCV = dialog.findViewById(R.id.spinnerCV);
                        final Button btnHuy = dialog.findViewById(R.id.btnHuy);
                        final Button btnLuu = dialog.findViewById(R.id.btnLuu);

                        edtMaND.setText(listND.get(position).getManguoidung());
                        edtTenND.setText(listND.get(position).getTennguoidung());
                        edtGioiTinhND.setText(listND.get(position).getGioitinh());
                        edtSdtND.setText(listND.get(position).getSodienthoai());
                        edtEmailND.setText(listND.get(position).getEmail());
                        edtDiaChiND.setText(listND.get(position).getDiachi());
                        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, chucvuArr);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCV.setAdapter(arrayAdapter);

                        spinnerCV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                chucvu = adapterView.getItemAtPosition(i).toString();
                                Toast.makeText(adapterView.getContext(), "Selected: " + chucvu, Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        edtMaND.setEnabled(false);

                        btnLuu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String ma = edtMaND.getText().toString();
                                String tenDN = listND.get(position).getTendangnhap();
                                String matkhau = listND.get(position).getMatkhau();
                                String ten = edtTenND.getText().toString();
                                String gioitinh = edtGioiTinhND.getText().toString();
                                String sdt = edtSdtND.getText().toString();
                                String email = edtEmailND.getText().toString();
                                String diachi = edtDiaChiND.getText().toString();
                                String cv = chucvu;

                                NguoiDung item = new NguoiDung(ma, tenDN, matkhau, ten, sdt, email, gioitinh, diachi, cv);
                                dao.update(item);
                                listND = dao.getAllND();
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

                    }else{
                        Toast.makeText(context, "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });


    }

    @Override
    public int getItemCount() {
            return listND.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        CardView  nguoidung_item;
        TextView tvHoVaTenNguoiDung, tvSDTNguoiDung;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
                nguoidung_item = itemView.findViewById(R.id.nguoidung_item);
                tvHoVaTenNguoiDung = itemView.findViewById(R.id.tvHoTenNguoiDung);
                tvSDTNguoiDung = itemView.findViewById(R.id.tvSDTNguoiDung);
        }
    }

    public int convertImage(String imgName){
        int resId = context.getResources().getIdentifier(imgName, "drawable", context.getPackageName());
        return resId;
    }
}
