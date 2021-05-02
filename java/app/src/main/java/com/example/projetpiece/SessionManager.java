/**
 * Nom de classe : SessionManager
 * Description   : Classe qui s'occupe de gérer la session sur l'appareil de l'utilisateur
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
 *  28 Avril 2021   Olivier              création du fichier et de ses méthodes
 *  ****************************************/
package com.example.projetpiece;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * Constructeur par défaut de la classe
     * @param context le contexte de l'application
     */
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("appKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * enregistrer le résultat de l'authentification de l'utilisateur
     * @param login état de l'authentification (true : authentification validée, false : aucune authentification effectuée)
     */
    public void setLogin(boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }

    /**
     * retourner l'état de l'authentification de la session
     * @return boolean de l'état de l'authentification de l'utilisateur de la session de l'application
     */
    public boolean getLogin() {
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    /**
     * enregistrer le courriel de l'utilisateur dans la session
     * @param courriel le courriel de l'utilisateur
     */
    public void setCourriel(String courriel) {
        editor.putString("KEY_COURRIEL", courriel);
        editor.commit();
    }

    /**
     * retourner le courriel de l'utilisateur enregistré dans la session
     * @return String : courriel de l'utilisateur
     */
    public String getCourriel() {
        return sharedPreferences.getString("KEY_COURRIEL", "");
    }

    /**
     * enregistrer l'id de l'utilisateur dans la session
     * @param id l'identifiant de l'utilisateur
     */
    public void setID(String id) {
        editor.putString("KEY_ID", id);
        editor.commit();
    }

    /**
     * retourner l'identifiant de l'utilisateur enregistré dans la session
     * @return String : identifiant de l'utilisateur
     */
    public String getID() {
        return sharedPreferences.getString("KEY_ID", "0");
    }

}

