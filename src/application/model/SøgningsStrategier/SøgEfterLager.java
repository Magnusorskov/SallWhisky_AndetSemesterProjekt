package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Lager;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class SøgEfterLager implements Søgning<Fad> {
    String lager;

    public SøgEfterLager(String lager) {
        this.lager = lager;
    }

    @Override
    public List<Fad> søgning(List<Fad> fade) {
        List<Fad> resultat = new ArrayList<>();

        for (Fad fad : fade) {
            Lager fadLager = fad.getLager();
            if (lager.equals("På lager") && fadLager != null) {
                resultat.add(fad);
            } else if (lager.equals("Ikke på lager") && fadLager == null) {
                resultat.add(fad);
            } else if (fadLager != null && lager.equals(fadLager.toString())) {
                resultat.add(fad);
            }
        }
        return resultat;
    }

}
