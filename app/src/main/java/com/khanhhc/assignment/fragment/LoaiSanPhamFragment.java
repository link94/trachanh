package com.khanhhc.assignment.fragment;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.khanhhc.assignment.R;
import com.khanhhc.assignment.adapter.LoaiSanPhamAdapter;
import com.khanhhc.assignment.adapter.MenuAdapter;
import com.khanhhc.assignment.dao.LoaiSanPhamDAO;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.LoaiSanPham;
import com.khanhhc.assignment.model.SanPham;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class LoaiSanPhamFragment extends Fragment {
    LoaiSanPhamDAO dao;
    ArrayList<LoaiSanPham> list;
    LoaiSanPhamAdapter adapter;
    RecyclerView recyclerView;

    Toolbar tb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_san_pham, container, false);

        //set togle bar cho fragment menu
        tb = view.findViewById(R.id.tg_bar_menu);
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_title.setText("LOẠI SẢN PHẨM");
        toolbar_title.setTypeface(Typeface.DEFAULT_BOLD);
        //

        //set data len recyclerview
        dao = new LoaiSanPhamDAO(getContext());
        list = new ArrayList<LoaiSanPham>();
        list = dao.getAllLoaiSP();
        adapter = new LoaiSanPhamAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                final String maLoai = list.get(position).getMaloai();

                Toast.makeText(getActivity(), "Delete ", Toast.LENGTH_SHORT).show();
                if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                    Snackbar.make(getView(), "Nếu bạn xóa tất cả thông tin của loại này sẽ bị mất",5000)
                            .setActionTextColor(Color.RED)
                            .setAction("Có", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dao.delete(maLoai);
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    //Remove swiped item from list and notify the RecyclerView
                                }
                            })
                            .show();
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getContext(), "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                //





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

        return view;
    }

}
