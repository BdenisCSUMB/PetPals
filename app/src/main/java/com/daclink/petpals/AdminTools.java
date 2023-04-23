package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: AdminTools.java
 * Abstract: Admin only screen to select tools
 * Date: 21 - April - 2023
 */

public class AdminTools extends AppCompatActivity {
    public static final String ADMIN_ID = "com.daclink.petpals.adminID";


    Button viewBannedUsers;
    Button viewReportedPosts;
    Button adminback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);

        wireUpDisplay();

        viewBannedUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewUsers();
            }
        });
        viewReportedPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReviewPosts();
            }
        });
        adminback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void startReviewPosts() {
        Intent intent = new Intent(getApplicationContext(), ReviewPost.class);
        startActivity(intent);
    }

    private void startReviewUsers() {
        Intent intent = new Intent(getApplicationContext(), BannedUsers.class);
        startActivity(intent);
    }

    private void wireUpDisplay() {
         viewBannedUsers = findViewById(R.id.banned_users);
         viewReportedPosts = findViewById(R.id.admin_review_posts);
         adminback = findViewById(R.id.admin_back);
    }

    public static Intent getIntent(Context context, int adminID){
        Intent intent = new Intent(context, AdminTools.class);
        intent.putExtra(ADMIN_ID, adminID);
        return intent;
    }
}