package application.model;

public interface Lagervare {
    public String beskrivelse();

    public int getReol();

    public int getHylde();

    public Lager getLager();

    public void setLager(Lager lager);

}
