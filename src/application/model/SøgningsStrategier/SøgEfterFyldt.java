package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterFyldt implements Søgning<Fad> {
    private boolean fyldt;

    public SøgEfterFyldt(boolean fyldt) {
        this.fyldt = fyldt;
    }

    @Override
    public List<Fad> søgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>();

        if (fyldt) {
            for (Fad fad : fade) {
                if (fad.getDestillat() != null) {
                    resultat.add(fad);
                }
            }
        } else {
            for (Fad fad : fade) {
                if (fad.getDestillat() == null) {
                    resultat.add(fad);
                }
            }
        }
        return resultat;
    }
}
