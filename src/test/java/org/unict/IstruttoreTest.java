package org.unict;

import org.junit.*;
import org.unict.domain.Istruttore;


public class IstruttoreTest {

    Istruttore i;

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
    public void idDisponibileTest(){
        i.setOccupato("501");
        Assert.assertFalse(i.isDisponibile("501"));
    }
}
