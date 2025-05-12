package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterStørrelse implements Søgning<Fad> {
    private double størrelse;

    public SøgEfterStørrelse(double størrelse) {
        this.størrelse = størrelse;
    }

    @Override
    public List<Fad> søgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>();

        for (Fad fad : fade) {
            if (fad.getStørrelse() >= størrelse) {
                resultat.add(fad);
            }
        }
        return resultat;
    }
}
