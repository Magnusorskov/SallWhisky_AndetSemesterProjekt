package storage;

import application.controller.Storage;
import application.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementering af Storage interfacet ved brug af ArrayList og TreeSet til at gemme data i hukommelsen.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public class ListStorage implements Storage, Serializable {

    private final List<Batch> igangværendeBatches = new ArrayList<>();
    private final List<Batch> færdigeBatches = new ArrayList<>();
    private final List<Fad> fade = new ArrayList<>();
    private final List<Lager> lagre = new ArrayList<>();
    private final List<Destillat> destillater = new ArrayList<>();
    private final Set<Whisky> whiskyer = new TreeSet<>();
    private final int[] ids = new int[4];

    /**
     * Henter en kopi af listen over de batches der er igangværende.
     *
     * @return en ny ArrayList indeholdende de Batch objekter som er igangværende.
     */
    @Override
    public List<Batch> getIgangværendeBatches() {
        return new ArrayList<>(igangværendeBatches);
    }

    /**
     * Henter en kopi af listen over de batches der er færdige.
     *
     * @return en ny ArrayList indeholdende de Batch objekter som er færdige.
     */
    public List<Batch> getFærdigeBatches() {
        return new ArrayList<>(færdigeBatches);
    }

    /**
     * Tilføjer en ny batch til listen over igangværende batches, hvis den ikke allerede findes.
     *
     * @param batch det Batch objekt der skal tilføjes.
     */
    @Override
    public void addBatch(Batch batch) {
        if (!igangværendeBatches.contains(batch)) {
            igangværendeBatches.add(batch);
        }
    }

    /**
     * Fjerner en igangværende batch fra listen, hvis den findes.
     *
     * @param batch det Batch objekt der skal fjernes fra de igangværende.
     */
    @Override
    public void removeIgangværendeBatch(Batch batch) {
        if (igangværendeBatches.contains(batch)) {
            igangværendeBatches.remove(batch);
        }
    }

    /**
     * Tilføjer en færdig batch til listen over færdige batches, hvis den ikke allerede findes.
     *
     * @param batch det Batch objekt der skal tilføjes.
     */
    @Override
    public void addFærdigBatch(Batch batch) {
        if (!færdigeBatches.contains(batch)) {
            færdigeBatches.add(batch);
        }
    }

    // -------------------------------------------------------------------------

    /**
     * Henter en kopi af listen over alle fade i systemet.
     *
     * @return en ny ArrayList indeholdende de Fad objekter.
     */
    @Override
    public List<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    /**
     * Tilføjer et nyt fad til listen over fade, hvis det ikke allerede findes.
     *
     * @param fad det Fad objekt der skal tilføjes.
     */
    @Override
    public void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
        }
    }

    //--------------------------------------------------------

    /**
     * Henter en kopi af listen over alle lagre i systemet.
     *
     * @return en ny ArrayList indeholdende de Lager objekter.
     */
    @Override
    public List<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }

    /**
     * Tilføjer et nyt lager til listen over lagre, hvis det ikke allerede findes.
     *
     * @param lager det Lager objekt der skal tilføjes.
     */
    @Override
    public void addLager(Lager lager) {
        if (!lagre.contains(lager)) {
            lagre.add(lager);
        }
    }

    //------------------------------------------------------

    /**
     * Henter en kopi af listen over alle destillater i systemet.
     *
     * @return en ny ArrayList indeholdende de Destillat objekter.
     */
    @Override
    public List<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }

    /**
     * Tilføjer et nyt destillat til listen over destillater, hvis det ikke allerede findes.
     *
     * @param destillat det Destillat objekt der skal tilføjes.
     */
    @Override
    public void addDestillat(Destillat destillat) {
        if (!destillater.contains(destillat)) {
            destillater.add(destillat);
        }
    }

    /**
     * Genererer et nyt unikt nummer for en whisky ved at inkrementere en intern tæller.
     *
     * @return et nyt unikt nummer til en whisky.
     */
    @Override
    public int whiskyUniktNummer() {
        ids[0]++;
        return ids[0];
    }

    /**
     * Genererer et nyt unikt nummer for en batch ved at inkrementere en intern tæller.
     *
     * @return et nyt unikt nummer til en batch.
     */
    @Override
    public int batchUniktNummer() {
        ids[1]++;
        return ids[1];
    }

    /**
     * Genererer et nyt unikt nummer for et fad ved at inkrementere en intern tæller.
     *
     * @return et nyt unikt nummer til et fad.
     */
    @Override
    public int fadUniktNummer() {
        ids[2]++;
        return ids[2];
    }

    /**
     * Genererer et nyt unikt nummer for et destillat ved at inkrementere en intern tæller.
     *
     * @return et nyt unikt nummer til et destillat.
     */
    @Override
    public int destillatUniktNummer() {
        ids[3]++;
        return ids[3];
    }

    //------------------------------------------------------

    /**
     * Henter en kopi af mængden af alle whiskyer i systemet.
     *
     * @return et nyt TreeSet indeholdende unikke Whisky objekter.
     */
    @Override
    public Set<Whisky> getWhiskyer() {
        return new TreeSet<>(whiskyer);
    }

    /**
     * Tilføjer en ny whisky til mængden af whiskyer.
     *
     * @param whisky det Whisky objekt der skal tilføjes.
     */
    @Override
    public void addWhisky(Whisky whisky) {
        whiskyer.add(whisky);
    }
}