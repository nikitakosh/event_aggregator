package com.example.event_aggregator2.ui.create_event;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentCreateEventBinding;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApp;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap eventImage;
    CreateEventViewModel viewModel;
    FragmentCreateEventBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CreateEventViewModel.class);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение на использование камеры не было предоставлено
            // Запрашиваем разрешение
            ActivityCompat.requestPermissions(requireActivity(), new String[] { Manifest.permission.CAMERA }, 1);
        }
        FirebaseApp.initializeApp(requireContext());
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
        Calendar selectedDate = Calendar.getInstance();
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate.set(year, month, dayOfMonth);
            }
        });
        binding.createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                String formattedDate = dateFormat.format(selectedDate.getTime());
                String address = binding.address.getText().toString();
                String description = binding.description.getText().toString();
                String topic = binding.topic.getText().toString();
                viewModel.LoadDataToDataBase(address, topic, description, formattedDate, ((BitmapDrawable)binding.PhotoEvent.getDrawable()).getBitmap());
                NavHostFragment.findNavController(CreateEventFragment.this).navigate(R.id.eventFragment);
            }
        });
        binding.GoBackToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(CreateEventFragment.this).navigate(R.id.profileFragment);
            }
        });
        binding.LoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            Bundle extras = data.getExtras();
            eventImage = (Bitmap) extras.get("data");
            binding.PhotoEvent.setImageBitmap(eventImage);
        }
    }
}