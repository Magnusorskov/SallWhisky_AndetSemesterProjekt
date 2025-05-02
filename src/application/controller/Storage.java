package application.controller;

import application.model.Batch;
import application.model.Destillat;
import application.model.Fad;
import application.model.Lager;

import java.util.List;

public interface Storage {
    public List<Batch> getIgangværendeBatches();
    public List<Fad> getFade();
    public List<Batch> getFærdigeBatches();
    public List<Lager> getLagre();
    public  List<Destillat> getDestillater();
    public void addBatch(Batch batch);
    public void addFad(Fad fad);
    public void removeIgangværendeBatch(Batch batch);
    public void addFærdigBatch(Batch batch);
    public void addLager(Lager lager);
    public void addDestillat(Destillat destillat);
}
