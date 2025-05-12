package application.controller;

import application.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Abstrakt klasse der fungerer som controller for applikationen.
 * Indeholder statiske metoder til at håndtere oprettelse, færdiggørelse og hentning af forskellige model objekter.
 * Klassen er afhængig af et Storage interface for at håndtere persistens.
 */
public abstract class Controller {
    private static Storage storage;


    /**
     * Sætter storage objektet.
     * Pre: newStorage er ikke null
     *
     * @param newStorage er det storage objekt storage bliver sat til
     */
    public static void setStorage(Storage newStorage) {
        storage = newStorage;
    }

    //-----------------------------------------------------------------------------------------


    /**
     * Initialiserer en batch's bygsort, mark, initialer, rygemateriale, maltbatch, start dato og kommentar og tilføjer den til storage.
     * Pre: bygsort, mark, initialer, rygemateriale, maltbatch og startdato er ikke null.
     * Note: kommentar kan være null, da en kommentar ikke er et krav.
     *
     * @param bygsort       bygsorten der er i batchen.
     * @param mark          hvilken mark kornet kommer fra.
     * @param initialer     intialerne på personen der håndterer batchen.
     * @param rygemateriale rygematerialet der er brugt i batchen.
     * @param maltBatch     maltbatchen der er brugt i batchen.
     * @param startDato     datoen batchen er startet.
     * @param kommentar     eventuel kommentar til batchen.
     * @return den oprettede batch
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
     * Henter beskrivelsen på den valgte batch.
     * Pre: batch er ikke null
     *
     * @param batch den batch der kræves en beskrivelse af.
     * @return beskrivelse af batch i en String
     */
    public static String getBatchBeskrivelse(Batch batch) {
        return "" + batch.hentHistorik();
    }


    /**
     * Henter batches med tilgængelige liter tilbage.
     *
     * @return en liste med batches med tilgængelige liter
     */
    public static List<Batch> getFærdigeBatchesMedTilgængeligeLiter() {
        List<Batch> resultat = new ArrayList<>();
        for (Batch batch : getFærdigeBatches()) {
            if (batch.getVæskemængde() > 0) {
                resultat.add(batch);
            }
        }
        return resultat;
    }


    /**
     * Henter de batches der er igangværende.
     *
     * @return en liste med igangværende batches
     */
    public static List<Batch> getIgangværendeBatches() {
        return storage.getIgangværendeBatches();
    }

    /**
     * Henter de færdige batches.
     *
     * @return en liste med færdige batches
     */
    public static List<Batch> getFærdigeBatches() {
        return storage.getFærdigeBatches();
    }

    /**
     * Fjerner batch fra igangværende batches.
     * Pre: batch er ikke null
     *
     * @param batch den batch der skal fjernes fra igangværende batches
     */
    public static void removeBatch(Batch batch) {
        storage.removeIgangværendeBatch(batch);
    }

    //---------------------------------------------------------------------------------------------------------------


    /**
     * Henter fade.
     *
     * @return en liste med fade.
     */
    public static List<Fad> getFade() {
        return storage.getFade();
    }


    /**
     * Henter fade der har ufærdige destillater i sig eller som er tilgængelige.
     *
     * @return en liste med tilgængelige fade og fade med ufærdige destillater i sig.
     */
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
     * Pre: land og fadtype er ikke null.
     * Pre: størrelse > 0.
     *
     * @param land fadets oprindelsesland.
     * @param fadType         fadets type.
     * @param størrelse       fadets størrelse.
     */ //TODO Lav mulighed for at oprette flere fade af gangen.
    public static Fad createFad(Land land, Fadtype fadType, double størrelse) {
        Fad fad = new Fad(land, fadType, størrelse);
        fad.setId(storage.fadID());
        storage.addFad(fad);
        return fad;
    }

    /**
     * Påfylder et antal liter af en batch på et fads destillat og hvis der ikke findes et destillat
     * oprettes der et destillat først.
     * Pre: batch, fad og navn er ikke null.
     * Pre: antalLiter er større end 0.
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

    /**
     * Henter et fads beskrivelse.
     *
     * @param fad er ikke null.
     * @return en string med beskrivelse på et fad og dets batches (hvis relevant).
     */
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

    /**
     * Henter lagre.
     *
     * @return en liste med lagre.
     */
    public static List<Lager> getLagre() {
        return storage.getLagre();
    }

