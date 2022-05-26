package com.example.allodocc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.allodoc.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CoursesLVAdapter extends ArrayAdapter<DataModal> {

    // constructor for our list view adapter.
    public CoursesLVAdapter(@NonNull Context context, ArrayList<DataModal> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.image_lv_item, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        DataModal dataModal = getItem(position);

        // initializing our UI components of list view item.
        TextView nameTV = listitemView.findViewById(R.id.Nom);
        TextView Specialite = listitemView.findViewById(R.id.Specialite);
        TextView Prix = listitemView.findViewById(R.id.Prix);
        ImageView courseIV = listitemView.findViewById(R.id.idIVimage);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        nameTV.setText(dataModal.getName());
        Specialite.setText(dataModal.getSpecialite());
        Prix.setText(dataModal.getPrix());

        // in below line we are using Picasso to
        // load image from URL in our Image VIew.
        String UID = dataModal.getUID();
        StorageReference storageReference ;
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("docteurs/"+UID+"/profile.jpg") ;
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(courseIV);
            }
        });

       // Picasso.get().load(dataModal.getImgUrl()).into(courseIV);

        // below line is use to add item click listener
        // for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view.
                // we are displaying a toast message.

                Intent intent = new Intent(getContext(), Docteur_Profil.class);
                String Name = dataModal.getName();
                String Specialite = dataModal.getSpecialite();
                String Prix = dataModal.getPrix();
                String UID = dataModal.getUID();
                String typeConsultation = dataModal.getTypeConsultation();

              //  StorageReference storageReference = dataModal.getStorageReference();
                intent.putExtra("Name", Name);
                intent.putExtra("Specialite",Specialite);
                intent.putExtra("Prix",Prix);
                intent.putExtra("UID",UID);
                intent.putExtra("typeConsultation",typeConsultation);
                getContext().startActivity(intent);

                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getName(), Toast.LENGTH_SHORT).show();
//
            }
        });
        return listitemView;
    }
}
