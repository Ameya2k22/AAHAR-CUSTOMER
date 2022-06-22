package com.example.enduser.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.enduser.R;
import com.example.enduser.StudentInfo;
import com.example.enduser.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater);
        FirebaseThread thread = new FirebaseThread();
        thread.start();
        FirebaseDatabase.getInstance().getReference().child("Customer").child("Mess-Info").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).child("mess_name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mess_name = snapshot.getValue(String.class);
                binding.messName.setText(mess_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }

    public class FirebaseThread extends Thread{
        DatabaseReference mBase = FirebaseDatabase.getInstance().getReference();

        @Override
        public void run() {
            binding.progressBar.setVisibility(View.VISIBLE);
            mBase.child("Customer").child("Reviews").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        long count = snapshot.getChildrenCount();
                        binding.reviews.setText(String.valueOf(count));
                    }
                    else{
                        binding.reviews.setText(String.valueOf(0));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        long count = snapshot.getChildrenCount();
                        binding.totalCustomers.setText(String.valueOf(count));
                    }
                    else{
                        binding.totalCustomers.setText(String.valueOf(0));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            FirebaseDatabase.getInstance().getReference().child("Customer").child("Ratings").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        binding.rating.setText(snapshot.getValue(String.class));
                    }
                    else{
                        binding.rating.setText(getResources().getString(R.string.not_rated_yet));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            binding.progressBar.setVisibility(View.INVISIBLE);
        }



    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    StudentInfo studentInfo = snapshot1.getValue(StudentInfo.class);
                    assert studentInfo != null;
                    long ans = studentInfo.dayRemaining;
                    if(ans == 0){
                        FirebaseDatabase.getInstance().getReference().child("Customer").child("Students").child(FirebaseAuth.getInstance().getUid()).child(Objects.requireNonNull(snapshot1.getKey())).setValue(null);
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