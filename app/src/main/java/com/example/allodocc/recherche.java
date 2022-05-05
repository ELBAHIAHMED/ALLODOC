package com.example.allodocc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.allodoc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class recherche extends AppCompatActivity {
    ImageView imageBack,déconnecter ;
    Spinner Specialite,TypeConsultation;
    Button chercher;
    FirebaseAuth firebaseAuth ;
    FirebaseFirestore fStore ;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        imageBack=findViewById(R.id.imageBack);
        déconnecter=findViewById(R.id.déconnecter);
        chercher = findViewById(R.id.trouve);
        Specialite=findViewById(R.id.specialite);
        TypeConsultation=findViewById(R.id.typeConsultation);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId= firebaseAuth.getCurrentUser().getUid();

        chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Specialitee = Specialite.getSelectedItem().toString();
                String TypeConsultationn = TypeConsultation.getSelectedItem().toString();
                if(Specialitee.equals("choisir votre spécialité")){
                    Toast.makeText(recherche.this, " merci de choisir une spécialité ", Toast.LENGTH_SHORT).show();
                }
                if(TypeConsultationn.equals("Type de la consulation")){
                    Toast.makeText(recherche.this, " merci de choisir le type de consultation souhaité ", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(getApplicationContext(), Liste_Docteur.class);
                     i.putExtra("Specialite", Specialitee);
                    i.putExtra("TypeConsultation", TypeConsultationn);
                    startActivity(i);
                }}
        });
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),connexion.class));
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