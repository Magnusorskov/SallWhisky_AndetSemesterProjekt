package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Fadtype;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterFadType implements Søgning {
    private Fadtype fadtype;

    public SøgEfterFadType(Fadtype fadtype) {
        this.fadtype = fadtype;
    }


    @Override
    public List<Fad> fadsøgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>(fade);
        for (Fad fad : fade) {
            if (fad.getFadType() == fadtype) {
                resultat.add(fad);
            }
        }
        return resultat;
    }
}
