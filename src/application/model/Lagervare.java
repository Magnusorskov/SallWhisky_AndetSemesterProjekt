package application.model;

import java.io.Serializable;

public abstract class Lagervare implements Serializable {
    private int reolNummer;
    private int hyldeNummer;
    private Lager lager;

    public Lagervare(int reolNummer, int hyldeNummer, Lager lager) {
        this.reolNummer = reolNummer;
        this.hyldeNummer = hyldeNummer;
        this.lager = lager;
    }

    public Lagervare() {
    }

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
