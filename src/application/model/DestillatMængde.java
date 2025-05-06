package application.model;

import java.io.Serializable;

public class DestillatMængde implements Serializable {
    double antalLiter;
    Destillat destillat;

    DestillatMængde(double antalLiter, Destillat destillat) {
        this.antalLiter = antalLiter;
        this.destillat = destillat;
        destillat.tapDestillat(antalLiter);
    }
}
