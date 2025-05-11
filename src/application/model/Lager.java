package application.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Repræsenterer et lager, der kan indeholde forskellige typer af lagervarer på et antal reoler og hylder.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 * Klassen håndterer placering og hentning af lagervarer inden for lagerets struktur.
 */
public class Lager implements Serializable {

    private final Lagervare[][] pladser;
    private String navn;
    private String adresse;
    private int[] næsteLedigPlads = new int[2];


    /**
     * Initialiserer et lagers navn, adresse og antal reoler og hylder.
     * Pre: navn og adresse er ikke null.
     * Pre: antal reoler og hylder > 0.
     *
     * @param navn        lagerets navn.
     * @param adresse     lagerets adresse.
     * @param antalReoler lagerets antal reoler.
     * @param antalHylder lagerets antal hylder.
     */

    public Lager(String navn, int antalReoler, int antalHylder, String adresse) {
        this.navn = navn;
        this.adresse = adresse;
        pladser = new Lagervare[antalReoler + 1][antalHylder + 1];
        næsteLedigPlads[0] = 1;
        næsteLedigPlads[1] = 1;
    }

    /**
     * Henter lagerets navn.
     *
     * @return lagerets navn.
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Henter lagerets adresse.
     *
     * @return lagerets adresse.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Beregner det samlede antal pladser i lageret.
     *
     * @return det samlede antal pladser i lageret.
     */
    public int getStørrelsePåLager() {
        return getAntalReoler() * getAntalHylder();
    }

    /**
     * Tilføjer en lagervare til en specifik placering i lageret.
     * Pre: lagervare er ikke null.
     * Pre: reol og hylde er inden for lagerets grænser (1 til antalReoler/antalHylder).
     *
     * @param lagervare den lagervare der skal tilføjes.
     * @param reol      reolnummeret hvor lagervaren skal placeres.
     * @param hylde     hyldenummeret hvor lagervaren skal placeres.
     */
    public void addLagerVare(Lagervare lagervare, int reol, int hylde) {
        if (pladser[reol][hylde] == null) {
            pladser[reol][hylde] = lagervare;
        }
    }

    /**
     * Fjerner en specifik lagervare fra lageret hvis den findes.
     * Pre: lagervare er ikke null.
     *
     * @param lagervare den lagervare der skal fjernes.
     */
    public void removeLagerVare(Lagervare lagervare) {
        if (pladser[lagervare.getReol()][lagervare.getHylde()] == lagervare) {
            pladser[lagervare.getReol()][lagervare.getHylde()] = null;
        }
    }

    /**
     * Returnerer en lagervare fra en specifik placering i lageret.
     * Pre: reol og hylde er inden for lagerets grænser (1 til antalReoler/antalHylder).
     * Note: Returnerer null hvis der ikke er en lagervare på pladsen.
     *
     * @param reol  reolnummeret på den ønskede plads.
     * @param hylde hyldenummeret på den ønskede plads.
     * @return lagervaren på den angivne plads.
     */
    public Lagervare getLagerVare(int reol, int hylde) {
        if (pladser[reol][hylde] == null) {
            return null;
        } else return pladser[reol][hylde];
    }

    /**
     * Henter mængden af hylder på hver reol.
     *
     * @return det samlede antal hylder.
     */
    public int getAntalHylder() {
        return pladser[1].length - 1;
    }

    /**
     * Henter det samlede antal reoler i lageret.
     *
     * @return det samlede antal reoler.
     */
    public int getAntalReoler() {
        return pladser.length - 1;
    }


    /**
     * Henter to-dimensionelt array repræsentationen af lagerets pladser.
     *
     * @return et 2D array der repræsenterer lagerets pladser.
     */
    public Lagervare[][] getPladser() {
        return Arrays.copyOf(pladser, pladser.length);
    }

    public int[] getNæsteLedigPlads(){
        return næsteLedigPlads;
    }

    public void indsætVarePåLager(int reol, int hylde, Lagervare lagervare){
        if (reol < 1 || reol > pladser.length - 1){
            throw new IllegalArgumentException("Indtast gyldigt reolnr - Der er " + getAntalReoler() + " reoler på lageret");
        }

        if (hylde < 1 || hylde > pladser[0].length - 1){
            throw new IllegalArgumentException("Indtast gyldigt hyldenr - Der er " + getAntalHylder() + " hylder på lageret");
        }

        if (pladser[reol][hylde] != null){
            throw new IllegalArgumentException("Pladsen er allerede optaget");
        }

        pladser[reol][hylde] = lagervare;

        opdaterNæsteLedigePlads();
    }

    public void opdaterNæsteLedigePlads(){
        boolean fundet = false;
        int reol = næsteLedigPlads[0];
        int hylde = næsteLedigPlads[1];

        while (!fundet){
            if (hylde >= pladser[reol].length){
                hylde = 1;
                reol++;
            }

            if (reol >= pladser.length){
                reol = 1;
            }

            if (pladser[reol][hylde] == null){
                næsteLedigPlads[0] = reol;
                næsteLedigPlads[1] = hylde;
                fundet = true;
            }
            hylde++;
        }

        if (reol == næsteLedigPlads[0] && hylde == næsteLedigPlads[1]){
            throw new IllegalStateException("Lageret er fyldt - ingen ledige pladser");
        }
    }






    /**
     * Laver en String repræsentation af Lager objektet.
     *
     * @return en String der indeholder lagerets navn.
     */
    @Override
    public String toString() {
        return "Lager: " + navn;
    }
}
