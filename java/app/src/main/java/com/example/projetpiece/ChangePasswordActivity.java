package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordActivity extends AppCompatActivity {

    TextView tvError;
    EditText etMotDePasse, etMotDePasseConfirmation;
    Button btConfirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tvError = findViewById(R.id.txtErreurChangeMDP);
        etMotDePasse = findViewById(R.id.etChangeMDP);
        etMotDePasseConfirmation = findViewById(R.id.etConfirmMDP);
        btConfirmer = findViewById(R.id.btnConfirmer);

        btConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMotDePasse = etMotDePasse.getText().toString().trim();
                String sConfirmation = etMotDePasseConfirmation.getText().toString().trim();

                if (sMotDePasse.equals("") || sConfirmation.equals("")) {
                    if (sMotDePasse.equals("")) {
                        etMotDePasse.setError("Entrez votre nouveau mot de passe");
                    }
                    if (sConfirmation.equals("")) {
                        etMotDePasseConfirmation.setError("Confirmer le nouveau mot de passe");
                    }
                }

                else if (!sMotDePasse.equals(sConfirmation)) {
                    tvError.setTextColor(Color.parseColor("#FF0000"));
                    tvError.setText("Les mots de passes doivent être identiques!");
                }

                else {
                    startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
                }
            }
        });
    }
}