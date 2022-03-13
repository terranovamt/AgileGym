package org.unict.domain;

import java.io.*;
import java.util.*;

public class Istruttore {
    private final String idIstruttore;
    private final Map<Integer, Slot> listaSlot; //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

    public Istruttore(String idIstruttore){
         this.idIstruttore = idIstruttore;
         this.listaSlot = loadSlot();
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
                s.setDataOra(dataora);
                s.setDisponibile(disponibilita);
                map.put(Integer.parseInt(dataora), s);
                dataora = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt DI ISTRUTTORE\n" );
            System.exit(-2);
        }
        return map;
    }

    public boolean isIstruttoreDiponibili(int dataora){
        for (Map.Entry<Integer, Slot> entry : listaSlot.entrySet()) { //scorre gli slot per l'istuttore corrente
            if (Integer.parseInt(entry.getValue().getDataora())==dataora) {// seleziona l'id slot gisuto
                if (entry.getValue().isDisponibile()) return true;// se disponibile torna vero
            }
        }
        return false;
    }

    public void setOccupato(String s){
        Slot c=this.listaSlot.get(Integer.parseInt(s));
                c.setDisponibile(false);
    }

    //GET E SET STANDARD
    public String getIdIstruttore() {
        return idIstruttore;
    }

    //STAMPA
    public String stampaListaSlot(){
        StringBuilder s= new StringBuilder();
        for (Integer key: listaSlot.keySet()){
            s.append("\t").append(listaSlot.get(key));
        }
        return s.toString();
    }
    @Override
    public String toString(){
        return "ID-Istruttore:\t" + idIstruttore + "\n" + "\tLista Slot Orari:\n" + stampaListaSlot();
    }
}
