package com.example.enduser.ui.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.enduser.UtitlityClasses.Customer;
import com.example.enduser.databinding.FragmentAttendanceBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AttendanceFragment extends Fragment {

    private FragmentAttendanceBinding binding;
    FirebaseAuth auth;
    TextView count;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        inflater = LayoutInflater.from(getActivity());
        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        final Button startSession = binding.startSession;
        final Button endSession = binding.endSession;
        count = binding.totalCount;
        auth = FirebaseAuth.getInstance();
        
        startSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = auth.getUid();
                FirebaseDatabase.getInstance().getReference().child("Customer").child("Attendance").child(id).child("SessionOn").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Attendance Session Created.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        endSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = auth.getUid();
                FirebaseDatabase.getInstance().getReference().child("Customer").child("Attendance").child(id).child("SessionOn").setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Attendance Session Closed", Toast.LENGTH_SHORT).show();

                            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                            FirebaseDatabase.getInstance().getReference().child("Customer").child("AttendanceByDay").child(FirebaseAuth.getInstance().getUid()).child(date).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    long count = snapshot.getChildrenCount();
                                    binding.totalCount.setText(String.valueOf(count));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
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