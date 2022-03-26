package com.codingstuff.photozzapp.ui;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codingstuff.photozzapp.R;
import com.codingstuff.photozzapp.firebase.viewmodel.FirebaseViewModel;
import com.codingstuff.photozzapp.room.viewmodel.PhotoRoomViewModel;
import com.codingstuff.photozzapp.utils.model.Photo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class MainActivity extends AppCompatActivity {

    private Uri mImageURI = null;
    private ImageView mImageView;
    private Button uploadImageBtn, viewAllImagesBtn;
    private FirebaseViewModel firebaseViewModel;
    private PhotoRoomViewModel photoRoomViewModel;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.mainActivityImageView);
        uploadImageBtn = findViewById(R.id.mainActivityUploadBtn);
        viewAllImagesBtn = findViewById(R.id.mainActivityViewAllBtn);
        progressBar = findViewById(R.id.progressBar);

        photoRoomViewModel = new ViewModelProvider(this).get(PhotoRoomViewModel.class);
        firebaseViewModel = new ViewModelProvider(this).get(FirebaseViewModel.class);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImagesFromGallery.launch("image/*");
            }
        });

        viewAllImagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , PhotoActivity.class));
            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseViewModel.uploadImagesToFirebase(mImageURI , photoRoomViewModel);
                firebaseViewModel.getTaskMutableLiveData().observe(MainActivity.this, new Observer<Task<DocumentReference>>() {
                    @Override
                    public void onChanged(Task<DocumentReference> documentReferenceTask) {
                        if (documentReferenceTask.isSuccessful()){
                            mImageView.setImageResource(R.drawable.upload_icon);
                            Toast.makeText(MainActivity.this, "Image Uploaded Successfully !!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, documentReferenceTask.getException().toString() , Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

    }

    ActivityResultLauncher<String> pickImagesFromGallery = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null){
                        mImageURI = result;
                        mImageView.setImageURI(result);
                    }

                }
            });
}