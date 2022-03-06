package org.unict;

import java.util.ArrayList;
import java.util.*;
import java.util.Map;

public class Sala {
    private String idSala;
    //non deve avere il numero degli attrezzi, perchè implementi via codice la ricerca del numero di occorrenze presente nella lista
    //LinkedList<Attrezzo> listaAttrezzi;
    //ciascuna sala non ha un id slot, ma ha una lista di idslot
    Map<String, Slot> listaSlot;
    List<String> listaAttrezzi;

    public Sala(String idSala, Map<String, Slot> listaSlot, List listaAttrezzi){
        this.idSala = idSala;
        this.listaSlot = listaSlot;                                //NON E NEW altimenti crea una cosa vuolta invece devi inserire quello che passi
        this.listaAttrezzi = listaAttrezzi;
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

    public Map<String, Slot> getListaSlot() {
        return listaSlot;
    }

    public void setListaSlot(Map<String, Slot> listaSlot) {
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
       for (Map.Entry<String, Slot> entry : listaSlot.entrySet()) {
           //s+="Sonodentroilfor";
           s += entry.getValue().getIdSlot() +"-" +entry.getValue().isDisponibile() +", ";
       }
       return s;
   }
    public String stampaListaAtrezzi(){
        String s="";
        /*+ " - " + entry.getValue().get()*/
        // Fabiola ti serve per il to string di lista sala
        for (String l:listaAttrezzi) {
            s += l +", ";
        }
        return s;
    }


    //NON FUNZIONA IL TOSTRING DI LISTASLOT E LISTA ATTREZZI
    @Override
    public String toString(){

        String s =      "ID-Sala: " + idSala + "\n" +
                        "\tLista Slot: " + stampaListaSlot()+ "\n" +
                        "\tLista Attrezzi della sala: " + stampaListaAtrezzi() + "\n";
        return s;
    }


}
