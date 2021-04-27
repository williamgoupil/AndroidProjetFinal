/**
 * Nom de classe : BDVersion
 * Description   : Object pour garder la version de la BD de l'utilisateur
 * @version       : 1.0
 * Date          : 26/04/2021
 * @author      : Samuel Fournier, Olivier Vigneault, Pier-Alexandre Caron, William Goupil
 */
package model;

public class BDVersion {
    private int id;

    /**
     * Retourne le ID d'une de la version
     *
     */
    public int getId() {
        return id;
    }

    /**
     * assigne le ID d'une de la version
     *
     * @param id set le ID pour une version
     */
    public void setId(int id) {
        this.id = id;
    }
}


