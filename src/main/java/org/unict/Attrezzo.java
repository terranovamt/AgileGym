package org.unict;

import java.util.*;

public class Attrezzo {
    private String idAttrezzo;
    //l'attrezzo non ha il numero di attrezzi, ce l'ha sala
    //private int numAttrezzi;      //l'attrezzo deve avere il numero di attrezi di quel tipo
    //attrezzo deve avere una lista di sale, perchè quando faccio l'inserisci corso faccio il find attrezzo nella lista di attrezzi di attrezzo

    private List listaSalediAttrezzo;

    public Attrezzo(String idAttrezzo, List listaSalediAttrezzo) {
        this.idAttrezzo = idAttrezzo;
        this.listaSalediAttrezzo = listaSalediAttrezzo;
    }


    public void setListaSalediAttrezzo(List listaSalediAttrezzo) {
        this.listaSalediAttrezzo = listaSalediAttrezzo;
    }

    public List getListaSalediAttrezzo() {
        return listaSalediAttrezzo;
    }

    public String getIdAttrezzo() {
        return idAttrezzo;
    }

    public void setIdAttrezzo(String idAttrezzo) {
        this.idAttrezzo = idAttrezzo;
    }

   /* public String stampaListaSale(){
        String s="";
        for (String str: listaSalediAttrezzo) {
            s+= str.toString();/*+ " - " + entry.getValue().get()*/; // Fabiola ti serve per il to string di lista sala
        //}
       // return s;
   // }*/

    public String stampaListaSale(){
        String s="";
        for (Object l:listaSalediAttrezzo) {
            s +="\t\t"+ l +", \n";
        }
        s=s.substring(0, s.length()-3);//rimuove l'ultimo ", "
        return s;
    }
    @Override
    public String toString(){
        String a =
                "ID-Attrezzo: " + idAttrezzo + "\n" +
                        "\tLista Sale:\n" + stampaListaSale()+"\n";
        return a;
    }
}
