package com.example.projetpiece;

import DatabaseHelper.DatabaseHelper;

public class bootload {

    static Requests requests = new Requests();

    public static void execute(DatabaseHelper db, String id){

        downloadDBInfo(db,id);
        db.checkUnsent();
        //db.loadEmprunt( requests.loadEmprunt(id),id);



    }

    public static void downloadDBInfo(DatabaseHelper db,String id){




        int responseCodeBD = requests.checkBDVersion(db.getcurrentDBVersion());
        CharSequence text = "";
        if( responseCodeBD == 0 ) {
            db.loadquantity(requests.downloadQuantity());

        } else {

            db.checkUnsent();
            db.trunctateALL();

            db.newBDVersion(responseCodeBD);
            db.loadNewCat(requests.downloadCat());
            db.loadNewInventory(requests.downloadFullBD());
            db.loadEmprunt(requests.loadEmprunt(id),id);

        }


    }

}
