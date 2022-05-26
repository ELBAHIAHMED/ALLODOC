package com.example.allodocc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allodoc.R;

public class acceuilADMIN extends AppCompatActivity {
    Button nvdocteur ;
    ImageView déconnecter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_admin);
        déconnecter=findViewById(R.id.déconnecter);
        nvdocteur = findViewById(R.id.nvDOCTEUR);
        nvdocteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ajoutDOCTEUR.class));
            }
        });
        déconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),connexion.class));

            }
        });

    }
}