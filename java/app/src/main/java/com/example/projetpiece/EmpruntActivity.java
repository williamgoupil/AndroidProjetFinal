/**
 * Nom de classe : InventaireActivity
 * Description   : Classe qui s'occupe de la logique pour la fenêtre de l'inventaire
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier
 */
package com.example.projetpiece;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import DatabaseHelper.DatabaseHelper;
import model.Empruntpersonnel;
import model.Piece;

public class EmpruntActivity extends AppCompatActivity {


    DatabaseHelper db;

    private RecyclerView recyclerView;
    private List<Empruntpersonnel> listEmprunt = new ArrayList<>();

    /**
     * Méthode qui fait la création de la page de la liste des emprunts
     *
     * @param savedInstanceState Si on reçoit un bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO VALIDER LE NOM DU LAYOUT
        setContentView(R.layout.activity_liste_emprunt);
        setTitle("Liste des emprunts");

        db = DatabaseHelper.getInstance(this);


        recyclerView = (RecyclerView) findViewById(R.id.RVEmprunt);
        listEmprunt = db.getListEmprunts();


        AdapterEmprunt adapterEmprunt = new AdapterEmprunt(this,listEmprunt, recyclerView);
        recyclerView.setAdapter(adapterEmprunt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}