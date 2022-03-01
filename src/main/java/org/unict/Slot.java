package org.unict;

public class Slot {
    private String idSlot; //il primo numero indica il giono della settimana i successivi due indicano l'ora N.B. 208 == Martedí 8AM
    private boolean disponibile;

    public Slot(String idSlot, boolean disponibilita){
        this.idSlot = this.idSlot;
        this.disponibile = disponibile;
    }

    public void setIdSlot(String idSlot) {
        this.idSlot = idSlot;
    }


    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    /*public void setIdSlot() {
                    try
                    {
                        FileReader fr = new FileReader( "slot.txt" );
                        BufferedReader br = new BufferedReader( fr );

                        // dichiaro la variabile String
                        String stringRead = br.readLine( );

                        while( stringRead != null )
                        {
                            // uso lo StringTokenizer per scorrere il file suddiviso dai pipe
                            StringTokenizer st = new StringTokenizer( stringRead, "-" );
                            String id = st.nextToken( );
                            try
                            {
                                this.idSlot = Integer.parseInt( st.nextToken( ) );
                            }
                            catch( NumberFormatException nfe )
                            {
                                System.out.println( "Errore in slot: " + stringRead + ";" );
                            }
                            stringRead = br.readLine( );
                        }
                        br.close( );
                    }
                    catch( FileNotFoundException fnfe )
                    {
                        System.out.println( "ERRORE:File slot.txt non trovato" );
                    }
                    catch( IOException ioe )
                    {
                        ioe.printStackTrace( );
                    }
                }

                public void setDisponibile(boolean disponibile) {
                    this.disponibile = disponibile;
                }

                public String getIdSlot() {
                    return idSlot;
                }

                public boolean isDisponibile(int idSlot) {
                    if (idSlot==this.idSlot){
                        return disponibile;
                    }
                    else return !disponibile;
                }*/
    public String getIdSlot() {
        return idSlot;
    }
    @Override
    public String toString(){

       String s =
                "ID-Slot:\t" + idSlot + "\n" +
                        "Disponibile?':\t" + disponibile + "\n";
        return s;
    }
}
