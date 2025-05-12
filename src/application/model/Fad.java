package application.model;

import java.io.Serializable;

/**
 * Repræsenterer et fad, der bruges til lagring af destillat.
 * Arver fra Lagervare og implementerer Serializable for at kunne gemmes og indlæses.
 * Klassen indeholder information om fadets oprindelse, type, størrelse og hvor mange gange det er blevet brugt.
 * Et fad kan indeholde et enkelt destillat.
 */
public class Fad extends Lagervare implements Serializable {
    private Land land;
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
     * @param land fadets oprindelsesland.
     * @param fadType         fadets type.
     * @param størrelse       fadets størrelse.
     */
    public Fad(Land land, Fadtype fadType, double størrelse) {
        super();
        this.land = land;
        this.fadType = fadType;
        this.størrelse = størrelse;

        if (fadType == Fadtype.NEW) {
            this.antalBrug = 0;
        } else {
            this.antalBrug = 1;
        }
    }

    //getter og setter
    /**
     * Henter fadets oprindelsesland.
     *
     * @return fadets oprindelsesland.
     */
    public Land getOprindelsesLand() {
        return land;
    }

    /**
     * Henter antallet af gange fadet har været brugt.
     *
     * @return antal gange fadet har været brugt.
     */
    public int getAntalBrug() {
        return antalBrug;
    }

    /**
     * Henter fadets type.
     *
     * @return fadets type.
     */
    public Fadtype getFadType() {
        return fadType;
    }

    /**
     * Henter fadets størrelse i liter.
     *
     * @return fadets størrelse i liter.
     */
    public double getStørrelse() {
        return størrelse;
    }

    /**
     * Henter fadets unikke ID.
     *
     * @return fadets ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sætter fadets unikke ID.
     *
     * @param id det nye ID for fadet.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sætter antallet af gange fadet har været brugt.
     *
     * @param antalBrug det nye antal brug for fadet.
     */
    public void setAntalBrug(int antalBrug) {
        this.antalBrug = antalBrug;
    }

    // TODO tom krop skal udfyldes
    /**
     * Returnerer en beskrivelse af fadet.
     * Bemærk: Metoden har en tom krop og skal implementeres.
     *
     * @return en tom streng.
     */
//    @Override
//    public String beskrivelse() {
//        return hentHistorik() + super.getHylde() + super.getReol();
//    }

    //sammenhæng til Destillat
    /**
     * Henter det destillat der er på fadet.
     * Note: Returnerer null, hvis fadet er tomt.
     *
     * @return det destillat der er på fadet.
     */
    public Destillat getDestillat() {
        return destillat;
    }


    /**
     *Sætter destillat på fadet
     *Pre: destillat er ikke null
     *
     * @param destillat er fadets destillat
     */
    public void setDestillat(Destillat destillat) {
        if (this.destillat != destillat) {
            this.destillat = destillat;
//            if (destillat != null){
//                destillat.setFad(this);
//            }
        }
    }


    /**
     * Beregner antallet af liter der er ledige i fadet.
     *
     * @return antal liter der er tilgængelige i fadet.
     */
    public double getTilgængeligeLiter() {
        double antalLiter;
        if (destillat == null) {
            antalLiter = størrelse;
        } else {
            antalLiter = størrelse - destillat.beregnAntalLiter();
        }
        return antalLiter;
    }

    /**
     * Henter historikken for fadet som en String.
     * Historikken indeholder fadets ID, oprindelsesland, fadtype, antal brug og størrelse.
     *
     * @return en StringBuilder indeholdende fadets historik.
     */
    public StringBuilder hentHistorik(){
        StringBuilder sb = new StringBuilder();
        sb.append("Fad nr: " + id);
        sb.append("\nOprindelses Land: " + land);
        sb.append("\nFad type: " + fadType);
        sb.append("\nAntal brug: " + antalBrug);
        sb.append("\nFad størrelse: " + størrelse);
        return sb;
    }

    /**
     * Laver en String repræsentation af Fad objektet.
     * Hvis fadet indeholder et destillat, inkluderes destillatets navn i strengen.
     *
     * @return en String der beskriver fadet (inkl. destillatnavn hvis relevant).
     */ //TODO tilføj lagernavn hvis den er på lager
    @Override
    public String toString() {
        if (destillat != null) {
            return id + " " + fadType + " liter: " + størrelse + " Destillat: " + destillat.getNavn();
        } else {
            return id + " " + fadType + " liter: " + størrelse;
        }
    }

}
