/**
 * Nom de classe : MainActivity
 * Description   : Activité principale qui permet d'authentifier les utilisateurs sur l'application
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Olivier Vigneault
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *  2 mai 2021      Équipe entière      approuvé
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  24 Avril 2021 P-A                   Création de do in background
 *  27 Avril        P-A                  Implémentation de do in background dans android studio ( beaucoup de bug )
 *  28 Avril    P-A                        Correction des bugs de la tache en async est modification envoyé seulement un string retrait du traiment json ici
 *  ****************************************/

package com.example.projetpiece;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class webApiGetRequest extends AsyncTask<String, Void, String> {


    /**
     * se connecte a un site web en tache asynchrone et converti le contenu jason en string grace a un scanner
     * @param urls un string qui sera le URL de l'api ou on se connecte
     * @return un string qui est lue par un scanner ( originalement un objet jason )
     */
    @Override
    protected String doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                //Getting the response code
                int responsecode = conn.getResponseCode();

                if (responsecode != 200) {

                    throw new RuntimeException("HttpResponseCode: " + responsecode);
                } else {

                    String inline = "";
                    Scanner scanner = new Scanner(conn.getInputStream());

                    //Write all the JSON data into a string using a scanner
                    while (scanner.hasNext()) {
                        inline += scanner.nextLine();
                    }
                    //Close the scanner
                    scanner.close();
                    return inline;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

    @Override
    protected void onPostExecute(String s) {

    }
}
