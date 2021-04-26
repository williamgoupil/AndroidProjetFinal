package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import DatabaseHelper.DatabaseHelper;
import model.Categorie;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Categorie c = new Categorie();

        c.setNom("MOTOR");

        db = DatabaseHelper.getInstance(this);

        db.addCategorie(c);
    }


}