package application.model;

import application.model.VæskeMængde.VæskeMængde;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Repræsenterer en mængde af et specifikt destillat, der er blevet omhældt til et nyt destillat.
 *  * Bruges til at spore sammensætningen af destillater baseret på de anvendte destillater og deres mængder.
 *  * Implementerer Serializable for at kunne gemmes og indlæses.
 */

public class OmhældningsMængde extends VæskeMængde {
    /**
     * Initialiserer en OmhældningsMængdes antal liter og oldDestillat.
     *      * Pre: antalLiter er større end 0.
     *      * Pre: oldDestillat er ikke null.
     * @param antalLiter det antal liter man omhælder fra destillatet.
     * @param oldDestillat det oldDestillat man tager væske fra.
     */
    OmhældningsMængde(double antalLiter, Destillat oldDestillat) {
        super(antalLiter, oldDestillat);
        oldDestillat.tapDestillat(antalLiter);
    }

    /**
     * Setter lagringstiden på destillatet
     * Pre: påfyldningsDato er ikke null
     * @param påfyldningsDato dato for påfyldning
     */
    public void setLagringstidIMåneder(LocalDate påfyldningsDato) {
        Destillat destillat = (Destillat) væske;
        long antalDage = ChronoUnit.DAYS.between(destillat.getPåfyldningsDato(), påfyldningsDato);
        destillat.setLagringstidIMåneder((int) (antalDage / 30.436768));
    }

    /**
     * Tilføjer antal liter til omhældning
     * @param antalLiter antal liter man ønsker at tilføje
     */
    public void addLiterTilEksisterendeOM(double antalLiter) {
        Destillat destillat = (Destillat) væske;
        this.antalLiter += antalLiter;
        destillat.tapDestillat(antalLiter);
    }

    /**
     * Returnerer DestillatMængdens tilknyttede batch.
     *
     * @return DestillatMængdens destillat.
     */
    public Destillat getDestillat() {
        return (Destillat) væske;
    }
}
