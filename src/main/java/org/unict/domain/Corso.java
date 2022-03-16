package org.unict.domain;

import java.util.*;

public class Corso {
    private String idCorso;
    private String nomeCorso;
    private String livello;
    private String focus;
    private Attrezzo attrezzo;
    private final Map<String, Lezione> elencoLezioni;

    public Corso(String idCorso, String nomeCorso, String livello, String focus, Attrezzo attrezzo){
        this.idCorso =idCorso;
        this.nomeCorso=nomeCorso;
        this.livello=livello;
        this.focus=focus;
        this.attrezzo = attrezzo;
        this.elencoLezioni=new HashMap<>();
    }
    //UC1
    public void inserisciLezione(String idLezione, Slot slot, Corso c, Sala s, Istruttore i ){
        Lezione l  = new Lezione(idLezione, slot, c, s, i);
        elencoLezioni.put(idLezione,l);
    }
    //UC2
    public Map<String,Lezione> mostraLezioni(Map<String,Prenotazione> elencoPrenotazioneUtente){
        Map<String,Lezione> elencoLezioniDisponibili= new HashMap<>();

        for (String key : elencoLezioni.keySet()){
            if(elencoLezioni.get(key).isDisponibile(elencoPrenotazioneUtente)){
                elencoLezioniDisponibili.put(elencoLezioni.get(key).getIdLezione(),elencoLezioni.get(key));
            }
        }
        return elencoLezioniDisponibili;
    }

    public Prenotazione confermaPrenotazione(String idLezione, String idCliente){
        return this.elencoLezioni.get(idLezione).creaPrenotazione(idCliente);
    }

    //GET E SET STANDARD
    public String getIdCorso() {
        return idCorso;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public Attrezzo getAttrezzo() {
        return attrezzo;
    }

    public String getLivello() {
        return livello;
    }

    public String getFocus() {
        return focus;
    }

    public Map<String, Lezione> getElencoLezioni() {
        return elencoLezioni;
    }

    //STAMPA
    public String stampaLezione() {
        String str="";
        for (String key: elencoLezioni.keySet()){//STAMPA ISTRUTTORI DISPONIBILI
            str+= "LEZIONE: \n" + elencoLezioni.get(key).toString();
        }
        return str;
    }

    @Override
    public String toString(){
        String l=stampaLezione(),
                s =
                    "\tID: " + idCorso + "\n" +
                    "\tNome: " + nomeCorso + "\n" +
                    "\tLivello: " + livello+ "\n" +
                    "\tFocus: " + focus + "\n" +
                    "\tID-Attrezzo: " + attrezzo.getIdAttrezzo()+ "\n\n";

        if (l.length()!=0){
            s+="Lista delle lezioni: \n"+l;
        }
        else {
            s+="\n\t\tNESSUNA LEZIONE INSERITA\n";
        }
        return s;
    }

    public String stampaCorsi(){
        return  "\tNome: " + nomeCorso + "\n" +
                "\tLivello:  " + livello+ "\n" +
                "\tFocus: " + focus ;
    }
}
