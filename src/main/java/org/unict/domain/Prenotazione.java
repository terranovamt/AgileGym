package org.unict.domain;

public class Prenotazione {
    private  String idPrenotazione;
    private  String idCliente;
    private  String idSlot;
    private Lezione lezione;
    //dobbiamo mettere l'oggetto lezione
    //
    public  Prenotazione(String idCliente, String  idSlot,Lezione lezione){
        this.idPrenotazione = Agilegym.randId();
        this.idCliente=idCliente;
        this.idSlot=idSlot;
        this.lezione = lezione;
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

    public Lezione getLezione() {
        return lezione;
    }

    public void setIdSlot(String idSlot) {
        this.idSlot = idSlot;
    }

    public void setLezione(Lezione lezione) {
        this.lezione = lezione;
    }

    //STAMPA
    @Override
    public String toString(){
        return "ID-prenotazione: " + idPrenotazione  +"\nLezione: \n" + lezione.stampaRiepilogo();
    }
}
