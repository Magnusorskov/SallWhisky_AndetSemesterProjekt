package application.model;

import application.model.Enums.Fadtype;
import application.model.Enums.Land;

import java.io.Serializable;

/**
 * Repræsenterer et fad, der bruges til lagring af destillat.
 * Arver fra Lagervare og implementerer Serializable for at kunne gemmes og indlæses.
 */
public class Fad extends Lagervare implements Serializable, Historik, Comparable<Fad> {
    private final Land land;
    private final String leverandør;
    private final Fadtype fadType;
    private final double størrelse;
    private int antalBrug;
    private int uniktNummer;
    private Destillat destillat;

    /**
     * Initialiserer et fads oprindelsesland, fadType, leverandør og størrelse.
     * Pre: oprindelsesland, leverandør og fadType er ikke null.
     * Pre: størrelse > 0.
     *
     * @param land       fadets oprindelsesland.
     * @param fadType    fadets type.
     * @param størrelse  fadets størrelse.
     * @param leverandør fadets leverandør.
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
     * Sætter antallet af gange fadet har været brugt.
     *
     * @param antalBrug det nye antal brug for fadet.
     */
    public void setAntalBrug(int antalBrug) {
        this.antalBrug = antalBrug;
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
     * Henter fadets unikke nummer.
     *
     * @return fadets nummer.
     */
    public int getUniktNummer() {
        return uniktNummer;
    }

    /**
     * Sætter fadets unikke nummer.
     *
     * @param uniktNummer det nye nummer for fadet.
     */
    public void setUniktNummer(int uniktNummer) {
        this.uniktNummer = uniktNummer;
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
     * Sætter destillat på fadet.
     * Pre: destillat er ikke null.
     *
     * @param destillat det nye destillat på fadet.
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
     * Henter historikken for fadet.
     * Historikken indeholder fadets nummer, oprindelsesland, leverandør, fadtype, antal brug,
     * placering på lager og størrelse og beskrivelse af destillatet og dets batches, hvis fadet har et destillat.
     *
     * @return en StringBuilder indeholdende fadets historik.
     */
    public StringBuilder hentHistorik() {
        StringBuilder sb = getFadBeskrivelse();
        if (destillat != null) {
            sb.append("\n\nDestillat: " + destillat.getNavn());
            sb.append("\nBatches: " + destillat.getDestillatetsBatchesTilBeskrivelse());
        }
        return sb;
    }

    /**
     * Henter beskrivelsen på fadet, som indeholder information om fadets nummer, oprindelsesland,
     * leverandør, fadtype, antal brug, placering på lager og størrelse.
     *
     * @return beskrivelsen på fadet som StringBuilder.
     */
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

        if (destillat != null) {
            sb.append("\n\tDestillat: " + destillat.getNavn());
        }
        if (getLager() != null) {
            sb.append("\n\tLager: " + getLager().getNavn());
        }

        return "" + sb;
    }

    /**
     * Definerer hvordan fade skal sammenlignes.
     *
     * @param fad fadet der skal sammenlignes med.
     * @return en int der beskriver forholdet i sammenligningen mellem to fade.
     */
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
