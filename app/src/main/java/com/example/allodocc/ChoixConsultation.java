package com.example.allodocc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allodoc.R;
import com.example.allodocc.Utilisateur.UserProfil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChoixConsultation extends AppCompatActivity {
    TextView email ;
    Button verificationEmail ;
    FirebaseAuth firebaseAuth ;
    FirebaseFirestore fStore ;
    String userId;
    ImageButton btnprofil;
    Button consultationADomicile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_consultation);
        verificationEmail = findViewById(R.id.verificationEmail);
        email = findViewById(R.id.email);
        consultationADomicile = findViewById(R.id.consultationADomicile);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnprofil = findViewById(R.id.buttonProfil);
        userId= firebaseAuth.getCurrentUser().getUid();
        FirebaseUser utilisateur = firebaseAuth.getCurrentUser();
        btnprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfil.class));
            }
        });

        consultationADomicile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  startActivity(new Intent(getApplicationContext(),Liste_Docteur.class));
            }
        });

        //verification de l'adresse mail
        if(!utilisateur.isEmailVerified()){
            email.setVisibility(View.VISIBLE);
            verificationEmail.setVisibility(View.VISIBLE);
            verificationEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    utilisateur.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(view.getContext(), "Email de vérification a été envoyé ! ", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "Erreur : Email non envoyé du à" +e.getMessage());
                        }
                    });
                }
            });
                }


        DocumentReference documentReference=fStore.collection("users").document(userId);

    }
}