package com.example.projetpiece;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import DatabaseHelper.DatabaseHelper;

public class bootload {

    Requests requests = new Requests();

    public void execute(DatabaseHelper db,String id){
        if(isNetworkAvailable()) {
            downloadDBInfo(db);
            db.checkUnsent();
            db.loadEmprunt( requests.loadEmprunt(id),id);


        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public void downloadDBInfo(DatabaseHelper db){




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

        }


    }

}
