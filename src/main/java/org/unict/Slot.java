package org.unict;

import static java.lang.Math.abs;

public class Slot {
    private String idSlot;
    private String dataora; //il primo numero indica il giono della settimana i successivi due indicano l'ora N.B. 208 == Martedí 8AM
    private boolean disponibile;

    public Slot(String dataora, boolean disponibile){
        this.idSlot = String.valueOf((abs((int) System.currentTimeMillis() + (int)(Math.random()*(1000000000)))/10));
        this.dataora=dataora;
        this.disponibile = disponibile;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setIdSlot(String idSlot) {
        this.idSlot = idSlot;
    }

    public void setDataora(String dataora) {
        this.dataora = dataora;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public String getIdSlot() {
        return idSlot;
    }

    public String getDataora() {
        return dataora;
    }

    @Override
    public String toString(){
        String dis="";
        if (disponibile==true)dis="\tDisponibile";else dis="\tOccupato";
       String s =
                "\tID-Slot:" + dataora  + dis +"\n";
        return s;
    }
}
