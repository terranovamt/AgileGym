package org.unict;

import java.util.HashMap;
import java.util.Map;

public class Agilegym {
    //attributes
    private int n= 5;
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
        loadSale();
        loadIstruttore();
        createAttrezzi();


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
                this.corsoCorrente.inserisciLezione(idLezione, idSlot, s, i);
                System.out.println("Lezione Inserita");
            }
            else
                System.out.println("Sala occupata");



    }
    public  void conferma(){
        if(corsoCorrente != null){
            this.elencoCorsi.put(corsoCorrente.getIdCorso(), corsoCorrente);
            System.out.println("Il corso e'stato inserito!");
        }
    }

    public void loadSale(){
        int i;
        Sala[] s = new Sala[n];
        for(i=0; i<=n; i++){

            s[i] = new Sala(String.valueOf(i));
            this.elencoSaleDisponibili.put(String.valueOf(i), s[i]);

        }
        System.out.println("Elenco Sale caricato!");
    }
    public void loadIstruttore(){
        int j;
        Istruttore[] i = new Istruttore[n];
        Slot slot = new Slot();
        for(j=0; j<=n; j++){

            i[j] = new Istruttore(String.valueOf(j),  slot);
            this.elencoIstruttoriDisponibili.put(String.valueOf(i), i[j]);

        }
        System.out.println("Elenco Istruttori caricato!");

    }
    public void createAttrezzi(){
        int i;
        int numAttrezzi = (int) Math.random();
        Attrezzo[] a = new Attrezzo[n];
        for(i=0; i<=n; i++){

            a[i] = new Attrezzo(String.valueOf(i),  numAttrezzi);
        }
        System.out.println("Attrezzi creati!");

    }





}
