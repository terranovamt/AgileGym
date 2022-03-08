package org.unict;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.Map;

public class Sala {
    private String idSala;
    //non deve avere il numero degli attrezzi, perchè implementi via codice la ricerca del numero di occorrenze presente nella lista
    Map<Integer, Slot> listaSlot;
    List<String> listaAttrezzi;

    public Sala(String idSala){
        this.idSala = idSala;
        this.listaSlot = loadSlot();
        this.listaAttrezzi = new ArrayList<>();
    }

    public Map<Integer, Slot>  loadSlot(){
        Map<Integer, Slot> map=new HashMap<>();
        String dataora;
        boolean disponibilita=true;
        Slot s;
        try {
            //System.out.println("sono dentro loadSlot");
            BufferedReader br1 = new BufferedReader(new FileReader("slot.txt"));
            dataora = br1.readLine();
            while (dataora != null) {
                s = new Slot(dataora, disponibilita);
                s.setDataora(dataora);
                s.setDisponibile(disponibilita);
                map.put(Integer.parseInt(s.getDataora()), s);
                dataora = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt\n" );
            System.exit(-3);
        }
        return map;
    }

    public  Map<Integer, Slot> getSlotDisponibili(){
        Map<Integer, Slot> s=new HashMap<>();

        for (Map.Entry<Integer, Slot> entry : this.listaSlot.entrySet()) {
            if (entry.getValue().isDisponibile()==true) {
                s.put(Integer.parseInt(entry.getValue().getDataora()), entry.getValue());
            }
        }
        return s;
    }

    public void setOccupato(String s){
        this.listaSlot.get(Integer.parseInt(s)).setDisponibile(false);
    }

    public String getIdSala() {

        return idSala;
    }

    public List getListaAttrezzi() {
        return listaAttrezzi;
    }

    public Map<Integer, Slot> getListaSlot() {
        return listaSlot;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public void setListaAttrezzi(List listaAttrezzi) {
        this.listaAttrezzi = listaAttrezzi;
    }

    public void setListaSlot(Map<Integer, Slot> listaSlot) {
        this.listaSlot = listaSlot;
    }

   public String stampaListaSlot(){
       String s="";
       for (Integer key: listaSlot.keySet()){
           s +="\t"+listaSlot.get(key);
       }
       return s;
   }

    public String stampaListaAtrezzi(){
        String s="";
        /*+ " - " + entry.getValue().get()*/
        // Fabiola ti serve per il to string di lista sala
        for (String l:listaAttrezzi) {
            s +="\t\t"+ l +"\n";
        }
        //s=s.substring(0, s.length()-3);//rimuove l'ultimo ", "
        return s;
    }

    @Override
    public String toString(){

        String s =      "ID-Sala: " + idSala + "\n" +
                        "\tLista Attrezzi della sala:\n " + stampaListaAtrezzi() + "\n" +
                        "\tLista Slot Orari: \n" + stampaListaSlot()+ "\n";
        return s;
    }
}
