package org.unict;

public class Slot {
    private int idSlot; //il primo numer oindica il giono della settimana i successivi due indicano l'ora N.B. 208 == Martedí 8AM
    private boolean disponibile= true;

    public void setIdSlot(int idSlot) {

        this.idSlot = idSlot;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public int getIdSlot() {
        return idSlot;
    }

    public boolean isDisponibile(int idSlot) {
        if (idSlot==this.idSlot){
            return disponibile;
        }
        else return !disponibile;
    }
}
