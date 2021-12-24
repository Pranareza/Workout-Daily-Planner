package com.tubes.workoutdailyplanner.Ui;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.tubes.workoutdailyplanner.R;
import com.tubes.workoutdailyplanner.User.User;

import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        Button registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        Button login = (Button) findViewById(R.id.Login);
        login.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.username);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerUser:
                registerUser();
                break;
            case R.id.Login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String fullName = editTextFullName.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullName.setError("Nama Lengkap Wajib Diisi!");
            editTextFullName.requestFocus();
            return;
        }

        if (age.isEmpty()){
            editTextAge.setError("Umur Wajib Diisi!");
            editTextAge.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextEmail.setError("Email Wajib Diisi!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password Wajib Diisi!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Password minimal harus 6 karakter!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        User user = new User(fullName, age, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User telah berhasil terdaftar!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));

                                        //redirect to login layout!
                                    }
                                });
                    }else {
                        Toast.makeText(RegisterUser.this, " Register gagal! Coba Lagi", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


    }
}
