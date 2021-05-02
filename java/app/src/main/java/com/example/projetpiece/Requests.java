/**
 * Nom de classe : Requests
 * Description   : Classe qui contient l'ensemble des requêtes pour l'API
 * @version       : 1.0
 * Date          : 30/04/2021
 * @author      : Olivier Vigneault, Pier-Alexandre Caron
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *  2 mai 2021      Équipe entière      approuvé
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  30 Avril 2021   Olivier             fait la création du fichier et ajout des méthodes pour requêtes POST
 *  1 mai 2021      Pier-Alexandre      ajout des méthodes pour requêtes GET
 *  ****************************************/

package com.example.projetpiece;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Requests {



    /**
     * méthode pour valider les informations de connexion de l'utilisateur
     * @param courriel courriel de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @return array associatif contenant les éléments reçus dans la réponse de l'API
     */
    public HashMap<String, String> authenticateUser(String courriel, String password) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://6e6d03f2c802.ngrok.io/api-mobile-authenticate");
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


    /**
     * vérifier si le courriel entré par l'utilisateur est associé à un compte dans la BD
     * @param sCourriel courriel de l'utilisateur
     * @return array associatif avec la réponse de l'API (courriel valide ou non)
     */
    public HashMap<String, String> isEmailUsed(String sCourriel) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://6e6d03f2c802.ngrok.io/api-mobile-emailUsed");
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

    /**
     * retourne le status du mot de passe de l'utilisateur (doit être changé ou non)
     * @param courriel courriel de l'utilisateur
     * @return array associatif avec la réponse de l'API (mot de passe doit être changé ou non)
     */
    public HashMap<String, String> getPasswordStatus(String courriel) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://6e6d03f2c802.ngrok.io/api-mobile-passwordStatus");
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

    /**
     * verifie si le mot de passe est le même que celui présent en BD
     * @param courriel courriel de l'utilisateur
     * @param password nouveau mot de passe de l'utilisateur
     * @return array associatif avec la réponse de l'API (même mot de passe ou non)
     */
    public HashMap<String, String> verifyPassword(String courriel, String password) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://6e6d03f2c802.ngrok.io/api-mobile-verifyPassword");
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

    /**
     * changer le mot de passe de l'utilisateur (compte associé au courriel)
     * @param courriel courriel de l'utilisateur
     * @param password nouveau mot de passe à ajouter en BD
     */
    public void changePassword(String courriel, String password) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://6e6d03f2c802.ngrok.io/api-mobile-new-password");
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

    /**
     * méthode pour réinitialiser le mot de passe de l'utilisateur (mot de passe temporaire envoyé par courriel)
     * @param courriel courriel de l'utilisateur
     */
    public void resetPassword(String courriel) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://6e6d03f2c802.ngrok.io/api-mobile-resetPassword");
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
    public int checkBDVersion(int currentbdversion){
        String response="";
        int responseCode=1;


        String urlCancel = "https://bc9f74bc64d0.ngrok.io/api-mobile-checkBDVersion/" + String.valueOf(currentbdversion);
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
    public String downloadQuantity(){
        String response="";

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://bc9f74bc64d0.ngrok.io/api-mobile-list";
        try {
            return response = new webApiGetRequest().execute(urlCancel).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
    }
    public String downloadFullBD(){
        String response="";

        //NEED SQL QUERRY FOR CURRENT DB
        String urlCancel = "https://bc9f74bc64d0.ngrok.io/api-mobile-listeComplete";

        try {
            return response = new webApiGetRequest().execute(urlCancel).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "";
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

    public String downloadCat(){
        String urlcat = "https://bc9f74bc64d0.ngrok.io/api-mobile-getCategories";
        String response = "";
        String cat;

        try {
            return response = new webApiGetRequest().execute(urlcat).get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String downloadEmprunt(int idUser){
        String urlEmprunt = "https://bc9f74bc64d0.ngrok.io/api-mobile-getCategories";
        String response = "";


        try {
            return response = new webApiGetRequest().execute(urlEmprunt).get();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }



}