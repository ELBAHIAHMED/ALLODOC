// Generated by view binder compiler. Do not edit!
package com.example.allodoc.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.allodoc.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRechercheBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatImageView buttonProfil;

  @NonNull
  public final AppCompatImageView dConnecter;

  @NonNull
  public final TextView email;

  @NonNull
  public final Spinner specialite;

  @NonNull
  public final TextView textName;

  @NonNull
  public final Button trouve;

  @NonNull
  public final Spinner typeConsultation;

  @NonNull
  public final Button verificationEmail;

  private ActivityRechercheBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatImageView buttonProfil, @NonNull AppCompatImageView dConnecter,
      @NonNull TextView email, @NonNull Spinner specialite, @NonNull TextView textName,
      @NonNull Button trouve, @NonNull Spinner typeConsultation,
      @NonNull Button verificationEmail) {
    this.rootView = rootView;
    this.buttonProfil = buttonProfil;
    this.dConnecter = dConnecter;
    this.email = email;
    this.specialite = specialite;
    this.textName = textName;
    this.trouve = trouve;
    this.typeConsultation = typeConsultation;
    this.verificationEmail = verificationEmail;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRechercheBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRechercheBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_recherche, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRechercheBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonProfil;
      AppCompatImageView buttonProfil = ViewBindings.findChildViewById(rootView, id);
      if (buttonProfil == null) {
        break missingId;
      }

      id = R.id.déconnecter;
      AppCompatImageView dConnecter = ViewBindings.findChildViewById(rootView, id);
      if (dConnecter == null) {
        break missingId;
      }

      id = R.id.email;
      TextView email = ViewBindings.findChildViewById(rootView, id);
      if (email == null) {
        break missingId;
      }

      id = R.id.specialite;
      Spinner specialite = ViewBindings.findChildViewById(rootView, id);
      if (specialite == null) {
        break missingId;
      }

      id = R.id.textName;
      TextView textName = ViewBindings.findChildViewById(rootView, id);
      if (textName == null) {
        break missingId;
      }

      id = R.id.trouve;
      Button trouve = ViewBindings.findChildViewById(rootView, id);
      if (trouve == null) {
        break missingId;
      }

      id = R.id.typeConsultation;
      Spinner typeConsultation = ViewBindings.findChildViewById(rootView, id);
      if (typeConsultation == null) {
        break missingId;
      }

      id = R.id.verificationEmail;
      Button verificationEmail = ViewBindings.findChildViewById(rootView, id);
      if (verificationEmail == null) {
        break missingId;
      }

      return new ActivityRechercheBinding((ConstraintLayout) rootView, buttonProfil, dConnecter,
          email, specialite, textName, trouve, typeConsultation, verificationEmail);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}