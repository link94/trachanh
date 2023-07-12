package com.khanhhc.assignment.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.khanhhc.assignment.LoginActivity;
import com.khanhhc.assignment.R;
import com.khanhhc.assignment.database.DbHelper;


public class ThongTinCaNhanFragment extends Fragment {
    TextView fullName, email, sex, adress, phone, changPassword, logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin_ca_nhan, container, false);

        fullName = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.email);
        sex = view.findViewById(R.id.sex);
        adress = view.findViewById(R.id.adress);
        phone = view.findViewById(R.id.phone);
        changPassword = view.findViewById(R.id.changPassword);
        logout = view.findViewById(R.id.logout);


        fullName.setText(DbHelper.tenNguoiDung);
        email.setText(DbHelper.email);
        sex.setText(DbHelper.sex);
        adress.setText(DbHelper.diaChi);
        phone.setText(DbHelper.phone);
        changPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, new ChangPasswordFragment()).commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}
