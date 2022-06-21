package com.example.enduser.ui.addMess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enduser.UtitlityClasses.MessInfo;
import com.example.enduser.databinding.FragmentAddMessBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;

public class AddMessFragment extends Fragment {

    private FragmentAddMessBinding binding;
    FirebaseAuth auth;
    String phone;
    private Uri messImageUrl;
    private String messImage = "";

    public AddMessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddMessBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase.getInstance().getReference().child("Customer").child("Details").child("phone_no").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phone = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewMess();
            }
        });

        binding.messImage.setOnClickListener(v -> {
            Intent imageIntent = new Intent();
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            imageIntent.setType("image/*");
            someActivityResultLauncher.launch(imageIntent);
        });
        
        return binding.getRoot();
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setTitle("File Uploading");
                pd.setProgressStyle(pd.STYLE_SPINNER);
                pd.setMessage("Uploaded : ");
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);

                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    if(data.getData() != null){
                        messImageUrl = data.getData();
                    } else{
                        Toast.makeText(getActivity(), "Unable to bring image...", Toast.LENGTH_LONG).show();
                        return;
                    }
                    messImage = String.valueOf(messImageUrl);
                    binding.messImage.setImageURI(messImageUrl);
                }
            });

    private void createNewMess() {
        FirebaseDatabase.getInstance().getReference().child("Customer").child("Details").child("phone_no").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phone = snapshot.getValue(String.class);
                String ownerName_val = binding.ownerName.getText().toString();
                String messName_val = binding.messName.getText().toString();
                String location_val = binding.messLocation.getText().toString();
                String messEmail_val = binding.messEmail.getText().toString();
                String monthlyPrice_val = binding.monthlyPrice.getText().toString();
                String specialDishes_val = binding.specialDishes.getText().toString();
                String messUpiId = binding.messUpiId.getText().toString();

                if(ownerName_val.isEmpty() || messName_val.isEmpty() || location_val.isEmpty() || messEmail_val.isEmpty() || monthlyPrice_val.isEmpty() || specialDishes_val.isEmpty() || messUpiId.isEmpty()){
                    Toast.makeText(getActivity(), "Fill Information Properly", Toast.LENGTH_SHORT).show();
                } else if(messImage.isEmpty() || messImage.equals("") || messImageUrl.equals("")){
                    Toast.makeText(getActivity(), "Mess Image is Mandatory", Toast.LENGTH_LONG).show();
                }
                else{
                    MessInfo messInfo = new MessInfo(ownerName_val, messName_val, location_val, messEmail_val, monthlyPrice_val, specialDishes_val, phone);
                    if(!messUpiId.isEmpty()){
                        messInfo.setMess_upi_id(messUpiId);
                    } else{
                        Toast.makeText(getActivity(), "Upi Field is Empty", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(!messImage.isEmpty()){
                        messInfo.setMess_image(messImage);
                    }
                    FirebaseDatabase.getInstance().getReference().child("Customer").child("Mess-Info").child(FirebaseAuth.getInstance().getUid()).setValue(messInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Mess Details Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}