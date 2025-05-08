package storage;

import application.controller.Storage;
import application.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ListStorage implements Storage, Serializable {

    private final List<Batch> igangværendeBatches = new ArrayList<>();
    private final List<Batch> færdigeBatches = new ArrayList<>();
    private final List<Fad> fade = new ArrayList<>();
    private final List<Lager> lagre = new ArrayList<>();
    private final List<Destillat> destillater = new ArrayList<>();
    private final Set<Whisky> whiskyer = new TreeSet<>();
    private final int[] ids = new int[4];

    
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
    public void addLager(Lager lager) {
        if (!lagre.contains(lager)) {
            lagre.add(lager);
        }
    }

    //------------------------------------------------------
    
    @Override
    public List<Destillat> getDestillater() {
        return new ArrayList<>(destillater);
    }
    
    @Override
    public void addDestillat(Destillat destillat) {
        if (!destillater.contains(destillat)) {
            destillater.add(destillat);
        }
    }
    
    public int whiskyID() {
        ids[0]++;
        int id = ids[0];
        return id;
    }
    
    public int batchID() {
        ids[1]++;
        int id = ids[1];
        return id;
    }
    
    public int fadID() {
        ids[2]++;
        int id = ids[2];
        return id;
    }
    
    public int destillatID() {
        ids[3]++;
        int id = ids[3];
        return id;
    }

    //------------------------------------------------------
    
    @Override
    public Set<Whisky> getWhiskyer() {
        return new TreeSet<>(whiskyer);
    }
    
    @Override
    public void addWhisky(Whisky whisky) {
        whiskyer.add(whisky);
    }

}
