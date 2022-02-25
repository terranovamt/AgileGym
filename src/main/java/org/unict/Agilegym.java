package org.unict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Agilegym {
    //attributi
    private static  Agilegym agilegym;
    private Corso corsoCorrente;
    private Map<String, Corso> elencoCorsi;
    private Map<String, Attrezzi> elencoAttrezzi;
    private Map<String, Istruttore> elencoIstruttori;

    //constructor
    private Agilegym(){
        this.elencoCorsi = new HashMap<>();
        this.elencoAttrezzi = new HashMap<>();
        this.elencoIstruttori = new HashMap<>();

    }

    public static Agilegym getInstance(){
        if (agilegym == null)
            agilegym = new Agilegym();
        else
            System.out.println("Instanza già creata");

        return agilegym;
    }

    public void inserisciCorso(String idCorso, String nomeCorso, String livello, String focus){
        this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus);
        System.out.println("Corso inserito");
    }





}
