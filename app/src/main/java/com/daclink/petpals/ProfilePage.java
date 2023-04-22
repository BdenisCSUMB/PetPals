package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;

public class ProfilePage extends AppCompatActivity {

    private static final String PROFILE_ID = "com.daclink.petpals.profileID";

    private TextView profileUsername;
    private TextView profileName;
    private TextView profileBreed;
    private TextView profileAge;
    private TextView profileSex;
    private TextView profileLocation;

    private Button profileBack;
    private Button profileEdit;
    private Button profileDelete;

    private int profileID;
    private PetPalUser petPalUser;
    private PetPalProfile userProfile;
    private PetPalUserDAO petPalUserDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        System.out.println("Wiring up display...");
        wireUpDisplay();


        profileID = getIntent().getIntExtra(PROFILE_ID, -1);
        if(profileID != -1) {
            System.out.println("Loading database");
            loadDataBase();
        } else {
            System.out.println("Profile not found. Exiting...");
            finish();
        }

        populateData(userProfile, petPalUser.getUserName());

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ProfileEdit.getIntent(getApplicationContext(), profileID);
                startActivity(intent);
            }
        });

        profileDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePage.this);
                builder.setMessage("Do you want to delete profile ?");
                builder.setTitle("RUFF !");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    petPalUserDAO.delete(petPalUser);
                    Intent intent = MainActivity.getIntent(getApplicationContext());
                    startActivity(intent);
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userProfile = petPalUserDAO.findPetPalProfile(profileID);
        populateData(userProfile, petPalUser.getUserName());
    }

    private void populateData(PetPalProfile petPalProfile, String userName) {

        profileUsername.setText(userName);
        profileName.setText(petPalProfile.getPetName());
        profileBreed.setText(petPalProfile.getPetBreed());
        profileAge.setText(String.valueOf(petPalProfile.getAge()));
        profileSex.setText(petPalProfile.getPetSex());
        profileLocation.setText(petPalProfile.getPetLocation());

        System.out.println("Data populated...");

    }

    private void loadDataBase() {

        petPalUserDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        petPalUser = petPalUserDAO.findPetPalUser(profileID);
        userProfile = petPalUserDAO.findPetPalProfile(profileID);

        System.out.println("Database loaded...");
    }

    private void wireUpDisplay() {
        profileUsername = findViewById(R.id.profile_username);
        profileName = findViewById(R.id.profile_name) ;
        profileBreed = findViewById(R.id.profile_breed);
        profileAge = findViewById(R.id.profile_age);
        profileSex = findViewById(R.id.profile_sex);
        profileLocation = findViewById(R.id.profile_location);

        profileBack = findViewById(R.id.profile_back);
        profileEdit = findViewById(R.id.profile_edit);
        profileDelete = findViewById(R.id.profile_delete);

        System.out.println("wire up display");
    }

    public static Intent getIntent(Context context, int profileID) {
        Intent intent = new Intent(context, ProfilePage.class);
        intent.putExtra(PROFILE_ID, profileID);
        return intent;
    }
}