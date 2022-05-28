package com.example.allodocc.Utilisateur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allodoc.R;
import com.example.allodocc.Docteur.DataModal;
import com.example.allodocc.Docteur.Docteur_Profil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Patient_Adapter  extends ArrayAdapter<UserModel> {

    public Patient_Adapter(@NonNull Context context, ArrayList<UserModel> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the
        // layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_patient_adapter, parent, false);
        }

        // after inflating an item of listview item
        // we are getting data from array list inside
        // our modal class.
        UserModel UserModel = getItem(position);

        // initializing our UI components of list view item.
        TextView nomComplet = listitemView.findViewById(R.id.PatientNom);
        TextView Address = listitemView.findViewById(R.id.PatientsAdress);
        Button Accepter = listitemView.findViewById(R.id.Accepter);
        Button Refuser = listitemView.findViewById(R.id.Refuser);

        // after initializing our items we are
        // setting data to our view.
        // below line is use to set data to our text view.
        nomComplet.setText(UserModel.getNomComplet());
        Address.setText(UserModel.getAddress());

        // in below line we are using Picasso to
        // load image from URL in our Image VIew.
        String UID = UserModel.getUID();
        StorageReference storageReference ;
        storageReference= FirebaseStorage.getInstance().getReference();


        // Picasso.get().load(dataModal.getImgUrl()).into(courseIV);

        // below line is use to add item click listener
        // for our item of list view.
//        listitemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // on the item click on our list view.
//                // we are displaying a toast message.
//
//                Intent intent = new Intent(getContext(),Liste_Patients.class);
//                String Name = UserModel.getName();
//                String Address = UserModel.getAddress();
//                String UID = UserModel.getUid();
//
//                //  StorageReference storageReference = dataModal.getStorageReference();
//                intent.putExtra("Name", Name);
//                intent.putExtra("Specialite",Specialite);
//                intent.putExtra("Prix",Prix);
//                intent.putExtra("UID",UID);
//                intent.putExtra("typeConsultation",typeConsultation);
//                getContext().startActivity(intent);
//
//                Toast.makeText(getContext(), "Item clicked is : " + dataModal.getName(), Toast.LENGTH_SHORT).show();
////
//            }
//        });

        Accepter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
        return listitemView;
    }
}