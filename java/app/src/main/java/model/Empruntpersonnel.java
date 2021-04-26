package model;

import java.util.Date;

public class Empruntpersonnel {

    private int id;
    private int QTEEmprunter;
    private Date dateDemande;
    private Date dateFin;
    private String etatCourant;
    private int piece;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQTEEmprunter() {
        return QTEEmprunter;
    }

    public void setQTEEmprunter(int QTEEmprunter) {
        this.QTEEmprunter = QTEEmprunter;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getEtatCourant() {
        return etatCourant;
    }

    public void setEtatCourant(String etatCourant) {
        this.etatCourant = etatCourant;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }
}

