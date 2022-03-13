package org.unict.domain;

public class Prenotazione {
    String idPrenotazione;
    String idUtente;

    public void Prenotazione(String idUtente){
        this.idPrenotazione = Agilegym.randId();
        this.idUtente=idUtente;
    }



    @Override
    public String toString(){
        return "ID PRENOTAZIONE: " + idPrenotazione;
    }
}
