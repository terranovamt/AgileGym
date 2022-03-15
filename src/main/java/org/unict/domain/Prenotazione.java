package org.unict.domain;

public class Prenotazione {
    private final String idPrenotazione;
    private final String idCliente;

    public  Prenotazione(String idCliente){
        this.idPrenotazione = Agilegym.randId();
        this.idCliente=idCliente;
    }

    public String getIdPrenotazione() {
        return idPrenotazione;
    }

    public String getIdCliente() {
        return idCliente;
    }

    @Override
    public String toString(){
        return "ID PRENOTAZIONE: " + idPrenotazione;
    }
}
