package com.example.enduser.ui.addMess;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.enduser.R;
import com.example.enduser.databinding.FragmentAddMessBinding;

public class AddMessFragment extends Fragment {

    private FragmentAddMessBinding addMessBinding;

    public AddMessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addMessBinding = FragmentAddMessBinding.inflate(inflater, container, false);
        View root = addMessBinding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addMessBinding = null;
    }
}