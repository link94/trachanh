package com.khanhhc.assignment.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.adapter.HoaDonAdapterForTK;
import com.khanhhc.assignment.adapter.MenuAdapter;
import com.khanhhc.assignment.dao.ChiTietHoaDonDAO;
import com.khanhhc.assignment.dao.HoaDonDAO;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;
import java.util.Calendar;


public class ThongKeSanPhamFragment extends Fragment {
    RecyclerView rv_topSP;
    Button btnTuNgay, btnDenNgay, btnTK;
    MenuDAO dao;
    MenuAdapter adapter;
    ArrayList<SanPham> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke_san_pham, container, false);

        rv_topSP = view.findViewById(R.id.rv_topSP);
        btnTuNgay = view.findViewById(R.id.btnTuNgay);
        btnDenNgay = view.findViewById(R.id.btnDenNgay);
        btnTK = view.findViewById(R.id.btnTK);

        //
        final Calendar cldr = Calendar.getInstance();
        final int day = cldr.get(Calendar.DAY_OF_MONTH);
        final int month = cldr.get(Calendar.MONTH);
        final int year = cldr.get(Calendar.YEAR);
        //

        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext(), R.style.DatePicker, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear < 9 && dayOfMonth < 10){
                            btnTuNgay.setText(year+"/"+"0"+(monthOfYear+1)+"/"+"0"+dayOfMonth);
                        } else if(monthOfYear < 9){
                            btnTuNgay.setText(year+"/"+"0"+(monthOfYear+1)+"/"+dayOfMonth);
                        }else if(dayOfMonth < 10){
                            btnTuNgay.setText(year+"/"+(monthOfYear+1)+"/"+"0"+dayOfMonth);
                        }else{
                            btnTuNgay.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                        }
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext(), R.style.DatePicker, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear < 9 && dayOfMonth < 10){
                            btnDenNgay.setText(year+"/"+"0"+(monthOfYear+1)+"/"+"0"+dayOfMonth);
                        } else if(monthOfYear < 9){
                            btnDenNgay.setText(year+"/"+"0"+(monthOfYear+1)+"/"+dayOfMonth);
                        }else if(dayOfMonth < 10){
                            btnDenNgay.setText(year+"/"+(monthOfYear+1)+"/"+"0"+dayOfMonth);
                        }else{
                            btnDenNgay.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                        }
                    }
                }, year, month, day);
                datePicker.show();
            }
        });

        btnTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set tong doanh so
                dao = new MenuDAO(getContext());
                list = new ArrayList<SanPham>();
                list = dao.getTopSP(btnTuNgay.getText().toString(), btnDenNgay.getText().toString());
                adapter = new MenuAdapter(getActivity(), list);
                rv_topSP.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_topSP.setAdapter(adapter);
            }
        });
        return view;
    }
}
