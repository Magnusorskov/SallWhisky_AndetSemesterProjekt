package application.model.Væske;

import application.model.Historik;

import java.io.Serializable;

public abstract class Væske implements Historik, Serializable {
    protected int id;
    protected double antalLiter;
    protected double alkoholprocent;

    public Væske() {
    }

    /**
     * Henter batchens unikke ID.
     *
     * @return batch'ens ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Henter den aktuelle væskemængde i batchen i liter.
     *
     * @return den aktuelle væskemængde i liter.
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Henter alkoholprocenten for batchen.
     *
     * @return batch'ens alkoholprocent.
     */
    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    /**
     * Sætter den aktuelle væskemængde i batchen i liter.
     *
     * @param antalLiter den nye væskemængde i liter.
     */
    public void setAntalLiter(double antalLiter) {
        this.antalLiter = antalLiter;
    }

    /**
     * Sætter alkoholprocenten for væsken.
     *
     * @param alkoholprocent den nye alkoholprocent for væsken.
     */
    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    /**
     * Sætter batchens unikke ID.
     *
     * @param id det nye ID for batchen.
     */
    public void setId(int id) {
        this.id = id;
    }
}
