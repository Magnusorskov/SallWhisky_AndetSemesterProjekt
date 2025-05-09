package application.model;

import java.io.Serializable;

/**
 * Abstrakt superklasse for alle lagervarer i systemet.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 * Klassen definerer fælles egenskaber for lagervarer, såsom placering i et lager (reol og hylde)
 * og en reference til det lager, de er placeret i.
 */
public abstract class Lagervare implements Serializable {
    private int reolNummer;
    private int hyldeNummer;
    private Lager lager;

    /**
     * Initialiserer en lagervare med dens placering (reol og hylde) i et givent lager.
     * Pre: lager er ikke null.
     * Pre: reolNummer og hyldeNummer er gyldige placeringer inden for lageret.
     *
     * @param reolNummer  lagervarens reolnummer.
     * @param hyldeNummer lagervarens hyldenummer.
     * @param lager       det lager hvor lagervaren er placeret.
     */
    public Lagervare(int reolNummer, int hyldeNummer, Lager lager) {
        this.reolNummer = reolNummer;
        this.hyldeNummer = hyldeNummer;
        this.lager = lager;
    }

    /**
     * Default konstruktør for Lagervare.
     */
    public Lagervare() {
    }


    /**
     * Henter placering på lagervaren.
     *
     * @return en String der beskriver placering med lager, hylde og reolnummer.
     */
    public String placering() {
        if (lager == null) {
            return "Ikke placeret på lager";
        } else {
            return "Lager: " + lager + "\nHylde: " + hyldeNummer + "\nReol: " + reolNummer;
        }
    }

    /**
     * Henter lagervarens reolnummer.
     *
     * @return lagervarens reolnummer.
     */
    public int getReol() {
        return reolNummer;
    }

    /**
     * Henter lagervarens hyldenummer.
     *
     * @return lagervarens hyldenummer.
     */
    public int getHylde() {
        return hyldeNummer;
    }

    /**
     * Henter det lager hvor lagervaren er placeret.
     *
     * @return det lager lagervaren tilhører.
     */
    public Lager getLager() {
        return lager;
    }

    /**
     * Sætter lagervaren til at være placeret i et givent lager på en specifik placering.
     * Hvis lagervaren allerede er i et andet lager, fjernes den fra det gamle lager.
     * Pre: reolNummer og hyldeNummer er gyldige placeringer inden for det nye lager.
     *
     * @param lager det nye lager hvor lagervaren skal placeres.
     */
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
