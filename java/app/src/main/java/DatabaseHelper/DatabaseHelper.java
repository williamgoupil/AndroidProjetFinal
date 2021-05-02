/**
 * Nom de classe : DatabaseHelper
 * Description   : Object DatabaseHelper pour gérer la BD
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier, Olivier Vigneault, Pier-Alexandre Caron, William Goupil
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  26 Avril 2021   Samuel              Fait l'ajout des méthodes addCatégorie et Addpièces
 *  26 Avril 2021   Samuel              Fait l'ajout de la méthodes getInventairePositive
 *  27 Avril 2021   Samuel              Fait l'ajout des méthodes getOnePieceById, getCategorieName, updateQTE
 *  28 Avril 2021   Samuel              Fait l'ajout des méthodes getInventairePositiveByCategorie, getAllCategorieName
 *
 *  ****************************************/
package DatabaseHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.projetpiece.SessionManager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import model.Categorie;
import model.Empruntpersonnel;
import model.Piece;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class DatabaseHelper extends SQLiteOpenHelper {

        private static DatabaseHelper db;

        //Info de la BD
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_PATH = "/data/data/com.example.projetfinal/databases/";
        private static final String DATABASE_NAME = "gestionnairePiece";
        private static final String LOG = "DatabaseHelper";

        //TABLE DE LA BD
        private static final String TABLE_PIECE = "piece";
        private static final String TABLE_EMPRUNTPERSONNEL = "empruntPersonnel";
        private static final String TABLE_CATEGORIE = "categorie";
        private static final String TABLE_BDVERSION = "BDVersion";

        //nom de colonne commune
        private static final String KEY_ID = "id";
        private static final String KEY_NOM = "nom";


        //table Piece - nom des colonnes
        private static final String KEY_DESCRIPTION = "description";
        private static final String KEY_QTEDISPONIBLE = "QTEDisponible";
        private static final String KEY_IDCATEGORIE = "categorie";

        //table Emprunt - nom des colonnes
        private static final String KEY_QTEEMPRUNTER = "QTEEmprunter";
        private static final String KEY_DATEDEMANDE = "dateDemande";
        private static final String KEY_DATEFIN = "dateFin";
        private static final String KEY_IDUSER = "IdUtilisateur";
        private static final String KEY_ETATCOURANT = "etatCourant";
        private static final String KEY_IDPIECE = "piece";
        private static final String KEY_ONLYLOCAL = "envoyer";


        private static final String CREATE_TABLE_PIECE = "CREATE TABLE " + TABLE_PIECE +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOM + " VARCHAR," + KEY_DESCRIPTION + " TEXT," +
                KEY_QTEDISPONIBLE + " INTEGER," + KEY_IDCATEGORIE + " INTEGER, " +
                "FOREIGN KEY (" + KEY_IDCATEGORIE + ") REFERENCES " + TABLE_CATEGORIE + " (" + KEY_ID + "))";

        private static final String CREATE_TABLE_CATEGORIE = "CREATE TABLE " + TABLE_CATEGORIE +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " +  KEY_NOM + " VARCHAR )";

        private static final String CREATE_TABLE_EMPRUNTPERSONNEL = "CREATE TABLE " + TABLE_EMPRUNTPERSONNEL +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_QTEEMPRUNTER + " INTEGER," + KEY_DATEDEMANDE + " INTEGER, " +
                KEY_DATEFIN + " DATE, " + KEY_ETATCOURANT + " VARCHAR, " + KEY_IDUSER + " INTEGER," +
                KEY_IDPIECE + " INTEGER, " + KEY_ONLYLOCAL + " BOOLEAN, " +
                "FOREIGN KEY (" + KEY_IDPIECE + ") REFERENCES " + TABLE_PIECE + " (" + KEY_ID + "))";

        private static final String CREATE_TABLE_BDVERSION = "CREATE TABLE " + TABLE_BDVERSION +
                "(" + KEY_ID + " INTEGER PRIMARY KEY" + ")";

        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    /**
     * Retourne l'instance de DatabaseHelper
     *
     * @param context le context de la BD
     */
        public static synchronized DatabaseHelper getInstance(Context context) {
            if (db == null) {
                db = new DatabaseHelper(context.getApplicationContext());

            }
            return db;
        }

        /**
        * Retourne vrai si la BD existe ou faux si elle n'existe pas
        *
        */
        public static boolean databaseExist()
        {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            return dbFile.exists();
        }

    /**
     * Active les Foreign key pour la BD
     *
     * @param db instance de la BD
     */
        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (!db.isReadOnly()) {
                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }

    /**
     * Ferme la BD
     *
     */
        public void closeDB() {
            SQLiteDatabase db = this.getReadableDatabase();
            if (db != null && db.isOpen())
                db.close();
        }

    /**
     * Fait la création des tables
     *
     * @param db instance de la BD
     */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CATEGORIE);
            db.execSQL(CREATE_TABLE_PIECE);
            db.execSQL(CREATE_TABLE_EMPRUNTPERSONNEL);
            db.execSQL(CREATE_TABLE_BDVERSION);
        }

    /**
     * Methodes non utilisé
     */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }


    /**
     * Méthode pour insérer une catégorie dans la BD
     *
     * @param c La catégorie à insérer dans la BD
     */
        public void addCategorie(Categorie c){
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(KEY_NOM, c.getNom());

            database.insertOrThrow(TABLE_CATEGORIE, null, values);

        }

    /**
     * Méthode pour insérer une piece dans la BD
     *
     * @param p La piece à insérer dans la BD
     */
        public void addPiece(Piece p){
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(KEY_NOM, p.getNom());
            values.put(KEY_DESCRIPTION, p.getDescription());
            values.put(KEY_QTEDISPONIBLE, p.getQTEDisponible());
            values.put(KEY_IDCATEGORIE, p.getCategorie());

            database.insertOrThrow(TABLE_PIECE, null, values);
        }



    /**
     * Méthode pour insérer une reservation dans la BD
     *
     * @param e La reservation à insérer dans la BD
     */
    public void addEmprunt(Empruntpersonnel e,String userEmail){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QTEEMPRUNTER, e.getQTEEmprunter());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(e.getDateDemande());
        values.put(KEY_DATEDEMANDE, strDate);
        strDate = formatter.format(e.getDateFin());
        values.put(KEY_DATEFIN, strDate);
        values.put(KEY_ETATCOURANT, e.getEtatCourant());
        values.put(KEY_IDUSER, userEmail);
        values.put(KEY_IDPIECE, e.getPiece());
        values.put(KEY_ONLYLOCAL, e.isEnvoyer());

        database.insertOrThrow(TABLE_EMPRUNTPERSONNEL, null, values);
    }


    /**
     * Méthode pour retourner la liste de tous les pièces qui ont une QTE disponible de plus de 0
     *
     */
    public List<Piece> getInventairePositive(){
        List<Piece> listPiece = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PIECE + " WHERE " + KEY_QTEDISPONIBLE + " > 0";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String nom = c.getString(c.getColumnIndex(KEY_NOM));
                String desc = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
                int qte = c.getInt(c.getColumnIndex(KEY_QTEDISPONIBLE));
                int categorie = c.getInt(c.getColumnIndex(KEY_IDCATEGORIE));
                Piece p = new Piece(nom,desc,qte,categorie);
                p.setId(id);
                listPiece.add(p);
            } while (c.moveToNext());
        }

        return listPiece;
    }


    /**
     * Méthode pour retourner la liste de tous les pièces qui ont une QTE disponible de plus de 0 filtrer par categorie
     *
     */
    public List<Piece> getInventairePositiveByCategorie(String categorieName){
        List<Piece> listPiece = new ArrayList<>();

        String query = "SELECT p.id, p.nom, p.description ,p.QTEDisponible, p.categorie FROM " + TABLE_PIECE +
                        " p INNER JOIN " +  TABLE_CATEGORIE + " c ON p." + KEY_IDCATEGORIE + " = c." + KEY_ID + " WHERE c." + KEY_NOM + " = '" + categorieName + "' AND " + KEY_QTEDISPONIBLE + " > 0";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                String nom = c.getString(c.getColumnIndex( KEY_NOM));
                String desc = c.getString(c.getColumnIndex(KEY_DESCRIPTION));
                int qte = c.getInt(c.getColumnIndex(KEY_QTEDISPONIBLE));
                int categorie = c.getInt(c.getColumnIndex(KEY_IDCATEGORIE));
                Piece p = new Piece(nom,desc,qte,categorie);
                p.setId(id);
                listPiece.add(p);
            } while (c.moveToNext());
        }

        return listPiece;
    }


    /**
     * Méthode pour retourner les informations d'une pièce avec un id
     *
     * @param id le id de la pièce
     */
    public Piece getOnePieceById(String id){


        String query = "SELECT * FROM " + TABLE_PIECE + " WHERE " + KEY_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null){
            c.moveToFirst();

            Piece p = new Piece();

            p.setNom(c.getString(c.getColumnIndex(KEY_NOM)));
            p.setDescription(c.getString(c.getColumnIndex(KEY_DESCRIPTION)));
            p.setQTEDisponible(c.getInt(c.getColumnIndex(KEY_QTEDISPONIBLE)));

            return  p;

        }
        return new Piece();
    }


    /**
     * Méthode pour retourner le nom d'une catégorie selon son id
     *
     * @param id le id de la categorie
     */
    public String getCategorieName(int id){

        String query = "SELECT nom FROM " + TABLE_CATEGORIE + " WHERE " + KEY_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null){
            c.moveToFirst();
            return c.getString(c.getColumnIndex(KEY_NOM));
        }
            return  "";
    }

    /**
     * Permet de remplir la list reçu en paramètre avec le nom de tous les catégories et la retourne
     *
     * @param listCategorie
     */
    public List<String> getAllCategorieName(List<String> listCategorie){


        String query = "SELECT nom FROM " + TABLE_CATEGORIE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                String nom = c.getString(c.getColumnIndex(KEY_NOM));
                listCategorie.add(nom);
            } while (c.moveToNext());
        }

        return listCategorie;
    }


    /**
     * Méthode qui permet de chaner la QTE d'une pièce
     *
     * @param id le id de la pièce à modifier
     * @param newQTE la nouvelle qte pour la pièce
     */
    public void updateQTE(String id, int newQTE){
        String query = "UPDATE " + TABLE_PIECE + " SET " + KEY_QTEDISPONIBLE + " = " + newQTE +  " WHERE " + KEY_ID + " = " + id;
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(query);
    }


    /**
     * Méthode pour vider tous les tables
     *
     */
    public void trunctateALL(){
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("DELETE FROM " + TABLE_CATEGORIE);
        database.execSQL("DELETE FROM " + TABLE_PIECE);
        //database.execSQL("DELETE FROM " + TABLE_EMPRUNTPERSONNEL);
    }



    /**
     * Méthode pour retourner la liste de tous les emprunts
     *
     */
    public List<Empruntpersonnel> getListEmprunts() throws ParseException {
        List<Empruntpersonnel> listEmprunt = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_EMPRUNTPERSONNEL;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(KEY_ID));
                int QTEEmprunter = c.getInt(c.getColumnIndex(KEY_QTEEMPRUNTER));
                String StrDateDebut = c.getString(c.getColumnIndex(KEY_DATEDEMANDE));
                String StrDateFin = c.getString(c.getColumnIndex(KEY_DATEFIN));
                String etat = c.getString(c.getColumnIndex(KEY_ETATCOURANT));
                int piece = c.getInt(c.getColumnIndex(KEY_IDPIECE));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date DateDebut = formatter.parse(StrDateDebut);
                Date DateFin = formatter.parse(StrDateFin);
                Empruntpersonnel E = new Empruntpersonnel (QTEEmprunter, DateDebut, DateFin, etat, piece,false);
                E.setId(id);
                listEmprunt.add(E);
            } while (c.moveToNext());
        }

        return listEmprunt;
    }

    /**
     * Permet de supprimer un emprunt de la BD selon l'id
     * @param id id de l'emprunt à supprimer
     */
    public void deleteEmpruntById(int id) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("DELETE FROM "+TABLE_EMPRUNTPERSONNEL+" WHERE " + KEY_ID + " = " + id);
    }
}
