package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Destillat implements Serializable {
    private final List<BatchMængde> mængder = new ArrayList<>();
    private String navn;
    private int id;
    private double alkoholprocent;
    private double antalLiter;
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

    /**
     * Initialiserer en BatchMængdes antal liter og batch og tilføjer den til destillatet.
     * Pre: antalLiter er større end 0.
     * Pre: batch er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     * @param batch      den batch man tager væske fra.
     * @return den færdige BatchMængde.
     */
    public BatchMængde createMængde(double antalLiter, Batch batch) {
        BatchMængde batchMængde = new BatchMængde(antalLiter, batch);
        mængder.add(batchMængde);
        return batchMængde;
    }

    //metoder

    /**
     * Beregner antal liter destillatet indeholder.
     *
     * @return antal liter destillatet indeholder.
     */
    public double beregnAntalLiter() {
        double liter = 0;
        for (BatchMængde m : mængder) {
            liter += m.getAntalLiter();
        }
        return liter;
    }

    /**
     * Laver en String med id og liter om alle batches destillatet indeholder.
     *
     * @return String med id og liter om alle batches destillatet indeholder.
     */
    public String destilatBatches() {
        StringBuilder sb = new StringBuilder();
        for (BatchMængde m : mængder) {
            sb.append("\n   Batch: " + m.getBatch().getId() + "(Liter: " + m.getAntalLiter() + ")");
        }
        String s = sb + "";
        return s;
    }

    /**
     * Tapper destillatet en bestemt mængde liter.
     * Pre: tapLiter > 0.
     * Pre: tapLiter <= destillatets tapLiter.
     *
     * @param tapLiter antallet af liter der skal tappes.
     */
    public void tapDestillat(double tapLiter) {
        this.antalLiter -= tapLiter;
    }

    /**
     * Laver en historik over destillatet der indeholder destillatets id, navn, påfyldningsdato, alkoholprocent, fadets historik
     * og alle batches historik som destillatet indeholder.
     *
     * @return Stringbuilder med historikken.
     */
    public StringBuilder hentHistorik() {
        StringBuilder sb = new StringBuilder();
        sb.append("Destillat: " + id + " " + navn);
        sb.append("\nPåfyldnings dato: " + påfyldningsDato);
        if (alkoholprocent > 0) {
            sb.append("\nAlkoholprocent: " + alkoholprocent);
        }
        sb.append("\n----------------------------------");

        sb.append("\n\nFad:\n" + fad.hentHistorik());
        sb.append("\n----------------------------------");

        sb.append("\n\nBatches:\n");
        Map<Batch, Double> batches = new HashMap<>();
        for (BatchMængde bm : getMængder()) {
            batches.merge(bm.getBatch(), bm.getAntalLiter(), Double::sum);
        }
        for (Map.Entry<Batch, Double> k : batches.entrySet()) {
            sb.append(k.getKey().hentHistorik());
            sb.append("\nAntal Liter af batch: " + k.getValue() + "\n\n");
        }

        return sb;
    }
    // TODO dokumentation
    public ArrayList<Mark> getMarker() {
        ArrayList<Mark> marker = new ArrayList<>();
        for (BatchMængde bm : mængder) {
            if (!marker.contains(bm.getBatch().getMark())) {
                marker.add(bm.getBatch().getMark());
            }
        }
        return marker;
    }
    // TODO dokumentation
    public ArrayList<Fadtype> getFadtyper() {
        ArrayList<Fadtype> fadtyper = new ArrayList<>();
        fadtyper.add(fad.getFadType());
        return fadtyper;
    }

    @Override
    public String toString() {
        return navn;
    }
}
