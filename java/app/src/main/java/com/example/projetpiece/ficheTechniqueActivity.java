package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import DatabaseHelper.DatabaseHelper;
import model.Empruntpersonnel;
import model.Piece;

public class ficheTechniqueActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {



    private TextView nomPiece;
    private TextView descriptionPiece;
    private TextView qtePiece;
    private TextView dateChoisirText;

    private EditText QTEDesirer;
    private Button DateRetour;

    private Button btnReserver;
    private Button btnRetour;
    private Date dateChoisie;

    private Date todayDate;

    private String idPiece;


    DatabaseHelper db;

    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_technique);

        init();


        DateRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });


        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnButton();
            }
        });


        btnReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForm();
            }
        });

    }

    private void init(){
        initAttributes();
        initFiche();
        initBuilder();
    }

    private void initFiche(){
        idPiece = getIntent().getExtras().getString("id");
        db = DatabaseHelper.getInstance(this);
        Piece p = db.getOnePieceById(idPiece);
        nomPiece.setText(p.getNom());
        descriptionPiece.setText(p.getDescription());
        qtePiece.setText(String.valueOf(p.getQTEDisponible()));
    }

    private void initAttributes(){
        nomPiece = (TextView) findViewById(R.id.nomPieceDetaille);
        descriptionPiece = (TextView) findViewById(R.id.descriptionPieceDetaillé);
        qtePiece = (TextView) findViewById(R.id.QTEPieceDetaille);
        dateChoisirText = (TextView) findViewById(R.id.dateChoisie);
        QTEDesirer = (EditText) findViewById(R.id.QTEDesire);

        btnRetour = (Button) findViewById(R.id.buttonRetourner);
        btnReserver = (Button) findViewById(R.id.buttonReserver);
        DateRetour = (Button) findViewById(R.id.ButtonDateRetourPrevue);
        Calendar calendar = Calendar.getInstance();
        todayDate = calendar.getTime();
    }


    private void initBuilder(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    private void showDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            dateChoisie = calendar.getTime();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date =  formatter.format(dateChoisie);

            dateChoisirText.setText(date);
    }


    private void returnButton(){
        finish();
    }


    private void checkForm(){
        String qteDesirerString = QTEDesirer.getText().toString();

        //Validé si le champs qte est vide
        if(qteDesirerString.equals("")){
            showAlert("Erreur dans la quantité", "Vous devez entré une quantité");
            return;
        }
        int qteDesirerInt = Integer.parseInt(qteDesirerString);
        int qteDisponible = Integer.parseInt(qtePiece.getText().toString());

        //validé si le champ qte est inférieur à qte disponible et supérieur à 0
        if (qteDesirerInt > qteDisponible || qteDesirerInt == 0){
            showAlert("Erreur dans la quantité", "Vous devez entré une quantité valide");
            return;
        }

        String date = dateChoisirText.getText().toString();

        //Validé si le champs date est vide
        if(date.equals("")){
            showAlert("Erreur dans la date", "Vous devez choisir une date");
            return;
        }

        Calendar calendar = Calendar.getInstance();
        Date todayDate = calendar.getTime();

        //si la date choisie est inférieur au lendemain
        if(!dateChoisie.after(todayDate)){
            showAlert("Erreur dans la date", "Vous devez choisir une date supérieur à aujourd'hui");
            return;
        }

            sendBooking();
    }


    private void sendBooking(){

        Empruntpersonnel e = new Empruntpersonnel(Integer.parseInt(QTEDesirer.getText().toString()),todayDate,dateChoisie,"Envoyé, en attente",Integer.parseInt(idPiece),false);


        int newQTE = Integer.parseInt(qtePiece.getText().toString()) - Integer.parseInt(QTEDesirer.getText().toString());
        db.updateQTE(idPiece,newQTE);
        if(isNetworkAvailable()){
            //Fonction à PA pour envoyer avec l'API


            //e.setEnvoyer(true);
        }

        db.addEmprunt(e);
        finish();
    }



    private void showAlert(String title, String message){
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }



}