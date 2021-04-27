/**
 * Nom de classe : InventaireActivity
 * Description   : Classe qui s'occupe de la logique pour la fenêtre de l'inventaire
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier
 */
package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import DatabaseHelper.DatabaseHelper;
import model.Piece;

public class InventaireActivity extends AppCompatActivity {


    DatabaseHelper db;

    private RecyclerView recyclerView;
    private List<Piece> listPiece = new ArrayList<>();

    /**
     * Méthode qui fait la création de la page d'inventaire
     *
     * @param savedInstanceState Si on reçoit un bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventaire);
        setTitle("Liste des pièces disponibles");

        db = DatabaseHelper.getInstance(this);


        recyclerView = (RecyclerView) findViewById(R.id.RVInventaire);
        listPiece = db.getInventairePositive();

        AdapterInventaire adapterInventaire = new AdapterInventaire(this,listPiece, recyclerView);
        recyclerView.setAdapter(adapterInventaire);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}