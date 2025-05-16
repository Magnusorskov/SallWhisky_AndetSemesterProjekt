package application.model.VæskeMængde;

import application.model.Historik;
import application.model.Væske.Væske;

import java.io.Serializable;

/**
 * Repræsenterer en væskemængde af en specifik væske, der er blevet tappet.
 * Bruges til at holde styr på hvor meget af en given væske der er brugt i f.eks. et destillat.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public abstract class VæskeMængde implements Serializable {
    protected double antalLiter;
    protected Væske væske;

    /**
     * Initialiserer en VæskeMængdes antal liter og væske.
     * Pre: antalLiter er større end 0.
     * Pre: væske er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra væskeen.
     */
    public VæskeMængde(double antalLiter, Væske væske) {
        this.antalLiter = antalLiter;
        this.væske = væske;
    }

    /**
     * Returnerer en double med væskemængdens liter.
     *
     * @return væskemængdens liter som double.
     */
    public double getAntalLiter() {
        return antalLiter;
    }




}
