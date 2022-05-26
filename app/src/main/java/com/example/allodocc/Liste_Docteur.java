package com.example.allodocc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.allodoc.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Liste_Docteur extends AppCompatActivity {
    // creating a variable for our list view,
    // arraylist and firebase Firestore.
    ListView coursesLV;
    ArrayList<DataModal> dataModalArrayList;
    FirebaseFirestore db;
    ImageView imageBack ;
    StorageReference storageReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_docteur);
      //  below line is use to initialize our variables
        coursesLV = findViewById(R.id.idLVCourses);
        imageBack = findViewById(R.id.imageBack);
        dataModalArrayList = new ArrayList<>();
        Intent intent = getIntent();
        String Specialite = intent.getStringExtra("Specialite");
        String TypeConsultation = intent.getStringExtra("TypeConsultation");


        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // here we are calling a method
        // to load data in our list view.
        loadDatainListview(TypeConsultation,Specialite);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChoixConsultation.class));

            }
        });
    }

    //whereArrayContains("Specialite",Specialite)
    //.whereArrayContains("type consultation",TypeConsultation)
    private void loadDatainListview(String TypeConsultation , String Specialite) {

        // below line is use to get data from Firebase
        // firestore using collection in android.
        db.collection("Docteur").whereEqualTo("type consultation",TypeConsultation)
                .whereEqualTo("Specialite",Specialite)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                DataModal dataModal = d.toObject(DataModal.class);
                                dataModal.setTypeConsultation(TypeConsultation);
                                dataModalArrayList.add(dataModal);
                            }
                            // after that we are passing our array list to our adapter class.
                            CoursesLVAdapter adapter = new CoursesLVAdapter(Liste_Docteur.this, dataModalArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            coursesLV.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(Liste_Docteur.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Intent intent = new Intent(getBaseContext(), UserProfil.class);
                startActivity(intent);
                Toast.makeText(Liste_Docteur.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}