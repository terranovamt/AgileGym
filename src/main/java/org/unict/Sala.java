package org.unict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sala {

    private String idSala;
    private Map<Integer, FasciaOraria> elencoFasceOrarie;

    public Sala(String idSala){
        this.idSala = idSala;

    }

    public List<FasciaOraria> getFasceOrarieDisponibili(FasciaOraria fo, Sala s){
        List<FasciaOraria> orariaList = new ArrayList<>();
        orariaList.addAll(elencoFasceOrarie.values());
        return  orariaList;
    }


}
