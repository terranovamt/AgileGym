package org.unict;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.unict.domain.Sala;
import org.junit.*;

import java.io.IOException;
import java.util.*;
@RunWith(Theories.class)
public class SalaTest {

    private Sala s;

    @Before
    public void setup() throws IOException {
        s= new Sala("Sala1");
    }

    @After
    public void teardown(){
        s=null;
    }

    @Test
    public void loadSlot_mapSlotLoaded_returnTrue(){
        Assert.assertFalse(s.getSlotDisponibili().isEmpty());
    }
    @Test
    public void getSlotDisponibili_slotLiberi_returnAll() throws IOException{
        Assert.assertEquals(s.loadSlot().size(), s.getSlotDisponibili().size());
    }

    @Test
    public void getSlotDisponibili_slotOccupato_returnSlotLiberi () throws IOException{
        s.setOccupato("516");
        Assert.assertFalse(s.getSlotDisponibili().contains("516"));
        Assert.assertEquals(s.loadSlot().size()-1, s.getSlotDisponibili().size());
    }

    @DataPoints
    public static String[][] dati() {
        return new String[][] {{"3","tappetino"},{"1","ball"},{"0","pesi"}};
    }
    @Theory
    public void getNumAttrezzi_salaAttrezzata_returnNum(final String[] dati){
        List<String> l= new LinkedList<>();
        l.add("tappetino");
        l.add("tappetino");
        l.add("ball");
        l.add("tappetino");
        s.setListaAttrezzi(l);
        Assert.assertEquals(Integer.parseInt(dati[0]),s.getNumAttrezzi(dati[1]));
    }

    @Test
    public void getNumAttrezzi_salaEmpty_returnZero() {
        Assert.assertEquals(0,s.getNumAttrezzi("ball"));
    }
}
