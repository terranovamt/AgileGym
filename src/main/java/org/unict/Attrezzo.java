package org.unict;

import java.util.*;

public class Attrezzo {
    private String idAttrezzo;
    //l'attrezzo non ha il numero di attrezzi, ce l'ha sala
    //private int numAttrezzi;      //l'attrezzo deve avere il numero di attrezi di quel tipo
    //attrezzo deve avere una lista di sale, perchè quando faccio l'inserisci corso faccio il find attrezzo nella lista di attrezzi di attrezzo

    private Map<String, Sala> listaSalediAttrezzo;

    public Attrezzo(String idAttrezzo, Map<String, Sala> listaSalediAttrezzo) {
        this.idAttrezzo = idAttrezzo;
        this.listaSalediAttrezzo = new HashMap<>();
    }


    public void setListaSalediAttrezzo(Map<String, Sala> listaSalediAttrezzo) {
        this.listaSalediAttrezzo = listaSalediAttrezzo;
    }

    public Map<String, Sala> getListaSalediAttrezzo() {
        return listaSalediAttrezzo;
    }

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }



    @Override
    public String toString(){
        String a =
                "ID-Attrezzo:\t" + idAttrezzo + "\n" +
                        "ListaSale:\n\t" + listaSalediAttrezzo;
        return a;
    }
}
