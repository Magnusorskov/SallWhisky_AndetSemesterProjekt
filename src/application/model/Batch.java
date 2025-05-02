package application.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Batch implements Serializable {
    private static int idTæller = 1;
    private Bygsort bygsort;
    private Mark mark;
    private String initialer;
    private String maltBatch;
    private String rygemateriale;
    private LocalDate startDato;
    private LocalDate slutDato;
    private double alkoholprocent;
    private double væskemængde;
    private String kommentar;
    private int id;

    /**
     * Initialiserer en batch's bygsort, mark, initialer, rygemateriale, maltbatch, start dato og kommentar.
     * Pre: bygsort, mark, initialer, maltbatch og startdato er ikke null.
     *
     * @param bygsort       bygsorten der er i batchen.
     * @param mark          hvilken mark kornet kommer fra.
     * @param initialer     intialerne på personen der håndterer batchen.
     * @param rygemateriale rygematerialet der er brugt i batchen.
     * @param maltBatch     maltbatchen der er brugt i batchen.
     * @param startDato     datoen batchen er startet.
     * @param kommentar     eventuel kommentar til batchen.
     */
    public Batch(Bygsort bygsort, Mark mark, String initialer, String rygemateriale, String maltBatch, LocalDate startDato, String kommentar) {
        this.bygsort = bygsort;
        this.mark = mark;
        this.initialer = initialer;
        this.rygemateriale = rygemateriale;
        this.maltBatch = maltBatch;
        this.startDato = startDato;
        this.kommentar = kommentar;
        this.id = idTæller;
        idTæller++;
    }

    /**
     * Tapper en batch en bestemt mængde liter
     * Pre: antalLiter er større end nul
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     */
    private void tapBatch(double antalLiter) {
        væskemængde -= antalLiter;
    }

    public Mark getMark() {
        return mark;
    }


    public String getInitialer() {
        return initialer;
    }

    public Bygsort getBygsort() {
        return bygsort;
    }

    public String getMaltBatch() {
        return maltBatch;
    }

    public String getRygemateriale() {
        return rygemateriale;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    public double getVæskemængde() {
        return væskemængde;
    }

    public void setVæskemængde(double væskemængde) {
        this.væskemængde = væskemængde;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "BatchNr:" + id + " StartDato: " + startDato;
    }
}
