package org.unict;

public class Istruttore {

    private String idIstruttore;
    private int idSlot;     //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

    public Istruttore(String idIstruttore, int idSlot){
         this.idIstruttore = idIstruttore;
         this.idSlot = idSlot;
     }

    public String getIdIstruttore() {
        return idIstruttore;
    }

    public void setIdIstruttore(String idIstruttore) {
        this.idIstruttore = idIstruttore;
    }

    public int getIdSlot() {
        return idSlot;
    }

    public void setIdSlot(int idSlot) {
        this.idSlot = idSlot;
    }
}
