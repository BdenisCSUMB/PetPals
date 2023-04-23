package com.daclink.petpals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
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
 * File: BannedUsers.java
 * Abstract: Admin only view banned users
 * Date: 22 - April - 2023
 */

public class BannedUsers extends AppCompatActivity {

    Button bannedUsersBack;
    TextView bannedUsersTextView;

    PetPalUserDAO petPalUserDAO;
    List<PetPalProfile> bannedUsers;
    List<PetPalProfile> userProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banned_users);

        wireUpDisplay();

        loadDatabase();

        populateData();


        bannedUsersBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void wireUpDisplay() {
        bannedUsersBack = findViewById(R.id.banned_users_back);
        bannedUsersTextView = findViewById(R.id.banned_users_textview);
    }

    private void loadDatabase() {
        petPalUserDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME).allowMainThreadQueries().build().getPetPalUserDAO();

        userProfiles = petPalUserDAO.getPetPalProfiles();
        bannedUsers = new ArrayList<>();

        for (PetPalProfile p : userProfiles) {
            if (p.isBanned()) {
                bannedUsers.add(p);
            }
        }
    }

    private void populateData() {
        bannedUsersTextView.setText("Banned Users...\n");

        if (bannedUsers.isEmpty()) {
            return;
        }

        for (PetPalProfile banned : bannedUsers) {
            String bannedUser = petPalUserDAO.findPetPalUser(banned.getProfileID()).getUserName();
            SpannableString userName = new SpannableString(bannedUser);
            ClickableSpan clickName = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    Intent intent = new Intent(getApplicationContext(), PetPalProfile.class);
                    intent.putExtra("USER_ID", banned.getProfileID());
                    startActivity(intent);
                }
            };
            userName.setSpan(clickName, 0, userName.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            bannedUsersTextView.append(bannedUser + "\n");
        }
    }
}