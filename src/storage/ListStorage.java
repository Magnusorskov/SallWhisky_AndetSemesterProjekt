package storage;

import application.controller.Storage;
import application.model.Batch;
import application.model.Destillat;
import application.model.Fad;
import application.model.Lager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListStorage implements Storage, Serializable {

    private final List<Batch> igangværendeBatches = new ArrayList<>();
    private final List<Batch> færdigeBatches = new ArrayList<>();
    private final List<Fad> fade = new ArrayList<>();
    private final List<Lager> lagre = new ArrayList<>();
    private final List<Destillat> destillater = new ArrayList<>();

    @Override
    public List<Batch> getIgangværendeBatches() {
        return new ArrayList<>(igangværendeBatches);
    }

    public List<Batch> getFærdigeBatches() {
        return new ArrayList<>(færdigeBatches);
    }

    @Override
    public void addBatch(Batch batch) {
        if (!igangværendeBatches.contains(batch)) {
            igangværendeBatches.add(batch);
        }
    }

    public void removeIgangværendeBatch(Batch batch) {
        if (igangværendeBatches.contains(batch)) {
            igangværendeBatches.remove(batch);
        }
    }

    public void addFærdigBatch(Batch batch) {
        if (!færdigeBatches.contains(batch)) {
            færdigeBatches.add(batch);
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

    //--------------------------------------------------------

    @Override
    public List<Lager> getLagre() {
        return new ArrayList<>(lagre);
    }
    @Override
    public void addLager(Lager lager){
        if(!lagre.contains(lager)){
            lagre.add(lager);
        }
    }

    //------------------------------------------------------

    @Override
    public List<Destillat> getDestillater(){
        return new ArrayList<>(destillater);
    }

    @Override
    public void addDestillat(Destillat destillat){
        if (!destillater.contains(destillat)){
            destillater.add(destillat);
        }
    }
}
