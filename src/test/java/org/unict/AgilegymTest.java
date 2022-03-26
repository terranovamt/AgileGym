package org.unict;

import org.junit.*;

import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.util.LinkedList;
import java.util.List;

public class AgilegymTest {

    private Agilegym gym;
    private Corso c;
    private Cliente x;
    private Lezione l;

    @Before
    public void setup(){
       gym= Agilegym.getInstance();
    }

    @After
    public void tearDown(){
        gym.getElencoCorsi().clear();
        gym.getElencoClienti().clear();
        gym.loadIstruttore();
        gym.loadAttrezzi();
        gym.loadSale();
        gym=null;
        x=null;
        c=null;
        l=null;
    }
    //UC1
    @Test
    public void nuovoCorso_corsoValidoPalestraVuota_returnAdded() throws CorsoException{
        c= gym.nuovoCorso("Judo","principiante","forza","tappetino");
        Assert.assertNotNull(c);
        Assert.assertEquals("Judo", c.getNomeCorso());
    }

    @Test //o qui o in confermaCorso controllo doppioni
    public void nuovoCorso_corsoPresente_error() throws CorsoException{
        gym.riempiPalestra();
        gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino");
    }

    @Test
    public void nuovoCorso_attrezzoNonPresente_throwsException() throws CorsoException{
        Throwable ex= Assert.assertThrows(CorsoException.class, ()-> gym.nuovoCorso("Judo","principiante","forza","tatami"));
        Assert.assertEquals(CorsoException.class, ex.getClass());
    }

    @Test
    public void confermaCorso_corsoValido_returnTrue() throws CorsoException{
        c= gym.nuovoCorso("Judo","principiante","forza","tappetino");
        Assert.assertTrue(gym.confermaCorso(c));
    }

    @Test
    public void getIstruttori_istruttoriLiberi_returnAll() throws IstruttoreException{

        Assert.assertEquals(5,gym.getIstruttori("516").size());
    }

    @Test
    public void getIstruttori_istruttoreOccupato_returnOthers() throws IstruttoreException{
        Istruttore i= gym.getElencoIstruttori().get("Matteo");
        i.setOccupato("516");
        Assert.assertFalse(gym.getIstruttori("516").contains(i.getIdIstruttore()));
    }

    @Test
    public void getIstruttori_istruttoriOccupati_throwsException() throws IstruttoreException{
        Istruttore i;
        for(String key : gym.getElencoIstruttori().keySet()){
            i=gym.getElencoIstruttori().get(key);
            i.setOccupato("516");
        }
        Throwable ex= Assert.assertThrows(IstruttoreException.class, ()->gym.getIstruttori("516"));
        Assert.assertEquals(IstruttoreException.class, ex.getClass());
    }
//aggiungere eventualmente test case per lezioni doppie
    @Test //caso corsoSelezionato=null?
    public void creaLezione_corsoValido_returnNotNull() throws CorsoException {
        Corso c=gym.nuovoCorso("Judo","principiante","forza","tappetino");
        Assert.assertNotNull(gym.creaLezione(c,"Sala1","510","Matteo"));
    }

    @Test
    public void confermaLezione_lezioneValida_returnAdded() throws CorsoException, LezioneException{
        Corso c=gym.nuovoCorso("Judo","principiante","forza","tappetino");
        l=gym.creaLezione(c,"Sala1","510","Matteo");
        Assert.assertTrue(gym.confermaLezione(l));
        Assert.assertFalse(gym.getElencoIstruttori().get(l.getIstruttore().getIdIstruttore()).isDisponibile(l.getIdSlot()));
        Assert.assertFalse(gym.getElencoSale().get(l.getSala().getIdSala()).getSlotDisponibili().contains("510"));
    }
    //UC2
    @Test
    public void nuovaPrenotazione_elencoCorsiEmpty_returnEmpty(){
        Assert.assertEquals(0,gym.nuovaPrenotazione().size());
    }

    @Test
    public void nuovaPrenotazione_corsoInElenco_returnCorso()throws CorsoException, LezioneException{
        Corso c=gym.nuovoCorso("Judo","principiante","forza","tappetino");
        gym.confermaCorso(c);
        Assert.assertEquals(1,gym.nuovaPrenotazione().size());
        Assert.assertTrue(gym.nuovaPrenotazione().contains(c));
    }

