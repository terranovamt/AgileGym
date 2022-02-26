package org.unict;


public class Istruttore {

    private String idIstruttore;
    private Slot s;     //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

    public Istruttore(String idIstruttore, Slot s){
         this.idIstruttore = idIstruttore;
         this.s =s;
    }

    public String getIdIstruttore() {
        return idIstruttore;
    }

    public void setIdIstruttore(String idIstruttore) {
        this.idIstruttore = idIstruttore;
    }

    public int getIdSlot() {
        return s.getIdSlot();
    }

    public void setIdSlot(Slot s) {
        this.s = s;
    }
}
