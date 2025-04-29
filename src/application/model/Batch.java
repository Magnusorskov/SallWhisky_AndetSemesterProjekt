package application.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Batch implements Serializable {
    private static int antalBatches = 1;
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
    private int nummer;

    public Batch(Bygsort bygsort, Mark mark, String initialer, String rygemateriale, String maltBatch, LocalDate startDato, String kommentar) {
        this.bygsort = bygsort;
        this.mark = mark;
        this.initialer = initialer;
        this.rygemateriale = rygemateriale;
        this.maltBatch = maltBatch;
        this.startDato = startDato;
        this.kommentar = kommentar;
        this.nummer = antalBatches;
        antalBatches++;
    }

    public void setSlutDato(LocalDate slutDato) {
        this.slutDato = slutDato;
    }

    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    public void setVæskemængde(double væskemængde) {
        this.væskemængde = væskemængde;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
