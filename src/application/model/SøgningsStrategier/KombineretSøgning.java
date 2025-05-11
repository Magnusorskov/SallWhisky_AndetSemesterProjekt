package application.model.SøgningsStrategier;

import application.model.Fad;
import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class KombineretSøgning implements Søgning {
    private final List<Søgning> kombineredeSøgninger;

    public KombineretSøgning(List<Søgning> kombineredeSøgninger) {
        this.kombineredeSøgninger = kombineredeSøgninger;
    }

    @Override
    public List<Fad> fadsøgning(List<Fad> fade) {
        List<Fad> resultater = new ArrayList<>(fade);

        for (Søgning søgning : kombineredeSøgninger) {
            resultater = søgning.fadsøgning(resultater);
        }
        return resultater;
    }
}
