package org.unict;

import java.util.ArrayList;
import java.util.*;
import java.util.Map;

public class Sala {
    private String idSala;
    //non deve avere il numero degli attrezzi, perchè implementi via codice la ricerca del numero di occorrenze presente nella lista
    //LinkedList<Attrezzo> listaAttrezzi;
    //ciascuna sala non ha un id slot, ma ha una lista di idslot
    Map<Integer, Slot> listaSlot;
    List<String> listaAttrezzi;

    public Sala(String idSala, Map<Integer, Slot> listaSlot, List listaAttrezzi){
        this.idSala = idSala;
        this.listaSlot = listaSlot;                                //NON E NEW altimenti crea una cosa vuolta invece devi inserire quello che passi
        this.listaAttrezzi = listaAttrezzi;
    }


    public  Map<String, Slot> getSlotDiponibili(){
        Map<String, Slot> s=new HashMap<>();

        for (Map.Entry<Integer, Slot> entry : listaSlot.entrySet()) {
            if (entry.getValue().isDisponibile()==true) {
                s.put(entry.getValue().getIdSlot(), entry.getValue());
            }
        }

        return s;

    }

    public List getListaAttrezzi() {
        return listaAttrezzi;
    }

    public void setListaAttrezzi(List listaAttrezzi) {
        this.listaAttrezzi = listaAttrezzi;
    }

    public String getIdSala() {

        return idSala;
    }
    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public Map<Integer, Slot> getListaSlot() {
        return listaSlot;
    }

    public void setListaSlot(Map<Integer, Slot> listaSlot) {
        this.listaSlot = listaSlot;
    }
    /*public boolean salaDisponibile(String idSala, int idSlot){
        if( this.idSala== idSala & )
    }



    /*public List<FasciaOraria> getFasceOrarieDisponibili(FasciaOraria fo, Sala s){
        List<FasciaOraria> orariaList = new ArrayList<>();
        orariaList.addAll(elencoFasceOrarie.values());
        return  orariaList;
    }*/

   /* public String stampaListaAttrezzi(){
        String stringa="";



        return stringa;
    }
    public String stampaListaSlot(){
        String stringa="";
        stringa +=listaSlot;
        return stringa;
    }
*/
   public String stampaListaSlot(){
       String s="";
       /*+ " - " + entry.getValue().get()*/
       // Fabiola ti serve per il to string di lista sala
       for (Map.Entry<Integer, Slot> entry : listaSlot.entrySet()) {
           //s+="Sonodentroilfor";
           s += entry.getValue().getIdSlot() +"-" +entry.getValue().isDisponibile() +", ";
       }
       s=s.substring(0, s.length()-2);//rimuove l'ultimo ", "
       return s;
   }
    public String stampaListaAtrezzi(){
        String s="";
        /*+ " - " + entry.getValue().get()*/
        // Fabiola ti serve per il to string di lista sala
        for (String l:listaAttrezzi) {
            s += l +", ";
        }
        s=s.substring(0, s.length()-2);//rimuove l'ultimo ", "
        return s;
    }


    //NON FUNZIONA IL TOSTRING DI LISTASLOT E LISTA ATTREZZI
    @Override
    public String toString(){

        String s =      "ID-Sala: " + idSala + "\n" +
                        "\tLista Slot: " + stampaListaSlot()+ "\n" +
                        "\tLista Attrezzi della sala:\t " + stampaListaAtrezzi() + "\n";
        return s;
    }


}
