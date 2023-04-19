package com.example.event_aggregator2.ui.profile;

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

public class ProfileViewModel extends ViewModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MutableLiveData<String> NameSurname = new MutableLiveData<>();
    private MutableLiveData<String> City = new MutableLiveData<>();
    private MutableLiveData<String> AboutYourself = new MutableLiveData<>();

    public void setNameSurname(String NameSurname) {
        this.NameSurname.setValue(NameSurname);
    }
    public void setCity(String City) {
        this.City.setValue(City);
    }
    public void setAboutYourself(String AboutYourself) {
        this.AboutYourself.setValue(AboutYourself);
    }

    public MutableLiveData<String> getNameSurname() {
        return NameSurname;
    }
    public MutableLiveData<String> getCity() {
        return City;
    }
    public MutableLiveData<String> getAboutYourself() {
        return AboutYourself;
    }

    public void GetDataFromDataBase(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/" + userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Mytest", "GetDataFromDataBase");
                String name = snapshot.child("name").getValue(String.class);
                String surname = snapshot.child("surname").getValue(String.class);
                String city = snapshot.child("city").getValue(String.class);
                String AboutYourself = snapshot.child("AboutYourself").getValue(String.class);
                setNameSurname(name + " " + surname);
                setAboutYourself(AboutYourself);
                setCity(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Mytest", "getUser:onCancelled", error.toException());
            }
        });
    }
}
