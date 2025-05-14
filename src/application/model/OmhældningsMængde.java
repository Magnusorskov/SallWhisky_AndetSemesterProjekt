package application.model;

public class OmhældningsMængde {
    private final double antalLiter;
    private final Destillat destillat;

    OmhældningsMængde(double antalLiter, Destillat destillat) {
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
