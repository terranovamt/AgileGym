package org.unict;

import java.util.HashMap;
import java.util.Map;

public class Agilegym {
    //attributes
    private static  Agilegym agilegym;
    private Corso corsoCorrente;
    private String idAttrezzo;
    private Map<String, Corso> elencoCorsi;
    private Map<String, Sala> elencoSaleDisponibili;
    private Map<String, Istruttore> elencoIstruttoriDisponibili;
    private Map<String, Lezione>  elencoLezioni;


    //constructor
    private Agilegym(){
        this.elencoCorsi = new HashMap<>();
        this.elencoIstruttoriDisponibili = new HashMap<>();
        this.elencoLezioni = new HashMap<>();
        this.elencoSaleDisponibili = new HashMap<>();

    }

    public static Agilegym getInstance(){
        if (agilegym == null)
            agilegym = new Agilegym();
        else
            System.out.println("Instanza già creata");

        return agilegym;
    }

    public void inserisciCorso(String idCorso, String nomeCorso, String livello, String focus, String idAttrezzo, int idSlot){
        this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus);
        System.out.println("Corso inserito");

    }

    public void inserisciLezione(String idLezione, String idAttrezzo, int idSlot){
        Sala s = elencoSaleDisponibili.get(idAttrezzo);
        Istruttore i = elencoIstruttoriDisponibili.get(idSlot);
            if(s != null){
                this.corsoCorrente.inserisciLezione(idLezione, idSlot ,s, i);
                System.out.println("Lezione Inserita");
            }
            else
                System.out.println("Sala occupata");

        System.out.println("Corso inserito");

    }






}
