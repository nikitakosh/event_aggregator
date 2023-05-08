package com.example.event_aggregator2.ui.event;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentEventBinding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EventFragment extends Fragment {
    EventViewModel viewModel;
    FragmentEventBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        MapKitFactory.initialize(requireContext());
        checkLocationPermission();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String idEvent = getArguments().getString("idEvent");
        viewModel.GetDataFromDataBase(idEvent);
        viewModel.EventIsVisited(idEvent);
        viewModel.getEventIsVisited().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.visit.setVisibility(View.GONE);
            }
        });

        viewModel.getAddress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocationName(s, 1);
                    if (addresses != null && !addresses.isEmpty()) {
                        Address firstAddress = addresses.get(0);
                        Point point = new Point(firstAddress.getLatitude(), firstAddress.getLongitude());
                        binding.mapview.getMap().move(
                                new CameraPosition(point, 20.0f, 0.0f, 0.0f),
                                new Animation(Animation.Type.SMOOTH, 0),
                                null);
                        PlacemarkMapObject mark = binding.mapview.getMap().getMapObjects().addPlacemark(point);
                        mark.setOpacity(0.5f);
                        mark.setIcon(ImageProvider.fromResource(requireContext(), R.drawable.ic_marker));
                        mark.setDraggable(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        viewModel.getDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.date.setText(s);
            }
        });
        viewModel.getDescription().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.description.setText(s);
            }
        });
        binding.GoBackToCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(EventFragment.this).navigate(R.id.profileFragment);
            }
        });
        viewModel.getPhotoEventUri().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(requireActivity())
                        .asBitmap()
                        .load(s)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Bitmap scaledImage = Bitmap.createScaledBitmap(resource, binding.PhotoEvent.getWidth(), binding.PhotoEvent.getHeight(), true);
                                binding.PhotoEvent.setImageBitmap(scaledImage);

                            }
                        });
            }
        });
        binding.visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.VisitEvent();
            }
        });
    }
    @Override
    public void onStop() {
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        binding.mapview.onStart();
    }
    private static final int LOCATION_PERMISSION_CODE = 1001;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение получено, можно использовать геолокацию
            } else {
                // Разрешение не получено, сообщите пользователю
            }
        }
    }






}