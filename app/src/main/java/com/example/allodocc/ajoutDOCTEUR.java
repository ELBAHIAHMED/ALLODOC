package com.example.allodocc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allodoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ajoutDOCTEUR extends AppCompatActivity {
    EditText nomDocteur , emailDocteur ,mdpDocteur;
    Spinner specialite ;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore ;
    String docteurID ;
    Button enregistrer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_docteur);
        nomDocteur = findViewById(R.id.nomDocteur);
        emailDocteur = findViewById(R.id.emailDocteur);
        mdpDocteur = findViewById(R.id.mdpDocteur);
        specialite = findViewById(R.id.specialite);
        enregistrer = findViewById(R.id.enregistrer);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        enregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trim supprime les whitespaces
                String Nom =nomDocteur.getText().toString();
                String Email = emailDocteur.getText().toString().trim();
                String MotDePasse = mdpDocteur.getText().toString().trim();
                String Specialite = specialite.getSelectedItem().toString();
                //String Specialite = specialite.getText().toString().trim();
                if (TextUtils.isEmpty(Nom)){
                    nomDocteur.setError("merci d'entrer le nom complet du docteur");
                    return ;
                }
                if (TextUtils.isEmpty(Email)){
                    emailDocteur.setError("merci entrer l'email du docteur");
                    return ;
                }
                if (TextUtils.isEmpty(MotDePasse)){
                    mdpDocteur.setError("merci d'entrer le mot de passe du docteur");
                    return ;
                }
                // if (TextUtils.isEmpty(Specialite)){
                //   specialite.setError("merci d'entrer votre spécialité");
                // return ;
                // }
                if (MotDePasse.length()<6){
                    mdpDocteur.setError("le mot de passe doit contenir au moins 6 caractères");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(Email,MotDePasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //email de vérification
                            FirebaseUser utilisateur = firebaseAuth.getCurrentUser();
                            utilisateur.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ajoutDOCTEUR.this, " docteur ajouté ", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });


                            Toast.makeText(ajoutDOCTEUR.this,"docteur ajouté ",Toast.LENGTH_SHORT).show();
                            docteurID = firebaseAuth.getCurrentUser().getUid();
                            //créer un document pour chaque utilisateur afin de collecter ses informations
                            DocumentReference documentReference = fStore.collection("Docteur").document(docteurID);
                            Map<String,Object> docteur = new HashMap<>();
                            //donnée d'un utilisateur
                            //TODO : ajouter le numéro de téléphone et séparer nom et prénom dans l'inscription
                            docteur.put("name",Nom);
                            docteur.put ("email",Email);
                            docteur.put("Specialite",Specialite);
                            docteur.put("UID",docteurID);

                            documentReference.set(docteur).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Log.d(TAG, "onSuccess: le compte a été créé pour"+ docteurID);

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),acceuilADMIN.class));
                        } else{
                            Toast.makeText(ajoutDOCTEUR.this, "Erreur " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //enCours.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });


    }
}