package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Destillat {
    private static int idTæller = 1;
    private String navn;
    private int id;
    private double alkoholprocent;
    private LocalDate påfyldningsDato;

    private Fad fad;
    private final List<Mængde> mængder = new ArrayList<>();

    public Destillat(String navn, Fad fad) {
        this.navn = navn;
        this.fad = fad;
        id = idTæller;
        idTæller++;
    }

    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    public void setPåfyldningsDato(LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
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

    public Mængde createMængde (double antalLiter, Batch batch){
        Mængde mængde = new Mængde(antalLiter, batch);
        mængder.add(mængde);
        return mængde;
    }

    //metoder
    public double beregnAntalLiter(){
        double liter = 0;
        for (Mængde m : mængder){
            liter += m.getAntalLiter();
        }
        return liter;
    }

}
