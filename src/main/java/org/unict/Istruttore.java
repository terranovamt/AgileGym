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

    public String getIstruttoreDiponibili(int idSlot){

        String s="";

        for (Map.Entry<Integer, Slot> entry : listaSlot.entrySet()) {
            if (Integer.parseInt(entry.getValue().getIdSlot())==idSlot) {
                s += this.idIstruttore+ ", ";
            }
        }
        return s;

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
        for (Map.Entry<Integer, Slot> entry : listaSlot.entrySet()) {
            //s+="Sonodentroilfor";
            s += entry.getValue().getIdSlot() +"-" +entry.getValue().isDisponibile() +", ";
        }
        s=s.substring(0, s.length()-2);//rimuove l'ultimo ", "
        return s;
    }
    @Override
    public String toString(){

        String s =
                "ID-Istruttore:\t" + idIstruttore + "\n" +
                        "\tLista Slot Orari:\t" + stampaListaSlot()+ "\n";
        return s;
    }
}
