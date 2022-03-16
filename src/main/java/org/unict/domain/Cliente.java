package org.unict.domain;

import java.util.HashMap;

public class Cliente {
    private final String idCliente;
    private final String nome;
    private final String cognome;
    private final HashMap<String,Prenotazione> elencoPrenotazioni;


    public  Cliente(String username, String nome, String cognome, String dataNascita){
        this.idCliente = username;
        this.nome=nome;
        this.cognome=cognome;
        this.elencoPrenotazioni=new HashMap<>();
    }

    //UC2
    public void addPrenotazione(Prenotazione p){
        elencoPrenotazioni.put(p.getIdPrenotazione(),p);
    }

    //GET E SET STANDARD
    public String getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public HashMap<String, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
    }

    //STAMPA
    @Override
    public String toString() {
        return "\tNome: " + nome + "\n" + "\tCognome:" + cognome;
    }
}
