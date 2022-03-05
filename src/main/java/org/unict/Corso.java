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

    public Corso(String idCorso, String nomeCorso, String livello, String focus, String idAttrezzo){
        this.idCorso =idCorso;
        this.nomeCorso=nomeCorso;
        this.livello=livello;
        this.focus=focus;
        this.idAttrezzo = idAttrezzo;
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

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }

    /*public void inserisciLezione(String idLezione, int idSlot, Corso c, Sala s, Istruttore i ){
        Lezione l  = new Lezione(idLezione, idSlot, c, s,i);

        System.out.println("Corso inserito");

    }*/



    @Override
    public String toString(){
        String s =
                "CORSO: \n" +
                        "ID:\t" + idCorso + "\n" +
                        "Nome:\t" + nomeCorso + "\n" +
                        "Livello:\t " + livello+ "\n" +
                        "Focus:\t" + focus + "\n" +
                        "ID-Attrezzo:\t" + idAttrezzo;
        return s;
    }

}