    /**
     * Initialiserer et lagers navn, antalReoler, antalHylder, adresse og tilføjer det til storage.
     * Pre: navn og adresse er ikke null.
     * Pre: antal reoler og hylder > 0.
     *
     * @param navn        navnet på lageret.
     * @param antalReoler antal reoler på lageret.
     * @param antalHylder antal hylder på lageret.
     * @param adresse     adressen på lageret.
     * @return det oprettede lager.
     */
    public static Lager createLager(String navn, int antalReoler, int antalHylder, String adresse) {
        Lager lager = new Lager(navn, antalReoler, antalHylder, adresse);
        storage.addLager(lager);
        return lager;
    }

    /**
     * Placerer en lagervare på et lager
     * Pre: Lager og lagervare er ikke null
     *
     * @param lager det lager man ønsker at placere varen på
     * @param lagervare lagervaren man ønsker at placere på lageret.
     * @param reol reolnummeret på den ønskede plads.
     * @param hylde hyldenummeret på den ønskede plads.
     */

    public static void placerVarePåLager(Lager lager, Lagervare lagervare, int reol, int hylde){
        lager.indsætVarePåLager(reol,hylde,lagervare);
    }

    public static void placerVarePåLagerMedNæsteLedigePlads(Lager lager,Lagervare lagervare){
        int[] næsteLedigePlads = lager.getNæsteLedigPlads();
        int reol = næsteLedigePlads[0];
        int hylde = næsteLedigePlads[1];
        lager.indsætVarePåLager(reol,hylde,lagervare);
    }

    public void fjernLagerVare(Lagervare lagervare){
        Lager lager = lagervare.getLager();
        lager.removeLagerVare(lagervare);
    }


    //-----------------------------------------------------------------------------------------------------

    /**
     * Initialiserer et destillats navn, fad og tilføjer det til storage.
     * Pre: navn, fad ikke er null.
     *
     * @param navn navnet på destillatet.
     * @param fad  fadet hvorpå destillatet skal påfyldes.
     * @return det oprettede destillat.
     */
    public static Destillat createDestillat(String navn, Fad fad) {
        Destillat destillat = new Destillat(navn, fad);
        destillat.setId(storage.destillatID());
        storage.addDestillat(destillat);
        return destillat;
    }

    /**
     * Færdiggører et destillat med alkoholprocent og påfyldningsDato og tæller fadets brug op med én.
     * Pre: alkoholprocent, påfyldningsdato og destillat er ikke null.
     *
     * @param alkoholprocent  alkoholprocenten på destillatet.
     * @param påfyldningsDato påfyldningsdatoen på destillatet.
     * @param destillat       der skal færdiggøres.
     */
    public static void færdiggørDestillat(double alkoholprocent, LocalDate påfyldningsDato, Destillat destillat) {
        destillat.setAlkoholprocent(alkoholprocent);
        destillat.setPåfyldningsDato(påfyldningsDato);
        destillat.setAntalLiter(destillat.beregnAntalLiter());

        int antalBrug = destillat.getFad().getAntalBrug() + 1;
        destillat.getFad().setAntalBrug(antalBrug);
    }

    /**
     * Henter alle destillater.
     *
     * @return en liste med destillater.
     */
    public static List<Destillat> getDestillater() {
        return storage.getDestillater();
    }

    /**
     * Finder destillater der har en påfyldningsdato der er mere end 3 år tilbage.
     *
     * @return en liste med færdige destillater der er over 3 år gamle.
     */
    public static List<Destillat> getFærdigeDestillater() {
        List<Destillat> resultat = new ArrayList<>();
        for (Destillat destillat : getDestillater()) {
            if (destillat.getAntalLiter() > 0 && destillat.getPåfyldningsDato().isBefore(LocalDate.now().minusYears(3))) {
                resultat.add(destillat);
            }
        }
        return resultat;
    }

