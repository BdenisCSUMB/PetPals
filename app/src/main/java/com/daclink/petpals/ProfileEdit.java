package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daclink.petpals.db.AppDatabase;
import com.daclink.petpals.db.PetPalUserDAO;

public class ProfileEdit extends AppCompatActivity {

    private static final String PROFILEEDIT_ID = "com.daclink.petpals.ProfileEditID";

    private Button editProfileBack;
    private Button editProfileSave;

    private TextView editProfileUsername;
    private EditText editProfileName;
    private EditText editProfileBreed;
    private EditText editProfileSex;
    private EditText editProfileLocation;
    private EditText editProfileAge;

    private int profileID;
    private PetPalProfile petPalProfile;
    private PetPalUser petPalUser;
    private PetPalUserDAO petPalUserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        System.out.println("Wiring up display...");
        wireUpDisplay();

        profileID = getIntent().getIntExtra(PROFILEEDIT_ID, -1);
        if(profileID != -1) {
            System.out.println("Building database");
            buildDatabase();
        } else {
            System.out.println("Profile not found. Exiting...");
            finish();
        }

        System.out.println("Populating data...");
        populateValues();

        editProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        editProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updateProfile() {
        petPalProfile.setPetName(editProfileName.getText().toString());
        petPalProfile.setPetBreed(editProfileBreed.getText().toString());
        petPalProfile.setPetSex(editProfileSex.getText().toString());
        petPalProfile.setPetLocation(editProfileLocation.getText().toString());
        petPalProfile.setAge(Integer.parseInt(editProfileAge.getText().toString()));

        petPalUserDAO.update(petPalProfile);
        finish();
    }

    private void populateValues() {
        editProfileUsername.setText(petPalUser.getUserName());
        editProfileName.setText(petPalProfile.getPetName());
        editProfileBreed.setText(petPalProfile.getPetBreed());
        editProfileSex.setText(petPalProfile.getPetSex());
        editProfileLocation.setText(petPalProfile.getPetLocation());
        editProfileAge.setText(String.valueOf(petPalProfile.getAge()));
    }

    private void buildDatabase() {
        petPalUserDAO = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getPetPalUserDAO();

        petPalUser = petPalUserDAO.findPetPalUser(profileID);
        petPalProfile = petPalUserDAO.findPetPalProfile(profileID);

        System.out.println("Database loaded...");
    }

    private void wireUpDisplay() {
        editProfileBack = findViewById(R.id.edit_profile_back);
        editProfileSave = findViewById(R.id.edit_profile_save);

        editProfileUsername = findViewById(R.id.edit_profile_username);
        editProfileName = findViewById(R.id.edit_profile_name);
        editProfileBreed = findViewById(R.id.edit_profile_breed);
        editProfileSex = findViewById(R.id.edit_profile_sex);
        editProfileLocation = findViewById(R.id.edit_profile_location);
        editProfileAge = findViewById(R.id.edit_profile_age);

        System.out.println("Display wiring complete.");
    }

    public static Intent getIntent(Context context, int profileID){
        Intent intent = new Intent(context, ProfileEdit.class);
        intent.putExtra(PROFILEEDIT_ID, profileID);
        return intent;
    }
}