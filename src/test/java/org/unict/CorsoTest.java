package org.unict;

import org.junit.*;
import org.unict.domain.*;

import java.util.*;


public class CorsoTest {
    //nome_scenario_expected add_singlenumber_returnsamenumber
    Corso c;
    Attrezzo a;
    List<String> l;
    Istruttore i;
    Sala s;
    Slot o;


    @Before
    public void setup(){
        l = new LinkedList<>();
        l.add("Sala1");
        l.add("Sala2");
        a= new Attrezzo("tappetino",l);
        c= new Corso("1000","Yoga","principiante","equilibrio",a);
        o=new Slot("216", true);
        s= new Sala("Sala1");
        i= new Istruttore("Fabiola");
    }

    @After
    public void teardown(){
        c=null;
        a=null;
        o=null;
        s=null;
        i=null;
    }
    @Test
    public void inserisciILezioneTest_datiValidi_returnTrue(){
        c.inserisciLezione("233445", o,c,s,i);
        Assert.assertTrue(c.getElencoLezioni().containsKey("233445"));
        Assert.assertEquals (1,c.getElencoLezioni().size());
    }
}
