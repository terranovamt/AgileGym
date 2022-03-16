package org.unict.domain;

import javax.swing.*;
import java.util.*;
import java.io.*;

import static java.lang.Math.abs; //rand num
import static java.lang.Math.log;

public class Agilegym {
    static  Agilegym agilegym;
    private Corso corsoCorrente;
    private final Map<String, Cliente> elencoClienti;
    private final Map<String, Attrezzo> elencoAttrezzi;
    private final Map<String, Corso> elencoCorsi;
    private final Map<String, Sala> elencoSale;
    private final Map<String, Istruttore> elencoIstruttori;

    private Agilegym() throws IOException {
        this.elencoCorsi = new HashMap<>();
        this.elencoAttrezzi  = new HashMap<>();
        this.elencoSale = new HashMap<>();
        this.elencoIstruttori = new HashMap<>();
        this.elencoClienti = new HashMap<>();

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
    //UC1
    public void inserisciCorso() {
        String idCorso;
        String nomeCorso;
        String livello;
        String focus;
        String idAttrezzo;
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
            if (Objects.equals(nomeCorso, "0"))return;
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
            confermaCorso(idCorso,nomeCorso,livello,focus,attrezzoSelezionato);
        } catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA: "+e.getMessage());
            System.exit(-7);
        }
    }

    public void confermaCorso(String idCorso, String nomeCorso, String livello, String focus, Attrezzo idAttrezzo){
        String s;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus, idAttrezzo);
        System.out.println("\n--------RIEPILOGO CORSO--------");
        System.out.println(corsoCorrente+"\n--------------------------------\n");
        try{
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
        String  s, idSalaSelezionata, idIstruttoreSelezionato, idLezione = randId();
        int dataOra = 0;
        Attrezzo attrezzoSelezionato ;
        Sala salaSelezionata = null;
        Slot slotSelezionato= null;
        Istruttore istruttoreSelezionato = null;
        corsoCorrente = corso;

        try {
            attrezzoSelezionato = elencoAttrezzi.get(corso.getAttrezzo().getIdAttrezzo());
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
                    idSalaSelezionata = br.readLine();
                    if(attrezzoSelezionato.getListaSaleDiAttrezzo().contains(idSalaSelezionata)){
                        salaSelezionata = elencoSale.get(idSalaSelezionata);
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
                    idIstruttoreSelezionato = br.readLine();
                    if (isDisponibile.containsKey(idIstruttoreSelezionato)) {
                        istruttoreSelezionato = elencoIstruttori.get(idIstruttoreSelezionato);
                    }
                } while (istruttoreSelezionato == null);
                //System.out.println(istruttoreSelezionato);
            }
            //TUTTE LE SCELTE SONO ANDATE A BUON FINE
            System.out.println("\n------------RIEPILOGO------------");
            System.out.println( "LEZIONE: \n" +
                                "\tID: " + idLezione + "\n" +
                                "\tNome Corso: " + corsoCorrente.getNomeCorso() + "\n" +
                                "\tSala: " + salaSelezionata.getIdSala()+ "\n" +
                                "\tIstruttore: " + istruttoreSelezionato.getIdIstruttore() + "\n" +
                                "\tI#D-Slot: " + slotSelezionato.getDataora());
            System.out.println("\n--------------------------------\n");

            confermaLezione(slotSelezionato,salaSelezionata,istruttoreSelezionato);

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

    public void confermaLezione(Slot slotSelezionato, Sala salaSelezionata, Istruttore istruttoreSelezionato){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String  s, idLezione = randId();
        try {
            do {
                s="";
                System.out.print("Vuoi inserire questa lezione? (si/no): ");
                String str=br.readLine();
                if (str.equals("si")){
                    s=str;
                    corsoCorrente.inserisciLezione(idLezione, slotSelezionato, corsoCorrente , salaSelezionata, istruttoreSelezionato);
                    salaSelezionata.setOccupato(slotSelezionato.getDataora());
                    istruttoreSelezionato.setOccupato(slotSelezionato.getDataora());
                    System.out.println("\n------------CONFERMA INSERIMENTO LEZIONE-------------\n");
                    System.out.println(corsoCorrente);
                }
                if (str.equals("no")){
                    s=str;
                }
            }while (s.equals(""));
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" +e.getMessage());
            System.exit(-8);
        }
    }
    //UC2
    public void nuovaPrenotazione(){
        int i=0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Cliente logged = null;
        String username;

        try {
            do {
            System.out.print("Inserisci username: ");
                username = br.readLine();
            if(elencoClienti.containsKey(username)){
                logged=elencoClienti.get(username);
            }
            }while (logged ==null);
            System.out.println();
            for (String key : elencoCorsi.keySet()) {
                i++;
                System.out.println("CORSO: "+ i );
                System.out.println(elencoCorsi.get(key).stampaCorsi());
            }
            int scelta = 0;
            do{
                System.out.print("Scegli un corso: ");
                int s=Integer.parseInt(br.readLine());
                if(s>0 && s < elencoCorsi.size()){
                    scelta=s;
                    mostraLezione(scelta,logged);
                }
            }while(scelta==0);
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" +e.getMessage());
            System.exit(-11);
        }
    }

    public void mostraLezione(int sceltaCorso,Cliente logged){
        int i=0;
        Map<Integer,Corso> corsi =new HashMap<>();
        Map<String,Lezione> lezioniDisponibili;
        Map<Integer,Lezione> sceltaLezioni =new HashMap<>();
        Map<String,Prenotazione> elencoPrenotazioneUtente;
        String s;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Lezione lezioneCorrente;

        elencoPrenotazioneUtente=logged.getElencoPrenotazioni();

        for (String key : elencoCorsi.keySet()) {
            i++;
            corsi.put(i,getElencoCorsi().get(key));
        }
        corsoCorrente=corsi.get(sceltaCorso);
        System.out.println();
        lezioniDisponibili=corsoCorrente.mostraLezioni(elencoPrenotazioneUtente);
        if (lezioniDisponibili.size()!=0) {
            i = 0;
            for (String key : lezioniDisponibili.keySet()) {
                i++;
                System.out.println("LEZIONE: " + i);
                sceltaLezioni.put(i, lezioniDisponibili.get(key));
                System.out.println(lezioniDisponibili.get(key));
            }

            System.out.print("Scegli una lezione: ");
            int scelta = 0;
            try {
                do {
                    int s1 = Integer.parseInt(br.readLine());
                    if (s1 > 0 || s1 < lezioniDisponibili.size()) {
                        scelta = s1;
                    }
                } while (scelta == 0);
                lezioneCorrente = sceltaLezioni.get(scelta);

                System.out.println("\nLEZIONE SCELTA: " + scelta + "\n" + lezioneCorrente.stampaRiepilogo());

                System.out.print("-------MENU-------\n 1. Conferma la scelta\n 2. Cambia Lezione\n 3. Cambia Corso\n 0.Menu principale\nScelta:");
                int menu = Integer.parseInt(br.readLine());
                switch (menu) {
                    case 1:
                        confermaPrenotazione(lezioneCorrente.getIdLezione(), logged);
                        break;
                    case 2:
                        mostraLezione(sceltaCorso, logged);
                        break;
                    case 3:
                        nuovaPrenotazione();
                        break;
                    case 0:
                        break;
                }
                do {
                    s = "";
                    System.out.print("Vuoi iscriversi ad un altra lezione?: ");
                    String str = br.readLine();
                    if (str.equals("si")) {
                        s = str;
                        mostraLezione(sceltaCorso, logged);
                    }
                    if (str.equals("no")) {
                        s = str;
                    }
                } while (s.equals(""));

            } catch (Exception e) {
                System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" + e.getMessage());
                System.exit(-12);
            }
        }else{
            System.out.println("\nNON CI SONO LEZIONI ATTUALEMTEN PRENOTABILI");
            nuovaPrenotazione();
        }
    }

    public void confermaPrenotazione(String idLezione, Cliente logged){
        List<String> slotPrenotati = new ArrayList<>();
        Lezione lezioneSelezionata=corsoCorrente.getElencoLezioni().get(idLezione);
        for (String key : logged.getElencoPrenotazioni().keySet()) {
            slotPrenotati.add(logged.getElencoPrenotazioni().get(key).getSlot().getDataora());
        }
        if((lezioneSelezionata.postiDisponibili()-lezioneSelezionata.getElencoPrenotazioni().size())!=0){
            if(!slotPrenotati.contains(lezioneSelezionata.getSlot().getDataora())){
                Prenotazione p = corsoCorrente.confermaPrenotazione(idLezione, logged.getIdCliente());
                logged.addPrenotazione(p);
             }else System.out.println("Hai gia una lezione prenotata coincidente ");
        }else System.out.println("Posti per la lezione pieni");
       /* if (!logged.getElencoPrenotazioni().isEmpty()){


            int flag=0;
            for (String key : logged.getElencoPrenotazioni().keySet()) {
                Prenotazione pLogged= logged.getElencoPrenotazioni().get(key);
                String slotCliente=pLogged.getSlot().getDataora();
                String SlotLezione= corsoCorrente.getElencoLezioni().get(idLezione).getSlot().getDataora();
                if(slotCliente.equals(SlotLezione)) {
                    flag=0;
                    break;
                }else {
                    flag=1;
                }
            }
            if (flag==1){
                System.out.println("PRENOTAZIONE EFFETTUATA CON SUCCESSO");
                Prenotazione p = corsoCorrente.confermaPrenotazione(idLezione, logged.getIdCliente());
                logged.addPrenotazione(p);
            }if(flag==0) {
                System.out.println("HAI GIA UNA PRENOTAZIONE");
            }
        }else {
            System.out.println("PRIMA PRENOTAZIONE EFFETTUATA CON SUCCESSO");
            Prenotazione p = corsoCorrente.confermaPrenotazione(idLezione, logged.getIdCliente());
            logged.addPrenotazione(p);
        }*/
    }


    //CASO DUSO DI AVVIAMENTO
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
            BufferedReader bcliente = new BufferedReader(new FileReader("clienti.txt"));
            BufferedReader bprenotazioni= new BufferedReader(new FileReader("prenotazioni.txt"));

            str = bcorsi.readLine();
            while (str != null){
                strings=str.split("-");
                Attrezzo a=elencoAttrezzi.get((strings[4]));
                Corso c= new Corso(strings[0], strings[1], strings[2], strings[3],a);
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
            str = bcliente.readLine();
            while (str != null){
                strings=str.split("-");
                Cliente c=new Cliente(strings[0], strings[1], strings[2], strings[3]);
                elencoClienti.put(strings[0],c);
                str = bcliente.readLine();
            }
            str = bprenotazioni.readLine();
            while (str != null){
                strings=str.split("-");
                List<String> slotPrenotati = new ArrayList<>();
                for(String key : elencoCorsi.keySet()){
                    if(elencoCorsi.get(key).getElencoLezioni().containsKey(strings[0])){
                        corsoCorrente=elencoCorsi.get(key);
                        break;
                    }
                }
                Cliente logged=elencoClienti.get(strings[1]);
                Lezione lezioneSelezionata=corsoCorrente.getElencoLezioni().get(strings[0]);
                for (String key : logged.getElencoPrenotazioni().keySet()) {
                    slotPrenotati.add(logged.getElencoPrenotazioni().get(key).getSlot().getDataora());
                }
                if((lezioneSelezionata.postiDisponibili()-lezioneSelezionata.getElencoPrenotazioni().size())!=0){
                    if(!slotPrenotati.contains(lezioneSelezionata.getSlot().getDataora())){
                        Prenotazione p = corsoCorrente.confermaPrenotazione(strings[0], logged.getIdCliente());
                        logged.addPrenotazione(p);
                    }else System.out.println("Hai gia una lezione prenotata coincidente ");
                }else System.out.println("Posti per la lezione pieni");
                str = bprenotazioni.readLine();
            }

            System.out.println("\nI corsi e le lezioni sono stati inseriti con successo\n");
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DELLA PALESTRA\n" );
            System.exit(-9);
        }
    }

    public static String randId(){
        return String.valueOf((abs((int) System.currentTimeMillis() + (int)(Math.random()*(1000000000)))/10));
    }

    //GET E SET STANDARD
    public Map<String, Attrezzo> getElencoAttrezzi() {
        return elencoAttrezzi;
    }

    public Map<String, Corso> getElencoCorsi() {
        return elencoCorsi;
    }

    public Map<String, Sala> getElencoSale() {
        return elencoSale;
    }

    public Map<String, Istruttore> getElencoIstruttori() {
        return elencoIstruttori;
    }

    public Map<String, Cliente> getElencoClienti() {
        return elencoClienti;
    }
}
