package com.example.courseprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminPage extends AppCompatActivity {
    Button btn, btn1, btn2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseFirestore db;
    private Uri selectedImageUri;
    Dialog dialog1;
    ArrayList<DTO> aa = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        RecyclerView rec = findViewById(R.id.rv1);
        AdminAdapter adapter = new AdminAdapter(AdminPage.this, aa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AdminPage.this);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        String coursename = document.getString("coursename");
                        String imageuri=document.getString("imageurl");
                        aa.add(new DTO(coursename, id,imageuri));
                       // Toast.makeText(AdminPage.this, "get susses", Toast.LENGTH_SHORT).show();
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AdminPage.this, "Error Getting documents", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn1 = findViewById(R.id.buttonsubject);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1= new Dialog(AdminPage.this);
                dialog1.setContentView(R.layout.addedcourse);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog1.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog1.getWindow().setAttributes(layoutParams);
                Button submit = dialog1.findViewById(R.id.submit);
                EditText coursename = dialog1.findViewById(R.id.coursename);
                CheckBox checkBox = dialog1.findViewById(R.id.check);
                Button imageViewInDialog=dialog1.findViewById(R.id.uploadimage);
                Button upload = dialog1.findViewById(R.id.uploadimage);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, PICK_IMAGE_REQUEST);
                    }
                });

                // Submit button in the dialog
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String courseName = coursename.getText().toString();

                        if (selectedImageUri != null && !courseName.isEmpty() && checkBox.isChecked()) {
                            // Upload the image to Firebase Storage and Firestore
                            uploadImageToFirebaseStorageAndFirestore(courseName, selectedImageUri);
                            dialog1.dismiss(); // Close the dialog after upload
                        } else {
                            Toast.makeText(AdminPage.this, "Must fill all fields and check the Agreements", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog1.show();
            }
        });


        btn2 = findViewById(R.id.buttonsubtopicdetail);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog1 = new Dialog(AdminPage.this);
                dialog1.setContentView(R.layout.addedsubtopicdetails);
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
                            user.put("coursename", courseng);
                            // Add a new document with a generated ID
                            db.collection("courses").document().collection("subtopic").document().collection("subjectdetails")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(AdminPage.this, "Data added Successfully", Toast.LENGTH_SHORT).show();
                                            dialog1.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AdminPage.this, "Error Try again " + e, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Toast.makeText(AdminPage.this, "Must fill all fields and check the Agreements", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog1.show();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ImageView imageView = dialog1.findViewById(R.id.imageView);
            imageView.setImageURI(selectedImageUri);
        }
    }


    private void uploadImageToFirebaseStorageAndFirestore(String courseName, Uri imageUri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String imageName = "image_" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageRef.child(courseName + "/" + imageName);

        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                // Save the course name, image URL, and text data to Firestore
                                saveCourseDataToFirestore(courseName, downloadUri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminPage.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Function to save course data to Firestore
    private void saveCourseDataToFirestore(String courseName, String imageUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> courseData = new HashMap<>();
        courseData.put("coursename", courseName);
        courseData.put("imageurl", imageUrl);

        db.collection("courses")
                .add(courseData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AdminPage.this, "Data added Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminPage.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}