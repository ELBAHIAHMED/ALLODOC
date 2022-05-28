package com.example.allodocc.Docteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.allodoc.R;
import com.example.allodocc.Utilisateur.Liste_Patients;

public class Accueil_Doc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil_doc);
        ImageButton Profil = findViewById(R.id.Profil_Button);
        Button Liste_Patients = findViewById(R.id.Liste_Patients);

        Profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Docteur_Profil.class));
            }
        });

        Liste_Patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(), com.example.allodocc.Utilisateur.Liste_Patients.class));
            }
        });

    }
}