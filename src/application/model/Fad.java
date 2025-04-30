package application.model;

import java.io.Serializable;

public class Fad implements Serializable {
    private static int antalFade = 1;
    private String oprindelsesLand;
    private int antalBrug;
    private Fadtype fadType;
    private double størrelse;
    private String placering;
    private int nummer;

    public Fad(String oprindelsesLand, Fadtype fadType, double størrelse) {
        this.oprindelsesLand = oprindelsesLand;
        this.fadType = fadType;
        this.størrelse = størrelse;

        nummer = antalFade;
        antalFade++;

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
