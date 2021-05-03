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
 *
 *  28 Avril 2021 P-A                   Ajout des méthodes pour tester la version de la BD et les méthode pour actualiser la BD
 *  29            P-A                   Modification de certaine méthode pour réutiliser la connexion en get plus facilement
 *  30 Avril        P-A                 Ajout de méthode pour gerer les emprunts
 *  1 Avril         P-A                 Correction de bug
 *  2 Avril         P-A                 Modification de méthode et ajout de loadCatégorie et loadEmprunt
 *  ****************************************/


package com.example.projetpiece;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Requests {



    public HashMap<String, String> authenticateUser(String courriel, String password) {
        HashMap<String, String> params = new HashMap<>();
        String response = "";
        JSONObject data = new JSONObject();
        JSONParser parse = new JSONParser();
        HashMap<String, String> responseArray = new HashMap<>();

        params.put("url", "https://bc9f74bc64d0.ngrok.io/api-mobile-authenticate");
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

        params.put("url", "https://bc9f74bc64d0.ngrok.io/api-mobile-emailUsed");
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

        params.put("url", "https://bc9f74bc64d0.ngrok.io/api-mobile-passwordStatus");
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

        params.put("url", "https://bc9f74bc64d0.ngrok.io/api-mobile-verifyPassword");
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

        params.put("url", "https://bc9f74bc64d0.ngrok.io/api-mobile-new-password");
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

        params.put("url", "https://bc9f74bc64d0.ngrok.io/api-mobile-resetPassword");
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

    /**
     *  fais une connection au site pour creer un enregistrement
     * @param idPiece le id pour identifier la piece
     * @param qqtPiece un int la quantité de piece
     * @param idUser le id du user
     * @param retour un int qui sera la durée en jour de l'emprunt
     * @return un int est le ID de la réservation dans la BD de l'api
     */
    public int makeReservation(int idPiece, int qqtPiece, int idUser, int retour){

        int idReservation=0;
        String response="";

        //Il faut faire un concatenate pour avoir le bon URL
        String urlReservation = "https://bc9f74bc64d0.ngrok.io/api-mobile-annuleremprunt/api-mobile-reserverPieces/"+idPiece+qqtPiece+idUser+retour;
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

    /**
     * fais une connection a l'api et recois un réponse selon le id actuel de BD envoyé
     * @param currentbdversion un int qui est le id de la BDCourante
     * @return la réponse du site web qui sera un int
     */
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

    /**
     * lis le site pour avoir les quantités des pieces
     * @return un string qui est la liste des pieces et leur inventaire
     */
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

    /**
     * va lire l'entiéreté de l'inventaire sur le site
     * @return un string qui est une liste json de l,inventaire
     */
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

    /**
     * apelle request get pour aller cherhcer l'entiéreté des categorie
     * @return un string qui est la liste des categorie
     */
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

    /**
     *
     * @param idUser un string qui est le id du suer
     * @return un string qui est la réponse du siteWeb
     */
    public String loadEmprunt(String idUser){

        String urlrequeteParUser= "https://bc9f74bc64d0.ngrok.io/api-mobile-checkEmpruntUser"+idUser;

        String response = "";
        try {
            return response = new webApiGetRequest().execute(urlrequeteParUser).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}