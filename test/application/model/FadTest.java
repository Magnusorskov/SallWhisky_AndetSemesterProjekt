package application.model;

import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {
    private Fad fad;

    @BeforeEach
    void setUp() {
        fad = new Fad("Spanien",Fadtype.EXSHERRY,100);
    }
    @org.junit.jupiter.api.Test
    void test03_KontruktorFad(){
        assertNotNull(fad, "Fad ikke oprettet");
        assertEquals("Spanien", fad.getOprindelsesLand(),"Oprindelsesland er forkert");
        assertEquals(Fadtype.EXSHERRY,fad.getFadType(),"Fadtype er forkert");
        assertEquals(100,fad.getStørrelse(), "Størrelse er forkert");

    }

}