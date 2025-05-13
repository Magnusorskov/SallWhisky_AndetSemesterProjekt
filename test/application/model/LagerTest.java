package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LagerTest {

    private Lager lager;
    private Lagervare[][] pladser;

    @BeforeEach
    void setUp() {
        lager = new Lager("TestLager",3,5,"Testvej 8");
         pladser = new Lagervare[4][6];
    }


    @Test
    void test08_KontruktorLager() {
        assertNotNull(lager,"Lager ikke oprettet");
        assertEquals(lager.getNavn(),"TestLager","Navn på lageret er forkert");
        assertEquals(lager.getAdresse(),"Testvej 8","Adressen på lageret er forkert");
        assertEquals(lager.getAntalReoler(),3,"Antal reoler er forkert");
        assertEquals(lager.getAntalHylder(),5,"Antal rækker er forkert");
        assertTrue(Arrays.deepEquals(pladser, lager.getPladser()), "Pladser er forkerte");    }

    @Test
    void test09_getStørrelsePåLager(){
        assertEquals(15,lager.getStørrelsePåLager(),"Lager størrelse er forkert");
    }






}