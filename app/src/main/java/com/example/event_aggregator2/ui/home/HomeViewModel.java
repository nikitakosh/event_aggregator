package com.example.event_aggregator2.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MutableLiveData<String> address = new MutableLiveData<>();

    public void setAddress(String address) {
        this.address.setValue(address);
    }

    public MutableLiveData<String> getAddress() {
        return address;
    }

    void GetDataFromDataBase(){
        DatabaseReference userRef = database.getReference("users/");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot CreatedEvent : userSnapshot.child("CreatedEvents").getChildren()){
                        String address = CreatedEvent.child("address").getValue(String.class);
                        Log.d("Mytest", address);
                        setAddress(address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок
            }
        });

    }
}
