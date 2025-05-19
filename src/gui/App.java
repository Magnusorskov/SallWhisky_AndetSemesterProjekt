package gui;

import application.controller.Controller;
import application.controller.Storage;
import application.model.*;
import application.model.Enums.Bygsort;
import application.model.Enums.Fadtype;
import application.model.Enums.Land;
import application.model.Enums.Mark;
import javafx.application.Application;
import storage.ListStorage;

import java.io.*;
import java.time.LocalDate;

/**
 * Hovedklassen for applikationen.
 * Initialiserer storage, indlæser eksisterende data fra fil (hvis tilgængeligt),
 * starter JavaFX applikationen og gemmer storage ved afslutning.
 * Indeholder også en metode til at initialisere storage med testdata, hvis den er tom.
 */
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

    /**
     * Forsøger at indlæse storage fra en fil ved navn "storage.ser".
     * Note: Returnerer null hvis storage.ser ikke findes eller hvis der opstår en IOException.
     *
     * @return det indlæste Storage objekt.
     */
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

    /**
     * Gemmer det aktuelle Storage objekt til en fil ved navn "storage.ser".
     * Hvis serialiseringen mislykkes, kastes en RuntimeException.
     *
     * @param storage det Storage objekt der skal gemmes.
     */
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

    /**
     * Initialiserer storage med en række testdata, herunder lagre, fade, batches, destillater og en whisky.
     * Denne metode kaldes kun, hvis storage er tom ved opstart.
     */
    public static void initStorage(){

        Lager lager = Controller.createLager("Hovedlager",20,20,"Testvej 4");
        Lager lager1 = Controller.createLager("ProduktionsLager",10,10,"Testvej 76");


        Fad fad = Controller.createFad(Land.SPANIEN, Fadtype.EXSHERRY,1000);
        Fad fad1 = Controller.createFad(Land.SPANIEN, Fadtype.EXSHERRY,1000);
        Fad fad2 = Controller.createFad(Land.SPANIEN, Fadtype.EXSHERRY,1000);
        Fad fad3 = Controller.createFad(Land.PORTUGAL, Fadtype.EXBOURBON,800);
        Fad fad4 = Controller.createFad(Land.PORTUGAL, Fadtype.EXBOURBON,800);
        Fad fad5 = Controller.createFad(Land.PORTUGAL, Fadtype.EXBOURBON,800);
        Fad fad6 = Controller.createFad(Land.USA, Fadtype.NEW,700);
        Fad fad7 = Controller.createFad(Land.USA, Fadtype.NEW,700);
        Fad fad8 = Controller.createFad(Land.USA, Fadtype.NEW,700);

       Batch batch1 = Controller.createBatch(Bygsort.EVERGREEN, Mark.MOSEVANG,"CLN","Tørv","Nr12", LocalDate.of(2020,01,01),"TestBatch1");
        Batch batch2 = Controller.createBatch(Bygsort.EVERGREEN,Mark.MOSEVANG,"CLN","Tørv","Nr13", LocalDate.of(2020,01,10),"TestBatch2");
        Batch batch3 = Controller.createBatch(Bygsort.EVERGREEN,Mark.MOSEVANG,"CLN","Tørv","Nr14", LocalDate.of(2020,01,20),"TestBatch3");
        Batch batch4 = Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr43", LocalDate.of(2020,02,25),"ProduktionsBatch1");
        Batch batch5 = Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr43", LocalDate.of(2020,02,28),"ProduktionsBatch2");
        Batch batch6 = Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr43", LocalDate.of(2020,02,20),"ProduktionsBatch3");

        Batch batch7 =  Controller.createBatch(Bygsort.STAIRWAY,Mark.STADSGAARD,"CLN","Tørv","Nr101", LocalDate.of(2020,05,14),"StærktBatch1");
        Batch batch8 = Controller.createBatch(Bygsort.STAIRWAY,Mark.STADSGAARD,"CLN","Tørv","Nr111", LocalDate.of(2020,05,14),"StærktBatch2");
        Batch batch9 = Controller.createBatch(Bygsort.STAIRWAY,Mark.STADSGAARD,"CLN","Tørv","Nr111", LocalDate.of(2020,05,20),"StærktBatch3");

        Controller.færdiggørBatch(batch1,LocalDate.of(2020,01,10),70,1000,null);
        Controller.færdiggørBatch(batch2,LocalDate.of(2020,01,19),60,1200,null);
        Controller.færdiggørBatch(batch3,LocalDate.of(2020,01,29),58,800,null);

        Destillat destillat = Controller.createDestillat("WhiskyDestillatPrøve",fad);
        Destillat destillat1 = Controller.createDestillat("WhiskyDestillatProduktion",fad4);
        Destillat destillat2 = Controller.createDestillat("Strong",fad8);

        destillat.createBatchMængde(100,batch1);
        destillat.createBatchMængde(200,batch2);
        destillat.createBatchMængde(50,batch3);

        destillat1.createBatchMængde(300,batch1);
        destillat1.createBatchMængde(430,batch2);

        destillat2.createBatchMængde(380,batch2);
        destillat2.createBatchMængde(519,batch3);

        Controller.færdiggørDestillat(78,LocalDate.of(2020,01,01),destillat);
        Controller.færdiggørDestillat(56,LocalDate.of(2021,02,02),destillat1);
        Controller.færdiggørDestillat(61,LocalDate.of(2021,03,03),destillat2);

        Whisky whisky = Controller.createWhisky("Whisky1");
        Whisky whisky1 = Controller.createWhisky("Whisky2");
        Whisky whisky2 = Controller.createWhisky("Whisky3");
        Whisky whisky3 = Controller.createWhisky("Whisky4");

        Controller.påfyldVand(10,whisky);
        String label = Controller.genereLabel(whisky,"67");
        Controller.færdiggørWhisky(whisky,label,67);


    }
}
