package org.unict;

//caso d'uso di avviamento, caricamento sale, caricamento lista attrezzi, caricamento slot e caricamento istruttori

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
    private Map<String, Sala> elencoSale;
    private Slot slotSelezionato;
    private Istruttore istruttoreSelezionato;
    private Map<String, Istruttore> elencoIstruttori;



    //constructor
    private Agilegym() throws IOException {
        this.elencoCorsi = new HashMap<>();
        this.elencoAttrezzi  = new HashMap<>();
        this.elencoSale = new HashMap<>();
        this.elencoIstruttori = new HashMap<>();

        //riempiPalestra();
        loadAttrezzi();
        loadSale();
        loadIstruttore();
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
            idCorso=String.valueOf((abs((int) System.currentTimeMillis() + (int)(Math.random()*(1000000000)))/10));
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
                System.out.println("\n--------RIEPILOGO CORSO--------");
                System.out.println(corsoCorrente+"\n--------------------------------\n");
                inserisciLezione();
            }
        } catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:\n" );
            System.exit(-12);
        }

    }

    public void inserisciLezione()throws inserisciCorsoException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String  idSalaSelezioanta="",
                idIstruttoreSelezioanto="",
                idLezione=String.valueOf((abs((int) System.currentTimeMillis() + (int)(Math.random()*(1000000000)))/10));
        int dataora=0;
        System.out.println("-------Inserisci una Lezione-------\n");
        try {
            System.out.println("Elenco degli Sale dispnibili per l'attrezzo ~" +attrezzoSelezionato.getIdAttrezzo()+"~:");
            System.out.print(attrezzoSelezionato.stampaListaSale());
            do {
                salaSelezioanta=null;
                System.out.print("Inserisci una sala Disponibile: ");
                idSalaSelezioanta = br.readLine();
                if(attrezzoSelezionato.getListaSalediAttrezzo().contains(idSalaSelezioanta)){
                    salaSelezioanta = elencoSale.get(idSalaSelezioanta);
                }
            }while (salaSelezioanta==null);

            //STAMPA SLOT DISPONIBILI
            System.out.println("Elenco degli Slot dispnibili per la sala ~" +salaSelezioanta.getIdSala()+"~:");

            for (Integer key: salaSelezioanta.getSlotDisponibili().keySet()){//Serve per non stampare con le graffe e avere solo il valore e non la key, la formattazione e va fatto nel to string del tipo(sala.toString)
                System.out.print("\tID-Slot: "+salaSelezioanta.getSlotDisponibili().get(key).getDataora()+"\n");
            }

            do {
                slotSelezionato=null;
                System.out.print("Inserisci uno slot Disponibile: ");
                dataora = Integer.parseInt(br.readLine());

                if(salaSelezioanta.getSlotDisponibili().containsKey(dataora)){
                    slotSelezionato = salaSelezioanta.getSlotDisponibili().get(dataora);
                }
            }while (slotSelezionato==null);
            //System.out.println(idSlotSelezionato);

            //STAMPA ISTRUTTORI DISPONIBILI
            Iterator it = elencoIstruttori.entrySet().iterator();
            Map<String,Istruttore> isDisponibile=new HashMap<>();
            System.out.println("Elenco degli Istruttori dispnibili per lo Slot ~" +dataora+"~:");
            while (it.hasNext()) {
                // Utilizza il nuovo elemento (coppia chiave-valore) dell'hashmap per wcorrete tutti gli istruttori
                Map.Entry entry = (Map.Entry)it.next();
                Istruttore i=(Istruttore) entry.getValue();
                if(i.isIstruttoreDiponibili(dataora)==true){//verifico se disponibile nello solt
                isDisponibile.put(i.getIdIstruttore(),i);// se disponibile aggiu go listruttore ad una losta di istruttori disponibili
                }
            }
            for (String key: isDisponibile.keySet()){//STAMPA ISTRUTTORI DISPONIBILI
                System.out.println("\t"+isDisponibile.get(key).getIdIstruttore());
            }
            do {
                istruttoreSelezionato=null;
                System.out.print("Inserisci una istruttore Disponibile: ");
                idIstruttoreSelezioanto = br.readLine();
                if (isDisponibile.containsKey(idIstruttoreSelezioanto)==true) {
                    istruttoreSelezionato = elencoIstruttori.get(idIstruttoreSelezioanto);
                }
            }while (istruttoreSelezionato==null);
            //System.out.println(idIstruttoreSelezioanto);
            //System.out.println(istruttoreSelezionato);

            //TUTTE LE SCELTE SONO ANDATE A BUON FINE
            System.out.println("\n------------RIEPILOGO------------");
            corsoCorrente.inserisciLezione(idLezione,slotSelezionato ,corsoCorrente ,salaSelezioanta ,istruttoreSelezionato);
            System.out.println(corsoCorrente);
            System.out.println("\n--------------------------------\n");


            String s;
            do {
                s="";
            System.out.print("Vuoi inserire un altra lezione per questo corso? (si/no):");
                String str=br.readLine();
                if (str.equals("si")){
                    s=str;
                }
                if (str.equals("no")){
                    s=str;
                }
            }while (s.equals(""));
            if (s.equals("si")) {
                inserisciLezione();
            }if (s.equals("no")){
                conferma();
            }
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA: " +e.getMessage() );
            System.exit(-12);//CONTROLLA IL NUMERO
        }
    }

    public  void conferma(){
        if(corsoCorrente != null){
            this.elencoCorsi.put(corsoCorrente.getIdCorso(), corsoCorrente);
            this.salaSelezioanta.setOccupato(slotSelezionato.getDataora());
            this.istruttoreSelezionato.setOccupato(slotSelezionato.getDataora());
            System.out.println("CONFERMA INSERIMENTO CORSO");
            for (String key: elencoCorsi.keySet()){         //STAMPA ISTRUTTORI DISPONIBILI
                System.out.println(elencoCorsi.get(key));
            }
        }
    }

    public void loadAttrezzi(){
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
    }

    public void loadSale() {
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
                Sala s=new Sala(idSala);
                s.setListaAttrezzi(listaAtrezziSala);
                this.elencoSale.put(idSala, s);
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Sale.txt\n" );
            System.exit(-10);
        }
        System.out.println("Elenco Sale Disponibili:");
        for (String key: elencoSale.keySet()){//Serve per non stampare le graffe elas key ma slo il value, la formattazione e va fatto nel to string del tipo(sala.toString)
            System.out.println(elencoSale.get(key));
        }

    }

    public void loadIstruttore(){
        String idIstruttore;

        try{
            //System.out.println("sono dentro load istruttore\n\n");
            BufferedReader br = new BufferedReader(new FileReader("Istruttori.txt"));
            idIstruttore = br.readLine();
            while (idIstruttore != null){
                this.elencoIstruttori.put(idIstruttore, new Istruttore(idIstruttore));
                idIstruttore = br.readLine();
            }
        }catch (IOException e){
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE istruttori.txt:\n" );
            System.exit(-11);
        }
        System.out.println("Ecco l'elenco degli istruttori disponibili:");
        for (String key: elencoIstruttori.keySet()){
            System.out.println(elencoIstruttori.get(key));
        }
    }

    private void riempiPalestra(){
        String str;
        try {
            BufferedReader bcorsi = new BufferedReader(new FileReader("corsi.txt"));
            BufferedReader blezioni = new BufferedReader(new FileReader("lezioni.txt"));
            String [] strings;
            str = bcorsi.readLine();
            while (str != null){
                strings=str.split("-");
                Corso c= new Corso(strings[0], strings[1], strings[2], strings[3], (strings[4]));
                this.elencoCorsi.put(strings[0],c);
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE corsi.txt\n" );
            System.exit(-10);
        }
    }

    public Map<String, Corso> getElencoCorsi() {
        return elencoCorsi;
    }
}
