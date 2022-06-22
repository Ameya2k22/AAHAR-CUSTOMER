package com.example.enduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enduser.Models.UserDisplayModel;
import com.example.enduser.R;
import com.example.enduser.databinding.SingleUserActivityBinding;
import java.util.ArrayList;

public class UserDisplayAdapter extends RecyclerView.Adapter<UserDisplayAdapter.myViewHolder>{

    ArrayList<UserDisplayModel> list;
    Context context;

    public UserDisplayAdapter(ArrayList<UserDisplayModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_user_activity, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        UserDisplayModel user = list.get(position);

        holder.binding.name.setText(user.getName());
        holder.binding.mobile.setText(user.getPhone_no());
        holder.binding.email.setText(user.getEmail());

        holder.binding.attendancebtn.setOnClickListener(v -> ShowAttendance());

        holder.binding.paymentbtn.setOnClickListener(v -> ShowPayment());
    }

    private void ShowAttendance() {
        Toast.makeText(context, "To Attendance Activity", Toast.LENGTH_SHORT).show();
    }

    private void ShowPayment() {
        Toast.makeText(context, "To Payment Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder{

        SingleUserActivityBinding binding;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = SingleUserActivityBinding.bind(itemView);
        }
    }
}
