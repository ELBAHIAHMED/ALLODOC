package com.example.allodocc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allodoc.R;

public class chatActivity extends AppCompatActivity {
TextView textName ;
ImageView imageBack ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        textName = findViewById(R.id.textName);
        imageBack = findViewById(R.id.imageBack);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String UID = intent.getStringExtra("UID");
        String name = intent.getStringExtra("Name");
        textName.setText(name);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Docteur_Profil.class));
            }
        });
    }
}