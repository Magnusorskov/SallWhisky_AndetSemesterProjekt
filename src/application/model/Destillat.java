package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Destillat implements Serializable {
    private static int idTæller = 1;
    private final List<Mængde> mængder = new ArrayList<>();
    private String navn;
    private int id;
    private double alkoholprocent;
    private LocalDate påfyldningsDato;
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
        id = idTæller;
        idTæller++;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }

    public void setPåfyldningsDato(LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
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
    public List<Mængde> getMængder() {
        return new ArrayList<>(mængder);
    }

    public Mængde createMængde(double antalLiter, Batch batch) {
        Mængde mængde = new Mængde(antalLiter, batch);
        mængder.add(mængde);
        return mængde;
    }

    //metoder

    /**
     * Beregner antal liter destillatet indeholder.
     *
     * @return antal liter for destillat
     */
    public double beregnAntalLiter() {
        double liter = 0;
        for (Mængde m : mængder) {
            liter += m.getAntalLiter();
        }
        return liter;
    }

    public String destilatBatches() {
        StringBuilder sb = new StringBuilder();
        for (Mængde m : mængder) {
            sb.append("Batch: " + m.getBatch().getId() + "(Liter: " + m.getAntalLiter() + ")\n");
        }
        String s = sb + "";
        return s;
    }

}
