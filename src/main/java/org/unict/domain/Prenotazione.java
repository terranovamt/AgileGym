package org.unict.domain;

public class Prenotazione {
    private String idPrenotazione;
    private String idCliente;

    public  Prenotazione(String idCliente){
        this.idPrenotazione = Agilegym.randId();
        this.idCliente=idCliente;
    }

    public String getIdPrenotazione() {
        return idPrenotazione;
    }

    @Override
    public String toString(){
        return "ID PRENOTAZIONE: " + idPrenotazione;
    }
}
