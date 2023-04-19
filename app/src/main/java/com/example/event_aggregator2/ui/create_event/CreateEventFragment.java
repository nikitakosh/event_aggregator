package com.example.event_aggregator2.ui.create_event;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentCreateEventBinding;
import com.google.android.gms.common.api.Status;

import java.util.Arrays;
import java.util.Locale;

public class CreateEventFragment extends Fragment {
    CreateEventViewModel viewModel;
    FragmentCreateEventBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CreateEventViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateEventBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = binding.address.getText().toString();
                viewModel.setAddress(address);
                String description = binding.description.getText().toString();
                String topic = binding.topic.getText().toString();
                viewModel.setDescription(description);
                NavHostFragment.findNavController(CreateEventFragment.this).navigate(R.id.eventFragment);
            }
        });
    }
}