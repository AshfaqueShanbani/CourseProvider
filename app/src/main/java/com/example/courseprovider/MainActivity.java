package com.example.courseprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout admin=(LinearLayout) findViewById(R.id.adminpage);
        LinearLayout appin=(LinearLayout) findViewById(R.id.appinfo);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,loginadmin.class);
                startActivity(intent);
            }
        });
        appin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Appinfo.class);
                startActivity(intent);
            }
        });
        LinearLayout learn=(LinearLayout) findViewById(R.id.learn);

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,Courses.class);
                startActivity(intent);

            }
        });
    }
}