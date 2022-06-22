package com.example.enduser.ui.addMenu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enduser.databinding.FragmentAddMenuBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AddMenuFragment extends Fragment {

    private FragmentAddMenuBinding binding;
    FirebaseAuth auth;

    public AddMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText todayMenu = binding.todayMenu;
        final Button add_menu = binding.addMenu;
        auth = FirebaseAuth.getInstance();

        add_menu.setEnabled(false);
        FirebaseDatabase.getInstance().getReference().child("Customer").child("Details").child(Objects.requireNonNull(auth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    todayMenu.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }
                    @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            String menu = todayMenu.getText().toString();
                        add_menu.setEnabled(!menu.isEmpty());
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "You are not a valid Mess Owner", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_menu.setOnClickListener(view -> {
            String menu = todayMenu.getText().toString();
            String id = auth.getUid();
            assert id != null;
            FirebaseDatabase.getInstance().getReference().child("Customer").child("Today's Menu").child(id).setValue(menu).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Menu Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}