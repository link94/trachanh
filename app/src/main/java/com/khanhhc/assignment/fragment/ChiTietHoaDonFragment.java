package com.khanhhc.assignment.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.khanhhc.assignment.R;
import com.khanhhc.assignment.adapter.CartAdapter;
import com.khanhhc.assignment.adapter.HoaDonAdapter;
import com.khanhhc.assignment.adapter.MenuAdapterForDialog;
import com.khanhhc.assignment.dao.ChiTietHoaDonDAO;
import com.khanhhc.assignment.dao.HoaDonDAO;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.model.ChiTietHoaDon;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;


public class ChiTietHoaDonFragment extends Fragment {
    FloatingActionButton fab_AddCart;
    TextView tvMaHoaDon, tvThoiGian;
    RecyclerView recyclerView;
    ChiTietHoaDonDAO dao;
    ArrayList<ChiTietHoaDon> listCTHD;
    CartAdapter cartAdapter;
    Button btnClose, btnThanhToan;
    Toolbar tb;

    ArrayList<SanPham> listSP;
    MenuDAO menuDAO;
    MenuAdapterForDialog menuAdapterForDialog;
    HoaDonDAO hoaDonDAO;
    ArrayList<HoaDon> listHD;

    TextView tvThanhTien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_hoa_don, container, false);




        //anh xa view
        fab_AddCart = view.findViewById(R.id.fab_AddCart);
        tvMaHoaDon = view.findViewById(R.id.tvMaHoaDon);
        tvThoiGian = view.findViewById(R.id.tvThoiGian);
        recyclerView = view.findViewById(R.id.recyclerView_cart);
        tvThanhTien = view.findViewById(R.id.tvTongTien);
        btnThanhToan = view.findViewById(R.id.btnThanhToan);


        //

        //set data len cart
        capnhatrv();
        //

        //set togle bar cho fragment menu
        tb = view.findViewById(R.id.tg_bar);
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("CHI TIẾT HÓA ĐƠN");
        toolbar_title.setTypeface(Typeface.DEFAULT_BOLD);
        //




        tvMaHoaDon.setText("Mã hóa đơn: "+HoaDonAdapter.mahoadon);
        tvThoiGian.setText("Ngày: "+HoaDonAdapter.ngay);



        fab_AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_cart);
                //set data len recyclerview
                menuDAO = new MenuDAO(getContext());
                listSP = new ArrayList<SanPham>();
                listSP = menuDAO.getAllMenu();
                menuAdapterForDialog = new MenuAdapterForDialog(getActivity(), listSP);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView_cart_dialog);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(menuAdapterForDialog);
                //

                btnClose = dialog.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        capnhatrv();
                    }
                });
                dialog.show();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle);
                builder.setTitle("Bạn có chắc muốn thanh toán");
                //builder.setMessage("Bạn có chắc muốn thanh toán");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnThanhToan.setText("Đã thanh toán");
                        btnThanhToan.setEnabled(false);
                        fab_AddCart.setEnabled(false);

                        listHD = new ArrayList<>();
                        listHD = hoaDonDAO.getAllHD();
                        String mahoadon = HoaDonAdapter.mahoadon;
                        String ngay = listHD.get(0).getNgay();
                        String soBan = listHD.get(0).getSoban();
                        String makhachhang = listHD.get(0).getMakhachhang();
                        String tranghai = "DTT";
                        HoaDon item = new HoaDon(mahoadon, ngay, soBan, makhachhang, tranghai);
                        hoaDonDAO.update(item);
                        dialogInterface.cancel();
                        Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //something
                    }
                });
                AlertDialog alert = builder.create();
                builder.show();

            }
        });

        return view;
    }

    public void capnhatrv(){
        //set data len cart
        dao = new ChiTietHoaDonDAO(getContext());
        listCTHD = new ArrayList<ChiTietHoaDon>();
        listCTHD = dao.getAllCTHD(HoaDonAdapter.mahoadon);
        cartAdapter = new CartAdapter(getActivity(), listCTHD);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartAdapter);
        hoaDonDAO = new HoaDonDAO(getContext());
        try{
            String trangthai = hoaDonDAO.getAllTrangThai(HoaDonAdapter.mahoadon).get(0).getTrangthai();
            if(trangthai == null){

            }else{
                if(trangthai.equalsIgnoreCase("DTT")){
                    btnThanhToan.setEnabled(false);
                    fab_AddCart.setEnabled(false);
                    btnThanhToan.setText("Đã thanh toán");
                }
            }
        }catch (Exception e){

        }
        try{
            double thanhTien = dao.getAll(HoaDonAdapter.mahoadon).get(0).getThanhTien();
            tvThanhTien.setText("Thành tiền: "+thanhTien);
        }catch (Exception e){

        }


        //
    }


}
