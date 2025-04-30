package application.controller;

import application.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Controller {
    private static Storage storage;

    public static void setStorage(Storage newStorage) {
        storage = newStorage;
    }

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

    public static List<Batch> getIgangværendeBatches() {
        return storage.getIgangværendeBatches();
    }

    public static List<Batch> getFærdigeBatches() {
        return storage.getFærdigeBatches();
    }

    public static void removeBatch(Batch batch) {
        storage.removeIgangværendeBatch(batch);
    }

}
