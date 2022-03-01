package org.unict;

import java.util.ArrayList;
import java.util.*;
import java.util.Map;

public class Sala {

    private String idSala;
    private int numAttrezzi;
    LinkedList<Attrezzo> listaAttrezzi;
    //ciascuna sala non ha un id slot, ma ha una lista di idslot
    LinkedList<Slot> listaSlot;

    public Sala(String idSala, int numattrezzi, LinkedList<Slot> listaSlot, LinkedList<Attrezzo> listaAttrezzi){
        this.idSala = idSala;
        this.numAttrezzi = numattrezzi;
        this.listaSlot = new LinkedList<Slot>();
        this.listaAttrezzi = new LinkedList<Attrezzo>();
    }

    public int getNumAttrezzi() {
        return numAttrezzi;
    }

    public void setNumAttrezzi(int numAttrezzi) {
        this.numAttrezzi = numAttrezzi;
    }

    public LinkedList<Attrezzo> getListaAttrezzi() {
        return listaAttrezzi;
    }

    public void setListaAttrezzi(LinkedList<Attrezzo> listaAttrezzi) {
        this.listaAttrezzi = listaAttrezzi;
    }

    public String getIdSala() {

        return idSala;
    }
    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public LinkedList<Slot> getListaSlot() {
        return listaSlot;
    }

    public void setListaSlot(LinkedList<Slot> listaSlot) {
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

    public String stampaListaAttrezzi(){
        String stringa="";
        for(Attrezzo a:listaAttrezzi){
            stringa += a.toString();
        }
        return stringa;
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
                        "ID-Sala:\t" + idSala + "\n" +
                        "Lista Slot:\t" + stampaListaSlot() + "\n" +
                                "Numero Attrezzi:\t" + numAttrezzi +
                        "Lista Attrezzi della sala:\t " + stampaListaAttrezzi() + "\n";
        return s;
    }


}
