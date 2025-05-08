package application.model;

import java.io.Serializable;

public abstract class Lagervare implements Serializable {
    private int reolNummer;
    private int hyldeNummer;
    private Lager lager;

    // TODO dokumentation
    public Lagervare(int reolNummer, int hyldeNummer, Lager lager) {
        this.reolNummer = reolNummer;
        this.hyldeNummer = hyldeNummer;
        this.lager = lager;
    }

    // TODO dokumentation
    public Lagervare() {
    }

    // TODO dokumentation
    public abstract String beskrivelse();

    public int getReol() {
        return reolNummer;
    }

    public int getHylde() {
        return hyldeNummer;
    }

    public Lager getLager() {
        return lager;
    }

    // TODO dokumentation
    public void setLager(Lager lager) {
        if (this.lager != lager) {
            Lager oldLager = this.lager;
            if (oldLager != null) {
                oldLager.removeLagerVare(this);
            }
            this.lager = lager;
            if (lager != null) {
                lager.addLagerVare(this, reolNummer, hyldeNummer);
            }
        }
    }
}
