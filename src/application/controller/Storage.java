package application.controller;

import application.model.*;

import java.util.List;
import java.util.Set;

/**
 * Interface der definerer de metoder, som en klasse der håndterer lagerstyring skal implementere.
 * Dette inkluderer metoder til at hente, tilføje og fjerne forskellige typer af lagrede enheder.
 */
public interface Storage {
    /**
     * Henter en liste over de batches der er igangværende.
     *
     * @return en liste af Batch objekter som er igangværende.
     */
    public List<Batch> getIgangværendeBatches();

    /**
     * Henter en liste over alle fade i systemet.
     *
     * @return en liste af Fad objekter.
     */
    public List<Fad> getFade();

    /**
     * Henter en liste over de batches der er færdige.
     *
     * @return en liste af Batch objekter som er færdige.
     */
    public List<Batch> getFærdigeBatches();

    /**
     * Henter en liste over alle lagre i systemet.
     *
     * @return en liste af Lager objekter.
     */
    public List<Lager> getLagre();

    /**
     * Henter en liste over alle destillater i systemet.
     *
     * @return en liste af Destillat objekter.
     */
    public List<Destillat> getDestillater();

    /**
     * Henter en mængde af alle whiskyer i systemet.
     *
     * @return et Set af unikke Whisky objekter.
     */
    public Set<Whisky> getWhiskyer();

    /**
     * Tilføjer en ny batch til systemet.
     *
     * @param batch det Batch objekt der skal tilføjes.
     */
    public void addBatch(Batch batch);

    /**
     * Tilføjer et nyt fad til systemet.
     *
     * @param fad det Fad objekt der skal tilføjes.
     */
    public void addFad(Fad fad);

    /**
     * Fjerner en igangværende batch.
     *
     * @param batch det Batch objekt der skal fjernes fra de igangværende.
     */
    public void removeIgangværendeBatch(Batch batch);

    /**
     * Tilføjer en færdig batch.
     *
     * @param batch det Batch objekt der skal tilføjes.
     */
    public void addFærdigBatch(Batch batch);

    /**
     * Tilføjer et nyt lager til systemet.
     *
     * @param lager det Lager objekt der skal tilføjes.
     */
    public void addLager(Lager lager);

    /**
     * Tilføjer et nyt destillat til systemet.
     *
     * @param destillat det Destillat objekt der skal tilføjes.
     */
    public void addDestillat(Destillat destillat);

    /**
     * Tilføjer en ny whisky til systemet.
     *
     * @param whisky det Whisky objekt der skal tilføjes.
     */
    public void addWhisky(Whisky whisky);

    /**
     * Genererer et nyt unikt ID for en whisky.
     *
     * @return et nyt unikt ID til en whisky.
     */
    public int whiskyID();

    /**
     * Genererer et nyt unikt ID for en batch.
     *
     * @return et nyt unikt ID til en batch.
     */
    public int batchID();

    /**
     * Genererer et nyt unikt ID for et fad.
     *
     * @return et nyt unikt ID til et fad.
     */
    public int fadID();

    /**
     * Genererer et nyt unikt ID for et destillat.
     *
     * @return et nyt unikt ID til et destillat.
     */
    public int destillatID();
}