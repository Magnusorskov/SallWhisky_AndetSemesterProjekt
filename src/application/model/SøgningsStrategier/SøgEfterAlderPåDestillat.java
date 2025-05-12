package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterAlderPåDestillat implements Søgning {
    private int måneder;

    public SøgEfterAlderPåDestillat(int måneder) {
        this.måneder = måneder;
    }

    @Override
    public List<Fad> fadsøgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>(fade);
        for (Fad fad : fade) {
            if (fad.getDestillat() != null) {
                if (fad.getDestillat().beregnAlderIMåneder() > måneder) {
                    resultat.add(fad);
                }
            }
        }
        return resultat;
    }
}
