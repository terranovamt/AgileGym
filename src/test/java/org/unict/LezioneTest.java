package org.unict;

import org.junit.*;
import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class LezioneTest {

    private Lezione l;
    private Sala s;
    private Istruttore i;
    private Attrezzo a;
    private Corso c;
    private Prenotazione p;
    private List<String> h;
    private Map<String, Prenotazione> e;

    @Before
    public void setup() throws IOException {
        h = new LinkedList<>();
        h.add("Sala1");
        h.add("Sala2");
        a = new Attrezzo("tappetino", h);
        c = new Corso("1000", "Yoga", "principiante", "equilibrio", a);
        s = new Sala("Sala1");
        List<String> n = new LinkedList<>();
        n.add("tappetino");
        n.add("tappetino");
        s.setListaAttrezzi(n);
        i = new Istruttore("Gianni");
        l = new Lezione("233445","110", c,s,i);
    }

    @After
    public void teardown(){
        l.getElencoPrenotazioni().clear();
        l=null;
        a=null;
        s=null;
        i=null;
        c=null;
        h.clear();
    }

    @Test
    public void isPrenotabile_lezioneNonHaPrenotazioni_returnTrue()throws PrenotazionePresenteException, ClienteException, SalaException {
        e = new TreeMap<>();
        p= new Prenotazione("Pippo",l);
        e.put(p.getIdPrenotazione(),p);
        p=new Prenotazione("Orazio",l);
        e.put(p.getIdPrenotazione(),p);
        l=new Lezione("233446","310", c,s,i);
        Assert.assertTrue(l.isPrenotabile(e));
        Assert.assertEquals(0,l.getElencoPrenotazioni().size());
    }

    @Test
    public void isPrenotabile_clienteNonHaPrenotazioni_returnTrue()throws PrenotazionePresenteException, ClienteException, SalaException {
        e = new TreeMap<>();
        Assert.assertTrue(l.isPrenotabile(e));
    }

    @Test
    public void isPrenotabile_salaPiena_returnFalse()throws PrenotazionePresenteException, ClienteException, SalaException {
        e = new TreeMap<>();
        p= new Prenotazione("Pippo",l);
        e.put(p.getIdPrenotazione(),p);
        p=new Prenotazione("Pippo",l);
        e.put(p.getIdPrenotazione(),p);
        l.creaPrenotazione("Pippo"); //manca il controllo su idCliente in isPrenotabile
        l.creaPrenotazione("Orazio");
        Assert.assertFalse(l.isPrenotabile(e));
    }

    @Test
    public void isPrenotabile_slotClienteOccupato_returnFalse()throws PrenotazionePresenteException, ClienteException, SalaException {
        e = new TreeMap<>();
        p= new Prenotazione("Pippo",l);
        e.put(p.getIdPrenotazione(),p);
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

    @Test
    public void controlloNewPrenotazione_prenotazioneLezioneInCorso_returnFalse(){
        LocalDateTime d= LocalDateTime.now();
        String now=String.valueOf(((d.getDayOfWeek().ordinal()+1)*100)+d.getHour());
        l = new Lezione("233445", now, c,s,i);
        e= new TreeMap<>();
        Assert.assertFalse( l.isPrenotabile(e));
    }

    @Test
    public void updatePrenotazione_elencoPrenotazioniEmpty_returnTrue() {
        p=new Prenotazione("Pippo",l);
        Assert.assertTrue(l.updatePrenotazione(p));
        Assert.assertEquals(1, l.getElencoPrenotazioni().size());
    }

    @Test
    public void removePrenotazione_unaPrenotazioneInElenco_returnTrue() throws PrenotazionePresenteException{
        p=l.creaPrenotazione("Pippo");
        Assert.assertTrue(l.removePrenotazione(p));
        Assert.assertEquals(0, l.getElencoPrenotazioni().size());
    }
}
