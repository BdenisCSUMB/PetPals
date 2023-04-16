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

public class UserLandingPage extends AppCompatActivity {

    private static final String USER_ID = "com.daclink.petpals.UserID";

    Button adminButton;
    Button logoutButton;

    TextView userNameDisplay;

    //TODO
    //Button profileButton;
    //Button userInbox;

    int userId;
    PetPalUserDAO mPetPalUserDAO;
    PetPalUser userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_landing_page);

        adminButton = findViewById(R.id.landing_admin);
        logoutButton = findViewById(R.id.landing_logout);

        userNameDisplay = findViewById(R.id.landing_username);

        mPetPalUserDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        userId = getIntent().getIntExtra(USER_ID, 0);
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
    }

    public static Intent getIntent(Context context, PetPalUser loginUser) {
        Intent intent = new Intent(context, UserLandingPage.class);
        intent.putExtra(USER_ID, loginUser.getUserID());
        return intent;
    }
}