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
            if (lagervare.getLager() != null) {
                lagervare.getLager().getPladser()[lagervare.getReol()][lagervare.getHylde()] = null;
            }
            lagervare.setLager(this);
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
            if (næsteLedigPlads[0] == -1) {
                næsteLedigPlads[0] = lagervare.getReol();
                næsteLedigPlads[1] = lagervare.getHylde();
            }
            pladser[lagervare.getReol()][lagervare.getHylde()] = null;
            lagervare.setLager(null);
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

    /**
     * Henter den næste ledige plads på lageret.
     *
     * @return et int array med index på næste ledige plads på lageret.
     */
    public int[] getNæsteLedigPlads() {
        return næsteLedigPlads;
    }


    /**
     * Finder den næste ledige plads på lageret og opdaterer atributten næsteLedigePlads
     */

    public void opdaterNæsteLedigePlads() {
        int startReol = næsteLedigPlads[0];
        int startHylde = næsteLedigPlads[1];

        if (pladser[startReol][startHylde] != null) {
            boolean fundet = false;
            int reol = startReol;
            int hylde = startHylde;

            while (!fundet) {
                hylde++;
                if (hylde >= pladser[reol].length) {
                    hylde = 1;
                    reol++;
                    if (reol >= pladser.length) {
                        reol = 1;
                    }
                }
                if (pladser[reol][hylde] == null) {
                    næsteLedigPlads[0] = reol;
                    næsteLedigPlads[1] = hylde;
                    fundet = true;
                } else {

                    if (reol == startReol && hylde == startHylde) {
                        næsteLedigPlads[0] = -1;
                        næsteLedigPlads[1] = -1;
                        throw new IllegalStateException("Lageret er fyldt - ingen ledige pladser");
                    }
                }
            }
        }
    }


    /**
     * Sætter næste ledige plads til et bestemt sted på lageret
     *
     * @param reol  reolnummeret på den ønskede plads.
     * @param hylde hyldenummeret på den ønskede plads.
     */

    public void setNæsteLedigPlads(int reol, int hylde) {
        næsteLedigPlads[0] = reol;
        næsteLedigPlads[1] = hylde;
    }

    /**
     * Beregner det samlede antal ledige pladser der er tilbage på lageret
     *
     * @return en int med antal ledige pladser på lageret
     */

    public int antalLedigePladser() {
        if (næsteLedigPlads[0] == -1) {
            return 0;
        }
        int count = 0;
        for (int reol = 1; reol < pladser.length; reol++) {
            for (int hylde = 1; hylde < pladser[reol].length; hylde++) {
                if (pladser[reol][hylde] == null) {
                    count++;
                }
            }
        }
        return count;
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
