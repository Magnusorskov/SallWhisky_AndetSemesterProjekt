package application.model;

import application.model.Enums.Mark;
import application.model.Væske.ProduktionsVæske;
import application.model.VæskeMængde.VæskeMængde;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Repræsenterer et destillat, som er væsken lagret på et fad.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public class Destillat extends ProduktionsVæske {
    private final List<BatchMængde> batchMængder = new ArrayList<>();
    private final List<OmhældningsMængde> omhældningsMængder = new ArrayList<>();
    private final Fad fad;
    private String navn;
    private LocalDate påfyldningsDato;
    private int lagringstidIMåneder;

    /**
     * Initialiserer et destillats navn, fad.
     * Pre: navn og fad er ikke er null.
     *
     * @param navn destillatets navn.
     * @param fad  det fad destillatet ligger på.
     */
    public Destillat(String navn, Fad fad) {
        this.navn = navn;
        this.fad = fad;
        this.alkoholprocent = -1;
        this.lagringstidIMåneder = -1;
        fad.setDestillat(this);
    }

    /**
     * Henter destillatets navn.
     *
     * @return destillatets navn.
     */
    public String getNavn() {
        return navn;
    }

    /**
     * Sætter destillatets navn.
     *
     * @param navn det nye navn for destillatet.
     */
    public void setNavn(String navn) {
        this.navn = navn;
    }


    /**
     * Henter destillatets påfyldningsdato.
     *
     * @return datoen hvor destillatet blev påfyldt fadet.
     */
    public LocalDate getPåfyldningsDato() {
        return påfyldningsDato;
    }

    /**
     * Sætter destillatets påfyldningsdato.
     *
     * @param påfyldningsDato den nye påfyldningsdato for destillatet.
     */
    public void setPåfyldningsDato(LocalDate påfyldningsDato) {
        this.påfyldningsDato = påfyldningsDato;
    }


    public void setLagringstidIMåneder(int måneder) {
        lagringstidIMåneder = måneder;
    }


    //sammenhæng til fad

    /**
     * Henter det fad destillatet ligger på.
     *
     * @return det fad destillatet er placeret i.
     */
    public Fad getFad() {
        return fad;
    }


    //sammenhæng til batchMængde

    /**
     * Henter en liste over de batchmængder, der udgør destillatet.
     *
     * @return en ny liste indeholdende BatchMængde objekter.
     */
    public List<BatchMængde> getBatchMængder() {
        return new ArrayList<>(batchMængder);
    }

    /**
     * Initialiserer en BatchMængdes antal liter og batch og tilføjer den til destillatet.
     * Pre: antalLiter er større end 0.
     * Pre: batch er ikke null.
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     * @param batch      den batch man tager væske fra.
     * @return den færdige BatchMængde.
     */
    public BatchMængde createBatchMængde(double antalLiter, Batch batch) {
        this.antalLiter += antalLiter;
        BatchMængde batchMængde;
        int index = findesVæskeIVæskeMængdeListe(batch);
        if (index != -1) {
            batchMængde = batchMængder.get(index);
            batchMængde.addLiterTilEksisterendeVM(antalLiter);
        } else {
            batchMængde = new BatchMængde(antalLiter, batch);
            batchMængder.add(batchMængde);
        }
        return batchMængde;
    }

    //sammenhæng til OmhældningsMængde

    /**
     * Initialiserer en OmhældningsMængdes antal liter og oldDestillat og tilføjer den til destillatet.
     * Den sørger også for at tilføje antalLiter til destillatets totale antal liter.
     * Pre: oldDestillat er ikke null.
     * Pre: antalLiter > 0.
     *
     * @param antalLiter   det antal liter man omhælder fra destillatet.
     * @param oldDestillat det oldDestillat der skal omhældes en mængde fra.
     * @return den færdige omhældnings mængde.
     */
    public OmhældningsMængde createOmhældningsMængde(double antalLiter, Destillat oldDestillat) {
        OmhældningsMængde omhældningsMængde;
        this.antalLiter += antalLiter;

        int index = findesVæskeIVæskeMængdeListe(oldDestillat);
        if (index != -1) {
            omhældningsMængde = omhældningsMængder.get(index);
            omhældningsMængder.get(index).addLiterTilEksisterendeVM(antalLiter);
        } else {
            omhældningsMængde = new OmhældningsMængde(antalLiter, oldDestillat);
            omhældningsMængder.add(omhældningsMængde);
        }

        return omhældningsMængde;
    }

    //TODO java doc
    private int findesVæskeIVæskeMængdeListe(ProduktionsVæske produktionsVæske) {
        List<? extends VæskeMængde> væskeMængder = null;

        if (produktionsVæske instanceof Destillat) {
            væskeMængder = this.omhældningsMængder;
        } else if (produktionsVæske instanceof Batch) {
            væskeMængder = this.batchMængder;
        }
        if (væskeMængder != null) {
            int i = 0;
            while (i < væskeMængder.size()) {
                if (væskeMængder.get(i).getVæske() == produktionsVæske) {
                    return i;
                } else {
                    i++;
                }
            }
        }
        return -1;
    }

    //TODO java doc
    public List<OmhældningsMængde> getOmhældningsMængder() {
        return new ArrayList<>(omhældningsMængder);
    }

    //metoder


    /**
     * Laver en String med id og liter om alle batches destillatet indeholder.
     *
     * @return String med id og liter om alle batches destillatet indeholder.
     */
    public String getDestillatetsBatchesTilBeskrivelse() {
        StringBuilder sb = new StringBuilder();
        for (BatchMængde m : batchMængder) {
            sb.append("\n   Batch: " + m.getBatch().getUniktNummer() + "(Liter: " + m.getAntalLiter() + ")");
        }
        String s = sb + "";
        return s;
    }

    /**
     * Laver en historik over destillatet der indeholder destillatets id, navn, påfyldningsdato, alkoholprocent, fadets historik
     * og alle batches historik som destillatet indeholder.
     *
     * @return Stringbuilder med historikken.
     */
    public StringBuilder hentHistorikHjælpeMetode() {
        StringBuilder sb = new StringBuilder();
        sb.append("Destillat: " + uniktNummer + " " + navn);
        sb.append("\nPåfyldnings dato: " + påfyldningsDato);
        if (alkoholprocent > 0) {
            sb.append("\nAlkoholprocent: " + alkoholprocent);
        }
        sb.append("\n----------------------------------");

        sb.append("\n\nFad:\n" + fad.getFadBeskrivelse());
        if (lagringstidIMåneder != -1) {
            sb.append("\nLagringstid i måneder : " + lagringstidIMåneder);
        }
        sb.append("\n----------------------------------");

        if (!batchMængder.isEmpty()) {
            sb.append("\n\nBatches:\n");
            for (BatchMængde bm : batchMængder) {
                sb.append(bm.getBatch().hentHistorik() + "\n\n");
            }
            sb.append("----------------------------------\n\n");
        }

        return sb;
    }

    /**
     * Henter de unikke marker fra de batches, der udgør destillatet.
     *
     * @return et Set af unikke Mark objekter.
     */
    public Set<Mark> getMarker() {
        Set<Mark> marker = new HashSet<>();
        for (BatchMængde bm : batchMængder) {
            Batch batch = bm.getBatch();
            marker.add(batch.getMark());
        }
        return marker;
    }

    /**
     * Beregner alderen fra påfyldningsdato til nu.
     *
     * @return alderen fra påfyldningsdato til nu i måneder.
     */
    public int beregnAlderIMåneder() {
        long antalDage = ChronoUnit.DAYS.between(påfyldningsDato, LocalDate.now());
        return (int) (antalDage / 30.436768);
    }


    //TODO java doc
    public StringBuilder hentHistorik() {
        StringBuilder sb = new StringBuilder(hentHistorikHjælpeMetode());
        if (omhældningsMængder.isEmpty()) {
            return sb;
        } else {
            sb.append("\n\n" + navn + " indeholder: ");
            for (OmhældningsMængde omhældningsMængde : omhældningsMængder) {
                sb.append("\n" + omhældningsMængde.getDestillat().hentHistorik());
            }
            return sb;
        }
    }

    //TODO java doc
    public boolean isSingleCask() {
        return omhældningsMængder.isEmpty();
    }


    /**
     * Laver en String repræsentation af Destillat objektet (returnerer navnet).
     *
     * @return destillatets navn.
     */
    @Override
    public String toString() {
        String destillat = navn;
        if (antalLiter == 0) {
            destillat = destillat + "(Tom)";
        }
        return destillat;
    }
}
