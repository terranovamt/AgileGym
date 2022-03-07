package org.unict;


import java.util.HashMap;
import java.util.Map;

public class Istruttore {

    private String idIstruttore;
    private Map<Integer, Slot> listaSlot;     //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

        //l'istruttore non ha uno slot, ma ha una lista di slot in cui lavora
    public Istruttore(String idIstruttore, Map<Integer, Slot> listaSlot){
         this.idIstruttore = idIstruttore;
         this.listaSlot = listaSlot;
    }

    public boolean isIstruttoreDiponibili(int idSlot){
        for (Map.Entry<Integer, Slot> entry : listaSlot.entrySet()) { //scorre gli slot per l'istuttore corrente
            if (Integer.parseInt(entry.getValue().getIdSlot())==idSlot) {// seleziona l'id slot gisuto
                if (entry.getValue().isDisponibile() == true) return true;// se disponibile torna vero
            }
        }
        return false;
    }

    public String getIdIstruttore() {
        return idIstruttore;
    }

    public void setIdIstruttore(String idIstruttore) {
        this.idIstruttore = idIstruttore;
    }

    public void setListaSlot(Map<Integer, Slot> listaSlot) {
        this.listaSlot = listaSlot;
    }

    public Map<Integer, Slot> getListaSlot() {
        return listaSlot;
    }

    public String stampaListaSlot(){
        String s="";
        for (Integer key: listaSlot.keySet()){//Serve per non stampare con le graffe e avere solo il valore e non la key, la formattazione e va fatto nel to string del tipo(sala.toString)
            s +="\t"+listaSlot.get(key);
        }
        return s;
    }
    @Override
    public String toString(){

        String s =
                "ID-Istruttore:\t" + idIstruttore + "\n" +
                        "\tLista Slot Orari:\n" + stampaListaSlot();
        return s;
    }
}
