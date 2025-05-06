package application.model;

import java.io.Serializable;

public class DestillatMængde implements Serializable {
    private double antalLiter;
    private Destillat destillat;

    DestillatMængde(double antalLiter, Destillat destillat) {
        this.antalLiter = antalLiter;
        this.destillat = destillat;
        destillat.tapDestillat(antalLiter);
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public Destillat getDestillat() {
        return destillat;
    }
}
