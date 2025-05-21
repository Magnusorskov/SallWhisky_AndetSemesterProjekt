package application.controller;

import application.model.*;
import application.model.Enums.Bygsort;
import application.model.Enums.Fadtype;
import application.model.Enums.Land;
import application.model.Enums.Mark;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

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
     * Initialiserer en batch's bygsort, mark, initialer, rygemateriale, maltbatch, unikke nummer,
     * start dato og kommentar og tilføjer den til storage.
     * Pre: bygsort, mark, initialer, rygemateriale, maltbatch og startdato er ikke null.
     * Note: kommentar og rygemateriale kan være null.
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
        batch.setUniktNummer(storage.batchUniktNummer());
        storage.addBatch(batch);
        return batch;
    }


    /**
     * Færdiggør en batch med en slutdato, alkoholprocent, væskemængde, kommentar og flytter den til færdige batches i storage.
     * Pre: batch og slutDato er ikke null.
     * Pre: slutDato skal være lig eller efter startDato på batch.
     * Pre: væskemængde > 0.
     * Pre: alkoholprocent skal være større end 0 og mindre eller lig med 100.
     * Note: kommentar kan være null.
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
        batch.setAntalLiter(væskemængde);
        batch.setKommentar(kommentar);
        storage.removeIgangværendeBatch(batch);
        storage.addFærdigBatch(batch);
    }

    /**
     * Henter batches med tilgængelige liter tilbage.
     *
     * @return en liste med batches med tilgængelige liter
     */
    public static List<Batch> getFærdigeBatchesMedTilgængeligeLiter() {
        List<Batch> resultat = new ArrayList<>();
        for (Batch batch : getFærdigeBatches()) {
            if (batch.getAntalLiter() > 0) {
                resultat.add(batch);
            }
        }
        return resultat;
    }

    /**
     * Sætter en batches væskemængde til 0.
     *
     * @param batch batchen der bliver tømt.
     */
    public static void tømBatch(Batch batch) {
        batch.tømVæske();
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
     * Henter de færdiggjorte batches.
     *
     * @return en liste med færdige batches
     */
    public static List<Batch> getFærdigeBatches() {
        return storage.getFærdigeBatches();
    }

    /**
     * Fjerner batch fra igangværende batches.
     * Pre: batch er ikke null.
     *
     * @param batch batchen der skal fjernes fra igangværende batches.
     */
    public static void removeBatch(Batch batch) {
        storage.removeIgangværendeBatch(batch);
    }

    //---------------------------------------------------------------------------------------------------------------


    /**
     * Henter alle fade.
     *
     * @return en liste med alle fade.
     */
    public static List<Fad> getFade() {
        return storage.getFade();
    }


    /**
     * Henter fade der har ufærdige destillater eller er tomme og som er brugt under 3 gange.
     *
     * @return en liste med tilgængelige fade og fade med ufærdige destillater i sig.
     */
    public static List<Fad> getFadeUdenFærdigDestillat() {
        List<Fad> resultat = new ArrayList<>();
        for (Fad f : getFade()) {
            if (f.getAntalBrug() < 3) {
                Destillat fadDestillat = f.getDestillat();
                if (fadDestillat == null) {
                    resultat.add(f);
                } else if (fadDestillat.getPåfyldningsDato() == null) {
                    resultat.add(f);
                }
            }
        }
        Collections.sort(resultat);
        return resultat;
    }

    /**
     * Initialiserer et fads oprindelsesland, fadtype, størrelse, leverandør og tilføjer den til storage.
     * Pre: land, leverandør og fadtype er ikke null.
     * Pre: størrelse > 0.
     *
     * @param land       fadets oprindelsesland.
     * @param fadType    fadets type.
     * @param størrelse  fadets størrelse.
     * @param leverandør fadets leverandør.
     */
    public static Fad createFad(Land land, Fadtype fadType, double størrelse, String leverandør) {
        Fad fad = new Fad(land, fadType, størrelse, leverandør);
        fad.setUniktNummer(storage.fadUniktNummer());
        storage.addFad(fad);
        return fad;
    }

    /**
     * Henter fade der har omhældningsmængder, ikke er færdiggjorte og brugt højest 3 gange og
     * fade der er tomme og er brugt under 3 gange.
     *
     * @return en liste over fade der kan omhældes til.
     */
    public static List<Fad> getFadeDerKanOmhældesTil() {
        List<Fad> resultat = new ArrayList<>();

        for (Fad fad : getFade()) {
            Destillat destillat = fad.getDestillat();
            if (fad.getAntalBrug() <= 3) {
                if (destillat != null) {
                    boolean destillatHarOmhældningsMængder = !destillat.getOmhældningsMængder().isEmpty();
                    boolean destillatIkkeFærdiggjort = destillat.getAlkoholprocent() == -1;

                    if (destillatHarOmhældningsMængder && destillatIkkeFærdiggjort) {
                        resultat.add(fad);
                    }
                } else if (destillat == null && fad.getAntalBrug() < 3) {
                    resultat.add(fad);
                }
            }
        }
        return resultat;
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
     * Indsætter en vare på på en plads på lageret.
     * Pre: lagervare er ikke null
     *
     * @param reolNummer  reolnummeret på den ønskede plads.
     * @param hyldeNummer hyldenummeret på den ønskede plads.
     * @param lagervare   lagervaren man ønsker at indsætte på lageret.
     * @throws IllegalArgumentException hvis reolNummer er mindre end 1 eller større en lagerets antal reoler.
     * @throws IllegalArgumentException hvis hyldeNummer er mindre end 1 eller større en lagerets antal hylder.
     * @throws IllegalArgumentException hvis der allerede er en lagervare på pladsen.
     */

    public static void indsætVarePåLager(Lager lager, int reolNummer, int hyldeNummer, Lagervare lagervare) {
        if (reolNummer < 1 || reolNummer > lager.getAntalReoler()) {
            throw new IllegalArgumentException("Indtast gyldigt reolnr - Der er " + lager.getAntalReoler() + " reoler på lageret");
        } else if (hyldeNummer < 1 || hyldeNummer > lager.getAntalHylder()) {
            throw new IllegalArgumentException("Indtast gyldigt hyldenr - Der er " + lager.getAntalHylder() + " hylder på lageret");
        } else if (lager.getPladser()[reolNummer][hyldeNummer] != null) {
            throw new IllegalArgumentException("Pladsen er allerede optaget");
        } else {
            lager.addLagerVare(lagervare, reolNummer, hyldeNummer);
            lagervare.setReolNummer(reolNummer);
            lagervare.setHyldeNummer(hyldeNummer);
            lager.opdaterNæsteLedigePlads();
        }

    }

    /**
     * Fjerner en lagervare fra lageret
     * Pre: lagervarens lager er ikke null.
     *
     * @param lagervare den lagervare man ønsker fjerne
     */
    public static void fjernLagervareFraLager(Lagervare lagervare) {
        Lager lager = lagervare.getLager();
        lager.removeLagerVare(lagervare);
    }

    /**
     * Udvider lageret med et større antalReoler og antalHylder.
     * Pre: antalReoler >= lagerets nuværende antalReoler.
     * Pre: antalHylder >= lagerets nuværende antalHylder.
     * Pre: lager er ikke null.
     *
     * @param lager       lageret der skal udvides.
     * @param antalReoler det nye antalReoler.
     * @param antalHylder det nye antalHylder.
     */
    public static void udvidLager(Lager lager, int antalReoler, int antalHylder) {
        if (lager != null) {
            lager.udvidLager(antalReoler, antalHylder);
        }
    }

    /**
     * Beregner det samlede antal ledige pladser der er tilbage på lageret.
     * Note: returnerer 0 hvis lageret er null.
     *
     * @return en int med antal ledige pladser på lageret.
     */
    public static int beregnAntalLedigePladserPåLager(Lager lager) {
        if (lager != null) {
            return lager.antalLedigePladser();
        }
        return 0;
    }

    //----------------------------------------------------------------------------------

    /**
     * Initialiserer et destillats navn, fad, unikke nummer og tilføjer det til storage.
     * Pre: navn, fad ikke er null.
     *
     * @param navn navnet på destillatet.
     * @param fad  fadet hvorpå destillatet skal påfyldes.
     * @return det oprettede destillat.
     */
    public static Destillat createDestillat(String navn, Fad fad) {
        Destillat destillat = new Destillat(navn, fad);
        destillat.setUniktNummer(storage.destillatUniktNummer());
        storage.addDestillat(destillat);
        return destillat;
    }

    /**
     * Færdiggører et destillat med alkoholprocent og påfyldningsDato og tæller fadets brug op med én.
     * Pre: <påfyldningsdato og destillat er ikke null.
     * Pre: alkoholprocent er større end 0 og mindre end 100.
     *
     * @param alkoholprocent  alkoholprocenten på destillatet.
     * @param påfyldningsDato påfyldningsdatoen på destillatet.
     * @param destillat       der skal færdiggøres.
     */
    public static void færdiggørDestillat(double alkoholprocent, LocalDate påfyldningsDato, Destillat destillat) {
        destillat.setAlkoholprocent(alkoholprocent);
        destillat.setPåfyldningsDato(påfyldningsDato);
        destillat.setAntalLiter(destillat.getAntalLiter());

        Fad fad = destillat.getFad();
        int antalBrug = fad.getAntalBrug() + 1;
        fad.setAntalBrug(antalBrug);

        List<OmhældningsMængde> omhældningsMængder = destillat.getOmhældningsMængder();
        if (!omhældningsMængder.isEmpty()) {
            for (OmhældningsMængde omhældningsMængde : omhældningsMængder) {
                omhældningsMængde.setLagringstidIMåneder(påfyldningsDato);
            }
        }
    }

    /**
     * Opretter en BatchMængde af en batch på et fads destillat med et antalLiter og hvis der ikke findes et destillat
     * oprettes der et destillat først.
     * Pre: batch, fad og navn er ikke null.
     * Pre: antalLiter er større end 0.
     *
     * @param antalLiter mængden af liter der påfyldes fadet.
     * @param batch      batchen der bliver tilføjet liter af.
     * @param navn       navnet på destillatet der skal oprettes hvis der ikke er et destillat i forvejen.
     * @param fad        fadet mængden skal påfyldes i.
     * @throws IllegalArgumentException hvis antalLiter er større end fadets tilgængelige antal liter.
     * @throws IllegalArgumentException hvis antalLiter er større end batchens tilbageværende antal liter.
     */
    public static void påfyldDestillatPåFad(double antalLiter, Batch batch, String navn, Fad fad) {
        if (antalLiter > fad.getTilgængeligeLiter()) {
            throw new IllegalArgumentException("Der er ikke nok plads i fadet");
        } else if (antalLiter > batch.getAntalLiter()) {
            throw new IllegalArgumentException("Der er ikke nok væske i batchen");
        }
        Destillat destillat = createDestillatHvisIngenFindes(navn, fad);
        destillat.createBatchMængde(antalLiter, batch);
    }

    /**
     * Opretter et destillat på et fad, hvis der ikke findes et i forvejen med navn og fad.
     *
     * @param navn det navn det nye destillat skal have.
     * @param fad  det fad det nye destillat skal være tilknyttet.
     * @return destillatet der er på fadet i forvejen eller det nye destillat, hvis der ikke var et i forvejen.
     */
    private static Destillat createDestillatHvisIngenFindes(String navn, Fad fad) {
        Destillat destillat = fad.getDestillat();
        if (destillat == null) {
            destillat = Controller.createDestillat(navn, fad);
        }
        return destillat;
    }


    //-----------------------------------------------------------------------------------------------------

    /**
     * Henter alle destillater.
     *
     * @return en liste med destillater.
     */
    public static List<Destillat> getDestillater() {
        return storage.getDestillater();
    }

    /**
     * Finder destillater der har lagret i 3 år eller mere.
     *
     * @return en liste med færdige destillater der er over 3 år gamle.
     */
    public static List<Destillat> getFærdigmodnedeDestillater() {
        List<Destillat> resultat = new ArrayList<>();
        for (Destillat destillat : getDestillater()) {
            if (destillat.getPåfyldningsDato() != null) {
                if (destillat.getAntalLiter() > 0 && destillat.beregnAlderIMåneder() >= 36) {
                    resultat.add(destillat);
                }
            }
        }
        return resultat;
    }

    /**
     * Henter alle destillater der ikke er tomme og som er færdiggjorte.
     *
     * @return en liste med færdiggjorte og ikke tomme destillater.
     */
    public static List<Destillat> getFærdiggjorteDestillater() {
        List<Destillat> resultat = new ArrayList<>();
        for (Destillat destillat : getDestillater()) {
            if (destillat.getAntalLiter() > 0 && destillat.getPåfyldningsDato() != null) {
                resultat.add(destillat);
            }
        }
        return resultat;
    }

    /**
     * Omhælder en oldDestillat mængde fra et newFad til et andet og hvis der ikke findes et oldDestillat på fadet
     * bliver der lavet et nyt.
     *
     * @param oldDestillat det destillat der skal omhældes fra.
     * @param antalLiter   den mængde af liter der skal omhældes.
     * @param newFad       det fad der bliver omhældt til.
     * @param navn         navnet på det eventuelle nye destillat.
     * @throws IllegalArgumentException hvis antal liter er større end pladsen i fadet.
     * @throws IllegalArgumentException hvis antal liter er større end mængden af resterende væske i oldDestillat.
     */
    public static void omhældDestillat(Destillat oldDestillat, double antalLiter, Fad newFad, String navn) {
        if (antalLiter > newFad.getTilgængeligeLiter()) {
            throw new IllegalArgumentException("Der er ikke nok plads i fadet");
        } else if (antalLiter > oldDestillat.getAntalLiter()) {
            throw new IllegalArgumentException("Der er ikke nok væske i destillatet");
        }

        Destillat fadDestillat = createDestillatHvisIngenFindes(navn, newFad);
        fadDestillat.createOmhældningsMængde(antalLiter, oldDestillat);
        if (oldDestillat.getAntalLiter() == 0) {
            oldDestillat.getFad().setDestillat(null);
        }

    }

    /**
     * Sætter et destillats væskemængde til 0.
     *
     * @param destillat destillatet der bliver tømt.
     */
    public static void tømDestillat(Destillat destillat) {
        destillat.tømVæske();
    }


    //------------------------------------------------------------------------------------------------------------------

    /**
     * Initialiserer en whiskys navn, unikke nummer og tilføjer det til storage.
     *
     * @param navn navnet på whiskyen.
     * @return den oprettede whisky.
     */
    public static Whisky createWhisky(String navn) {
        Whisky whisky = new Whisky(navn);
        whisky.setUniktNummer(storage.whiskyUniktNummer());
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
    public static List<Whisky> getIgangværendeWhiskyer() {
        List<Whisky> resultat = new ArrayList<>();
        for (Whisky w : Controller.getWhiskyer()) {
            if (w.getAlkoholprocent() == 0.0) {
                resultat.add(w);
            }
        }
        return resultat;
    }

    /**
     * Tapper et destillat for et antal liter og opretter en destillat mængde på whiskyen. Hvis destillatet
     * tømmes, sættes fadets link til destillatet til null.
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
        if (destillat.getAntalLiter() == 0) {
            destillat.getFad().setDestillat(null);
        }
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
    public static String genererLabel(Whisky whisky, String alkoholprocent) {
        return "Handcrafted from organic barley harvested from our fields " + whisky.getMarker() + " and "
                + ". Double distilled slowly in direct fired copper pot stills. Matured in carefully selected " + whisky.getFadtyper()
                + " casks for 3 years. Bottled in " + LocalDate.now().getYear() + "."
                + "\n\nBottle number x of " + whisky.beregnAntalFlasker() + " bottles"
                + "\n\n70 cl.\n" + alkoholprocent + "% Vol.";
    }

    /**
     * Sætter mængden af tilsat vand i liter på en given whisky.
     * Pre: whisky er ikke null.
     *
     * @param antalLiter den nye litermængde på whiskyen.
     * @param whisky     den whisky der skal have den nye litermængde.
     */
    public static void påfyldVand(int antalLiter, Whisky whisky) {
        whisky.setLiterVand(whisky.getLiterVand() + antalLiter);
    }

    /**
     * Færdiggører en whisky med label og alkoholprocent.
     * Pre: Whisky og label er ikke null.
     * Pre: alkoholprocent er større end 0 og mindre en 100.
     *
     * @param whisky         den whisky man ønsker færdiggjort
     * @param label          den label der skal på whiskyen
     * @param alkoholprocent whiskyens alkoholprocent
     */
    public static void færdiggørWhisky(Whisky whisky, String label, double alkoholprocent) {
        whisky.setLabel(label);
        whisky.setAlkoholprocent(alkoholprocent);
        whisky.setKvalitetsStempel();
    }

    //------------------------------------------------------------------------------------------------------------------


    //------------------------------------------------------------------------------------------------------------------

    /**
     * Søger efter fade baseret på de kriterier der bliver givet med.
     * Note: alt kan være null, alle fade returneres hvis ingen parametre gives.
     *
     * @param fadtype          fadtypen man vil søge efter.
     * @param fills            antallet af fadets brug.
     * @param land             landet som fadet kommer fra.
     * @param alderPåDestillat alderen på fadets tilknyttede destillat.
     * @param literStørrelse   størrelsen på fadet.
     * @param fyldt            om fadet er fyldt eller ej.
     * @param lagret           en string der repræsenterer om fadet er på lager, om det ikke er på lager eller et specifikt lager.
     * @return en liste over alle fade der overholder kriterierne.
     */
    public static List<Fad> fadSoegning(Fadtype fadtype, Integer fills, Land land,
                                        Integer alderPåDestillat, Double literStørrelse, Boolean fyldt, String lagret) {
        List<Fad> fade = Controller.getFade();
        if (fadtype != null) {
            fade = søgEfterFadType(fade, fadtype);
        }
        if (fills != null) {
            fade = søgEfterAntalBrug(fade, fills);
        }
        if (lagret != null) {
            fade = søgEfterLager(fade, lagret);
        }
        if (land != null) {
            fade = søgEfterLand(fade, land);
        }
        if (alderPåDestillat != null) {
            fade = søgEfterAlderPåDestillat(fade, alderPåDestillat);
        }
        if (literStørrelse != null) {
            fade = søgEferStørrelse(fade, literStørrelse);
        }
        if (fyldt != null) {
            fade = søgEfterFyldtStatus(fade, fyldt);
        }
        return fade;
    }

    /**
     * En søgning efter fade baseret på dets tilknyttede destillats alder.
     *
     * @param fade    listen over fade der skal søges i.
     * @param måneder alderen på destillatet.
     * @return en liste over fade der overholder kriteriet.
     */
    private static List<Fad> søgEfterAlderPåDestillat(List<Fad> fade, int måneder) {
        List<Fad> resultat = new ArrayList<>();
        for (Fad fad : fade) {
            Destillat destillat = fad.getDestillat();
            if (destillat != null && destillat.getPåfyldningsDato() != null) {
                if (fad.getDestillat().beregnAlderIMåneder() > måneder) {
                    resultat.add(fad);
                }
            }
        }
        return resultat;
    }

    /**
     * En søgning efter fade baseret på antal gange det er blevet brugt.
     *
     * @param fade      listen over fade der skal søges i.
     * @param antalBrug mængden af gange fadet er blevet brugt der skal søges efter.
     * @return en liste over fade der overholder kriteriet.
     */
    private static List<Fad> søgEfterAntalBrug(List<Fad> fade, int antalBrug) {
        List<Fad> resultat = new ArrayList<>();
        for (Fad fad : fade) {
            if (fad.getAntalBrug() == antalBrug) {
                resultat.add(fad);
            }
        }
        return resultat;
    }

    /**
     * En søgning efter fade baseret på fadtype.
     *
     * @param fade    listen over fade der skal søges i.
     * @param fadtype den fadtype der skal søges efter.
     * @return en liste over fade der overholder kriteriet.
     */
    private static List<Fad> søgEfterFadType(List<Fad> fade, Fadtype fadtype) {
        List<Fad> resultat = new ArrayList<>();
        for (Fad fad : fade) {
            if (fad.getFadType() == fadtype) {
                resultat.add(fad);
            }
        }
        return resultat;
    }

    /**
     * En søgning efter fade baseret på om fadet er fyldt eller ej.
     *
     * @param fade  listen over fade der skal søges i.
     * @param fyldt den status fadet skal have.
     * @return en liste over fade der overholder kriteriet.
     */
    private static List<Fad> søgEfterFyldtStatus(List<Fad> fade, boolean fyldt) {
        List<Fad> resultat = new ArrayList<>();

        if (fyldt) {
            for (Fad fad : fade) {
                if (fad.getDestillat() != null) {
                    resultat.add(fad);
                }
            }
        } else {
            for (Fad fad : fade) {
                if (fad.getDestillat() == null) {
                    resultat.add(fad);
                }
            }
        }
        return resultat;
    }

    /**
     * En søgning efter fade baseret på om fadet er på lager, om det ikke er på lager
     * eller om det er på et specifikt lager.
     *
     * @param fade  listen over fade der skal søges i.
     * @param lager kan enten være "På lager", "Ikke på lager" eller et specifikt lager.
     * @return en liste over fade der overholder kriteriet.
     */
    private static List<Fad> søgEfterLager(List<Fad> fade, String lager) {
        List<Fad> resultat = new ArrayList<>();

        for (Fad fad : fade) {
            Lager fadLager = fad.getLager();
            if (lager.equals("På lager") && fadLager != null) {
                resultat.add(fad);
            } else if (lager.equals("Ikke på lager") && fadLager == null) {
                resultat.add(fad);
            } else if (fadLager != null && lager.equals(fadLager.toString())) {
                resultat.add(fad);
            }
        }
        return resultat;
    }

    /**
     * En søgning efter fade baseret på hvilket land fadet kommer fra.
     *
     * @param fade listen over fade der skal søges i.
     * @param land det land fadet kommer fra.
     * @return en liste over fade der overholder kriteriet.
     */
    private static List<Fad> søgEfterLand(List<Fad> fade, Land land) {
        List<Fad> resultat = new ArrayList<>();

        for (Fad fad : fade) {
            if (fad.getOprindelsesLand() == land) {
                resultat.add(fad);
            }
        }
        return resultat;
    }

    /**
     * En søgning efter fade baseret på størrelsen af fadet.
     *
     * @param fade      listen over fade der skal søges i.
     * @param størrelse den størrelse fadet mindst skal have.
     * @return en liste over fade der overholder kriteriet.
     */
    private static List<Fad> søgEferStørrelse(List<Fad> fade, double størrelse) {
        List<Fad> resultat = new ArrayList<>();

        for (Fad fad : fade) {
            if (fad.getStørrelse() >= størrelse) {
                resultat.add(fad);
            }
        }
        return resultat;
    }


    //------------------------------------------------------------------------------------------------------------------

    /**
     * Henter en historik på et objekt der implementer Historik interfacet.
     * Pre: historik er ikke null.
     *
     * @param historik Det objekt der implementere Historik, som man ønsker en beskrivelse af
     * @return en String med en historik på den valgte Historik objekt
     */
    public static String getHistorik(Historik historik) {
        return String.valueOf(historik.hentHistorik());
    }

    /**
     * Kombinerer historikker fra en mængde af objekter der implementerer historik og som er i en collection.
     *
     * @param collection den collection objekterne findes i.
     * @return en String med den kombinerede historik.
     */
    public static String kombinerHistorik(Collection<? extends Historik> collection) {
        StringBuilder sb = new StringBuilder();
        for (Historik h : collection) {
            sb.append(getHistorik(h));
            if (h instanceof Whisky) {
                sb.append("\n\n");
            } else {
                sb.append("\n--------------------------------\n\n");
            }
        }
        return String.valueOf(sb);
    }

    /**
     * Udtrækker en tekst til en fil.
     *
     * @param forhåndsvisning den String der skal skrives ind i filen.
     * @return en fil med historik.
     */
    public static File udtrækTilFilPåList(String forhåndsvisning) {
        String fileName = "Udtræk.txt";
        File fil = new File(fileName);

        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.append(forhåndsvisning);
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til fil");
        }
        return fil;
    }


}
