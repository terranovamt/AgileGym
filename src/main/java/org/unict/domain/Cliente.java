package org.unict.domain;

import java.util.Date;
import java.util.HashMap;

public class Cliente {
    private String idCliente;
    private String nome;
    private String cognome;
    private String dataNascita;
    private HashMap<String,Prenotazione> elencoPrenotazioni;


    public  Cliente(String nome, String cognome, String dataNascita){
        this.idCliente = Agilegym.randId();
        this.nome=nome;
        this.cognome=cognome;
        this.dataNascita = dataNascita;
        this.elencoPrenotazioni=new HashMap<>();
    }

    public String getidCliente() {
        return idCliente;
    }
    public void addPrenotazione(Prenotazione p){
        elencoPrenotazioni.put(p.getIdPrenotazione(),p);
    }
    public HashMap<String, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
    }
}
