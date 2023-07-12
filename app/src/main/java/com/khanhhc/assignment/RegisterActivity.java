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
import com.khanhhc.assignment.model.NguoiDung;

public class RegisterActivity extends AppCompatActivity {
    TextView tvHead, tvSmall;
    TextView txtRUserName, txtRPassWord, txtRPassWordAgain, txtHVT, txtPhone, login_text_view_btn;
    Button btnRegister;
    NguoiDungDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //anh xa
        tvHead = findViewById(R.id.tvHead);
        tvSmall = findViewById(R.id.tvSmall);
        txtRUserName = findViewById(R.id.txtRUserName);
        txtRPassWord = findViewById(R.id.txtRPassWord);
        txtRPassWordAgain = findViewById(R.id.txtRPassWordAgain);
        txtHVT = findViewById(R.id.txtHVT);
        txtPhone = findViewById(R.id.txtSDT);
        btnRegister = findViewById(R.id.btnRegister);
        login_text_view_btn = findViewById(R.id.login_text_view_btn);

        // dinh dang font chu
        Typeface fontHead = Typeface.createFromAsset(getAssets(), "RosewoodStd-Regular.otf");
        Typeface fontSmall = Typeface.createFromAsset(getAssets(), "segoesc.ttf");
        tvHead.setTypeface(fontHead);
        tvSmall.setTypeface(fontSmall);
        //

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txtRUserName.getText().toString();
                String password = txtRPassWord.getText().toString();
                String passwordAgain = txtRPassWordAgain.getText().toString();
                String tennguoidung = txtHVT.getText().toString();
                String phone = txtPhone.getText().toString();
                String pathName = "^[A-Za-z0-9_\\.]{6,32}$";
                String pathPass = "^([A-Z]){1}([\\w_\\.!@#$%^&*()]+){5,31}$";
                if(userName.length() == 0 || password.length() == 0 || passwordAgain.length() == 0){
                    Toast.makeText(RegisterActivity.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if(userName.matches(pathName) && password.matches(pathPass) && passwordAgain.equals(password)){
                    NguoiDung item = new NguoiDung(null, userName, password, tennguoidung, phone, null, null, null, "user");
                    dao = new NguoiDungDAO(getBaseContext());
                    dao.insert(item);
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                }else{
                    if(!userName.matches(pathName)){
                        Toast.makeText(RegisterActivity.this,
                                "Tên đăng nhập bao gồm các ký tự chữ cái, chữ số, dấu gạch dưới, dấu chấm\n" +
                                "Độ dài 6-32 ký tự", Toast.LENGTH_SHORT).show();
                    }
                    if(!password.matches(pathPass)){
                        Toast.makeText(RegisterActivity.this,
                                "Mật khẩu bao gồm các ký chữ cái, chữ số, ký tự đặc biệt, dấu chấm\n" +
                                "Bắt đầu bằng ký tự in hoa\n" +
                                "Độ dài 6-32 ký tự", Toast.LENGTH_SHORT).show();
                    }
                    if(!passwordAgain.equals(password)){
                        Toast.makeText(RegisterActivity.this,
                                "Hai mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        login_text_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
