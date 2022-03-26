package com.codingstuff.photozzapp.utils.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

@Entity(tableName = "photo_table")
public class Photo {

    @Exclude
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imageURL;

    public Photo(){}

    @Exclude
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
