package application.model;

public class Mængde {
    private double antalLiter;
    private Batch batch;

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
