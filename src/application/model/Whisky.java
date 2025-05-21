package application.model;

import application.model.Enums.Fadtype;
import application.model.Enums.Kvalitetsstempel;
import application.model.Enums.Mark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Repræsenterer en færdig whisky, som er en blanding af forskellige destillater og eventuelt vand.
 * Implementerer Serializable for at kunne gemmes og indlæses, og Comparable for at kunne sorteres.
 */
public class Whisky implements Comparable<Whisky>, Historik, Serializable {
    private final List<DestillatMængde> destillatMængder;
    private String navn;
    private double literVand;
    private String label;
    private Kvalitetsstempel kvalitetsstempel;
    private int uniktNummer;
    private double antalLiter;
    private double alkoholprocent;

    /**
     * Initialiserer en ny whisky med et givent navn.
     *
     * @param navn navnet på whiskyen.
     */
    public Whisky(String navn) {
        this.navn = navn;
        literVand = 0;
        antalLiter = 0;
        destillatMængder = new ArrayList<>();
    }

    /**
     * Henter whiskyens navn.
     *
     * @return whiskyens navn.
     */
    public String getNavn() {
        return navn;
    }


    /**
     * Henter mængden af tilsat vand i liter.
     *
     * @return mængden af tilsat vand i liter.
     */
    public double getLiterVand() {
        return literVand;
    }

    /**
     * Sætter mængden af tilsat vand i liter.
     *
     * @param literVand den nye mængde af tilsat vand i liter.
     */
    public void setLiterVand(double literVand) {
        this.literVand = literVand;
    }

    /**
     * Henter whiskyens label.
     *
     * @return whiskyens label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sætter whiskyens label.
     *
     * @param label den nye label for whiskyen.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Henter whiskyens unikke nummer.
     *
     * @return whiskyens nummer.
     */
    public int getUniktNummer() {
        return uniktNummer;
    }

    /**
     * Sætter whiskyens unikke nummer.
     *
     * @param uniktNummer det nye nummer for væsken.
     */
    public void setUniktNummer(int uniktNummer) {
        this.uniktNummer = uniktNummer;
    }

    /**
     * Henter den aktuelle væskemængde i væsken i liter.
     *
     * @return den aktuelle væskemængde i liter.
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Sætter den aktuelle væskemængde i væsken i liter.
     *
     * @param antalLiter den nye væskemængde i liter.
     */
    public void setAntalLiter(double antalLiter) {
        this.antalLiter = antalLiter;
    }

