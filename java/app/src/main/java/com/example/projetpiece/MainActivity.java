package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import DatabaseHelper.DatabaseHelper;
import model.Categorie;
import model.Empruntpersonnel;
import model.Piece;

import static java.time.LocalDate.now;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "test";
    DatabaseHelper db;

    TextView tvError;
    EditText etCourriel, etMotDePasse;
    Button btLogin, btOubliMDP;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvError = findViewById(R.id.txtErreur);
        etCourriel = findViewById(R.id.inputCourriel);
        etMotDePasse = findViewById(R.id.inputMDP);
        btLogin = findViewById(R.id.btnLogin);
        btOubliMDP = findViewById(R.id.btnMDPoubli);

        sessionManager = new SessionManager(getApplicationContext());

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCourriel = etCourriel.getText().toString().trim();
                String sMotDePasse = etMotDePasse.getText().toString().trim();

                if (sCourriel.equals("") || sMotDePasse.equals("")) {
                    if (sCourriel.equals("")) {
                        etCourriel.setError("Entrez l'adresse courriel");
                    }
                    if (sMotDePasse.equals("")) {
                        etMotDePasse.setError("Entrez le mot de passe");
                    }
                }

                //si l'api nous retourne vrai, c'est que le courriel et le mot de passe sont les bons e.g id = authenticate(sCourriel, sMotdePasse)
                else if (sMotDePasse.equals("root")) {
                    sessionManager.setLogin(true);
                    sessionManager.setCourriel(sCourriel);
                    boolean changePassword = true;

                    if (changePassword) {
                        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
                    }
                    finish();
                }
                else {
                    tvError.setTextColor(Color.parseColor("#FF0000"));
                    tvError.setText("informations d'identifications invalides");
                }
            }
        });

        if (sessionManager.getLogin()) {
            boolean changePassword = false;
            if (changePassword) {
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
            }
            finish();
        }

        btOubliMDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                finish();
            }
        });

        Button buttonInventaire = (Button) findViewById(R.id.buttonInventaire);
        buttonInventaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InventaireActivity.class);
                startActivity(intent);
            }
        });
        Button buttonEmprunt = (Button) findViewById(R.id.buttonEmprunt);
        buttonEmprunt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmpruntActivity.class);
                startActivity(intent);
            }
        });
    }
}
