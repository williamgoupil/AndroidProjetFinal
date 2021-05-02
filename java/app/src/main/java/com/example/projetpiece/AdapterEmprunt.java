/**
 * Nom de classe : AdapterInventaire
 * Description   : Classe qui s'occupe de de remplir le recycler view avec les informations des pièces
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier
 */
package com.example.projetpiece;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import DatabaseHelper.DatabaseHelper;
import model.Empruntpersonnel;

public class AdapterEmprunt extends RecyclerView.Adapter<AdapterEmprunt.MyViewHolder>{

    private Context context;

    private List<Empruntpersonnel> listEmprunt;

    private RecyclerView RVEmprunt;

    final View.OnClickListener onClickListener = new MyOnClickListener();
    DatabaseHelper db;
    Empruntpersonnel mRecentlyDeletedItem;
    int mRecentlyDeletedItemPosition;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rowemprunt,parent,false);

        view.setOnClickListener(onClickListener);

        return new MyViewHolder(view);
    }


    public AdapterEmprunt(Context context, List<Empruntpersonnel> listEmprunt, RecyclerView RVEmprunt){

        this.listEmprunt = listEmprunt;
        this.context = context;
        this.RVEmprunt = RVEmprunt;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        db = DatabaseHelper.getInstance(context);
        int IdPiece = listEmprunt.get(position).getPiece();
        String nomPiece =  db.getOnePieceById(Integer.toString(IdPiece)).getNom();
        SimpleDateFormat formatter = new SimpleDateFormat("y-M-dd");
        String dateInit = formatter.format(listEmprunt.get(position).getDateDemande());
        String dateFin = formatter.format(listEmprunt.get(position).getDateFin());

        holder.nompiece.setText(nomPiece);
        holder.etat.setText(String.valueOf(listEmprunt.get(position).getEtatCourant()));
        holder.dateInit.setText(dateInit);
        holder.dateFin.setText(dateFin);
    }

    @Override
    public int getItemCount(){return listEmprunt.size(); }

    public void deleteItem(int position) {

        mRecentlyDeletedItem = listEmprunt.get(position);
        mRecentlyDeletedItemPosition = position;

        listEmprunt.remove(position);
        notifyItemRemoved(position);

        String response="";
        try {
            //URL pour cancel une certaine commande
            String urlCancel = "https://d11d840bcd81.ngrok.io/api-mobile-annuleremprunt/" + mRecentlyDeletedItem.getId();
            response = new webApiGetRequest().execute(urlCancel).get();

            JSONParser parse = new JSONParser();
            JSONObject data_obj = (JSONObject) parse.parse(response);

            int responseCode = (int) data_obj.get("codeErreur");

            //1 pas réussi
            //0 réussi

            if(responseCode == 1) {
                showUndoSnackbar();
            }
            else if(responseCode == 0){
                db = DatabaseHelper.getInstance(context);
                db.deleteEmpruntById(mRecentlyDeletedItem.getId());
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showUndoSnackbar() {
        View view = RVEmprunt;
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.snack_bar_text, v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        listEmprunt.add(mRecentlyDeletedItemPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nompiece;
        TextView dateInit;
        TextView dateFin;
        TextView etat;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            nompiece = (TextView) itemView.findViewById(R.id.NomPieceEmprunt);
            dateInit = (TextView) itemView.findViewById(R.id.dateDebut);
            dateFin = (TextView) itemView.findViewById(R.id.dateFin);
            etat = (TextView) itemView.findViewById(R.id.EtatEmprunt);

        }
    }

    /**
     * Class qui permet d'Avoir un listener sur chaque row pour savoir lequel est cliqué
     *
     */
    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int empruntPosition = RVEmprunt.getChildLayoutPosition(v);
            String item = String.valueOf(listEmprunt.get(empruntPosition).getId());
            //Toast.makeText(v.getContext(),item,Toast.LENGTH_SHORT).show();
        }
    }
}