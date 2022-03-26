package com.codingstuff.photozzapp.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.codingstuff.photozzapp.utils.model.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Insert
    void insertPhoto(Photo photo);

    @Query("SELECT * FROM  photo_table")
    LiveData<List<Photo>> getAllPhotos();

}
