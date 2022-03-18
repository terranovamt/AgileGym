package org.unict.domain;

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
    public boolean isDisponibile(Map<String,Prenotazione> elencoPrenotazioneUtente){
        List<String> slotUtente= new ArrayList<>();
        for (String key : elencoPrenotazioneUtente.keySet()) {
            slotUtente.add(elencoPrenotazioneUtente.get(key).getIdSlot());
        }

        if((this.postiDisponibili()-this.elencoPrenotazioni.size())!=0) {
            if(!slotUtente.contains(this.idSlot)){
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
        return sala.getNumAttrezzi(idAttrezzo);
    }

    public Prenotazione creaPrenotazione(String idCliente){
        Prenotazione p=new Prenotazione(idCliente,this.idSlot);
        elencoPrenotazioni.put(p.getIdPrenotazione(),p);
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
