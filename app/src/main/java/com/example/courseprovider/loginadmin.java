package com.example.courseprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginadmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin2);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        EditText email=(EditText) findViewById(R.id.email);
        EditText password=(EditText) findViewById(R.id.password);
        Button login=(Button) findViewById(R.id.login);
        ImageView log=(ImageView)findViewById(R.id.log);
        ProgressBar pro=(ProgressBar)findViewById(R.id.plogin);
        final float zoomInScale = 0.86f;
        final float zoomOutScale= 1.98f;
        final ValueAnimator animator = ValueAnimator.ofFloat(zoomOutScale, zoomInScale);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(2500); // Set the duration for each cycle (in milliseconds)

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float scale = (float) valueAnimator.getAnimatedValue();
                log.setScaleX(scale);
                log.setScaleY(scale);
            }
        });
        animator.start();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String pas = password.getText().toString();
                login.setClickable(false);
                pro.setVisibility(View.VISIBLE);
                if (em.isEmpty() || pas.isEmpty()) {
                    pro.setVisibility(View.GONE);
                    login.setClickable(true);
                    Toast.makeText(loginadmin.this, "Must fill both Email and password fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Use Firebase Authentication to sign in
                    auth.signInWithEmailAndPassword(em, pas)
                            .addOnCompleteListener(loginadmin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // You can navigate to another activity here
                                        Intent intent = new Intent(loginadmin.this,AdminPage.class);
                                        startActivity(intent);
                                    } else {
                                        pro.setVisibility(View.GONE);
                                        login.setClickable(true);
                                        // Sign in failed, display an error message
                                        Toast.makeText(loginadmin.this, "Email or password wrong Try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .addOnFailureListener(loginadmin.this, new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    pro.setVisibility(View.GONE);
                                    login.setClickable(true);
                                    // Handle any authentication failure
                                    Toast.makeText(loginadmin.this, "Authentication failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

    }
}