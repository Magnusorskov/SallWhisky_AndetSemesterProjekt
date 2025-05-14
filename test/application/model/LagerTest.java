package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LagerTest {

    private Lager lager;
    private Lagervare[][] pladser;
    private Lagervare lagervare;

    @BeforeEach
    void setUp() {
        lager = new Lager("TestLager", 3, 5, "Testvej 8");
        pladser = new Lagervare[4][6];
        lagervare = new Fad(Land.SPANIEN, Fadtype.EXSHERRY, 200);
    }


    @Test
    void test08_KontruktorLager() {
        assertNotNull(lager, "Lager ikke oprettet");
        assertEquals(lager.getNavn(), "TestLager", "Navn på lageret er forkert");
        assertEquals(lager.getAdresse(), "Testvej 8", "Adressen på lageret er forkert");
        assertEquals(lager.getAntalReoler(), 3, "Antal reoler er forkert");
        assertEquals(lager.getAntalHylder(), 5, "Antal rækker er forkert");
        assertTrue(Arrays.deepEquals(pladser, lager.getPladser()), "Pladser er forkerte");
    }

    @Test
    void test09_getStørrelsePåLager() {
        assertEquals(15, lager.getStørrelsePåLager(), "Lager størrelse er forkert");
    }

    // TC1: Lager er tomt, næste ledige plads skal være [1][1]
    @Test
    void test10_opdaterNæsteLedigePlads_tomtLager() {
        lager.opdaterNæsteLedigePlads();
        assertArrayEquals(new int[]{1, 1}, lager.getNæsteLedigPlads(), "Skal finde første plads [1,1] på tomt lager");
    }

    // TC2: [1][1] er optaget, næste ledige er [1][2]
    @Test
    void test11_opdaterNæsteLedigePlads_førsteOptaget() {
        lager.addLagerVare(lagervare, 1, 1);
        lager.opdaterNæsteLedigePlads();
        assertArrayEquals(new int[]{1, 2}, lager.getNæsteLedigPlads(), "Skal finde [1,2] når [1,1] er optaget");
    }


    @Test
    void test12_opdaterNæsteLedigePlads_sidstePladsLedig() {
        for (int reol = 1; reol <= 3; reol++) {
            for (int hylde = 1; hylde <= 5; hylde++) {
                lager.addLagerVare(new Fad(Land.SPANIEN,Fadtype.EXSHERRY,100), reol, hylde);
            }
        }
        lager.getPladser()[3][5].setReolNummer(3);
        lager.getPladser()[3][5].setHyldeNummer(5);
        lager.removeLagerVare(lager.getPladser()[3][5]);
        lager.opdaterNæsteLedigePlads();
        assertArrayEquals(new int[]{3, 5}, lager.getNæsteLedigPlads(), "Skal finde sidste ledige plads [3,5]");
    }


    @Test
    void test13_opdaterNæsteLedigePlads_lagerFyldt() {
        for (int reol = 1; reol <= 3; reol++) {
            for (int hylde = 1; hylde <= 5; hylde++) {
                lager.addLagerVare(new Fad(Land.SPANIEN,Fadtype.EXSHERRY,100), reol, hylde);
            }
        }
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            lager.opdaterNæsteLedigePlads();
        });
        assertEquals("Lageret er fyldt - ingen ledige pladser", exception.getMessage());
    }


    @Test
    void test14_opdaterNæsteLedigePlads_wrapRundtOgFinderLedig() {
        for (int reol = 1; reol <= 3; reol++) {
            for (int hylde = 1; hylde <= 5; hylde++) {
                lager.addLagerVare(new Fad(Land.SPANIEN,Fadtype.EXSHERRY,100), reol, hylde);
            }
        }

        lager.getPladser()[2][3].setReolNummer(2);
        lager.getPladser()[2][3].setHyldeNummer(3);
        lager.removeLagerVare(lager.getPladser()[2][3]);

        lager.opdaterNæsteLedigePlads();
        assertArrayEquals(new int[]{2, 3}, lager.getNæsteLedigPlads(), "Skal wrappe og finde [2,3]");
    }
}