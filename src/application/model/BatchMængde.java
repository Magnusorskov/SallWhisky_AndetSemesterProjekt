package application.model;

import application.model.VæskeMængde.VæskeMængde;

/**
 * Repræsenterer en mængde af en specifik batch, der er blevet tappet.
 * Bruges til at holde styr på hvor meget af en given batch der er brugt i et destillat.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public class BatchMængde extends VæskeMængde {

    /**
     * Initialiserer en BatchMængdes antal liter og batch.
     * Pre: antalLiter er større end 0.
     * Pre: batch er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     * @param batch      den batch man tager væske fra.
     */
    BatchMængde(double antalLiter, Batch batch) {
        super(antalLiter, batch);
        batch.tapVæske(antalLiter);
    }


    /**
     * Returnerer BatchMængdens tilknyttede batch.
     *
     * @return BatchMængdens destillat.
     */
    public Batch getBatch() {
        return (Batch) produktionsVæske;
    }
}
