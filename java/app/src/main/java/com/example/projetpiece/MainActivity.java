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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import DatabaseHelper.*;

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
        setContentView(R.layout.activity_main);
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
                //si l'api nous retourne vrai, c'est que le courriel et le mot de passe sont les bons e.g id = authenticate(sCourriel, sMotdePasse)
                if ((userInfo.get("authenticated")).equals("true")) {
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

        if (sessionManager.getLogin()) {
            HashMap<String, String> passwordStatus = requests.getPasswordStatus(sessionManager.getCourriel());
            if ((passwordStatus.get("changePassword")).equals("true")) {
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


        bootload();
    }


    public void bootload(){
        downloadDBInfo();

        if(isNetworkAvailable()) {
            //querry pour trouver les commandes pas send
            //send them
        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public void downloadDBInfo(){
        int responseCodeBD = requests.checkBDVersion();
        CharSequence text = "";
        if( responseCodeBD == 0 ) {
            requests.downloadQuantity();
            text = "BD a jour";
        } else {
            //insert le nouveaux Code de la BD
            requests.downloadFullBD();
            text = "BD mise a jour";
        }

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }



}

