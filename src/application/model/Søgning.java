package application.model;

import java.util.List;

public interface Søgning<E> {

    List<E> søgning(List<E> elementer);

}
