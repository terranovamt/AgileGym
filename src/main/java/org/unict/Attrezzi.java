package org.unict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attrezzi {
    private String idAttrezzo;
    private Map<String, Sala> elencoSale;


    public Attrezzi(String idAttrezzo){
        this.idAttrezzo = idAttrezzo;
    }

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }
    public  List<Sala> getSaleDisponibili(String idSala){
        List<Sala> salaList = new ArrayList<>();
        salaList.addAll(elencoSale.values());
        return salaList;
    }

}
