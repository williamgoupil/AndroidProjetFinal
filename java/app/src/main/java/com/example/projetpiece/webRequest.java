package com.example.projetpiece;

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
            response = new webApiRequest().execute(urlReservation).get();

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


}
