package application.model.SøgningsStrategier;

import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class KombineretSøgning<E> implements Søgning {
    private final List<Søgning> kombineredeSøgninger;

    public KombineretSøgning(List<Søgning> kombineredeSøgninger) {
        this.kombineredeSøgninger = kombineredeSøgninger;
    }


    @Override
    public List<E> søgning(List elementer) {
        List<E> resultater = new ArrayList<E>(elementer);

        for (Søgning søgning : kombineredeSøgninger) {
            resultater = søgning.søgning(resultater);
        }
        return resultater;
    }
}
