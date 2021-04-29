package com.example.projetpiece;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class webApiRequest extends AsyncTask<String, Void, String> {



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

                    //JSON SIMPLE LIBRARY pour faire un parse
                    /*JSONParser parse = new JSONParser();
                    JSONObject data_obj = (JSONObject) parse.parse(inline);

                    //avoir le bon objet selon la bonne clé
                    JSONObject obj = (JSONObject) data_obj.get("Global");*/

                    //Get the required data using its key
                    //System.out.println(obj.get("idVersion"));

                    //currentBDVersion = obj.get("idVersion").toString();

                    //JSONArray arr = (JSONArray) data_obj.get("idVersion");

               /* for (int i = 0; i < arr.length(); i++) {

                    JSONObject new_obj = (JSONObject) arr.get(i);

                    if (new_obj.get("Slug").equals("albania")) {
                        System.out.println("Total Recovered: " + new_obj.get("TotalRecovered"));
                        break;
                    }
                }*/
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
