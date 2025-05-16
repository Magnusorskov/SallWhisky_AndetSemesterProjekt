package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Repræsenterer en mængde af et specifikt destillat, der er blevet omhældt til et nyt destillat.
 *  * Bruges til at spore sammensætningen af destillater baseret på de anvendte destillater og deres mængder.
 *  * Implementerer Serializable for at kunne gemmes og indlæses.
 */

public class OmhældningsMængde implements Serializable {
    private double antalLiter;
    private final Destillat destillat;


    /**
     * Initialiserer en OmhældningsMængdes antal liter og destillat.
     *      * Pre: antalLiter er større end 0.
     *      * Pre: destillat er ikke null.
     * @param antalLiter det antal liter man omhælder fra destillatet.
     * @param destillat det destillat man tager væske fra.
     */
    OmhældningsMængde(double antalLiter, Destillat destillat) {
        this.antalLiter = antalLiter;
        this.destillat = destillat;
        destillat.tapDestillat(antalLiter);


    }

    /**
     * Getter en double med antal liter
     * @return double med antal liter
     */

    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Getter destillat
     * @return et destillat
     */
    public Destillat getDestillat() {
        return destillat;
    }


    /**
     * Setter lagringstiden på destillatet
     * Pre: påfyldningsDato er ikke null
     * @param påfyldningsDato dato for påfyldning
     */
    public void setLagringstidIMåneder(LocalDate påfyldningsDato) {
        long antalDage = ChronoUnit.DAYS.between(destillat.getPåfyldningsDato(), påfyldningsDato);
        destillat.setLagringstidIMåneder((int) (antalDage / 30.436768));
    }

    /**
     * Tilføjer antal liter til omhældning
     * @param antalLiter antal liter man ønsker at tilføje
     */
    public void addLiterTilEksisterendeOM(double antalLiter) {
        this.antalLiter += antalLiter;
        destillat.tapDestillat(antalLiter);
    }
}
