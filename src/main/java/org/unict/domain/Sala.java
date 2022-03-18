package org.unict.domain;

import java.io.*;
import java.util.*;

public class Sala {
    private final String idSala;
    private final Map<String, Slot> mapSlot;
    private List<String> listaAttrezzi;

    public Sala(String idSala){
        this.idSala = idSala;
        this.mapSlot = loadSlot();
        this.listaAttrezzi = new LinkedList<>();
    }

    //CASO D'USO DI AVVIMENTO
    //Caricamento degli slot da file
    public Map<String, Slot>  loadSlot(){
        Map<String, Slot> map=new TreeMap<>();
        String dataOra;
        boolean disponibilita=true;
        Slot s;
        try {
            //System.out.println("sono dentro loadSlot");
            BufferedReader br1 = new BufferedReader(new FileReader("slot.txt"));
            dataOra = br1.readLine();
            while (dataOra != null) {
                s = new Slot(dataOra, disponibilita);
                s.setIdSlot(dataOra);
                s.setDisponibile(disponibilita);
                map.put(s.getIdSlot(), s);
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

        for (Map.Entry<String, Slot> entry : this.mapSlot.entrySet()) {
            if (entry.getValue().getDisponibile()) {
                s.put(entry.getValue().getIdSlot(), entry.getValue());
            }
        }
        return s;
    }

    public void setOccupato(String idSlot){
        this.mapSlot.get(idSlot).setDisponibile(false);
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

    public Map<String, Slot> getListaSlot() {
        return mapSlot;
    }

    public void setListaAttrezzi(List<String> listaAttrezzi) {
        this.listaAttrezzi = listaAttrezzi;
    }

    //STAMPA
    public String stampaListaSlot () {
        StringBuilder s = new StringBuilder();

        for (String key : mapSlot.keySet()) {
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
