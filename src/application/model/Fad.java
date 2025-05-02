package application.model;

import java.io.Serializable;

public class Fad implements Serializable, Lagervare {
    private static int idTæller = 1;
    private String oprindelsesLand;
    private int antalBrug;
    private Fadtype fadType;
    private double størrelse;
    private int reolNummer;
    private int hyldeNummer;
    private int id;

    private Destillat destillat;
    private Lager lager;

    /**
     * Initialiserer et fads oprindelsesland, fadtype og størrelse.
     * Pre: oprindelsesland og fadtype er ikke null.
     * Pre: størrelse > 0.
     *
     * @param oprindelsesLand   fadets oprindelsesland.
     * @param fadType           fadets type.
     * @param størrelse         fadets størrelse.
     */
    public Fad(String oprindelsesLand, Fadtype fadType, double størrelse) {
        this.oprindelsesLand = oprindelsesLand;
        this.fadType = fadType;
        this.størrelse = størrelse;

        id = idTæller;
        idTæller++;

        if (fadType == Fadtype.NEW) {
            this.antalBrug = 0;
        } else {
            this.antalBrug = 1;
        }
    }

    //getter og setter

    public static int getIdTæller() {
        return idTæller;
    }

    public String getOprindelsesLand() {
        return oprindelsesLand;
    }

    public int getAntalBrug() {
        return antalBrug;
    }

    public Fadtype getFadType() {
        return fadType;
    }

    public double getStørrelse() {
        return størrelse;
    }

    public int getId() {
        return id;
    }

    @Override
    public String beskrivelse() {
        return "";
    }

    //sammenhæng til Destillat
    public Destillat getDestillat() {
        return destillat;
    }

    public void setDestillat(Destillat destillat){
        if (this.destillat != destillat){
            this.destillat = destillat;
//            if (destillat != null){
//                destillat.setFad(this);
//            }
        }
    }

    //sammenhæng til lager
    public Lager getLager() {
        return lager;
    }

    public void setLager(Lager lager){
        if (this.lager != lager){
            Lager oldLager = this.lager;
            if (oldLager != null){
                oldLager.removeLagerVare(this);
            }
            this.lager = lager;
            if (lager != null){
                lager.addLagerVare(this, reolNummer, hyldeNummer);
            }
        }
    }

    public double getTilgængeligeLiter() {
        double antalLiter = 0;
        if (destillat == null) {
            antalLiter = størrelse;
        } else {
            antalLiter = størrelse - destillat.beregnAntalLiter();
        }
            return antalLiter;
    }

    @Override
    public int getReol() {
        return reolNummer;
    }

    @Override
    public int getHylde() {
        return hyldeNummer;
    }

    //metoder


}
