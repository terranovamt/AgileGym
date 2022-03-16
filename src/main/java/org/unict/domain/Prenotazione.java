package org.unict.domain;

public class Prenotazione {
    private final String idPrenotazione;
    private final String idCliente;
    private final Slot slot;

    public  Prenotazione(String idCliente, Slot slot){
        this.idPrenotazione = Agilegym.randId();
        this.idCliente=idCliente;
        this.slot=slot;
    }

    //GET E SET STANDARD
    public String getIdPrenotazione() {
        return idPrenotazione;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public Slot getSlot() {
        return slot;
    }

    //STAMPA
    @Override
    public String toString(){
        return "ID PRENOTAZIONE: " + idPrenotazione + slot;
    }
}
