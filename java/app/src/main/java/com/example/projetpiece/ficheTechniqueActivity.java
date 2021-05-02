/**
 * Nom de classe : ficheTechniqueActivity
 * Description   : Classe qui s'occupe de la logique pour le détails de chaque pièces ainsi que la réservation d'une pièce
 * @version       : 1.0
 * Date          : 28/04/2021
 * @author      : Samuel Fournier
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  27 Avril 2021   Samuel              Fait l'ajout de initFiche et initAttribute et des méthodes pour que les boutons soit fonctionnels sur la view
 *  27 Avril 2021   Samuel              Fait l'ajout des méthodes showDate et onDateSet pour afficher un calendrier lorsque le bouton choisir une date est affiché
 *  27 Avril 2021   Samuel              Fait l'ajout de la fonction checkForm et sendForm pour envoyé les réservations et validé les champs du formulaire
 *  27 Avril 2021   Samuel              Fait l'ajout du builder d'alert pour des meilleurs affiches de message d'erreur
 *  28 Avril 2021   Samuel              Fait l'ajout d'un autre builder d'alert pour afficher un message de réussite lorsque la réservation se fait
 *  ****************************************/
package com.example.projetpiece;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import DatabaseHelper.DatabaseHelper;
import model.Empruntpersonnel;
import model.Piece;

public class ficheTechniqueActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {



    // Les champs à remplir avec les informations de chaque pièce
    private TextView nomPiece;
    private TextView descriptionPiece;
    private TextView qtePiece;

    //Champs qui montre à l'utlisateur sa date choisie
    private TextView dateChoisirText;

    //Champ pour la quantité désiré et le bouton pour choisir la date
    private EditText QTEDesirer;
    private Button DateRetour;

    //Les boutons de contrôle
    private Button btnReserver;
    private Button btnRetour;

    //La date choisis et la date d'aujourd'hui
    private Date dateChoisie;
    private Date todayDate;

    // Le id de la pièce
    private String idPiece;

    //instance de la database
    DatabaseHelper db;

    //Les builder pour la création des alerts pour les erreurs et la réussite de la réservation
    private AlertDialog.Builder builder;
    private AlertDialog.Builder finishBuilder;


    /**
     * Méthode qui fait la création de la page d'inventaire
     *
     * @param savedInstanceState Si on reçoit un bundle
     */
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

    /**
     * Méthode qui appel l'initilisation des champs, des attributs  et des builders d'alerte
     *
     */
    private void init(){
        initAttributes();
        initFiche();
        initBuilder();
    }

    /**
     * Méthode qui appel l'initilisation des champs
     *
     */
    private void initFiche(){
        idPiece = getIntent().getExtras().getString("id");
        db = DatabaseHelper.getInstance(this);
        Piece p = db.getOnePieceById(idPiece);
        nomPiece.setText(p.getNom());
        descriptionPiece.setText(p.getDescription());
        qtePiece.setText(String.valueOf(p.getQTEDisponible()));
    }

    /**
     * Méthode qui appel l'initilisation  des attributs
     *
     */
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


    /**
     * Méthode qui appel l'initilisation  des builders d'alerte
     *
     */
    private void initBuilder(){
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        finishBuilder = new AlertDialog.Builder(this);
        finishBuilder.setCancelable(false);
        finishBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });


    }

    /**
     * Méthode qui montre le calendrier pour que l'utilisateur choisises la date de retour
     *
     */
    private void showDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();
    }

    /**
     * Méthode qui doit être override pour être le listener lorsque la date est choisi dans le calendrier
     *
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth);
            dateChoisie = calendar.getTime();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date =  formatter.format(dateChoisie);

            dateChoisirText.setText(date);
    }


    /**
     * Méthode qui retourner à la page d'inventaire
     *
     */
    private void returnButton(){
        finish();
    }


    /**
     * Méthode qui fait toutes les validations sur le formulaire pour réserver une pièces
     *
     */
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


    /**
     * Méthode qui envoie la Réservation à l'API et la place dans la BD local
     *
     */
    private void sendBooking(){

        Empruntpersonnel e = new Empruntpersonnel(Integer.parseInt(QTEDesirer.getText().toString()),todayDate,dateChoisie,"Envoyé, en attente",Integer.parseInt(idPiece),false);


        int newQTE = Integer.parseInt(qtePiece.getText().toString()) - Integer.parseInt(QTEDesirer.getText().toString());
        db.updateQTE(idPiece,newQTE);
        if(isNetworkAvailable()){
            //Fonction à PA pour envoyer avec l'API

            Requests requestEmprunt = new Requests();

            //la fonction est pas fini sur la partie web mais ceci devrais marcher
            //retour est un int ( durée en jour de location )
            //idEmprunt = requestEmprunt.makeReservation(int idPiece, int qqtPiece, int idUser, int retour);

            //e.setEnvoyer(true);
        }
        SessionManager sm = new SessionManager(getApplicationContext());
        String UserId = sm.getID();
        db.addEmprunt(e,UserId);

        showSuccessAlert("Votre Emprunt", "Vous pouvez maintenant suivre l'état de votre emprunt dans la section Mes emprunts ! ");
    }



    /**
     * Méthode qui montre la fenêtre d'alerte lorsqu'il à une erreur
     *
     * @param message le message à afficher dans la fenêtre d'Alerte
     * @param title le titre de la fenêtre
     */
    private void showAlert(String title, String message){
        builder.setMessage(message);
        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }


    /**
     * Méthode qui montre la fenêtre d'alerte lorsque la réservation est terminer
     *
     * @param message le message à afficher dans la fenêtre d'Alerte
     * @param title le titre de la fenêtre
     */
    private void showSuccessAlert(String title, String message){
        finishBuilder.setMessage(message);
        AlertDialog alert = finishBuilder.create();
        alert.setTitle(title);
        alert.show();
    }




    /**
     * Méthode qui permet de savoir si il a une connexion à internet
     *
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }



}