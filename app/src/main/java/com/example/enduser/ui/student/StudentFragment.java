package com.example.enduser.ui.student;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.enduser.Activity.Customer;
import com.example.enduser.Activity.User;
import com.example.enduser.Adapter.StudentAdapter;
import com.example.enduser.R;
import com.example.enduser.StudentInfo;
import com.example.enduser.databinding.FragmentAttendanceBinding;
import com.example.enduser.databinding.FragmentStudentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentFragment extends Fragment {


    public StudentFragment() {
        // Required empty public constructor
    }

    private FragmentStudentBinding binding;
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    ArrayList<User> studentList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        recyclerView = binding.Recyclerview;
        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList, getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(studentAdapter);

        String id = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        StudentInfo studentInfo = snapshot1.getValue(StudentInfo.class);
                        String id = studentInfo.getStudentID();

                        FirebaseDatabase.getInstance().getReference().child("EndUser").child("Details").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                studentList.add(user);
                                studentAdapter.notifyDataSetChanged();
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
        return view;


    }
}