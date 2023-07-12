package com.khanhhc.assignment.fragment;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.khanhhc.assignment.R;
import com.khanhhc.assignment.adapter.MenuAdapter;
import com.khanhhc.assignment.adapter.SpinnerAdapter;
import com.khanhhc.assignment.adapter.TabAdapter;
import com.khanhhc.assignment.dao.LoaiSanPhamDAO;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.database.DbHelper;
import com.khanhhc.assignment.model.LoaiSanPham;
import com.khanhhc.assignment.model.SanPham;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class SanPhamFragment extends Fragment {
    private static final int GALLER_ACTION_PICK_CODE = 100;
    ArrayList<SanPham> list;
    MenuAdapter adapter;
    MenuDAO dao;
    public static RecyclerView recyclerView;
    FloatingActionButton fab_AddMenu;
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    LoaiSanPhamDAO loaiSanPhamDAO;
    ArrayList<LoaiSanPham> listLSP;
    String maloai;
    Button btnAddLoaiSP;

    Toolbar tb;

    ImageView iv_addmenu;
    Uri imageUri;

    AppCompatAutoCompleteTextView tvSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);
        //set togle bar cho fragment menu
        tb = view.findViewById(R.id.tg_bar_menu);
        TextView toolbar_title = view.findViewById(R.id.toolbar_title);
        TextView option = view.findViewById(R.id.option);
        toolbar_title.setText("MENU");
        toolbar_title.setTypeface(Typeface.DEFAULT_BOLD);

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(R.id.frameLayout, new LoaiSanPhamFragment()).commit();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.smool,  // enter
                                R.anim.smool,  // exit
                                R.anim.smool,   // popEnter
                                R.anim.smool  // popExit
                        )
                        .replace(R.id.frameLayout, new LoaiSanPhamFragment())
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(getActivity(), "Click vào loại sp", Toast.LENGTH_SHORT).show();
            }
        });
        //

        //set data len recyclerview
        dao = new MenuDAO(getContext());
        list = new ArrayList<SanPham>();
        list = dao.getAllMenu();
        adapter = new MenuAdapter(getActivity(), list);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        //

        // tham chieu
        fab_AddMenu = view.findViewById(R.id.fab_AddMenu);
        spinner = view.findViewById(R.id.spiner);
        btnAddLoaiSP = view.findViewById(R.id.btnAddLoaiSP);



        //them loai san pham
        btnAddLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_add_loaisp);
                    final EditText edtLoaiSanPham = dialog.findViewById(R.id.edtLoaiSanPham);
                    final Button btnLuu = dialog.findViewById(R.id.btnLuu);
                    final Button btnHuy = dialog.findViewById(R.id.btnHuy);

                    btnLuu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String tenLoai = edtLoaiSanPham.getText().toString();
                            if(tenLoai.length() == 0){
                                Toast.makeText(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                            }else{
                                LoaiSanPham item = new LoaiSanPham(null, tenLoai);
                                loaiSanPhamDAO.insert(item);
                                Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //
                                listLSP = loaiSanPhamDAO.getAllLoaiSP();
                                spinnerAdapter = new SpinnerAdapter(getContext(), listLSP);
                                spinner.setAdapter(spinnerAdapter);
                                //
                            }

                        }
                    });
                    btnHuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }else {
                    Toast.makeText(getContext(), "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
                final String maSanPham = list.get(position).getMasanpham();

                Toast.makeText(getActivity(), "Delete ", Toast.LENGTH_SHORT).show();
                //
                if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                    Snackbar.make(getView(), "Bạn có chắc muốn xóa",5000)
                            .setActionTextColor(Color.RED)
                            .setAction("Có", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dao.delete(maSanPham);
                                    list.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .show();
                    adapter.notifyDataSetChanged();
                    //Remove swiped item from list and notify the RecyclerView
                }else {
                    Toast.makeText(getContext(), "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }




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
        //do spiner vao fragment de show du lieu
        loaiSanPhamDAO = new LoaiSanPhamDAO(getContext());
        listLSP = new ArrayList<LoaiSanPham>();
        listLSP = loaiSanPhamDAO.getAllLoaiSP();
        spinnerAdapter = new SpinnerAdapter(getContext(), listLSP);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maloai = listLSP.get(i).getMaloai();
                Toast.makeText(getActivity(), "Bạn đã chọn "+listLSP.get(i).getTenloai(), Toast.LENGTH_SHORT).show();
                if(Integer.parseInt(maloai) == 1){
                    dao = new MenuDAO(getContext());
                    list = new ArrayList<SanPham>();
                    list = dao.getAllMenu();
                    adapter = new MenuAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                    fab_AddMenu.setEnabled(false);
                }else {
                    //
                    dao = new MenuDAO(getContext());
                    list = new ArrayList<SanPham>();
                    list = dao.getAllMenuSpiner(maloai+"");
                    adapter = new MenuAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                    fab_AddMenu.setEnabled(true);
                    //
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //

        fab_AddMenu.setOnClickListener(new View.OnClickListener() {
            ArrayList<SanPham> listHinhAnh;
            @Override
            public void onClick(View view) {
                if(DbHelper.chucvu.equalsIgnoreCase("admin")){
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_add_menu);
                    //tham chieu cac gia tri tu layout dialog_add_menu
                    final EditText edtMaSanPham, edtTenSanPham, edtMoTa, edtGiaTien;
                    final ImageView iv_choseImage;
                    final Button btnThemSanPham, btnHuy;

                    edtTenSanPham = dialog.findViewById(R.id.edtTenSanPham);
                    edtGiaTien = dialog.findViewById(R.id.edtGiaTien);
                    edtMoTa = dialog.findViewById(R.id.edtMoTa);
                    btnThemSanPham = dialog.findViewById(R.id.btnLuu);
                    btnHuy = dialog.findViewById(R.id.btnHuy);
                    iv_addmenu = dialog.findViewById(R.id.iv_addmenu);

                    iv_addmenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            galleryIntent();
                        }
                    });


                    listHinhAnh = new ArrayList<SanPham>();
                    listHinhAnh = dao.getAllMenu();
                    btnThemSanPham.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String tenSanPham = edtTenSanPham.getText().toString();
                            String giaTien = edtGiaTien.getText().toString();
                            String moTa = edtMoTa.getText().toString();
                            byte[] hinhAnh = imageViewToByte(iv_addmenu);
                            SanPham item = new SanPham(null, tenSanPham, giaTien, moTa, maloai, hinhAnh);
                            if(tenSanPham.length() == 0 || giaTien.length() == 0 || moTa.length() == 0){
                                Toast.makeText(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                            }else{
                                dao.insert(item);
                                Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
                                list = new ArrayList<SanPham>();
                                list = dao.getAllMenuSpiner(maloai+"");
                                adapter = new MenuAdapter(getActivity(), list);
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
                }else {
                    Toast.makeText(getContext(), "Bạn không có chức năng này", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }
    public void runTimePermission(){
        Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                galleryIntent();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }


            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    //Pick Image From Gallery
    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,GALLER_ACTION_PICK_CODE);
    }
    //Convert Bitmap To Byte
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //Convert Byte To BitMap
    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src){
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLER_ACTION_PICK_CODE){
                imageUri = data.getData();
                iv_addmenu.setImageURI(imageUri);
            }
        }
    }

}
