package org.unict;

import org.junit.*;
import org.unict.domain.*;
import org.unict.domain.exception.*;

import java.io.IOException;
import java.util.*;

public class AgilegymTest {

    private Agilegym gym;
    Corso c;

    @Before
    public void setup(){
       gym= Agilegym.getInstance();
    }

    @After
    public void teardown(){
        gym=null;
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

    @Test //caso corsoSelezionato=null?
    public void creaLezione_corsoValido_returnNotNull() throws CorsoException{
        Corso c=gym.nuovoCorso("Judo","principiante","forza","tappetino");
        Assert.assertNotNull(gym.creaLezione(c,"Sala1","510","Matteo"));
    }

    @Test
    public void confermaLezione_lezioneValida_returnAdded() throws CorsoException{
        Corso c=gym.nuovoCorso("Judo","principiante","forza","tappetino");
        Lezione l=gym.creaLezione(c,"Sala1","510","Matteo");
        Assert.assertTrue(gym.confermaLezione(l));
        //Assert.assertFalse(gym.getElencoIstruttori().get(l.getIstruttore().getIdIstruttore()).isDisponibile(l.getIdSlot()));
        Assert.assertFalse(gym.getElencoSale().get(l.getSala().getIdSala()).getSlotDisponibili().contains("510"));
    }
    //UC2
    @Test
    public void nuovaPrenotazione_(){

    }

    @Test
    public void mostraLezione_(){

    }

    @Test
    public void confermaPrenotazione_(){

    }

    //UC3
    @Test
    public void modificaPrenotazione_(){

    }

    @Test
    public void getLezioni_(){

    }

    @Test
    public void updatePrenotazione_(){

    }

    //metodi di load?

    //login
}

