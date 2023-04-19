package com.example.event_aggregator2.ui.create_profile;

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

import java.security.spec.NamedParameterSpec;

public class CreateProfileViewModel extends ViewModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private MutableLiveData<String> city = new MutableLiveData<>();
    private MutableLiveData<String> NameSurname = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> AboutYourself = new MutableLiveData<>();

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public void setNameSurname(String NameSurname) {
        this.NameSurname.setValue(NameSurname);
    }


    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public void setAboutYourself(String aboutYourself) {
        this.AboutYourself.setValue(aboutYourself);
    }

    public MutableLiveData<String> getCity() {
        return city;
    }

    public MutableLiveData<String> getNameSurname() {
        return NameSurname;
    }


    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
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
                String password = snapshot.child("password").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                Log.d("Mytest", "GetDataFromDataBase " + password + " " + email);
                setEmail(email);
                setPassword(password);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Mytest", "getUser:onCancelled", error.toException());
            }
        });
    }
    public void LoadDataToDataBase(String name, String surname, String city, String password, String AboutYourself, String email){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = database.getReference("users/" + userId);
        userRef.child("name").setValue(name);
        userRef.child("surname").setValue(surname);
        userRef.child("city").setValue(city);
        userRef.child("password").setValue(password);
        userRef.child("AboutYourself").setValue(AboutYourself);
        userRef.child("email").setValue(email);
    }
}
