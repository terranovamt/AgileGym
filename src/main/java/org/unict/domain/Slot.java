package org.unict.domain;

import static java.lang.Math.abs;

public class Slot {
    private String idSlot;
    private String dataora; //il primo numero indica il giono della settimana i successivi due indicano l'ora N.B. 208 == Martedí 8AM
    private boolean disponibile;

    public Slot(String dataora, boolean disponibile){
        this.idSlot = Agilegym.randId();
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
    private String stampadata(String slot){
        String str="", giorno, ora="";

        switch (Integer.parseInt(String.valueOf(slot.charAt(0)))){
            case 1: giorno="LUNEDI' ore ";     break;
            case 2: giorno="MARTEDI' ore ";    break;
            case 3: giorno="MERCOLEDI' ore ";  break;
            case 4: giorno="GIOVEDI ore ";     break;
            case 5: giorno="VENERDI ore ";     break;
            case 6: giorno="SABATO ore ";      break;
            default:giorno="";
        }
        ora= slot.charAt(1)+String.valueOf((slot.charAt(2)));
        str+=giorno+ora+":00";
        return str;
    }

    @Override
    public String toString(){
        String dis="";
        if (disponibile==true)dis="  Disponibile";else dis="  Occupato";
       String s =
                "\tSlot: " + stampadata( dataora)  + dis +"\n";
        return s;
    }
}
