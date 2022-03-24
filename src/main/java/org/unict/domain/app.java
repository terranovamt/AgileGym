package org.unict.domain;

import org.unict.domain.exception.*;

import java.io.*;
import java.util.*;

import static org.unict.domain.Agilegym.agilegym;

public class app {


    public static void main (String [] args) {
        int scelta;
        Agilegym agilegym = Agilegym.getInstance();
        System.out.println("\n\n");
        System.out.println(logo());
        String idCliente=login();
        do {

            if (idCliente.equals("ADMIN")) {
                do {
                    scelta = menuAdmin();
                    switch (scelta) {
                        case 1 -> inserisciCorso();
                        case 2 -> {
                            if (agilegym.getElencoCorsi().isEmpty()) {
                                stampaCorsi();
                                break;
                            }
                            selezionaCorso();
                        }
                        case 3 -> {
                            if (agilegym.getElencoCorsi().isEmpty()) {
                                stampaCorsi();
                                break;
                            }
                            Cliente logged=agilegym.loginCliente();
                            getCorsiPrenotabili(logged);
                        }
                        case 4 -> {
                            if (agilegym.getElencoCorsi().isEmpty()) {
                                stampaCorsi();
                                break;
                            }
                            Cliente logged=agilegym.loginCliente();
                            modificaPrenotazione(logged);
                        }
                        case 5 -> stampaCorsi();
                        case 6 -> stampaSale();
                        case 7 -> stampaIstruttori();
                        case 8 -> stampaPrenotazioni();
                        case 9 -> agilegym.riempiPalestra();
                        case 0 -> idCliente=login();
                    }
                } while (idCliente.equals("ADMIN"));
            }
            else {
                do {
                    scelta = menuCliente(idCliente);
                    Cliente logged=agilegym.getElencoClienti().get(idCliente);
                    switch (scelta) {
                        case 1 -> getCorsiPrenotabili(logged);
                        case 2 -> stampaPrenotazioniCliente(idCliente);
                        case 3 -> modificaPrenotazione(logged);
                        case 0 -> idCliente=login();
                    }
                } while (!idCliente.equals("ADMIN"));
            }
        }while (true);
    }

    public static String login(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String username, logged=null;
        try {
            do {
                System.out.println("\n#--------------------------------------------------------------#");
                System.out.println("#----------------------------LOGIN-----------------------------#");
                System.out.println("#--------------------------------------------------------------#\n");
                System.out.print("Inserisci username: ");
                username = br.readLine();
                if (username.equals("ADMIN")){
                    logged=username;
                }
                if (username.equals("0")){
                    System.exit(0);
                }
                if(agilegym.getElencoClienti().containsKey(username)){
                    logged=agilegym.getElencoClienti().get(username).getIdCliente();
                }
            }while (logged ==null);
        }catch(IOException e){
            System.out.println("ERRORE!"+e.getMessage());
            System.exit(-1);
        }
        return logged;
    }

