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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.enduser.R;
import com.example.enduser.databinding.ActivitySigninBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
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
    ProgressBar _progressBar;

    private boolean showPass = false;

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

        binding.loginBtn.setOnClickListener(view -> {
            validateFieldsForLogin();
        });

        binding.signInGoogleBtn.setOnClickListener(view -> SignInUsingGoogle());


        binding.loginBtn.setOnClickListener(view -> {
            String Email = binding.loginEmail.getText().toString();
            String Password = binding.loginPassword.getText().toString();

            FirebaseAuth.getInstance().signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SigninActivity.this, "LoginActivity Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SigninActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void SignInUsingGoogle() {
        _progressBar.setVisibility(View.VISIBLE);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                    try {
                        task.getResult(ApiException.class);
                        NavigationToNextActivity();
                    } catch (ApiException e){
                        _progressBar.setVisibility(View.INVISIBLE);
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
        _progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()) {
                        binding.loginEmail.setText("");
                        binding.loginPassword.setText("");
                        _progressBar.setVisibility(View.INVISIBLE);
                        NavigationToNextActivity();
                    } else {
                        binding.loginEmail.setText("");
                        binding.loginPassword.setText("");
                        _progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SigninActivity.this, "Email is not verified yet please verify...", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    binding.loginEmail.setText("");
                    binding.loginPassword.setText("");
                    _progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SigninActivity.this, "Login Failed try again...", Toast.LENGTH_LONG).show();
                });
    }

    private void    NavigationToNextActivity(){
        _progressBar.setVisibility(View.INVISIBLE);
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }



    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}