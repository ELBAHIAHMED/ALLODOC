package com.example.allodocc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allodoc.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class connexion extends AppCompatActivity {
    TextView compte , mdpOblié;
    EditText email , motDePasse;
    Button seConnecter;
    ProgressBar enCours ;
    FirebaseAuth firebaseAuth ;
    FirebaseFirestore firebaseFirestore;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private TextView textViewUser;
    private LoginButton loginButton;
    private FirebaseAuth.AuthStateListener  authStateListener;
    private static final String TAG= "FacebookAuthentification";
    private AccessTokenTracker accessTokenTracker;
    private ImageView mLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        email = findViewById(R.id.email);
        motDePasse = findViewById(R.id.motDePasse);
        compte = findViewById(R.id.compte);
        enCours = findViewById(R.id.enCours);
        seConnecter = findViewById(R.id.seConnecter);
        firebaseAuth = FirebaseAuth.getInstance();
        mdpOblié = findViewById(R.id.mdpOblié);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        textViewUser= findViewById(R.id.textView_ID);
        loginButton= findViewById(R.id.login_facebook);
        loginButton.setReadPermissions("email","public_Profile");
        mLogo=findViewById(R.id.IMAGE_Logo);
        mCallbackManager=CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "OnSucsess" +loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel" );
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError" +error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user !=null){
                    updateUI(user);

              }
                else{
                    updateUI(null);
                }

            }
        };
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null){
                    mFirebaseAuth.signOut();
                }


            }
        };



        seConnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString().trim();
                String MotDePasse = motDePasse.getText().toString().trim();
                if (TextUtils.isEmpty(Email)){
                    email.setError("merci d'entrer votre email");
                    return ;
                }
                if (TextUtils.isEmpty(MotDePasse)){
                    motDePasse.setError("merci d'entrer votre mot de passe");
                    return ;
                }
                enCours.setVisibility(View.VISIBLE);
                //authenfication
                firebaseAuth.signInWithEmailAndPassword(Email,MotDePasse).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (Email.equals("admin@gmail.com")){
                                startActivity(new Intent(getApplicationContext(),acceuilADMIN.class));
                            } else{
                            String UID = firebaseAuth.getCurrentUser().getUid();
                            firebaseFirestore= FirebaseFirestore.getInstance();
                            firebaseFirestore.collection("Docteur").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {

                                            String UidConnecté = d.getString("UID");
                                            if(UidConnecté.equals(UID)){
                                                Toast.makeText(connexion.this,"BIENVENUE A alloDOC",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),profilDocteur.class));
                                                break ;
                                            }else{
                                                Toast.makeText(connexion.this,"BIENVENUE A alloDOC",Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(getApplicationContext(), recherche.class));
                                            }

                                        }}
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // we are displaying a toast message
                                    // when we get any error from Firebase.
                                    Intent intent = new Intent(getBaseContext(), UserProfil.class);
                                    startActivity(intent);
                                    Toast.makeText(connexion.this, "mochkila", Toast.LENGTH_SHORT).show();
                                }
                            });
                            }
                            }
                    }
                });
            }
        });
        compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),inscription.class));

            }
        });
        //mot de passe oublié
        mdpOblié.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nouveauMdpEmail = new EditText(view.getContext());
                AlertDialog.Builder motDepasseOubliéDialog = new AlertDialog.Builder(view.getContext());
                motDepasseOubliéDialog.setTitle("mot de passe oublié ?");
                motDepasseOubliéDialog.setMessage("entrer votre email pour réinitialiser votre mot de passe");
                motDepasseOubliéDialog.setView(nouveauMdpEmail);
                motDepasseOubliéDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = nouveauMdpEmail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(connexion.this, "Lien envoyé à votre adresse mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(connexion.this, "Erreur ! Lien non envoyé du au "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                motDepasseOubliéDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //la nouvelle fenetre sera fermé
                    }
                });
                motDepasseOubliéDialog.create().show();
            }
        });
    }

     private void handleFacebookToken(AccessToken token){
         Log.d(TAG, "handleFacebookToken" +token);
         AuthCredential credential= FacebookAuthProvider.getCredential(token.getToken());
         mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     Log.d(TAG, "sign in with credential successful");
                     FirebaseUser user = mFirebaseAuth.getCurrentUser();
                     updateUI(user);
                 }
                 else{
                     Log.d(TAG, "sign in with credential successful", task.getException());
                     Toast.makeText(connexion.this, "Authentification failed", Toast.LENGTH_SHORT);
                     updateUI(null);

                 }

             }
         });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateUI(FirebaseUser user) {
        if(user !=null){
            textViewUser.setText(user.getDisplayName());
            if (user.getPhotoUrl() !=null){
                String phototUrl= user.getPhotoUrl().toString();
                phototUrl= phototUrl+ "?type=large";
                Picasso.get().load(phototUrl).into(mLogo);
            }
        }
        else{
            textViewUser.setText("");
            mLogo.setImageResource(R.drawable.logo);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener !=null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }

    }
}