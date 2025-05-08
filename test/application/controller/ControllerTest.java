package application.controller;

import application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.ListStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

    private Batch batch;
    private Fad fad;
    private Storage storage;
    private Lager lager;
    private Destillat destillat;
    private String navn;
    private double antalLiter;

    @BeforeEach
    void setUp(){
        storage = new ListStorage();
        Controller.setStorage(storage);
        batch = Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr34",LocalDate.of(2025,01,01),"Test");
        batch.setVæskemængde(50);
        fad = Controller.createFad("Portugal",Fadtype.EXBOURBON,50);
        lager = Controller.createLager("Det lille test lager",2,2,"Testvej 14");
        destillat = Controller.createDestillat("Test",fad);
        navn = "Test";
    }

    @Test
    void test01_createBatch() {
        Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr34",LocalDate.of(2025,01,01),"Test");
        assertNotNull(batch,"Batch er ikke oprettet");
        assertTrue(Controller.getIgangværendeBatches().contains(batch), "Batchen er ikke oprettet i storage");
    }

    @Test
    void test01_createFad() {
        assertNotNull(fad,"Fad ikke oprettet");
        assertTrue(Controller.getFade().contains(fad),"Fadet er ikke oprettet i storage");
    }

    @Test
    void test01_færdiggørBatch() {
        Controller.færdiggørBatch(batch,LocalDate.of(2025,02,02),60,50,"Test");
        assertNotNull(batch, "Batch ikke færdiggjort");
        assertEquals(LocalDate.of(2025,02,02),batch.getSlutDato(), "Slutdato er forkert");
        assertEquals(60,batch.getAlkoholprocent(),"Alkoholprocent er forkert");
        assertEquals(50,batch.getVæskemængde(),"Væskemængde er forkert");
        assertEquals("Test",batch.getKommentar(),"Kommentar er forkert");
        assertTrue(Controller.getFærdigeBatches().contains(batch));
    }

    @Test
    void test01_createLager(){
        assertNotNull(lager,"Lager er ikke oprettet");
        assertTrue(Controller.getLagre().contains(lager),"Lageret er ikke oprettet i storage");
    }

    @Test
    void test1_createDestillat(){
        assertNotNull(destillat,"Destillat er ikke oprettet");
        assertTrue(Controller.getDestillater().contains(destillat),"Destillat er ikke oprettet i storage");
    }

    @Test
    void test1_påfyldFad(){
        antalLiter = 1;
        Controller.påfyldFad(antalLiter,batch,navn,fad);
        assertNotNull(fad.getDestillat().getMængder().getFirst());
    }

    @Test
    void test2_påfyldFad(){
        antalLiter = 25;
        Controller.påfyldFad(antalLiter,batch,navn,fad);
        assertNotNull(fad.getDestillat().getMængder().getFirst());
    }

    @Test
    void test3_påfyldFad(){
        antalLiter = 49;
        Controller.påfyldFad(antalLiter,batch,navn,fad);
        assertNotNull(fad.getDestillat().getMængder().getFirst());
    }

    @Test
    void test4_påfyldFad(){
        antalLiter = 50;
        Controller.påfyldFad(antalLiter,batch,navn,fad);
        assertNotNull(fad.getDestillat().getMængder().getFirst());
    }

    @Test
    void test5_påfyldFad(){
        antalLiter = 10;
        fad.setDestillat(null);
        Controller.påfyldFad(antalLiter,batch,navn,fad);
        assertNotNull(fad.getDestillat());
        assertNotNull(fad.getDestillat().getMængder().getFirst());
    }

    @Test
    void test6_påfyldFad(){
        antalLiter = 51;
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.påfyldFad(antalLiter,batch,navn,fad);
        });
    }

    @Test
    void test7_påfyldFad(){
        antalLiter = 10;
        fad = Controller.createFad("Portugal",Fadtype.EXBOURBON,9);
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.påfyldFad(antalLiter,batch,navn,fad);
        });
    }



}