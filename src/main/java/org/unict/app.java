package org.unict;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class app {

    public static void main (String [] args) throws Exception, inserisciCorsoException {

        int scelta = 0;
        Agilegym agilegym = Agilegym.getInstance();

        logo1();

        do{
            scelta = menu();
            switch(scelta){
                case 1:
                        agilegym.inserisciCorso();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    if(agilegym.getElencoCorsi().isEmpty()){
                        System.out.print    ("\n#--------------------------------------------------------------#\n");
                        System.out.print    ("  #--------------NON CI SONO CORSI, INSERISCILI !!!--------------#");
                        System.out.println  ("\n#--------------------------------------------------------------#\n");

                    };
                    for (String key: agilegym.getElencoCorsi().keySet()){
                        System.out.println(agilegym.getElencoCorsi().get(key));
                    }
                    break;
                case 5:
                    System.exit(0);
                    break;
            }
        }while(scelta!=6);




        //caso d'uso di avviamento:caricamento lista attrezzi, caricamento sale, caricamento istruttori

        /*PER IL MENU
        * 1.Crea corso
        * 1.Craa lezione, stampa i corsi o chiese se si vuole aggiungere una lezione alla
        *
        *
        *
        * */



    }
    public static int menu(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("\n#-----------------------------MENU-----------------------------#\n");
            System.out.println("1. Inserisci Corso\n");
            System.out.println("2. \n");
            System.out.println("3. \n");
            System.out.println("4.Stampa Corsi\n");
            System.out.println("5. Esci\n");
            System.out.print("Scelta: ");
            return Integer.parseInt(br.readLine());
        }catch(IOException e){
            System.out.println("ERRORE!");
            return -1;
        }
    }

    public static void clearConsole(){
        System.out.print("\\033[H\\033[2J");
        System.out.flush();
        logo1();
    }
    private static void logo0() {
        System.out.println( "     _              _   _           ____                     \n" +
                            "    / \\      __ _  (_) | |   ___   / ___|  _   _   _ __ ___  \n" +
                            "   / _ \\    / _` | | | | |  / _ \\ | |  _  | | | | | '_ ` _ \\ \n" +
                            "  / ___ \\  | (_| | | | | | |  __/ | |_| | | |_| | | | | | | |\n" +
                            " /_/   \\_\\  \\__, | |_| |_|  \\___|  \\____|  \\__, | |_| |_| |_|\n" +
                            "            |___/                          |___/             \n\n\n");
    }
    public static void logo1() {
        System.out.println( " █████   ██████  ██ ██      ███████  ██████  ██    ██ ███    ███ \n" +
                            "██   ██ ██       ██ ██      ██      ██        ██  ██  ████  ████ \n" +
                            "███████ ██   ███ ██ ██      █████   ██   ███   ████   ██ ████ ██ \n" +
                            "██   ██ ██    ██ ██ ██      ██      ██    ██    ██    ██  ██  ██ \n" +
                            "██   ██  ██████  ██ ███████ ███████  ██████     ██    ██      ██ \n");
    }

}


