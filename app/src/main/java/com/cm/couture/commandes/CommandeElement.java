package com.cm.couture.commandes;


public class CommandeElement {

    private long id;
    private long commande;
    private String model;
    private String imagePagne;
    private String imageModele;
    private float prix;
    private String observation;

    public CommandeElement() {

    }


    public CommandeElement(long id, long commande, String model, String imagePagne, String imageModele, float prix, String observation) {
        this.id = id;
        this.commande = commande;
        this.model = model;
        this.imagePagne = imagePagne;
        this.imageModele = imageModele;
        this.prix = prix;
        this.observation = observation;
    }

    public CommandeElement(long commande, String model, String imagePagne, String imageModele, float prix, String observation) {
        this.commande = commande;
        this.model = model;
        this.imagePagne = imagePagne;
        this.imageModele = imageModele;
        this.prix = prix;
        this.observation = observation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCommande() {
        return commande;
    }

    public void setCommande(long commande) {
        this.commande = commande;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImagePagne() {
        return imagePagne;
    }

    public void setImagePagne(String imagePagne) {
        this.imagePagne = imagePagne;
    }

    public String getImageModele() {
        return imageModele;
    }

    public void setImageModele(String imageModele) {
        this.imageModele = imageModele;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
