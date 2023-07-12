package com.khanhhc.assignment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.NguoiDung;


public class ChangPasswordFragment extends Fragment {
    EditText txtCurrentPassword, txtPassWord, txtPassWordAgain;
    Button btnCLogin;
    NguoiDungDAO dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chang_password, container, false);

        //tham chieu gia tri
        txtCurrentPassword = view.findViewById(R.id.txtCurrentPassword);
        txtPassWord = view.findViewById(R.id.txtPassWord);
        txtPassWordAgain = view.findViewById(R.id.txtPassWordAgain);
        btnCLogin = view.findViewById(R.id.btnCLogin);
        dao = new NguoiDungDAO(getContext());


        btnCLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matKhauCu = txtCurrentPassword.getText().toString();
                String matKhauMoi = txtPassWord.getText().toString();
                String matKhauMoiLanNua = txtPassWordAgain.getText().toString();
                String maNguoiDung = DbHelper.maNguoiDung;

                if(matKhauCu.length() == 0 || matKhauMoi.length() == 0 || matKhauMoiLanNua.length() == 0){
                    Toast.makeText(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(!matKhauCu.equals(DbHelper.matKhau)){
                        txtCurrentPassword.setError("Mật khẩu cũ không đúng");
                    }else{
                        NguoiDung item = new NguoiDung(maNguoiDung, matKhauMoi);
                        dao.updatePass(item);
                        Toast.makeText(getContext(), "Save", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
}
