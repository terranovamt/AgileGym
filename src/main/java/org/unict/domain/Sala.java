package org.unict.domain;

import java.io.*;
import java.util.*;

public class Sala {
    private  String idSala;
    private  Map<String, Boolean> mapSlot;
    private List<String> listaAttrezzi;

    public Sala(String idSala){
        this.idSala = idSala;
        this.mapSlot = loadSlot();
        this.listaAttrezzi = new LinkedList<>();
    }

    //CASO D'USO DI AVVIMENTO
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
    //Ricerca nella listaSlot se l'attributo disponibilità è settato su falso
    public  List<String> getSlotDisponibili(){
        List<String> s=new LinkedList<>();

        for (Map.Entry<String, Boolean> entry : this.mapSlot.entrySet()) {
            if (entry.getValue()) {
                s.add(entry.getKey());
            }
        }
        return s;
    }

    public void setOccupato(String idSlot){
        this.mapSlot.replace(idSlot,false);
    }

    //UC2
    public int getNumAttrezzi(String idAttrezzo){
        int count=0;
        for (String s :listaAttrezzi){
            if(s.equals(idAttrezzo)){
                count++;
            }
        }
        return count;
    }

    //GET E SET STANDARD
    public String getIdSala() {
        return idSala;
    }

    public void setListaAttrezzi(List<String> listaAttrezzi) {
        this.listaAttrezzi = listaAttrezzi;
    }

    //STAMPA
    private String stampaData(String slot){
        String str = "", giorno, ora;
        giorno = switch (Integer.parseInt(String.valueOf(slot.charAt(0)))) {
            case 1 -> "LUNEDI' ore ";
            case 2 -> "MARTEDI' ore ";
            case 3 -> "MERCOLEDI' ore ";
            case 4 -> "GIOVEDI ore ";
            case 5 -> "VENERDI ore ";
            case 6 -> "SABATO ore ";
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
            if (mapSlot.get(key))dis="  Disponibile";else dis="  Occupato";
            s.append("\t").append(stampaData(key)).append(dis).append("\n");
        }
        return s.toString();
    }

    public String stampaListaAttrezzi() {
        StringBuilder s = new StringBuilder();
        List<String> lista = new ArrayList<>();

        for (String l : listaAttrezzi) {
            if (!lista.contains(l)) {
                lista.add(l);
                int n = getNumAttrezzi(l);
                s.append("\t\t n").append(n).append(" ").append(l).append("\n");
            }
        }
        return s.toString();
    }

    @Override
    public String toString(){
        return "ID-Sala: " + idSala + "\n" +
                        "\tLista Attrezzi della sala:\n " + stampaListaAttrezzi() + "\n" +
                        "\tLista Slot Orari: \n" + stampaListaSlot()+ "\n";
    }
}
