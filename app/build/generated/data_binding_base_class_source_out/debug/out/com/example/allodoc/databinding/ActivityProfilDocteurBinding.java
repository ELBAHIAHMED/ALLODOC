// Generated by view binder compiler. Do not edit!
package com.example.allodoc.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.allodoc.R;
import com.google.android.material.imageview.ShapeableImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfilDocteurBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button changer;

  @NonNull
  public final TextView emailDocteur;

  @NonNull
  public final TextView nomDocteur;

  @NonNull
  public final ShapeableImageView photoProfil;

  @NonNull
  public final EditText prix;

  @NonNull
  public final TextView salam;

  @NonNull
  public final Button save;

  @NonNull
  public final TextView specialite;

  @NonNull
  public final EditText telDocteur;

  @NonNull
  public final Spinner type;

  private ActivityProfilDocteurBinding(@NonNull RelativeLayout rootView, @NonNull Button changer,
      @NonNull TextView emailDocteur, @NonNull TextView nomDocteur,
      @NonNull ShapeableImageView photoProfil, @NonNull EditText prix, @NonNull TextView salam,
      @NonNull Button save, @NonNull TextView specialite, @NonNull EditText telDocteur,
      @NonNull Spinner type) {
    this.rootView = rootView;
    this.changer = changer;
    this.emailDocteur = emailDocteur;
    this.nomDocteur = nomDocteur;
    this.photoProfil = photoProfil;
    this.prix = prix;
    this.salam = salam;
    this.save = save;
    this.specialite = specialite;
    this.telDocteur = telDocteur;
    this.type = type;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfilDocteurBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfilDocteurBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profil_docteur, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfilDocteurBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.changer;
      Button changer = ViewBindings.findChildViewById(rootView, id);
      if (changer == null) {
        break missingId;
      }

      id = R.id.emailDocteur;
      TextView emailDocteur = ViewBindings.findChildViewById(rootView, id);
      if (emailDocteur == null) {
        break missingId;
      }

      id = R.id.nomDocteur;
      TextView nomDocteur = ViewBindings.findChildViewById(rootView, id);
      if (nomDocteur == null) {
        break missingId;
      }

      id = R.id.photoProfil;
      ShapeableImageView photoProfil = ViewBindings.findChildViewById(rootView, id);
      if (photoProfil == null) {
        break missingId;
      }

      id = R.id.prix;
      EditText prix = ViewBindings.findChildViewById(rootView, id);
      if (prix == null) {
        break missingId;
      }

      id = R.id.salam;
      TextView salam = ViewBindings.findChildViewById(rootView, id);
      if (salam == null) {
        break missingId;
      }

      id = R.id.save;
      Button save = ViewBindings.findChildViewById(rootView, id);
      if (save == null) {
        break missingId;
      }

      id = R.id.specialite;
      TextView specialite = ViewBindings.findChildViewById(rootView, id);
      if (specialite == null) {
        break missingId;
      }

      id = R.id.telDocteur;
      EditText telDocteur = ViewBindings.findChildViewById(rootView, id);
      if (telDocteur == null) {
        break missingId;
      }

      id = R.id.type;
      Spinner type = ViewBindings.findChildViewById(rootView, id);
      if (type == null) {
        break missingId;
      }

      return new ActivityProfilDocteurBinding((RelativeLayout) rootView, changer, emailDocteur,
          nomDocteur, photoProfil, prix, salam, save, specialite, telDocteur, type);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
