package org.unict.domain;

public class Prenotazione {
    private  String idPrenotazione;
    private  String idCliente;
    private  String idSlot;
    //dobbiamo mettere l'oggetto lezione
    //
    public  Prenotazione(String idCliente, String  idSlot){
        this.idPrenotazione = Agilegym.randId();
        this.idCliente=idCliente;
        this.idSlot=idSlot;
        //this.lezione = lezione;
    }

    //GET E SET STANDARD
    public String getIdPrenotazione() {
        return idPrenotazione;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getIdSlot() {
        return idSlot;
    }

    //STAMPA
    @Override
    public String toString(){
        return "ID PRENOTAZIONE: " + idPrenotazione + idSlot;
    }
}
