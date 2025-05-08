package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Whisky implements Serializable, Comparable<Whisky> {
    private String navn;
    private int id;
    private double alkoholprocent;
    private double literVand;
    private String label;

    private final List<DestillatMængde> destillatMængder;

    // TODO dokumentation
    public Whisky(String navn) {
        this.navn = navn;
        literVand = 0;
        destillatMængder = new ArrayList<>();
    }

    public String getNavn() {
        return navn;
    }

    public int getId() {
        return id;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public double getLiterVand() {
        return literVand;
    }

    public String getLabel() {
        return label;
    }

    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    public void setLiterVand(double literVand) {
        this.literVand = literVand;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    //------------------------------------------------------

    // TODO dokumentation
    public void createDestillatMængde(double antalLiter, Destillat destillat) {
        DestillatMængde destillatMængde = new DestillatMængde(antalLiter,destillat);
        destillatMængder.add(destillatMængde);
    }

    public List<DestillatMængde> getDestillatMængder() {
        return new ArrayList<>(destillatMængder);
    }

    //------------------------------------------------------

    // TODO dokumentation
    public double beregnAntalLiter(){
        double antal = 0;
        for (DestillatMængde d : destillatMængder) {
            antal += d.getAntalLiter();
        }
        antal += literVand;
        return antal;
    }

    // TODO dokumentation
    public String hentHistorik() {
        StringBuilder sb = new StringBuilder();
        sb.append("Whisky: " + id + " " + navn);
        if (alkoholprocent > 0){
            sb.append("\nAlkoholprocent: " + alkoholprocent);
        }
        double antalLiter = beregnAntalLiter();
        if (antalLiter > 0){
            sb.append("\nAntal liter: " + antalLiter);
            if (literVand > 0){
                sb.append("\nHeraf vand: " + literVand);
            }
            sb.append("\nAntal Flasker: " + beregnAntalFlasker());

        }
        sb.append("\n----------------------------------");

        Map<Destillat, Double> destillater = new HashMap<>();
        for (DestillatMængde dm : getDestillatMængder()) {
            destillater.merge(dm.getDestillat(), dm.getAntalLiter(), Double::sum);
        }

        if(!destillater.isEmpty()) {
            sb.append("\n\nDestillater:\n");

            Iterator<Map.Entry<Destillat, Double>> iterator = destillater.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Destillat, Double> k = iterator.next();
                sb.append("Antal Liter af Destillat: " + k.getValue());
                sb.append("\n" + k.getKey().hentHistorik());
                sb.append("----------------------------------\n\n");
            }
        }

        return "" + sb;
    }

    // TODO dokumentation
    public int beregnAntalFlasker() {
        return (int) (beregnAntalLiter() / 0.7);
    }

    // TODO dokumentation
    public Set<Mark> getMarker (){
        Set<Mark> marker = new HashSet<>();
        for (DestillatMængde dm : destillatMængder){
            marker.addAll(dm.getDestillat().getMarker());
           }
        return marker;
    }
    // Todo lav om til addAll
    public Set<Fadtype> getFadtyper(){
        Set<Fadtype> fadtyper = new HashSet<>();
        for (DestillatMængde dm : destillatMængder){
            fadtyper.add(dm.getDestillat().getFad().getFadType());
        }
        return fadtyper;
    }

    // TODO dokumentation
    @Override
    public int compareTo(Whisky w) {
        return w.getId() - this.id;
    }

    @Override
    public String toString() {
        return navn;
    }
}
