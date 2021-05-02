/**
 * Nom de classe : ResetPasswordActivity
 * Description   : Activité appelé lorsque l'utilisateur souhaite réinitialiser son mot de passe
 * @version       : 1.0
 * Date          : 28/04/2021
 * @author      : Olivier Vigneault
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *  2 mai 2021      Équipe entière      approuvé
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  28 Avril 2021   Olivier              création du fichier et de la fonction onClick
 *  29 Avril 2021   Olivier              ajout des requêtes à l'API
 *  ****************************************/
package com.example.projetpiece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText etCourriel;
    Button btEnvoyer;
    TextView tvError;
    Requests requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etCourriel = findViewById(R.id.etEmail);
        btEnvoyer = findViewById(R.id.btnEnvoyer);
        tvError = findViewById(R.id.textView5);

        requests = new Requests();

        btEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCourriel = etCourriel.getText().toString().trim();
                HashMap<String, String> data = requests.isEmailUsed(sCourriel);

                if ((data.get("emailUsed")).equals("false")) {
                    tvError.setTextColor(Color.parseColor("#FF0000"));
                    tvError.setText("Aucun compte associé à cette adresse courriel!");
                }
                else if ((data.get("emailUsed")).equals("true")) {
                    requests.resetPassword(sCourriel);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }
}