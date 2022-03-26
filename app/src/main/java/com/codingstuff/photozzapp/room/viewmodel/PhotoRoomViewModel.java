package com.codingstuff.photozzapp.room.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.codingstuff.photozzapp.room.repo.PhotoRoomRepo;
import com.codingstuff.photozzapp.utils.model.Photo;

import java.util.List;

public class PhotoRoomViewModel extends AndroidViewModel {

    private PhotoRoomRepo photoRoomRepo;

    public PhotoRoomViewModel(@NonNull Application application) {
        super(application);

        photoRoomRepo = new PhotoRoomRepo(application);
    }

    public void insertPhoto(Photo photo){
        photoRoomRepo.insertPhoto(photo);
    }

    public LiveData<List<Photo>> getAllPhotosLiveData(){
        return photoRoomRepo.getPhotoListLiveData();
    }
}
