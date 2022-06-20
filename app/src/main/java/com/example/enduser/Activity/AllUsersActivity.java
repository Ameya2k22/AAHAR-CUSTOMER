package com.example.enduser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.enduser.Adapter.UserDisplayAdapter;
import com.example.enduser.Models.UserDisplayModel;
import com.example.enduser.R;
import com.example.enduser.databinding.ActivityAllUsersBinding;

import java.util.ArrayList;

public class AllUsersActivity extends AppCompatActivity {

    ActivityAllUsersBinding binding;
    ArrayList<UserDisplayModel> List;
    UserDisplayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List = new ArrayList<>();
        List.add(new UserDisplayModel(R.drawable.aahar_logo, "Mahesh Pimpale", "9568742310", "mahesh@gmail.com"));
        List.add(new UserDisplayModel(R.drawable.profile, "Ankita Zade", "9568742310", "ankita@gmail.com"));
        List.add(new UserDisplayModel(R.drawable.profile_avatar, "Ankur Sigh", "9568742310", "ankur@gmail.com"));
        List.add(new UserDisplayModel(R.drawable.aahar_logo, "Pratham Yadav", "9568742310", "pratham@gmail.com"));

        adapter = new UserDisplayAdapter(List, AllUsersActivity.this);
        binding.userRclv.setLayoutManager(new LinearLayoutManager(this));
        binding.userRclv.setAdapter(adapter);
    }
}