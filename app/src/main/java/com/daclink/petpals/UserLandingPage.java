package com.daclink.petpals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: UserLandingPage.java
 * Abstract: LandingPage activity. From this page users may update profile, create new post, or
 * logout. Users will only see adminbutton if isAdmin field = TRUE.
 * Date: 11 - April - 2023
 */

public class UserLandingPage extends AppCompatActivity {

    private static final String USER_ID = "com.daclink.petpals.UserID";

    private Button adminButton;
    private Button logoutButton;
    private Button profileButton;
    private FloatingActionButton addNewPost;
    private FloatingActionButton viewPostByID;


    private TextView userNameDisplay;
    private TextView petPalPostView;

    private int userId;
    private PetPalUserDAO mPetPalUserDAO;
    private PetPalUser userAccount;
    private PetPalUser postUserAccount;
    private PetPalPost newPetPalPost;
    private List<PetPalPost> petPalPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing_page);

        wireUpDisplay();

        loadDatabase();

        populateLandingPage();


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProfileView();
            }
        });

        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPost();
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminTools.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });

        viewPostByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ViewPostByID.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateLandingPage();

    }

    private void createNewPost() {
        Intent intent = CreateNewPost.getIntent(getApplicationContext(), userId);
        startActivity(intent);
    }

    private void populateLandingPage() {
        userNameDisplay.setText("Hello " + userAccount.getUserName() + "!");

        if (userAccount.getIsAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
        }

        //Populate Posts
        petPalPosts = mPetPalUserDAO.getPetPalPosts();
        if (petPalPosts.isEmpty()) {
            PetPalPost firstPost = new PetPalPost(-1, "Welcome to Petpals!");
            petPalPosts.add(firstPost);
            mPetPalUserDAO.insert(firstPost);
        }
        for (PetPalPost p : petPalPosts) {
            refreshDisplay(p);
        }
    }

    private void refreshDisplay(PetPalPost newPetPalPost) {
        if (newPetPalPost.getUserID() == -1) {
            petPalPostView.setText(newPetPalPost.getPostText() + "\n");
        } else if (!newPetPalPost.isReported()) {
            String postUser = mPetPalUserDAO.findPetPalUser(newPetPalPost.getUserID()).getUserName();

            SpannableString userName = new SpannableString(postUser);
            ClickableSpan clickName = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent = ViewPost.getIntent
                            (UserLandingPage.this, newPetPalPost.getPostID(), userId);
                    startActivity(intent);
                }
            };
            userName.setSpan
                    (clickName, 0, postUser.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            petPalPostView.append
                    (userName + ": " + newPetPalPost.toString() + "\n"
                            + newPetPalPost.getPostID() + "\n");


        }

    }

    private void loadDatabase() {
        mPetPalUserDAO = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        userId = getIntent().getIntExtra(USER_ID, -1);
        userAccount = mPetPalUserDAO.findPetPalUser(userId);
    }

    private void wireUpDisplay() {
        adminButton = findViewById(R.id.landing_admin);
        logoutButton = findViewById(R.id.landing_logout);
        profileButton = findViewById(R.id.landing_profile);
        petPalPostView = findViewById(R.id.pet_posts);
        petPalPostView.setMovementMethod(new ScrollingMovementMethod());
        addNewPost = findViewById(R.id.add_new_post);
        viewPostByID = findViewById(R.id.view_post_by_id);

        userNameDisplay = findViewById(R.id.landing_username);
    }

    private void loadProfileView() {
        Intent intent = ProfilePage.getIntent(getApplicationContext(), userId);
        System.out.println("starting Profile Activity...");
        startActivity(intent);

    }

    public static Intent getIntent(Context context, int loginUserOrPost) {
        Intent intent = new Intent(context, UserLandingPage.class);
        intent.putExtra(USER_ID, loginUserOrPost);
        return intent;
    }
}