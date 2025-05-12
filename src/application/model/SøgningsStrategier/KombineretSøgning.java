package application.model.SøgningsStrategier;

import application.model.Søgning;

import java.util.ArrayList;
import java.util.List;

public class KombineretSøgning<E> implements Søgning<E> {
    private final List<Søgning<E>> kombineredeSøgninger;

    public KombineretSøgning(List<Søgning<E>> kombineredeSøgninger) {
        this.kombineredeSøgninger = kombineredeSøgninger;
    }


    @Override
    public List<E> søgning(List<E> elementer) {
        List<E> resultater = new ArrayList<>(elementer);

        for (Søgning<E> søgning : kombineredeSøgninger) {
            resultater = søgning.søgning(resultater);
        }
        return resultater;
    }
}