    /**
     * Henter en beskrivelse på det valgte destillat.
     * Pre: destillat er ikke null.
     *
     * @param destillat der ønskes beskrivelse af.
     * @return en string med beskrivelse af det valgte destillat.
     */
    public static String getDestillatBeskrivelse(Destillat destillat) {
        StringBuilder sb = new StringBuilder();
        sb.append(destillat.hentHistorik());
        return String.valueOf(sb);
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * Initialiserer en whiskys navn og tilføjer det til storage.
     *
     * @param navn navnet på whiskyen.
     * @return den oprettede whisky.
     */
    public static Whisky createWhisky(String navn) {
        Whisky whisky = new Whisky(navn);
        whisky.setId(storage.whiskyID());
        storage.addWhisky(whisky);
        return whisky;
    }

    /**
     * Henter alle whiskyer.
     *
     * @return en liste med whiskyer.
     */
    public static Set<Whisky> getWhiskyer() {
        return storage.getWhiskyer();
    }

    /**
     * Henter whiskyer der er igangværende.
     *
     * @return en liste med igangværende whiskyer.
     */
    public static List<Whisky> getIgangværendeWhisky() {
        List<Whisky> resultat = new ArrayList<>();
        for (Whisky w : Controller.getWhiskyer()) {
            if (w.getAlkoholprocent() == 0.0) {
                resultat.add(w);
            }
        }
        return resultat;
    }

    /**
     * Tapper et destillat for et antal liter og opretter en destillat mængde på whiskyen.
     * Pre: destillat og whisky er ikke null.
     * Pre: antalLiter > 0.
     * Pre: Antal liter i destillat skal være >= antalLiter
     *
     * @param antalLiter antal liter der ønskes tappet fra destillatet
     * @param destillat  det destillat der ønskes tappet fra
     * @param whisky     den whisky der ønskes en destillat mængde til
     */
    public static void tapningAfDestillat(double antalLiter, Destillat destillat, Whisky whisky) {
        whisky.createDestillatMængde(antalLiter, destillat);
    }

    /**
     * Henter en beskrivelse på en whisky.
     * Pre: whisky er ikke null.
     *
     * @param whisky Den whisky man ønsker en beskrivelse af
     * @return en String med en beskrivelse på den valgte whisky
     */
    public static String getWhiskeyBeskrivelse(Whisky whisky) {
        StringBuilder sb = new StringBuilder();
        sb.append(whisky.hentHistorik());
        return String.valueOf(sb);
    }

    /**
     * Beregner det samlede antal liter whiskyen indeholder (summen af alle destillater og tilsat vand).
     * Pre: whisky er ikke null.
     *
     * @param whisky wiskyen der beregnes antal liter på.
     * @return det samlede antal liter whisky som double.
     */
    public static double getAntalLiterWhisky(Whisky whisky) {
        return whisky.beregnAntalLiter();
    }

    /**
     * Beregner det estimerede antal flasker (af 0.7 liter) whiskyen kan fylde.
     *
     * @param whisky whisken der bliver beregnet flasker på.
     * @return det estimerede antal flasker.
     */
    public static int getAntalFlasker(Whisky whisky) {
        return whisky.beregnAntalFlasker();
    }


    /**
     * Genererer et label med en beskrivelse til en whisky.
     * Pre: Whisky er ikke null.
     * Pre: alkoholprocent > 0.
     *
     * @param whisky         den whisky man ønsker et label på
     * @param alkoholprocent whiskyens alkoholprocent
     * @return en string med et label til en whisky
     */
    public static String genereLabel(Whisky whisky, String alkoholprocent) {
        return "Handcrafted from organic barley harvested from our fields " + whisky.getMarker() + " and "
                + ". Double distilled slowly in direct fired copper pot stills. Matured in carefully selected " + whisky.getFadtyper()
                + " casks for 3 years. Bottled in " + LocalDate.now().getYear() + "."
                + "\n\nBottle number x of " + whisky.beregnAntalFlasker() + " bottles"
                + "\n\n70 cl.\n" + alkoholprocent + "% Vol.";
    }

    /**
     * Sætter mængden af tilsat vand i liter på en given whisky.
     *
     * @param antalLiter den nye litermængde på whiskyen.
     * @param whisky     den whisky der skal have den nye litermængde.
     */
    public static void påfyldVand(int antalLiter, Whisky whisky) {
        whisky.setLiterVand(whisky.getLiterVand() + antalLiter);
    }

    /**
     * Færdiggører en whisky med label og alkoholprocent.
     * Pre: Whisky er ikke null.
     * Pre: alkoholprocent er større end 0 og mindre en 100.
     *
     * @param whisky         den whisky man ønsker færdiggjort
     * @param label          den label der skal på whiskyen
     * @param alkoholprocent whiskyens alkoholprocent
     */
    public static void færdiggørWhisky(Whisky whisky, String label, double alkoholprocent) {
        whisky.setLabel(label);
        whisky.setAlkoholprocent(alkoholprocent);
    }


}