    public static int menuAdmin(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("\n#--------------------------------------------------------------#");
            System.out.println("#--------------------------MENU ADMIN--------------------------#");
            System.out.println("#--------------------------------------------------------------#\n");
            System.out.println("1. Inserisci Corso");
            System.out.println("2. Inserisci Lezione");
            System.out.println("3. Nuova Prenotazione");
            System.out.println("4. Modifica Prenotazione");
            System.out.println("#--------------------------------------------------------------#");
            System.out.println("5. Stampa Corsi");
            System.out.println("6. Stampa Sale");
            System.out.println("7. Stampa Istruttori");
            System.out.println("8. Stampa Prenotazioni");
            System.out.println("9. Riempi Palestra");
            System.out.println("0. LOGOUT");
            System.out.print("Scelta: ");
            return Integer.parseInt(br.readLine());
        }catch(IOException e){
            System.out.println("ERRORE!");
            return -13;// NUMERO PIU' ALTO DI EXIT
        }
    }

    public static int menuCliente(String username){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("\n#--------------------------------------------------------------#");
            System.out.println("#-----------------------------MENU-----------------------------#");
            System.out.println("#--------------------------------------------------------------#\n");
            System.out.println("Benvenuto " + agilegym.getElencoClienti().get(username).getNome() + "\n");
            System.out.println("1. Nuova Prenotazione");
            System.out.println("2. Stampa Prenotazioni");
            System.out.println("3. Modifica Prenotazione");
            System.out.println("0. LOGOUT");
            System.out.print("Scelta: ");
            return Integer.parseInt(br.readLine());
        }catch(IOException e){
            System.out.println("ERRORE!");
            return -13;// NUMERO PIU' ALTO DI EXIT
        }
    }

    //CASE1
    public static void inserisciCorso(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String nomeCorso = "";
        String livello = "";
        String focus = "";
        String idAttrezzo = "";
        Attrezzo attrezzoSelezionato;

        System.out.println("-------Inserisci un corso-------\n");
        try {
            System.out.print("Inserisci il nome del corso: ");
            nomeCorso = br.readLine();
            if (Objects.equals(nomeCorso, "0")) return;
            System.out.print("Inserisci il livello del corso: ");
            livello = br.readLine();
            System.out.print("Inserisci il focus del corso: ");
            focus = br.readLine();
            //L'amministratore inserisce l'id dell'attrezzo che vuole per quel corso, si cerca se quell'attrezzo
            //è presente nella lista degli attrezzi, una volta trovato, si prende quell'attrezzo e lo si inserisce nel corso
            System.out.print("Attrezzi della palestre: ");
            for (String key : agilegym.getElencoAttrezzi().keySet()) {
                System.out.print(agilegym.getElencoAttrezzi().get(key).getIdAttrezzo() + ", ");
            }
            System.out.print("\n");
            do {
                System.out.print("Inserisci l'ID dell'attrezzo del corso: ");
                idAttrezzo = br.readLine();
                attrezzoSelezionato = agilegym.getElencoAttrezzi().get(idAttrezzo);
            } while (attrezzoSelezionato == null);

        } catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA: " + e.getMessage());
            System.exit(-7);
        }
        try {
            Corso corsoCorrente = agilegym.nuovoCorso(nomeCorso, livello, focus, idAttrezzo);
            String s,s1="",s2="";
            System.out.println("\n--------RIEPILOGO CORSO--------");
            System.out.println(corsoCorrente + "\n--------------------------------\n");
            do {
                System.out.print("Vuoi inserire questo corso? (si/no): ");
                s=br.readLine();
                if (s.equals("si")||s.equals("no")) {
                    s1=s;
                    if(s1.equals("si")) {
                        if(agilegym.confermaCorso(corsoCorrente)){
                            System.out.println("\nCORSO INSERITO CORRETTAMENTE\n");
                        }
                    }
                    else break;
                    do {
                        System.out.print("Vuoi inserire una lezione per questo corso? (si/no): ");
                        s=br.readLine();
                        if (s.equals("si")||s.equals("no")){
                            s2=s;
                            if(s2.equals("si")) {
                                Lezione l=inserisciLezione(corsoCorrente);
                                if(l==null) throw new LezioneException("ERRORE creazione lezione");
                                s2="";
                            }
                        }
                    }while (s2.equals(""));
                }
            } while (s1.equals(""));

        } catch (CorsoException | IOException | LezioneException | IstruttoreException e) {
            e.printStackTrace();
        }
    }

    public static Lezione inserisciLezione(Corso corsoSelezionato) throws IOException, IstruttoreException, CorsoException, LezioneException {
        List<String> listIdSale, listIdSlot, listIdIstruttore;
        String idSalaSelezionata = "",idSlotSelezionato="",idIstruttoreSelezionato="", controllo;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Lezione lezioneCorrente = null;

        if (corsoSelezionato!=null){

            //GET IDSALE
            System.out.println("-------Inserisci una Lezione-------\n");
            listIdSale = agilegym.getSale(corsoSelezionato);
            if (listIdSale.size() == 1) {
                idSalaSelezionata=listIdSale.get(0);
                System.out.println("E' disponibile solo lo sala: " +idSalaSelezionata);
            }else {
                System.out.println("Elenco degli Sale disponibili per l'attrezzo ~" + corsoSelezionato.getAttrezzo().getIdAttrezzo() + "~:");
                for (String value : listIdSale) {
                    System.out.print("\t"+value + "\n");
                }
            }

            //SCELTA DELLA SALA
            while (idSalaSelezionata.equals("")){
                System.out.print("Inserisci una sala Disponibile: ");
                controllo = br.readLine();
                if (listIdSale.contains(controllo)) {
                    idSalaSelezionata=controllo;
                }
            }

            //GET IDSLOT
            listIdSlot= agilegym.getSlot(idSalaSelezionata);
            if (listIdSlot.size()==1){
                idSlotSelezionato = listIdSlot.get(0);
                System.out.println("E' disponibile solo lo Slot: "+idSlotSelezionato);
            }else{
                System.out.println("Elenco degli slot disponibili per la sala ~" + idSalaSelezionata+"~:");;
                int i=49;
                for (String value : listIdSlot) {
                    if(value.charAt(0)==i|value.charAt(1)=='-') {
                        if (value.charAt(1)=='-')System.out.print("\t---");
                        else System.out.print("\t" + value);
                    }
                    else {
                        i++;
                        System.out.print("\n\t" + value );
                    }
                }
            }
            System.out.println();

            //SCELTA SLOT
            while (idSlotSelezionato.equals("")) {
                System.out.print("Inserisci uno slot disponibile: ");
                controllo = br.readLine();
                if (listIdSlot.contains(controllo)) {
                    idSlotSelezionato = controllo;
                }
            }
            //GET ID ISTRUTTORI
            listIdIstruttore= agilegym.getIstruttori(idSlotSelezionato);
            if (listIdIstruttore.size()==1){
                idIstruttoreSelezionato = listIdIstruttore.get(0);
                System.out.println("E' disponibile solo l'istruttore: "+idIstruttoreSelezionato);
            }else{
                System.out.println("Elenco degli Istruttori disponibili per lo slot ~" + idSlotSelezionato+"~:");
                for (String value : listIdIstruttore) {
                    System.out.print("\t" + value + "\n");
                }
            }
            //SCELTA ISTRUTTORE
            while (idIstruttoreSelezionato.equals("")) {
                System.out.print("Inserisci un'istruttore disponibile: ");
                controllo = br.readLine();
                if (listIdIstruttore.contains(controllo)) {
                    idIstruttoreSelezionato = controllo;
                }
            }
            lezioneCorrente=agilegym.creaLezione(corsoSelezionato,idSalaSelezionata,idSlotSelezionato,idIstruttoreSelezionato);

            if(lezioneCorrente !=null) {
                String st, s1 = "";
                System.out.println("\n------------RIEPILOGO------------");
                System.out.println(lezioneCorrente);
                System.out.println("--------------------------------\n");
                do {
                    System.out.print("Vuoi inserire questa lezione? (si/no): ");
                    st = br.readLine();
                    if (st.equals("si") || st.equals("no")) {
                        s1 = st;
                        if (s1.equals("si")) {
                            if(agilegym.confermaLezione(lezioneCorrente)){
                                System.out.println("\nLEZIONE INSERITA CORRETTAMENTE\n");
                            }
                        }
                    }

                } while (s1.equals(""));
            }
            else throw new LezioneException("Errore nella creazione della lezione");
        }
        return lezioneCorrente;
    }

    //CASE2
    public static void selezionaCorso(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Corso corsoSelezionato=null;
        if (agilegym.getElencoCorsi().isEmpty()) {
            stampaCorsi();
            return;
        }
        try{
            int n=0;
            for (String key : agilegym.getElencoCorsi().keySet()) {
                n++;
                System.out.println("CORSO: "+ n );
                System.out.println(agilegym.getElencoCorsi().get(key).stampaCorsi());
            }
            System.out.print("\n");
            int s = 0;
            do{
                System.out.print("Scegli un corso, 0 per tornare a MENU:");
                int s1=Integer.parseInt(br.readLine());
                if (s1==0){
                    return;
                }
                if(s1>0 && s1 <= agilegym.getElencoCorsi().size()) {
                    n = 0;
                    s = s1;
                    Map<Integer,Corso> corsi=new TreeMap<>();
                    for (String key : agilegym.getElencoCorsi().keySet()) {
                        n++;
                        corsi.put(n, agilegym.getElencoCorsi().get(key));
                    }
                    corsoSelezionato = corsi.get(s);
                }
            }while(s==0);

            inserisciLezione(corsoSelezionato);


        }catch(IOException e){
            System.out.println("ERRORE INPUT DA TASTIERA!");
            System.exit(-1);
        } catch (IstruttoreException | CorsoException | LezioneException e) {
            e.printStackTrace();
        }
    }

    //CASE3
    public static void getCorsiPrenotabili(Cliente logged){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            List<Corso> corsi =agilegym.nuovaPrenotazione();
            int index=0, controllo, i = 0;
            for (Corso value : corsi){
                i++;
                System.out.println("CORSO: " + i);
                System.out.print(value.stampaCorsi() + "\n");
            }
            do {
                System.out.print("Scegli un corso, 0 per tornare a MENU: ");
                controllo = Integer.parseInt(br.readLine());
                if (controllo == 0) return;
                if (controllo > 0 && controllo <= corsi.size()) {
                    index = controllo;
                    String idCorso = corsi.get(index - 1).getIdCorso();
                    getLezioniPrenotabili(idCorso, logged);
                }
            }while (index==0);
        }catch(IOException e){
        System.out.println("ERRORE!"+e.getMessage());
        System.exit(-1);
        }
    }

    public static void getLezioniPrenotabili(String idCorso, Cliente logged){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Lezione> lezioniDisponibili=agilegym.mostraLezione(idCorso,logged);
        Lezione lezioneCorrente = null;
        int index=0,i,controllo;
        try {
            System.out.println("\n ELENCO LEZIONI PRENOTABILI\n");
            if (lezioniDisponibili.size() != 0) {
                i = 0;
                for (Lezione value : lezioniDisponibili) {
                    i++;
                    System.out.println("LEZIONE: " + i);
                    System.out.println(value.stampaRiepilogo());
                }
                do {
                    System.out.print("Scegli una lezione, 0 per tornare a MENU: ");
                    controllo = Integer.parseInt(br.readLine());
                    if (controllo == 0) return;
                    if (controllo > 0 && controllo <= lezioniDisponibili.size()) {
                        index = controllo;
                        lezioneCorrente = lezioniDisponibili.get(index - 1);
                    }
                }while (index==0);
                System.out.println("\nLEZIONE SCELTA: " + index + "\n" + lezioneCorrente.stampaRiepilogo());
                sottoMenu(lezioneCorrente,logged,idCorso);
            }else System.out.print("NON CI SONO LEZIONI PRENOTABILI");
        }catch (IOException e){
            System.out.println("ERRORE!"+e.getMessage());
            System.exit(-1);
        }
    }

    public static void sottoMenu(Lezione lezioneCorrente, Cliente logged, String idCorso){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("-------MENU-------\n 1. Conferma la scelta\n 2. Cambia Lezione\n 3. Cambia Corso\n 0.Menu principale\nScelta:");
        try {
            int menu = Integer.parseInt(br.readLine());
            switch (menu) {
                case 1:
                    agilegym.confermaPrenotazione(lezioneCorrente.getIdLezione(), logged);
                    String s;
                    do {
                        s = "";
                        System.out.print("Vuoi iscriverti ad un altra lezione?: ");
                        String str = br.readLine();
                        if (str.equals("si")) {
                            s = str;
                            getLezioniPrenotabili(idCorso,logged);
                        }
                        if (str.equals("no"))return;
                    } while (s.equals(""));
                    break;
                case 2:
                    getLezioniPrenotabili(idCorso,logged);
                    break;
                case 3:
                    getCorsiPrenotabili(logged);
                    break;
                case 0:
                    break;
            }

        }catch(IOException e){
            System.out.println("ERRORE!"+e.getMessage());
            System.exit(-1);
        } catch (LezioneException | PrenotazionePresenteException e) {
            e.printStackTrace();
        }
    }

    //CASE4
    public static void modificaPrenotazione(Cliente logged){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Prenotazione> prenotazioni;
        int index=0, controllo, i = 0;
        System.out.println();
        try {
            prenotazioni =agilegym.modificaPrenotazione(logged.getIdCliente());
            System.out.println("\nELENCO PRENOTAZIONI MODIFICABILI\n");
            for (Prenotazione value : prenotazioni){
                i++;
                System.out.println("Prenotazione: " + i);
                System.out.print("\t" + value + "\n");
            }
            do {
                System.out.print("Scegli un prenotazione, 0 per tornare a MENU: ");
                controllo = Integer.parseInt(br.readLine());
                if (controllo == 0) return;
                if (controllo > 0 && controllo <= prenotazioni.size()) {
                    index = controllo;
                    Prenotazione p = prenotazioni.get(index - 1);
                    getLezioni(p, logged);


                }
            }while (index==0);
        }catch(IOException e){
            System.out.println("ERRORE!"+e.getMessage());
            System.exit(-1);
        }

    }

    public static void getLezioni(Prenotazione prenotazione, Cliente logged){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Lezione> lezioniDisponibili= agilegym.getLezioni(prenotazione, logged);
        Lezione lezioneCorrente = null;
        int index=0, i, controllo;
        try {
            System.out.println("\nELENCO DELLE LEZIONI PRENOTABILI:");
            if (lezioniDisponibili.size() != 0) {
                i = 0;
                for (Lezione value : lezioniDisponibili) {
                    i++;
                    System.out.println("LEZIONE: " + i);
                    System.out.println(value.stampaRiepilogo());
                }
                do {
                    System.out.print("Scegli una lezione, 0 per tornare a MENU: ");
                    controllo = Integer.parseInt(br.readLine());
                    if (controllo == 0) return;
                    if (controllo > 0 && controllo <= lezioniDisponibili.size()) {
                        index = controllo;
                        lezioneCorrente = lezioniDisponibili.get(index - 1);
                    }
                }while (index==0);
                System.out.println("\nLEZIONE SCELTA: " + index + "\n" + lezioneCorrente.stampaRiepilogo());
                updatePrenotazione(lezioneCorrente,logged, prenotazione);
            }else System.out.print("NON CI SONO LEZIONI PRENOTABILI\n");
        }catch (IOException e){
            System.out.println("ERRORE!"+e.getMessage());
            System.exit(-1);
        }
    }

    public static void updatePrenotazione(Lezione lezione, Cliente logged, Prenotazione prenotazione){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            if(lezione !=null) {
                String st, s1 = "";
                do {
                    System.out.print("Vuoi modificare questa prenotazione (si/no): ");
                    st = br.readLine();
                    if (st.equals("si") || st.equals("no")) {
                        s1 = st;
                        if (s1.equals("si")) {
                            if(agilegym.updatePrenotazione(lezione,prenotazione,logged)){
                                System.out.println("\nPRENOTAZIONE AGGIORNATA CORRETTAMENTE");
                            }else throw new PrenotazionePresenteException("Errore nella modifica della prenotazione");
                        }else return;
                    }
                } while (s1.equals(""));
            }
            else throw new LezioneException("Errore nella selezione della lezione");
        } catch (IOException  e){
            System.out.println("ERRORE!"+e.getMessage());
            System.exit(-1);
        } catch (PrenotazionePresenteException | LezioneException e) {
            e.printStackTrace();
        }
    }

    //STAMPE
    public static void stampaCorsi(){
        int i=0;
        if(agilegym.getElencoCorsi().isEmpty()){
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.print    (  "#--------------NON CI SONO CORSI, INSERISCILI !!!--------------#");
            System.out.println  ("\n#--------------------------------------------------------------#\n");
        }
        for (String key: agilegym.getElencoCorsi().keySet()){
            i++;
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.print("CORSO: " + i +"\n");
            System.out.print(agilegym.getElencoCorsi().get(key));
                for (String key2 : agilegym.getElencoCorsi().get(key).getElencoLezioni().keySet()){
                    Lezione l = agilegym.getElencoCorsi().get(key).getElencoLezioni().get(key2);
                    if(l.getElencoPrenotazioni().size()!=0){
                        System.out.println("Lezione:\n" + l + "\t\t---\nElenco clienti:");
                        for(String key3 : l.getElencoPrenotazioni().keySet()) {
                            Prenotazione p = l.getElencoPrenotazioni().get(key3);
                            System.out.println(agilegym.getElencoClienti().get(p.getIdCliente()));
                        }
                    }
                    else  System.out.println("\nLezione:\n" + l);
                }
            System.out.print("\n");
        }
    }

    public static void stampaSale(){
        if(agilegym.getElencoSale().isEmpty()){
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.print    (  "#--------------NON CI SONO SALE, INSERISCILE !!!---------------#");
            System.out.println  ("\n#--------------------------------------------------------------#\n");
        }
        for (String key: agilegym.getElencoSale().keySet()){
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.println(agilegym.getElencoSale().get(key));
        }
    }

    public static void stampaIstruttori(){
        if(agilegym.getElencoIstruttori().isEmpty()){
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.print    (  "#-----------NON CI SONO ISTRUTTORI, INSERISCILI !!!------------#");
            System.out.println  ("\n#--------------------------------------------------------------#\n");
        }
        for (String key: agilegym.getElencoIstruttori().keySet()){
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.println(agilegym.getElencoIstruttori().get(key));
        }
    }

    public static void stampaPrenotazioni(){
        if(agilegym.getElencoClienti().isEmpty()){
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.print    (  "#---------------NON CI SONO CLIENTI, TROVALI !!!---------------#");
            System.out.println  ("\n#--------------------------------------------------------------#\n");
        }
        for (String key1 : agilegym.getElencoCorsi().keySet()){
            for (String key2 : agilegym.getElencoCorsi().get(key1).getElencoLezioni().keySet()){
                Lezione l = agilegym.getElencoCorsi().get(key1).getElencoLezioni().get(key2);
                if(l.getElencoPrenotazioni().size()!=0){
                    System.out.println("\nLezione:\n" + l + "Elenco clienti: ");
                    for(String key3 : l.getElencoPrenotazioni().keySet()) {
                        Prenotazione p = l.getElencoPrenotazioni().get(key3);
                        System.out.println("\t\t---\n" + agilegym.getElencoClienti().get(p.getIdCliente()));
                    }
                }
            }
        }
    }

    public static void stampaPrenotazioniCliente(String username){
       Cliente logged=agilegym.getElencoClienti().get(username);
       StringBuilder str = new StringBuilder();
        for (String key1 : logged.getElencoPrenotazioni().keySet()){
            str.append(logged.getElencoPrenotazioni().get(key1)).append("\n");
        }
        System.out.println("\nELENCO LEZIONI PRENOTATE\n" + str);
    }

    private static String logo() {
        String[] s = new String[3];
        s[0]= """
                     _              _   _           ____                    \s
                    / \\      __ _  (_) | |   ___   / ___|  _   _   _ __ ___ \s
                   / _ \\    / _` | | | | |  / _ \\ | |  _  | | | | | '_ ` _ \\\s
                  / ___ \\  | (_| | | | | | |  __/ | |_| | | |_| | | | | | | |
                 /_/   \\_\\  \\__, | |_| |_|  \\___|  \\____|  \\__, | |_| |_| |_|
                            |___/                          |___/            \s


                """;
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        s[1]= """
                 █████   ██████  ██ ██      ███████  ██████  ██    ██ ███    ███\s
                ██   ██ ██       ██ ██      ██      ██        ██  ██  ████  ████\s
                ███████ ██   ███ ██ ██      █████   ██   ███   ████   ██ ████ ██\s
                ██   ██ ██    ██ ██ ██      ██      ██    ██    ██    ██  ██  ██\s
                ██   ██  ██████  ██ ███████ ███████  ██████     ██    ██      ██\s
                """;
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        s[2]= """
                                                                                                                                                                                  \s
                               AAA                                      iiii  lllllll                               GGGGGGGGGGGGG                                                 \s
                              A:::A                                    i::::i l:::::l                            GGG::::::::::::G                                                 \s
                             A:::::A                                    iiii  l:::::l                          GG:::::::::::::::G                                                 \s
                            A:::::::A                                         l:::::l                         G:::::GGGGGGGG::::G                                                 \s
                           A:::::::::A              ggggggggg   ggggg  iiiiiii l::::l      eeeeeeeeeeee      G:::::G       GGGGGG yyyyyyy           yyyyyyy   mmmmmmm    mmmmmmm  \s
                          A:::::A:::::A            g:::::::::ggg::::g  i:::::i l::::l    ee::::::::::::ee   G:::::G                y:::::y         y:::::y  mm:::::::m  m:::::::mm\s
                         A:::::A A:::::A          g:::::::::::::::::g   i::::i l::::l   e::::::eeeee:::::ee G:::::G                 y:::::y       y:::::y  m::::::::::mm::::::::::m
                        A:::::A   A:::::A        g::::::ggggg::::::gg   i::::i l::::l  e::::::e     e:::::e G:::::G    GGGGGGGGGG    y:::::y     y:::::y   m::::::::::::::::::::::m
                       A:::::A     A:::::A       g:::::g     g:::::g    i::::i l::::l  e:::::::eeeee::::::e G:::::G    G::::::::G     y:::::y   y:::::y    m:::::mmm::::::mmm:::::m
                      A:::::AAAAAAAAA:::::A      g:::::g     g:::::g    i::::i l::::l  e:::::::::::::::::e  G:::::G    GGGGG::::G      y:::::y y:::::y     m::::m   m::::m   m::::m
                     A:::::::::::::::::::::A     g:::::g     g:::::g    i::::i l::::l  e::::::eeeeeeeeeee   G:::::G        G::::G       y:::::y:::::y      m::::m   m::::m   m::::m
                    A:::::AAAAAAAAAAAAA:::::A    g::::::g    g:::::g    i::::i l::::l  e:::::::e             G:::::G       G::::G        y:::::::::y       m::::m   m::::m   m::::m
                   A:::::A             A:::::A   g:::::::ggggg:::::g i::::::i l::::::l e::::::::e             G:::::GGGGGGGG::::G         y:::::::y        m::::m   m::::m   m::::m
                  A:::::A               A:::::A   g::::::::::::::::g i::::::i l::::::l  e::::::::eeeeeeee      GG:::::::::::::::G          y:::::y         m::::m   m::::m   m::::m
                 A:::::A                 A:::::A   gg::::::::::::::g i::::::i l::::::l   ee:::::::::::::e        GGG::::::GGG:::G         y:::::y          m::::m   m::::m   m::::m
                AAAAAAA                   AAAAAAA    gggggggg::::::g iiiiiiii llllllll     eeeeeeeeeeeeee           GGGGGG   GGGG        y:::::y           mmmmmm   mmmmmm   mmmmmm
                                                             g:::::g                                                                    y:::::y                                   \s
                                                 gggggg      g:::::g                                                                   y:::::y                                    \s
                                                 g:::::gg   gg:::::g                                                                  y:::::y                                     \s
                                                  g::::::ggg:::::::g                                                                 y:::::y                                      \s
                                                   gg:::::::::::::g                                                                 yyyyyyy                                       \s
                                                     ggg::::::ggg                                                                                                                 \s
                                                        gggggg                                                                                                                    \s
                """;
//---------------------------------------------------------------------------------------------------------------------------------------------------------------

            return s[1];
    }

}


