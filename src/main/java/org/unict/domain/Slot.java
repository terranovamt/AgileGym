package org.unict.domain;

public class Slot {
    private String idSlot; //il primo numero indica il giono della settimana i successivi due indicano l'ora N.B. 208 == Martedí 8AM
    private boolean disponibile;

    public Slot(String dataora, boolean disponibile){
        this.idSlot=dataora;
        this.disponibile = disponibile;
    }

    //GET E SET STANDARD

    public String getIdSlot() {
        return idSlot;
    }

    public void setIdSlot(String idSlot) {
        this.idSlot = idSlot;
    }

    public boolean getDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    //STAMPA
    private String stampaData(String slot){
        String str = "", giorno, ora;

        giorno = switch (Integer.parseInt(String.valueOf(slot.charAt(0)))) {
            case 1 -> "LUNEDI' ore ";
            case 2 -> "MARTEDI' ore ";
            case 3 -> "MERCOLEDI' ore ";
            case 4 -> "GIOVEDI ore ";
            case 5 -> "VENERDI ore ";
            case 6 -> "SABATO ore ";
            default -> "";
        };
        ora= slot.charAt(1)+String.valueOf((slot.charAt(2)));
        str+=giorno+ora+":00";
        return str;
    }

    @Override
    public String toString(){
        String dis;
        if (disponibile)dis="  Disponibile"; else dis="  Occupato";
        return "\tSlot: " + stampaData(idSlot)  + dis +"\n";
    }
}
