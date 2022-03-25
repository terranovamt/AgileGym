package org.unict.domain;

import org.unict.domain.exception.*;

import java.util.*;
import java.io.*;

import static java.lang.Math.abs; //rand num

public class Agilegym {
    static  Agilegym agilegym;
    private Corso corsoCorrente;
    private  Map<String, Cliente> elencoClienti;
    private  Map<String, Attrezzo> elencoAttrezzi;
    private  Map<String, Corso> elencoCorsi;
    private  Map<String, Sala> elencoSale;
    private  Map<String, Istruttore> elencoIstruttori;

    private Agilegym() throws IOException {
        this.elencoCorsi = new TreeMap<>();
        this.elencoAttrezzi  = new TreeMap<>();
        this.elencoSale = new TreeMap<>();
        this.elencoIstruttori = new TreeMap<>();
        this.elencoClienti = new TreeMap<>();
        loadAttrezzi();
        loadSale();
        loadIstruttore();
    }

    public static Agilegym getInstance(){
        if (agilegym == null)
            try{
                agilegym = new Agilegym();
            }catch (IOException e){
                System.out.println("PRIMO ERRORE");
            }
        else
            System.out.println("Instanza già creata");
        return agilegym;
    }
    //UC1
    public Corso nuovoCorso(String nomeCorso, String livello,String focus,String idAttrezzo) throws CorsoException{
        String idCorso =randId();
        Attrezzo attrezzoSelezionato = elencoAttrezzi.get(idAttrezzo);
        if (attrezzoSelezionato!=null){
            this.corsoCorrente = new Corso(idCorso, nomeCorso, livello, focus, attrezzoSelezionato);
        }else{
            throw new CorsoException("Errore nella ricerca dell'attrezzo");
        }
        return corsoCorrente;
    }

    public boolean confermaCorso(Corso corsoCorrente) {
        //se = null nessun problema
        return this.elencoCorsi.put(corsoCorrente.getIdCorso(), corsoCorrente)==null;
    }

    public List<String> getSale(Corso corsoCorrente){
        return corsoCorrente.getIdSaleAttrezzate();
    }

    public List<String> getSlot(String idSala){
        return elencoSale.get(idSala).getSlotDisponibili();
    }

    public List<String> getIstruttori(String idSlot) throws IstruttoreException {
        List<String> listIdIstruttori= new ArrayList<>();
        for(String key : elencoIstruttori.keySet()){
            Istruttore i=elencoIstruttori.get(key);
            if(i.getMapSlot().get(idSlot))listIdIstruttori.add(i.getIdIstruttore());
        }
        if(listIdIstruttori.size()==0) throw new IstruttoreException("Non ci sono istruttori disponibili in questo slot");
        return listIdIstruttori;
    }

    public Lezione creaLezione(Corso corsoSelezionato,String idSala, String idSlot, String idIstruttore){
        corsoCorrente=corsoSelezionato;
        Sala s =elencoSale.get(idSala);
        Istruttore i =elencoIstruttori.get(idIstruttore);
        String idLezione="";
        idLezione+=corsoCorrente.getIdCorso()+s.getIdSala()+idSlot;

        return new Lezione(idLezione,idSlot,corsoCorrente,s,i);
    }

    public boolean confermaLezione(Lezione lezioneCorrente) throws LezioneException {
        if(corsoCorrente.getElencoLezioni().containsValue(lezioneCorrente)) throw new LezioneException("LEZIONE GIA CREATA");
        if(corsoCorrente.inserisciLezione(lezioneCorrente)) {
            lezioneCorrente.getSala().setOccupato(lezioneCorrente.getIdSlot());
            lezioneCorrente.getIstruttore().setOccupato(lezioneCorrente.getIdSlot());
            return true;
        }else return false;
    }

    //UC2
    public List<Corso> nuovaPrenotazione(){
        List<Corso> listaCorsi=new ArrayList<>();
            for (String key : elencoCorsi.keySet()) {
                listaCorsi.add(elencoCorsi.get(key));
            }
            return listaCorsi;
    }

