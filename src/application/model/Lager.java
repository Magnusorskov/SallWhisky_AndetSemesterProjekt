package application.model;

public class Lager {

    private String navn;
    private int antalHylder;
    private int antalReoler;
    private String adresse;
    private Lagervare[][] pladser;


    /**
     * Initialiserer et lagers navn, adresse og antal reoler og hylder.
     * Pre: navn og adresse er ikke null.
     * Pre: antal reoler og hylder > 0.
     *
     * @param navn   lagerets navn.
     * @param adresse lagerets adresse.
     * @param antalReoler lagerets antal reoler.
     * @param antalHylder lagerets antal hylder.
     */

    public Lager(String navn, int antalReoler, int antalHylder, String adresse) {
        this.navn = navn;
        this.antalHylder = antalHylder;
        this.antalReoler = antalReoler;
        this.adresse = adresse;
        pladser = new Lagervare[antalReoler + 1][antalHylder + 1];
    }

    public String getNavn() {
        return navn;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getStørrelsePåLager(){
        return (antalHylder) * (antalReoler);
    }

    public void addLagerVare(Lagervare lagervare, int reol, int hylde){
        if (pladser[reol][hylde] == null){
            pladser[reol][hylde] = lagervare;
        }
    }

    public void removeLagerVare(Lagervare lagervare){
        if (pladser[lagervare.getReol()][lagervare.getHylde()] == lagervare){
            pladser[lagervare.getReol()][lagervare.getHylde()] = null;
        }
    }
}
