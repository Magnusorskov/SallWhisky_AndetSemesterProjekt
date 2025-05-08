package application.model;

import java.io.Serializable;
import java.util.Arrays;

public class Lager implements Serializable {

    private final int antalHylder;
    private final int antalReoler;
    private final Lagervare[][] pladser;
    private String navn;
    private String adresse;


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
        this.antalHylder = antalHylder;
        this.antalReoler = antalReoler;
        this.adresse = adresse;
        pladser = new Lagervare[antalReoler + 1][antalHylder + 1];
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
        return (antalHylder) * (antalReoler);
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
     * Henter det samlede antal hylder i lageret.
     *
     * @return det samlede antal hylder.
     */
    public int getAntalHylder() {
        return antalHylder;
    }

    /**
     * Henter det samlede antal reoler i lageret.
     *
     * @return det samlede antal reoler.
     */
    public int getAntalReoler() {
        return antalReoler;
    }


    /**
     * Henter to-dimensionelt array repræsentationen af lagerets pladser.
     *
     * @return et 2D array der repræsenterer lagerets pladser.
     */
    public Lagervare[][] getPladser() {
        return Arrays.copyOf(pladser, pladser.length);
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
