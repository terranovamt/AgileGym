package org.unict.domain;

import java.util.*;
import java.io.*;

import static java.lang.Math.abs; //rand num

public class Agilegym {
    static  Agilegym agilegym;
    private Corso corsoCorrente;
    public  Map<String, Attrezzo> elencoAttrezzi;
    private final Map<String, Sala> elencoSale;
    private final Map<String, Corso> elencoCorsi;
    private final Map<String, Istruttore> elencoIstruttori;

    private Agilegym() throws IOException {
        this.elencoCorsi = new HashMap<>();
        this.elencoAttrezzi  = new HashMap<>();
        this.elencoSale = new HashMap<>();
        this.elencoIstruttori = new HashMap<>();

        loadAttrezzi();
        loadSale();
        loadIstruttore();
    }

    public static Agilegym getInstance(){
        if (agilegym == null)
            try{
                agilegym = new Agilegym();
            }catch (IOException e){
                System.out.println("PRIMO ERRORE");
            }
        else
            System.out.println("Instanza già creata");
        return agilegym;
    }

    public void inserisciCorso() {
        String idCorso;
        String nomeCorso;
        String livello;
        String focus;
        String idAttrezzo;
        String s;
        Attrezzo attrezzoSelezionato;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-------Inserisci un corso-------\n");

        try {
            //System.out.println("Inserisci l'ID del corso: \n");
            idCorso=randId();
            //idCorso = br.readLine();
            System.out.println("ID CORSO:"+idCorso +" (generato autonomamente)");
            System.out.print("Inserisci il nome del corso: ");
            nomeCorso = br.readLine();
            System.out.print("Inserisci il livello del corso: ");
            livello = br.readLine();
            System.out.print("Inserisci il focus del corso: ");
            focus = br.readLine();
            //L'amministratore inserisce l'id dell'attrezzo che vuole per quel corso, si cerca se quell'attrezzo
            //è presente nella lista degli attrezzi, una volta trovato, si prende quell'attrezzo e lo si inserisce nel corso
            System.out.print("Attrezzi della palestre: ");
            for (String key: elencoAttrezzi.keySet()){
                System.out.print(elencoAttrezzi.get(key).getIdAttrezzo()+", ");
            }
            System.out.print("\n");
            do{
                System.out.print("Inserisci l'ID dell'attrezzo del corso: ");
                idAttrezzo = br.readLine();
                attrezzoSelezionato = elencoAttrezzi.get(idAttrezzo);
            }while (attrezzoSelezionato == null);
            //System.out.println(attrezzoSelezionato.toString());
            this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus, attrezzoSelezionato.getIdAttrezzo());
            System.out.println("\n--------RIEPILOGO CORSO--------");
            System.out.println(corsoCorrente+"\n--------------------------------\n");
            do {
                s="";
                System.out.print("Vuoi inserire questo corso? (si/no): ");
                String str=br.readLine();
                if (str.equals("si")){
                    s=str;
                }
                if (str.equals("no")){
                    s=str;
                }
            }while (s.equals(""));
            if (s.equals("si")) {
                this.elencoCorsi.put(corsoCorrente.getIdCorso(), corsoCorrente);
            }
            do {
                s="";
                System.out.print("Vuoi inserire una lezione per questo corso? (si/no): ");
                String str=br.readLine();
                if (str.equals("si")){
                    s=str;
                }
                if (str.equals("no")){
                    s=str;
                }
            }while (s.equals(""));
            if (s.equals("si")) {
                inserisciLezione(corsoCorrente);
            }
        } catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA: "+e.getMessage());
            System.exit(-7);
        }
    }

    public void inserisciLezione(Corso corso){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String  s, idSalaSelezioanta, idIstruttoreSelezioanto, idLezione = randId();
        int dataOra = 0;
        Attrezzo attrezzoSelezionato ;
        Sala salaSelezionata = null;
        Slot slotSelezionato= null;
        Istruttore istruttoreSelezionato = null;
        corsoCorrente = corso;

        try {
            attrezzoSelezionato = elencoAttrezzi.get(corso.getIdAttrezzo());
            System.out.println("-------Inserisci una Lezione-------\n");
            if (attrezzoSelezionato.getListaSaleDiAttrezzo().size()==1){
                System.out.println("E' disponibile solo la sala: "+attrezzoSelezionato.getListaSaleDiAttrezzo().get(0));
                salaSelezionata = elencoSale.get(String.valueOf(attrezzoSelezionato.getListaSaleDiAttrezzo().get(0)));
            }else {
            System.out.println("Elenco degli Sale disponibili per l'attrezzo ~" +attrezzoSelezionato.getIdAttrezzo()+"~:");
            System.out.print(attrezzoSelezionato.stampaListaSale());
            //SCELTA DELLA SALA
                do {
                    System.out.print("Inserisci una sala Disponibile: ");
                    idSalaSelezioanta = br.readLine();
                    if(attrezzoSelezionato.getListaSaleDiAttrezzo().contains(idSalaSelezioanta)){
                        salaSelezionata = elencoSale.get(idSalaSelezioanta);
                    }
                }while (salaSelezionata ==null);

            }

            //SCELTA DELLO SLOT
            if (salaSelezionata.getSlotDisponibili().size()==1){
                System.out.println("E' disponibile solo lo Slot: "+salaSelezionata.getSlotDisponibili().get(0).getDataora());
                slotSelezionato = salaSelezionata.getSlotDisponibili().get(Integer.parseInt(salaSelezionata.getSlotDisponibili().get(0).getDataora()));
            }else{
                System.out.println("Elenco degli Slot disponibili per la sala ~" + salaSelezionata.getIdSala()+"~:");
                for (Integer key: salaSelezionata.getSlotDisponibili().keySet()){//Serve per non stampare con le graffe e avere solo il valore e non la key, la formattazione e va fatto nel to string del tipo(sala.toString)
                    System.out.print("\tID-Slot: "+ salaSelezionata.getSlotDisponibili().get(key).getDataora()+"\n");
                }

                do {
                    System.out.print("Inserisci uno slot Disponibile: ");
                    dataOra = Integer.parseInt(br.readLine());
                    if(salaSelezionata.getSlotDisponibili().containsKey(dataOra)){
                        slotSelezionato = salaSelezionata.getSlotDisponibili().get(dataOra);
                    }
                }while (slotSelezionato ==null);
                //System.out.println(idSlotSelezionato);
            }
            //SCELTA DELL'ISTRUTTORE
            Iterator<Map.Entry<String, Istruttore>> it = elencoIstruttori.entrySet().iterator();
            Map<String,Istruttore> isDisponibile=new HashMap<>();

            while (it.hasNext()) {
                Map.Entry<String, Istruttore> entry = it.next();
                Istruttore i = entry.getValue();
                if (i.isIstruttoreDiponibili(dataOra)) {
                    isDisponibile.put(i.getIdIstruttore(), i);
                }
            }
            if (isDisponibile.size()==0){
                System.out.println("NON SONO DISPONIBILI ISTUTTORI SCEGLI UN ALTRO SLOT");
                inserisciLezione(corso);
            }
            if (isDisponibile.size()==1){
                for (String key : isDisponibile.keySet()) {//STAMPA ISTRUTTORI DISPONIBILI
                    System.out.println("E' disponibile solo l'istruttore: " + isDisponibile.get(key).getIdIstruttore());
                    istruttoreSelezionato = elencoIstruttori.get(isDisponibile.get(key).getIdIstruttore());
                }
            }
            else {
                System.out.println("Elenco degli Istruttori disponibili per lo Slot ~" + dataOra + "~:");
                for (String key : isDisponibile.keySet()) {//STAMPA ISTRUTTORI DISPONIBILI
                    System.out.println("\t" + isDisponibile.get(key).getIdIstruttore());
                }
                do {
                    System.out.print("Inserisci una istruttore Disponibile: ");
                    idIstruttoreSelezioanto = br.readLine();
                    if (isDisponibile.containsKey(idIstruttoreSelezioanto)) {
                        istruttoreSelezionato = elencoIstruttori.get(idIstruttoreSelezioanto);
                    }
                } while (istruttoreSelezionato == null);
                //System.out.println(istruttoreSelezionato);
            }
            //TUTTE LE SCELTE SONO ANDATE A BUON FINE
            System.out.println("\n------------RIEPILOGO------------");
            System.out.println( "LEZIONE: \n" +
                                "\tID: " + idLezione + "\n" +
                                "\tNome Corso: " + corsoCorrente.getNome() + "\n" +
                                "\tSala: " + salaSelezionata.getIdSala()+ "\n" +
                                "\tIstruttore: " + istruttoreSelezionato.getIdIstruttore() + "\n" +
                                "\tI#D-Slot: " + slotSelezionato.getDataora());
            System.out.println("\n--------------------------------\n");

            do {
                s="";
                System.out.print("Vuoi inserire questa lezione? (si/no): ");
                String str=br.readLine();
                if (str.equals("si")){
                    s=str;
                    corsoCorrente.inserisciLezione(idLezione, slotSelezionato,corsoCorrente , salaSelezionata, istruttoreSelezionato);
                    salaSelezionata.setOccupato(slotSelezionato.getDataora());
                    istruttoreSelezionato.setOccupato(slotSelezionato.getDataora());
                    System.out.println("\n------------CONFERMA INSERIMENTO LEZIONE-------------\n");
                    System.out.println(corsoCorrente);
                }
                if (str.equals("no")){
                    s=str;
                }
            }while (s.equals(""));
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
                inserisciLezione(corsoCorrente);
            }
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" +e.getMessage());
            System.exit(-8);
        }
    }

    public void loadAttrezzi(){
        String str, idAtrezzo;
        String [] strings;
        //System.out.println("sono dentro Attrezzi\n\n");
        try{
            BufferedReader br = new BufferedReader(new FileReader("Attrezzi.txt"));
            str = br.readLine();
            while (str != null){
                strings=str.split("-");
                idAtrezzo=strings[0];
                List<String> listaSala = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length));
                Attrezzo a=new Attrezzo(idAtrezzo,listaSala);
                this.elencoAttrezzi.put(idAtrezzo,a);
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Attrezzi.txt\n" );
            System.exit(-4);
        }
    }

    public void loadSale() {
        String str, idSala;
        String [] strings;

        try {
            BufferedReader br = new BufferedReader(new FileReader("sale.txt"));

            str = br.readLine();
            while (str != null){
                strings=str.split("-");
                idSala=strings[0];
                List<String> listaAtrezziSala = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length));
                Sala s=new Sala(idSala);
                s.setListaAttrezzi(listaAtrezziSala);
                this.elencoSale.put(idSala, s);
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Sale.txt\n" );
            System.exit(-5);
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
            System.exit(-6);
        }
    }

    public void riempiPalestra(){
        String str;
        String [] strings;
        try {
            BufferedReader bcorsi = new BufferedReader(new FileReader("corsi.txt"));
            BufferedReader blezioni = new BufferedReader(new FileReader("lezioni.txt"));

            str = bcorsi.readLine();
            while (str != null){
                strings=str.split("-");
                Corso c= new Corso(strings[0], strings[1], strings[2], strings[3], (strings[4]));
                this.elencoCorsi.put(strings[0],c);
                str = bcorsi.readLine();
            }

            str = blezioni.readLine();
            while (str != null){
                strings=str.split("-");
                Corso c=elencoCorsi.get(strings[1]);
                String idLezione=strings[0];
                Sala s=elencoSale.get(strings[2]);
                Istruttore i=elencoIstruttori.get(strings[3]);
                Slot slot=s.getListaSlot().get(Integer.parseInt(strings[4]));
                c.inserisciLezione(idLezione,slot ,c,s,i);
                s.setOccupato(slot.getDataora());
                i.setOccupato(slot.getDataora());
                str = blezioni.readLine();
            }
            System.out.println("\nI corsi e le lezioni sono stati inseriti con successo\n\n");
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE corsi.txt\n" );
            System.exit(-9);
        }
    }

    public static String randId(){
        return String.valueOf((abs((int) System.currentTimeMillis() + (int)(Math.random()*(1000000000)))/10));
    }

    //GET E SET STANDARD
    public Map<String, Corso> getElencoCorsi() {
        return elencoCorsi;
    }

    public Map<String, Sala> getElencoSale() {
        return elencoSale;
    }

    public Map<String, Istruttore> getElencoIstruttori() {
        return elencoIstruttori;
    }

}
