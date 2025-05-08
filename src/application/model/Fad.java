package application.model;

import java.io.Serializable;

public class Fad extends Lagervare implements Serializable {
    private String oprindelsesLand;
    private int antalBrug;
    private Fadtype fadType;
    private double størrelse;
    private int id;

    private Destillat destillat;

    /**
     * Initialiserer et fads oprindelsesland, fadtype og størrelse.
     * Pre: oprindelsesland og fadtype er ikke null.
     * Pre: størrelse > 0.
     *
     * @param oprindelsesLand fadets oprindelsesland.
     * @param fadType         fadets type.
     * @param størrelse       fadets størrelse.
     */
    public Fad(String oprindelsesLand, Fadtype fadType, double størrelse) {
        super();
        this.oprindelsesLand = oprindelsesLand;
        this.fadType = fadType;
        this.størrelse = størrelse;

        if (fadType == Fadtype.NEW) {
            this.antalBrug = 0;
        } else {
            this.antalBrug = 1;
        }
    }

    //getter og setter
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

    public void setId(int id) {
        this.id = id;
    }

    public void setAntalBrug(int antalBrug) {
        this.antalBrug = antalBrug;
    }

    // TODO tom krop skal udfyldes
    @Override
    public String beskrivelse() {
        return "";
    }

    //sammenhæng til Destillat
    public Destillat getDestillat() {
        return destillat;
    }

    // TODO dokumentation
    public void setDestillat(Destillat destillat) {
        if (this.destillat != destillat) {
            this.destillat = destillat;
//            if (destillat != null){
//                destillat.setFad(this);
//            }
        }
    }

    //metoder
    // TODO dokumentation
    public double getTilgængeligeLiter() {
        double antalLiter;
        if (destillat == null) {
            antalLiter = størrelse;
        } else {
            antalLiter = størrelse - destillat.beregnAntalLiter();
        }
        return antalLiter;
    }
    // TODO dokumentation
    public StringBuilder hentHistorik(){
        StringBuilder sb = new StringBuilder();
        sb.append("Fad nr: " + id);
        sb.append("\nOprindelses Land: " + oprindelsesLand);
        sb.append("\nFad type: " + fadType);
        sb.append("\nAntal brug: " + antalBrug);
        sb.append("\nFad størrelse: " + størrelse);
        return sb;
    }

    @Override
    public String toString() {
        if (destillat != null) {
            return id + " " + fadType + " liter: " + størrelse + " Destillat: " + destillat.getNavn();
        } else {
            return id + " " + fadType + " liter: " + størrelse;
        }
    }

}
