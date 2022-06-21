package com.example.enduser.ui.student;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enduser.Adapter.UserDisplayAdapter;
import com.example.enduser.Models.UserDisplayModel;
import com.example.enduser.StudentInfo;
import com.example.enduser.databinding.FragmentStudentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentFragment extends Fragment {

    RecyclerView recyclerView;
    UserDisplayAdapter userDisplayAdapter;
    ArrayList<UserDisplayModel> studentList;

    public StudentFragment() {
        // Required empty public constructor
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseThread thread = new FirebaseThread();
        thread.start();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.example.enduser.databinding.FragmentStudentBinding binding = FragmentStudentBinding.inflate(inflater, container, false);

        recyclerView = binding.Recyclerview;
        studentList = new ArrayList<>();

        userDisplayAdapter = new UserDisplayAdapter(studentList, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(userDisplayAdapter);

        return binding.getRoot();


    }

    private class FirebaseThread extends Thread{
        String messId = FirebaseAuth.getInstance().getUid();

        @Override
        public void run() {

            FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(messId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        StudentInfo studentInfo = snapshot1.getValue(StudentInfo.class);
                        assert studentInfo != null;
                        String id = studentInfo.getStudentID();

                        FirebaseDatabase.getInstance().getReference().child("EndUser").child("Details").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserDisplayModel userDisplayModel = snapshot.getValue(UserDisplayModel.class);
                                studentList.add(userDisplayModel);
                                userDisplayAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}