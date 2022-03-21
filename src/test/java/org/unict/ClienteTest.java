package org.unict;

import org.junit.*;
import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.util.*;
public class ClienteTest {

    Cliente c;
    Map<String, Prenotazione> e;
    Prenotazione p;

    @Before
    public void setup(){
        c=new Cliente("Pippo","Pippo","Franco","null");
    }
    @After
    public void teardown(){
        c=null;
    }

    @Test
    public void addPrenotazione_elencoPrenotazioniEmpty_returnAdded()throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),"516");
        c.addPrenotazione(p);
        Assert.assertTrue(c.getElencoPrenotazioni().containsValue(p));
    }

    @Test
    public void addPrenotazione_prenotazionePresente_throwsException() throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),"516");
        c.addPrenotazione(p);
        Throwable ex= Assert.assertThrows(PrenotazionePresenteException.class, ()->c.addPrenotazione(p));
        Assert.assertEquals(PrenotazionePresenteException.class, ex.getClass());
    }

    @Test
    public void addPrenotazione_prenotazioniInElenco_returnAdded()throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),"516");
        c.addPrenotazione(p);
        p=new Prenotazione(c.getIdCliente(),"316");
        c.addPrenotazione(p);
        Assert.assertTrue(c.getElencoPrenotazioni().containsValue(p));
    }

}
