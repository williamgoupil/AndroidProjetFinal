package com.example.projetpiece;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccueilActivity extends AppCompatActivity {

    Button btLogout;
    TextView tvCourriel;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btLogout = findViewById(R.id.btnLogout);
        tvCourriel = findViewById(R.id.txtCourriel);
        sessionManager = new SessionManager(getApplicationContext());

        String sCourriel = sessionManager.getCourriel();
        tvCourriel.setText(sCourriel);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Déconnexion");
                builder.setMessage("Êtes-vous certain de vouloir vous déconnecter?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.setLogin(false);
                        sessionManager.setCourriel("");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        Button buttonInventaire = (Button) findViewById(R.id.buttonInventaire);
        buttonInventaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, InventaireActivity.class);
                startActivity(intent);
            }
        });
        Button buttonEmprunt = (Button) findViewById(R.id.buttonEmprunt);
        buttonEmprunt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(AccueilActivity.this, EmpruntActivity.class);
                startActivity(intent);
            }
        });
    }

}