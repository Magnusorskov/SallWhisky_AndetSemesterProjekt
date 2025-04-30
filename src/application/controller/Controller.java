package application.controller;

public abstract class Controller {
    private Storage storage;

    public void setStorage(Storage newStorage){
        storage = newStorage;
    }

}
