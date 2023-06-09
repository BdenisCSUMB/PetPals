package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;

import java.util.List;

/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: LoginActivity.java
 * Abstract: This Activity displays the login screen where users may enter their username and
 *      password. This activity references values stored in the ROOM database and allows users to
 *      log in if matching accounts are found.
 * Date: 11 - April - 2023
 */

public class LoginActivity extends AppCompatActivity {

    Button returnToMainButton;
    Button loginButton;
    EditText userNameEdit;
    EditText passwordEdit;

    PetPalUserDAO mPetPalUserDAO;

    PetPalUser loginUser;

    List<PetPalUser> petPalUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        returnToMainButton = findViewById(R.id.backbuttonlogin);
        userNameEdit = findViewById(R.id.username);
        passwordEdit = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        mPetPalUserDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        petPalUsers = mPetPalUserDAO.getUserIDs();
        if (petPalUsers.isEmpty()) {
            PetPalUser testuser1 = new PetPalUser("testuser1", "testuser1");
            PetPalUser admin2 = new PetPalUser("admin2", "admin2");
            admin2.setIsAdmin(true);
            mPetPalUserDAO.insert(testuser1, admin2);
            petPalUsers = mPetPalUserDAO.getUserIDs();

            // Link profiles to new users
            for (PetPalUser p: petPalUsers ){
                PetPalProfile userProfile = new PetPalProfile(p.getUserID());
                mPetPalUserDAO.insert(userProfile);
            }
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser = getValuesFromDisplay();

                if (loginUser == null) {
                    Context context = getApplicationContext();
                    CharSequence text = "User not found. Try again.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast.makeText(context, text, duration).show();
                } else {
                    System.out.println("Starting LandingPage Activity...");
                    OpenLandingPage(loginUser.getUserID());
                }

            }
        });


        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public static Intent getIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private PetPalUser getValuesFromDisplay() {
        String userName = "";
        String password = "";

        userName = userNameEdit.getText().toString();
        password = passwordEdit.getText().toString();

        for (PetPalUser p : petPalUsers) {
            if (p.getUserName().equals(userName)
                    && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    private void OpenLandingPage(int loginUser) {
        Intent intent = UserLandingPage.getIntent(getApplicationContext(), loginUser);
        startActivity(intent);
    }

}