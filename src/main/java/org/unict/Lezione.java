package org.unict;

public class Lezione {

    private String idLezione;
    private int idSlot;
    private Sala s;
    private Istruttore i;

    public Lezione(String idLezione, int idSlot, Sala s, Istruttore i){
        this.idLezione = idLezione;
        this.idSlot = idSlot;
        this.s=s;
        this.i=i;
    }

    public String getIdLezione() {

        return idLezione;
    }
    public int getIdSlot() {

        return idSlot;
    }

    public void setIdLezione(String idLezione){
        this.idLezione = idLezione;
    }

    public void setIdSlot(int idSlot) {
        this.idSlot = idSlot;
    }

}