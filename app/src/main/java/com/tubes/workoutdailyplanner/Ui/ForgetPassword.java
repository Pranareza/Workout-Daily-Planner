package com.tubes.workoutdailyplanner.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tubes.workoutdailyplanner.R;

public class ForgetPassword extends AppCompatActivity {

     EditText emailEditText;
     ProgressBar progressBar;
     Button resetPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailEditText = (EditText) findViewById(R.id.email);
        resetPassword = (Button) findViewById(R.id.resetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();


   resetPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String email = emailEditText.getText().toString();

            if (email.equals("")) {
                Toast.makeText(ForgetPassword.this, "Semua berkas diperlukan !", Toast.LENGTH_SHORT).show();
            } else {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPassword.this, "Mohon perikas Email anda", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(ForgetPassword.this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    });
    }
}