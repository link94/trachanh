package com.khanhhc.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.model.LoaiSanPham;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<LoaiSanPham> {
    private Context context;
    private ArrayList<LoaiSanPham> list;
    TextView spinner_item;

    public SpinnerAdapter(@NonNull Context context, ArrayList<LoaiSanPham> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spiner_item, null);
        }
        final LoaiSanPham item = list.get(position);
        if (item != null) {
            spinner_item = v.findViewById(R.id.spinner_item);
            spinner_item.setText(String.valueOf(item.getTenloai()));
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        final LoaiSanPham item = list.get(position);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spiner_item, null);
        }
        if (item != null) {
            spinner_item = v.findViewById(R.id.spinner_item);
            spinner_item.setText(String.valueOf(item.getTenloai()));
        }
        return v;
    }
}
