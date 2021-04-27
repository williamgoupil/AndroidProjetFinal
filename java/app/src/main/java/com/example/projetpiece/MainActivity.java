package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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


        Button button = (Button) findViewById(R.id.buttonInventaire);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InventaireActivity.class);
                startActivity(intent);
            }
        });

    }

}
