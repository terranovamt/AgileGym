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
    List listaAttrezzi;

    public Sala(String idSala, Map<String, Slot> listaSlot, List listaAttrezzi){
        this.idSala = idSala;
        this.listaSlot = new HashMap<>();
        this.listaAttrezzi = new ArrayList();
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
//NON FUNZIONA IL TOSTRING DI LISTASLOT E LISTA ATTREZZI
    @Override
    public String toString(){

        String s =
                        "ID-Sala:\t" + idSala + "\n" +
                        "Lista Slot:\t" + listaSlot+ "\n" +
                        "Lista Attrezzi della sala:\t " + listaAttrezzi + "\n";
        return s;
    }


}
