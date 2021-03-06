package org.unict.domain;

import org.unict.domain.exception.PrenotazionePresenteException;

import java.time.LocalDateTime;
import java.util.*;


public class Lezione {

    private  String idLezione;
    private  String idSlot;
    private  Sala sala;
    private  Istruttore istruttore;
    private  Corso corso;
    private  Map<String, Prenotazione> elencoPrenotazioni;

    public Lezione(String idLezione, String idSlot, Corso c, Sala s, Istruttore i){
        this.idLezione = idLezione;
        this.idSlot = idSlot;
        this.corso =c;
        this.sala =s;
        this.istruttore =i;
        this.elencoPrenotazioni= new TreeMap<>();
    }


    //UC2
    public boolean isPrenotabile(Map<String,Prenotazione> elencoPrenotazioneCliente) {
        List<String> slotUtentePrenotato= new ArrayList<>();

        for (String key : elencoPrenotazioneCliente.keySet()) {
            slotUtentePrenotato.add(elencoPrenotazioneCliente.get(key).getLezione().getIdSlot());
        }

        if((this.postiDisponibili())>0) {
            if(!slotUtentePrenotato.contains(this.idSlot)){
                if (controlloNewPrenotazione(this.idSlot)) return true;
                else{
                    System.out.println("\nLEZIONE TROPPO VICINA");
                    System.out.print(this);
                    return false;
                }
            }else{
                // qui inseriamo la stampa della lezione che si sovrappone cosi portiamo
                // a conoscenza l'utente che esiste quella lezione
                System.out.println("\nLEZIONE CON SOVRAPPOSIZIONE");
                System.out.print(this);
                return false;
            }
        }else {
            System.out.println("\nLEZIONE GIA' PIENA");
            System.out.print(this);
            return false;
            //throw new SalaPienaException("Non ci sono piú posti prenotabili in questa lezione");
        }
    }

    private boolean controlloNewPrenotazione(String idSlot){
        LocalDateTime d= LocalDateTime.now();
        float now=(((d.getDayOfWeek().ordinal()+1)*100)+d.getHour());
        float controllo= Float.parseFloat(idSlot);
        return (controllo - now) > 1 || (now - controllo) > 0;
    }

    public int postiDisponibili(){
        String idAttrezzo = corso.getAttrezzo().getIdAttrezzo();
        return sala.getNumAttrezzi(idAttrezzo)-this.elencoPrenotazioni.size();
    }

    public Prenotazione creaPrenotazione(String idCliente) throws PrenotazionePresenteException{
        if(elencoPrenotazioni.size() == 0){
            Prenotazione p = new Prenotazione(idCliente,this);
            elencoPrenotazioni.put(p.getIdPrenotazione(), p);
            return p;
        }
        else {
            for (String key : elencoPrenotazioni.keySet()) {
                if (elencoPrenotazioni.get(key).getIdCliente().equals(idCliente))
                    throw new PrenotazionePresenteException("Esiste giá una prenotazione per questo cliente");
            }
            Prenotazione p = new Prenotazione(idCliente, this);
            elencoPrenotazioni.put(p.getIdPrenotazione(), p);
            return p;
        }
    }

    //UC3
    public boolean updatePrenotazione(Prenotazione p){
        return elencoPrenotazioni.put(p.getIdPrenotazione(), p)==null;
    }

    public boolean removePrenotazione(Prenotazione p){
        return elencoPrenotazioni.remove(p.getIdPrenotazione())!=null;
    }

    //GET E SET STANDARD

    public String getIdLezione() {
        return idLezione;
    }

    public String  getIdSlot() {
        return idSlot;
    }

    public Sala getSala() {
        return sala;
    }

    public Istruttore getIstruttore() {
        return istruttore;
    }

    public Map<String, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
    }

    public Corso getCorso() {
        return corso;
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
        return  "\tNome Corso: " + corso.getNomeCorso() + "\n" +
                "\tLivello Corso: " + corso.getLivello() + "\n" +
                "\tFocus Corso: " + corso.getFocus() + "\n" +
                "\tSala: " + sala.getIdSala()+ "\n" +
                "\tIstruttore: " + istruttore.getIdIstruttore() + "\n" +
                "\tData: " +stampaData(idSlot)+ "\n";
    }
    @Override
    public String toString(){
        return  "\tID: " + idLezione + "\n" +
                "\tNome Corso: " + corso.getNomeCorso() + "\n" +
                "\tSala: " + sala.getIdSala()+ "\n" +
                "\tPosti Lezione:" + sala.getNumAttrezzi(corso.getAttrezzo().getIdAttrezzo())+ "\n" +
                "\tPosti Disponibili: " + postiDisponibili() + "\n" +
                "\tIstruttore: " + istruttore.getIdIstruttore() + "\n" +
                "\tData: " +stampaData(idSlot)+ "\n";
    }
}
