package org.unict.domain;

import java.util.*;


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
        this.elencoPrenotazioni= new TreeMap<>();
    }

    //UC2
    public boolean isDisponibile(Map<String,Prenotazione> elencoPrenotazioneUtente){
        List<String> slotUtente= new ArrayList<>();
        for (String key : elencoPrenotazioneUtente.keySet()) {
            slotUtente.add(elencoPrenotazioneUtente.get(key).getSlot().getDataora());
        }

        if((this.postiDisponibili()-this.elencoPrenotazioni.size())!=0) {
            if(!slotUtente.contains(this.slot.getDataora())){
                return true;
            }
            else   {
                //System.out.println("Sovrapposizione di orario");
                //qui inseriamo la stampa della lezione che si sovrappone cosi portiamo
                // a conoscenza l'utente che esiste quella lezione
            }
        }else {
            //System.out.println("Lezione gia piena");
        }
        return false;
    }

    public int postiDisponibili(){
        String idAttrezzo = c.getAttrezzo().getIdAttrezzo();
        return s.getNumAttrezzi(idAttrezzo);
    }

    public Prenotazione creaPrenotazione(String idCliente){
        Prenotazione p=new Prenotazione(idCliente,this.getSlot());
        elencoPrenotazioni.put(p.getIdPrenotazione(),p);
        return p;
    }

    //GET E SET STANDARD

    public String getIdLezione() {
        return idLezione;
    }

    public Slot getSlot() {
        return slot;
    }

    public Map<String, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
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

    public String stampaRiepilogo(){
        return  "\tNome Corso: " + c.getNomeCorso() + "\n" +
                "\tLivello Corso: " + c.getLivello() + "\n" +
                "\tFocus Corso: " + c.getFocus() + "\n" +
                "\tSala: " + s.getIdSala()+ "\n" +
                "\tIstruttore: " + i.getIdIstruttore() + "\n" +
                "\tData: " +stampaData();
    }
    @Override
    public String toString(){
        return  "\tID: " + idLezione + "\n" +
                "\tNome Corso: " + c.getNomeCorso() + "\n" +
                "\tSala: " + s.getIdSala()+ "\n" +
                "\tPosti Disponibili: " + (postiDisponibili() - this.elencoPrenotazioni.size())+ "\n" +
                "\tIstruttore: " + i.getIdIstruttore() + "\n" +
                "\tData: " +stampaData();
    }
}
