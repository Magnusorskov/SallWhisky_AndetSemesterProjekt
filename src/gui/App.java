package gui;

import application.controller.Controller;
import application.controller.Storage;
import application.model.*;
import javafx.application.Application;
import storage.ListStorage;

import java.io.*;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        Storage storage = loadStorage();
        if (storage == null) {
            storage = new ListStorage();
            System.out.println("Empty ListStorage created");
        }
        Controller.setStorage(storage);
        if (Controller.getWhiskyer().isEmpty()) {
            initStorage();
            System.out.println("Storage initialized");
        }

        Application.launch(gui.StartWindow.class);
        saveStorage(storage);
    }

    public static Storage loadStorage() {
        String fileName = "storage.ser";
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)
        ) {
            Object obj = objIn.readObject();
            Storage storage = (ListStorage) obj;
            System.out.println("Storage loaded from file " + fileName);
            return storage;
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error deserializing storage");
            System.out.println(ex);
            return null;
        }
    }

    public static void saveStorage(Storage storage) {
        String fileName = "storage.ser";
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream objOut = new ObjectOutputStream(fileOut)
        ) {
            objOut.writeObject(storage);
            System.out.println("Storage saved in file " + fileName);
        } catch (IOException ex) {
            System.out.println("Error serializing storage");
            System.out.println(ex);
            throw new RuntimeException();
        }
    }

    public static void initStorage(){
        Lager lager = new Lager("Hovedlager",20,20,"Testvej 4");
        Lager lager1 = new Lager("ProduktionsLager",10,10,"Testvej 76");

        Fad fad = new Fad("Spanien", Fadtype.EXSHERRY,1000);
        Fad fad1 = new Fad("Spanien", Fadtype.EXSHERRY,1000);
        Fad fad2 = new Fad("Spanien", Fadtype.EXSHERRY,1000);
        Fad fad3 = new Fad("Portugal", Fadtype.EXBOURBON,800);
        Fad fad4 = new Fad("Portugal", Fadtype.EXBOURBON,800);
        Fad fad5 = new Fad("Portugal", Fadtype.EXBOURBON,800);
        Fad fad6 = new Fad("USA", Fadtype.NEW,700);
        Fad fad7 = new Fad("USA", Fadtype.NEW,700);
        Fad fad8 = new Fad("USA", Fadtype.NEW,700);

        Batch batch = new Batch(Bygsort.EVERGREEN,Mark.MOSEVANG,"CLN","Tørv","Nr12", LocalDate.of(2020,01,01),"TestBatch1");
        Batch batch1 = new Batch(Bygsort.EVERGREEN,Mark.MOSEVANG,"CLN","Tørv","Nr13", LocalDate.of(2020,01,10),"TestBatch2");
        Batch batch2 = new Batch(Bygsort.EVERGREEN,Mark.MOSEVANG,"CLN","Tørv","Nr14", LocalDate.of(2020,01,20),"TestBatch3");
        Batch batch3 = new Batch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr43", LocalDate.of(2020,02,25),"ProduktionsBatch1");
        Batch batch4 = new Batch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr43", LocalDate.of(2020,02,30),"ProduktionsBatch2");
        Batch batch5 = new Batch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr43", LocalDate.of(2020,02,20),"ProduktionsBatch3");
        Batch batch6 = new Batch(Bygsort.STAIRWAY,Mark.STADSGAARD,"CLN","Tørv","Nr101", LocalDate.of(2020,05,14),"StærktBatch1");
        Batch batch7 = new Batch(Bygsort.STAIRWAY,Mark.STADSGAARD,"CLN","Tørv","Nr111", LocalDate.of(2020,05,14),"StærktBatch2");
        Batch batch8 = new Batch(Bygsort.STAIRWAY,Mark.STADSGAARD,"CLN","Tørv","Nr111", LocalDate.of(2020,05,20),"StærktBatch3");

        Controller.færdiggørBatch(batch,LocalDate.of(2020,01,10),70,1000,"");
        Controller.færdiggørBatch(batch1,LocalDate.of(2020,01,19),60,1200,"");
        Controller.færdiggørBatch(batch2,LocalDate.of(2020,01,29),58,800,"");



    }
}
