package org.unict;

import java.io.IOException;

public class app {

    public static void main (String [] args) {

        Agilegym agilegym = Agilegym.getInstance();
        //agilegym.loadListaAttrezzi();
        agilegym.loadSlot();
        //agilegym.loadSale();
        //agilegym.loadIstruttore();

        //caso d'uso di avviamento:caricamento lista attrezzi, caricamento sale, caricamento istruttori

        //agilegym.loadAttrezzi();
        //agilegym.loadIstruttore();
        //System.out.println("Inserimento nuovo corso");
        //agilegym.inserisciCorso();
        //agilegym.conferma();
        //agilegym.inserisciLezione("XXX","1",108);
        //System.out.println(agilegym.getCorsoCorrente()+"0------00");
    }



}