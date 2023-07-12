package com.khanhhc.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.NguoiDung;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TextView tvHead, tvSmall;
    TextView txtUserName, txtPassWord;
    TextView tvSignUp, tvsignUp;
    Button btnLogin;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //anh xa view
        tvHead = findViewById(R.id.tvHead);
        tvSmall = findViewById(R.id.tvSmall);
        btnLogin = findViewById(R.id.btnLogin);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassWord = findViewById(R.id.txtPassWord);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvsignUp = findViewById(R.id.tvsignUp);

        // dinh dang font chu
        Typeface fontHead = Typeface.createFromAsset(getAssets(), "RosewoodStd-Regular.otf");
        Typeface fontSmall = Typeface.createFromAsset(getAssets(), "segoesc.ttf");
        tvHead.setTypeface(fontHead);
        tvSmall.setTypeface(fontSmall);
        //
        nguoiDungDAO = new NguoiDungDAO(getBaseContext());
        // button dang nhap chuyen activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tendangnhap = txtUserName.getText().toString();
                String matkhau = txtPassWord.getText().toString();
                String chucVu = "";
                if( tendangnhap.length() == 0 || matkhau.length() == 0){
                    Toast.makeText(LoginActivity.this, "chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(nguoiDungDAO.checkLogin(getBaseContext(), tendangnhap, matkhau)){

                    for(int i = 0; i< nguoiDungDAO.getAllND().size();i++){
                        if(tendangnhap.equalsIgnoreCase(nguoiDungDAO.getAllND().get(i).getTendangnhap()) && matkhau.equals(nguoiDungDAO.getAllND().get(i).getMatkhau())){
                            //lay du lieu nguoi dung tu khi dang nhap
                            DbHelper.tenDangNhap = nguoiDungDAO.getAllND().get(i).getTendangnhap();
                            DbHelper.matKhau = nguoiDungDAO.getAllND().get(i).getMatkhau();
                            DbHelper.tenNguoiDung = nguoiDungDAO.getAllND().get(i).getTennguoidung();
                            DbHelper.maNguoiDung = nguoiDungDAO.getAllND().get(i).getManguoidung();
                            DbHelper.sex = nguoiDungDAO.getAllND().get(i).getGioitinh();
                            DbHelper.email = nguoiDungDAO.getAllND().get(i).getEmail();
                            DbHelper.phone = nguoiDungDAO.getAllND().get(i).getSodienthoai();
                            DbHelper.diaChi = nguoiDungDAO.getAllND().get(i).getDiachi();
                            DbHelper.chucvu = nguoiDungDAO.getAllND().get(i).getChucvu();
                            chucVu = nguoiDungDAO.getAllND().get(i).getChucvu();
                        }
                    }
                    if(chucVu.equalsIgnoreCase("admin")){
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(LoginActivity.this, "User", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(i);
                    }


                }else{
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                }

            }
        });


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        tvsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

    }
}
