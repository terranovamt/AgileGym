package org.unict.UI;

import org.unict.domain.Agilegym;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JLabel welcome;
    private JPanel mainPanel;
    private JButton inserisciCorso;
    private JButton inserisciLezione;
    private JTabbedPane menu;
    private JPanel newCorsoPanel;
    private JPanel newLezionePanel;
    private JLabel idCorsoText;
    private JLabel idCorsoValue;
    private JTextField nomeCorsoValue;
    private JTextField focusValue;
    private JTextField textField3;
    private JLabel focusText;
    private JLabel livelloText;
    private JLabel attrezzoText;
    private JComboBox comboBox1;

    public MainFrame(){
        setTitle("Benvenuti");
        setBounds(300,200,700,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(mainPanel);
        Agilegym agilegym = Agilegym.getInstance();




        inserisciCorso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
