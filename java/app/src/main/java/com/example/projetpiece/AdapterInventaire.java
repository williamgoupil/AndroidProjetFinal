/**
 * Nom de classe : AdapterInventaire
 * Description   : Classe qui s'occupe de de remplir le recycler view avec les informations des pièces
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  26 Avril 2021   Samuel              Fait la création de l'adapter pour le RV ainsi que le Onclick Listener pour chaque item dans le RV
 *  ****************************************/
package com.example.projetpiece;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import DatabaseHelper.DatabaseHelper;
import model.Piece;


public class AdapterInventaire extends RecyclerView.Adapter<AdapterInventaire.MyViewHolder>{

    private Context context;

    private List<Piece> listPiece;

    private RecyclerView RVInventaire;

    final View.OnClickListener onClickListener = new MyOnClickListener();


    /**
     * Méthode qui remplis le layout et qui initialise le onclic listener pour le RecyclerView
     *
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rowinventaire,parent,false);
        view.setOnClickListener(onClickListener);
        return new MyViewHolder(view);
    }


    /**
     * Constructeur par défault
     *
     * @param context Le context pour le RV
     * @param RVInventaire le RV en question
     * @param listPiece la liste de pièces à afficher
     */
    public AdapterInventaire(Context context, List<Piece> listPiece, RecyclerView RVInventaire){

        this.listPiece = listPiece;
        this.context = context;
        this.RVInventaire = RVInventaire;
    }


    /**
     * Méthode qui permet de lier les champs avec les données du arraylist
     *
     * @param position La position dans le arraylist pour chaque Pièce
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DatabaseHelper db = DatabaseHelper.getInstance(context);
        String categorieName = db.getCategorieName(listPiece.get(position).getCategorie());

        holder.nom.setText(String.valueOf(listPiece.get(position).getNom()));
        holder.categorie.setText(categorieName);
        holder.qte.setText(String.valueOf(listPiece.get(position).getQTEDisponible()));

    }

    /**
     * Méthode qui retourne le nombre d'item dans la liste
     *
     */
    @Override
    public int getItemCount(){return listPiece.size(); }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nom;
        TextView categorie;
        TextView qte;

        /**
         * Constructeur pour lier les champs au attribue
         *
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            nom = (TextView) itemView.findViewById(R.id.nomPiece);
            categorie = (TextView) itemView.findViewById(R.id.categoriePiece);
            qte = (TextView) itemView.findViewById(R.id.QTEPiece);
        }
    }

    /**
     * Class qui permet d'Avoir un listener sur chaque row pour savoir lequel est cliqué
     *
     */
    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            context = v.getContext();
            Intent intent = new Intent(context,ficheTechniqueActivity.class);

            int piecePosition = RVInventaire.getChildLayoutPosition(v);
            String item = String.valueOf(listPiece.get(piecePosition).getId());

            intent.putExtra("id", item);
            context.startActivity(intent);
        }
    }




}