    /**
     * Henter alkoholprocenten for væsken.
     *
     * @return whiskyens alkoholprocent.
     */
    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    /**
     * Sætter alkoholprocenten for væsken.
     *
     * @param alkoholprocent den nye alkoholprocent for væsken.
     */
    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }


    //------------------------------------------------------

    /**
     * Opretter en ny mængde af et destillat og tilføjer det til whiskyens blanding.
     * Hvis der findes en destillatMængde med det samme destillat i forvejen,
     * tilføjes antal liter til den destillatMængdes antalLiter.
     * <p>
     * Pre: antalLiter er større end 0.
     * Pre: destillat er ikke null.
     *
     * @param antalLiter den mængde af destillatet der tilføjes i liter.
     * @param destillat  det destillat der tilføjes til whiskyen.
     */
    public void createDestillatMængde(double antalLiter, Destillat destillat) {
        DestillatMængde destillatMængde = new DestillatMængde(antalLiter, destillat);
        this.antalLiter += antalLiter;

        int index = findesDestillatIDestillatMængdeListe(destillat);
        if (index != -1) {
            destillatMængder.get(index).addLiterTilEksisterendeVM(antalLiter);
        } else {
            destillatMængder.add(destillatMængde);
        }
    }

    /**
     * Laver en søgning efter et bestemt destillat i whiskyens destillatMængde liste.
     * Pre: destillat er ikke null.
     *
     * @param destillat det destillat der skal søges efter.
     * @return indekset på den destillatMængde, hvor destillat er på. Returnerer -1 hvis ikke fundet.
     */
    private int findesDestillatIDestillatMængdeListe(Destillat destillat) {
        int i = 0;
        while (i < destillatMængder.size()) {
            if (destillatMængder.get(i).getDestillat() == destillat) {
                return i;
            } else {
                i++;
            }

        }
        return -1;
    }

    //------------------------------------------------------

    /**
     * Beregner det samlede antal liter whiskyen indeholder (summen af alle destillater og tilsat vand).
     *
     * @return det samlede antal liter whisky.
     */
    public double beregnAntalLiter() {
        double antal = 0;
        for (DestillatMængde d : destillatMængder) {
            antal += d.getAntalLiter();
        }
        antal += literVand;
        return antal;
    }

    /**
     * Genererer en historik over whiskyen, inklusive unikt nummer, navn, alkoholprocent, kvalitetsstempel,
     * totalt antal liter, mængde af tilsat vand (hvis relevant), antal flasker og
     * historikken for de enkelte destillater.
     *
     * @return en String der indeholder whiskyens historik.
     */
    public StringBuilder hentHistorik() {
        StringBuilder sb = new StringBuilder();
        sb.append("Whisky: " + uniktNummer + " " + navn);

        double antalLiter = beregnAntalLiter();
        if (antalLiter > 0) {
            if (alkoholprocent > 0) {
                sb.append("\nAlkoholprocent: " + alkoholprocent);
            }
            if (kvalitetsstempel != null) {
                sb.append("\nKvalitetsstempel: " + kvalitetsstempel);
            }
            sb.append("\nAntal liter: " + antalLiter);
            if (literVand > 0) {
                sb.append("\n\tHeraf vand: " + literVand);
            }
            sb.append("\nAntal Flasker: " + beregnAntalFlasker());
            sb.append("\n----------------------------------");
            sb.append("\n\nDestillater:");

            for (DestillatMængde dm : destillatMængder) {
                sb.append("\n" + dm.getDestillat().hentHistorik());
            }
        }
        if (destillatMængder.isEmpty()){
            sb.append("\n----------------------------------");
        }
        return sb;
    }


    /**
     * Beregner det estimerede antal flasker (af 0.7 liter) whiskyen kan fylde.
     *
     * @return det estimerede antal flasker.
     */
    public int beregnAntalFlasker() {
        return (int) (beregnAntalLiter() / 0.7);
    }

    /**
     * Henter de unikke marker fra de destillater der indgår i whiskyen.
     *
     * @return et Set af unikke Mark objekter.
     */
    public Set<Mark> getMarker() {
        Set<Mark> marker = new HashSet<>();
        for (DestillatMængde dm : destillatMængder) {
            marker.addAll(dm.getDestillat().getMarker());
        }
        return marker;
    }

    /**
     * Henter de unikke fadtyper fra de destillater der indgår i whiskyen.
     *
     * @return et Set af unikke Fadtype objekter.
     */
    public Set<Fadtype> getFadtyper() {
        Set<Fadtype> fadtyper = new HashSet<>();
        for (DestillatMængde dm : destillatMængder) {
            fadtyper.add(dm.getDestillat().getFad().getFadType());
        }
        return fadtyper;
    }

    /**
     * Implementering af Comparable interface for at kunne sammenligne Whisky objekter baseret på deres ID i stigende rækkefølge.
     *
     * @param whisky det Whisky objekt der skal sammenlignes med.
     * @return et negativt heltal, nul eller et positivt heltal afhængigt af sammenligningen.
     */
    @Override
    public int compareTo(Whisky whisky) {
        return this.uniktNummer - whisky.getUniktNummer();
    }

    /**
     * Sætter kvalitetsstemplet på whisky'en
     * Metoden sætter stemplet ud fra kriterierne for de forskellige stempler, som er indholdet af vand og destillatets fad.
     */
    public void setKvalitetsStempel() {
        boolean singleCask = true;
        int i = 0;
        while (singleCask && destillatMængder.size() > i) {
            {
                if (!destillatMængder.get(i).getDestillat().isSingleCask()) {
                    singleCask = false;
                } else {
                    i++;
                }
            }
        }
        if (singleCask) {
            if (literVand == 0) {
                kvalitetsstempel = Kvalitetsstempel.SINGLE_CASK_CASK_STRENGTH;
            } else {
                kvalitetsstempel = Kvalitetsstempel.SINGLE_CASK;
            }
        } else {
            if (literVand == 0) {
                kvalitetsstempel = Kvalitetsstempel.SINGLE_MALT_CASK_STRENGTH;
            } else {
                kvalitetsstempel = Kvalitetsstempel.SINGLE_MALT;
            }
        }
    }

    /**
     * Laver en String repræsentation af Whisky objektet (returnerer navnet).
     *
     * @return whiskyens navn.
     */
    @Override
    public String toString() {
        return navn;
    }
}
