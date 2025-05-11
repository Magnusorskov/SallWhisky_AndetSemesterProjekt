package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {
    private Fad fad;

    @BeforeEach
    void setUp() {
        fad = new Fad(Land.SPANIEN,Fadtype.EXSHERRY,100);
    }
    @Test
    void test03_KontruktorFad(){
        assertNotNull(fad, "Fad ikke oprettet");
        assertEquals("Spanien", fad.getOprindelsesLand(),"Oprindelsesland er forkert");
        assertEquals(Fadtype.EXSHERRY,fad.getFadType(),"Fadtype er forkert");
        assertEquals(100,fad.getStørrelse(), "Størrelse er forkert");

    }

    @Test
    void test17_getTilgængeligeLiter(){

    }

}