package storage;

import application.controller.Storage;
import application.model.Batch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage, Serializable {

    private final List<Batch> batches = new ArrayList<>();

    @Override
    public List<Batch> getBatches() {
        return new ArrayList<>(batches);
    }

    @Override
    public void addBatch(Batch batch) {
        if (!batches.contains(batch)) {
            batches.add(batch);
        }
    }
}
