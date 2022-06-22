package com.example.enduser.ui.Notification;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enduser.Adapter.NotificationAdapter;
import com.example.enduser.Adapter.UserDisplayAdapter;
import com.example.enduser.Models.Notification;
import com.example.enduser.R;
import com.example.enduser.databinding.FragmentNotificationBinding;
import com.example.enduser.databinding.FragmentStudentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentNotificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerview;
        ArrayList<Notification> notifications = new ArrayList<>();
        NotificationAdapter adapter = new NotificationAdapter(notifications, getActivity());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference().child("Customer").child("Notification").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Notification notification = snapshot1.getValue(Notification.class);
                    notification.setNotificationId(snapshot1.getKey());
                    notifications.add(notification);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;
    }
}