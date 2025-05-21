package application.model;

import java.io.Serializable;

/**
 * Abstrakt superklasse for alle lagervarer i systemet.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 * Klassen definerer fælles egenskaber for lagervarer, som placering på lager
 * og en reference til det lager, de er placeret i.
 */
public abstract class Lagervare implements Serializable {
    private int reolNummer;
    private int hyldeNummer;
    private Lager lager;

    /**
     * Default konstruktør for Lagervare.
     */
    public Lagervare() {
    }


    /**
     * Henter lagervarens reolNummer.
     *
     * @return lagervarens reolNummer.
     */
    public int getReol() {
        return reolNummer;
    }

    /**
     * Henter lagervarens hyldeNummer.
     *
     * @return lagervarens hyldeNummer.
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
     * Sætter lagervaren til at være placeret i et givent lager på en specifik placering
     * og håndterer eventuelt eksisterende lager forbindelse.
     * Pre: reolNummer og hyldeNummer er gyldige placeringer inden for det nye lager.
     * Note: lager kan være null, hvis lagervare skal fjernes fra lagersystemet.
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
            if (lager == null) {
                setHyldeNummer(-1);
                setReolNummer(-1);
            }
        }

    }

    /**
     * Sætter reolNummer.
     *
     * @param reolNummer på en lagervare.
     */
    public void setReolNummer(int reolNummer) {
        this.reolNummer = reolNummer;
    }

    /**
     * Sætter hyldeNummer.
     *
     * @param hyldeNummer på en lagervare.
     */
    public void setHyldeNummer(int hyldeNummer) {
        this.hyldeNummer = hyldeNummer;
    }

    /**
     * Henter placering på lagervaren i tekst.
     *
     * @return en String der beskriver lagervarens placering med lager, hylde og reolnummer
     * eller beskriver at varen ikke er på lager.
     */
    public String getPlacering() {
        if (lager == null) {
            return "Ikke placeret på lager";
        } else {
            return lager + "\nHylde: " + hyldeNummer + "\nReol: " + reolNummer;
        }
    }

}
