package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterAlderPåDestillat implements Søgning<Fad> {
    private int måneder;

    public SøgEfterAlderPåDestillat(int måneder) {
        this.måneder = måneder;
    }

    @Override
    public List<Fad> søgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>();
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
