package com.example.javaclient.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.javaclient.R;
import com.example.javaclient.adapters.AdapterContacts;
import com.example.javaclient.utils.CircleImageView;
import com.example.javaclient.utils.Client;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {

    private final int PICK_IMAGE = 1;

    private Intent intent;
    private ImageView imgMenu, imgSearch;
    private CircleImageView profileImage;
    private RecyclerView recyclerContacts;

    public FirebaseStorage storage;
    public StorageReference storageReference;
    public Uri filePath;

    private ArrayList<User> contacts;
    private AdapterContacts adapterContacts;

    private static User user;

    public static User getUser(){
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        intent = getIntent();

        user = User.getApplicationUser();

        ClientHandler.getInstance().setContext(this);
        ClientHandler.getInstance().startListening();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        loadViews(this);
        configureRecyclerContacts();
    }

    public void onProfileImgChange(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, PICK_IMAGE);
    }

    private void configureRecyclerContacts() {
        contacts = new ArrayList();
        contacts.add(new User("g", "g"));
        contacts.add(new User("s", "s"));
        adapterContacts = new AdapterContacts(contacts, this);
        recyclerContacts.setHasFixedSize(true);
        recyclerContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerContacts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerContacts.setAdapter(adapterContacts);
    }

    private void loadViews(AppCompatActivity view) {
        imgMenu = view.findViewById(R.id.imgMainScreenMenu);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        imgSearch = view.findViewById(R.id.imgMainScreenSearch);
        recyclerContacts = view.findViewById(R.id.recyclerChats);
        profileImage = view.findViewById(R.id.imgProfileBtn);

        //Try and load existing profile image
//        StorageReference reference = storageReference.child("profiles/" + clientHandler.getCurrentUser().getUsername());
//        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                if (uri != null)
//                    Picasso.with(MainScreenActivity.this).load(uri).into(profileImage);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
    }

    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_main_screen);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemBroadcast:
                        showBroadcastDialog();
                        break;
                    case R.id.itemNewChat:
                        showNewChatDialog();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void showNewChatDialog() {
        new BroadcastOrNewChatDialog(this, false).show();
    }

    private void showBroadcastDialog() {
        new BroadcastOrNewChatDialog(this, true).show();
    }

    private void uploadImageToFirebase() {
        if (filePath == null) return;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("profiles/" + User.getApplicationUser());
        reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(MainScreenActivity.this, "Profile image has been uploaded!", Toast.LENGTH_SHORT).show();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    profileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainScreenActivity.this, "Profile upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int) progress + "%");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            uploadImageToFirebase();
        }
    }
}