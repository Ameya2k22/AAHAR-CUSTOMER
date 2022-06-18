package com.example.enduser.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enduser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, phone, password, confirmPassword;
    Button signUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        signUp = findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Please fill details properly", Toast.LENGTH_SHORT).show();
                }
                else{
                    String name_val = name.getText().toString();
                    String email_val = email.getText().toString();
                    String phone_val = phone.getText().toString();
                    String password_val = password.getText().toString();
                    String confirmPassword_val = confirmPassword.getText().toString();

                    Customer endUser = new Customer(name_val, email_val, phone_val, confirmPassword_val);

                    if(password_val.equals(confirmPassword_val)){
                        auth.createUserWithEmailAndPassword(email_val, password_val).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String id = task.getResult().getUser().getUid();
                                    FirebaseDatabase.getInstance().getReference().child("Customer").child("Details").child(id).setValue(endUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "User Created Succesfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}