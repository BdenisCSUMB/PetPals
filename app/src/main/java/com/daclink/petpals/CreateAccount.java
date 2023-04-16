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
 * File: CreateAccount.java
 * Abstract: CreateAccount activity for users to create a new account.
 * Date: 11 - April - 2023
 */

public class CreateAccount extends AppCompatActivity {

    Button returnToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        returnToMainButton = (Button) findViewById(R.id.backbuttoncreate);

        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public static Intent getIntent(Context context) {
        return new Intent(context, CreateAccount.class);
    }
}