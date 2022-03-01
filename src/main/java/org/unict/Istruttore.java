package org.unict;


import java.util.LinkedList;

public class Istruttore {

    private String idIstruttore;
    private LinkedList<Slot> listaSlot;     //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

        //l'istruttore non ha uno slot, ma ha una lista di slot in cui lavora
    public Istruttore(String idIstruttore, LinkedList<Slot> listaSlot){
         this.idIstruttore = idIstruttore;
         this.listaSlot = new LinkedList<Slot>();
    }

    public String getIdIstruttore() {
        return idIstruttore;
    }

    public void setIdIstruttore(String idIstruttore) {
        this.idIstruttore = idIstruttore;
    }

    public void setListaSlot(LinkedList<Slot> listaSlot) {
        this.listaSlot = listaSlot;
    }

    public LinkedList<Slot> getListaSlot() {
        return listaSlot;
    }
    public String stampaListaSlot(){
        String stringa="";
        for(Slot s:listaSlot){
            stringa += s.toString();
        }
        return stringa;
    }
    @Override
    public String toString(){

        String s =
                "ID-Istruttore:\t" + idIstruttore + "\n" +
                        "Lista Slot Orari:\t" + stampaListaSlot() + "\n";
        return s;
    }
}
