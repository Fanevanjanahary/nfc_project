package faneva.parking.model;

/**
 * Created by ANDRIAMIADANTSOA on 30/04/2017.
 */
public class Vehicule {
    private int id;
    private String marque;
    private String categorie;
    private String type;
    private String couleur;
    private String matriculation;

    public Vehicule() {
    }

    public Vehicule(int id, String marque, String categorie, String type, String couleur, String matriculation) {
        this.id = id;
        this.marque = marque;
        this.categorie = categorie;
        this.type = type;
        this.couleur = couleur;
        this.matriculation = matriculation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getMatriculation() {
        return matriculation;
    }

    public void setMatriculation(String matriculation) {
        this.matriculation = matriculation;
    }
}
