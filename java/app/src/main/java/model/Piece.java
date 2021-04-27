/**
 * Nom de classe : Piece
 * Description   : Object Piece pour stocker les pièces de l'inventaire
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier, Olivier Vigneault, Pier-Alexandre Caron, William Goupil
 */
package model;

public class Piece {
    private int id;
    private String nom;
    private String description;
    private int QTEDisponible;
    private int categorie;


    /**
     * Constructeur avec paramètre pour Piece
     *
     * @param nom set le nom pour une Piece
     * @param desc set la description pour une Piece
     * @param qte set la qte disponible pour une Piece
     * @param categorie set la categorie (id) pour une Piece
     */
    public Piece(String nom, String desc, int qte, int categorie){
        this.nom = nom;
        this.description = desc;
        this.QTEDisponible = qte;
        this.categorie = categorie;
    }

    /**
     * Constructeur sans paramètre pour Piece
     *
     */
    public Piece(){}

    /**
     * retourne le id de la pièce
     *
     */
    public int getId() {
        return id;
    }

    /**
     * Pour assigner le id  d'une piece
     *
     * @param id set le id pour une Piece
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * retourne le nom de la pièce
     *
     */
    public String getNom() {
        return nom;
    }

    /**
     * Pour assigner le nom  d'une piece
     *
     * @param nom set le nom pour une Piece
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * retourne la description de la pièce
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Pour assigner une description  d'une piece
     *
     * @param description set la description pour une Piece
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * retourne la qte disponible de la pièce
     *
     */
    public int getQTEDisponible() {
        return QTEDisponible;
    }

    /**
     * Pour assigner la qte disponible  d'une piece
     *
     * @param QTEDisponible set la qte disponible pour une Piece
     */
    public void setQTEDisponible(int QTEDisponible) {
        this.QTEDisponible = QTEDisponible;
    }

    /**
     * retourne le id de la catégorie de la pièce
     *
     */
    public int getCategorie() {
        return categorie;
    }

    /**
     * Pour assigner une catégrie d'une piece
     *
     * @param categorie set le id categorie pour une Piece
     */
    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }
}