package application.controller;

public abstract class Controller {
    private static Storage storage;

    public static void setStorage(Storage newStorage){
        storage = newStorage;
    }

}
