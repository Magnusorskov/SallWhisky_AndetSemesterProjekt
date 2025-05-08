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
        if (beregnAntalLiter() > 0){
            sb.append("\nAntal liter: " + beregnAntalLiter());
            if (literVand > 0){
                sb.append("\nHeraf vand: " + literVand);
            }
            sb.append("\nAntal Flasker: " + beregnAntalFlasker());

        }
        sb.append("\n----------------------------------");

        Map<Destillat, Double> destillater = new HashMap<>();
        for (DestillatMængde dm : getDestillatMængder()){
            Destillat destillat = dm.getDestillat();
            if(!destillater.containsKey(destillat)){
                destillater.put(destillat, dm.getAntalLiter());
            } else {
                double liter = destillater.get(destillat) + dm.getAntalLiter();
                destillater.put(destillat, liter);
            }
        }
        sb.append("\n\nDestillater:\n");

        Iterator<Map.Entry<Destillat, Double>> iterator = destillater.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Destillat, Double> k = iterator.next();
            sb.append("Antal Liter af Destillat: " + k.getValue());
            sb.append("\n" + k.getKey().hentHistorik() + "\n\n");
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
    // Todo lav om til set
    public ArrayList<Fadtype> getFadtyper(){
        ArrayList<Fadtype> fadtyper = new ArrayList<>();
        for (DestillatMængde dm : destillatMængder){
            for (Fadtype f : dm.getDestillat().getFadtyper()){
                if(!fadtyper.contains(f)){
                    fadtyper.add(f);
                }
            }
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
