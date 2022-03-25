package org.unict;

import org.junit.*;
import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.io.IOException;
import java.util.*;


public class CorsoTest {

    private Corso c;
    private Attrezzo a;
    private List<String> l,n;
    private Istruttore i;
    private Sala s;
    private String o;
    private Prenotazione p;
    private Cliente b;
    private Map<String,Prenotazione> e;


    @Before
    public void setup() throws IOException {
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
        p=null;
        l.clear();
        n.clear();
    }
    @Test
    public void inserisciILezioneTest_datiValidi_returnTrue() throws CorsoException {
        c.inserisciLezione(new Lezione("233445", o,c,s,i));
        Assert.assertTrue(c.getElencoLezioni().containsKey("233445"));
        Assert.assertEquals (1,c.getElencoLezioni().size());
    }

    @Test
    public void inserisciILezioneTest_salaErrata_throwsException() throws IOException{
        s = new Sala("Sala5");
        n =  new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        Throwable e = Assert.assertThrows(CorsoException.class, ()-> c.inserisciLezione(new Lezione("233445", o,c,s,i)));
        Assert.assertEquals(CorsoException.class,e.getClass());
    }

    @Test
    public void inserisciILezioneTest_slotSalaOccupato_throwsException() throws CorsoException{
        c.inserisciLezione(new Lezione("233445", o,c,s,i));
        s.setOccupato(o);
        Throwable e = Assert.assertThrows(CorsoException.class, ()-> c.inserisciLezione(new Lezione("233446", o,c,s,i)));
        Assert.assertEquals(CorsoException.class,e.getClass());
    }

    @Test
    public void inserisciILezioneTest_slotIstruttoreOccupato_throwsException() throws CorsoException{
        c.inserisciLezione(new Lezione("233445", o,c,s,i));
        i.setOccupato(o);
        Throwable e = Assert.assertThrows(CorsoException.class, ()-> c.inserisciLezione(new Lezione("233446", o,c,s,i)));
        Assert.assertEquals(CorsoException.class,e.getClass());
    }

    @Test
    public void mostraLezioni_elencoPrenotazioneEmpty_returnAll() throws SalaPienaException, ClienteOccupatoException, CorsoException {
        n = new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        c.inserisciLezione(new Lezione("233445", o,c,s,i));
        c.inserisciLezione(new Lezione("233446", "316",c,s,i));
        Lezione x= new Lezione("233447", "516",c,s,i);
        c.inserisciLezione(x);
        e= new TreeMap<>();
        Assert.assertEquals(3, c.mostraLezioni(e).size());
        Assert.assertTrue(c.mostraLezioni(e).contains(x));
    }

    @Test
    public void mostraLezioni_prenotazioneInserita_returnLezioniDisponibili() throws SalaPienaException, ClienteOccupatoException, CorsoException {
        n = new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        Lezione x = new Lezione("233445", o,c,s,i);
        c.inserisciLezione(x);
        c.inserisciLezione(new Lezione("233446", "316",c,s,i));
        c.inserisciLezione(new Lezione("233447", "516",c,s,i));
        p= new Prenotazione("Pippo",x);
        e= new TreeMap<>();
        e.put(p.getIdPrenotazione(),p);
        Assert.assertEquals(2, c.mostraLezioni(e).size());
        Assert.assertFalse(c.mostraLezioni(e).contains(x));
    }

    @Test
    public void mostraLezioni_lezioniPrenotate_returnEmpty() throws SalaPienaException, ClienteOccupatoException, CorsoException {
        n = new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        Lezione x= new Lezione("233445", o,c,s,i);
        c.inserisciLezione(x);
        p= new Prenotazione("Pippo",x);
        x=new Lezione("233446", "316",c,s,i);
        c.inserisciLezione(x);
        e= new TreeMap<>();
        e.put(p.getIdPrenotazione(),p);
        p= new Prenotazione("Pippo",x);
        e.put(p.getIdPrenotazione(),p);
        Assert.assertTrue(c.mostraLezioni(e).isEmpty());
    }

    @Test
    public void confermaPrenotazione_lezionePresente_returnPrenotazione() throws CorsoException, LezioneException, PrenotazionePresenteException {
        n = new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        c.inserisciLezione(new Lezione("233445", o,c,s,i));
        c.inserisciLezione(new Lezione("233446", "316",c,s,i));
        c.inserisciLezione(new Lezione("233447", "516",c,s,i));
        p= c.confermaPrenotazione(c.getElencoLezioni().get("233445"), "Pippo");
        Assert.assertNotNull(p);
    }

    @Test
    public void confermaPrenotazione_lezioneNonPresente_throwsException()throws CorsoException, LezioneException, PrenotazionePresenteException{
        n = new LinkedList<>();
        n.add("tappetino");
        s.setListaAttrezzi(n);
        c.inserisciLezione(new Lezione("233445", o,c,s,i));
        c.inserisciLezione(new Lezione("233446", "316",c,s,i));
        c.inserisciLezione(new Lezione("233447", "516",c,s,i));
        Throwable e = Assert.assertThrows(LezioneException.class, ()-> c.confermaPrenotazione(c.getElencoLezioni().get("334555"), "Pippo"));
        Assert.assertEquals(LezioneException.class,e.getClass());
    }
}
