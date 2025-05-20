package application.model.VæskeMængde;

import application.model.Væske.ProduktionsVæske;

import java.io.Serializable;

/**
 * Repræsenterer en væskemængde af en specifik væske, der er blevet tappet.
 * Bruges til at holde styr på hvor meget af en given væske der er brugt i f.eks. et destillat.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public abstract class VæskeMængde implements Serializable {
    protected double antalLiter;
    protected ProduktionsVæske produktionsVæske;

    /**
     * Initialiserer en VæskeMængdes antal liter og væske.
     * Pre: antalLiter er større end 0.
     * Pre: væske er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra væskeen.
     */
    public VæskeMængde(double antalLiter, ProduktionsVæske produktionsVæske) {
        this.antalLiter = antalLiter;
        this.produktionsVæske = produktionsVæske;
    }

    /**
     * Returnerer en double med væskemængdens liter.
     *
     * @return væskemængdens liter som double.
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    //TODO javadoc
    public ProduktionsVæske getVæske() {
        return produktionsVæske;
    }

    /**
     * Tilføjer antal liter til omhældning
     * @param antalLiter antal liter man ønsker at tilføje
     */
    public void addLiterTilEksisterendeVM(double antalLiter) {
        this.antalLiter += antalLiter;
        produktionsVæske.tapVæske(antalLiter);
    }
}
