package org.unict;

import org.junit.*;
import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.util.*;

public class LezioneTest {

    private Lezione l;
    private Sala s;
    private Istruttore i;
    private Attrezzo a;
    private Prenotazione p;
    private List<String> h;
    private Map<String, Prenotazione> e;

    @Before
    public void setup(){
        h = new LinkedList<>();
        h.add("Sala1");
        h.add("Sala2");
        a = new Attrezzo("tappetino", h);
        Corso c = new Corso("1000", "Yoga", "principiante", "equilibrio", a);
        s = new Sala("Sala1");
        List<String> n = new LinkedList<>();
        n.add("tappetino");
        n.add("tappetino");
        s.setListaAttrezzi(n);
        i = new Istruttore("Gianni");

        l = new Lezione("233445","116", c,s,i);
    }

    @After
    public void teardown(){
        l=null;
        a=null;
        s=null;
        i=null;
        h.clear();
    }

    @Test
    public void isPrenotabile_lezioneNonHaPrenotazioni_returnTrue()throws PrenotazionePresenteException, ClienteOccupatoException, SalaPienaException{
        e = new TreeMap<>();
        p= new Prenotazione("Pippo","316");
        e.put(p.getIdPrenotazione(),p);
        p=new Prenotazione("Pippo","516");
        e.put(p.getIdPrenotazione(),p);
        Assert.assertTrue(l.isPrenotabile(e));
    }

    @Test
    public void isPrenotabile_clienteNonHaPrenotazioni_returnTrue()throws PrenotazionePresenteException, ClienteOccupatoException, SalaPienaException{
        e = new TreeMap<>();
        Assert.assertTrue(l.isPrenotabile(e));
    }

    @Test
    public void isPrenotabile_salaPiena_returnException()throws PrenotazionePresenteException, ClienteOccupatoException, SalaPienaException{
        e = new TreeMap<>();
        p= new Prenotazione("Pippo","316");
        e.put(p.getIdPrenotazione(),p);
        p=new Prenotazione("Pippo","516");
        e.put(p.getIdPrenotazione(),p);
        l.creaPrenotazione("Pippo"); //manca il controllo su idCliente in isPrenotabile
        l.creaPrenotazione("Orazio");
        Throwable ex = Assert.assertThrows(SalaPienaException.class, ()-> l.isPrenotabile(e));
        Assert.assertEquals(SalaPienaException.class, ex.getClass());
    }

    @Test
    public void isPrenotabile_slotClienteOccupato_returnFalse()throws PrenotazionePresenteException, ClienteOccupatoException, SalaPienaException{
        e = new TreeMap<>();
        p= new Prenotazione("Pippo","116");
        e.put(p.getIdPrenotazione(),p);
        p=new Prenotazione("Pippo","516");
        e.put(p.getIdPrenotazione(),p);
        //l.creaPrenotazione("Pippo");
        l.creaPrenotazione("Orazio");
        Assert.assertFalse(l.isPrenotabile(e));
    }

    @Test
    public void postiDisponibili_nessunaPrenotazione_returnTrue() {
        Assert.assertEquals(2, l.postiDisponibili());
    }

    @Test
    public void postiDisponibili_elencoPrenotazioniFull_returnTrue() throws PrenotazionePresenteException{
        l.creaPrenotazione("Pippo");
        l.creaPrenotazione("Orazio");
        Assert.assertEquals(0, l.postiDisponibili());
    }

    @Test
    public void creaPrenotazione_elencoPrenotazioniEmpty_returnTrue() throws PrenotazionePresenteException{
        Assert.assertNotNull(l.creaPrenotazione("Pippo"));
    }

    @Test
    public void creaPrenotazione_clienteNonPrenotato_returnTrue() throws PrenotazionePresenteException{
        l.creaPrenotazione("Orazio");
        Assert.assertNotNull(l.creaPrenotazione("Pippo"));
    }

    @Test
    public void creaPrenotazione_clientePrenotato_throwsException() throws PrenotazionePresenteException{
        l.creaPrenotazione("Orazio");
        l.creaPrenotazione("Pippo");
        Throwable ex = Assert.assertThrows(PrenotazionePresenteException.class, ()-> l.creaPrenotazione("Pippo"));
        Assert.assertEquals(PrenotazionePresenteException.class, ex.getClass());
    }
}
