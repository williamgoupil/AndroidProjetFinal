/**
 * Nom de classe : Categorie
 * Description   : Object categorie pour stocker les catégories des pièces
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier, Olivier Vigneault, Pier-Alexandre Caron, William Goupil
 */
package model;


public class Categorie {
    private int id;
    private String nom;

    /**
     Constructeur avec paramètre pour Categorie
     @param nom pour le nom de la piece
     */
    public  Categorie(String nom){
        this.nom = nom;
    }

    /**
     Constructeur sans paramètre pour Categorie
     */
    public  Categorie(){};


    /**
     * Retourne le ID d'une de la catégorie
     *
     */
    public int getId() {
        return id;
    }

    /**
     * assigne le ID d'une de la catégorie
     *
     * @param id set le ID pour une catégorie
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la catégotie
     *
     */
    public String getNom() {
        return nom;
    }

    /**
     * assigne le nom d'une de la catégorie
     *
     * @param nom set le nom pour une catégorie
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
}
