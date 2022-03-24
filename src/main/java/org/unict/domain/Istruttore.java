package org.unict.domain;

import java.io.*;
import java.util.*;

public class Istruttore {
    private  String idIstruttore;
    private  Map<String, Boolean> mapSlot; //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

    public Istruttore(String idIstruttore){
         this.idIstruttore = idIstruttore;
         this.mapSlot = loadSlot();
    }

    //CASO D'USO DI AVVIAMENTO
    //Caricamento degli slot da file
    public Map<String, Boolean>  loadSlot(){
        Map<String, Boolean> map=new TreeMap<>();
        String idSlot;
        try {
            BufferedReader br1 = new BufferedReader(new FileReader("slot.txt"));
            idSlot = br1.readLine();
            while (idSlot != null) {
                map.put(idSlot, true);
                idSlot = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt IN SALA\n" );
            System.exit(-1);
        }
        return map;
    }
    //UC1
    public  boolean isDisponibile(String idSlot){
        return this.mapSlot.get(idSlot);

    }

    public void setOccupato(String idSlot){
        this.mapSlot.replace(idSlot,false);
    }

    //GET E SET STANDARD
    public String getIdIstruttore() {
        return idIstruttore;
    }

    public Map<String, Boolean> getMapSlot() {
        return mapSlot;
    }

    //STAMPA
    private String stampaData(String slot){
        String str = "", giorno, ora;
        giorno = switch (Integer.parseInt(String.valueOf(slot.charAt(0)))) {
            case 1 -> "LUNEDI' \tore ";
            case 2 -> "MARTEDI'\tore ";
            case 3 -> "MERCOLEDI'\tore ";
            case 4 -> "GIOVEDI'\tore ";
            case 5 -> "VENERDI'\tore ";
            default -> "";
        };
        ora= slot.charAt(1)+String.valueOf((slot.charAt(2)));
        str+=giorno+ora+":00";
        return str;
    }

    public String stampaListaSlot(){
        String dis;
        StringBuilder s = new StringBuilder();
        for (String key: mapSlot.keySet()){
            if (mapSlot.get(key))dis=" Disponibile";else dis=" Occupato";
            s.append("\t").append(stampaData(key)).append(dis).append("\n");
        }
        return s.toString();
    }

    @Override
    public String toString(){
        return "ID-Istruttore:\t" + idIstruttore + "\n" + "\tLista Slot Orari:\n" + stampaListaSlot();
    }
}
