package application.model.Væske;

import application.model.Historik;

import java.io.Serializable;

/**
 * Repræsenterer en produktionsvæske som indeholder et unikt nummer, et antal liter og en alkoholprocent.
 * Implementerer Serializable for at kunne gemmes og indlæses.
 * Implementerer historik for at kunne hente historik på fadet.
 */
public abstract class ProduktionsVæske implements Historik, Serializable {
    protected int uniktNummer;
    protected double antalLiter;
    protected double alkoholprocent;

    /**
     * Default konstruktør for ProduktionsVæske.
     */
    public ProduktionsVæske() {
    }

    /**
     * Henter væskens unikke nummer.
     *
     * @return væskens nummer.
     */
    public int getUniktNummer() {
        return uniktNummer;
    }

    /**
     * Sætter væskens unikke nummer.
     *
     * @param uniktNummer det nye nummer for væsken.
     */
    public void setUniktNummer(int uniktNummer) {
        this.uniktNummer = uniktNummer;
    }

    /**
     * Henter den aktuelle væskemængde i væsken i liter.
     *
     * @return den aktuelle væskemængde i liter.
     */
    public double getAntalLiter() {
        return antalLiter;
    }

    /**
     * Sætter den aktuelle væskemængde i væsken i liter.
     *
     * @param antalLiter den nye væskemængde i liter.
     */
    public void setAntalLiter(double antalLiter) {
        this.antalLiter = antalLiter;
    }

    /**
     * Henter alkoholprocenten for væsken.
     *
     * @return væskens alkoholprocent.
     */
    public double getAlkoholprocent() {
        return alkoholprocent;
    }

    /**
     * Sætter alkoholprocenten for væsken.
     *
     * @param alkoholprocent den nye alkoholprocent for væsken.
     */
    public void setAlkoholprocent(double alkoholprocent) {
        this.alkoholprocent = alkoholprocent;
    }

    /**
     * Tømmer en ProduktionsVæske for væske.
     */
    public void tømVæske() {
        setAntalLiter(0);
    }

    /**
     * Tapper en ProduktionsVæske en bestemt mængde liter.
     * Pre: antalLiter er større end nul.
     * Pre: antalLiter <= væskemængde på ProduktionsVæske.
     *
     * @param antalLiter det antal liter man tapper fra ProduktionsVæsken.
     */
    public void tapVæske(double antalLiter) {
        this.antalLiter -= antalLiter;
    }


}
