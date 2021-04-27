package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import DatabaseHelper.DatabaseHelper;
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


    DatabaseHelper db;


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
                returnButton();
            }
        });

    }

    private void init(){
        initAttributes();
        initFiche();
    }

    private void initFiche(){
        String idPiece = getIntent().getExtras().getString("id");
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


    public void returnButton(){
        finish();
    }


    public void sendBooking(){


    }






}