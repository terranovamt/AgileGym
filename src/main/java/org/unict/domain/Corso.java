package org.unict.domain;

import org.unict.domain.exception.*;
import java.util.*;

public class Corso {
    private String idCorso;
    private String nomeCorso;
    private String livello;
    private String focus;
    private Attrezzo attrezzo;
    private  Map<String, Lezione> elencoLezioni;

    public Corso(String idCorso, String nomeCorso, String livello, String focus, Attrezzo attrezzo){
        this.idCorso =idCorso;
        this.nomeCorso=nomeCorso;
        this.livello=livello;
        this.focus=focus;
        this.attrezzo = attrezzo;
        this.elencoLezioni=new TreeMap<>();
    }

    //UC1
    public List <String> getIdSaleAttrezzate() {
        return this.getAttrezzo().getSale();
    }

    public boolean inserisciLezione (Lezione lezioneCorrente )  {
        elencoLezioni.put(lezioneCorrente.getIdLezione(), lezioneCorrente);
        return true;
    }

    //UC2
    public List<Lezione> mostraLezioni(Map<String,Prenotazione> elencoPrenotazioneUtente) throws SalaPienaException, ClienteOccupatoException {
        List<Lezione> elencoLezioniDisponibili= new ArrayList<>();

        for (String key : elencoLezioni.keySet()){
            if(elencoLezioni.get(key).isPrenotabile(elencoPrenotazioneUtente)){
                elencoLezioniDisponibili.add(elencoLezioni.get(key));
            }
        }
        return elencoLezioniDisponibili;
    }

    public Prenotazione confermaPrenotazione(String idLezione, String idCliente) throws LezioneNonPresente, PrenotazionePresenteException {
        if(elencoLezioni.containsKey(idLezione)){
            return this.elencoLezioni.get(idLezione).creaPrenotazione(idCliente);
        }
        else throw new LezioneNonPresente("Non ci sono lezioni con questo ID");
    }

    //GET E SET STANDARD
    public String getIdCorso() {
        return idCorso;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public Attrezzo getAttrezzo() {
        return attrezzo;
    }

    public String getLivello() {
        return livello;
    }

    public String getFocus() {
        return focus;
    }

    public Map<String, Lezione> getElencoLezioni() {
        return elencoLezioni;
    }

    //STAMPA
    public String stampaLezione() {
        String str="";
        for (String key: elencoLezioni.keySet()){//STAMPA ISTRUTTORI DISPONIBILI
            str+= "LEZIONE: \n" + elencoLezioni.get(key).toString();
        }
        return str;
    }

    @Override
    public String toString(){
        String l=stampaLezione(),
                s =
                    "\tID: " + idCorso + "\n" +
                    "\tNome: " + nomeCorso + "\n" +
                    "\tLivello: " + livello+ "\n" +
                    "\tFocus: " + focus + "\n" +
                    "\tID-Attrezzo: " + attrezzo.getIdAttrezzo()+ "\n\n";

        if (l.length()==0) s+="\t\tNESSUNA LEZIONE INSERITA\n";

        return s;
    }

    public String stampaCorsi(){
        return  "\tNome: " + nomeCorso + "\n" +
                "\tLivello: " + livello+ "\n" +
                "\tFocus: " + focus ;
    }
}
