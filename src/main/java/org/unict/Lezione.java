package org.unict;

public class Lezione {

    private String idLezione;
    private Slot slot;
    private Sala s;
    private Istruttore i;
    private Corso c;

    public Lezione(String idLezione, Slot slot, Corso c, Sala s, Istruttore i){
        this.idLezione = idLezione;
        this.slot = slot;
        this.c= c;
        this.s=s;
        this.i=i;
    }

    public String getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(String idLezione){
        this.idLezione = idLezione;
    }

}
