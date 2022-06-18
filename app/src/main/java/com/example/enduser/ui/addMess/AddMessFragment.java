package com.example.enduser.ui.addMess;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enduser.Activity.MessInfo;
import com.example.enduser.R;
import com.example.enduser.databinding.FragmentAddMessBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddMessFragment extends Fragment {

    private FragmentAddMessBinding binding;
    FirebaseAuth auth;

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
        View root = binding.getRoot();

        final EditText ownerName = binding.ownerName;
        final EditText messName = binding.messName;
        final EditText location = binding.messLocation;
        final EditText messEmail = binding.messEmail;
        final EditText monthlyPrice = binding.monthlyPrice;
        final EditText specialDishes = binding.specialDishes;
        final Button create = binding.create;
        auth = FirebaseAuth.getInstance();
        
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ownerName_val = ownerName.getText().toString();
                String messName_val = messName.getText().toString();
                String location_val = location.getText().toString();
                String messEmail_val = messEmail.getText().toString();
                String monthlyPrice_val = monthlyPrice.getText().toString();
                String specialDishes_val = specialDishes.getText().toString();

                if(ownerName_val.isEmpty() || messName_val.isEmpty() || location_val.isEmpty() || messEmail_val.isEmpty() || monthlyPrice_val.isEmpty() || specialDishes_val.isEmpty()){
                    Toast.makeText(getActivity(), "Fill Information Properly", Toast.LENGTH_SHORT).show();
                }
                else{
                    MessInfo messInfo = new MessInfo(ownerName_val, messName_val, location_val, messEmail_val, monthlyPrice_val, specialDishes_val);
                    String id = auth.getUid();
                    FirebaseDatabase.getInstance().getReference().child("Customer").child("Mess-Info").child(id).setValue(messInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "Mess Details Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}