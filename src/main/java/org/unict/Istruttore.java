package org.unict;


import java.util.HashMap;
import java.util.Map;

public class Istruttore {

    private String idIstruttore;
    private Map<String, Slot> listaSlot;     //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

        //l'istruttore non ha uno slot, ma ha una lista di slot in cui lavora
    public Istruttore(String idIstruttore, Map<String, Slot> listaSlot){
         this.idIstruttore = idIstruttore;
         this.listaSlot = new HashMap<>();
    }


    public String getIdIstruttore() {
        return idIstruttore;
    }

    public void setIdIstruttore(String idIstruttore) {
        this.idIstruttore = idIstruttore;
    }

    public void setListaSlot(Map<String, Slot> listaSlot) {
        this.listaSlot = listaSlot;
    }

    public Map<String, Slot> getListaSlot() {
        return listaSlot;
    }

    /*public String stampaListaSlot(){
        String stringa="";
        for(Slot s:listaSlot){
            stringa += s.toString();
        }
        return stringa;
    }*/
    @Override
    public String toString(){

        String s =
                "ID-Istruttore:\t" + idIstruttore + "\n" +
                        "Lista Slot Orari:\t" + listaSlot+ "\n";
        return s;
    }
}
