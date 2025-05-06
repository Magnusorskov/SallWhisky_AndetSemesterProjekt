package application.model;

import java.io.Serializable;

public class BatchMængde implements Serializable {
    private double antalLiter;
    private Batch batch;

    /**
     * Initialiserer en mængdes antal liter og batch.
     * Pre: antalLiter er større end 0.
     * Pre: batch er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     * @param batch      den batch man tager væske fra.
     */
    BatchMængde(double antalLiter, Batch batch) {
        this.antalLiter = antalLiter;
        this.batch = batch;
        batch.tapBatch(antalLiter);
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public Batch getBatch() {
        return batch;
    }
}
