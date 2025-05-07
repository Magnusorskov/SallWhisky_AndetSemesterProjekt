package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {

    private Batch batch;

    @BeforeEach
    void setUp() {
        batch = new Batch(Bygsort.EVERGREEN, Mark.MOSEVANG, "CLN", "Tørv", "Nr11", LocalDate.of(2025, 01, 01), "Test start");
        batch.setVæskemængde(10);
    }


    @Test
    void test01_KontruktorBatch() {
        assertNotNull(batch, "Batch ikke oprettet");
        assertEquals(Bygsort.EVERGREEN, batch.getBygsort(), "Bygsort er forkert");
        assertEquals(Mark.MOSEVANG, batch.getMark(), "Marken er forkert");
        assertEquals("CLN", batch.getInitialer(), "Initialer er forkert");
        assertEquals("Tørv", batch.getRygemateriale(), "Rygemateriale er forkert");
        assertEquals("Nr11", batch.getMaltBatch(), "Maltbatch er forkert");
        assertEquals(LocalDate.of(2025, 01, 01), batch.getStartDato(), "Startdatoen er forkert");
        assertEquals("Test start", batch.getKommentar(), "Kommentar er forkert");
    }

    @Test
    void test02_KonstruktorBatch() {
        batch = new Batch(Bygsort.EVERGREEN, Mark.MOSEVANG, "CLN", null, "Nr11", LocalDate.of(2025, 01, 01), null);
        assertNotNull(batch, "Batch ikke oprettet");
        assertEquals(Bygsort.EVERGREEN, batch.getBygsort(), "Bygsort er forkert");
        assertEquals(Mark.MOSEVANG, batch.getMark(), "Marken er forkert");
        assertEquals("CLN", batch.getInitialer(), "Initialer er forkert");
        assertEquals("Nr11", batch.getMaltBatch(), "Maltbatch er forkert");
        assertEquals(LocalDate.of(2025, 01, 01), batch.getStartDato(), "Startdatoen er forkert");
        assertNull(batch.getKommentar(), "Kommentar ikke null");
        assertNull(batch.getRygemateriale(), "Rygemateriale ikke null");
    }

    @Test
    void test01_tapBatch() {
        batch.tapBatch(1);
        assertEquals(9,batch.getVæskemængde());
    }

    @Test
    void test02_tapBatch() {
        batch.tapBatch(5);
        assertEquals(5,batch.getVæskemængde());
    }

    @Test
    void test03_tapBatch() {
        batch.tapBatch(9);
        assertEquals(1,batch.getVæskemængde());
    }

    @Test
    void test04_tapBatch() {
        batch.tapBatch(10);
        assertEquals(0,batch.getVæskemængde());
    }
}