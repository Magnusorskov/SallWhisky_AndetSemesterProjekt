package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Whisky implements Serializable, Comparable<Whisky> {
    private String navn;
    // Todo nummer generation
    private int nummer;
    private double alkoholprocent;
    private int flasker;
    private double literVand;
    private String historie;
    private String label;
    private List<DestillatMængde> destillatMængder;

    public Whisky(String navn) {
        this.navn = navn;
        literVand = 0;
        destillatMængder = new ArrayList<>();
    }

    public String getNavn() {
        return navn;
    }

    public int getNummer() {
        return nummer;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public int getFlasker() {
        return flasker;
    }

    public double getLiterVand() {
        return literVand;
    }

    public String getHistorie() {
        return historie;
    }

    public String getLabel() {
        return label;
    }

    public List<DestillatMængde> getDestillatMængder() {
        return new ArrayList<>(destillatMængder);
    }

    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    public void setFlasker(int flasker) {
        this.flasker = flasker;
    }

    public void setLiterVand(double literVand) {
        this.literVand = literVand;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    //------------------------------------------------------

    public void createDestillatMængde(double antalLiter, Destillat destillat) {
        DestillatMængde destillatMængde = new DestillatMængde(antalLiter,destillat);
        destillatMængder.add(destillatMængde);
    }
    //------------------------------------------------------

    public double beregnAntalLiter(){
        double antal = 0;
        for (DestillatMængde d : destillatMængder) {
            antal += d.getAntalLiter();
        }
        antal += literVand;
        return antal;
    }

    public void hentHistorik() {
        StringBuilder sb = new StringBuilder();
    }

    private void beregnAntalFlasker() {
        setFlasker((int) (beregnAntalLiter() / 0.7));
    }

    @Override
    public int compareTo(Whisky w) {
        return w.getNummer() - this.nummer;
    }
}
