package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ficheTechniqueActivity extends AppCompatActivity {



    private TextView nomPiece;
    private TextView descriptionPiece;
    private TextView qtePiece;

    private EditText QTEDesirer;
    private EditText DateRetour;

    private Button btnReserver;
    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_technique);

        String id = getIntent().getExtras().getString("id");
        nomPiece = (TextView) findViewById(R.id.nomPieceDetaille);

        nomPiece.setText(id);

    }
}