package org.unict;

public class Slot {
    private String idSlot; //il primo numero indica il giono della settimana i successivi due indicano l'ora N.B. 208 == Martedí 8AM
    private boolean disponibile;

    public Slot(String idSlot, boolean disponibilita){
        this.idSlot = this.idSlot;
        this.disponibile = disponibile;
    }

    public void setIdSlot(String idSlot) {
        this.idSlot = idSlot;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }


    public String getIdSlot() {
        return idSlot;
    }
    @Override
    public String toString(){
        String dis="";
        if (disponibile==true)dis="\tDisponibile";else dis="\tOccupato";
       String s =
                "\tID-Slot:" + idSlot  + dis +"\n";
        return s;
    }
}
