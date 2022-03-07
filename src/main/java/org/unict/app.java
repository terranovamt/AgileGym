package org.unict;

import java.io.IOException;

public class app {
    public static void clearConsole(){
        System.out.print("\033[2J");
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
    private static void logo1() {
        System.out.println( " █████   ██████  ██ ██      ███████  ██████  ██    ██ ███    ███ \n" +
                            "██   ██ ██       ██ ██      ██      ██        ██  ██  ████  ████ \n" +
                            "███████ ██   ███ ██ ██      █████   ██   ███   ████   ██ ████ ██ \n" +
                            "██   ██ ██    ██ ██ ██      ██      ██    ██    ██    ██  ██  ██ \n" +
                            "██   ██  ██████  ██ ███████ ███████  ██████     ██    ██      ██ \n\n\n");
    }


    public static void main (String [] args) throws Exception, inserisciCorsoException {




        Agilegym agilegym = Agilegym.getInstance();
        //agilegym.loadSlot();
        //agilegym.loadAttrezzi();
        //agilegym.loadSale();
        //agilegym.loadIstruttore();
        logo1();
        agilegym.inserisciCorso();

        //caso d'uso di avviamento:caricamento lista attrezzi, caricamento sale, caricamento istruttori


        //System.out.println("Inserimento nuovo corso");

        //agilegym.conferma();
        //agilegym.inserisciLezione("XXX","1",108);
        //System.out.println(agilegym.getCorsoCorrente()+"0------00");
    }



}