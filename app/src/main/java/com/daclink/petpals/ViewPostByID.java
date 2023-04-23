package com.daclink.petpals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;
/**
 * Author: Benjamin Denis
 * Project: PetPals - Twitter for pets
 * File: ViewPostByID.java
 * Abstract: A janky work around for not having clickable usernames in posts
 * Date: 22 - April - 2023
 */
public class ViewPostByID extends AppCompatActivity {

    public static final String FIND_ID_USER = "com.daclink.petpals.findIdUser";
    private Button findPostId;
    private Button findPostBack;

    private EditText postIDTextview;

    private int postId;
    private int viewerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post_by_id);

        wireUpDisplay();

        viewerId = getIntent().getIntExtra(FIND_ID_USER, -1);

        findPostBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findPostId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postId = Integer.parseInt(postIDTextview.getText().toString());
                Intent intent = ViewPost.getIntent(getApplicationContext(), postId, viewerId);
                startActivity(intent);
            }
        });

    }

    private void wireUpDisplay() {
        postIDTextview = findViewById(R.id.post_by_id_edittext);
        findPostId = findViewById(R.id.post_by_id_find);
        findPostBack = findViewById(R.id.post_by_id_back);
    }

    public static Intent getIntent(Context context, int viewerID ) {
        Intent intent = new Intent(context, ViewPostByID.class);
        intent.putExtra(FIND_ID_USER, viewerID);
        return intent;
    }
}