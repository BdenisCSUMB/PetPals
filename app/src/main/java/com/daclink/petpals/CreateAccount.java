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
 * File: CreateAccount.java
 * Abstract: CreateAccount activity for users to create a new account.
 * Date: 11 - April - 2023
 */

public class CreateAccount extends AppCompatActivity {

    Button returnToMainButton;
    Button createNewAccount;

    EditText newUserName;
    EditText firstPassword;
    EditText secondPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireUpDisplay();

        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
            }
        });
    }

    private void createProfile() {
        String userName = "";
        String passwordOne = "";
        String passwordTwo = "";

        userName = newUserName.getText().toString();
        passwordOne = firstPassword.getText().toString();
        passwordTwo = secondPassword.getText().toString();


        if (userName.isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Choose a Username!";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();
            return;

        } else if (!(passwordTwo.equals(passwordOne))) {
            Context context = getApplicationContext();
            CharSequence text = "Passwords do not Match!";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();
            return;
        }

        PetPalUser newUser = new PetPalUser(userName, passwordOne);

        loadToDatabase(newUser);

    }

    private void loadToDatabase(PetPalUser user) {

        PetPalUserDAO petPalUserDAO = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        List<PetPalUser> currentUsers = petPalUserDAO.getUserIDs();

        for (PetPalUser p : currentUsers) {
            if (user.getUserName().equals(p.getUserName())) {

                Context context = getApplicationContext();
                CharSequence text = "Username Taken!";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();

                return;
            }
        }

        // Insert new user
        petPalUserDAO.insert(user);

        // Toast "User Created!"
        Context context = getApplicationContext();
        CharSequence text = "User Created!";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();

        // Return to Main Activity
        finish();
    }

    private void wireUpDisplay() {
        returnToMainButton = findViewById(R.id.backbuttoncreate);
        createNewAccount = findViewById(R.id.create_account_button);

        newUserName = findViewById(R.id.new_username_edittext);
        firstPassword = findViewById(R.id.first_password);
        secondPassword = findViewById(R.id.second_password);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CreateAccount.class);
    }
}