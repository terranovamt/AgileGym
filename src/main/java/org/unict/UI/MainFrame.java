package org.unict.UI;

import org.unict.domain.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class MainFrame extends JFrame{
    private JPanel newCorsoPanel;
    private JPanel newLezionePanel;
    private JPanel homePanel;
    private JTextField nomeCorsoValue;
    private JLabel idCorsoText;
    private JLabel idCorsoValue;
    private JPanel mainPanel;
    private JComboBox livelloValue;
    private JTextField focusValue;
    private JLabel focusText;
    private JLabel livelloText;
    private JComboBox<String> attrezzoValue;
    private JLabel attrezzoText;
    private JButton nuovoCorso;
    private JButton returnCorso;
    private JButton confermaCorso;
    private JButton nuovaLezione;
    private JLabel welcome;
    private JLabel nuovoCorsoLabel;
    private JButton printCorso;
    private JPanel stampaCorsiPanel;
    private JButton returnHomestayCorso;
    private JTextPane textPane1;
    private JTextArea textArea1;
    private JButton returnHome1;
    Agilegym agilegym = Agilegym.getInstance();


    public MainFrame(){
        setTitle("Benvenuti");
        setBounds(300,200,700,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        agilegym.riempiPalestra();

        idCorsoValue.setText(agilegym.randId());
        attrezzoValue.addItem(null);
        for (String key: agilegym.getElencoAttrezzi().keySet()){//Serve per non stampare con le graffe e avere solo il valore e non la key, la formattazione e va fatto nel to string del tipo(sala.toString)
            attrezzoValue.addItem(agilegym.getElencoAttrezzi().get(key).getIdAttrezzo());
        }
        nuovoCorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gocreaCorso();
            }
        });
        returnCorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gohome();
            }
        });
        confermaCorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idCorsoValue.getText().isEmpty()||nomeCorsoValue.getText().isEmpty()||
                        livelloValue.getActionCommand().isEmpty()||focusValue.getText().isEmpty()||
                        attrezzoValue.getActionCommand().isEmpty()){

                }
                else{
                    Attrezzo a=agilegym.getElencoAttrezzi().get(attrezzoValue.getActionCommand());
                    Corso c=new Corso(idCorsoValue.getText(), nomeCorsoValue.getText(),
                        livelloValue.getActionCommand(), focusValue.getText(),a);
                    agilegym.getElencoCorsi().put(idCorsoValue.getText(), c);
                    gohome();
                }
            }
        });
        returnHomestayCorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gohome();
            }
        });
        printCorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gostampaCorsi();
            }
        });
    }

    public void toTableModel(Map<String,Corso> map) {
        String s = "";
        for (Map.Entry<String,Corso> entry : map.entrySet()) {
            System.out.println(entry.getValue());
            s+=entry.getValue();
        }
        textPane1.setText(s);
    }

    public void gohome(){
        remove(newCorsoPanel);
        remove(stampaCorsiPanel);
        add(homePanel);
        setVisible(false);
        setVisible(true);
    }
    public void gocreaCorso(){
        remove(homePanel);
        remove(stampaCorsiPanel);
        add(newCorsoPanel);
        setVisible(false);
        setVisible(true);
    }
    public void gostampaCorsi(){
        toTableModel(agilegym.getElencoCorsi());
        remove(homePanel);
        remove(newCorsoPanel);
        add(stampaCorsiPanel);
        setVisible(false);
        setVisible(true);
    }
}
