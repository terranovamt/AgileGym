package org.unict;

import java.io.IOException;

public class app {

    public static void main (String [] args) throws Exception, inserisciCorsoException {

        Agilegym agilegym = Agilegym.getInstance();
        //agilegym.loadAttrezzi();
        //agilegym.loadSlot();
        agilegym.loadSale();
        agilegym.loadIstruttore();
        agilegym.inserisciCorso();
        //caso d'uso di avviamento:caricamento lista attrezzi, caricamento sale, caricamento istruttori


        //System.out.println("Inserimento nuovo corso");

        //agilegym.conferma();
        //agilegym.inserisciLezione("XXX","1",108);
        //System.out.println(agilegym.getCorsoCorrente()+"0------00");
    }



}