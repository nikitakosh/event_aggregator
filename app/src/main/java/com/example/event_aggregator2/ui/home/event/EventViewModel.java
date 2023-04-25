package com.example.event_aggregator2.ui.home.event;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EventViewModel extends ViewModel {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<String> address = new MutableLiveData<>();
    private MutableLiveData<String> topic = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();


    public void setDate(String date) {
        this.date.setValue(date);
    }

    public MutableLiveData<String> getDate() {
        return date;
    }


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

    public void GetDataFromDataBase(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/" + userId + "/CreatedEvents/" );
        Query lastQuery = userRef.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot event = dataSnapshot.getChildren().iterator().next();
                Log.d("Mytest", "dwefwefw " + event);
                String address = event.child("address").getValue(String.class);
                Log.d("Mytest", "dcwefwfw " + address);
                String description = event.child("description").getValue(String.class);
                String topic = event.child("topic").getValue(String.class);
                String date = event.child("date").getValue(String.class);
                setDate(date);
                setAddress(address);
                setTopic(topic);
                setDescription(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Ошибка получения данных из базы данных: " + databaseError.getMessage());
            }
        });
    }
}
