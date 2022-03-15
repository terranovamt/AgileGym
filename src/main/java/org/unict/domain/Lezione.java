package org.unict.domain;

import java.util.HashMap;
import java.util.Map;

public class Lezione {

    private final String idLezione;
    private final Slot slot;
    private final Sala s;
    private final Istruttore i;
    private final Corso c;
    private final Map<String, Prenotazione> elencoPrenotazioni;

    public Lezione(String idLezione, Slot slot, Corso c, Sala s, Istruttore i){
        this.idLezione = idLezione;
        this.slot = slot;
        this.c=c;
        this.s=s;
        this.i=i;
        this.elencoPrenotazioni= new HashMap<>();
    }

    public int postiDisponibili(){
        String idAttrezzo = c.getAttrezzo().getIdAttrezzo();
        return s.getNumAttrezzi(idAttrezzo);
    }

    public Map<String, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
    }

    public Lezione lezioneDisponibile(Map<String,Prenotazione> elencoPrenotazioniUtente){
        for (String key : elencoPrenotazioniUtente.keySet()) {
            if(this.elencoPrenotazioni.containsKey(elencoPrenotazioniUtente.get(key).getIdPrenotazione())){
                return null;
            }
        }
        return this;
    }

    public String getIdLezione() {
        return idLezione;
    }

    public Prenotazione creaPrenotazione(String idCliente){
        Prenotazione p=new Prenotazione(idCliente);
        elencoPrenotazioni.put(p.getIdPrenotazione(),p);
        return p;
    }

    //STAMPA
    private String stampaData(){
        String str="", giorno, ora;

            switch (Integer.parseInt(String.valueOf(String.valueOf(slot.getDataora()).charAt(0)))){
                case 1: giorno="LUNEDI' ore ";     break;
                case 2: giorno="MARTEDI' ore ";    break;
                case 3: giorno="MERCOLEDI' ore ";  break;
                case 4: giorno="GIOVEDI ore ";     break;
                case 5: giorno="VENERDI ore ";     break;
                case 6: giorno="SABATO ore ";      break;
                default:giorno="";
            }
            ora= slot.getDataora().charAt(1)+String.valueOf((slot.getDataora().charAt(2)));
            str+=giorno+ora+":00\n";
        return str;
    }

    @Override
    public String toString(){
        return  "\tID: " + idLezione + "\n" +
                "\tNome Corso: " + c.getNome() + "\n" +
                "\tSala: " + s.getIdSala()+ "\n" +
                "\tIstruttore: " + i.getIdIstruttore() + "\n" +
                "\tData: " +stampaData();
    }
}
