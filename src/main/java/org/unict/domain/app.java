package org.unict.domain;

import java.io.*;
import java.util.*;

import static org.unict.domain.Agilegym.agilegym;

public class app {

    public static void main (String [] args){
        int scelta;

        Agilegym agilegym = Agilegym.getInstance();
        System.out.println("\n\n");
        System.out.println(logo(1));
        do{
            scelta = menu();
            switch(scelta){
                case 1:
                    agilegym.inserisciCorso();
                    break;
                case 2:
                    if(agilegym.getElencoCorsi().isEmpty()){
                        stampaCorsi();
                        break;
                    }
                    sottoScelta();
                    break;
                case 3:
                    agilegym.nuovaPrenotazione();
                    break;
                case 4:
                    stampaCorsi();
                    break;
                case 5:
                    stampaSale();
                    break;
                case 6:
                    stampaIstruttori();
                    break;
                case 7:
                    agilegym.riempiPalestra();
                    break;
                case 0:
                    System.exit(0);
                    break;
            }
        }while(true);

    }

    public static int menu(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("\n#--------------------------------------------------------------#");
            System.out.println("#-----------------------------MENU-----------------------------#");
            System.out.println("#--------------------------------------------------------------#\n");
            System.out.println("1. Inserisci Corso");
            System.out.println("2. Inserisci Lezione");
            System.out.println("3. Nuova Prenotazione");
            System.out.print    ("\n#--------------------------------------------------------------#\n");
            System.out.println("4. Stampa Corsi");
            System.out.println("5. Stampa Sale");
            System.out.println("6. Stampa Istruttori");
            System.out.println("7. Riempi Palestra");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");
            return Integer.parseInt(br.readLine());
        }catch(IOException e){
            System.out.println("ERRORE!");
            return -1;
        }
    }

    public static void sottoScelta(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Corso corsoScelto;
        Map<Integer,Corso> corsi =new HashMap<>();
        try{
            stampaCorsi();
            System.out.print("\nScegli un corso, 0 per crearlo:");
            int s = 0;
            do{
                int scelta=Integer.parseInt(br.readLine());
                if (scelta==0){
                    agilegym.inserisciCorso();
                }
                if(scelta>0 || scelta < agilegym.getElencoCorsi().size()){
                    int i=0;
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
            System.exit(-11);// NUMERO PIU' ALTO DI EXIT
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

    private static String logo(int scelta) {
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

            return s[scelta];
    }

}


