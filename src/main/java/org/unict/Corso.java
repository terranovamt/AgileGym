package org.unict;

import java.util.HashMap;
import java.util.Map;

public class Corso {
    private String idCorso;
    private String nomeCorso;
    private String livello;
    private String focus;

    public Corso(String idCorso, String nomeCorso, String livello, String focus){
        this.idCorso =idCorso;
        this.nomeCorso=nomeCorso;
        this.livello=livello;
        this.focus=focus;
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

    public void inserisciLezione(String idLezione, int idSlot, Sala s, Istruttore i ){
        Lezione l  = new Lezione(idLezione, idSlot, s,i);

        System.out.println("Corso inserito");

    }


}
