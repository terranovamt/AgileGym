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
                        case 1 -> {
                            inserisciCorso();
                        }
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
                        case 4 -> stampaCorsi();
                        case 5 -> stampaSale();
                        case 6 -> stampaIstruttori();
                        case 7 -> stampaPrenotazioni();
                        case 8 -> agilegym.riempiPalestra();
                        case 0 -> idCliente=login();
                    }
                } while (idCliente.equals("ADMIN"));
            }
            else {
                do {
                    scelta = menu(idCliente);
                    switch (scelta) {
                        case 1 -> {
                            Cliente logged=agilegym.getElencoClienti().get(idCliente);
                            getCorsiPrenotabili(logged);
                        }
                        case 2 -> stampaPrenotazioniCliente(idCliente);
                        //case 3 -> agilegym.modificaPrenotazione(logged);
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
            System.out.println("#--------------------------------------------------------------#");
            System.out.println("4. Stampa Corsi");
            System.out.println("5. Stampa Sale");
            System.out.println("6. Stampa Istruttori");
            System.out.println("7. Stampa Prenotazioni");
            System.out.println("8. Riempi Palestra");
            System.out.println("0. LOGOUT");
            System.out.print("Scelta: ");
            return Integer.parseInt(br.readLine());
        }catch(IOException e){
            System.out.println("ERRORE!");
            return -13;// NUMERO PIU' ALTO DI EXIT
        }
    }

    public static int menu(String username){
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
            String s,s1="",s2="",s3="";
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
                                inserisciLezione(corsoCorrente);
                                s2="";
                            }
                            else {
                                s2=s2;
                            }
                        }
                    }while (s2.equals(""));
                }
            } while (s1.equals(""));

        } catch (CorsoException | IOException | LezioneNonCreataException | IstruttoreException e) {
            e.printStackTrace();
        }
    }

    public static Lezione inserisciLezione(Corso corsoSelezionato) throws IOException, IstruttoreException, LezioneNonCreataException,CorsoException {
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
                System.out.println("Elenco degli slot disponibili per la sala ~" + idSalaSelezionata+"~:");
                for (String value : listIdSlot) {
                    System.out.print("\t" + value + "\n");
                }
            }
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
            lezioneCorrente=agilegym.creaLezione(idSalaSelezionata,idSlotSelezionato,idIstruttoreSelezionato);

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
            else throw new LezioneNonCreataException("Errore nella creazione della lezione");
        }
        return lezioneCorrente;
    }

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
        } catch (IstruttoreException | CorsoException | LezioneNonCreataException e) {
            e.printStackTrace();
        }
    }

    public static void getCorsiPrenotabili(Cliente logged){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Lezione lezioneCorrente = null;
        try {
            List<Corso> corsi =agilegym.nuovaPrenotazione();
            int index;
            int i = 0;
            for (Corso value : corsi){
                i++;
                System.out.println("CORSO: " + i);
                System.out.print(value.stampaCorsi() + "\n\n");
            }
            System.out.print("Scegli un corso:");
            index= Integer.parseInt(br.readLine());
            String idCorso=corsi.get(index-1).getIdCorso();
            getLezioniPrenotabili(idCorso,logged);


        }catch(IOException e){
        System.out.println("ERRORE!"+e.getMessage());
        System.exit(-1);
        }
    }

    public static void getLezioniPrenotabili(String idCorso, Cliente logged){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Lezione> lezioniDisponibili=agilegym.mostraLezione(idCorso,logged);
        Lezione lezioneCorrente;
        int index;
        try {
            if (lezioniDisponibili.size() != 0) {
                int i = 0;
                for (Lezione value : lezioniDisponibili) {
                    i++;
                    System.out.println("LEZIONE: " + i);
                    System.out.println(value);
                }

                System.out.print("Scegli una lezione: ");
                index = Integer.parseInt(br.readLine());
                lezioneCorrente = lezioniDisponibili.get(index - 1);

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
                    break;
                case 2:
                    getLezioniPrenotabili(idCorso,logged);
                    break;
                case 3:
                    getCorsiPrenotabili(logged);
                    break;
                case 0:
                    return;
            }
            String s;
            do {
                s = "";
                System.out.print("Vuoi iscriverti ad un altra lezione?: ");
                String str = br.readLine();
                if (str.equals("si")) {
                    s = str;
                    getLezioniPrenotabili(idCorso,logged);
                }
                if (str.equals("no")) {
                    s = str;
                    return;
                }
            } while (s.equals(""));
        }catch(IOException e){
            System.out.println("ERRORE!"+e.getMessage());
            System.exit(-1);
        } catch (LezioneNonPresente | PrenotazionePresenteException e) {
            e.printStackTrace();
        }
    }

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
            System.out.print("CORSO: " +i+"\n");
            System.out.print(agilegym.getElencoCorsi().get(key));
                for (String key2 : agilegym.getElencoCorsi().get(key).getElencoLezioni().keySet()){
                    Lezione l = agilegym.getElencoCorsi().get(key).getElencoLezioni().get(key2);
                    if(l.getElencoPrenotazioni().size()!=0){
                        System.out.println("\nLezione:\n" + l + "\tElenco clienti: ");
                        for(String key3 : l.getElencoPrenotazioni().keySet()) {
                            Prenotazione p = l.getElencoPrenotazioni().get(key3);
                            System.out.println("\t\t---\n\t" + agilegym.getElencoClienti().get(p.getIdCliente()));
                        }
                    }
                    else  System.out.println("\nLezione:\n" + l);
                }

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
            String idPrenotazione=logged.getElencoPrenotazioni().get(key1).getIdPrenotazione();
            for (String key2 : agilegym.getElencoCorsi().keySet()) {

                int i=0;
                for (String key3 : agilegym.getElencoCorsi().get(key2).getElencoLezioni().keySet()) {
                    Lezione l = agilegym.getElencoCorsi().get(key2).getElencoLezioni().get(key3);
                    if (l.getElencoPrenotazioni().containsKey(idPrenotazione)) {
                        str.append("Lezione:\n").append(l);
                    }
                }
            }
        }
        System.out.println("ELENCO LEZIONI PRENOTATE\n" + str);
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


