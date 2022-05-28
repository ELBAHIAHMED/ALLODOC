package com.example.allodocc.Utilisateur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.allodoc.R;
import com.example.allodocc.ChoixConsultation;
import com.example.allodocc.Docteur.CoursesLVAdapter;
import com.example.allodocc.Docteur.DataModal;
import com.example.allodocc.Docteur.Liste_Docteur;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Liste_Patients extends AppCompatActivity {
    ListView coursesLV;
    ArrayList<UserModel> dataModalArrayList;
    FirebaseFirestore db;
    ImageView imageBack ;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference ;
    String[] Pat_UID = new String[8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_patients); coursesLV = findViewById(R.id.PatientsidLVCourses);
        imageBack = findViewById(R.id.PatientsimageBack);
        dataModalArrayList = new ArrayList<>();
//        Intent intent = getIntent();
//        String Specialite = intent.getStringExtra("Specialite");
//        String TypeConsultation = intent.getStringExtra("TypeConsultation");


        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        firebaseAuth =FirebaseAuth.getInstance();
        String UID = firebaseAuth.getCurrentUser().getUid();



        firebaseFirestore= FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("Docteur").document(UID);
//        if (ContextCompat.checkSelfPermission(Liste_Patients.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(Liste_Patients.this,new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION
//            },100);
//        }

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                 Pat_UID[0] = value.get("Patients").toString();
                loadDatainListview(Pat_UID[0]);





            }
        });





        // here we are calling a metho
        // to load data in our list view.

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChoixConsultation.class));

            }
        });
    }

    //whereArrayContains("Specialite",Specialite)
    //.whereArrayContains("type consultation",TypeConsultation)
    private void loadDatainListview(String Patients_UID ) {

//        DocumentReference documentReference1 = firebaseFirestore.collection("users").document(Patients_UID);
        db.collection("users").whereEqualTo("UID",Patients_UID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                UserModel userModel = d.toObject(UserModel.class);
                                dataModalArrayList.add(userModel);
                            }
                            // after that we are passing our array list to our adapter class.
                            Patient_Adapter adapter = new Patient_Adapter(Liste_Patients.this, dataModalArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            coursesLV.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(Liste_Patients.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Intent intent = new Intent(getBaseContext(), UserProfil.class);
                startActivity(intent);
                Toast.makeText(Liste_Patients.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}