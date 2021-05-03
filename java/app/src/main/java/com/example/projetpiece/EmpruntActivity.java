/**
 * Nom de classe : Emprunt Activity
 * Description   : Classe qui s'occupe de la logique pour la fenêtre de l'inventaire
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : William Goupil
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  27 Avril 2021   William              Fait l'ajout des méthodes pour rendre la classe fonctionne
 *  28 Avril 2021   William              Fait l'ajout des méthodes pour rendre fonctionnel le SwipeToDelete
 *  ****************************************/

package com.example.projetpiece;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DatabaseHelper.DatabaseHelper;
import model.Categorie;
import model.Empruntpersonnel;
import model.Piece;

public class EmpruntActivity extends AppCompatActivity {


    DatabaseHelper db;

    private RecyclerView recyclerView;
    private List<Empruntpersonnel> listEmprunt = new ArrayList<>();
    private SessionManager sessionManager;

    /**
     * Méthode qui fait la création de la page de la liste des emprunts
     *
     * @param savedInstanceState Si on reçoit un bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_emprunt);
        setTitle("Liste des emprunts");
        //addEmpruntDB();
        db = DatabaseHelper.getInstance(this);
        sessionManager = new SessionManager(getApplicationContext());

        bootload bootload = new bootload();
        if(isNetworkAvailable()){
            bootload.execute(db,sessionManager.getID());
        }


        recyclerView = (RecyclerView) findViewById(R.id.RVEmprunt);
        try {
            listEmprunt = db.getListEmprunts();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        AdapterEmprunt adapterEmprunt = new AdapterEmprunt(this,listEmprunt, recyclerView);
        recyclerView.setAdapter(adapterEmprunt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapterEmprunt));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /*
        TO BE DELETED ONLY FOR TESTING
         */
    private void addEmpruntDB(){

        Categorie c = new Categorie();
        c.setNom("MOTOR");
        Piece p = new Piece();
        p.setCategorie(1);
        p.setDescription("Ceci est un moteur electrique");
        p.setNom("Moteur Electrique");
        p.setQTEDisponible(5);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
        SessionManager sm = new SessionManager(getApplicationContext());
        String UserEmail = sm.getCourriel();
        db.addEmprunt(e,UserEmail);


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}