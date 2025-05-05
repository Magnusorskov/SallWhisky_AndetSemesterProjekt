package application.controller;

import application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.ListStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

    private Batch batch;
    private Fad fad;
    private Storage storage;
    private Lager lager;
    private Destillat destillat;

    @BeforeEach
    void setUp(){
        storage = new ListStorage();
        Controller.setStorage(storage);
        batch = Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr34",LocalDate.of(2025,01,01),"Test");
        fad = Controller.createFad("Portugal",Fadtype.EXBOURBON,60);
        lager = Controller.createLager("Det lille test lager",2,2,"Testvej 14");
        destillat = Controller.createDestillat("Testsprut",fad);

    }

    @Test
    void test04_createBatch() {
        Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr34",LocalDate.of(2025,01,01),"Test");
        assertNotNull(batch,"Batch er ikke oprettet");
        assertTrue(Controller.getIgangværendeBatches().contains(batch), "Batchen er ikke oprettet i storage");
    }

    @Test
    void test05_createFad() {
        assertNotNull(fad,"Fad ikke oprettet");
        assertTrue(Controller.getFade().contains(fad),"Fadet er ikke oprettet i storage");
    }

    @Test
    void test06_færdiggørBatch() {
        Controller.færdiggørBatch(batch,LocalDate.of(2025,02,02),60,100,"Test");
        assertNotNull(batch, "Batch ikke færdiggjort");
        assertEquals(LocalDate.of(2025,02,02),batch.getSlutDato(), "Slutdato er forkert");
        assertEquals(60,batch.getAlkoholprocent(),"Alkoholprocent er forkert");
        assertEquals(100,batch.getVæskemængde(),"Væskemængde er forkert");
        assertEquals("Test",batch.getKommentar(),"Kommentar er forkert");
        assertTrue(Controller.getFærdigeBatches().contains(batch));
    }

    @Test
    void test07_createLager(){
        assertNotNull(lager,"Lager er ikke oprettet");
        assertTrue(Controller.getLagre().contains(lager),"Lageret er ikke oprettet i storage");
    }

    @Test
    void test14_createDestillat(){
        assertNotNull(destillat,"Destillat er ikke oprettet");
        assertTrue(Controller.getDestillater().contains(destillat),"Destillat er ikke oprettet i storage");
    }

    @Test
    void test15_PåfyldFad_opretterDestillatOgTilføjerMængde(){
        boolean resultat = Controller.påfyldFad(30,batch,"TestDestillat",fad);

        assertTrue(resultat,"");
    }


}