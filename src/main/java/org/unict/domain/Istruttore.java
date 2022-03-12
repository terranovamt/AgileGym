package org.unict.domain;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Istruttore {

    private String idIstruttore;
    private Map<Integer, Slot> listaSlot; //primo numero giorno della settimana e i successivi due indicano l'ora. N.B. 209= martedi' ore 9 am

        //l'istruttore non ha uno slot, ma ha una lista di slot in cui lavora
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
                s.setDataora(dataora);
                s.setDisponibile(disponibilita);
                map.put(Integer.parseInt(dataora), s);
                dataora = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt\n" );
            System.exit(-3);
        }
        return map;
    }

    public boolean isIstruttoreDiponibili(int dataora){
        for (Map.Entry<Integer, Slot> entry : listaSlot.entrySet()) { //scorre gli slot per l'istuttore corrente
            if (Integer.parseInt(entry.getValue().getDataora())==dataora) {// seleziona l'id slot gisuto
                if (entry.getValue().isDisponibile() == true) return true;// se disponibile torna vero
            }
        }
        return false;
    }

    public void setOccupato(String s){
        Slot c=this.listaSlot.get(Integer.parseInt(s));
                c.setDisponibile(false);
    }

    public String getIdIstruttore() {
        return idIstruttore;
    }

    public Map<Integer, Slot> getListaSlot() {
        return listaSlot;
    }

    public void setIdIstruttore(String idIstruttore) {
        this.idIstruttore = idIstruttore;
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
    @Override
    public String toString(){

        String s =
                    "ID-Istruttore:\t" + idIstruttore + "\n" +
                    "\tLista Slot Orari:\n" + stampaListaSlot();
        return s;
    }
}
