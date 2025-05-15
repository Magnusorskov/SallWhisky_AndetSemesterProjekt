package application.model;

public class OmhældningsMængde {
    private double antalLiter;
    private final Destillat destillat;

    //TODO java doc
    OmhældningsMængde(double antalLiter, Destillat destillat) {
        this.antalLiter = antalLiter;
        this.destillat = destillat;
        destillat.tapDestillat(antalLiter);
    }

    //TODO java doc
    public double getAntalLiter() {
        return antalLiter;
    }

    //TODO java doc
    public Destillat getDestillat() {
        return destillat;
    }

    //TODO java doc
    public void addLiterTilEksisterendeOM(double antalLiter) {
        this.antalLiter += antalLiter;
        destillat.tapDestillat(antalLiter);
    }
}
