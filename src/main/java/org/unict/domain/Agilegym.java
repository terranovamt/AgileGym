package org.unict.domain;

import org.unict.domain.exception.*;

import java.util.*;
import java.io.*;

import static java.lang.Math.abs; //rand num


public class Agilegym {
    static  Agilegym agilegym;
    private Corso corsoCorrente;
    private  Map<String, Cliente> elencoClienti;
    private  Map<String, Attrezzo> elencoAttrezzi;
    private  Map<String, Corso> elencoCorsi;
    private  Map<String, Sala> elencoSale;
    private  Map<String, Istruttore> elencoIstruttori;

    private Agilegym() throws IOException {
        this.elencoCorsi = new TreeMap<>();
        this.elencoAttrezzi  = new TreeMap<>();
        this.elencoSale = new TreeMap<>();
        this.elencoIstruttori = new TreeMap<>();
        this.elencoClienti = new TreeMap<>();
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
            this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus, attrezzoSelezionato);
            confermaCorso(this.corsoCorrente);
        } catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA: "+e.getMessage());
            System.exit(-7);
        }
    }

    public void confermaCorso(Corso corsoCorrente){
        String s;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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

    public void inserisciLezione(Corso corsoCorrente){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String  s, idSalaSelezionata, idIstruttoreSelezionato,idSlotSelezionato = "", idLezione ="";
        String idSlot;
        Sala salaSelezionata = null;
        Istruttore istruttoreSelezionato = null;
        this.corsoCorrente = corsoCorrente;

        try {

            System.out.println("-------Inserisci una Lezione-------\n");
            if (corsoCorrente.getIdSaleAttrezzate().size()==1){
                System.out.println("E' disponibile solo la sala: "+corsoCorrente.getIdSaleAttrezzate().get(0));
                salaSelezionata = elencoSale.get(String.valueOf(corsoCorrente.getIdSaleAttrezzate().get(0)));
            }else {
            System.out.println("Elenco degli Sale disponibili per l'attrezzo ~" +corsoCorrente.getAttrezzo().getIdAttrezzo()+"~:");
            System.out.print(corsoCorrente.getAttrezzo().stampaListaSale());
            //SCELTA DELLA SALA
                do {
                    System.out.print("Inserisci una sala Disponibile: ");
                    idSalaSelezionata = br.readLine();
                    if(corsoCorrente.getIdSaleAttrezzate().contains(idSalaSelezionata)){
                        salaSelezionata = elencoSale.get(idSalaSelezionata);
                    }
                }while (salaSelezionata ==null);

            }

            //SCELTA DELLO SLOT
            if (salaSelezionata.getSlotDisponibili().size()==1){
                idSlot = salaSelezionata.getSlotDisponibili().get(0);
                System.out.println("E' disponibile solo lo Slot: "+idSlot);
                idSlot = salaSelezionata.getSlotDisponibili().get(0);
            }else{
                System.out.println("Elenco degli Slot disponibili per la sala ~" + salaSelezionata.getIdSala()+"~:");
                for (String slot:salaSelezionata.getSlotDisponibili()) {
                    System.out.print("\tID-Slot: "+ slot +"\n");
                }

                do {
                    System.out.print("Inserisci uno slot Disponibile: ");
                    idSlot = br.readLine();
                    if(salaSelezionata.getSlotDisponibili().contains(idSlot)){
                        idSlotSelezionato = idSlot;
                    }
                }while (idSlotSelezionato.equals(""));
            }
            //SCELTA DELL'ISTRUTTORE
            Iterator<Map.Entry<String, Istruttore>> it = elencoIstruttori.entrySet().iterator();
            Map<String,Istruttore> elencoIstruttoriDisponibili=new TreeMap<>();

            while (it.hasNext()) {
                Map.Entry<String, Istruttore> entry = it.next();
                Istruttore i = entry.getValue();
                if (i.isDisponibile()) {
                    elencoIstruttoriDisponibili.put(i.getIdIstruttore(), i);
                }
            }
            if (elencoIstruttoriDisponibili.size()==0){
                System.out.println("NON SONO DISPONIBILI ISTUTTORI SCEGLI UN ALTRO SLOT");
                inserisciLezione(corsoCorrente);
            }
            if (elencoIstruttoriDisponibili.size()==1){
                for (String key : elencoIstruttoriDisponibili.keySet()) {//STAMPA ISTRUTTORI DISPONIBILI
                    System.out.println("E' disponibile solo l'istruttore: " + elencoIstruttoriDisponibili.get(key).getIdIstruttore());
                    istruttoreSelezionato = elencoIstruttori.get(elencoIstruttoriDisponibili.get(key).getIdIstruttore());
                }
            }
            else {
                System.out.println("Elenco degli Istruttori disponibili per lo Slot ~" + idSlot + "~:");
                for (String key : elencoIstruttoriDisponibili.keySet()) {//STAMPA ISTRUTTORI DISPONIBILI
                    System.out.println("\t" + elencoIstruttoriDisponibili.get(key).getIdIstruttore());
                }
                do {
                    System.out.print("Inserisci una istruttore Disponibile: ");
                    idIstruttoreSelezionato = br.readLine();
                    if (elencoIstruttoriDisponibili.containsKey(idIstruttoreSelezionato)) {
                        istruttoreSelezionato = elencoIstruttori.get(idIstruttoreSelezionato);
                    }
                } while (istruttoreSelezionato == null);
                //System.out.println(istruttoreSelezionato);
            }
            //TUTTE LE SCELTE SONO ANDATE A BUON FINE
            idLezione+=corsoCorrente.getIdCorso()+salaSelezionata.getIdSala()+idSlot;
            System.out.println("\n------------RIEPILOGO------------");
            System.out.println( "LEZIONE: \n" +
                                "\tID: " + idLezione + "\n" +
                                "\tNome Corso: " + corsoCorrente.getNomeCorso() + "\n" +
                                "\tSala: " + salaSelezionata.getIdSala()+ "\n" +
                                "\tIstruttore: " + istruttoreSelezionato.getIdIstruttore() + "\n" +
                                "\tI#D-Slot: " + idSlot);
            System.out.println("\n--------------------------------\n");

            confermaLezione(idSlot,salaSelezionata,istruttoreSelezionato);

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
        }catch (IOException e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" +e.getMessage());
            System.exit(-8);
        }
    }

    public void confermaLezione(String idSlot, Sala salaSelezionata, Istruttore istruttoreSelezionato){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String  s, idLezione = randId();
        try {
            do {
                s="";
                System.out.print("Vuoi inserire questa lezione? (si/no): ");
                String str=br.readLine();
                if (str.equals("si")){
                    s=str;
                    corsoCorrente.inserisciLezione(idLezione, idSlot, corsoCorrente , salaSelezionata, istruttoreSelezionato);
                    salaSelezionata.setOccupato(idSlot);
                    istruttoreSelezionato.setOccupato(idSlot);
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
    public void nuovaPrenotazione(String idCliente){
        int i=0;
        Map<Integer,Corso> corsi =new TreeMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Cliente logged = elencoClienti.get(idCliente);

        try {

            for (String key : elencoCorsi.keySet()) {
                i++;
                corsi.put(i,getElencoCorsi().get(key));
                System.out.println(elencoCorsi.get(key).stampaCorsi());
            }
            int scelta = -1;
            do{
                System.out.print("Scegli un corso: ");
                int s=Integer.parseInt(br.readLine());
                if (s==0){
                    return;
                }
                if(s>0 && s <= elencoCorsi.size()){
                    scelta=s;
                    corsoCorrente =elencoCorsi.get(corsi.get(scelta).getIdCorso());
                    mostraLezione(logged);
                }
            }while(scelta==-1);
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" +e.getMessage());
            System.exit(-11);
        }
    }

    public void mostraLezione(Cliente logged){
        int i;

        Map<String,Lezione> lezioniDisponibili;
        Map<Integer,Lezione> sceltaLezioni =new TreeMap<>();
        Map<String,Prenotazione> elencoPrenotazioneCliente;
        String s;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Lezione lezioneCorrente;

        elencoPrenotazioneCliente=logged.getElencoPrenotazioni();
        System.out.println();
        try {
            lezioniDisponibili=corsoCorrente.mostraLezioni(elencoPrenotazioneCliente);
            if (lezioniDisponibili.size()!=0) {
            i = 0;
            for (String key : lezioniDisponibili.keySet()) {
                i++;
                System.out.println("LEZIONE: " + i);
                sceltaLezioni.put(i, lezioniDisponibili.get(key));
                System.out.println(lezioniDisponibili.get(key));
            }

            int scelta = -1;

                do {
                    System.out.print("Scegli una lezione: ");
                    int s1 = Integer.parseInt(br.readLine());
                    if (s1==0){
                        return;
                    }
                    if (s1 > 0 && s1 <= lezioniDisponibili.size()) {
                        scelta = s1;
                    }
                } while (scelta == -1);
                lezioneCorrente = sceltaLezioni.get(scelta);

                System.out.println("\nLEZIONE SCELTA: " + scelta + "\n" + lezioneCorrente.stampaRiepilogo());

                System.out.print("-------MENU-------\n 1. Conferma la scelta\n 2. Cambia Lezione\n 3. Cambia Corso\n 0.Menu principale\nScelta:");
                int menu = Integer.parseInt(br.readLine());
                switch (menu) {
                    case 1:
                        confermaPrenotazione(lezioneCorrente.getIdLezione(), logged);
                        break;
                    case 2:
                        mostraLezione(logged);
                        break;
                    case 3:
                        nuovaPrenotazione(logged.getIdCliente());
                        break;
                    case 0:
                        break;
                }
                do {
                    s = "";
                    System.out.print("Vuoi iscriverti ad un altra lezione?: ");
                    String str = br.readLine();
                    if (str.equals("si")) {
                        s = str;
                        mostraLezione(logged);
                    }
                    if (str.equals("no")) {
                        s = str;
                        nuovaPrenotazione(logged.getIdCliente());
                    }
                } while (s.equals(""));
            }else{
                System.out.println("\nNON CI SONO LEZIONI ATTUALEMTE PRENOTABILI");
                nuovaPrenotazione(logged.getIdCliente());
            }
        } catch (IOException e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" + e.getMessage());
            System.exit(-12);
        } catch (SalaPienaException | ClienteOccupatoException | PrenotazionePresenteException | LezioneNonPresente e) {
            e.printStackTrace();
        }

    }

    public void confermaPrenotazione(String idLezione, Cliente logged) throws PrenotazionePresenteException, LezioneNonPresente {
        List<String> slotPrenotati = new ArrayList<>();
        Lezione lezioneSelezionata=corsoCorrente.getElencoLezioni().get(idLezione);
        for (String key : logged.getElencoPrenotazioni().keySet()) {
            slotPrenotati.add(logged.getElencoPrenotazioni().get(key).getIdSlot());
        }
        if((lezioneSelezionata.postiDisponibili()-lezioneSelezionata.getElencoPrenotazioni().size())!=0){
            if(!slotPrenotati.contains(lezioneSelezionata.getIdSlot())){
                Prenotazione p = corsoCorrente.confermaPrenotazione(idLezione, logged.getIdCliente());
                logged.addPrenotazione(p);
             }else System.out.println("Hai gia una lezione prenotata coincidente ");
        }else System.out.println("Posti per la lezione pieni");
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
            BufferedReader bCorsi = new BufferedReader(new FileReader("corsi.txt"));
            BufferedReader bLezioni = new BufferedReader(new FileReader("lezioni.txt"));
            BufferedReader bCliente = new BufferedReader(new FileReader("clienti.txt"));
            BufferedReader bPrenotazioni= new BufferedReader(new FileReader("prenotazioni.txt"));

            str = bCorsi.readLine();
            while (str != null){
                strings=str.split("-");
                Attrezzo a=elencoAttrezzi.get((strings[4]));
                Corso c= new Corso(strings[0], strings[1], strings[2], strings[3],a);
                this.elencoCorsi.put(strings[0],c);
                str = bCorsi.readLine();
            }

            str = bLezioni.readLine();
            while (str != null){
                strings=str.split("-");
                Corso c=elencoCorsi.get(strings[1]);
                String idLezione=strings[0];
                Sala s=elencoSale.get(strings[2]);
                Istruttore i=elencoIstruttori.get(strings[3]);
                String idSlot=strings[4];
                c.inserisciLezione(idLezione,idSlot ,c,s,i);
                s.setOccupato(idSlot);
                i.setOccupato(idSlot);
                str = bLezioni.readLine();
            }
            str = bCliente.readLine();
            while (str != null){
                strings=str.split("-");
                Cliente c=new Cliente(strings[0], strings[1], strings[2], strings[3]);
                elencoClienti.put(strings[0],c);
                str = bCliente.readLine();
            }
            str = bPrenotazioni.readLine();
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
                    slotPrenotati.add(logged.getElencoPrenotazioni().get(key).getIdSlot());
                }
                if((lezioneSelezionata.postiDisponibili()-lezioneSelezionata.getElencoPrenotazioni().size())!=0){
                    if(!slotPrenotati.contains(lezioneSelezionata.getIdSlot())){
                        Prenotazione p = corsoCorrente.confermaPrenotazione(strings[0], logged.getIdCliente());
                        logged.addPrenotazione(p);
                    }else System.out.println("Hai gia una lezione prenotata coincidente ");
                }else System.out.println("Posti per la lezione pieni");
                str = bPrenotazioni.readLine();
            }

            System.out.println("\nI corsi e le lezioni sono stati inseriti con successo");
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DELLA PALESTRA\n" );
            System.exit(-9);
        } catch (CorsoException | PrenotazionePresenteException | LezioneNonPresente e) {
            e.printStackTrace();
        }
    }

    //FUNZIONI DI UTILITY
    public void scegliCliente(){
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
            nuovaPrenotazione(logged.getIdCliente());
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" +e.getMessage());
            System.exit(-11);
        }
    }

    public static String randId(){
        String s=String.valueOf((abs((int) System.currentTimeMillis() + (int)(Math.random()*(1000000000)))/10));
        return s.substring(3,7);
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
