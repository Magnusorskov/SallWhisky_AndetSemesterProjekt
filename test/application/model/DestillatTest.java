package application.model;

import application.model.Enums.Bygsort;
import application.model.Enums.Fadtype;
import application.model.Enums.Land;
import application.model.Enums.Mark;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DestillatTest {

    private Destillat destillat;
    private Fad fad;
    private BatchMængde mængde;
    private Batch batch;

    @BeforeEach
    void setUp() {
        fad = new Fad(Land.SPANIEN, Fadtype.EXSHERRY,100);
        batch = new Batch(Bygsort.EVERGREEN, Mark.MOSEVANG,"CLN","Tørv","Nr11", LocalDate.of(2025,01,01),"Test start");
        destillat = new Destillat("Testsprut",fad);
        mængde = destillat.createBatchMængde(100,batch);


    }

    @Test
    void test10_KontruktorDestillat(){
        assertNotNull(destillat,"Destillat ikke oprettet");
        assertEquals(destillat.getNavn(),"Testsprut","Navn på destillat er forkert");
        assertEquals(destillat.getFad(),fad,"Fadet er forkert");
    }

    @Test
    void test11_CreateBatchMængde(){
        assertNotNull(mængde,"Mængde er ikke oprettet");
        assertEquals(mængde.getBatch(),batch,"Batch er forkert");
        assertEquals(mængde.getAntalLiter(),100,"Antal liter er forkert");
    }

    @Test
    void test12_beregnAntalLiterPåBatchMængder(){
        assertEquals(destillat.getAntalLiter(),100,"Antal liter er forker");
    }

    @Test
    void test13_getMængder() {
        assertEquals(1, destillat.getBatchMængder().size(), "Antallet af mængder i destillat er forkert");
        assertTrue(destillat.getBatchMængder().contains(mængde), "Destillat indeholder ikke den forventede mængde");
    }

    @Test
    void test14_beregnAntalMåneder() {
        destillat.setPåfyldningsDato(LocalDate.of(2025,1,1));
        assertEquals(4,destillat.beregnAlderIMåneder());
    }

    @Test
    void test15_totalHistorik() {
        System.out.println(destillat);
    }
}