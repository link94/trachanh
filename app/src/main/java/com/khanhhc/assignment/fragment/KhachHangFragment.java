package com.khanhhc.assignment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.adapter.KhachHangAdapter;
import com.khanhhc.assignment.adapter.NguoiDungAdapter;
import com.khanhhc.assignment.dao.KhachHangDAO;
import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.model.KhachHang;
import com.khanhhc.assignment.model.NguoiDung;

import java.util.ArrayList;


public class KhachHangFragment extends Fragment {
    ArrayList<KhachHang> list;
    KhachHangAdapter adapter;
    KhachHangDAO dao;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);

        //
        dao = new KhachHangDAO(getContext());
        list = new ArrayList<KhachHang>();
        list = dao.getAllKH();
        adapter = new KhachHangAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.rv_khachhang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //

        return view;
    }
}
