package com.example.allodocc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allodoc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class recherche extends AppCompatActivity {
    ImageView buttonProfil,déconnecter ;
    Spinner Specialite,TypeConsultation;
    Button chercher;
    FirebaseAuth firebaseAuth ;
    Button verificationEmail ;
    FirebaseFirestore fStore ;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference ;
    LocationManager locationManager;
    String userId;
    TextView email ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        buttonProfil=findViewById(R.id.buttonProfil);
        déconnecter=findViewById(R.id.déconnecter);
        chercher = findViewById(R.id.trouve);
        Specialite=findViewById(R.id.specialite);
        verificationEmail = findViewById(R.id.verificationEmail);
        TypeConsultation=findViewById(R.id.typeConsultation);
        email = findViewById(R.id.email);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId= firebaseAuth.getCurrentUser().getUid();
        storageReference= FirebaseStorage.getInstance().getReference();
        FirebaseUser utilisateur = firebaseAuth.getCurrentUser();
        StorageReference profileRef = storageReference.child("users/"+userId+"/profile.jpg") ;
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(buttonProfil);
            }
        });
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
        buttonProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UserProfil.class));
            }
        });
        déconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),connexion.class));

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