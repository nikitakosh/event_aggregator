package com.example.event_aggregator2.ui.create_event;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class CreateEventViewModel extends ViewModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> topic = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();


    public void setTopic(String topic) {
        this.topic.setValue(topic);
    }

    public MutableLiveData<String> getTopic() {
        return topic;
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }


    public MutableLiveData<String> getAddress() {
        return address;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public void LoadDataToDataBase(String address, String topic, String description, String date, Bitmap eventImage) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/" + userId + "/CreatedEvents/").push();
        userRef.child("address").setValue(address);
        userRef.child("topic").setValue(topic);
        userRef.child("description").setValue(description);
        userRef.child("date").setValue(date);
        String uniqueID = UUID.randomUUID().toString();
        StorageReference storageRef = storage.getReference().child(userId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        eventImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataBytes = baos.toByteArray();
        StorageReference imageRef = storageRef.child("CreatedEvents").child(uniqueID);
        UploadTask uploadTask = imageRef.putBytes(dataBytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Mytest", "картинка незагружена");
                Log.d("Mytest", String.valueOf(exception));
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("Mytest", "картинка загружена");
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        userRef.child("imageEvent").setValue(uri.toString());
                    }
                });
            }
        });
    }
}