    @Test
    public void mostraLezione_lezionePrenotata_returnOthers()throws CorsoException,LezioneException,PrenotazionePresenteException{
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        List<Lezione> e;
        gym.confermaLezione(gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaLezione(gym.creaLezione(c,"Sala1","310","Matteo"));
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","510","Matteo"));
        x= new Cliente("Pippo","Pippo","Franco");
        Prenotazione p= new Prenotazione(x.getIdCliente(),l);
        x.addPrenotazione(p);
        e=gym.mostraLezione(c.getIdCorso(),x);
        Assert.assertEquals(2,e.size());
        Assert.assertFalse(e.contains(l));
    }

    @Test
    public void mostraLezione_corsoNonHaLezioni_returnEmpty() throws CorsoException,LezioneException,PrenotazionePresenteException{
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        List<Lezione> e;
        gym.confermaLezione(gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaLezione(gym.creaLezione(c,"Sala1","310","Matteo"));
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","510","Matteo"));
        x= new Cliente("Pippo","Pippo","Franco");
        Prenotazione p= new Prenotazione(x.getIdCliente(),l);
        x.addPrenotazione(p);
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","avanzato","equilibrio","tappetino"));
        e=gym.mostraLezione(c.getIdCorso(),x);
        Assert.assertEquals(0,e.size());
        Assert.assertNotNull(e);
    }

    @Test
    public void mostraLezione_lezioneOrarioCoincidente_returnEmpty() throws CorsoException,LezioneException,PrenotazionePresenteException{
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        List<Lezione> e;
        gym.confermaLezione(gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaLezione(gym.creaLezione(c,"Sala1","310","Matteo"));
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","510","Matteo"));
        x= new Cliente("Pippo","Pippo","Franco");
        Prenotazione p= new Prenotazione(x.getIdCliente(),l);
        x.addPrenotazione(p);
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","avanzato","equilibrio","tappetino"));
        gym.confermaLezione(gym.creaLezione(c,"Sala2","510","Federica"));
        e=gym.mostraLezione(c.getIdCorso(),x);
        Assert.assertEquals(0,e.size());
        Assert.assertNotNull(e);
    }

    @Test
    public void mostraLezione_lezioniTuttePrenotate_returnEmpty() throws CorsoException,LezioneException,PrenotazionePresenteException{
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        List<Lezione> e;
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","110","Matteo"));
        x= new Cliente("Pippo","Pippo","Franco");
        Prenotazione p= new Prenotazione(x.getIdCliente(),l);
        x.addPrenotazione(p);
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","310","Matteo"));
        p= new Prenotazione(x.getIdCliente(),l);
        x.addPrenotazione(p);
        e=gym.mostraLezione(c.getIdCorso(),x);
        Assert.assertEquals(0,e.size());
        Assert.assertNotNull(e);
    }

    @Test
    public void confermaPrenotazione_elencoPrenotazioniClienteEmpty_returnAdded() throws CorsoException, LezioneException, PrenotazionePresenteException{
        x= new Cliente("Pippo","Pippo","Franco");
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaPrenotazione(l,x);
        Assert.assertEquals(1,x.getlistPrenotazioni().size());
    }

    @Test
    public void confermaPrenotazione_lezionePrenotata_returnZero() throws CorsoException, LezioneException, PrenotazionePresenteException{
        x= new Cliente("Pippo","Pippo","Franco");
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","110","Matteo"));
        Prenotazione p= new Prenotazione(x.getIdCliente(),l);
        x.addPrenotazione(p);
        gym.confermaPrenotazione(l,x);
        Assert.assertEquals(1,x.getlistPrenotazioni().size());
    }

    @Test
    public void confermaPrenotazione_limitePostiRaggiunto_returnZero() throws CorsoException, LezioneException, PrenotazionePresenteException{
        Sala s= gym.getElencoSale().get("Sala1");
        List<String> a= new LinkedList<>();
        a.add("tappetino");
        s.setListaAttrezzi(a);
        x= new Cliente("Pippo","Pippo","Franco");
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaPrenotazione(l,x);
        Cliente y= new Cliente("GAscia","Giuseppe","Ascia");
        gym.confermaPrenotazione(l,y);
        Assert.assertEquals(0,y.getlistPrenotazioni().size());
    }

    //UC3
    @Test
    public void getLezioni_lezionePrenotata_returnOthers() throws CorsoException, LezioneException, PrenotazionePresenteException{
        x= new Cliente("Pippo","Pippo","Franco");
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        gym.confermaLezione(gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaLezione(gym.creaLezione(c,"Sala1","310","Matteo"));
        gym.confermaLezione(l=gym.creaLezione(c,"Sala1","510","Matteo"));
        Prenotazione p= new Prenotazione(x.getIdCliente(),l);
        x.addPrenotazione(p);
        Assert.assertEquals(2,gym.getLezioni(p,x).size());
        Assert.assertFalse(gym.getLezioni(p,x).contains(l));
    }

    @Test
    public void updatePrenotazione_lezioniDiverse_returnTrue() throws CorsoException, LezioneException, PrenotazionePresenteException{
        x= new Cliente("Pippo","Pippo","Franco");
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        Lezione lo,lc;
        gym.confermaLezione(lo=gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaLezione(lc=gym.creaLezione(c,"Sala1","310","Matteo"));
        Prenotazione p= lo.creaPrenotazione(x.getIdCliente());
        x.addPrenotazione(p);
        Assert.assertTrue(gym.updatePrenotazione(lc,p,x));
    }

    @Test
    public void updatePrenotazione_stessaLezioneSelezionata_returnFalse() throws CorsoException, LezioneException, PrenotazionePresenteException{
        x= new Cliente("Pippo","Pippo","Franco");
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        Lezione lo,lc;
        gym.confermaLezione(lo=gym.creaLezione(c,"Sala1","110","Matteo"));
        lc=lo;
        Prenotazione p= lo.creaPrenotazione(x.getIdCliente());
        x.addPrenotazione(p);
        Assert.assertFalse(gym.updatePrenotazione(lc,p,x));
    }

    @Test
    public void updatePrenotazione_clienteErrato_returnFalse() throws CorsoException, LezioneException, PrenotazionePresenteException{
        x= new Cliente("Pippo","Pippo","Franco");
        gym.confermaCorso(c=gym.nuovoCorso("Yoga","principiante","equilibrio","tappetino"));
        Lezione lo,lc;
        gym.confermaLezione(lo=gym.creaLezione(c,"Sala1","110","Matteo"));
        gym.confermaLezione(lc=gym.creaLezione(c,"Sala1","310","Matteo"));
        Prenotazione p= lo.creaPrenotazione(x.getIdCliente());
        x.addPrenotazione(p);
        Cliente a= new Cliente("Gascia","Giuseppe","Ascia");
        Prenotazione b= lo.creaPrenotazione(a.getIdCliente());
        a.addPrenotazione(b);
        Assert.assertNotEquals(b.getIdPrenotazione(),p.getIdPrenotazione());
        Assert.assertFalse(gym.updatePrenotazione(lc,p,a));
    }

}

