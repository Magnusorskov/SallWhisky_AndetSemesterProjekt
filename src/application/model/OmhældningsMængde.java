package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OmhældningsMængde {
    private double antalLiter;
    private final Destillat destillat;


    //TODO java doc
    OmhældningsMængde(double antalLiter, Destillat destillat) {
        this.antalLiter = antalLiter;
        this.destillat = destillat;
        destillat.tapDestillat(antalLiter);


    }

    public double getAntalLiter() {
        return antalLiter;
    }

    //TODO java doc
    public Destillat getDestillat() {
        return destillat;
    }



    public void setLagringstidIMåneder(LocalDate påfyldningsDato) {
        long antalDage = ChronoUnit.DAYS.between(destillat.getPåfyldningsDato(), påfyldningsDato);
        destillat.setLagringstidIMåneder((int) (antalDage / 30.436768));
    }

    //TODO java doc
    public void addLiterTilEksisterendeOM(double antalLiter) {
        this.antalLiter += antalLiter;
        destillat.tapDestillat(antalLiter);
    }
}
