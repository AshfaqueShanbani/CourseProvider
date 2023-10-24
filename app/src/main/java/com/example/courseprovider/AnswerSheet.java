package com.example.courseprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class AnswerSheet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_sheet);
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        String subid=intent.getStringExtra("subid");
        TextView tv=findViewById(R.id.tv);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").document(id).collection("subtopic").document(subid).collection("Answer")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(AnswerSheet.this, "Error getting subdocuments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (DocumentSnapshot subDocument : querySnapshot) {
                                String Answer = subDocument.getString("answer");
                                tv.setText(Answer);
                                // Update your UI with the new data
                            }
                        } else {
                            Toast.makeText(AnswerSheet.this, "Error Getting subdocuments", Toast.LENGTH_SHORT).show();
                            // Handle the case where there are no documents in the collection
                        }
                    }
                });


    }
}