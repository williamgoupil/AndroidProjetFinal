/**
 * Nom de classe : Empruntpersonnel
 * Description   : Object Empruntpersonnel pour stocker les Empruntpersonnel de l'utilisateur
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier, Olivier Vigneault, Pier-Alexandre Caron, William Goupil
 */
package model;

import java.util.Date;

public class Empruntpersonnel {

    private int id;
    private int QTEEmprunter;
    private Date dateDemande;
    private Date dateFin;
    private String etatCourant;
    private int piece;

    /**
     * Constructeur avec paramètre pour Piece
     *
     * @param QTEEmprunter set le nom pour une Piece
     * @param dateDemande set la description pour une Piece
     * @param dateFin set la qte disponible pour une Piece
     * @param etatCourant set la categorie (id) pour une Piece
     * @param piece set la categorie (id) pour une Piece
     */
    public Empruntpersonnel(int QTEEmprunter, Date dateDemande, Date dateFin, String etatCourant, int piece){
        this.QTEEmprunter = QTEEmprunter;
        this.dateDemande = dateDemande;
        this.dateFin = dateFin;
        this.etatCourant = etatCourant;
        this.piece = piece;
    }

    /**
     * Constructeur sans paramètre pour Piece
     *
     */
    public Empruntpersonnel(){}


    /**
     * retourne le id d'une réservation
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Pour assigner le Id d'une réservation
     *
     * @param id set le id pour un emprunt
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * retourne la qte d'une réservation
     *
     */
    public int getQTEEmprunter() {
        return QTEEmprunter;
    }

    /**
     * Pour assigner la qte d'une réservation
     *
     * @param QTEEmprunter set la qte emprunter pour une un emprunt
     */
    public void setQTEEmprunter(int QTEEmprunter) {
        this.QTEEmprunter = QTEEmprunter;
    }

    /**
     * retourne la date de demande d'une réservation
     *
     */
    public Date getDateDemande() {
        return dateDemande;
    }

    /**
     * Pour assigner la date de demande d'une réservation
     *
     * @param dateDemande set la date de la demande pour un emprunt
     */
    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    /**
     * retourne la date de fin d'une réservation
     *
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * Pour assigner la date de fin d'une réservation
     *
     * @param dateFin set la date de fin pour un emprunt
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * retourne l'état d'une réservation
     *
     */
    public String getEtatCourant() {
        return etatCourant;
    }

    /**
     * Pour assigner l'état courant d'une réservation
     *
     * @param etatCourant set l'état courant d'un emprunt
     */
    public void setEtatCourant(String etatCourant) {
        this.etatCourant = etatCourant;
    }

    /**
     * retourne le id de la piece
     *
     */
    public int getPiece() {
        return piece;
    }

    /**
     * Pour assigner la pièce à une réservation
     *
     * @param piece set le id piece pour un emprunt
     */
    public void setPiece(int piece) {
        this.piece = piece;
    }
}

