package application.controller;

import application.model.*;

import java.util.List;
import java.util.Set;

public interface Storage {
    public List<Batch> getIgangværendeBatches();

    public List<Fad> getFade();

    public List<Batch> getFærdigeBatches();

    public List<Lager> getLagre();

    public List<Destillat> getDestillater();

    public Set<Whisky> getWhiskyer();

    public void addBatch(Batch batch);

    public void addFad(Fad fad);

    public void removeIgangværendeBatch(Batch batch);

    public void addFærdigBatch(Batch batch);

    public void addLager(Lager lager);

    public void addDestillat(Destillat destillat);

    public void addWhisky(Whisky whisky);
}
