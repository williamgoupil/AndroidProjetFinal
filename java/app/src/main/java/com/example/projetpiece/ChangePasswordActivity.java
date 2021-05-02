/**
 * Nom de classe : ChangePasswordActivity
 * Description   : Activité qui permet de changer le mot de passe de l'utilisateur
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Olivier Vigneault
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *  2 mai 2021      Équipe entière      approuvé
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  26 Avril 2021   Olivier             création du fichier
 *  27 Avril 2021   Olivier             création des champs editText et du bouton
 *  29 Avril 2021   Olivier             ajout des requêtes à l'API
 *  ****************************************/
package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {

    TextView tvError;
    EditText etMotDePasse, etMotDePasseConfirmation;
    Button btConfirmer;
    Requests requests;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sessionManager = new SessionManager(getApplicationContext());

        requests = new Requests();

        tvError = findViewById(R.id.txtErreurChangeMDP);
        etMotDePasse = findViewById(R.id.etChangeMDP);
        etMotDePasseConfirmation = findViewById(R.id.etConfirmMDP);
        btConfirmer = findViewById(R.id.btnConfirmer);

        btConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sMotDePasse = etMotDePasse.getText().toString().trim();
                String sConfirmation = etMotDePasseConfirmation.getText().toString().trim();

                //si un des champs est vide
                if (sMotDePasse.equals("") || sConfirmation.equals("")) {
                    if (sMotDePasse.equals("")) {
                        etMotDePasse.setError("Entrez votre nouveau mot de passe");
                    }
                    if (sConfirmation.equals("")) {
                        etMotDePasseConfirmation.setError("Confirmer le nouveau mot de passe");
                    }
                }

                //si les deux champs ne sont pas équivalents -> pas le même mot de passe entré
                else if (!sMotDePasse.equals(sConfirmation)) {
                    tvError.setTextColor(Color.parseColor("#FF0000"));
                    tvError.setText("Les mots de passes doivent être identiques!");
                }
                else
                {
                    HashMap<String, String> data = requests.verifyPassword(sessionManager.getCourriel(), sMotDePasse);
                    //si le nouveau mot de passe est le même mot de passe que dans la BD
                    if ((data.get("samePassword")).equals("true")) {
                        tvError.setTextColor(Color.parseColor("#FF0000"));
                        tvError.setText("Le mot de passe doit être différent du mot de passe précédent!");
                    }
                    else {
                        requests.changePassword(sessionManager.getCourriel(), sMotDePasse);
                        startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
                    }
                }
            }
        });
    }
}