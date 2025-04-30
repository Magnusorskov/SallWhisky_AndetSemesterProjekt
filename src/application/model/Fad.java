package application.model;

import java.io.Serializable;

public class Fad implements Serializable {
    private static int idTæller = 1;
    private String oprindelsesLand;
    private int antalBrug;
    private Fadtype fadType;
    private double størrelse;
    private String placering;
    private int id;

    /**
     * Initialiserer et fads oprindelsesland, fadtype og størrelse.
     * Pre: oprindelsesland og fadtype er ikke null.
     * Pre: størrelse > 0.
     *
     * @param oprindelsesLand   fadets oprindelsesland.
     * @param fadType           fadets type.
     * @param størrelse         fadets størrelse.
     */
    public Fad(String oprindelsesLand, Fadtype fadType, double størrelse) {
        this.oprindelsesLand = oprindelsesLand;
        this.fadType = fadType;
        this.størrelse = størrelse;

        id = idTæller;
        idTæller++;

        if (fadType == Fadtype.NEW) {
            this.antalBrug = 0;
        } else {
            this.antalBrug = 1;
        }
    }

    //getter og setter

    //sammenhæng til

    //metoder


}
