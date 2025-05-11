package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Land;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterOprindelsesLand implements Søgning {
    private Land land;

    public SøgEfterOprindelsesLand(Land land) {
        this.land = land;
    }


    @Override
    public List<Fad> fadsøgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>(fade);

        for (Fad fad : fade) {
            if (fad.getOprindelsesLand() == land) {
                resultat.add(fad);
            }
        }
        return resultat;
    }
}
