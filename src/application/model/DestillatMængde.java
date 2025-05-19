package application.model;

import application.model.VæskeMængde.VæskeMængde;

import java.io.Serializable;

/**
 * Repræsenterer en mængde af et specifikt destillat, der er blevet tappet og indgår i en whisky.
 * Bruges til at spore sammensætningen af en whisky baseret på de anvendte destillater og deres mængder.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public class DestillatMængde extends VæskeMængde {

    /**
     * Initialiserer en DestillatMængdes antal liter og destillat.
     * Pre: antalLiter er større end 0.
     * Pre: destillat er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     * @param destillat  det destillat man tager væske fra.
     */
    DestillatMængde(double antalLiter, Destillat destillat) {
        super(antalLiter,destillat);
        destillat.tapDestillat(antalLiter);
    }

    public void addLiterTilEksisterendeDM(double antalLiter) {
        this.antalLiter += antalLiter;
        Destillat destillat = (Destillat) væske;
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
