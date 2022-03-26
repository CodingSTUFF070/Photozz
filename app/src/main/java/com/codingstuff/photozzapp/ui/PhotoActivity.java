package com.codingstuff.photozzapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.codingstuff.photozzapp.R;
import com.codingstuff.photozzapp.room.viewmodel.PhotoRoomViewModel;
import com.codingstuff.photozzapp.utils.adapter.PhotoAdapter;
import com.codingstuff.photozzapp.utils.model.Photo;

import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        recyclerView = findViewById(R.id.photoActivityRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PhotoAdapter adapter = new PhotoAdapter();
        recyclerView.setAdapter(adapter);

        PhotoRoomViewModel photoRoomViewModel = new ViewModelProvider(this).get(PhotoRoomViewModel.class);
        photoRoomViewModel.getAllPhotosLiveData().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                adapter.setPhotoList(photos);
                adapter.notifyDataSetChanged();
            }
        });
    }
}