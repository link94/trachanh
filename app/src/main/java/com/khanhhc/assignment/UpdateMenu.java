package com.khanhhc.assignment;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.khanhhc.assignment.adapter.MenuAdapter;
import com.khanhhc.assignment.dao.MenuDAO;
import com.khanhhc.assignment.fragment.SanPhamFragment;
import com.khanhhc.assignment.model.SanPham;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UpdateMenu extends DialogFragment {
    ImageView iv_addmenu;
    Uri imageUri;
    EditText edtTenSanPham, edtMoTa, edtGiaTien;
    MenuDAO menuDAO;
    ArrayList<SanPham> listSP;
    MenuAdapter menuAdapter;
    String maloai;

    private static final int GALLER_ACTION_PICK_CODE = 100;

    public UpdateMenu() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_menu, container, false);

        edtTenSanPham = view.findViewById(R.id.edtTenSanPham);
        edtMoTa = view.findViewById(R.id.edtMoTa);
        edtGiaTien = view.findViewById(R.id.edtGiaTien);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        iv_addmenu = view.findViewById(R.id.iv_addmenu);

        // lay du lieu tu MenuAdapter qua de xu ly
        Bundle bundle = getArguments();
        final String maSanPham = bundle.getString("masanpham");
        String tenSanPham = bundle.getString("tensanpham");
        String moTa = bundle.getString("mota");
        String giaTien = bundle.getString("giatien");
        final String maLoai = bundle.getString("maloai");
        maloai = maLoai; // lay ma loai tu adapter put  qua de show du lieu len recyclerview cho ham capnhat() o ben duoi
        byte[] hinhAnh = bundle.getByteArray("hinhanh");

        edtTenSanPham.setText(tenSanPham);
        edtMoTa.setText(moTa);
        edtGiaTien.setText(giaTien);

        try {
            iv_addmenu.setImageBitmap(convertCompressedByteArrayToBitmap(hinhAnh));
        }catch (Exception e){

        }

        iv_addmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntent();
            }
        });


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDAO = new MenuDAO(getContext());
                byte[] hinhAnh = imageViewToByte(iv_addmenu);
                SanPham item = new SanPham(maSanPham, edtTenSanPham.getText().toString(), edtGiaTien.getText().toString(), edtMoTa.getText().toString(), maLoai, hinhAnh);
                menuDAO.update(item);
                capNhat();
                Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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

    public void capNhat(){
        listSP = new ArrayList<>();
        listSP = menuDAO.getAllMenuSpiner(maloai);
        menuAdapter = new MenuAdapter(getActivity(), listSP);
        SanPhamFragment.recyclerView.setAdapter(menuAdapter);
        menuAdapter.notifyDataSetChanged();
    }
}
