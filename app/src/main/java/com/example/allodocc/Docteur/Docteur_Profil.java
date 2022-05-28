package com.example.allodocc.Docteur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allodoc.R;
import com.example.allodocc.Validation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Docteur_Profil extends AppCompatActivity {
    TextView DocName;
    TextView SpecialiteDoc;
    TextView PrixDoc;
    ImageView imageDoc;
    Button MettreEnContact ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docteur_profil);
        DocName = findViewById(R.id.NameDoc);
        SpecialiteDoc=findViewById(R.id.SpecialiteDoc);
        PrixDoc=findViewById(R.id.NumPrix);
        imageDoc=findViewById(R.id.imgDoc);
        MettreEnContact = findViewById(R.id.MettreEnContact);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        String Specialite = intent.getStringExtra("Specialite");
        String Prix = intent.getStringExtra("Prix");
        String UID = intent.getStringExtra("UID");
        String typeConsultation = intent.getStringExtra("typeConsultation");
        StorageReference storageReference ;
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("docteurs/"+UID+"/profile.jpg") ;
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageDoc);
            }
        });

        SpecialiteDoc.setText(Specialite);
        PrixDoc.setText(Prix);
        DocName.setText("Dr "+Name);
        MettreEnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeConsultation.equals("en ligne")){




                }else  {

                    Intent intent1 = new Intent(getApplicationContext(), Validation.class
                    );
                    //  StorageReference storageReference = dataModal.getStorageReference();
                    intent1.putExtra("Name", Name);
                    intent1.putExtra("Specialite",Specialite);
                    intent1.putExtra("Prix",Prix);
                    intent1.putExtra("UID",UID);
                    intent1.putExtra("typeConsultation",typeConsultation);
                    startActivity(intent1);

                }

            }
        });

    }
}