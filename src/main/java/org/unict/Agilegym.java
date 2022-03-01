package org.unict;



///caso d'uso di avviamento, caricamento sale, carimento lista attrezzi, caricamento slot e caricamnento istruttori

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


    //constructor
    private Agilegym() throws IOException {
        this.elencoCorsi = new HashMap<>();
        this.elencoIstruttoriDisponibili = new HashMap<>();
        this.elencoLezioni = new HashMap<>();
        this.elencoSaleDisponibili = new HashMap<>();
        this.listaSlot  = new HashMap<>();
       // loadSale();
       // loadIstruttore();
        //loadIstruttore();
        loadListaAttrezzi();


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

    /*public void inserisciCorso(){
        //METTEVAMO ANCHE L'ID DELL'ATTREZZO E DELLO SLOT, MA IN CORSO NO, PERCHE'?
        String idCorso;
        String nomeCorso;
        String livello;
        String focus;
        Attrezzo attrezzo;
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
            //passare l'oggetto di tipo attrezzo
            System.out.println("Inserisci l'ID dell'attrezzo del corso: \n" );
            //l'amministatore inserisce l'id dell'attrezzo che vuole per quel corso, si cerca se quell'attrezzo
            //e' presente nella lista degli attrezzi, una volta trovato, si prende quell'attrezzo e lo si inserisce nel corso
            attrezzo.setIdAttrezzo(br.readLine());
            System.out.println("Inserisci il numero degli attrezzi del corso: \n");
            attrezzo.setNumAttrezzi(Integer.parseInt(br.readLine()));
            //System.out.println(corsoCorrente.toString());*/
           /* this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus, attrezzo);
            System.out.println("Corso inserito");
            System.out.println(corsoCorrente.toString());
        }catch(Exception e) {
            System.out.println(e);
        }



    }*/

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

    public LinkedList<Attrezzo> loadListaAttrezzi() {
        LinkedList<Attrezzo> listaAttrezzi = new LinkedList<Attrezzo>();
        //dal primo file si carica la lista di attrezzi che fa parte dell'oggetto sala,
        //dal secondo file si carica le sale
        String id2, idAttrezzo;
        //int numAttrezzi;
        System.out.println("sono dentro loadListaAttrezzi\n\n");

        try {
            BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\listaAttrezzi.txt"));
            idAttrezzo = br2.readLine();
            //System.out.println(id);
            while (idAttrezzo != null){
                //idAttrezzo = br2.readLine();
                //numAttrezzi = Integer.parseInt(br2.readLine());
                listaAttrezzi.add(new Attrezzo(idAttrezzo));
                idAttrezzo = br2.readLine();
            }
        }catch (IOException e) {
            System.out.println("erroreeeeeee2:\n" );
            System.exit(-2);
        }
        //per stampare la lista devi scorrerlaaaaaaaaaaaaa
        // System.out.println("Ecco la lista degli attrezzi:\n" + listaAttrezzi );
        for(Attrezzo a: listaAttrezzi){
            System.out.println("Ecco la lista attrezzi!!\n\n");

            System.out.println(a.toString());

        }

        return listaAttrezzi;
    }
    public  void loadSlot(){
        String idSlot;
        boolean disponibilita;
        int i = 1;
        Slot [] s = new Slot[n];
        try {
            System.out.println("sono dentro loadSlot");

            BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\slot.txt"));
            //id = br1.readLine();
            for(int j= 0; j<5; j++) {

                idSlot = br1.readLine();
                System.out.println(idSlot);

                //System.out.println(id);
                while (idSlot != null) {
                    //idSlot = br1.readLine();
                    disponibilita = Boolean.parseBoolean(br1.readLine());
                    System.out.println(disponibilita);
                    s[j] =  new Slot(idSlot, disponibilita);
                    System.out.println(s[j].toString());

                    //System.out.println(idSala);
                    //System.out.println(idSlot);

                    this.listaSlot.put(String.valueOf(i), s[j]);  //------DA IL PROBLEMA CHE SI PRENDE L'ULTIMO VALORE DI DISPONIBILITA CHE LEGGE DA FILE
                    idSlot = br1.readLine();
                    i++;
                }
            }
        }catch (IOException e) {
            System.out.println("erroreeeeeee1:\n" );
            System.exit(-3);
        }
        System.out.println("Ecco la lista slot!!\n\n");
        //System.out.println(listaSlot);


        /*for(Slot s: listaSlot){
            System.out.println("Ecco la lista slot!!\n\n");

            System.out.println(s.toString());

        }*/
        //return listaSlot;
    }

    /*public void loadSale() {
        //dal file sala.txt carico solo gli id
        String idSala;
        String id;
        int numAttrezzi;
        LinkedList<Attrezzo> listaAttrezzi = loadListaAttrezzi();
        LinkedList<Slot> listaOrari = loadSlot();
        try {
            System.out.println("sono dentro load sale\n\n");

            BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\sale.txt"));
            idSala = br1.readLine();
            //System.out.println(id);
            while (idSala != null){
               // idSala = br1.readLine();
                numAttrezzi = Integer.parseInt(br1.readLine());
                //System.out.println(idSala);
                //idSlot = br1.readLine();
                //System.out.println(idSlot);
                this.elencoSaleDisponibili.put(idSala, new Sala(idSala, numAttrezzi,  listaOrari, listaAttrezzi));
                idSala = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("erroreeeeeee1:\n" );
            System.exit(-1);
        }
        System.out.println("Ecco l'elenco delle sale disponibili:\n" +elencoSaleDisponibili );

    }

    public void loadIstruttore(){
        String idIstruttore;
        String id;
        LinkedList<Slot> listaOrari = loadSlot();

        try{
            System.out.println("sono dentro load istruttore\n\n");

            BufferedReader br1 = new BufferedReader(new FileReader("C:\\Users\\Fabiola Marchì\\istruttori.txt"));
            idIstruttore = br1.readLine();
            while (idIstruttore != null){
                //idIstruttore = br1.readLine();
                this.elencoIstruttoriDisponibili.put(idIstruttore, new Istruttore(idIstruttore, listaOrari));
                idIstruttore = br1.readLine();
            }
        }catch (IOException e){
            System.out.println("erroreeeeeee4:\n" );
            System.exit(-4);
        }
        System.out.println("Ecco l'elenco degli istruttori disponibili:\n" +elencoIstruttoriDisponibili );

    }
    public List<Corso> getElencoCorsi() {
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
