package com.example.enduser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.example.enduser.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private String username = "", email = "", password = "", mobile = "";

    private ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.regSubmit.setOnClickListener(view -> validateFieldsForSignUp());

        binding.toLogin.setOnClickListener(v -> NavigationToNextActivity());
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