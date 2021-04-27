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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class apiRequest {


    URL url;

    public static void main(String[] args) {
        try {

            URL url = new URL("https://api.covid19api.com/summary");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
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

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                //Get the required object from the above created object
                JSONObject obj = (JSONObject) data_obj.get("Global");

                //Get the required data using its key
                System.out.println(obj.get("TotalRecovered"));

                JSONArray arr = (JSONArray) data_obj.get("Countries");

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject new_obj = (JSONObject) arr.get(i);

                    if (new_obj.get("Slug").equals("albania")) {
                        System.out.println("Total Recovered: " + new_obj.get("TotalRecovered"));
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}