package application.model;

import application.model.VæskeMængde.VæskeMængde;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Repræsenterer en mængde af et specifikt destillat, der er blevet omhældt til et nyt destillat.
 * Bruges til at spore sammensætningen af destillater baseret på de anvendte destillater og deres mængder.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */

public class OmhældningsMængde extends VæskeMængde {
    /**
     * Initialiserer en OmhældningsMængdes antal liter og oldDestillat.
     * * Pre: antalLiter er større end 0.
     * * Pre: oldDestillat er ikke null.
     *
     * @param antalLiter   det antal liter man omhælder fra det gamle destillat.
     * @param oldDestillat det gamle destillat man tager væske fra.
     */
    OmhældningsMængde(double antalLiter, Destillat oldDestillat) {
        super(antalLiter, oldDestillat);
        oldDestillat.tapVæske(antalLiter);
    }

    /**
     * Setter lagringstiden på det gamle destillat.
     * Pre: omhældningsDato er ikke null.
     * Pre: omhældningsDato er senere end destillatets påfyldningsDato.
     *
     * @param omhældningsDato dato for påfyldning.
     */
    public void setLagringstidIMåneder(LocalDate omhældningsDato) {
        Destillat destillat = (Destillat) produktionsVæske;
        long antalDage = ChronoUnit.DAYS.between(destillat.getPåfyldningsDato(), omhældningsDato);
        destillat.setLagringstidIMåneder((int) (antalDage / 30.436768));
    }


    /**
     * Returnerer OmhældningsMængdens tilknyttede destillat.
     *
     * @return OmhældningsMængdens destillat.
     */
    public Destillat getDestillat() {
        return (Destillat) produktionsVæske;
    }
}
