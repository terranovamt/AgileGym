package org.unict.domain;

import java.io.*;
import java.util.*;

public class Sala {
    private final String idSala;
    private final Map<Integer, Slot> mapSlot;
    private List<String> listaAttrezzi;

    public Sala(String idSala){
        this.idSala = idSala;
        this.mapSlot = loadSlot();
        this.listaAttrezzi = new ArrayList<>();
    }

    //CASO D'USO DI AVVIMENTO
    //Caricamento degli slot da file
    public Map<Integer, Slot>  loadSlot(){
        //Map<Integer, Slot> map=new HashMap<>();
        Map<Integer, Slot> map=new TreeMap<>();
        String dataOra;
        boolean disponibilita=true;
        Slot s;
        try {
            //System.out.println("sono dentro loadSlot");
            BufferedReader br1 = new BufferedReader(new FileReader("slot.txt"));
            dataOra = br1.readLine();
            while (dataOra != null) {
                s = new Slot(dataOra, disponibilita);
                s.setDataOra(dataOra);
                s.setDisponibile(disponibilita);
                map.put(Integer.parseInt(s.getDataora()), s);
                dataOra = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt IN SALA\n" );
            System.exit(-1);
        }
        return map;
    }

    //UC1
    //Ricerca nella listaSlot se l'attributo disponibilità è settato su falso
    public  Map<String, Slot> getSlotDisponibili(){
        Map<String, Slot> s=new HashMap<>();

        for (Map.Entry<Integer, Slot> entry : this.mapSlot.entrySet()) {
            if (entry.getValue().getDisponibile()) {
                s.put(entry.getValue().getDataora(), entry.getValue());
            }
        }
        return s;
    }

    public void setOccupato(String s){
        this.mapSlot.get(Integer.parseInt(s)).setDisponibile(false);
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

    public Map<Integer, Slot> getListaSlot() {
        return mapSlot;
    }

    public void setListaAttrezzi(List<String> listaAttrezzi) {
        this.listaAttrezzi = listaAttrezzi;
    }

    //STAMPA
    public String stampaListaSlot () {
        StringBuilder s = new StringBuilder();

        for (Integer key : mapSlot.keySet()) {
            s.append("\t").append(mapSlot.get(key));
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
