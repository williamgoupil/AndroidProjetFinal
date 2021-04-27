package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Categorie c = new Categorie();
        c.setNom("MOTOR");

        Piece p = new Piece();
        p.setCategorie(1);
        p.setDescription("Ceci est un moteur electrique");
        p.setNom("Moteur Electrique");
        p.setQTEDisponible(5);


        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        String StrDateDebut = new String("2020-04-27");
        Date d1 = null;
        try {
            d1 = formatter.parse(StrDateDebut);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
        Empruntpersonnel e = new Empruntpersonnel();

        e.setPiece(1);
        e.setQTEEmprunter(2);
        e.setEtatCourant("Emprunter");
        e.setDateDemande(d1);
        e.setDateFin(d1);

        db = DatabaseHelper.getInstance(this);

        db.addCategorie(c);
        db.addPiece(p);
        db.addEmprunt(e);
    }
    public void LaunchEmprunt(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, EmpruntActivity.class);
        startActivity(intent);
    }

}