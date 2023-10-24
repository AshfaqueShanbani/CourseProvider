package com.example.courseprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnterBook extends AppCompatActivity {
    private ArrayList<DTO_For_book> dataclasslist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_book);
        RecyclerView rec = findViewById(R.id.rv1);
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        Adapter2 courseAdapter = new Adapter2(EnterBook.this,dataclasslist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(EnterBook.this);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(courseAdapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").document(id).collection("subtopic")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(EnterBook.this, "Error getting subdocuments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dataclasslist.clear(); // Clear the list before updating with new data

                        for (DocumentSnapshot subDocument : querySnapshot) {
                            String subDocumentId = subDocument.getId();
                            String coursename = subDocument.getString("q1");
                            String providername = "zain";
                            dataclasslist.add(new DTO_For_book(coursename, id, subDocumentId));
                        }

                        courseAdapter.notifyDataSetChanged(); // Update your adapter and UI
                        // Toast.makeText(EnterBook.this, "Data retrieved successfully", Toast.LENGTH_SHORT).show();
                    }
                });



    }
}