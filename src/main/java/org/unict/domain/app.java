package org.unict.domain;

import org.unict.UI.MainFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.unict.domain.Agilegym.agilegym;


public class app {

    public static void main (String [] args) throws inserisciCorsoException {

        int scelta = 0;
        //MainFrame frame= new MainFrame();
        //frame.setVisible(true);
        Agilegym agilegym = Agilegym.getInstance();
        System.out.println("\n\n");
        System.out.println(logo(2));

        do{
            scelta = menu();
            switch(scelta){
                case 1:
                    agilegym.inserisciCorso();
                    break;
                case 2:
                    sottoscelta();
                    break;
                case 3:

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
                case 8:
                    System.exit(0);
                    break;
            }
        }while(scelta!=8);

    }

    public static int menu(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("\n#--------------------------------------------------------------#");
            System.out.println("#-----------------------------MENU-----------------------------#");
            System.out.println("#--------------------------------------------------------------#\n");
            System.out.println("1. Inserisci Corso");
            System.out.println("2. Inserieci Lezione");
            System.out.println("3. ");
            System.out.println("4. Stampa Corsi");
            System.out.println("5. Stampa Sale");
            System.out.println("6. Stampa Istuttori");
            System.out.println("7. Riempi Palestra");
            System.out.println("8. Esci");
            System.out.print("Scelta: ");
            return Integer.parseInt(br.readLine());
        }catch(IOException e){
            System.out.println("ERRORE!");
            return -1;
        }
    }

    public static void sottoscelta(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Corso corsoScelto=null;
        Map<Integer,Corso> corsi =new HashMap<>();
        try{
            stampaCorsi();
            System.out.print("\nScegli un corso, 0 per crearlo:");
            int scelta =-1;
            do{
                scelta =Integer.parseInt(br.readLine());
                if (scelta==0){
                    agilegym.inserisciCorso();
                }
                else {
                    int i=0;
                    for (String key: agilegym.getElencoCorsi().keySet()){
                        i++;
                        corsi.put(i,agilegym.getElencoCorsi().get(key));
                    }
                    corsoScelto=corsi.get(scelta);
                    agilegym.inserisciLezione(corsoScelto);
                }
            }while(scelta==-1);
        }catch(IOException e){
            System.out.println("ERRORE!");
           System.exit(-20);
        } catch (inserisciCorsoException e) {
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

    public static void clearConsole(){
        System.out.print("\\033[H\\033[2J");
        System.out.flush();
        logo(2);
    }

    private static String logo(int scelta) {
        String s[] = new String[3];
        s[0]=       "     _              _   _           ____                     \n" +
                    "    / \\      __ _  (_) | |   ___   / ___|  _   _   _ __ ___  \n" +
                    "   / _ \\    / _` | | | | |  / _ \\ | |  _  | | | | | '_ ` _ \\ \n" +
                    "  / ___ \\  | (_| | | | | | |  __/ | |_| | | |_| | | | | | | |\n" +
                    " /_/   \\_\\  \\__, | |_| |_|  \\___|  \\____|  \\__, | |_| |_| |_|\n" +
                    "            |___/                          |___/             \n\n\n";
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
        s[1]=       " █████   ██████  ██ ██      ███████  ██████  ██    ██ ███    ███ \n" +
                    "██   ██ ██       ██ ██      ██      ██        ██  ██  ████  ████ \n" +
                    "███████ ██   ███ ██ ██      █████   ██   ███   ████   ██ ████ ██ \n" +
                    "██   ██ ██    ██ ██ ██      ██      ██    ██    ██    ██  ██  ██ \n" +
                    "██   ██  ██████  ██ ███████ ███████  ██████     ██    ██      ██ \n";
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
        s[2]=   "                                                                                                                                                                   \n" +
                    "               AAA                                      iiii  lllllll                               GGGGGGGGGGGGG                                                  \n" +
                    "              A:::A                                    i::::i l:::::l                            GGG::::::::::::G                                                  \n" +
                    "             A:::::A                                    iiii  l:::::l                          GG:::::::::::::::G                                                  \n" +
                    "            A:::::::A                                         l:::::l                         G:::::GGGGGGGG::::G                                                  \n" +
                    "           A:::::::::A              ggggggggg   ggggg  iiiiiii l::::l      eeeeeeeeeeee      G:::::G       GGGGGG yyyyyyy           yyyyyyy   mmmmmmm    mmmmmmm   \n" +
                    "          A:::::A:::::A            g:::::::::ggg::::g  i:::::i l::::l    ee::::::::::::ee   G:::::G                y:::::y         y:::::y  mm:::::::m  m:::::::mm \n" +
                    "         A:::::A A:::::A          g:::::::::::::::::g   i::::i l::::l   e::::::eeeee:::::ee G:::::G                 y:::::y       y:::::y  m::::::::::mm::::::::::m\n" +
                    "        A:::::A   A:::::A        g::::::ggggg::::::gg   i::::i l::::l  e::::::e     e:::::e G:::::G    GGGGGGGGGG    y:::::y     y:::::y   m::::::::::::::::::::::m\n" +
                    "       A:::::A     A:::::A       g:::::g     g:::::g    i::::i l::::l  e:::::::eeeee::::::e G:::::G    G::::::::G     y:::::y   y:::::y    m:::::mmm::::::mmm:::::m\n" +
                    "      A:::::AAAAAAAAA:::::A      g:::::g     g:::::g    i::::i l::::l  e:::::::::::::::::e  G:::::G    GGGGG::::G      y:::::y y:::::y     m::::m   m::::m   m::::m\n" +
                    "     A:::::::::::::::::::::A     g:::::g     g:::::g    i::::i l::::l  e::::::eeeeeeeeeee   G:::::G        G::::G       y:::::y:::::y      m::::m   m::::m   m::::m\n" +
                    "    A:::::AAAAAAAAAAAAA:::::A    g::::::g    g:::::g    i::::i l::::l  e:::::::e             G:::::G       G::::G        y:::::::::y       m::::m   m::::m   m::::m\n" +
                    "   A:::::A             A:::::A   g:::::::ggggg:::::g i::::::i l::::::l e::::::::e             G:::::GGGGGGGG::::G         y:::::::y        m::::m   m::::m   m::::m\n" +
                    "  A:::::A               A:::::A   g::::::::::::::::g i::::::i l::::::l  e::::::::eeeeeeee      GG:::::::::::::::G          y:::::y         m::::m   m::::m   m::::m\n" +
                    " A:::::A                 A:::::A   gg::::::::::::::g i::::::i l::::::l   ee:::::::::::::e        GGG::::::GGG:::G         y:::::y          m::::m   m::::m   m::::m\n" +
                    "AAAAAAA                   AAAAAAA    gggggggg::::::g iiiiiiii llllllll     eeeeeeeeeeeeee           GGGGGG   GGGG        y:::::y           mmmmmm   mmmmmm   mmmmmm\n" +
                    "                                             g:::::g                                                                    y:::::y                                    \n" +
                    "                                 gggggg      g:::::g                                                                   y:::::y                                     \n" +
                    "                                 g:::::gg   gg:::::g                                                                  y:::::y                                      \n" +
                    "                                  g::::::ggg:::::::g                                                                 y:::::y                                       \n" +
                    "                                   gg:::::::::::::g                                                                 yyyyyyy                                        \n" +
                    "                                     ggg::::::ggg                                                                                                                  \n" +
                    "                                        gggggg                                                                                                                     \n";
//---------------------------------------------------------------------------------------------------------------------------------------------------------------

            return s[scelta];
    }

}


