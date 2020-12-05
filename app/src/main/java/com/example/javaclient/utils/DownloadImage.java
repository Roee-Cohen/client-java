package com.example.javaclient.utils;

import android.net.Uri;
import android.os.Build;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by User on 01/03/2019.
 */

public class DownloadImage {
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private String patchImage;
    private String imageName;
//    private IDownloadImage mListener;

//    public DownloadImage(String patchImage, String imageName, IDownloadImage mListener) {
//        this.patchImage = patchImage;
//        this.imageName = patchImage.name().equals(Patch.GROUPS_PROFILES.name())?imageName:imageName.toLowerCase().replace(" ", "_");
//        this.mListener = mListener;
//    }
//
//    public interface IDownloadImage {
//        void onSuccess(Uri uri);
//        void onFail(String error);
//    }
//
//    public void startLoading() {
//
//        mStorageRef.child(patchImage.name()).child(imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//
//            @Override
//            public void onSuccess(Uri uri) {
//                if (mListener != null) {
//                    mListener.onSuccess(uri);
//                }
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                if (mListener != null)
//                    mListener.onFail(exception.getMessage());
//            }
//        });
//
//    }
}
