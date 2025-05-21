package application.controller;

import application.model.*;
import application.model.Enums.Bygsort;
import application.model.Enums.Fadtype;
import application.model.Enums.Land;
import application.model.Enums.Mark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.ListStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllerTest {

    private Batch batch;
    private Batch batch1;
    private Batch batch2;
    private Batch batch3;
    private Batch batch4;
    private Fad fad;
    private Fad fad1;
    private Fad fad2;
    private Fad fad3;
    private Fad fad4;
    private Storage storage;
    private Lager lager;
    private Destillat destillat;
    private Destillat destillat1;
    private Destillat destillat2;
    private Destillat destillat3;
    private Destillat destillat4;
    private String navn;
    private double antalLiter;

    @BeforeEach
    void setUp(){
        storage = new ListStorage();
        Controller.setStorage(storage);

        batch = Controller.createBatch(Bygsort.IRINA, Mark.STADSGAARD,"CLN","Tørv","Nr34",LocalDate.of(2025,01,01),"Test");
        batch.setAntalLiter(50);
        batch1 = Controller.createBatch(Bygsort.IRINA,Mark.STADSGAARD,"CLN","Tørv","Nr34",LocalDate.of(2025,01,01),"Test");
        batch1.setAntalLiter(1000);

        batch2 = Controller.createBatch(Bygsort.EVERGREEN,Mark.MOSEVANG,"MTD",null,"Nr35",LocalDate.of(2025,7,01),null);
        batch2.setAntalLiter(1000);
        batch3 = Controller.createBatch(Bygsort.STAIRWAY,Mark.STADSGAARD,"HB","ipsum","Nr1",LocalDate.of(2025,01,30),"det var en god omgang");
        batch3.setAntalLiter(1000);
        batch4 = Controller.createBatch(Bygsort.IRINA,Mark.MOSEVANG,"ETP","Lorem","Nr999",LocalDate.of(2025,2,14),"av for den");
        batch4.setAntalLiter(1000);

        fad = Controller.createFad(Land.PORTUGAL, Fadtype.EXBOURBON,50,"Christoffer");
        fad1 = Controller.createFad(Land.PORTUGAL,Fadtype.EXBOURBON,1000, "Christoffer");
        fad2 = Controller.createFad(Land.USA,Fadtype.NEW,1000, "Christoffer");
        fad3 = Controller.createFad(Land.SPANIEN,Fadtype.EXSHERRY,1000, "Christoffer");
        fad4 = Controller.createFad(Land.PORTUGAL,Fadtype.EXOLOROSO,1000, "Christoffer");

        lager = Controller.createLager("Det lille test lager",2,2,"Testvej 14");

        destillat = Controller.createDestillat("Test", fad);

        Controller.påfyldDestillatPåFad(500,batch1,"Test",fad1);

        Controller.påfyldDestillatPåFad(500,batch1,"Test",fad1);
        navn = "Test";

        Controller.omhældDestillat(fad1.getDestillat(),500,fad2,"Hejsa");

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
        assertEquals(50,batch.getAntalLiter(),"Væskemængde er forkert");
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
    void test1_påfyldDestillatPåFad(){
        antalLiter = 1;
        Controller.påfyldDestillatPåFad(antalLiter,batch,navn, fad);
        assertNotNull(fad.getDestillat().getBatchMængder().getFirst());
    }

    @Test
    void test2_påfyldDestillatPåFad(){
        antalLiter = 25;
        Controller.påfyldDestillatPåFad(antalLiter,batch,navn, fad);
        assertNotNull(fad.getDestillat().getBatchMængder().getFirst());
    }

    @Test
    void test3_påfyldDestillatPåFad(){
        antalLiter = 49;
        Controller.påfyldDestillatPåFad(antalLiter,batch,navn, fad);
        assertNotNull(fad.getDestillat().getBatchMængder().getFirst());
    }

    @Test
    void test4_påfyldDestillatPåFad(){
        antalLiter = 50;
        Controller.påfyldDestillatPåFad(antalLiter,batch,navn, fad);
        assertNotNull(fad.getDestillat().getBatchMængder().getFirst());
    }

    @Test
    void test5_påfyldDestillatPåFad(){
        antalLiter = 10;
        fad.setDestillat(null);
        Controller.påfyldDestillatPåFad(antalLiter,batch,navn, fad);
        assertNotNull(fad.getDestillat());
        assertNotNull(fad.getDestillat().getBatchMængder().getFirst());
    }

    @Test
    void test6_påfyldDestillatPåFad(){
        antalLiter = 51;
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.påfyldDestillatPåFad(antalLiter,batch,navn, fad);
        });
    }

    @Test
    void test7_påfyldDestillatPåFad(){
        antalLiter = 10;
        fad = Controller.createFad(Land.PORTUGAL,Fadtype.EXBOURBON,9, "Christoffer");
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.påfyldDestillatPåFad(antalLiter,batch,navn, fad);
        });
    }

    @Test
    void test01_indsætVarePåLager() {
        Controller.indsætVarePåLager(lager,1,1, fad);
        Controller.indsætVarePåLager(lager,2,2, fad);
        assertNull(lager.getLagerVare(1,1));
    }


}