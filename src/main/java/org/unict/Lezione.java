package org.unict;

public class Lezione {

    private String idLezione;
    private Slot slot;
    private Sala s;
    private Istruttore i;
    private Corso c;

    public Lezione(String idLezione, Slot slot, Corso c, Sala s, Istruttore i){
        this.idLezione = idLezione;
        this.slot = slot;
        this.c= c;
        this.s=s;
        this.i=i;
    }

    public String getIdLezione() {
        return idLezione;
    }

    public void setIdLezione(String idLezione){
        this.idLezione = idLezione;
    }

    private String stampadata(){
        String str="", giorno, ora="";

            switch (Integer.parseInt(String.valueOf(String.valueOf(slot.getIdSlot()).charAt(0)))){
                case 1: giorno="LUNEDI' ore ";     break;
                case 2: giorno="MARTEDI' ore ";    break;
                case 3: giorno="MERCOLEDI' ore ";  break;
                case 4: giorno="GIOVEDI ore ";     break;
                case 5: giorno="VENERDI ore ";     break;
                case 6: giorno="SABATO ore ";      break;
                default:giorno="";
            }
            ora+= slot.getIdSlot().charAt(1)+slot.getIdSlot().charAt(2);
            str+=giorno+ora+":00";
        return str;
    }
    @Override
    public String toString(){

        String str =
                "LEZIONE: \n" +
                        "\tID: " + idLezione + "\n" +
                        "\tNome Corso: " + c.getNome() + "\n" +
                        "\tSala: " + s.getIdSala()+ "\n" +
                        "\tIstruttore: " + i.getIdIstruttore() + "\n" +
                        "\tData: " +stampadata();

        return str;
    }
}
