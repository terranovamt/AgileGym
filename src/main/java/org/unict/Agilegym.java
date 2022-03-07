package org.unict;



///caso d'uso di avviamento, caricamento sale, carimento lista attrezzi, caricamento slot e caricamnento istruttori

import javax.management.StringValueExp;
import java.util.*;
import java.io.*;

import static java.lang.Math.abs; //rand num

public class Agilegym {
    //attributes
    private static  Agilegym agilegym;
    private Corso corsoCorrente;
    private Map<String, Corso> elencoCorsi;
    private Attrezzo attrezzoSelezionato;
    private Map<String, Attrezzo> elencoAttrezzi;
    private Sala salaSelezioanta;
    private Map<String, Sala> elencoSaleDisponibili;
    private Slot slotSelezionato;
    private Map<Integer, Slot> listaSlot;
    private Istruttore istruttoreSelezionato;
    private Map<String, Istruttore> elencoIstruttoriDisponibili;



    //constructor
    private Agilegym() throws IOException {
        this.listaSlot  = new HashMap<>();
        this.elencoCorsi = new HashMap<>();
        this.elencoAttrezzi  = new HashMap<>();
        this.elencoSaleDisponibili = new HashMap<>();
        this.elencoIstruttoriDisponibili = new HashMap<>();

        loadAttrezzi();
        loadSale();
        loadIstruttore();

        // loadSale();
        // loadIstruttore();
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

    public void inserisciLezione(Corso c)throws inserisciCorsoException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s="", idSalaSelezioanta, idIstruttoreSelezioanto, idLezione=String.valueOf(abs((int) System.currentTimeMillis()) + (int)(Math.random()*(10000)));;
        int idSlotSelezionato=0;
        System.out.println("-------Inserisci una Lezione-------\n");
        try {
            System.out.println("Elenco degli Sale dispnibili per l'attrezzo ~" +attrezzoSelezionato.getIdAttrezzo()+"~:");
            System.out.println(attrezzoSelezionato.stampaListaSale());
            do {
                System.out.print("Inserisci una sala Disponibile: ");
                idSalaSelezioanta = br.readLine();
                if(attrezzoSelezionato.getListaSalediAttrezzo().contains(idSalaSelezioanta)){
                    salaSelezioanta = elencoSaleDisponibili.get(idSalaSelezioanta);
                }
            }while (salaSelezioanta==null);

            //STAMPA SLOT DIAPONIBILI
            System.out.println("Elenco degli Slot dispnibili per la sala ~" +salaSelezioanta.getIdSala()+"~:");
            for (String key: salaSelezioanta.getSlotDiponibili().keySet()){//Serve per non stampare con le graffe e avere solo il valore e non la key, la formattazione e va fatto nel to string del tipo(sala.toString)
                System.out.print(salaSelezioanta.getSlotDiponibili().get(key));
            }

            do {
                System.out.print("Inserisci uno slot Disponibile: ");
                idSlotSelezionato = Integer.parseInt(br.readLine());
                if(salaSelezioanta.getListaSlot().containsKey(idSlotSelezionato)){
                    System.out.print("Condizione vera");
                    salaSelezioanta = elencoSaleDisponibili.get(idSalaSelezioanta);
                }
            }while (idSlotSelezionato==0);
            //System.out.println(idSlotSelezionato);
            Iterator it = elencoIstruttoriDisponibili.entrySet().iterator();
            System.out.print("Elenco degli Istruttori dispnibili per la Slot ^" +idSlotSelezionato+"^:");
            while (it.hasNext()) {
                // Utilizza il nuovo elemento (coppia chiave-valore) dell'hashmap
                Map.Entry entry = (Map.Entry)it.next();
                Istruttore i=(Istruttore) entry.getValue();
                s+=i.getIstruttoreDiponibili(idSlotSelezionato);
            }
            s=s.substring(0, s.length()-2);//rimuove l'ultimo ", "
            System.out.println(s);//STAMPA ISTRUTTORI DISPONIBILI
            do {
                System.out.print("Inserisci una istruttore Disponibile: ");
                idIstruttoreSelezioanto = br.readLine();
                istruttoreSelezionato = elencoIstruttoriDisponibili.get(idIstruttoreSelezioanto);
            }while (istruttoreSelezionato==null);
            //System.out.println(idIstruttoreSelezioanto);
            //System.out.println(istruttoreSelezionato);
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:\n" );
            System.exit(-12);//CONTROLLA IL NUMERO
        }

    }

