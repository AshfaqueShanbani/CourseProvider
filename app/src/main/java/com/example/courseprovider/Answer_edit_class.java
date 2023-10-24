package com.example.courseprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class Answer_edit_class extends AppCompatActivity {
    FirebaseFirestore db;
    String id;
    String subid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_edit_class);
        try {
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            subid = intent.getStringExtra("subid");
            TextView tv = findViewById(R.id.tv);
            db = FirebaseFirestore.getInstance();
            db.collection("courses").document(id).collection("subtopic").document(subid).collection("Answer")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Toast.makeText(Answer_edit_class.this, "Error getting subdocuments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                for (DocumentSnapshot subDocument : querySnapshot) {
                                    String Answer = subDocument.getString("answer");
                                    tv.setText(Answer);
                                    // Update your UI with the new data
                                }
                            } else {
                                Toast.makeText(Answer_edit_class.this, "Error Getting subdocuments", Toast.LENGTH_SHORT).show();
                                // Handle the case where there are no documents in the collection
                            }
                        }
                    });
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Button btn=findViewById(R.id.editanswer);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAnswer();
            }
        });


    }
    void editAnswer(){
        Dialog dialog1 = new Dialog(Answer_edit_class.this);
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
                    user.put("answer", courseng);
                    // Add a new document with a generated ID
                    db.collection("courses").document(id).collection("subtopic").document(subid).collection("Answer")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Answer_edit_class.this, "Data added Successfully", Toast.LENGTH_SHORT).show();
                                    dialog1.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Answer_edit_class.this, "Error Try again " + e, Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    Toast.makeText(Answer_edit_class.this, "Must fill all fields and check the Agreements", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog1.show();
    }
}