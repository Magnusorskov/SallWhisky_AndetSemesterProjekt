package application.model.Væske;

import application.model.Historik;

import java.io.Serializable;
//TODO Javadoc
public abstract class ProduktionsVæske implements Historik, Serializable {
    protected int uniktNummer;
    protected double antalLiter;
    protected double alkoholprocent;
//TODO javadoc
    public ProduktionsVæske() {
    }

    /**
     * Henter væskens unikke ID.
     *
     * @return væskens ID.
     */
    public int getUniktNummer() {
        return uniktNummer;
    }

    /**
     * Sætter væskens unikke ID.
     *
     * @param uniktNummer det nye ID for væsken.
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
     * Tømmer et batch for væske.
     */
    public void tømVæske(){
        setAntalLiter(0);
    }

    /**
     * Tapper en batch en bestemt mængde liter.
     * Pre: antalLiter er større end nul.
     * Pre: antalLiter <= væskemængde på batch.
     *
     * @param antalLiter det antal liter man tapper fra batchen.
     */
    public void tapVæske(double antalLiter) {
        this.antalLiter -= antalLiter;
    }



}
