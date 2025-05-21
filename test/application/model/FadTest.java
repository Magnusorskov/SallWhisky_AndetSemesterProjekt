package application.model;

import application.model.Enums.Fadtype;
import application.model.Enums.Land;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FadTest {
    private Fad fad;

    @BeforeEach
    void setUp() {
        fad = new Fad(Land.SPANIEN, Fadtype.EXSHERRY,100, "Christoffer");
    }
    @Test
    void test01_KontruktorFad(){
        assertNotNull(fad, "Fad ikke oprettet");
        assertEquals(Land.SPANIEN, fad.getOprindelsesLand(),"Oprindelsesland er forkert");
        assertEquals(Fadtype.EXSHERRY,fad.getFadType(),"Fadtype er forkert");
        assertEquals(100,fad.getStørrelse(), "Størrelse er forkert");

    }


}