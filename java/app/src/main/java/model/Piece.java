package model;

public class Piece {
    private int id;
    private String nom;
    private String description;
    private int QTEDisponible;
    private int categorie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQTEDisponible() {
        return QTEDisponible;
    }

    public void setQTEDisponible(int QTEDisponible) {
        this.QTEDisponible = QTEDisponible;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }
}