package com.example.enduser.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enduser.R;
import com.example.enduser.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private String username = "", email = "", password = "", mobile = "";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mbase;
    private ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mbase = FirebaseDatabase.getInstance();

        binding.regSubmit.setOnClickListener(view -> validateFieldsForSignUp());

        binding.toLogin.setOnClickListener(v -> {
            NavigationToNextActivity();
        });
    }

    private void validateFieldsForSignUp() {
        email = binding.regEmail.getText().toString();
        password = binding.regPassword.getText().toString();
        username = binding.regUsername.getText().toString();
        mobile = binding.regMobile.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.regEmail.setError("Required");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Check your email again...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            binding.regMobile.setError("Required");
            return;
        } else if (!Patterns.PHONE.matcher(mobile).matches()) {
            Toast.makeText(this, "Wrong Mobile Number", Toast.LENGTH_LONG).show();
            return;
        } else if (mobile.length() != 10) {
            Toast.makeText(this, "Mobile Length should be 10 only.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.regPassword.setError("Required");
            return;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password Length should be 8 or greater than 8.", Toast.LENGTH_LONG).show();
            return;
        }

        registerUser();
    }

    private void registerUser() {
        Intent intent = new Intent(SignUpActivity.this, OtpVerificationActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("EMAIL", email);
        intent.putExtra("PASSWORD", password);
        intent.putExtra("MOBILE", mobile);
        startActivity(intent);
    }

    public void NavigationToNextActivity() {
        Intent intent = new Intent(SignUpActivity.this, SigninActivity.class);
        startActivity(intent);
    }
}