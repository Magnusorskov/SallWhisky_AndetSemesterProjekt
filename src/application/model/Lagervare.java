package application.model;

public interface Lagervare {
    public String beskrivelse();

    public String getPlacering();

    public Lager getLager();

    public void setLager(Lager lager);

}
