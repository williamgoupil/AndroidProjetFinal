/**
 * Nom de classe : SwipeToDeleteCallback
 * Description   : Classe qui s'occupe de supprimer un recycler view
 * @version       : 1.0
 * Date          : 28/04/2021
 * @author      : William Goupil
 *  Vérification :
 *  Date           	Nom               	Approuvé
 *  =========================================================
 *
 *  Historique de modifications :
 *  Date           	Nom               	Description
 *  =========================================================
 *  28 Avril 2021   William              Fait l'ajout de initFiche et initAttribute et des méthodes pour que les boutons soit fonctionnels sur la view
 *  ****************************************/
package com.example.projetpiece;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private AdapterEmprunt mAdapter;
    private final ColorDrawable background;

    /**
     * Constructeur par défault
     * @param adapter adapter à appliquer la swipe to delete
     */
    public SwipeToDeleteCallback(AdapterEmprunt adapter) {
        super(0,ItemTouchHelper.LEFT);
        mAdapter = adapter;
        background = new ColorDrawable(Color.RED);
    }

    /**
     * Permet de déterminer l'action à effectuer
     * @param viewHolder la recycle view concerné
     * @param direction la direction pour que le swipe fonctionne
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }

    /**
     * quoi faire lorsque le user bouge dans l'app
     * @param recyclerView la recycleView concerner
     * @param viewHolder Le viewHolder Concerner
     * @param target
     * @return l'action à effectuer
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * Quoi dessinner lorsque le swipe est fait
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if (dX > 0) { // Swiping to the right
            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());

        } else if (dX < 0) { // Swiping to the left
            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(c);
    }
}
