package org.unict;

import java.util.HashMap;
import java.util.Map;

public class Corso {
    private String idCorso;
    private String  nome;
    private String  livello;
    private String  focus;

    public Corso(String idCorso, String nome, String livello, String focus){
        this.idCorso =idCorso;
        this.nome=nome;
        this.livello=livello;
        this.focus=focus;
    }

    public String getIdCorso() {
        return idCorso;
    }

    public String getNome() {
        return nome;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLivello(String livello) {
        this.livello = livello;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }


}
