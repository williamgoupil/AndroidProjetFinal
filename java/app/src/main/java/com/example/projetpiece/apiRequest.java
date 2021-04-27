/****************************************
 Fichier : apiRequest.java
 Auteur : Samuel Fournier, Olivier Vigneault, William Goupil, Pier-Alexander Caron
 Fonctionnalité : Fichier qui contiendra l'ensemble des requetes a l'api
 Date : 27 avril 2021
 Vérification :
 Date           	Nom               	Approuvé
 =========================================================

 Historique de modifications :
 Date           	Nom               	Description
 =========================================================
27 avril 2021       Pier-Alexandre Caron    Création de la classe et de fonction fetchInventory qui agis comme premier test
 ****************************************/

package com.example.projetpiece;

import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

public class apiRequest {


    URL url;
    public void fetchInventory(){
        try {
            url = new URL("http://127.0.0.1:8000/api-mobile-list");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();


            }

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }





}
