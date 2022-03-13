package org.unict.domain;

import java.util.*;

public class Attrezzo{
    String idAttrezzo;
    List<String> listaSaleDiAttrezzo;

    public Attrezzo(String idAttrezzo, List<String> listaSaleDiAttrezzo){
        this.idAttrezzo = idAttrezzo;
        this.listaSaleDiAttrezzo = listaSaleDiAttrezzo;
    }

    //GET E SET STANDARD
    public List<String> getListaSaleDiAttrezzo() {
        return listaSaleDiAttrezzo;
    }

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    //STAMPA
    public String stampaListaSale() {
        StringBuilder s = new StringBuilder();
        for (Object l : listaSaleDiAttrezzo) {
            s.append("\t\t").append(l).append("\n");
        }
        //s=s.substring(0, s.length()-3);//rimuove l'ultimo ", "
        return s.toString();
    }

    @Override
    public String toString() {
        return "ID-Attrezzo: " + idAttrezzo + "\n" + "\tLista Sale:\n" + stampaListaSale();
    }
}