    public  void conferma(){
        if(corsoCorrente != null){
            this.elencoCorsi.put(corsoCorrente.getIdCorso(), corsoCorrente);
            System.out.println("CONFERMA INSERIMENTO CORSO");
        }
    }

    public  Map<Integer, Slot> loadSlot(){
        String idSlot;
        boolean disponibilita;
        //Slot [] s = new Slot[n];
        Slot s;
        try {
            //System.out.println("sono dentro loadSlot");
            BufferedReader br1 = new BufferedReader(new FileReader("slot.txt"));
            //BufferedReader br1 = new BufferedReader(new FileReader("C:\\Utenti\\Fabiola Marchì\\slot.txt"));
            //id = br1.readLine();
            /*for(int j= 0; j<5; j++) {                                                                                           //Non dovrebbe essere auomatico il num di righe
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
            }*/
            idSlot = br1.readLine();
            while (idSlot != null) {
                disponibilita = Boolean.parseBoolean(br1.readLine());
                s = new Slot(idSlot, disponibilita);
                s.setIdSlot(idSlot);
                s.setDisponibile(disponibilita);
                this.listaSlot.put(Integer.parseInt(s.getIdSlot()), s);
                idSlot = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE slot.txt\n" );
            System.exit(-3);
        }
       // System.out.println("Ecco la lista degli slot orari:\n\n");
       // System.out.println(listaSlot);
        return listaSlot;
    }
/*
    public List caricamentoListaAttrezziSala1(){
        List listaAttrezziSala1 = new ArrayList();
        String idAttrezzo;
        try {
            BufferedReader br = new BufferedReader(new FileReader("AttrezziSala1.txt"));
                idAttrezzo = br.readLine();
                while (idAttrezzo != null) {
                    listaAttrezziSala1.add(idAttrezzo);
                    idAttrezzo = br.readLine();
                }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE AttrezziSala1.txt\n" );
            System.exit(-5);
        }
        //System.out.println("ListaAttrezziSala1:\n" +listaAttrezziSala1 );

        return listaAttrezziSala1;
    }
    public List caricamentoListaAttrezziSala2(){
        List listaAttrezziSala2 = new ArrayList();
        String idAttrezzo;
        try {
            BufferedReader br = new BufferedReader(new FileReader("AttrezziSala2.txt"));
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
            BufferedReader br = new BufferedReader(new FileReader("AttrezziSala3.txt"));
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
            BufferedReader br = new BufferedReader(new FileReader("AttrezziSala4.txt"));
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
            BufferedReader br = new BufferedReader(new FileReader("AttrezziSala5.txt"));
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
    public List loadListaAtrezziinSala(String idSala) {
        String str;
        List listaAtrezziSala = new ArrayList();        //questa è l'elenco degli atrezzi in una sala
        System.out.println("sono dentro listaAtrezziInSale\n\n");
        try {
            BufferedReader br = new BufferedReader(new FileReader("listaAtrezziInSale.txt"));
            String [] strings;
            str = br.readLine();
            while (str != null){
                strings=str.split("-");
                if (idSala.equals(strings[0])){
                    for(int i =1; i<strings.length; i++) {
                        listaAtrezziSala.add(strings[i]);
                    }
                }
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE listaAtrezziInSale.txt\n" );
            System.exit(-10);
        }

        System.out.println("Ecco la lista degli attrezzi:\n" + listaAttrezzi );
        return listaAtrezziSala;
    }*/

    public Map<String, Attrezzo> loadAttrezzi(){
        String str, idAtrezzo;
        //System.out.println("sono dentro Attrezzi\n\n");
        try{
            BufferedReader br = new BufferedReader(new FileReader("Attrezzi.txt"));
            String [] strings;
            str = br.readLine();
            while (str != null){
                strings=str.split("-");
                idAtrezzo=strings[0];
                List listaSala=new ArrayList<>();
                for(int i =1; i<strings.length; i++) {
                    listaSala.add(strings[i]);
                }
                Attrezzo a=new Attrezzo(idAtrezzo,listaSala);
                this.elencoAttrezzi.put(idAtrezzo,a);
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Attrezzi.txt\n" );
            System.exit(-10);
        }
        System.out.println("Elenco Attezzi:");
        for (String key: elencoAttrezzi.keySet()){
            System.out.println(elencoAttrezzi.get(key));
        }
        return elencoAttrezzi;
    }

    public Map<String, Sala> loadSale() {
        String str, idSala;
        //System.out.println("sono dentro listaAtrezziInSale\n\n");
        try {
            BufferedReader br = new BufferedReader(new FileReader("sale.txt"));
            String [] strings;
            str = br.readLine();
            while (str != null){
                strings=str.split("-");
                idSala=strings[0];
                List listaAtrezziSala = new ArrayList(); //questa è l'elenco degli atrezzi in una sala, ogni volta che ciclo deve essere ua lista nuova, non la dichiaro fuori
                for(int i =1; i<strings.length; i++) {
                    listaAtrezziSala.add(strings[i]);
                }
                Sala s=new Sala(idSala,loadSlot(),listaAtrezziSala);
                this.elencoSaleDisponibili.put(idSala, s);
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Sale.txt\n" );
            System.exit(-10);
        }
        //fare la creazione e il put con un for
        //Sala sala1 = new Sala("Sala1", loadSlot(), loadListaAtrezziinSala("Sala1"));
        //Sala sala2 = new Sala("Sala2", loadSlot(), caricamentoListaAttrezziSala2());
        //Sala sala3 = new Sala("Sala3", loadSlot(), caricamentoListaAttrezziSala3());
        //Sala sala4 = new Sala("Sala4", loadSlot(), caricamentoListaAttrezziSala4());
        //Sala sala5 = new Sala("Sala5", loadSlot(), caricamentoListaAttrezziSala5());
        //this.elencoSaleDisponibili.put(sala1.getIdSala(), sala1);
        //this.elencoSaleDisponibili.put(sala2.getIdSala(), sala2);
        //this.elencoSaleDisponibili.put(sala3.getIdSala(), sala3);
        //this.elencoSaleDisponibili.put(sala4.getIdSala(), sala4);
        //this.elencoSaleDisponibili.put(sala5.getIdSala(), sala5);
        System.out.println("Elenco Sale Disponibili:");
        for (String key: elencoSaleDisponibili.keySet()){//Serve per non stampare con le graffe e avere solo il valore e non la key, la formattazione e va fatto nel to string del tipo(sala.toString)
            System.out.println(elencoSaleDisponibili.get(key));
        }
        /*elencoSaleDisponibili.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " = " + entry.getValue());
        });*/
        //System.out.println(sala1.toString());
        //System.out.println(this.elencoSaleDisponibili);
        //System.out.println(caricamentoListaAttrezziSala1());
        //System.out.println(loadSlot());
        //non stampa, ma si riempie
        return elencoSaleDisponibili;
    }
    /*public Map<String,Sala> loadSaleAttrezzi(){
        String str;
        Map<String,Sala> elencoSaleAttrezzate = new HashMap<>();        //questa  è l'elenco della sale di ciascun attrezzo
        try {
            BufferedReader br1 = new BufferedReader(new FileReader("saleDiAttrezzo.txt"));
            String [] strings;
            str = br1.readLine();
            while (str != null) {
                strings=str.split("-");
                strings[0]
                str = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE CARICAMENTO FILE saleDiAttrezzo.txt\n" );
            System.exit(-13);
        }
        return elencoSaleAttrezzate;
    }

    public void loadAttrezzi() {
        //dal primo file si carica la lista di attrezzi che fa parte dell'oggetto sala,
        //dal secondo file si carica le sale
        String idAttrezzo;
        String str;

        List elencoSaleAttrezzate = new ArrayList();        //questa  è l'elenco della sale di ciascun attrezzo

        System.out.println("sono dentro loadListaAttrezzi\n\n");

        try {
            BufferedReader br1 = new BufferedReader(new FileReader("Attrezzi.txt"));
            String [] strings;
            str = br1.readLine();
            //idAttrezzo = br.readLine();
            //System.out.println(id);
            while (str != null){
                strings=str.split("-");
                for(int i =1; i<strings.length; i++){
                    elencoSaleAttrezzate.add(strings[i]);
                }
                listaAttrezzi.put(strings[0], new Attrezzo(strings[0], elencoSaleAttrezzate));
                str = br1.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE saleDiAttrezzo.txt\n" );
            System.exit(-10);
        }

        System.out.println("Ecco la lista degli attrezzi:\n" + listaAttrezzi );
        //funziona, ma non funzione il toString di sala

    }*/

    public void loadIstruttore(){
        String idIstruttore;
        Map<Integer, Slot> listaOrari = loadSlot();
        try{
            //System.out.println("sono dentro load istruttore\n\n");
            BufferedReader br = new BufferedReader(new FileReader("Istruttori.txt"));
            idIstruttore = br.readLine();
            while (idIstruttore != null){
                this.elencoIstruttoriDisponibili.put(idIstruttore, new Istruttore(idIstruttore, listaOrari));
                idIstruttore = br.readLine();
            }
        }catch (IOException e){
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE istruttori.txt:\n" );
            System.exit(-11);
        }
        System.out.println("Ecco l'elenco degli istruttori disponibili:");
        for (String key: elencoIstruttoriDisponibili.keySet()){
            System.out.println(elencoIstruttoriDisponibili.get(key));
        }
        //funziona, ma non funzione il toString di slot, c'è qualcosa che no va nella stamp adi elenco---disponibili

    }

    public void inserisciCorso() throws inserisciCorsoException {
        String idCorso;
        String nomeCorso;
        String livello;
        String focus;
        String idAttrezzo;
        Map<String, Sala> saleDisponibili;
        LinkedList<Sala> sale;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-------Inserisci un corso-------\n");
        try {
            //System.out.println("Inserisci l'ID del corso: \n");
            //idCorso = br.readLine();
            idCorso=String.valueOf(abs((int) System.currentTimeMillis()) + (int)(Math.random()*(10000)));
            System.out.println("ID CORSO:"+idCorso +" (generato autonomamente)");
            System.out.print("Inserisci il nome del corso: ");
            nomeCorso = br.readLine();
            System.out.print("Inserisci il livello del corso: ");
            livello = br.readLine();
            System.out.print("Inserisci il focus del corso: ");
            focus = br.readLine();
            //l'amministatore inserisce l'id dell'attrezzo che vuole per quel corso, si cerca se quell'attrezzo
            //e' presente nella lista degli attrezzi, una volta trovato, si prende quell'attrezzo e lo si inserisce nel corso
             do{
                 System.out.print("Inserisci l'ID dell'attrezzo del corso: ");
                 idAttrezzo = br.readLine();
                 attrezzoSelezionato = elencoAttrezzi.get(idAttrezzo);
            }while (attrezzoSelezionato == null);
            //System.out.println(attrezzoSelezionato.toString());
            if (attrezzoSelezionato == null) {
                throw new inserisciCorsoException("Errore attrezzo inserito\n");
            } else {
                this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus, attrezzoSelezionato.getIdAttrezzo());
                System.out.println("\n-------Corso inserito-------");
                System.out.println(corsoCorrente+"\n------------------------------\n");
                inserisciLezione(corsoCorrente);
            }
        } catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:\n" );
            System.exit(-12);
        }

    }

    /*public boolean equals(Attrezzo a){
        return a.getIdAttrezzo() == idAttrezzo;
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
