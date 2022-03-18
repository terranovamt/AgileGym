package org.unict.domain;

import org.unict.domain.exception.ClienteOccupatoException;
import org.unict.domain.exception.PrenotazionePresenteException;
import org.unict.domain.exception.SalaPienaException;

import java.util.*;


public class Lezione {

    private  String idLezione;
    private  String idSlot;
    private  Sala sala;
    private  Istruttore istruttore;
    private  Corso c;
    private  Map<String, Prenotazione> elencoPrenotazioni;

    public Lezione(String idLezione, String idSlot, Corso c, Sala s, Istruttore i){
        this.idLezione = idLezione;
        this.idSlot = idSlot;
        this.c=c;
        this.sala =s;
        this.istruttore =i;
        this.elencoPrenotazioni= new TreeMap<>();
    }

    //UC2
    public boolean isDisponibile(Map<String,Prenotazione> elencoPrenotazioneUtente) throws ClienteOccupatoException , SalaPienaException {
        List<String> slotUtentePrenotato= new ArrayList<>();
        for (String key : elencoPrenotazioneUtente.keySet()) {
            slotUtentePrenotato.add(elencoPrenotazioneUtente.get(key).getIdSlot());
        }

        if((this.postiDisponibili()-this.elencoPrenotazioni.size())!=0) {
            if(!slotUtentePrenotato.contains(this.idSlot)){
                return true;
            }
            else   {
                throw new ClienteOccupatoException("Hai giá una prenotazione in questo orario");//qui inseriamo la stampa della lezione che si sovrappone cosi portiamo
                // a conoscenza l'utente che esiste quella lezione
            }
        }else {
            throw new SalaPienaException("Non ci sono piú posti prenotabili in questa lezione");
        }
    }

    public int postiDisponibili(){
        String idAttrezzo = c.getAttrezzo().getIdAttrezzo();
        return sala.getNumAttrezzi(idAttrezzo);
    }

    public Prenotazione creaPrenotazione(String idCliente) throws PrenotazionePresenteException{
        Prenotazione p=null;
        for (String key: elencoPrenotazioni.keySet()) {
            if(!elencoPrenotazioni.get(key).getIdCliente().equals(idCliente)){
                p = new Prenotazione(idCliente, this.idSlot);
                elencoPrenotazioni.put(p.getIdPrenotazione(), p);
            }
            else throw new PrenotazionePresenteException("Esiste giá una prenotazione per questo cliente");
        }
        return p;
    }

    //GET E SET STANDARD

    public String getIdLezione() {
        return idLezione;
    }

    public String  getIdSlot() {
        return idSlot;
    }


    public Map<String, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
    }
    //STAMPA
    private String stampaData(String slot){
        String str = "", giorno, ora;
        giorno = switch (Integer.parseInt(String.valueOf(slot.charAt(0)))) {
            case 1 -> "LUNEDI' ore ";
            case 2 -> "MARTEDI' ore ";
            case 3 -> "MERCOLEDI' ore ";
            case 4 -> "GIOVEDI ore ";
            case 5 -> "VENERDI ore ";
            case 6 -> "SABATO ore ";
            default -> "";
        };
        ora= slot.charAt(1)+String.valueOf((slot.charAt(2)));
        str+=giorno+ora+":00";
        return str;
    }

    public String stampaRiepilogo(){
        return  "\tNome Corso: " + c.getNomeCorso() + "\n" +
                "\tLivello Corso: " + c.getLivello() + "\n" +
                "\tFocus Corso: " + c.getFocus() + "\n" +
                "\tSala: " + sala.getIdSala()+ "\n" +
                "\tIstruttore: " + istruttore.getIdIstruttore() + "\n" +
                "\tData: " +stampaData(idSlot)+ "\n";
    }
    @Override
    public String toString(){
        return  "\tID: " + idLezione + "\n" +
                "\tNome Corso: " + c.getNomeCorso() + "\n" +
                "\tSala: " + sala.getIdSala()+ "\n" +
                "\tPosti Disponibili: " + (postiDisponibili() - this.elencoPrenotazioni.size())+ "\n" +
                "\tIstruttore: " + istruttore.getIdIstruttore() + "\n" +
                "\tData: " +stampaData(idSlot)+ "\n";
    }
}
