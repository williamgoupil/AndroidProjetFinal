package com.example.projetpiece;

import android.content.Context;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Requests {

    public HashMap<String, String> authenticateUser(String courriel, String password) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://ab1ae7403f39.ngrok.io/api-mobile-authenticate");
        params.put("email", courriel);
        params.put("password", password);

        try {
            response = new webApiPostRequest().execute(params).get();

            data = (JSONObject) parse.parse(response);
         }
        catch (ExecutionException e) {
            e.printStackTrace();
            } catch (InterruptedException e) {
            e.printStackTrace();
            } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!(data.get("id").toString()).equals("0")) {
            responseArray.put("authenticated", "true");
            responseArray.put("id", data.get("id").toString());
            responseArray.put("changePassword", data.get("changePassword").toString());
        } else {
            responseArray.put("authenticated", "false");
        }
        return responseArray;
    }

    public HashMap<String, String> isEmailUsed(String sCourriel) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://ab1ae7403f39.ngrok.io/api-mobile-emailUsed");
        params.put("email", sCourriel);

        try {
            response = new webApiPostRequest().execute(params).get();

            data = (JSONObject) parse.parse(response);
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        responseArray.put("emailUsed", data.get("emailUsed").toString());

        return responseArray;
    }

    public HashMap<String, String> getPasswordStatus(String courriel) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://ab1ae7403f39.ngrok.io/api-mobile-passwordStatus");
        params.put("email", courriel);

        try {
            response = new webApiPostRequest().execute(params).get();

            data = (JSONObject) parse.parse(response);
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        responseArray.put("changePassword", data.get("changePassword").toString());

        return responseArray;
    }

    public HashMap<String, String> verifyPassword(String courriel, String password) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://ab1ae7403f39.ngrok.io/api-mobile-verifyPassword");
        params.put("email", courriel);
        params.put("password", password);

        try {
            response = new webApiPostRequest().execute(params).get();

            data = (JSONObject) parse.parse(response);
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        responseArray.put("samePassword", data.get("samePassword").toString());

        return responseArray;
    }

    public void changePassword(String courriel, String password) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://ab1ae7403f39.ngrok.io/api-mobile-new-password");
        params.put("email", courriel);
        params.put("password", password);

        try {
            response = new webApiPostRequest().execute(params).get();

            data = (JSONObject) parse.parse(response);
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void resetPassword(String courriel) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://ab1ae7403f39.ngrok.io/api-mobile-resetPassword");
        params.put("email", courriel);

        try {
            response = new webApiPostRequest().execute(params).get();

            data = (JSONObject) parse.parse(response);
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //retour est un int ( durée en jour de location )
    public int makeReservation(int idPiece, int qqtPiece, int idUser, int retour){

        int idReservation=0;
        String response="";

        //Il faut faire un concatenate pour avoir le bon URL
        String urlReservation = "https://bc9f74bc64d0.ngrok.io/api-mobile-annuleremprunt//api-mobile-reserverPieces/{idPiece}/{qqtPiece}/{idUser}/{retour}";
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
        String urlCancel = "https://bc9f74bc64d0.ngrok.io/api-mobile-checkBDVersion/4";
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
        String urlCancel = "https://bc9f74bc64d0.ngrok.io/api-mobile-list";
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
        String urlCancel = "https://bc9f74bc64d0.ngrok.io/api-mobile-listeComplete";
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
        String urlState = "https://bc9f74bc64d0.ngrok.io/api-mobile-listeComplete";

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