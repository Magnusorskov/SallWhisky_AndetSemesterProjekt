package application.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Repræsenterer et destillat, som er væsken lagret på et fad.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 */
public class Destillat implements Serializable, Historik {
    private final List<BatchMængde> batchMængder = new ArrayList<>();
    private final List<OmhældningsMængde> omhældningsMængder = new ArrayList<>();
    private String navn;
    private int id;
    private double alkoholprocent;
    private double antalLiter;
    private LocalDate påfyldningsDato;
    private Fad fad;
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

    //TODO java doc
    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    /**
     * Sætter destillatets unikke ID.
     *
     * @param id det nye ID for destillatet.
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * Henter det aktuelle antal liter der er i destillatet.
     *
     * @return det aktuelle antal liter destillat.
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    public void setLagringstidIMåneder(int måneder) {
        lagringstidIMåneder = måneder;
    }
    /**
     * Sætter det aktuelle antal liter der er i destillatet.
     *
     * @param antalLiter det nye antal liter destillat.
     */
    public void setAntalLiter(double antalLiter) {
        this.antalLiter = antalLiter;
    }

    /**
     * Sætter destillatets alkoholprocent.
     *
     * @param alkoholprocent den nye alkoholprocent for destillatet.
     */
    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
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
        BatchMængde batchMængde = new BatchMængde(antalLiter, batch);
        this.antalLiter += antalLiter;

        int index = findesBatchIBatchMængdeListe(batch);
        if (index != -1) {
            batchMængder.get(index).addLiterTilEksisterendeBM(antalLiter);
        } else {
            batchMængder.add(batchMængde);
        }
        return batchMængde;
    }

    //TODO java doc
    private int findesBatchIBatchMængdeListe(Batch batch) {
        int i = 0;
        while (i < batchMængder.size()) {
            if (batchMængder.get(i).getBatch() == batch) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    //sammenhæng til DestillatMængde

    /**
     * Initialiserer en OmhældningsMængdes antal liter og destillat og tilføjer den til destillatet.
     * Den sørger også for at tilføje antalLiter til destillatets totale antal liter.
     * Pre: destillat er ikke null.
     * Pre: antalLiter > 0.
     *
     * @param antalLiter det antal liter man omhælder fra destillatet.
     * @param destillat  det destillat der skal omhældes en mængde fra.
     * @return den færdige omhældnings mængde.
     */
    public OmhældningsMængde createOmhældningsMængde(double antalLiter, Destillat destillat) {
        OmhældningsMængde omhældningsMængde = new OmhældningsMængde(antalLiter, destillat);
        this.antalLiter += antalLiter;

        int index = findesDestillatIOmhældningsMængdeListe(destillat);
        if (index != -1) {
            omhældningsMængder.get(index).addLiterTilEksisterendeOM(antalLiter);
        } else {
            omhældningsMængder.add(omhældningsMængde);
        }

        return omhældningsMængde;
    }

    //TODO java doc
    private int findesDestillatIOmhældningsMængdeListe(Destillat destillat) {
        int i = 0;
        while (i < omhældningsMængder.size()) {
            if (omhældningsMængder.get(i).getDestillat() == destillat) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    //metoder

    /**
     * Laver en String med id og liter om alle batches destillatet indeholder.
     *
     * @return String med id og liter om alle batches destillatet indeholder.
     */
    public String getDestillatsBatches() {
        StringBuilder sb = new StringBuilder();
        for (BatchMængde m : batchMængder) {
            sb.append("\n   Batch: " + m.getBatch().getId() + "(Liter: " + m.getAntalLiter() + ")");
        }
        String s = sb + "";
        return s;
    }

    /**
     * Tapper destillatet en bestemt mængde liter.
     * Pre: tapLiter > 0.
     * Pre: tapLiter <= destillatets tapLiter.
     *
     * @param tapLiter antallet af liter der skal tappes.
     */
    public void tapDestillat(double tapLiter) {
        this.antalLiter -= tapLiter;
    }

    /**
     * Laver en historik over destillatet der indeholder destillatets id, navn, påfyldningsdato, alkoholprocent, fadets historik
     * og alle batches historik som destillatet indeholder.
     *
     * @return Stringbuilder med historikken.
     */

    /**
     * Tømmer et destillat
     */
    public void tømDestillat(){
        setAntalLiter(0);
    }

    public StringBuilder hentHistorikHjælpeMetode() {
        StringBuilder sb = new StringBuilder();
        sb.append("Destillat: " + id + " " + navn);
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

        sb.append("\n\nBatches:\n");
        Map<Batch, Double> batches = new HashMap<>();
        for (BatchMængde bm : getBatchMængder()) {
            batches.merge(bm.getBatch(), bm.getAntalLiter(), Double::sum);
        }
        for (Map.Entry<Batch, Double> k : batches.entrySet()) {
            sb.append(k.getKey().hentHistorik());
            sb.append("\nAntal Liter af batch: " + k.getValue() + "\n\n");
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
            marker.add(bm.getBatch().getMark());
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
        if (omhældningsMængder.isEmpty()) {
            return true;
        }
        else return false;
    }

    //TODO java doc
    public List<OmhældningsMængde> getOmhældningsMængder() {
        return new ArrayList<>(omhældningsMængder);
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
