package org.unict;

public class Lezione {

    private String idLezione;
    private int idSlot;

    public Lezione(String idLezione, int idSlot){
        this.idLezione = idLezione;
        this.idSlot = idSlot;
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
