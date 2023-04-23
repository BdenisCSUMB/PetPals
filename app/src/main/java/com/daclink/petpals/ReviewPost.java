package com.daclink.petpals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: ReviewPost.java
 * Abstract: Admin only view reported posts
 * Date: 22 - April - 2023
 */

public class ReviewPost extends AppCompatActivity {

    Button reviewPostsBack;
    TextView reviewPostsTextview;

    PetPalUserDAO petPalUserDAO;
    List<PetPalPost> reportedPosts;
    int adminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post);

        adminID = getIntent().getIntExtra(AdminTools.ADMIN_ID, -1);

        wireUpDisplay();

        loadDatabase();

        populateDisplay();

        reviewPostsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void loadDatabase() {
        petPalUserDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        List<PetPalPost> userPosts = petPalUserDAO.getPetPalPosts();
        reportedPosts = new ArrayList<>();

        for (PetPalPost p : userPosts) {
            if (p.isReported()) {
                reportedPosts.add(p);
            }
        }

    }

    private void populateDisplay() {
        reviewPostsTextview.setText("Reported Posts...\n");

        if (reportedPosts.isEmpty()) {
            return;
        }

        for (PetPalPost reported : reportedPosts) {
            String bannedUser = petPalUserDAO.findPetPalUser(reported.getUserID()).getUserName();
            SpannableString userName = new SpannableString(bannedUser);
            ClickableSpan clickName = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    Intent intent = new Intent(getApplicationContext(), ViewPost.class);
                    intent.putExtra(ViewPost.POST_ID, reported.getPostID());
                    intent.putExtra(ViewPost.VIEWER_ID, adminID);
                    startActivity(intent);
                }
            };
            userName.setSpan
                    (clickName, 0, userName.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            reviewPostsTextview.append(bannedUser + "\n  " + reported.getPostText());
        }
    }

    private void wireUpDisplay() {
        reviewPostsBack = findViewById(R.id.review_posts_back);
        reviewPostsTextview = findViewById(R.id.review_posts_textview);
    }
}