    public List<Lezione> mostraLezione(String idCorso,Cliente logged) {
        List<Lezione> lezioniDisponibili = null;
        corsoCorrente = elencoCorsi.get(idCorso);
        Map<String, Prenotazione> elencoPrenotazioneCliente;
        elencoPrenotazioneCliente = logged.getElencoPrenotazioni();
        try {
            lezioniDisponibili = corsoCorrente.mostraLezioni(elencoPrenotazioneCliente);
        } catch (SalaPienaException | ClienteOccupatoException e) {
            e.printStackTrace();
        }
        return lezioniDisponibili;
    }

    public void confermaPrenotazione(String idLezione, Cliente logged) throws PrenotazionePresenteException, LezioneException {
        List<String> slotPrenotati = new ArrayList<>();
        Lezione lezioneSelezionata=corsoCorrente.getElencoLezioni().get(idLezione);
        for (String key : logged.getElencoPrenotazioni().keySet()) {
            slotPrenotati.add(logged.getElencoPrenotazioni().get(key).getLezione().getIdSlot());
        }
        if((lezioneSelezionata.postiDisponibili())>0){
            if(!slotPrenotati.contains(lezioneSelezionata.getIdSlot())){
                Prenotazione p = corsoCorrente.confermaPrenotazione(idLezione, logged.getIdCliente());
                logged.addPrenotazione(p);
             }else System.out.println("Hai gia una lezione prenotata coincidente ");
        }else System.out.println("Posti per la lezione pieni");
    }

    //UC3
    public List<Prenotazione> modificaPrenotazione(String idCliente)   {
         Cliente logged =elencoClienti.get(idCliente);
         return logged.getlistPrenotazioni();
    }

    public List<Lezione> getLezioni(Prenotazione prenotazione,Cliente logged) {
        List<Lezione> lezioniDisponibili = null;
        corsoCorrente = prenotazione.getLezione().getCorso();
        Map<String, Prenotazione> elencoPrenotazioneCliente;
        elencoPrenotazioneCliente = logged.getElencoPrenotazioni();
        try {
            lezioniDisponibili = corsoCorrente.mostraLezioni(elencoPrenotazioneCliente);
        } catch (SalaPienaException | ClienteOccupatoException e) {
            e.printStackTrace();
        }
        return lezioniDisponibili;
    }

    public boolean updatePrenotazione(Lezione newLezione, Prenotazione p, Cliente logged){
        Lezione oldLezione=p.getLezione();
        if(oldLezione != newLezione) return logged.replacePrenotazione(p, newLezione, oldLezione)!=null;
        else return false;
    }

