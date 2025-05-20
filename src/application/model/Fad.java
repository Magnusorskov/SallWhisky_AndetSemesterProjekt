package application.model;

import application.model.Enums.Fadtype;
import application.model.Enums.Land;

import java.io.Serializable;

/**
 * Repræsenterer et fad, der bruges til lagring af destillat.
 * Arver fra Lagervare og implementerer Serializable for at kunne gemmes og indlæses.
 * Klassen indeholder information om fadets oprindelse, type, størrelse og hvor mange gange det er blevet brugt.
 * Et fad kan indeholde et enkelt destillat.
 */
public class Fad extends Lagervare implements Serializable, Historik, Comparable<Fad> {
    private final Land land;
    private final String leverandør;
    private int antalBrug;
    private final Fadtype fadType;
    private final double størrelse;
    private int uniktNummer;

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
    public Fad(Land land, Fadtype fadType, double størrelse, String leverandør) {
        this.land = land;
        this.fadType = fadType;
        this.størrelse = størrelse;
        this.leverandør = leverandør;

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
    public int getUniktNummer() {
        return uniktNummer;
    }

    /**
     * Sætter fadets unikke ID.
     *
     * @param uniktNummer det nye ID for fadet.
     */
    public void setUniktNummer(int uniktNummer) {
        this.uniktNummer = uniktNummer;
    }

    /**
     * Sætter antallet af gange fadet har været brugt.
     *
     * @param antalBrug det nye antal brug for fadet.
     */
    public void setAntalBrug(int antalBrug) {
        this.antalBrug = antalBrug;
    }


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
            antalLiter = størrelse - destillat.getAntalLiter();
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
        StringBuilder sb = getFadBeskrivelse();
        if (destillat != null) {
            sb.append("\n\nDestillat: " + destillat.getNavn());
            sb.append("\nBatches: " + destillat.getDestillatetsBatchesTilBeskrivelse());
        }
        return sb;
    }

    //TODO java doc
    public StringBuilder getFadBeskrivelse() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fad nr: " + uniktNummer);
        sb.append("\nOprindelses Land: " + land);
        sb.append("\nLeverandør: " + leverandør);
        sb.append("\nFad type: " + fadType);
        sb.append("\nAntal brug: " + antalBrug);
        sb.append("\nFad størrelse: " + størrelse);
        sb.append("\n" + super.getPlacering());

        return sb;
    }

    /**
     * Laver en String repræsentation af Fad objektet.
     * Hvis fadet indeholder et destillat, inkluderes destillatets navn i strengen.
     *
     * @return en String der beskriver fadet (inkl. destillatnavn hvis relevant).
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(uniktNummer + " " + fadType + " liter: " + størrelse + " Antal brug: " + antalBrug);

        if (destillat != null){
            sb.append("\n\tDestillat: " + destillat.getNavn());
        }
        if (getLager() != null) {
            sb.append("\n\tLager: " + getLager().getNavn());
        }

        return "" + sb;
    }

    @Override
    public int compareTo(Fad fad) {
        boolean thisHarDestillat = this.getDestillat() != null;
        boolean otherHarDestillat = fad.getDestillat() != null;


        if (thisHarDestillat && !otherHarDestillat) {
            return -1;
        }
        if (!thisHarDestillat && otherHarDestillat) {
            return 1;
        }

        if (thisHarDestillat) {
            return Integer.compare(this.getUniktNummer(), fad.getUniktNummer());
        }
        return Double.compare(fad.getTilgængeligeLiter(), this.getTilgængeligeLiter());
    }

}
