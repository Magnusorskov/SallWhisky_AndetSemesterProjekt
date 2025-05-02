package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LagerTest {

    private Lager lager;

    @BeforeEach
    void setUp() {
        lager = new Lager("TestLager",3,5,"Testvej 8");
    }

    @Test
    void getStørrelsePåLager() {
        assertEquals(15,lager.getStørrelsePåLager());
    }
}