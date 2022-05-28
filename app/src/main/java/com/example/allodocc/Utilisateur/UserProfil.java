package com.example.allodocc.Utilisateur;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allodoc.R;
import com.example.allodocc.Maps;
import com.google.android.gms.location.places.ui.PlacePicker;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class UserProfil extends AppCompatActivity implements LocationListener {
    TextView textSalut;
    EditText name;
    EditText Email;
    EditText Phone;
    EditText Ville;
    Button localisation;
    Button save;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference ;
    FirebaseAuth firebaseAuth;
    LocationManager locationManager;
    ImageView photoProfil ;

    int PLACE_PICKER_REQUEST =1;
    ActivityResultLauncher<PlacePicker.IntentBuilder> mGetContent;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profil);
        photoProfil = findViewById(R.id.photoProfil);
       textSalut = findViewById(R.id.Salut);
        name=findViewById(R.id.EditFULLName);
        Email = findViewById(R.id.EditEmail);
        Phone=findViewById(R.id.EditPhone);
        Ville=findViewById(R.id.EditVille);
        localisation=findViewById(R.id.Localisation);
        save=findViewById(R.id.save);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        String UID = firebaseAuth.getCurrentUser().getUid();
        StorageReference profileRef = storageReference.child("users/"+UID+"/profile.jpg") ;
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               Picasso.get().load(uri).into(photoProfil);
            }
        });
        DocumentReference documentReference = firebaseFirestore.collection("users").document(UID);
        if (ContextCompat.checkSelfPermission(UserProfil.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UserProfil.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
//        localisation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),UserProfil.class));
//            }
//        });
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                name.setText(value.getString("nomCompet"));
                textSalut.setText("Salut "+value.getString("nomCompet"));
                Email.setText(value.getString("email"));
               if (value.getString("Telephone")!=null){
                Phone.setText(value.getString("Telephone"));}
                if(value.getString("Ville")!=null){
                Ville.setText(value.getString("Ville"));}

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> user = new HashMap<>();
                //donnée d'un utilisateur
                //TODO : ajouter le numéro de téléphone et séparer nom et prénom dans l'inscription
                String FULLNAME =name.getText().toString();
                String EMAIL=  Email.getText().toString();
                String PHONE = Phone.getText().toString();
                String VILLE = Ville.getText().toString();
                user.put("nomCompet",FULLNAME);
                user.put ("email",EMAIL);
                user.put ("Telephone",PHONE);
                user.put ("Ville",VILLE);


                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UserProfil.this, "success", Toast.LENGTH_SHORT).show();

                    }



                });
            }
        });


         localisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Maps.class));
               // PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
             //  getLocation();
//

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
        StorageReference fileRef = storageReference.child("users/"+UID+"/profile.jpg");
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
                Toast.makeText(UserProfil.this,"echec",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, (LocationListener) this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(UserProfil.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            Toast.makeText(this, address, Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }


    @Override
    public void onProviderDisabled(String provider) {

    }



}