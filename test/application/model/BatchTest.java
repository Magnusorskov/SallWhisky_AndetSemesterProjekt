package application.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BatchTest {

    private Batch batch;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        batch = new Batch(Bygsort.EVERGREEN,Mark.MOSEVANG,"CLN","Tørv","Nr11", LocalDate.of(2025,01,01),"Test start");

    }
    @org.junit.jupiter.api.Test
    void test01_KontruktorBatch(){
        assertNotNull(batch, "Batch ikke oprettet");
        assertEquals(Bygsort.EVERGREEN, batch.getBygsort(),"Bygsort er forkert");
        assertEquals(Mark.MOSEVANG, batch.getMark(),"Marken er forkert");
        assertEquals("CLN",batch.getInitialer(), "Initialer er forkert");
        assertEquals("Tørv",batch.getRygemateriale(),"Rygemateriale er forkert");
        assertEquals("Nr11",batch.getMaltBatch(),"Maltbatch er forkert");
        assertEquals(LocalDate.of(2025,01,01), batch.getStartDato(), "Startdatoen er forkert");
        assertEquals("Test start",batch.getKommentar(),"Kommentar er forkert");
        assertEquals(1,batch.getId());

    }
    @Test
    void test02_KonstruktorBatch(){
        Batch batch2 = new Batch(null,null,null,null,null,null,null);
        assertEquals(2,batch2.getId());
    }
}