package application.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BatchTest {

    private Batch batch1;
    private Batch batch2;
    private StringBuilder sb = new StringBuilder();

    @BeforeEach
    void setUp() {
        batch1 = new Batch(Bygsort.EVERGREEN, Mark.MOSEVANG, "CLN", "Tørv", "Nr11", LocalDate.of(2025, 01, 01), "Test start");
        batch1.setVæskemængde(10);
        batch1.setId(1);
        batch2 = new Batch(Bygsort.EVERGREEN,Mark.MOSEVANG,"CLN",null,"Nr11",LocalDate.of(2025,1,1),null);
        batch2.setId(1);
    }


    @Test
    void test01_KontruktorBatch() {
        assertNotNull(batch1, "Batch ikke oprettet");
        assertEquals(Bygsort.EVERGREEN, batch1.getBygsort(), "Bygsort er forkert");
        assertEquals(Mark.MOSEVANG, batch1.getMark(), "Marken er forkert");
        assertEquals("CLN", batch1.getInitialer(), "Initialer er forkert");
        assertEquals("Tørv", batch1.getRygemateriale(), "Rygemateriale er forkert");
        assertEquals("Nr11", batch1.getMaltBatch(), "Maltbatch er forkert");
        assertEquals(LocalDate.of(2025, 01, 01), batch1.getStartDato(), "Startdatoen er forkert");
        assertEquals("Test start", batch1.getKommentar(), "Kommentar er forkert");
    }

    @Test
    void test02_KonstruktorBatch() {
        batch1 = new Batch(Bygsort.EVERGREEN, Mark.MOSEVANG, "CLN", null, "Nr11", LocalDate.of(2025, 01, 01), null);
        assertNotNull(batch1, "Batch ikke oprettet");
        assertEquals(Bygsort.EVERGREEN, batch1.getBygsort(), "Bygsort er forkert");
        assertEquals(Mark.MOSEVANG, batch1.getMark(), "Marken er forkert");
        assertEquals("CLN", batch1.getInitialer(), "Initialer er forkert");
        assertEquals("Nr11", batch1.getMaltBatch(), "Maltbatch er forkert");
        assertEquals(LocalDate.of(2025, 01, 01), batch1.getStartDato(), "Startdatoen er forkert");
        assertNull(batch1.getKommentar(), "Kommentar ikke null");
        assertNull(batch1.getRygemateriale(), "Rygemateriale ikke null");
    }

    @Test
    void test01_tapBatch() {
        batch1.tapBatch(1);
        assertEquals(9, batch1.getVæskemængde());
    }

    @Test
    void test02_tapBatch() {
        batch1.tapBatch(5);
        assertEquals(5, batch1.getVæskemængde());
    }

    @Test
    void test03_tapBatch() {
        batch1.tapBatch(9);
        assertEquals(1, batch1.getVæskemængde());
    }

    @Test
    void test04_tapBatch() {
        batch1.tapBatch(10);
        assertEquals(0, batch1.getVæskemængde());
    }

    @Test
    void test01_hentHistorik() {
        String forventetHistorikBatch2 = "Batch nr: 1\n" +
                "Bygsort: EVERGREEN(MOSEVANG)\n" +
                "Maltbatch: Nr11\n" +
                "Startdato: 2025-01-01\n" +
                "Initialer: CLN";
        assertEquals(forventetHistorikBatch2,batch2.hentHistorik().toString());
    }

    @Test
    void test02_hentHistorik() {
        batch2.setAlkoholprocent(1);
        String forventetHistorikBatch2 = "Batch nr: 1\n" +
                "Bygsort: EVERGREEN(MOSEVANG)\n" +
                "Maltbatch: Nr11\n" +
                "Alkoholprocent: 1.0\n" +
                "Startdato: 2025-01-01\n" +
                "Initialer: CLN";
        assertEquals(forventetHistorikBatch2,batch2.hentHistorik().toString());
    }

    @Test
    void test03_hentHistorik() {
        batch2.setAlkoholprocent(5);
        String forventetHistorikBatch2 = "Batch nr: 1\n" +
                "Bygsort: EVERGREEN(MOSEVANG)\n" +
                "Maltbatch: Nr11\n" +
                "Alkoholprocent: 5.0\n" +
                "Startdato: 2025-01-01\n" +
                "Initialer: CLN";
        assertEquals(forventetHistorikBatch2,batch2.hentHistorik().toString());
    }

    @Test
    void test04_hentHistorik() {
        batch1.setAlkoholprocent(5);
        String forventetHistorikBatch2 = "Batch nr: 1\n" +
                "Bygsort: EVERGREEN(MOSEVANG)\n" +
                "Maltbatch: Nr11\n" +
                "Rygemateriale: Tørv\n" +
                "Alkoholprocent: 5.0\n" +
                "Startdato: 2025-01-01\n" +
                "Initialer: CLN\n" +
                "Kommentar: \nTest start";
        assertEquals(forventetHistorikBatch2,batch1.hentHistorik().toString());
    }


}