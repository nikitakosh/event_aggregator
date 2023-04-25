package com.example.event_aggregator2.ui.create_event;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateEventViewModel extends ViewModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> topic = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();


    public void setTopic(String  topic) {
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

    public void LoadDataToDataBase(String address, String topic, String description, String date){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/" + userId + "/CreatedEvents/").push();
//        String uniqueId = userRef.getKey();
//        assert uniqueId != null;
        userRef.child("address").setValue(address);
        userRef.child("topic").setValue(topic);
        userRef.child("description").setValue(description);
        userRef.child("date").setValue(date);
    }

}

