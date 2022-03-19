package org.unict;

import org.junit.*;
import org.unict.domain.*;
import org.unict.domain.exception.CorsoException;

import java.util.*;


public class CorsoTest {

    Corso c;
    Attrezzo a;
    List<String> l,n;
    Istruttore i;
    Sala s;
    String o;


    @Before
    public void setup(){
        l = new LinkedList<>();
        l.add("Sala1");
        l.add("Sala2");
        a= new Attrezzo("tappetino",l);
        c= new Corso("1000","Yoga","principiante","equilibrio",a);
        o="216";
        s= new Sala("Sala1");
        n=  new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        i= new Istruttore("Fabiola");
    }

    @After
    public void teardown(){
        c=null;
        a=null;
        o=null;
        s=null;
        i=null;
        l.clear();
        n.clear();

    }
    @Test
    public void inserisciILezioneTest_datiValidi_returnTrue() throws CorsoException {
        c.inserisciLezione("233445", o,c,s,i);
        Assert.assertTrue(c.getElencoLezioni().containsKey("233445"));
        Assert.assertEquals (1,c.getElencoLezioni().size());
    }

    @Test
    public void inserisciILezioneTest_salaErrata_throwsException() {
        s= new Sala("Sala5");
        n=  new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        Throwable e = Assert.assertThrows(CorsoException.class, ()-> c.inserisciLezione("233445", o,c,s,i));
        Assert.assertEquals(CorsoException.class,e.getClass());
    }

    @Test
    public void inserisciILezioneTest_slotSalaOccupato_throwsException() throws CorsoException{
        c.inserisciLezione("233445", o,c,s,i);
        s.setOccupato(o);
        Throwable e = Assert.assertThrows(CorsoException.class, ()-> c.inserisciLezione("233446", o,c,s,i));
        Assert.assertEquals(CorsoException.class,e.getClass());
    }

    @Test
    public void inserisciILezioneTest_slotIstruttoreOccupato_throwsException() throws CorsoException{
        c.inserisciLezione("233445", o,c,s,i);
        i.setOccupato(o);
        Throwable e = Assert.assertThrows(CorsoException.class, ()-> c.inserisciLezione("233446", o,c,s,i));
        Assert.assertEquals(CorsoException.class,e.getClass());
    }
}
