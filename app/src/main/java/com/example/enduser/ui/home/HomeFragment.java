package com.example.enduser.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.enduser.Activity.User;
import com.example.enduser.R;
import com.example.enduser.StudentInfo;
import com.example.enduser.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    TextView name, Rating, Review, students;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        name = binding.textView2;
        Rating = binding.rating;
        Review = binding.reviews;
        students = binding.Students;

        FirebaseDatabase.getInstance().getReference().child("Customer").child("Mess-Info").child(FirebaseAuth.getInstance().getUid()).child("mess_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String mess_name = snapshot.getValue(String.class);
                    name.setText(mess_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Customer").child("Ratings").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String rating = snapshot.getValue(String.class);
                Rating.setText(rating);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Customer").child("Reviews").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = 0;
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    count++;
                }
                Review.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = 0;
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    count++;
                }
                students.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    StudentInfo studentInfo = snapshot1.getValue(StudentInfo.class);
                    long ans = studentInfo.dayRemaining;
                    if(ans == 0){
                        FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(FirebaseAuth.getInstance().getUid()).child(snapshot1.getKey()).setValue(null);
                        FirebaseDatabase.getInstance().getReference().child("EndUser").child("Details").child(studentInfo.getStudentID()).child("mess_id").setValue("");
                    }
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