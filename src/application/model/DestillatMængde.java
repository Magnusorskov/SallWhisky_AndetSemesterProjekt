package application.model;

import java.io.Serializable;

public class DestillatMængde implements Serializable {
    private double antalLiter;
    private Destillat destillat;

    /**
     * Initialiserer en DestillatMængdes antal liter og destillat.
     * Pre: antalLiter er større end 0.
     * Pre: destillat er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     * @param destillat  det destillat man tager væske fra.
     */
    DestillatMængde(double antalLiter, Destillat destillat) {
        this.antalLiter = antalLiter;
        this.destillat = destillat;
        destillat.tapDestillat(antalLiter);
    }

    /**
     * Returnerer en double med DestillatMængdens liter.
     *
     * @return DestillatMængdens liter som double.
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Returnerer DestillatMængdens tilknyttede batch.
     *
     * @return DestillatMængdens destillat.
     */
    public Destillat getDestillat() {
        return destillat;
    }
}
