package org.unict;

import org.junit.*;
import org.unict.domain.Istruttore;


public class IstruttoreTest {

    private Istruttore i;

    @Before
    public void setup() {
        i= new Istruttore("Gino");
    }

    @After
    public void teardown() {
        i=null;
    }

    @Test
    public void loadSlot_mapSlotLoaded_returnTrue() {
        Assert.assertFalse(i.getMapSlot().isEmpty());
    }

    @Test
    public void isDisponibileTest(){
        i.setOccupato("510");
        Assert.assertFalse(i.isDisponibile("510"));
    }
}
