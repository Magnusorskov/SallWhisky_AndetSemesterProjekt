package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterStørrelse implements Søgning {
    private double størrelse;

    public SøgEfterStørrelse(double størrelse) {
        this.størrelse = størrelse;
    }

    @Override
    public List<Fad> fadsøgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>(fade);

        for (Fad fad : fade) {
            if (fad.getStørrelse() >= størrelse) {
                resultat.add(fad);
            }
        }
        return resultat;
    }
}
