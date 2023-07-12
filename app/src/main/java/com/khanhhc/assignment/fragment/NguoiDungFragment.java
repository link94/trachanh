package com.khanhhc.assignment.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.khanhhc.assignment.R;
import com.khanhhc.assignment.adapter.MenuAdapter;
import com.khanhhc.assignment.adapter.NguoiDungAdapter;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.model.NguoiDung;

import java.util.ArrayList;


public class NguoiDungFragment extends Fragment {
    ArrayList<NguoiDung> list;
    NguoiDungAdapter adapter;
    NguoiDungDAO dao;
    RecyclerView recyclerView;
    FloatingActionButton fab_AddNguoiDung;

    Toolbar tb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nguoi_dung, container, false);
        //set togle bar cho fragment menu
        tb = view.findViewById(R.id.tg_bar_menu);
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        TextView option = view.findViewById(R.id.option);
        toolbar_title.setText("Danh sách người dùng");
        //


        //tham chieu gia tri
        //
        dao = new NguoiDungDAO(getContext());
        list = new ArrayList<NguoiDung>();
        list = dao.getAllND();
        adapter = new NguoiDungAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //



        return view;
    }
}
