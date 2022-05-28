package com.example.allodocc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allodoc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Validation extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference ;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);

        TextView textView = findViewById(R.id.Traitement);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        String UID_Patients = firebaseAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        String Specialite = intent.getStringExtra("Specialite");
        String Prix = intent.getStringExtra("Prix");
        String UID = intent.getStringExtra("UID");
        String typeConsultation = intent.getStringExtra("typeConsultation");

        DocumentReference documentReference = firebaseFirestore.collection("Docteur").document(UID);
        Map<String,Object> Docteur = new HashMap<>();
        //donnée d'un utilisateur
        //TODO : ajouter le numéro de téléphone et séparer nom et prénom dans l'inscription
        String Patients[] = new String[20];
        Docteur.put("Name",Name);
        Docteur.put ("Specialite",Specialite);
        Docteur.put ("Prix",Prix);
        Docteur.put ("UID",UID);
        Docteur.put ("typeConsultation",typeConsultation);
        Docteur.put("Patients", Arrays.asList(UID_Patients));



        documentReference.update(Docteur).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Validation.this, "Waiting ", Toast.LENGTH_SHORT).show();

            }



        });




        textView.setText("Votre demande est en cours de traitement chez Dr "+Name);










    }
}