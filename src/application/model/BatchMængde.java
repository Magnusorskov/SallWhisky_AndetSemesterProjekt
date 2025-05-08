package application.model;

import java.io.Serializable;

public class BatchMængde implements Serializable {
    private double antalLiter;
    private Batch batch;

    /**
     * Initialiserer en BatchMængdes antal liter og batch.
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

    /**
     * Returnerer en double med batchmængdens liter.
     *
     * @return batchmængdens liter som double.
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Returnerer batchmængdens tilknyttede batch.
     *
     * @return batchmængdens batch.
     */
    public Batch getBatch() {
        return batch;
    }
}
