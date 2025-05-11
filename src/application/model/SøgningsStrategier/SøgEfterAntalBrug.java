package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterAntalBrug implements Søgning {
    private int antalBrug;

    public SøgEfterAntalBrug(int antalBrug) {
        this.antalBrug = antalBrug;
    }

    @Override
    public List<Fad> fadsøgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>(fade);
        for (Fad fad : fade) {
            if (fad.getAntalBrug() == antalBrug) {
                resultat.add(fad);
            }
        }
        return resultat;
    }
}
