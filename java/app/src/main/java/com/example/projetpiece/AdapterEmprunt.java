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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import DatabaseHelper.DatabaseHelper;
import model.Categorie;
import model.Empruntpersonnel;
import model.Piece;

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
        holder.nompiece.setText(nomPiece);
        holder.etat.setText(String.valueOf(listEmprunt.get(position).getEtatCourant()));
        SimpleDateFormat formatter = new SimpleDateFormat("y-M-dd");
        String tempDate = formatter.format(listEmprunt.get(position).getDateDemande());
        holder.dateInit.setText(tempDate);
        tempDate = formatter.format(listEmprunt.get(position).getDateFin());
        holder.dateFin.setText(tempDate);
    }

    @Override
    public int getItemCount(){return listEmprunt.size(); }

    public void deleteItem(int position) {

        mRecentlyDeletedItem = listEmprunt.get(position);
        mRecentlyDeletedItemPosition = position;

        db = DatabaseHelper.getInstance(context);
        Piece tempP =db.getOnePieceById(Integer.toString(mRecentlyDeletedItem.getId()));
        String tempCat = db.getCategorieName(tempP.getCategorie());
        if(!tempCat.equals("Emprunter"))
        {
            listEmprunt.remove(position);
            notifyItemRemoved(position);

            db.deleteEmpruntById(mRecentlyDeletedItem.getId());
        }
        else
        {
            int a = 1;
            //TODO Ajouter message impossible delete commande emprunter
        }
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