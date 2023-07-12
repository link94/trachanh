package com.khanhhc.assignment.fragment;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.khanhhc.assignment.R;
import com.khanhhc.assignment.adapter.HoaDonAdapter;
import com.khanhhc.assignment.adapter.MenuAdapter;
import com.khanhhc.assignment.dao.HoaDonDAO;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.HoaDon;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;
import java.util.Date;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class HoadonFragment extends Fragment {
    ArrayList<HoaDon> list;
    HoaDonAdapter adapter;
    HoaDonDAO dao;
    RecyclerView recyclerView;
    FloatingActionButton fab_AddHD;
    Toolbar tb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoadon, container, false);
        //
        dao = new HoaDonDAO(getContext());
        list = new ArrayList<HoaDon>();
        list = dao.getAllHD();
        adapter = new HoaDonAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //

        //set togle bar cho fragment menu
        tb = view.findViewById(R.id.tg_bar);
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("DANH SÁCH HÓA ĐƠN");
        toolbar_title.setTypeface(Typeface.DEFAULT_BOLD);
        //
        // swipe xoa item
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                final String maHoaDon = list.get(position).getMahoadon();

                Toast.makeText(getActivity(), "Delete ", Toast.LENGTH_SHORT).show();
                //
                if (DbHelper.chucvu.equalsIgnoreCase("admin")){
                    Snackbar.make(getView(), "Bạn có chắc muốn xóa",5000)
                            .setActionTextColor(Color.RED)
                            .setAction("Có", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dao.delete(maHoaDon);
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .show();
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }

                //Remove swiped item from list and notify the RecyclerView



            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(getContext(), c, recyclerView, viewHolder, dX, dY,
                        actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                        .addSwipeLeftLabel("Xóa").create().decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        //

        // xu ly su kien click vao nut floating button
        fab_AddHD = view.findViewById(R.id.fab_AddHoaDon);
        fab_AddHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_hoadon);
                //tham chieu cac gia tri tu layout dialog_add_menu
                final EditText edtSoBan;
                final Button btnThemSanPham, btnHuy;

                edtSoBan = dialog.findViewById(R.id.edtSoBan);
                btnThemSanPham = dialog.findViewById(R.id.btnLuu);
                btnHuy = dialog.findViewById(R.id.btnHuy);


                btnThemSanPham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String soBan = edtSoBan.getText().toString();
                        // lay  ngay hien tai
                        Date d = new Date();
                        CharSequence s  = DateFormat.format("yyyy/MM/dd ", d.getTime());
                        String ngay = (String)s;
                        HoaDon item = new HoaDon(null, ngay, soBan, null, null);
                        if(soBan.length() == 0){
                            Toast.makeText(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        }else{
                            dao.insert(item);
                            Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
                            list = dao.getAllHD();
                            adapter = new HoaDonAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            dialog.dismiss();
                        }
//                        list = dao.getAllMenu();
//                        adapter = new MenuAdapter(getActivity(), list);
//                        recyclerView.setAdapter(adapter);

                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        //
        return view;
    }
}
