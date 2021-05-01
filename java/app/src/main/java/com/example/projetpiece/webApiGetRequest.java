package com.example.projetpiece;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class webApiGetRequest extends AsyncTask<String, Void, String> {



    @Override
    protected String doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                conn.connect();

                //Getting the response code
                int responsecode = conn.getResponseCode();



                if (responsecode != 200) {

                    throw new RuntimeException("HttpResponseCode: " + responsecode);
                } else {

                    String inline = "";
                    Scanner scanner = new Scanner(url.openStream());

                    //Write all the JSON data into a string using a scanner
                    while (scanner.hasNext()) {
                        inline += scanner.nextLine();
                    }

                    //Close the scanner
                    scanner.close();

                    return inline;
                }

            } catch (Exception e) {
                e.printStackTrace();

                return e.toString();

            }
        }

    @Override
    protected void onPostExecute(String s) {

    }
}
