package org.unict.domain;

import java.io.*;
import java.util.*;

import static org.unict.domain.Agilegym.agilegym;

public class app {

    public static void main (String [] args){
        int scelta;

        Agilegym agilegym = Agilegym.getInstance();
        System.out.println("\n\n");
        System.out.println(logo());
        String logged=login();
        do {

            if (logged.equals("ADMIN")) {
                do {
                    scelta = menuAdmin();
                    switch (scelta) {
                        case 1 -> agilegym.inserisciCorso();
                        case 2 -> {
                            if (agilegym.getElencoCorsi().isEmpty()) {
                                stampaCorsi();
                                break;
                            }
                            sottoScelta();
                        }
                        case 3 -> {
                            if (agilegym.getElencoCorsi().isEmpty()) {
                                stampaCorsi();
                                break;
                            }
                            agilegym.scegliCliente();
                        }
                        case 4 -> stampaCorsi();
                        case 5 -> stampaSale();
                        case 6 -> stampaIstruttori();
                        case 7 -> stampaPrenotazioni();
                        case 8 -> agilegym.riempiPalestra();
                        case 0 -> logged=login();
                    }
                } while (logged.equals("ADMIN"));
            }
            else {
                do {
                    scelta = menu(logged);
                    switch (scelta) {
                        case 1 -> {
                            if (agilegym.getElencoCorsi().isEmpty()) {
                                stampaCorsi();
                                break;
                            }
                            agilegym.nuovaPrenotazione(logged);
                        }
                        case 2 -> stampaPrenotazioniCliente(logged);
                        case 0 -> logged=login();
                    }
                } while (!logged.equals("ADMIN"));
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
            System.out.println("0. LOGOUT");
            System.out.print("Scelta: ");
            return Integer.parseInt(br.readLine());
        }catch(IOException e){
            System.out.println("ERRORE!");
            return -13;// NUMERO PIU' ALTO DI EXIT
        }
    }

    public static void sottoScelta(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Corso corsoScelto;
        Map<Integer,Corso> corsi =new HashMap<>();
        try{
            int i=0;
            for (String key : agilegym.getElencoCorsi().keySet()) {
                i++;
                System.out.println("CORSO: "+ i );
                System.out.println(agilegym.getElencoCorsi().get(key).stampaCorsi());
            }
            System.out.print("\n");
            int s = 0;
            do{
                System.out.print("Scegli un corso, 0 per tornare a MENU:");
                int scelta=Integer.parseInt(br.readLine());
                if (scelta==0){
                    return;
                }
                if(scelta>0 && scelta <= agilegym.getElencoCorsi().size()){
                    i=0;
                    s=scelta;
                    for (String key: agilegym.getElencoCorsi().keySet()){
                        i++;
                        corsi.put(i,agilegym.getElencoCorsi().get(key));
                    }
                    corsoScelto=corsi.get(s);
                    agilegym.inserisciLezione(corsoScelto);
                }
            }while(s==0);

        }catch(IOException e){
            System.out.println("ERRORE!");
            System.exit(-1);
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
            System.out.println(agilegym.getElencoCorsi().get(key));
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
       String str = "";
        for (String key1 : logged.getElencoPrenotazioni().keySet()){
            String idPrenotazione=logged.getElencoPrenotazioni().get(key1).getIdPrenotazione();
            for (String key2 : agilegym.getElencoCorsi().keySet()) {

                int i=0;
                for (String key3 : agilegym.getElencoCorsi().get(key2).getElencoLezioni().keySet()) {
                    Lezione l = agilegym.getElencoCorsi().get(key2).getElencoLezioni().get(key3);
                    if (l.getElencoPrenotazioni().containsKey(idPrenotazione)) {
                        str+="Lezione:\n" + l;
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
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
        s[1]= """
                 █████   ██████  ██ ██      ███████  ██████  ██    ██ ███    ███\s
                ██   ██ ██       ██ ██      ██      ██        ██  ██  ████  ████\s
                ███████ ██   ███ ██ ██      █████   ██   ███   ████   ██ ████ ██\s
                ██   ██ ██    ██ ██ ██      ██      ██    ██    ██    ██  ██  ██\s
                ██   ██  ██████  ██ ███████ ███████  ██████     ██    ██      ██\s
                """;
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
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


