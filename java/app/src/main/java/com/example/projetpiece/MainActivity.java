/**
 * Nom de classe : MainActivity
 * Description   : Activité principale qui permet d'authentifier les utilisateurs sur l'application
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
 *  27 Avril 2021   Olivier             création du formulaire de connexion et du bouton
 *  29 Avril 2021   Olivier             ajout des requêtes à l'API
 *  29 Avril 2021 P-A                   Ajout de bootload
 *  ****************************************/

package com.example.projetpiece;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import DatabaseHelper.DatabaseHelper;


public class MainActivity extends AppCompatActivity  {


    private static final String LOG_TAG = "test";
    private static final String CHANNEL_ID = "Gestionnaire_Piece_channel";
    DatabaseHelper db;
    Requests requests;

    TextView tvError;
    EditText etCourriel, etMotDePasse;
    Button btLogin, btOubliMDP;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Pour l'utilisation d'une notification doit etre appellé au debut

        super.onCreate(savedInstanceState);

        //si l'utilisateur s'est déjà authentifié sur l'application
        if (sessionManager.getLogin()) {
            HashMap<String, String> passwordStatus = requests.getPasswordStatus(sessionManager.getCourriel());
            if ((passwordStatus.get("changePassword")).equals("true")) {
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
            }
            finish();
        } else {
            if (isNetworkAvailable()) {
                setContentView(R.layout.activity_main);
            } else {
                setContentView(R.layout.activity_no_internet);
            }
        }

        requests = new Requests();

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
                else {
                    HashMap<String, String> userInfo = requests.authenticateUser(sCourriel, sMotDePasse);

                //si l'api nous retourne vrai, c'est que le courriel et le mot de passe sont les bons
                if ((userInfo.get("authenticated")).equals("true")) {
                    bootload();
                    sessionManager.setLogin(true);
                    sessionManager.setCourriel(sCourriel);
                    sessionManager.setID(userInfo.get("id"));

                    if ((userInfo.get("changePassword")).equals("true")) {
                        startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
                    } else {

                        startActivity(new Intent(getApplicationContext(), AccueilActivity.class));
                    }
                    finish();
                } else {
                    tvError.setTextColor(Color.parseColor("#FF0000"));
                    tvError.setText("informations d'identifications invalides");
                }
            }
            }
        });

        btOubliMDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
                finish();
            }
        });



    }

    /**
     * s'éxécute lorsque l'utilisateur c'est connecté pour telecharger les infos nécéssaire
     */
    public void bootload(){

        db = DatabaseHelper.getInstance(this);

        if(isNetworkAvailable()) {
            downloadDBInfo();
            db.checkUnsent();
            db.loadEmprunt( requests.loadEmprunt(sessionManager.getID()),sessionManager.getID());

          
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void downloadDBInfo(){

        db = DatabaseHelper.getInstance(this);


        int responseCodeBD = requests.checkBDVersion(db.getcurrentDBVersion());
        CharSequence text = "";
        if( responseCodeBD == 0 ) {
            db.loadquantity(requests.downloadQuantity());
            text = "BD a jour";
        } else {

            db.checkUnsent();
            db.trunctateALL();

            db.newBDVersion(responseCodeBD);
            db.loadNewCat(requests.downloadCat());
            db.loadNewInventory(requests.downloadFullBD());
            text = "BD mise a jour";
        }

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



}

