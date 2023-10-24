package com.example.courseprovider;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class Course extends Fragment {
    private ArrayList<DTO> dataclasslist = new ArrayList<>();
    RecyclerView rec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        RecyclerView rec = view.findViewById(R.id.recy);
        Adapter courseAdapter = new Adapter(getActivity(), dataclasslist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(courseAdapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getActivity(), "Error getting documents: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dataclasslist.clear(); // Clear the list before updating with new data

                        for (DocumentSnapshot document : querySnapshot) {
                            String id = document.getId();
                            String coursename = document.getString("coursename");
                            String imageuri = document.getString("imageurl");
                            dataclasslist.add(new DTO(coursename, id, imageuri));
                        }
                        courseAdapter.notifyDataSetChanged(); // Update your adapter and UI
                        // Toast.makeText(getActivity(), "Data retrieved successfully", Toast.LENGTH_SHORT).show();
                    }
                });


        return view;
    }
}