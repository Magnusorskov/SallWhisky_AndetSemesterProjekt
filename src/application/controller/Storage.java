package application.controller;

import application.model.Batch;
import application.model.Fad;

import java.util.List;

public interface Storage {
    public List<Batch> getBatches();
    public List<Fad> getFade();

    public void addBatch(Batch batch);
    public void addFad(Fad fad);

}
