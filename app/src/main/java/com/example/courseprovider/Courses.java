package com.example.courseprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class Courses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        Fragment fr=new Course();
       FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
       transaction.replace(R.id.frame,fr);
       transaction.commit();
    }
}