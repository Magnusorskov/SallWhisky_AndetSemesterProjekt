package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Whisky implements Serializable, Comparable<Whisky> {
    private String navn;
    private int nummer;
    private double alkoholprocent;
    private int flasker;
    private double literVand;
    private String historie;
    private String label;
    private List<DestillatMængde> destillatMængder;

    public Whisky(String navn) {
        this.navn = navn;
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

    public void setHistorie(String historie) {
        this.historie = historie;
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

        }
        return antal;
    }

    public String hentHistorik() {
        return "s";
    }

    private int beregnAntalFlasker() {
        return 0;
    }

    @Override
    public int compareTo(Whisky w) {
        return w.getNummer() - this.nummer;
    }
}
