package com.example.event_aggregator2.ui.create_event;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentCreateEventBinding;
import com.google.android.gms.common.api.Status;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventFragment extends Fragment {
    static final int GALLERY_REQUEST = 1;
    Bitmap eventImage;
    CreateEventViewModel viewModel;
    FragmentCreateEventBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CreateEventViewModel.class);
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
        binding.PhotoEvent.setImageResource(R.drawable.create_event);
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
                try{
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    String formattedDate = dateFormat.format(selectedDate.getTime());
                    String address = binding.address.getText().toString();
                    String description = binding.description.getText().toString();
                    String topic = binding.topic.getText().toString();
                    if (address.equals("") || description.equals("") || topic.equals("")){
                        Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                    }else{
                        String idEvent = viewModel.LoadDataToDataBase(address, topic, description, formattedDate, ((BitmapDrawable)binding.PhotoEvent.getDrawable()).getBitmap());
                        Bundle bundle = new Bundle();
                        bundle.putString("idEvent", idEvent);
                        NavHostFragment.findNavController(CreateEventFragment.this).navigate(R.id.eventFragment, bundle);
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }
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
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        Log.d("Mytest", String.valueOf(requestCode));
        Log.d("Mytest", String.valueOf(GALLERY_REQUEST));
        switch(requestCode) {
            case GALLERY_REQUEST:
                Log.d("Mytest", "case");
                if(resultCode == RESULT_OK){
                    Log.d("Mytest", "if");
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap scaledImage = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                    binding.PhotoEvent.setImageBitmap(scaledImage);
                }
        }
    }
}