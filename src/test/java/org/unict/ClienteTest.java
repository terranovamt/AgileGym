package org.unict;

import org.junit.*;
import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class ClienteTest {

    private Cliente c;
    private Prenotazione p;
    private Lezione l;
    private Corso x;
    private Sala s;
    private Istruttore i;
    private Attrezzo a;

    @Before
    public void setup() throws IOException {
        List<String> e=new LinkedList<>();
        a=new Attrezzo("tappetino",e);
        s=new Sala("Sala1");
        i=new Istruttore("Gianni");
        x=new Corso("1000","Yoga","principiante","equilibrio",a);
        c=new Cliente("Pippo","Pippo","Franco");
        l=new Lezione("233445","516",x,s,i);
    }

    @After
    public void teardown(){
        c=null;
        p=null;
        l=null;
    }

    @Test
    public void addPrenotazione_elencoPrenotazioniEmpty_returnAdded()throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),"516",l);
        c.addPrenotazione(p);
        Assert.assertTrue(c.getElencoPrenotazioni().containsValue(p));
    }

    @Test
    public void addPrenotazione_prenotazionePresente_throwsException() throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),"516",l);
        c.addPrenotazione(p);
        Throwable ex= Assert.assertThrows(PrenotazionePresenteException.class, ()->c.addPrenotazione(p));
        Assert.assertEquals(PrenotazionePresenteException.class, ex.getClass());
    }

    @Test
    public void addPrenotazione_prenotazioniInElenco_returnAdded()throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),"516",l);
        c.addPrenotazione(p);
        p=new Prenotazione(c.getIdCliente(),"316",l);
        c.addPrenotazione(p);
        Assert.assertTrue(c.getElencoPrenotazioni().containsValue(p));
    }

    @Test
    public void getlistPrenotazioni_prenotazioniRavvicinate_returnLast() throws PrenotazionePresenteException{
        LocalDateTime d= LocalDateTime.now();
        float now=(((d.getDayOfWeek().ordinal()+1)*100)+d.getHour());
        p=new Prenotazione(c.getIdCliente(),String.valueOf(now),l);
        c.addPrenotazione(p);
        p=new Prenotazione(c.getIdCliente(),String.valueOf(now+2),l);
        c.addPrenotazione(p);
        p=new Prenotazione(c.getIdCliente(),String.valueOf(now+3),l);
        c.addPrenotazione(p);
        Assert.assertEquals(1,c.getlistPrenotazioni().size());
        Assert.assertEquals(p, c.getlistPrenotazioni().get(0));
    }

    @Test
    public void replacePrenotazioneTest() throws PrenotazionePresenteException{
        Lezione lc,lo;
        lo=new Lezione("00a316","316",x,s,i);
        lc=new Lezione("00a516","516",x,s,i);
        p=lo.creaPrenotazione("Pippo");
        Assert.assertTrue(c.replacePrenotazione(p,lc,lo));
        Assert.assertTrue(lc.getElencoPrenotazioni().containsValue(p));
        Assert.assertFalse(lo.getElencoPrenotazioni().containsValue(p));
        Assert.assertEquals(lc,p.getLezione());

    }

}
