package com.example.enduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.enduser.Models.User;
import com.example.enduser.Models.Notification;
import com.example.enduser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VH> {
    ArrayList<Notification> notifications;
    Context context;

    public NotificationAdapter(ArrayList<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.notifications, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Notification notification = notifications.get(position);
        String type = notification.getNotificationType();
        FirebaseDatabase.getInstance().getReference().child("EndUser").child("Details").child(notification.getNotificationBy()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.getValue(String.class);
                    if(type.equals("Review")){
                        holder.notificationBy.setText(name);
                        holder.notificationMessage.setText("Posted Review");
                    }
                    else if(type.equals("Payment")){
                        holder.notificationBy.setText(name);
                        holder.notificationMessage.setText("Payed the fees");
                    }
                    else if(type.equals("Joined")){
                        holder.notificationBy.setText(name);
                        holder.notificationMessage.setText("Joined the Mess");
                    }
                    else if(type.equals("Ratings")){
                        holder.notificationBy.setText(name);
                        holder.notificationMessage.setText("Posted Ratings");
                    }
                }
                else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        TextView notificationBy, notificationMessage;

        public VH(@NonNull View itemView) {
            super(itemView);
            notificationMessage = itemView.findViewById(R.id.notification_message);
            notificationBy = itemView.findViewById(R.id.notification_by);
        }
    }
}
