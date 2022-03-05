package org.unict;



///caso d'uso di avviamento, caricamento sale, carimento lista attrezzi, caricamento slot e caricamnento istruttori

import javax.management.StringValueExp;
import java.util.*;
import java.io.*;

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
    private Map<String, Slot> listaSlot;
    private Map<String, Attrezzo> listaAttrezzi;


    //constructor
    private Agilegym() throws IOException {
        this.elencoCorsi = new HashMap<>();
        this.elencoIstruttoriDisponibili = new HashMap<>();
        this.elencoLezioni = new HashMap<>();
        this.elencoSaleDisponibili = new HashMap<>();
        this.listaSlot  = new HashMap<>();
        this.listaAttrezzi  = new HashMap<>();

       // loadSale();
       // loadIstruttore();
        //loadIstruttore();
        //loadListaAttrezzi();


    }

    public static Agilegym getInstance(){
        if (agilegym == null)
            try{
                agilegym = new Agilegym();

            }catch (IOException e){
                System.out.println("errore1");
            }
        else
            System.out.println("Instanza già creata");

        return agilegym;
    }

    /*public void inserisciLezione(String idLezione, String idAttrezzo, int  idSlot){
        Sala s = elencoSaleDisponibili.get(idAttrezzo);
        Istruttore i = elencoIstruttoriDisponibili.get(idSlot);
            if(s != null){
                this.corsoCorrente.inserisciLezione(idLezione, idAttrezzo, idSlot);
                System.out.println("Lezione Inserita");
            }
            else
                System.out.println("Sala occupata");
    }*/

    public  void conferma(){
        if(corsoCorrente != null){
            this.elencoCorsi.put(corsoCorrente.getIdCorso(), corsoCorrente);
            System.out.println("CONFERMA INSERIMENTO CORSO");
        }
    }

    public  Map<String, Slot> loadSlot(){
        String idSlot;
        boolean disponibilita;
        int i = 1;
        Slot [] s = new Slot[n];
        try {
            System.out.println("sono dentro loadSlot");
            BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\slot.txt"));

            //BufferedReader br1 = new BufferedReader(new FileReader("C:\\Utenti\\Fabiola Marchì\\slot.txt"));
            //id = br1.readLine();
            for(int j= 0; j<5; j++) {
                idSlot = br1.readLine();
                while (idSlot != null) {
                    disponibilita = Boolean.parseBoolean(br1.readLine());
                    s[j] =  new Slot(idSlot, disponibilita);
                    s[j].setIdSlot(idSlot);
                    s[j].setDisponibile(disponibilita);
                    this.listaSlot.put(String.valueOf(i), s[j]);
                    idSlot = br1.readLine();
                    i++;
                }
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt\n" );
            System.exit(-3);
        }
        //System.out.println("Ecco la lista degli slot orari:\n\n");
        //System.out.println(listaSlot);
        return listaSlot;
    }

    public List caricamentoListaAttrezziSala1(){
        List listaAttrezziSala1 = new ArrayList();
        String idAttrezzo;
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\AttrezziSala1.txt"));
                idAttrezzo = br.readLine();
                while (idAttrezzo != null) {
                    listaAttrezziSala1.add(idAttrezzo);
                    idAttrezzo = br.readLine();
                }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE AttrezziSala1.txt\n" );
            System.exit(-5);
        }
       // System.out.println("ListaAttrezziSala1:\n" +listaAttrezziSala1 );

        return listaAttrezziSala1;
    }
    public List caricamentoListaAttrezziSala2(){
        List listaAttrezziSala2 = new ArrayList();
        String idAttrezzo;
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\AttrezziSala2.txt"));
            idAttrezzo = br.readLine();
            while (idAttrezzo != null) {
                listaAttrezziSala2.add(idAttrezzo);
                idAttrezzo = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE AttrezziSala2.txt\n" );
            System.exit(-6);
        }
        return listaAttrezziSala2;
    }
    public List caricamentoListaAttrezziSala3(){
        List listaAttrezziSala3 = new ArrayList();
        String idAttrezzo;
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\AttrezziSala3.txt"));
            idAttrezzo = br.readLine();
            while (idAttrezzo != null) {
                listaAttrezziSala3.add(idAttrezzo);
                idAttrezzo = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE AttrezziSala3.txt\n" );
            System.exit(-7);
        }
        return listaAttrezziSala3;
    }
    public List caricamentoListaAttrezziSala4(){
        List listaAttrezziSala4 = new ArrayList();
        String idAttrezzo;
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\AttrezziSala4.txt"));
            idAttrezzo = br.readLine();
            while (idAttrezzo != null) {
                listaAttrezziSala4.add(idAttrezzo);
                idAttrezzo = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE AttrezziSala4.txt\n" );
            System.exit(-8);
        }
        return listaAttrezziSala4;
    }
    public List caricamentoListaAttrezziSala5(){
        List listaAttrezziSala5 = new ArrayList();
        String idAttrezzo;
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\AttrezziSala5.txt"));
            idAttrezzo = br.readLine();
            while (idAttrezzo != null) {
                listaAttrezziSala5.add(idAttrezzo);
                idAttrezzo = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE AttrezziSala5.txt\n" );
            System.exit(-9);
        }
        return listaAttrezziSala5;
    }
    public Map<String, Sala> loadSale() {
        Sala sala1 = new Sala("Sala1", loadSlot(), caricamentoListaAttrezziSala1());
        Sala sala2 = new Sala("Sala2", loadSlot(), caricamentoListaAttrezziSala2());
        Sala sala3 = new Sala("Sala3", loadSlot(), caricamentoListaAttrezziSala3());
        Sala sala4 = new Sala("Sala4", loadSlot(), caricamentoListaAttrezziSala4());
        Sala sala5 = new Sala("Sala5", loadSlot(), caricamentoListaAttrezziSala5());
        this.elencoSaleDisponibili.put("1", sala1);
        this.elencoSaleDisponibili.put("2", sala2);
        this.elencoSaleDisponibili.put("3", sala3);
        this.elencoSaleDisponibili.put("4", sala4);
        this.elencoSaleDisponibili.put("5", sala5);
        System.out.println("Elenco Sale Disponibili:\n");
        //System.out.println(elencoSaleDisponibili);
        /*elencoSaleDisponibili.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " = " + entry.getValue());
        });*/
        //System.out.println(this.elencoSaleDisponibili);
        //System.out.println(caricamentoListaAttrezziSala1());
        //System.out.println(loadSlot());
        //non stampa, ma si riempie
        return elencoSaleDisponibili;
    }
    public void loadAttrezzi() {
        //dal primo file si carica la lista di attrezzi che fa parte dell'oggetto sala,
        //dal secondo file si carica le sale
        String idAttrezzo;
        Map<String,Sala> listaSaleAttrezzo = loadSale();

        System.out.println("sono dentro loadListaAttrezzi\n\n");

        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\Attrezzi.txt"));
            idAttrezzo = br.readLine();
            //System.out.println(id);
            while (idAttrezzo != null){
                listaAttrezzi.put(idAttrezzo, new Attrezzo(idAttrezzo, listaSaleAttrezzo));
                idAttrezzo = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Attrezzi.txt\n" );
            System.exit(-10);
        }

        System.out.println("Ecco la lista degli attrezzi:\n" + listaAttrezzi );
        //funziona, ma non funzione il toString di sala

    }
    public void loadIstruttore(){
        String idIstruttore;
        Map<String, Slot> listaOrari = loadSlot();
        int i = 1;
        try{
            System.out.println("sono dentro load istruttore\n\n");
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\IdeaProjects\\AgileGym\\Istruttori.txt"));
            idIstruttore = br.readLine();
            while (idIstruttore != null){
                this.elencoIstruttoriDisponibili.put(String.valueOf(i), new Istruttore(idIstruttore, listaOrari));
                idIstruttore = br.readLine();
                i++;
            }
        }catch (IOException e){
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE istruttori.txt:\n" );
            System.exit(-11);
        }
        System.out.println("Ecco l'elenco degli istruttori disponibili:\n" +elencoIstruttoriDisponibili );
        //funziona, ma non funzione il toString di slot, c'è qualcosa che no va nella stamp adi elenco---disponibili

    }
    public void inserisciCorso() throws inserisciCorsoException {
        String idCorso;
        String nomeCorso;
        String livello;
        String focus;
        String idAttrezzo;
        Attrezzo attrezzoSelezionato;
        Map<String, Sala> saleDisponibili;
        LinkedList<Sala> sale;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Inserisci un corso: \n");
        try {
            System.out.println("Inserisci l'ID del corso: \n");
            idCorso = br.readLine();
            System.out.println("Inserisci il nome del corso: \n");
            nomeCorso = br.readLine();
            System.out.println("Inserisci il livello del corso: \n");
            livello = br.readLine();
            System.out.println("Inserisci il focus del corso: \n");
            focus = br.readLine();
            System.out.println("Inserisci l'ID dell'attrezzo del corso: \n");
            //l'amministatore inserisce l'id dell'attrezzo che vuole per quel corso, si cerca se quell'attrezzo
            //e' presente nella lista degli attrezzi, una volta trovato, si prende quell'attrezzo e lo si inserisce nel corso
            idAttrezzo = br.readLine();
            attrezzoSelezionato = listaAttrezzi.get(idAttrezzo);
            System.out.println(attrezzoSelezionato.toString());
            if (attrezzoSelezionato == null) {
                throw new inserisciCorsoException("Errore attrezzo inserito\n");
            } else {
                this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus, idAttrezzo);
                System.out.println("Corso inserito");
                System.out.println("Lista delle sale contenenti questo attrezzo: \n");
                System.out.println(attrezzoSelezionato.getListaSalediAttrezzo());
                //anche qui bisogna aggiustare il tostring, non stampa getlistasalediattrezzo
            }
        } catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:\n" );
            System.exit(-12);
        }

    }

    public boolean equals(Attrezzo a){
        return a.getIdAttrezzo() == idAttrezzo;
    }
    /*public List<Corso> getElencoCorsi() {
        List<Corso> listCorsi = new ArrayList<>();
        listCorsi.addAll(elencoCorsi.values());
        return listCorsi;
    }

    public Corso getCorsoCorrente() {
        return corsoCorrente;
    }

   /* public Corso getCorso() {
        return elencoCorsi;
    }

   /* @Override
    public String toString(){
        String stringa = corsoCorrente.getNome();
        return System.out.println(stringa);
    }*/

}
