package com.codingstuff.photozzapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codingstuff.photozzapp.R;
import com.codingstuff.photozzapp.utils.model.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> photoList;
    public void setPhotoList(List<Photo> photoList){
        this.photoList = photoList;
    }
    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_photo , parent , false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext()).load(photoList.get(position).getImageURL())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (photoList == null){
            return 0;
        }else{
            return photoList.size();
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.eachPhotoImageView);
        }
    }
}
