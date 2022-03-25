package org.unict.domain;

import org.unict.domain.exception.PrenotazionePresenteException;

import java.time.LocalDateTime;
import java.util.*;

public class Cliente {
    private  String idCliente;
    private  String nome;
    private  String cognome;
    private  Map<String,Prenotazione> elencoPrenotazioni;


    public  Cliente(String username, String nome, String cognome){
        this.idCliente = username;
        this.nome=nome;
        this.cognome=cognome;
        this.elencoPrenotazioni=new TreeMap<>();
    }

    //UC2
    public void addPrenotazione(Prenotazione p)throws PrenotazionePresenteException {
        if(!elencoPrenotazioni.containsKey(p.getIdPrenotazione())){
        elencoPrenotazioni.put(p.getIdPrenotazione(),p);
        }
        else throw new PrenotazionePresenteException("Esiste giá una prenotazione per questa Lezione");
    }

    //UC3
    public List<Prenotazione> getlistPrenotazioni(){
        List<Prenotazione> listaPrenotazioni=new ArrayList<>();
        for (String key: this.getElencoPrenotazioni().keySet()){
            Prenotazione p=this.getElencoPrenotazioni().get(key);
            if(this.controlloOra(p.getLezione().getIdSlot()))listaPrenotazioni.add(p);
            else{
                System.out.println("\nPRENOTAZIONE TROPPO VICINA PER LA MOFDIFICA");
                System.out.print(p);
            }
        }
        return listaPrenotazioni;
    }

    private boolean controlloOra(String idSlot){
        LocalDateTime d= LocalDateTime.now();
        float now=(((d.getDayOfWeek().ordinal()+1)*100)+d.getHour());
        float controllo= Float.parseFloat(idSlot);
        return (controllo - now) > 2 || (now - controllo) > 0;
    }

    public Prenotazione replacePrenotazione(Prenotazione p, Lezione corrente, Lezione old){
        old.removePrenotazione(p);
        corrente.updatePrenotazione(p);
        p.setLezione(corrente);
        return this.elencoPrenotazioni.replace(p.getIdPrenotazione(), p);
    }

    //GET E SET STANDARD
    public String getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public Map<String, Prenotazione> getElencoPrenotazioni() {
        return elencoPrenotazioni;
    }

    //STAMPA
    @Override
    public String toString() {
        return "\tCliente: " + nome + " " + cognome;
    }
}
