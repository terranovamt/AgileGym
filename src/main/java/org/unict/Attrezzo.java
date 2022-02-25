package org.unict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Attrezzo {
    private String idAttrezzo;


    public Attrezzo(String idAttrezzo){
        this.idAttrezzo = idAttrezzo;
    }

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }
    /*public  List<Sala> getSaleDisponibili(String idSala){
        List<Sala> salaList = new ArrayList<>();
        salaList.addAll(elencoSale.values());
        return salaList;
    }*/

}
