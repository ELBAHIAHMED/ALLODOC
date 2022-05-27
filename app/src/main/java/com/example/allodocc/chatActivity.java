package com.example.allodocc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.allodoc.R;
import com.example.allodoc.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.DocumentType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class chatActivity extends AppCompatActivity {
    private ActivityChatBinding binding ;
TextView textNamee ;
ImageView imageBack ;
FrameLayout layoutSend ;
RecyclerView chatRecyclerView ;
EditText inputMessage ;
private List<chatMessage> chatMessages ;
private chatAdapter chatAdapter ;
private PreferenceManager preferenceManager;
private FirebaseFirestore database ;
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
StorageReference storageReference ;
ProgressBar progressBar ;
ImageView imageProfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        textNamee = findViewById(R.id.textName);
        imageBack = findViewById(R.id.imageBack);
        inputMessage = findViewById(R.id.inputMessage);
        layoutSend = findViewById(R.id.layoutSend);
        progressBar = findViewById(R.id.progressBar);
        imageProfile= findViewById(R.id.imageProfile);


        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        Intent intent = getIntent();
        String UID = intent.getStringExtra("UIDreceiver");
        String name = intent.getStringExtra("name");

        textNamee.setText(name);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Docteur_Profil.class));
            }
        });
        setListeners();
        init();
        ListenMessages();

    }
    private  void init (){
        Intent intent = getIntent();

        String UIDreceiver = intent.getStringExtra("UIDreceiver");
        chatMessages = new ArrayList<>() ;
        firebaseAuth =FirebaseAuth.getInstance();

        String UIDsender= firebaseAuth.getCurrentUser().getUid();
        chatAdapter = new chatAdapter(
                chatMessages,UIDsender,UIDreceiver);
        chatRecyclerView.setAdapter(chatAdapter);
        firebaseFirestore= FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        String UID = firebaseAuth.getCurrentUser().getUid();
        StorageReference profileRef = storageReference.child("docteurs/"+UID+"/profile.jpg") ;
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               Picasso.get().load(uri).into(imageProfile);
            }
        });
        database = FirebaseFirestore.getInstance();

    }
    private void sendMessage (){
        HashMap<String,Object> message = new HashMap<>();
        firebaseAuth =FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String UIDreceiver = intent.getStringExtra("UIDreceiver");
        String UIDsender= firebaseAuth.getCurrentUser().getUid();
        message.put("UIDsender",UIDsender);
        message.put("UIDreceiver",UIDreceiver);
        message.put("dateMessage",new Date());
        message.put("message",inputMessage.getText().toString());

        database.collection("chat").add(message);


    }
    private void ListenMessages(){
        firebaseAuth =FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String UIDreceiver = intent.getStringExtra("UIDreceiver");
        String UIDsender= firebaseAuth.getCurrentUser().getUid();
        database.collection("chat")
                .whereEqualTo("UIDsender",UIDsender)
                .whereEqualTo("UIDreceiver",UIDreceiver)
                .addSnapshotListener(eventListener);
        database.collection("chat")
                .whereEqualTo("UIDsender",UIDreceiver)
                .whereEqualTo("UIDreceiver",UIDsender)
                .addSnapshotListener(eventListener);
    }
    private final EventListener<QuerySnapshot> eventListener =(value,error) -> {
        firebaseAuth =FirebaseAuth.getInstance();
        String UIDsender= firebaseAuth.getCurrentUser().getUid();

        if (error !=null){
            return;
        }
        if(value !=null){
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()){
                if(documentChange.getType()== DocumentChange.Type.ADDED){
                    chatMessage cHatMessage = new chatMessage();
                    Intent intent = getIntent();
                    String UIDreceiver = intent.getStringExtra("UIDreceiver");
                    cHatMessage.senderId=documentChange.getDocument().getString("UIDsender");
                    cHatMessage.receiverId=documentChange.getDocument().getString("UIDreceiver");
                    cHatMessage.message=documentChange.getDocument().getString("message");
                    cHatMessage.dateTime=getReadableDateTime(documentChange.getDocument().getDate("dateMessage"));
                    cHatMessage.dateObject=documentChange.getDocument().getDate("dateMessage");
                    chatMessages.add(cHatMessage);
                }
            }
            Collections.sort(chatMessages,(obj1,obj2)->obj1.dateObject.compareTo(obj2.dateObject));
            if(count==0){
                chatAdapter.notifyDataSetChanged();
            }else {
                chatAdapter.notifyItemRangeChanged(chatMessages.size(),chatMessages.size());
                chatRecyclerView.smoothScrollToPosition(chatMessages.size()-1);
            }
            chatRecyclerView.setVisibility(View.VISIBLE);

        }
        progressBar.setVisibility(View.GONE);
    };

    private void setListeners(){
        //binding.imageBack.setOnClickListener(v -> onBackPressed());
        layoutSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                inputMessage.getText().clear();
            }
        });

    }
    private String getReadableDateTime(Date date ){
        return new SimpleDateFormat("MMMM dd , yyyy - hh:mm a", Locale.getDefault()).format(date);
    }
}