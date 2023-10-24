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

public class Add_Subtopic extends AppCompatActivity {
    private ArrayList<DTO_For_book> dataclasslist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subtopic);
        RecyclerView rec = findViewById(R.id.rv32);
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
       Admin_Adapter_subtopic courseAdapter = new Admin_Adapter_subtopic(Add_Subtopic.this,dataclasslist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Add_Subtopic.this);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(courseAdapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").document(id).collection("subtopic")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(Add_Subtopic.this, "Error getting subdocuments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        dataclasslist.clear(); // Clear the list before updating with new data

                        for (DocumentSnapshot subDocument : querySnapshot) {
                            String subDocumentId = subDocument.getId();
                            String coursename = subDocument.getString("q1");
                            dataclasslist.add(new DTO_For_book(coursename, id, subDocumentId));
                        }

                        courseAdapter.notifyDataSetChanged(); // Update your adapter and UI
                        Toast.makeText(Add_Subtopic.this, "Data retrieved successfully", Toast.LENGTH_SHORT).show();
                    }
                });

        Button btn=findViewById(R.id.buttonaddsubtopic);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog1 = new Dialog(Add_Subtopic.this);
                dialog1.setContentView(R.layout.addedsubtopic);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog1.getWindow().getAttributes());


                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog1.getWindow().setAttributes(layoutParams);
                Button submit = dialog1.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText coursen = dialog1.findViewById(R.id.coursename);
                        CheckBox check = dialog1.findViewById(R.id.check);
                        String courseng = coursen.getText().toString();
                        if (!courseng.isEmpty() && check.isChecked()) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("q1", courseng);
                            // Add a new document with a generated ID
                            db.collection("courses").document(id).collection("subtopic")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(Add_Subtopic.this, "Data added Successfully", Toast.LENGTH_SHORT).show();
                                            dialog1.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Add_Subtopic.this, "Error Try again " + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Toast.makeText(Add_Subtopic.this, "Must fill all fields and check the Agreements", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog1.show();
            }
        });

    }



}