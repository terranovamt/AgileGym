package org.unict;

public class app {

    public static void main (String [] args){
        Agilegym agilegym = Agilegym.getInstance();
        agilegym.loadSale();
        agilegym.createAttrezzi();
        agilegym.loadIstruttore();
        System.out.println("Inserimento nuovo corso");
        agilegym.inserisciCorso("AAA","CrossFit","Esperto","allBody", "1",108);
        agilegym.conferma();
        agilegym.inserisciLezione("XXX","1",108);
        System.out.println(agilegym.getElencoCorsi());
        System.out.println("Ciaooooooooooo");
    }
}