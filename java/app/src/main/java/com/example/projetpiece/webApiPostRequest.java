/**
 * Nom de classe : webApiPostRequest
 * Description   : tâche asynchrone s'occupant d'effectuer l'envoi des requêtes POST à l'API
 * @version       : 1.0
 * Date          : 29/04/2021
 * @author      : Olivier Vigneault
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *  2 mai 2021      Équipe entière      approuvé
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  29 Avril 2021   Olivier              création du fichier et de la tâche
 *  ****************************************/
package com.example.projetpiece;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class webApiPostRequest extends AsyncTask<HashMap, Void, String> {

    @Override
    /**
     * @param params map contenant l'url d'envoi (à contacter) et les informations à envoyer avec la requête
     * @return réponse reçu de l'API sous forme d'array associatif
     */
    protected String doInBackground(HashMap... params) {
        HashMap<String, String> map = params[0];
        try {
            URL url = new URL(map.get("url"));
            map.remove("url");
            System.out.println(map.get("url"));

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JSONObject json = new JSONObject();

            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }

            String message = json.toString();

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = message.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            //Getting the response code
            int responsecode = con.getResponseCode();

            if (responsecode != 200) {

                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String response = "";
                Scanner scanner = new Scanner(con.getInputStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    response += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                return response;
            }


        } catch (Exception e) {
            e.printStackTrace();

            return e.toString();
        }
    }
}
