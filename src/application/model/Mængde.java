package application.model;

public class Mængde {
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
    Mængde(double antalLiter, Batch batch) {
        this.antalLiter = antalLiter;
        this.batch = batch;
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public Batch getBatch() {
        return batch;
    }
}
