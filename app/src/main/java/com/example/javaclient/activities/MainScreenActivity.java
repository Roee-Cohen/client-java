package com.example.javaclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.javaclient.R;
import com.example.javaclient.adapters.AdapterContacts;
import com.example.javaclient.utils.ClientHandler;
import com.example.javaclient.utils.User;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {

    private Intent intent;
    private ImageView imgMenu, imgSearch;
    private RecyclerView recyclerContacts;

    private User user;
    private ClientHandler clientHandler;

    private ArrayList<User> contacts;
    private AdapterContacts adapterContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        intent = getIntent();

//        user = (User) intent.getSerializableExtra("user");
//        clientHandler = (ClientHandler) LoginActivity.clientHandler;

//        clientHandler.startListening(this);
//        clientHandler.sendMessage("AOIJKNLSADOPIJKASDJOIASDJINO");

        loadViews(this);
        configureRecyclerContacts();
    }

    private void configureRecyclerContacts() {
        contacts = new ArrayList();
        contacts.add(new User("a", "a"));
        contacts.add(new User("b", "b"));
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
    }

    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_main_screen);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itemBroadcast:
                        showBroadcastDialog();
                        break;
                    case R.id.itemQuit:
                        quit();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void quit() {
        //TODO quit function
    }

    private void showBroadcastDialog() {
        new BroadcastDialog(this).show();
    }
}