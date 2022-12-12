/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class main {
    public static ArrayList<String> sortLines(ArrayList<String> lines){
        int n = lines.size();
       System.out.println(lines);
        for(int i = 0 ; i < n;i++){
            int maxIDX = i;
            for(int j = i+1; j<n;j++){
                String strMax = lines.get(maxIDX);
                String strj = lines.get(j);

                int MaxNum = Integer.parseInt(strMax.split("\\s+")[1]);
                int jNum = Integer.parseInt(strj.split("\\s+")[1]);
                if(MaxNum < jNum){
                    maxIDX = j;
                }
            }
            String temp = lines.get(i);
            lines.set(i,lines.get(maxIDX));
            lines.set(maxIDX,temp);
            System.out.println(maxIDX);
        }
        return lines;
    }
    public static String rankString(ArrayList<String> lines){
        int n = lines.size();
        String ans = "";
        if(n<=10){
            for(int i = 0 ; i < n ; i++){
                ans = ans + Integer.toString(i+1) + ": " + lines.get(i) + "\n";
            }
        }
        else
        {
            for(int i = 0 ; i < 10 ; i++){
                ans = ans + Integer.toString(i+1) + ": " + lines.get(i) + "\n";
            }
        }
     
        return ans;
    }
    public static void main(String[] args)
    {
        JFrame window = new JFrame();
        window.setSize(800,800);
        window.setFocusable(false);
        
        JPanel menu = new JPanel();
        JButton playButton = new JButton("PLAY");
        JButton tableButton = new JButton("Scoreboard");
        menu.setFocusable(false);
        playButton.setFocusable(false);
        tableButton.setFocusable(false);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gamePanel theGamePanel = new gamePanel();
                window.add(theGamePanel);
                window.setFocusable(false);
                window.pack();
                theGamePanel.requestFocus();
                window.setLocationRelativeTo(null);
                window.setVisible(true);

                theGamePanel.startGameThread();
            }
        });
        tableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList<String> lines = new ArrayList<String>();
                BufferedReader reader;
                try {
                    reader = new BufferedReader(new FileReader("src/resource/table/scoreInfo.txt"));
                    String line;
                    line = reader.readLine();
                    
                    while (line != null) {
                        System.out.println(line);
                        lines.add(line);
                        line = reader.readLine();
                    }
                    System.out.println(lines.size());
                    lines =sortLines(lines);
                    System.out.println(lines);
                    
                    JOptionPane.showMessageDialog(null, rankString(lines));
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        menu.add(playButton);
        menu.add(tableButton);
        window.add(menu);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Yogi Bear");
        window.show();

        
    }
}
