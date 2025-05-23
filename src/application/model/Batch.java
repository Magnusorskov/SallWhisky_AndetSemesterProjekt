package application.model;

import application.model.Enums.Bygsort;
import application.model.Enums.Mark;
import application.model.Væske.ProduktionsVæske;

import java.time.LocalDate;

/**
 * Repræsenterer en enkelt batch af råmateriale i produktionsprocessen.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public class Batch extends ProduktionsVæske {
    private Bygsort bygsort;
    private Mark mark;
    private String initialer;
    private String maltBatch;
    private String rygemateriale;
    private LocalDate startDato;
    private LocalDate slutDato;
    private String kommentar;


    /**
     * Initialiserer en batch's bygsort, mark, initialer, rygemateriale, maltbatch, start dato og kommentar.
     * Pre: bygsort, mark, initialer, maltbatch og startdato er ikke null.
     * Note: kommentar og rygemateriale kan være null.
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
    }

    /**
     * Henter den mark hvor kornet til batchen kommer fra.
     *
     * @return marken for batchens korn.
     */
    public Mark getMark() {
        return mark;
    }

    /**
     * Henter initialerne på den person, der er ansvarlig for batchen.
     *
     * @return initialerne på batch-ansvarlig.
     */
    public String getInitialer() {
        return initialer;
    }

    /**
     * Henter den bygsort der anvendes i batchen.
     *
     * @return bygsorten for batchen.
     */
    public Bygsort getBygsort() {
        return bygsort;
    }

    /**
     * Henter den anvendte maltbatch.
     *
     * @return maltbatchen for batchen.
     */
    public String getMaltBatch() {
        return maltBatch;
    }

    /**
     * Henter det rygemateriale der er anvendt i batchen.
     * Note: returnerer null hvis der ikke er anvendt rygemateriale.
     *
     * @return det anvendte rygemateriale.
     */
    public String getRygemateriale() {
        return rygemateriale;
    }

    /**
     * Henter datoen hvor batchen blev startet.
     *
     * @return batch'ens startdato.
     */
    public LocalDate getStartDato() {
        return startDato;
    }

    /**
     * Henter datoen hvor batchen blev afsluttet.
     * Note: returnerer null, hvis batchen stadig er aktiv.
     *
     * @return batch'ens slutdato.
     */
    public LocalDate getSlutDato() {
        return slutDato;
    }

    /**
     * Sætter datoen hvor batchen blev afsluttet.
     *
     * @param slutDato den nye slutdato for batchen.
     */
    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }



    /**
     * Henter eventuelle kommentarer til batchen.
     * Note: returnerer null, hvis der ingen kommentar er givet.
     *
     * @return kommentarerne til batchen.
     */
    public String getKommentar() {
        return kommentar;
    }

    /**
     * Sætter eventuel kommentar til batchen.
     *
     * @param kommentar den nye kommentar til batchen.
     */
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }


    //metoder

    /**
     * Genererer en historik over batchen, inklusive ID, bygsort, mark, maltbatch, rygemateriale (hvis relevant),
     * alkoholprocent (hvis relevant), start- og slutdato (hvis relevant), initialer og kommentar (hvis relevant).
     *
     * @return en StringBuilder indeholdende batchens historik.
     */
    public StringBuilder hentHistorik() {
        StringBuilder sb = new StringBuilder();
        sb.append("Batch nr: " + uniktNummer);
        sb.append("\nBygsort: " + bygsort);
        sb.append("\nMark: " + mark);
        sb.append("\nMaltbatch: " + maltBatch);
        if (rygemateriale != null) {
            sb.append("\nRygemateriale: " + rygemateriale);
        }
        if (alkoholprocent > 0) {
            sb.append("\nAlkoholprocent: " + alkoholprocent);
        }
        if (slutDato != null) {
            sb.append("\nDatoer: " + startDato + " - " + slutDato);
        } else {
            sb.append("\nStartdato: " + startDato);
        }
        sb.append("\nInitialer: " + initialer);
        if (kommentar != null) {
            sb.append("\nKommentar: " + "\n\t" + kommentar);
        }
        return sb;
    }

    /**
     * Laver en String repræsentation af Batch objektet (viser batchnummer og startdato).
     *
     * @return en String der beskriver batchen kortfattet.
     */
    @Override
    public String toString() {
        String batch = "BatchNr:" + uniktNummer + " StartDato: " + startDato;
        if (antalLiter == 0 && slutDato != null) {
            batch = batch + " (Tom)";
        }
        return batch;
    }


}
