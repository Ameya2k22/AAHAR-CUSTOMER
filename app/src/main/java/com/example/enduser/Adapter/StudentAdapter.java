package com.example.enduser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enduser.Activity.User;
import com.example.enduser.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.VH> {
    public ArrayList<User> user;
    Context context;

    public StudentAdapter(ArrayList<User> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_list, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        User enduser = user.get(position);
        holder.name.setText(enduser.getName());
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView name;
        public VH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.Name);

        }
    }
}
