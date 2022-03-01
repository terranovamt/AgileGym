package org.unict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Attrezzo {
    private String idAttrezzo;
    //l'attrezzo non ha il numero di attrezzi, ce l'ha sala
    //private int numAttrezzi;


    public Attrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
        //this.numAttrezzi= numAttrezzi;
    }

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }

   /* public int getNumAttrezzi() {
        return numAttrezzi;
    }

    public void setNumAttrezzi(int numAttrezzi) {
        this.numAttrezzi = numAttrezzi;//caricamente da file
    }*/

    /*public  List<Sala> getSaleDisponibili(String idSala){
        List<Sala> salaList = new ArrayList<>();
        salaList.addAll(elencoSale.values());
        return salaList;
    }*/

    @Override
    public String toString(){
        String a =
                "ID-Attrezzo:\t" + idAttrezzo + "\n";
                        //"Num.Attrezzi:\t" + numAttrezzi;
        return a;
    }
}
