package com.codingstuff.photozzapp.firebase.repo;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codingstuff.photozzapp.room.viewmodel.PhotoRoomViewModel;
import com.codingstuff.photozzapp.utils.model.Photo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class FirebasePhotoRepo {

    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private OnDataUploaded onDataUploaded;

    public FirebasePhotoRepo(OnDataUploaded onDataUploaded){
        this.onDataUploaded = onDataUploaded;
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("images");
    }

    public void uploadImage(Uri uri , PhotoRoomViewModel photoRoomViewModel){

        StorageReference imageRef = storageReference.child(String.valueOf(System.currentTimeMillis()));
        imageRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.isComplete()){
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Photo photo = new Photo();
                                photo.setImageURL(uri.toString());

                                firebaseFirestore.collection("images")
                                        .add(photo)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                              if (task.isSuccessful()){
                                                  photoRoomViewModel.insertPhoto(photo);
                                              }
                                              onDataUploaded.onDataUpload(task);
                                            }
                                        });
                            }
                        });
                    }
                }
            }
        });
    }

    public void getImages(PhotoRoomViewModel photoRoomViewModel){
        firebaseFirestore.collection("images").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        Photo photo = doc.getDocument().toObject(Photo.class);
                        photoRoomViewModel.insertPhoto(photo);
                    }
                }
            }
        });
    }

    public interface OnDataUploaded{
        void onDataUpload(Task<DocumentReference> task);
    }

}
