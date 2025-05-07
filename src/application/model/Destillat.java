package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Destillat implements Serializable {
    private String navn;
    private int id;
    private double alkoholprocent;
    private double antalLiter;
    private LocalDate påfyldningsDato;

    private final List<BatchMængde> mængder = new ArrayList<>();
    private Fad fad;

    /**
     * Initialiserer et destillats navn, fad.
     * Pre: navn, fad ikke er null.
     *
     * @param navn destillatets navn.
     * @param fad  det fad destillatet ligger på.
     */
    public Destillat(String navn, Fad fad) {
        this.navn = navn;
        this.fad = fad;
        fad.setDestillat(this);
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }

    public void setPåfyldningsDato(LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
    }

    public double getAntalLiter() {
        return antalLiter;
    }

    public void setAntalLiter(double antalLiter) {
        this.antalLiter = antalLiter;
    }

    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    //sammenhæng til fad
    public Fad getFad() {
        return fad;
    }

//    public void setFad(Fad fad) {
//        this.fad = fad;
//    }

    //sammenhæng til mængde
    public List<BatchMængde> getMængder() {
        return new ArrayList<>(mængder);
    }

    public BatchMængde createMængde(double antalLiter, Batch batch) {
        BatchMængde batchMængde = new BatchMængde(antalLiter, batch);
        mængder.add(batchMængde);
        return batchMængde;
    }

    //metoder
    /**
     * Beregner antal liter destillatet indeholder.
     *
     * @return antal liter for destillat
     */
    public double beregnAntalLiter() {
        double liter = 0;
        for (BatchMængde m : mængder) {
            liter += m.getAntalLiter();
        }
        return liter;
    }

    public String destilatBatches() {
        StringBuilder sb = new StringBuilder();
        for (BatchMængde m : mængder) {
            sb.append("\n   Batch: " + m.getBatch().getId() + "(Liter: " + m.getAntalLiter() + ")");
        }
        String s = sb + "";
        return s;
    }

    public void tapDestillat(double antalLiter) {
        this.antalLiter -= antalLiter;
    }

    public StringBuilder hentHistorik(){
        StringBuilder sb = new StringBuilder();
        sb.append("Destillat: " + id + " " + navn);
        sb.append("\nPåfyldnings dato: " + påfyldningsDato);
        if (antalLiter > 0){
            sb.append("\nAntal liter: " + antalLiter);
        }
        if (alkoholprocent > 0){
            sb.append("\nAlkoholprocent: " + alkoholprocent);
        }

        sb.append("\n\nFad\n" + fad.hentHistorik());

        sb.append("\n\nBatches:\n");
        for (BatchMængde bm : getMængder()){
            sb.append(bm.getBatch().hentHistorik() + "\n\n");
        }
        return sb;
    }

    @Override
    public String toString() {
        return navn;
    }
}
