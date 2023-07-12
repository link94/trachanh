package com.khanhhc.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.khanhhc.assignment.fragment.KhachHangFragment;
import com.khanhhc.assignment.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    //Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new MainFragment()).commit();
        onBackPressed();


    }



    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                )
                .replace(R.id.frameLayout, new MainFragment())
                .addToBackStack(null)
                .commit();
    }
}