    //CASO DUSO DI AVVIAMENTO
    public void loadAttrezzi(){
        String str, idAtrezzo;
        String [] strings;
        //System.out.println("sono dentro Attrezzi\n\n");
        try{
            BufferedReader br = new BufferedReader(new FileReader("Attrezzi.txt"));
            str = br.readLine();
            while (str != null){
                strings=str.split("-");
                idAtrezzo=strings[0];
                List<String> listaSala = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length));
                Attrezzo a=new Attrezzo(idAtrezzo,listaSala);
                this.elencoAttrezzi.put(idAtrezzo,a);
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Attrezzi.txt\n" );
            System.exit(-4);
        }
    }

    public void loadSale() {
        String str, idSala;
        String [] strings;

        try {
            BufferedReader br = new BufferedReader(new FileReader("sale.txt"));

            str = br.readLine();
            while (str != null){
                strings=str.split("-");
                idSala=strings[0];
                List<String> listaAtrezziSala = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length));
                Sala s=new Sala(idSala);
                s.setListaAttrezzi(listaAtrezziSala);
                this.elencoSale.put(idSala, s);
                str = br.readLine();
            }
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE Sale.txt\n" );
            System.exit(-5);
        }
    }

    public void loadIstruttore(){
        String idIstruttore;

        try{
            //System.out.println("sono dentro load istruttore\n\n");
            BufferedReader br = new BufferedReader(new FileReader("Istruttori.txt"));
            idIstruttore = br.readLine();
            while (idIstruttore != null){
                this.elencoIstruttori.put(idIstruttore, new Istruttore(idIstruttore));
                idIstruttore = br.readLine();
            }
        }catch (IOException e){
            System.out.println("ERRORE NEL CARICAMENTO DEL FILE istruttori.txt:\n" );
            System.exit(-6);
        }
    }

    public void riempiPalestra(){
        String str;
        String [] strings;
        Prenotazione p = null;
        try {
            BufferedReader bCorsi = new BufferedReader(new FileReader("corsi.txt"));
            BufferedReader bLezioni = new BufferedReader(new FileReader("lezioni.txt"));
            BufferedReader bCliente = new BufferedReader(new FileReader("clienti.txt"));
            BufferedReader bPrenotazioni= new BufferedReader(new FileReader("prenotazioni.txt"));
            //INSERIEMNTO CORSI
            str = bCorsi.readLine();
            while (str != null){
                strings=str.split("-");
                Attrezzo a=elencoAttrezzi.get((strings[4]));
                Corso c= new Corso(strings[0], strings[1], strings[2], strings[3],a);
                this.elencoCorsi.put(strings[0],c);
                str = bCorsi.readLine();
            }
            //INSERIMENTO LEZIONI
            str = bLezioni.readLine();
            while (str != null){
                strings=str.split("-");
                corsoCorrente=elencoCorsi.get(strings[0]);
                Sala s=elencoSale.get(strings[1]);
                Istruttore i=elencoIstruttori.get(strings[2]);
                Lezione l=creaLezione(corsoCorrente,strings[1],strings[3],strings[2]);
                corsoCorrente.inserisciLezione(l);
                s.setOccupato(strings[3]);
                i.setOccupato(strings[3]);
                str = bLezioni.readLine();
            }
            //INSERIEMNTO CLIENTI
            str = bCliente.readLine();
            while (str != null){
                strings=str.split("-");
                Cliente c=new Cliente(strings[0], strings[1], strings[2]);
                elencoClienti.put(strings[0],c);
                str = bCliente.readLine();
            }
            //INSEIRMENTO PRENOTAZIONI
            str = bPrenotazioni.readLine();
            while (str != null){
                strings=str.split("-");
                List<String> slotPrenotati = new ArrayList<>();

                for(String key : elencoCorsi.keySet()){
                    if(elencoCorsi.get(key).getElencoLezioni().containsKey(strings[0])){
                        corsoCorrente=elencoCorsi.get(key);
                        break;
                    }
                }
                Cliente logged=elencoClienti.get(strings[1]);
                Lezione lezioneSelezionata=corsoCorrente.getElencoLezioni().get(strings[0]);
                for (String key : logged.getElencoPrenotazioni().keySet()) {
                    slotPrenotati.add(logged.getElencoPrenotazioni().get(key).getLezione().getIdSlot());
                }
                if((lezioneSelezionata.postiDisponibili()-lezioneSelezionata.getElencoPrenotazioni().size())!=0){
                    if(!slotPrenotati.contains(lezioneSelezionata.getIdSlot())){
                        p = corsoCorrente.confermaPrenotazione(strings[0], logged.getIdCliente());
                        logged.addPrenotazione(p);
                    }else System.out.println("Hai gia una lezione prenotata coincidente ");
                }else System.out.println("Posti per la lezione pieni");
                str = bPrenotazioni.readLine();
            }

            System.out.println("\n\tBackup caricato con successo");
        }catch (IOException e) {
            System.out.println("ERRORE NEL CARICAMENTO DELLA PALESTRA\n" );
            System.exit(-9);
        } catch (PrenotazionePresenteException | LezioneException e) {
            e.printStackTrace();
        }
    }

    //FUNZIONI DI UTILITY
    public Cliente loginCliente(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Cliente logged = null;
        String username;
        try {
            do {
                System.out.print("Inserisci username: ");
                username = br.readLine();
                if(elencoClienti.containsKey(username)){
                    logged=elencoClienti.get(username);
                }
            }while (logged ==null);
        }catch (Exception e) {
            System.out.println("ERRORE NELLA LETTURA DA TASTIERA:" +e.getMessage());
            System.exit(-11);
        }
        return logged;
    }

    public static String randId(){
        String s=String.valueOf((abs((int) System.currentTimeMillis() + (int)(Math.random()*(1000000000)))/10));
        return s.substring(3,7);
    }

    //GET E SET STANDARD
    public Map<String, Attrezzo> getElencoAttrezzi() {
        return elencoAttrezzi;
    }

    public Map<String, Corso> getElencoCorsi() {
        return elencoCorsi;
    }

    public Map<String, Sala> getElencoSale() {
        return elencoSale;
    }

    public Map<String, Istruttore> getElencoIstruttori() {
        return elencoIstruttori;
    }

    public Map<String, Cliente> getElencoClienti() {
        return elencoClienti;
    }
}
