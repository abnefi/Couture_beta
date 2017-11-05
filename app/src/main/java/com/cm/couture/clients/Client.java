package com.cm.couture.clients;


public class Client {

    private Long id;
    private String nom;
    private String telephone;
    private String teint;
    private String image;
    private String sexe;
    private int hpoitrine;
    private int lgbuste;
    private int lgcorsage;
    private int lgrobe;
    private int encolure;
    private int cadevant;
    private int trpoitrine;
    private int ecartsein;
    private int trtaille;
    private int trbassin;
    private int lgjupe;
    private int lgpantalon;
    private int lgdos;
    private int cados;
    private int largdos;
    private int lgmanche;
    private int trmanche;
    private int poignet;
    private int pente;
    private String observation;

    public Client() {
    }

    public Client(String nom, String telephone, String teint, String image, String sexe) {
        this.nom = nom;
        this.telephone = telephone;
        this.teint = teint;
        this.image = image;
        this.sexe = sexe;
    }

    public Client(Long id, String nom, String telephone, String teint, String image, String sexe, int hpoitrine, int lgbuste, int lgcorsage, int lgrobe, int encolure, int cadevant, int trpoitrine, int ecartsein, int trtaille, int trbassin, int lgjupe, int lgpantalon, int lgdos, int cados, int largdos, int lgmanche, int trmanche, int poignet, int pente, String observation) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.teint = teint;
        this.image = image;
        this.sexe = sexe;
        this.hpoitrine = hpoitrine;
        this.lgbuste = lgbuste;
        this.lgcorsage = lgcorsage;
        this.lgrobe = lgrobe;
        this.encolure = encolure;
        this.cadevant = cadevant;
        this.trpoitrine = trpoitrine;
        this.ecartsein = ecartsein;
        this.trtaille = trtaille;
        this.trbassin = trbassin;
        this.lgjupe = lgjupe;
        this.lgpantalon = lgpantalon;
        this.lgdos = lgdos;
        this.cados = cados;
        this.largdos = largdos;
        this.lgmanche = lgmanche;
        this.trmanche = trmanche;
        this.poignet = poignet;
        this.pente = pente;
        this.observation = observation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTeint() {
        return teint;
    }

    public void setTeint(String teint) {
        this.teint = teint;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getHpoitrine() {
        return hpoitrine;
    }

    public void setHpoitrine(int hpoitrine) {
        this.hpoitrine = hpoitrine;
    }

    public int getLgbuste() {
        return lgbuste;
    }

    public void setLgbuste(int lgbuste) {
        this.lgbuste = lgbuste;
    }

    public int getLgcorsage() {
        return lgcorsage;
    }

    public void setLgcorsage(int corsage) {
        this.lgcorsage = corsage;
    }

    public int getLgrobe() {
        return lgrobe;
    }

    public void setLgrobe(int lgrobe) {
        this.lgrobe = lgrobe;
    }

    public int getEncolure() {
        return encolure;
    }

    public void setEncolure(int encolure) {
        this.encolure = encolure;
    }

    public int getCadevant() {
        return cadevant;
    }

    public void setCadevant(int cadevant) {
        this.cadevant = cadevant;
    }

    public int getTrpoitrine() {
        return trpoitrine;
    }

    public void setTrpoitrine(int trpoitrine) {
        this.trpoitrine = trpoitrine;
    }

    public int getEcartsein() {
        return ecartsein;
    }

    public void setEcartsein(int ecartsein) {
        this.ecartsein = ecartsein;
    }

    public int getTrtaille() {
        return trtaille;
    }

    public void setTrtaille(int trtaille) {
        this.trtaille = trtaille;
    }

    public int getTrbassin() {
        return trbassin;
    }

    public void setTrbassin(int trbassin) {
        this.trbassin = trbassin;
    }

    public int getLgjupe() {
        return lgjupe;
    }

    public void setLgjupe(int lgjupe) {
        this.lgjupe = lgjupe;
    }

    public int getLgpantalon() {
        return lgpantalon;
    }

    public void setLgpantalon(int lgpantalon) {
        this.lgpantalon = lgpantalon;
    }

    public int getLgdos() {
        return lgdos;
    }

    public void setLgdos(int lgdos) {
        this.lgdos = lgdos;
    }

    public int getCados() {
        return cados;
    }

    public void setCados(int cados) {
        this.cados = cados;
    }

    public int getLargdos() {
        return largdos;
    }

    public void setLargdos(int largdos) {
        this.largdos = largdos;
    }

    public int getLgmanche() {
        return lgmanche;
    }

    public void setLgmanche(int lgmanche) {
        this.lgmanche = lgmanche;
    }

    public int getTrmanche() {
        return trmanche;
    }

    public void setTrmanche(int trmanche) {
        this.trmanche = trmanche;
    }

    public int getPoignet() {
        return poignet;
    }

    public void setPoignet(int poignet) {
        this.poignet = poignet;
    }

    public int getPente() {
        return pente;
    }

    public void setPente(int pente) {
        this.pente = pente;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
