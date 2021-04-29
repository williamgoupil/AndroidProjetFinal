package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText etCourriel;
    Button btEnvoyer;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etCourriel = findViewById(R.id.etEmail);
        btEnvoyer = findViewById(R.id.btnEnvoyer);
        tvError = findViewById(R.id.textView5);

        btEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCourriel = etCourriel.getText().toString().trim();
                boolean wrongEmail = false;
                //verifier si adresse courriel est associer a un compte avec methode de l'api
                if (wrongEmail) {
                    tvError.setTextColor(Color.parseColor("#FF0000"));
                    tvError.setText("aucun compte associé à cette adresse!");
                }
            }
        });
    }
}