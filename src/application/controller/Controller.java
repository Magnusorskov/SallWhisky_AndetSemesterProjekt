package application.controller;

import application.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Controller {
    private static Storage storage;

    public static void setStorage(Storage newStorage) {
        storage = newStorage;
    }

    //-----------------------------------------------------------------------------------------


    /**
     * Initialiserer en batch's bygsort, mark, initialer, rygemateriale, maltbatch, start dato og kommentar og tilføjer den til storage.
     * Pre: bygsort, mark, initialer, rygemateriale, maltbatch og startdato er ikke null.
     *
     * @param bygsort       bygsorten der er i batchen.
     * @param mark          hvilken mark kornet kommer fra.
     * @param initialer     intialerne på personen der håndterer batchen.
     * @param rygemateriale rygematerialet der er brugt i batchen.
     * @param maltBatch     maltbatchen der er brugt i batchen.
     * @param startDato     datoen batchen er startet.
     * @param kommentar     eventuel kommentar til batchen.
     */
    public static Batch createBatch(Bygsort bygsort, Mark mark, String initialer, String rygemateriale, String maltBatch, LocalDate startDato, String kommentar) {
        Batch batch = new Batch(bygsort, mark, initialer, rygemateriale, maltBatch, startDato, kommentar);
        batch.setId(storage.whiskyID());
        storage.addBatch(batch);
        return batch;
    }


    /**
     * Færdiggør en batch med en slutdato, alkoholprocent, væskemængde, kommentar og flytter den til færdige batches i storage.
     * Pre: batch og slutDato er ikke null.
     * Pre: slutDato skal være lig eller efter startDato på batch.
     * Pre: væskemængde > 0.
     * Pre: alkoholprocent skal være større end 0 og mindre eller lig med 100.
     *
     * @param batch          batchen der skal færdiggøres.
     * @param slutDato       datoen for færdiggørelsen.
     * @param alkoholprocent alkoholprocenten på batchen.
     * @param væskemængde    antal liter i batchen.
     * @param kommentar      eventuel kommentar til batchen.
     */
    public static void færdiggørBatch(Batch batch, LocalDate slutDato, double alkoholprocent, double væskemængde, String kommentar) {
        batch.setSlutDato(slutDato);
        batch.setAlkoholprocent(alkoholprocent);
        batch.setVæskemængde(væskemængde);
        batch.setKommentar(kommentar);
        storage.removeIgangværendeBatch(batch);
        storage.addFærdigBatch(batch);
    }

    /**
     * Pre: batch er ikke null
     *
     * @param batch den batch der kræves en beskrivelse af.
     * @return beskrivelse af batch i en String
     */
    public static String getBatchBeskrivelse(Batch batch) {
        return "" + batch.hentHistorik();
    }

    public static List<Batch> getFærdigeBatchesMedTilgængeligeLiter() {
        List<Batch> resultat = new ArrayList<>();
        for (Batch batch : getFærdigeBatches()) {
            if (batch.getVæskemængde() > 0) {
                resultat.add(batch);
            }
        }
        return resultat;
    }


    public static List<Batch> getIgangværendeBatches() {
        return storage.getIgangværendeBatches();
    }

    public static List<Batch> getFærdigeBatches() {
        return storage.getFærdigeBatches();
    }

    public static void removeBatch(Batch batch) {
        storage.removeIgangværendeBatch(batch);
    }

    //---------------------------------------------------------------------------------------------------------------

    public static List<Fad> getFade() {
        return storage.getFade();
    }

    public static List<Fad> getFadeUdenFærdigDestillat() {
        List<Fad> resultat = new ArrayList<>();
        for (Fad f : getFade()) {
            if (f.getDestillat() == null) {
                resultat.add(f);
            } else if (f.getDestillat().getPåfyldningsDato() == null) {
                resultat.add(f);
            }
        }
        return resultat;
    }

    /**
     * Initialiserer et fads oprindelsesland, fadtype, størrelse og tilføjer den til storage.
     * Pre: oprindelsesland og fadtype er ikke null.
     * Pre: størrelse > 0.
     *
     * @param oprindelsesLand fadets oprindelsesland.
     * @param fadType         fadets type.
     * @param størrelse       fadets størrelse.
     */
    public static Fad createFad(String oprindelsesLand, Fadtype fadType, double størrelse) {
        Fad fad = new Fad(oprindelsesLand, fadType, størrelse);
        fad.setId(storage.fadID());
        storage.addFad(fad);
        return fad;
    }

    /**
     * Pre: batch, fad og navn er ikke null
     *
     * @param antalLiter mængden af liter der påfyldes fadet.
     * @param batch      batchen der bliver tilføjet liter af.
     * @param navn       navnet på destillatet der skal oprettes hvis der ikke er et destillat i forvejen.
     * @param fad        fadet mængden skal påfyldes i.
     */
    public static void påfyldFad(double antalLiter, Batch batch, String navn, Fad fad) {
        Destillat destillat = fad.getDestillat();
        if (destillat == null) {
            destillat = Controller.createDestillat(navn, fad);
        }
        if (antalLiter > fad.getTilgængeligeLiter()) {
            throw new IllegalArgumentException("Der er ikke nok plads i fadet");
        } else if (antalLiter > batch.getVæskemængde()) {
            throw new IllegalArgumentException("Der er ikke nok væske i batchen");
        } else {
            destillat.createMængde(antalLiter, batch);
        }
    }

    public static String getFadBeskrivelse(Fad fad) {
        StringBuilder sb = new StringBuilder();
        String result;
        if (fad != null) {
            sb.append(fad.hentHistorik());
        }
        Destillat destillat = fad.getDestillat();
        if (destillat != null) {
            sb.append("\n\nDestillat: " + destillat.getNavn());
            sb.append("\nBatches: " + fad.getDestillat().destilatBatches());
        }
        result = String.valueOf(sb);
        return result;
    }

    //----------------------------------------------------------------------------------------------------

    public static List<Lager> getLagre() {
        return storage.getLagre();
    }

    public static Lager createLager(String navn, int antalReoler, int antalHylder, String adresse) {
        Lager lager = new Lager(navn, antalReoler, antalHylder, adresse);
        storage.addLager(lager);
        return lager;
    }


    //-----------------------------------------------------------------------------------------------------


    public static Destillat createDestillat(String navn, Fad fad) {
        Destillat destillat = new Destillat(navn, fad);
        destillat.setId(storage.destillatID());
        storage.addDestillat(destillat);
        return destillat;
    }

    public static void færdiggørDestillat(double alkoholprocent, LocalDate påfyldningsDato, Destillat destillat) {
        destillat.setAlkoholprocent(alkoholprocent);
        destillat.setPåfyldningsDato(påfyldningsDato);
        destillat.setAntalLiter(destillat.beregnAntalLiter());

        int antalBrug = destillat.getFad().getAntalBrug() + 1;
        destillat.getFad().setAntalBrug(antalBrug);
    }

    public static List<Destillat> getDestillater() {
        return storage.getDestillater();
    }

    public static List<Destillat> getFærdigeDestillater() {
        List<Destillat> resultat = new ArrayList<>();
        for (Destillat destillat : getDestillater()) {
            if (destillat.getAntalLiter() > 0 && destillat.getPåfyldningsDato().isBefore(LocalDate.now().minusYears(3))) {
                resultat.add(destillat);
            }
        }
        return resultat;
    }
    // TODO slet?
    public static String getDestillatBeskrivelse(Destillat destillat) {
        StringBuilder sb = new StringBuilder();
        String result;
        if (destillat != null) {
            sb.append(destillat.hentHistorik());
        }
        result = String.valueOf(sb);
        return result;
    }

    //------------------------------------------------------------------------------------------------------------------

    public static Whisky createWhisky(String navn) {
        Whisky whisky = new Whisky(navn);
        whisky.setId(storage.whiskyID());
        storage.addWhisky(whisky);
        return whisky;
    }

    public static Set<Whisky> getWhiskyer() {
        return storage.getWhiskyer();
    }

    public static List<Whisky> getIgangværendeWhisky() {
        List<Whisky> resultat = new ArrayList<>();
        for (Whisky w : Controller.getWhiskyer()) {
            if (w.getAlkoholprocent() == 0.0) {
                resultat.add(w);
            }
        }
        return resultat;
    }

    public static void tapningAfDestillat(double antalLiter, Destillat destillat, Whisky whisky) {
        whisky.createDestillatMængde(antalLiter, destillat);
    }

    public static String getWhiskeyBeskrivelse(Whisky whisky) {
        return whisky.hentHistorik();
    }

    public static double getAntalLiterWhisky(Whisky whisky){
        return whisky.beregnAntalLiter();
    }

    public static int getAntalFlasker(Whisky whisky){
        return whisky.beregnAntalFlasker();
    }

    public static String genereLabel (Whisky whisky, String alkoholprocent) {
        return "Handcrafted from organic barley harvested from our fields " + whisky.getMarker() + " and "
                + ". Double distilled slowly in direct fired copper pot stills. Matured in carefully selected " + whisky.getFadtyper()
                + " casks for 3 years. Bottled in " + LocalDate.now().getYear() + "."
                + "\n\nBottle number x of " + whisky.beregnAntalFlasker() + " bottles"
                + "\n\n70 cl.\n" + alkoholprocent + "% Vol.";
    }

    public static void påfyldVand(int antalLiter, Whisky whisky) {
        whisky.setLiterVand(whisky.getLiterVand() + antalLiter);
    }

    public static void færdiggørWhisky(Whisky whisky, String label, double alkoholprocent){
        whisky.setLabel(label);
        whisky.setAlkoholprocent(alkoholprocent);
    }


}
