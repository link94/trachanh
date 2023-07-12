package com.khanhhc.assignment.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.khanhhc.assignment.R;
import com.khanhhc.assignment.dao.NguoiDungDAO;
import com.khanhhc.assignment.database.DbHelper;


public class MainFragment extends Fragment {
    TextView tvHello;
    ImageView iv_NguoiDung, iv_HoaDon, iv_Menu, iv_ThongKe, iv_info, iv_customer;
    ViewFlipper viewFlipper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //tham chieu
        iv_NguoiDung = view.findViewById(R.id.iv_NguoiDung);
        iv_HoaDon = view.findViewById(R.id.iv_HoaDon);
        iv_Menu = view.findViewById(R.id.iv_Menu);
        iv_ThongKe = view.findViewById(R.id.iv_ThongKe);
        tvHello = view.findViewById(R.id.tvHello);
        iv_info = view.findViewById(R.id.iv_info);
        iv_customer = view.findViewById(R.id.iv_customer);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        //

        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);


        iv_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.frameLayout, new SanPhamFragment()).commit();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.smool,  // enter
                                R.anim.smool,  // exit
                                R.anim.smool,   // popEnter
                                R.anim.smool  // popExit
                        )
                        .replace(R.id.frameLayout, new SanPhamFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        iv_HoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.frameLayout, new HoadonFragment()).commit();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.smool,  // enter
                                R.anim.smool,  // exit
                                R.anim.smool,   // popEnter
                                R.anim.smool  // popExit
                        )
                        .replace(R.id.frameLayout, new HoadonFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        iv_NguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.frameLayout, new NguoiDungFragment()).commit();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.smool,  // enter
                                R.anim.smool,  // exit
                                R.anim.smool,   // popEnter
                                R.anim.smool  // popExit
                        )
                        .replace(R.id.frameLayout, new NguoiDungFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.frameLayout, new ThongTinCaNhanFragment()).commit();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.smool,  // enter
                                R.anim.smool,  // exit
                                R.anim.smool,   // popEnter
                                R.anim.smool  // popExit
                        )
                        .replace(R.id.frameLayout, new ThongTinCaNhanFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        iv_ThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.frameLayout, new ThongKeeFragment()).commit();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.smool,  // enter
                                R.anim.smool,  // exit
                                R.anim.smool,   // popEnter
                                R.anim.smool  // popExit
                        )
                        .replace(R.id.frameLayout, new ThongKeFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        iv_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.frameLayout, new KhachHangFragment()).commit();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.smool,  // enter
                                R.anim.smool,  // exit
                                R.anim.smool,   // popEnter
                                R.anim.smool  // popExit
                        )
                        .replace(R.id.frameLayout, new KhachHangFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvHello.setText("Hello " + DbHelper.tenNguoiDung);
        return view;
    }
}
