package application.controller;

import application.model.Batch;

import java.util.List;

public interface Storage {
    public List<Batch> getBatches();

    public void addBatch(Batch batch);

}
