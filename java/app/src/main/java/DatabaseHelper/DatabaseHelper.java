package DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.Locale;


import model.Categorie;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class DatabaseHelper extends SQLiteOpenHelper {

        private static DatabaseHelper db;

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_PATH = "/data/data/com.example.projetfinal/databases/";
        private static final String DATABASE_NAME = "gestionnairePiece";
        private static final String LOG = "DatabaseHelper";

        //TABLE DE LA BD
        private static final String TABLE_PIECE = "piece";
        private static final String TABLE_EMPRUNTPERSONNEL = "empruntPersonnel";
        private static final String TABLE_CATEGORIE = "categorie";
        private static final String TABLE_BDVERSION = "BDVersion";

        //common column names
        private static final String KEY_ID = "id";
        private static final String KEY_NOM = "nom";


        //table Piece - column names
        private static final String KEY_DESCRIPTION = "description";
        private static final String KEY_QTEDISPONIBLE = "QTEDisponible";
        private static final String KEY_IDCATEGORIE = "categorie";

        //table Emprunt - column names
        private static final String KEY_QTEEMPRUNTER = "QTEEmprunter";
        private static final String KEY_DATEDEMANDE = "dateDemande";
        private static final String KEY_DATEFIN = "dateFin";
        private static final String KEY_IDUSER = "IdUtilisateur";
        private static final String KEY_ETATCOURANT = "etatCourant";
        private static final String KEY_IDPIECE = "piece";


        private static final String CREATE_TABLE_PIECE = "CREATE TABLE " + TABLE_PIECE +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOM + " VARCHAR," + KEY_DESCRIPTION + " TEXT," +
                KEY_QTEDISPONIBLE + " INTEGER," + KEY_IDCATEGORIE + " INTEGER, " +
                "FOREIGN KEY (" + KEY_IDCATEGORIE + ") REFERENCES " + TABLE_CATEGORIE + " (" + KEY_ID + "))";

        private static final String CREATE_TABLE_CATEGORIE = "CREATE TABLE " + TABLE_CATEGORIE +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " +  KEY_NOM + " VARCHAR )";

        private static final String CREATE_TABLE_EMPRUNTPERSONNEL = "CREATE TABLE " + TABLE_EMPRUNTPERSONNEL +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_QTEEMPRUNTER + " INTEGER," + KEY_DATEDEMANDE + " INTEGER, " +
                KEY_DATEFIN + " DATE, " + KEY_ETATCOURANT + "VARCHAR, " + KEY_IDUSER + " INTEGER," +
                KEY_IDPIECE + " INTEGER, " +
                "FOREIGN KEY (" + KEY_IDPIECE + ") REFERENCES " + TABLE_PIECE + " (" + KEY_ID + "))";

        private static final String CREATE_TABLE_BDVERSION = "CREATE TABLE " + TABLE_BDVERSION +
                "(" + KEY_ID + " INTEGER PRIMARY KEY" + ")";

        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public static synchronized DatabaseHelper getInstance(Context context) {
            if (db == null) {
                db = new DatabaseHelper(context.getApplicationContext());

            }
            return db;
        }

        public static boolean databaseExist()
        {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            return dbFile.exists();
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (!db.isReadOnly()) {
                // Enable foreign key constraints
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }

        public void closeDB() {
            SQLiteDatabase db = this.getReadableDatabase();
            if (db != null && db.isOpen())
                db.close();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CATEGORIE);
            db.execSQL(CREATE_TABLE_PIECE);
            db.execSQL(CREATE_TABLE_EMPRUNTPERSONNEL);
            db.execSQL(CREATE_TABLE_BDVERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIECE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPRUNTPERSONNEL);

            onCreate(db);
        }


        /*
        Exemple pour un insert
        */
        public void addCategorie(Categorie c){
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(KEY_NOM, c.getNom());

            database.insertOrThrow(TABLE_CATEGORIE, null, values);

        }


        public void trunctateALL(){
            SQLiteDatabase database = this.getWritableDatabase();

            String sql = "DELETE FROM " + TABLE_CATEGORIE;
            database.execSQL(sql);
        }




}
