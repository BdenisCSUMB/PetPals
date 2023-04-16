package com.daclink.petpals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_STARTUP = "com.daclink.petpals.MainActivityStartup";

    Button mPetpal_SignIn;
    Button mPetpal_createAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPetpal_SignIn = (Button) findViewById(R.id.sign_in);
        mPetpal_createAccount = (Button) findViewById(R.id.create_account);

        mPetpal_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginActivity();
            }
        });
        mPetpal_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewAccountActivity();
            }
        });

    }

    public void openLoginActivity() {
        Intent intent = LoginActivity.getIntent(getApplicationContext());
        startActivity(intent);
    }

    public void openNewAccountActivity() {
        Intent intent = CreateAccount.getIntent(getApplicationContext());
        startActivity(intent);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

}