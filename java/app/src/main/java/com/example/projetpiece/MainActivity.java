package com.example.projetpiece;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.concurrent.ExecutionException;

import DatabaseHelper.DatabaseHelper;

public class MainActivity extends AppCompatActivity  {


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

        downloadDBInfo();
    }

    public void downloadDBInfo(){

        int responseCodeBD = checkBDVersion();
        CharSequence text = "";
        if( responseCodeBD == 0 ) {
            downloadQuantity();
            text = "BD a jour";
        } else {
            //insert le nouveaux Code de la BD
            downloadFullBD();
            text = "BD mise a jour";
        }

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public int checkBDVersion(){
        String response="";
        int responseCode=1;

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-checkBDVersion/4";
        try {
            response = new webApiRequest().execute(urlCancel).get();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject obj = (JSONObject) parse.parse(response);


            responseCode =  Integer.parseInt( obj.get("idVersion").toString());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return responseCode;
        }


    }
    public void downloadQuantity(){
        String response="";

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-list";
        try {
            response = new webApiRequest().execute(urlCancel).get();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(response);

            JSONArray arr = (JSONArray) data_obj.get("lstPiece");

            for (int i = 0; i < arr.size(); i++) {

                JSONObject new_obj = (JSONObject) arr.get(i);
                //Querry SQL pour insert les nouvelle quantités

            }



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void downloadFullBD(){
        String response="";

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-listeComplete";
        try {
            response = new webApiRequest().execute(urlCancel).get();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(response);

            JSONArray arr = (JSONArray) data_obj.get("lstPiece");

            for (int i = 0; i < arr.size(); i++) {

                JSONObject new_obj = (JSONObject) arr.get(i);
                //Querry SQL pour insert la nouvelle DB

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
/*
//Test API P-A
    //Annule une commande aussi ^^


        String responsecode="";
        try {
            //URL pour cancel une certaine commande
            String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-annuleremprunt/{IdCommande}";
            responsecode = new webApiRequest().execute(urlCancel).get();<
            //1 pas réussi
            //0 réussi
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Context context = getApplicationContext();
        CharSequence text = responsecode;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

 */