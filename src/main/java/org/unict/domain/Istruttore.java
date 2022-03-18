package org.unict.domain;

import java.io.*;
import java.util.*;

public class Istruttore {
    private final String idIstruttore;
    private final Map<String, Slot> listaSlot; //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

    public Istruttore(String idIstruttore){
         this.idIstruttore = idIstruttore;
         this.listaSlot = loadSlot();
    }

    //CASO D'USO DI AVVIEMENTO
    public Map<String, Slot>  loadSlot(){
        Map<String, Slot> map=new TreeMap<>();
        String idSlot;
        boolean disponibilita=true;
        Slot s;
        try {
            //System.out.println("sono dentro loadSlot");
            BufferedReader br1 = new BufferedReader(new FileReader("slot.txt"));
            idSlot = br1.readLine();
            while (idSlot != null) {
                s = new Slot(idSlot, disponibilita);
                s.setIdSlot(idSlot);
                s.setDisponibile(disponibilita);
                map.put(idSlot, s);
                idSlot = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt DI ISTRUTTORE\n" );
            System.exit(-2);
        }
        return map;
    }

    //UC1
    public boolean isDisponibile(String idSlot){
        for (Map.Entry<String, Slot> entry : listaSlot.entrySet()) { //scorre gli slot per l'istruttore corrente
            if (entry.getValue().getIdSlot().equals(idSlot)) {// seleziona l'id slot giusto
                if (entry.getValue().getDisponibile()) return true;// se disponibile torna vero
            }
        }
        return false;
    }

    public void setOccupato(String idSlot){
        this.listaSlot.get(idSlot).setDisponibile(false);
    }

    //GET E SET STANDARD
    public String getIdIstruttore() {
        return idIstruttore;
    }

    //STAMPA
    public String stampaListaSlot(){
        StringBuilder s= new StringBuilder();
        for (String key: listaSlot.keySet()){
            s.append("\t").append(listaSlot.get(key));
        }
        return s.toString();
    }
    @Override
    public String toString(){
        return "ID-Istruttore:\t" + idIstruttore + "\n" + "\tLista Slot Orari:\n" + stampaListaSlot();
    }
}
