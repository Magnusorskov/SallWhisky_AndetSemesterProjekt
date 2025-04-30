package storage;

import application.controller.Storage;
import application.model.Batch;
import application.model.Fad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage, Serializable {

    private final List<Batch> igangværendeBatches = new ArrayList<>();
    private final List<Batch> færdigeBatches = new ArrayList<>();
    private final List<Fad> fade = new ArrayList<>();

    @Override
    public List<Batch> getIgangværendeBatches() {
        return new ArrayList<>(igangværendeBatches);
    }

    @Override
    public void addBatch(Batch batch) {
        if (!igangværendeBatches.contains(batch)) {
            igangværendeBatches.add(batch);
        }
    }

    // -------------------------------------------------------------------------

    @Override
    public List<Fad> getFade() {
        return new ArrayList<>(fade);
    }

    @Override
    public void addFad(Fad fad) {
        if (!fade.contains(fad)) {
            fade.add(fad);
        }
    }
}
