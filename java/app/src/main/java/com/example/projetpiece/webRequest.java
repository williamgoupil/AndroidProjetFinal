package com.example.projetpiece;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.ExecutionException;

public class webRequest {

    //retour est un int ( durée en jour de location )
    public int makeReservation(int idPiece, int qqtPiece, int idUser, int retour){

        int idReservation=0;
        String response="";
        //Il faut faire un concatenate pour avoir le bon URL
        String urlReservation = "https://d11d840bcd81.ngrok.io/api-mobile-annuleremprunt//api-mobile-reserverPieces/{idPiece}/{qqtPiece}/{idUser}/{retour}";
        try {
            response = new webApiGetRequest().execute(urlReservation).get();

            JSONParser parse = new JSONParser();
            JSONObject obj = (JSONObject) parse.parse(response);


            idReservation =  Integer.parseInt( obj.get("code").toString());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return idReservation;
    }
    public int checkBDVersion(){
        String response="";
        int responseCode=1;

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-checkBDVersion/4";
        try {
            response = new webApiGetRequest().execute(urlCancel).get();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject obj = (JSONObject) parse.parse(response);


            responseCode =  Integer.parseInt( obj.get("idVersion").toString());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return responseCode;
        }


    }
    public void downloadQuantity(){
        String response="";

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-list";
        try {
            response = new webApiGetRequest().execute(urlCancel).get();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(response);

            JSONArray arr = (JSONArray) data_obj.get("lstPiece");

            for (int i = 0; i < arr.size(); i++) {

                JSONObject new_obj = (JSONObject) arr.get(i);
                //Querry SQL pour insert les nouvelle quantités

            }



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public void downloadFullBD(){
        String response="";

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-listeComplete";
        try {
            response = new webApiGetRequest().execute(urlCancel).get();

            //Using the JSON simple library parse the string into a json object
            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(response);

            JSONArray arr = (JSONArray) data_obj.get("lstPiece");

            for (int i = 0; i < arr.size(); i++) {

                JSONObject new_obj = (JSONObject) arr.get(i);
                //Querry SQL pour insert la nouvelle DB

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public String getCommandState(int idCommande){
        String empruntState = "";
        String response = "";
        String urlState = "https://d11d840bcd81.ngrok.io/api-mobile-listeComplete";

        try {
            response = new webApiGetRequest().execute(urlState).get();
            JSONParser parse = new JSONParser();
            JSONObject obj = (JSONObject) parse.parse(response);


            empruntState = obj.get("nomState").toString();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return empruntState;
    }
}
