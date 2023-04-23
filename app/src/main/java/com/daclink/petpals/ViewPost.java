package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: ViewPost.java
 * Abstract: An activity to view, report or delete posts.
 * Date: 22 - April - 2023
 */
public class ViewPost extends AppCompatActivity {

    public static final String POST_ID = "com.daclink.petpals.viewpost.POSTID";
    public static final String VIEWER_ID = "com.daclink.petpals.viewpost.VIEWERID";

    Button viewPostDelete;
    Button viewPostBack;

    TextView viewPost;
    TextView viewPostUser;

    int postID;
    int viewUserID;

    PetPalUserDAO petPalUserDAO;
    PetPalPost petPalPost;
    PetPalUser postUser;
    PetPalUser postViewer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        getIntent().getIntExtra(POST_ID, -1);
        wireUpDisplay();

        loadDatabase();

        populateData();

        if (petPalPost.getUserID() == viewUserID || postViewer.getIsAdmin()) {
            viewPostDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletePost();
                }
            });
        }
        if (postViewer.getIsAdmin()) {
            viewPostUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    banUser();
                }
            });
        }

        viewPostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        viewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportPost();
            }
        });

    }

    private void banUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPost.this);
        builder.setMessage("Do you want to ban this user?");
        builder.setTitle("RUFF !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // Set reported and update
            PetPalProfile postUserProfile = petPalUserDAO.findPetPalProfile(postUser.getUserID());
            postUserProfile.setBanned(true);
            petPalUserDAO.update(postUserProfile);

            // "Post Reported Toast
            Toast.makeText(getApplicationContext(), "User banned!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void reportPost() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPost.this);
        builder.setMessage("Do you want to report this post?");
        builder.setTitle("RUFF !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // Set reported and update
            petPalPost.setReported(true);
            petPalUserDAO.update(petPalPost);

            // "Post Reported Toast
            Toast.makeText(getApplicationContext(), "Post Reported!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePost() {
        petPalUserDAO.delete(petPalPost);
        // "Post Reported Toast
        Toast.makeText(getApplicationContext(), "Post Removed!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void loadDatabase() {
        petPalUserDAO = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        postID = getIntent().getIntExtra(POST_ID, -1);
        viewUserID = getIntent().getIntExtra(VIEWER_ID, -1);
        postViewer = petPalUserDAO.findPetPalUser(viewUserID);

        petPalPost = petPalUserDAO.findPetPalPost(postID);

        if (petPalPost == null) {
            Toast.makeText(getApplicationContext(), "Post not Found...", Toast.LENGTH_SHORT).show();
            finish();
        }

        postUser = petPalUserDAO.findPetPalUser(petPalPost.getUserID());

    }

    private void populateData() {
        if (petPalPost.getUserID() == viewUserID || postViewer.getIsAdmin()) {
            viewPostDelete.setVisibility(View.VISIBLE);
        }

        viewPostUser.setText(postUser.getUserName());
        viewPost.setText(petPalPost.getPostText());


    }

    private void wireUpDisplay() {
        viewPostBack = findViewById(R.id.viewpost_back);
        viewPostDelete = findViewById(R.id.viewpost_delete);

        viewPost = findViewById(R.id.viewpost_textview);
        viewPostUser = findViewById(R.id.viewpost_username);
    }

    public static Intent getIntent(Context context, int postID, int viewerID) {
        Intent intent = new Intent(context, ViewPost.class);
        intent.putExtra(POST_ID, postID);
        intent.putExtra(VIEWER_ID, viewerID);
        return intent;
    }
}