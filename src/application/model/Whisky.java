package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

/**
 * Repræsenterer en færdig whisky, som er en blanding af forskellige destillater og eventuelt vand.
 * Implementerer Serializable for at kunne gemmes og indlæses, og Comparable for at kunne sorteres.
 */
public class Whisky implements Serializable, Comparable<Whisky>, Historik {
    private String navn;
    private int id;
    private double alkoholprocent;
    private double literVand;
    private String label;
    private Kvalitetsstempel kvalitetsstempel;

    private final List<DestillatMængde> destillatMængder;

    /**
     * Initialiserer en ny whisky med et givent navn.
     *
     * @param navn navnet på whiskyen.
     */
    public Whisky(String navn) {
        this.navn = navn;
        literVand = 0;
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
     * Henter whiskyens unikke ID.
     *
     * @return whiskyens ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Henter whiskyens alkoholprocent.
     *
     * @return whiskyens alkoholprocent.
     */
    public double getAlkoholprocent() {
        return alkoholprocent;
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
     * Henter whiskyens label.
     *
     * @return whiskyens label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sætter whiskyens alkoholprocent.
     *
     * @param alkoholprocent den nye alkoholprocent for whiskyen.
     */
    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
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
     * Sætter whiskyens label.
     *
     * @param label den nye label for whiskyen.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Sætter whiskyens unikke ID.
     *
     * @param id det nye ID for whiskyen.
     */
    public void setId(int id) {
        this.id = id;
    }

    //------------------------------------------------------

    /**
     * Opretter en ny mængde af et destillat og tilføjer det til whiskyens blanding.
     * Pre: antalLiter er større end 0.
     * Pre: destillat er ikke null.
     *
     * @param antalLiter den mængde af destillatet der tilføjes i liter.
     * @param destillat   det destillat der tilføjes til whiskyen.
     */
    public void createDestillatMængde(double antalLiter, Destillat destillat) {
        DestillatMængde destillatMængde = new DestillatMængde(antalLiter,destillat);
        destillatMængder.add(destillatMængde);
    }

    /**
     * Henter en liste over de destillatmængder der indgår i whiskyen.
     *
     * @return en ny liste indeholdende DestillatMængde objekter.
     */
    public List<DestillatMængde> getDestillatMængder() {
        return new ArrayList<>(destillatMængder);
    }

    //------------------------------------------------------

    /**
     * Beregner det samlede antal liter whiskyen indeholder (summen af alle destillater og tilsat vand).
     *
     * @return det samlede antal liter whisky.
     */
    public double beregnAntalLiter(){
        double antal = 0;
        for (DestillatMængde d : destillatMængder) {
            antal += d.getAntalLiter();
        }
        antal += literVand;
        return antal;
    }

    /**
     * Genererer en historik over whiskyen, inklusive ID, navn, alkoholprocent, totalt antal liter,
     * mængde af tilsat vand (hvis relevant), antal flasker og historikken for de enkelte destillater.
     *
     * @return en String der indeholder whiskyens historik.
     */
    public StringBuilder hentHistorik() {
        StringBuilder sb = new StringBuilder();
        sb.append("Whisky: " + id + " " + navn);
        if (alkoholprocent > 0){
            sb.append("\nAlkoholprocent: " + alkoholprocent);
        }
        double antalLiter = beregnAntalLiter();
        if (antalLiter > 0){
            sb.append("\nAntal liter: " + antalLiter);
            if (literVand > 0){
                sb.append("\nHeraf vand: " + literVand);
            }
            sb.append("\nAntal Flasker: " + beregnAntalFlasker());

        }
        sb.append("\n----------------------------------");

        Map<Destillat, Double> destillater = new HashMap<>();
        for (DestillatMængde dm : getDestillatMængder()) {
            destillater.merge(dm.getDestillat(), dm.getAntalLiter(), Double::sum);
        }

        if(!destillater.isEmpty()) {
            sb.append("\n\nDestillater:\n");

            Iterator<Map.Entry<Destillat, Double>> iterator = destillater.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Destillat, Double> k = iterator.next();
                sb.append("Antal Liter af Destillat: " + k.getValue());
                sb.append("\n" + k.getKey().hentHistorik());
                sb.append("----------------------------------\n\n");
            }
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
    public Set<Mark> getMarker (){
        Set<Mark> marker = new HashSet<>();
        for (DestillatMængde dm : destillatMængder){
            marker.addAll(dm.getDestillat().getMarker());
           }
        return marker;
    }

    /**
     * Henter en de unikke fadtyper fra de destillater der indgår i whiskyen.
     *
     * @return et Set af unikke Fadtype objekter.
     */
    public Set<Fadtype> getFadtyper(){
        Set<Fadtype> fadtyper = new HashSet<>();
        for (DestillatMængde dm : destillatMængder){
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
        return this.id - whisky.getId();
    }


    public void setKvalitetsStempel() {
        boolean singleCask = true;
        int i = 0;
        while (singleCask && destillatMængder.size() > i) {{
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
