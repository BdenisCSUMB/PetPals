package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;
/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: CreateNewPost.java
 * Abstract: Ability to make a post
 * Date: 21 - April - 2023
 */
public class CreateNewPost extends AppCompatActivity {

    private static final String NEW_POST_USER = "com.daclink.petpals.newPostUser";

    EditText newPostEditText;

    Button newPostSpeak;
    Button newPostCancel;

    PetPalUserDAO newPostDAO;
    PetPalPost newPost;
    PetPalUser petPalUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);

        wireUpDisplay();

        loadDataBase();

        newPostCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        newPostSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNewPost();
            }
        });
    }

    private void makeNewPost() {
        String textFromInput = newPostEditText.getText().toString();
        newPost = new PetPalPost(petPalUser.getUserID(), textFromInput);
        newPostDAO.insert(newPost);
        finish();
    }

    private void loadDataBase() {
        newPostDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();
        petPalUser = newPostDAO.findPetPalUser(getIntent().getIntExtra(NEW_POST_USER, -1));
    }

    private void wireUpDisplay() {
        newPostEditText = findViewById(R.id.newpost_edit_text);
        newPostSpeak = findViewById(R.id.newpost_post);
        newPostCancel = findViewById(R.id.newpost_cancel);
    }

    public static Intent getIntent(Context context, int userID) {
        Intent intent = new Intent(context, CreateNewPost.class);
        intent.putExtra(NEW_POST_USER, userID);
        return intent;
    }
}