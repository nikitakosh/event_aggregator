package com.example.event_aggregator2.ui.create_profile;

import static android.app.Activity.RESULT_OK;
import static com.yandex.runtime.Runtime.getApplicationContext;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.event_aggregator2.R;
import com.example.event_aggregator2.databinding.FragmentCreateProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class CreateProfileFragment extends Fragment {
    static final int GALLERY_REQUEST = 1;

    private CreateProfileViewModel viewModel;
    private FragmentCreateProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CreateProfileViewModel.class);
        FirebaseApp.initializeApp(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.PhotoProfile.setImageResource(R.drawable.ava);
        viewModel.GetDataFromDataBase();
        GetImageFromDataBase();
        viewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.name.setText(s);
            }
        });
        viewModel.getSurname().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.surname.setText(s);
            }
        });
        viewModel.getCity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.city.setText(s);
            }
        });
        viewModel.getAboutYourself().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.AboutYorself.setText(s);
            }
        });
        viewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.email.setText(s);
            }
        });
        viewModel.getIsOrganizer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.IsOrganizer.setChecked(aBoolean);
            }
        });
        binding.CreateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean IsOrganizer;
                try{
                    String name = binding.name.getText().toString();
                    String surname = binding.surname.getText().toString();
                    String city = binding.city.getText().toString();
                    String AboutYourself = binding.AboutYorself.getText().toString();
                    String email = binding.email.getText().toString();
                    IsOrganizer = binding.IsOrganizer.isChecked();
                    if (name.equals("") || surname.equals("") || city.equals("") || AboutYourself.equals("") || email.equals("")){
                        Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                    }else {
                        viewModel.LoadDataToDataBase(name, surname, city, AboutYourself, email, IsOrganizer);
                        viewModel.UploadImageToDataBase(((BitmapDrawable) binding.PhotoProfile.getDrawable()).getBitmap());
                        NavHostFragment.findNavController(CreateProfileFragment.this).navigate(R.id.profileFragment);
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.GoToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean IsOrganizer;
                try{
                    String name = binding.name.getText().toString();
                    String surname = binding.surname.getText().toString();
                    String city = binding.city.getText().toString();
                    String AboutYourself = binding.AboutYorself.getText().toString();
                    String email = binding.email.getText().toString();
                    IsOrganizer = binding.IsOrganizer.isChecked();
                    if (name.equals("") || surname.equals("") || city.equals("") || AboutYourself.equals("") || email.equals("")){
                        Toast.makeText(getContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                    }else {
                        viewModel.LoadDataToDataBase(name, surname, city, AboutYourself, email, IsOrganizer);
                        viewModel.UploadImageToDataBase(((BitmapDrawable) binding.PhotoProfile.getDrawable()).getBitmap());
                        NavHostFragment.findNavController(CreateProfileFragment.this).navigate(R.id.profileFragment);
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.LoadFromGallery.setOnClickListener(new View.OnClickListener() {
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
                    binding.PhotoProfile.setImageBitmap(bitmap);
                }
        }
    }
    public void GetImageFromDataBase(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageRef = storage.getReference().child(userId);
        storageRef.child("profileImage.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(requireActivity()).load(uri).into(binding.PhotoProfile);
            }
        });
    }


}