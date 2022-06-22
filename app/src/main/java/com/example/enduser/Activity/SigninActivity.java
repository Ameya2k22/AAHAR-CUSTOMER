package com.example.enduser.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.enduser.databinding.ActivitySigninBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SigninActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mBase;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    String email;
    String password;

    ActivitySigninBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFieldsForLogin();
            }
        });

        binding.signInGoogleBtn.setOnClickListener(view -> SignInUsingGoogle());

        binding.forgotPassword.setOnClickListener(v -> {
            if(binding.loginEmail.getText().toString().isEmpty()){
                Toast.makeText(SigninActivity.this, "Enter your Email First", Toast.LENGTH_LONG).show();
            } else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(binding.loginEmail.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(SigninActivity.this, "Password Reset Email sent successfully check your email...", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(SigninActivity.this, "You are not a user sign up instead.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SigninActivity.this, SignUpActivity.class));
                        });
            }
        });
        binding.toSignUpPage.setOnClickListener(v -> startActivity(new Intent(SigninActivity.this, SignUpActivity.class)));
    }

    private void SignInUsingGoogle() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                    try {
                        task.getResult(ApiException.class);
                        NavigationToNextActivity();
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    } catch (ApiException e){
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SigninActivity.this, "Something went wrong" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

    private void validateFieldsForLogin() {
        email = binding.loginEmail.getText().toString();
        password = binding.loginPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            binding.loginEmail.setError("Required");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Check your email again...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.loginPassword.setError("Required");
            return;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password Length should be 8 or greater than 8.", Toast.LENGTH_LONG).show();
            return;
        }

        LoginUsingEmailPassword();
    }

    private void LoginUsingEmailPassword() {
        binding.progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    NavigationToNextActivity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SigninActivity.this, "Login Failed try again...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void    NavigationToNextActivity(){
        binding.progressBar.setVisibility(View.INVISIBLE);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}