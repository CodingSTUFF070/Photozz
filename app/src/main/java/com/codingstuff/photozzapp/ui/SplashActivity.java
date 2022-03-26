package com.codingstuff.photozzapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codingstuff.photozzapp.R;
import com.codingstuff.photozzapp.firebase.viewmodel.FirebaseViewModel;
import com.codingstuff.photozzapp.room.viewmodel.PhotoRoomViewModel;
import com.codingstuff.photozzapp.utils.model.Photo;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PhotoRoomViewModel photoRoomViewModel = new ViewModelProvider(this).get(PhotoRoomViewModel.class);
        FirebaseViewModel firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        photoRoomViewModel.getAllPhotosLiveData().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                if (photos == null){
                    firebaseViewModel.getImagesFromFirebase(photoRoomViewModel);
                }
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this , MainActivity.class));
                finish();
            }
        } , 3000);
    }
}