package org.unict;

import java.util.HashMap;
import java.util.Map;

//corso deve creare la lezione per information expert
//un corso ha una lista di lezioni

public class Corso {
    private String idCorso;
    private String nomeCorso;
    private String livello;
    private String focus;
    private String idAttrezzo;
    private Map<String, Lezione> elencoLezioni;

    public Corso(String idCorso, String nomeCorso, String livello, String focus, String idAttrezzo){
        this.idCorso =idCorso;
        this.nomeCorso=nomeCorso;
        this.livello=livello;
        this.focus=focus;
        this.idAttrezzo = idAttrezzo;
        this.elencoLezioni=new HashMap<>();
    }
    public void inserisciLezione(String idLezione, Slot slot, Corso c, Sala s, Istruttore i ){
        Lezione l  = new Lezione(idLezione, slot, c, s, i);
        elencoLezioni.put(idLezione,l);
    }

    public String getIdCorso() {
        return idCorso;
    }

    public String getNome() {
        return nomeCorso;
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

    public void setIdCorso(String idCorso) {
        this.idCorso = idCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }

    public String stampaLezione() {
        String str="";
        for (String key: elencoLezioni.keySet()){//STAMPA ISTRUTTORI DISPONIBILI
            str+= elencoLezioni.get(key).toString();
        }
        return str;
    }


    @Override
    public String toString(){

        String l=stampaLezione(),
                s =
                    "\tID: " + idCorso + "\n" +
                    "\tNome: " + nomeCorso + "\n" +
                    "\tLivello:  " + livello+ "\n" +
                    "\tFocus: " + focus + "\n" +
                    "\tID-Attrezzo: " + idAttrezzo+ "\n\n";

        if (l.length()!=0){
            s+="Lista delle lezioni: \n"+l;
        }
        else {
            s+="\n\t\tNESSUNA LEZIONE INSERITA\n";
        }
        return s;
    }
}
