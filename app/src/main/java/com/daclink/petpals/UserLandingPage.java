package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: UserLandingPage.java
 * Abstract: LandingPage activity. From this page users may update profile, create new post, or
 *      logout. Users will only see adminbutton if isAdmin field = TRUE.
 * Date: 11 - April - 2023
 */

public class UserLandingPage extends AppCompatActivity {

    private static final String USER_ID = "com.daclink.petpals.UserID";

    private Button adminButton;
    private Button logoutButton;
    private Button profileButton;


    private TextView userNameDisplay;
    private TextView petPalPosts;

    private int userId;
    private PetPalUserDAO mPetPalUserDAO;
    private PetPalUser userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing_page);

        adminButton = findViewById(R.id.landing_admin);
        logoutButton = findViewById(R.id.landing_logout);
        profileButton = findViewById(R.id.landing_profile);
        petPalPosts = findViewById(R.id.pet_posts);


        userNameDisplay = findViewById(R.id.landing_username);

        mPetPalUserDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        userId = getIntent().getIntExtra(USER_ID, -1);
        userAccount = mPetPalUserDAO.findPetPalUser(userId);

        userNameDisplay.setText("Hello " + userAccount.getUserName() + "!");

        if (userAccount.getIsAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
        }

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
    }

    private void loadProfileView() {
        Intent intent = ProfilePage.getIntent(getApplicationContext(), userId);
        System.out.println("starting Profile Activity...");
        startActivity(intent);

    }

    public static Intent getIntent(Context context, int loginUser) {
        Intent intent = new Intent(context, UserLandingPage.class);
        intent.putExtra(USER_ID, loginUser);
        return intent;
    }
}