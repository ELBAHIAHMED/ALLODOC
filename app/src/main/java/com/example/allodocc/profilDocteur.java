package com.example.allodocc;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.allodoc.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class profilDocteur extends AppCompatActivity {
    TextView nomDocteur , emailDocteur , specialite , salut;
    EditText telDocteur , prix ;
    Button save ;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference ;
    Spinner consultation ;

    ImageView photoProfil ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_docteur);
        consultation= findViewById(R.id.type);
        salut = findViewById(R.id.salam);
        nomDocteur = findViewById(R.id.nomDocteur);
        emailDocteur = findViewById(R.id.emailDocteur);
        specialite = findViewById(R.id.specialite);
        telDocteur = findViewById(R.id.telDocteur);
        photoProfil = findViewById(R.id.photoProfil);
        storageReference= FirebaseStorage.getInstance().getReference();
        prix = findViewById(R.id.prix);
        save = findViewById(R.id.save);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        String UID = firebaseAuth.getCurrentUser().getUid();
       StorageReference profileRef = storageReference.child("docteurs/"+UID+"/profile.jpg") ;
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(photoProfil);
            }
        });
        photoProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ouvrir le galerie
                Intent ouvrirGalerie = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(ouvrirGalerie,1000);
            }
        });
        DocumentReference documentReference = firebaseFirestore.collection("Docteur").document(UID);
        if (ContextCompat.checkSelfPermission(profilDocteur.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(profilDocteur.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }

        DocumentReference documentReferencee = firebaseFirestore.collection("Docteur").document(UID);
        documentReferencee.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                nomDocteur.setText(value.getString("name"));
                salut.setText("Salut "+value.getString("name"));
                emailDocteur.setText(value.getString("email"));
                telDocteur.setText(value.getString("Telephone"));
                prix.setText(value.getString("prix"));

                specialite.setText(value.getString("Specialite"));
                if (value.getString("Telephone")!=null){
                    telDocteur.setText(value.getString("Telephone"));}
                if(value.getString("Prix")!=null){
                    prix.setText(value.getString("Prix"));}


            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> user = new HashMap<>();
                //donnée d'un utilisateur
                //TODO : ajouter le numéro de téléphone et séparer nom et prénom dans l'inscription
                String Specialite =specialite.getText().toString();
                String Prix=  prix.getText().toString();
                String phone = telDocteur.getText().toString();
                String email = emailDocteur.getText().toString();
                String nom = nomDocteur.getText().toString();
                String type = consultation.getSelectedItem().toString();
                user.put("name",nom);
                user.put("UID",UID);
                user.put ("email",email);
                user.put ("Telephone",phone);
                user.put ("prix consultation",Prix);
                user.put("Specialite",Specialite);
                user.put("type consultation",type);


                documentReferencee.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(profilDocteur.this, "success", Toast.LENGTH_SHORT).show();

                    }



                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri = data.getData();
                //photoProfil.setImageURI(imageUri);
                chargerImageFirebase(imageUri);

            }
        }
    }
    private  void chargerImageFirebase(Uri imageUri){
        firebaseAuth =FirebaseAuth.getInstance();
        String UID = firebaseAuth.getCurrentUser().getUid();
        StorageReference fileRef = storageReference.child("docteurs/"+UID+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(photoProfil);
                    }
                });
                //Toast.makeText(UserProfil.this,"image chargé",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profilDocteur.this,"echec",Toast.LENGTH_SHORT).show();
            }
        });
    }

}