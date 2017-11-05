package com.cm.couture.commandes;


import com.cm.couture.clients.Client;

import java.util.Date;

public class Commande {

    private long id;
    private long client;
    private float montant;
    private float avance;
    private String datecommande;
    private String datelivraison;
    private String livree;

    public Commande() {

    }

    public Commande(long id,long client, float montant, float avance, String datecommande, String datelivraison, String livree) {
        this.client = client;
        this.id = id;
        this.montant = montant;
        this.avance = avance;
        this.datecommande = datecommande;
        this.datelivraison = datelivraison;
        this.livree = livree;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public float getAvance() {
        return avance;
    }

    public void setAvance(float avance) {
        this.avance = avance;
    }

    public String getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(String datecommande) {
        this.datecommande = datecommande;
    }

    public String getDatelivraison() {
        return datelivraison;
    }

    public void setDatelivraison(String datelivraison) {
        this.datelivraison = datelivraison;
    }

    public String getLivree() {
        return livree;
    }

    public void setLivree(String livree) {
        this.livree = livree;
    }
}
