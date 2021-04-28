/**
 * Nom de classe : InventaireActivity
 * Description   : Classe qui s'occupe de la logique pour la fenêtre de l'inventaire
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier
 *
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  26 Avril 2021   Samuel              Fait l'ajout des méthodes insertRV et On create pour faire fonctionner le RecyclerView
 *  27 Avril 2021   Samuel              Fait l'ajout des méthodes on resume et on start pour refresh le RecyclerView.
 *  28 Avril 2021   Samuel              Ajout des filtres pour la liste de pièces, méthodes setFilterCategorie et le listener à l'intérieur de insertRV
 *  ****************************************/

package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

 import com.example.projetpiece.AdapterInventaire;
 import com.example.projetpiece.R;

 import java.util.ArrayList;
import java.util.List;
import DatabaseHelper.DatabaseHelper;
import model.Categorie;
import model.Piece;

public class InventaireActivity extends AppCompatActivity {


    DatabaseHelper db;


    private RecyclerView recyclerView;
    private List<Piece> listPiece = new ArrayList<>();

    private Spinner filterCategorie;
    private String lastFilterCategorie;

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

       // addItemDB();

        setFilterCategorie();
        insertRV();


    }

    /**
     * Méthode qui fait réaffiche l'inventaire sur le resume
     *
     */
    @Override
    protected void onResume() {
        super.onResume();

        setFilterCategorie();
        insertRV();
    }

    /**
     * Méthode qui fait réaffiche l'inventaire sur le onstart
     *
     */
    @Override
    protected void onStart() {
        super.onStart();

        setFilterCategorie();
        insertRV();
    }

    /**
     * Méthode qui fait rempli le arraylist de pièce selon le filtre de catégorie choisi
     *
     */
    private void  insertRV(){


        filterCategorie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (!filterCategorie.getSelectedItem().toString().equals("Tous les pièces")){
                        listPiece = db.getInventairePositiveByCategorie(filterCategorie.getSelectedItem().toString());
                        AdapterInventaire adapterInventaire = new AdapterInventaire(InventaireActivity.this,listPiece, recyclerView);
                        recyclerView.setAdapter(adapterInventaire);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InventaireActivity.this));
                    }
                    else{
                        listPiece = db.getInventairePositive();
                        AdapterInventaire adapterInventaire = new AdapterInventaire(InventaireActivity.this,listPiece, recyclerView);
                        recyclerView.setAdapter(adapterInventaire);
                        recyclerView.setLayoutManager(new LinearLayoutManager(InventaireActivity.this));
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    /**
     * Méthode qui fait rempli le spinner avec le nom des catégories
     *
     */
    private void setFilterCategorie(){
        List<String> categorieListe = new ArrayList<>();
        categorieListe.add("Tous les pièces");
        filterCategorie = (Spinner) findViewById(R.id.filterCategorie);

        categorieListe = db.getAllCategorieName(categorieListe);

        ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, categorieListe);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterCategorie.setAdapter(adapterCategorie);


    }


    /*
    TO BE DELETED ONLY FOR TESTING
     */
    private void addItemDB(){
        Categorie c = new Categorie();
        c.setNom("LED");

        Categorie c2 = new Categorie();
        c2.setNom("Écran");

        Categorie c3 = new Categorie();
        c3.setNom("Autres");

        db.addCategorie(c);
        db.addCategorie(c2);
        db.addCategorie(c3);

        Piece p = new Piece();
        p.setCategorie(2);
        p.setDescription("Ceci est une LED rouge");
        p.setNom("Led rouge");
        p.setQTEDisponible(10);

        Piece p2 = new Piece();
        p2.setCategorie(3);
        p2.setDescription("Ceci est une écran LCD 16 par 2");
        p2.setNom("LCD 1602");
        p2.setQTEDisponible(12);


        Piece p3 = new Piece();
        p3.setCategorie(4);
        p3.setDescription("Ceci est une pièce autre");
        p3.setNom("Autre pièce");
        p3.setQTEDisponible(15);



        db.addPiece(p);
        db.addPiece(p2);
        db.addPiece(p3);

    }

}