package application.controller;

import application.model.*;

import java.time.LocalDate;
import java.util.List;

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

    public static String getBatchBeskrivelse(Batch batch) {
        StringBuilder sb = new StringBuilder();
        String result = "";
        if (batch != null) {
            sb.append("Batch nr: " + batch.getId());
            sb.append("\nBygsort: " + batch.getBygsort() + "(" + batch.getMark() + ")");
            sb.append("\nMaltbatch: " + batch.getMaltBatch());
            String rygemateriale = batch.getRygemateriale();
            if (rygemateriale != null) {
                sb.append("\nRygemateriale: " + rygemateriale);
            }
            sb.append("\nAlkoholprocent: " + batch.getAlkoholprocent());
            sb.append("\nInitialer: " + batch.getInitialer());
            sb.append("\n\nKommentar: " + "\n" + batch.getKommentar());
            result = String.valueOf(sb);
        }
        return result;
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
        storage.addFad(fad);
        return fad;
    }

    public static boolean påfyldFad(double antalLiter, Batch batch, String navn, Fad fad) {
        boolean gennemført = true;
        Destillat destillat = fad.getDestillat();
        if (destillat == null) {
            destillat = Controller.createDestillat(navn, fad);
        }
        if (antalLiter > fad.getTilgængeligeLiter()) {
            gennemført = false;
        } else {
            destillat.createMængde(antalLiter, batch);
        }

        return gennemført;
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
        storage.addDestillat(destillat);
        return destillat;
    }

    public static void færdiggørDestillat(double alkoholsprocent, LocalDate påfyldningsDato, Destillat destillat) {
        destillat.setAlkoholprocent(alkoholsprocent);
        destillat.setPåfyldningsDato(påfyldningsDato);
    }

    public static List<Destillat> getDestillater() {
        return storage.getDestillater();
    }

    //------------------------------------------------------------------------------------------------------------------

}
