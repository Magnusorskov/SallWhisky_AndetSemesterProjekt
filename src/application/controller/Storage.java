package application.controller;

import application.model.Batch;
import application.model.Fad;

import java.util.List;

public interface Storage {
    public List<Batch> getIgangværendeBatches();
    public List<Fad> getFade();
    public List<Batch> getFærdigeBatches();
    public void addBatch(Batch batch);
    public void addFad(Fad fad);
    public void removeIgangværendeBatch(Batch batch);
    public void addFærdigBatch(Batch batch);

}
