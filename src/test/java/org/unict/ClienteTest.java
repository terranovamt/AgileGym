package org.unict;

import org.junit.*;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RunWith(Theories.class)
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
        a=null;
        s=null;
        i=null;
        x=null;
    }

    @Test
    public void addPrenotazione_elencoPrenotazioniEmpty_returnAdded()throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),l);
        c.addPrenotazione(p);
        Assert.assertTrue(c.getElencoPrenotazioni().containsValue(p));
    }

    @Test
    public void addPrenotazione_prenotazionePresente_throwsException() throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),l);
        c.addPrenotazione(p);
        Throwable ex= Assert.assertThrows(PrenotazionePresenteException.class, ()->c.addPrenotazione(p));
        Assert.assertEquals(PrenotazionePresenteException.class, ex.getClass());
    }

    @Test
    public void addPrenotazione_prenotazioniInElenco_returnAdded()throws PrenotazionePresenteException{
        p=new Prenotazione(c.getIdCliente(),l);
        c.addPrenotazione(p);
        p=new Prenotazione(c.getIdCliente(),l);
        c.addPrenotazione(p);
        Assert.assertTrue(c.getElencoPrenotazioni().containsValue(p));
    }

    @DataPoints
    public static int[][] dati() {
        return new int[][] {{0,0},{2,0},{3,1}};
    }
    @Theory
    public void getlistPrenotazioni_prenotazioniRavvicinate_returnLast(final int[] dati) throws PrenotazionePresenteException{
        LocalDateTime d= LocalDateTime.now();
        float now=(((d.getDayOfWeek().ordinal()+1)*100)+d.getHour());
        l=new Lezione((String.valueOf(now)),(String.valueOf(now+dati[0])),x,s,i);
        p=new Prenotazione(c.getIdCliente(),l);
        c.addPrenotazione(p);
        /*l=new Lezione((String.valueOf(now+1)),(String.valueOf(now+2)),x,s,i);
        p=new Prenotazione(c.getIdCliente(),l);
        c.addPrenotazione(p);
        l=new Lezione((String.valueOf(now+2)),(String.valueOf(now+3)),x,s,i);
        p=new Prenotazione(c.getIdCliente(),l);
        c.addPrenotazione(p);*/
        Assert.assertEquals(dati[1],c.getlistPrenotazioni().size());
        Assert.assertTrue(dati[1]==0 || c.getlistPrenotazioni().contains(p));
    }

    @Test
    public void replacePrenotazioneTest() throws PrenotazionePresenteException{
        Lezione lc,lo;
        lo=new Lezione("00a316","316",x,s,i);
        p=lo.creaPrenotazione(c.getIdCliente());
        c.addPrenotazione(p);
        lc=new Lezione("00a516","516",x,s,i);
        Assert.assertNotNull(c.replacePrenotazione(p,lc,lo));
        Assert.assertTrue(lc.getElencoPrenotazioni().containsValue(p));
        Assert.assertFalse(lo.getElencoPrenotazioni().containsValue(p));
        Assert.assertEquals(lc,p.getLezione());

    }

}
