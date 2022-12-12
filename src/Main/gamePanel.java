/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Entity.player;
import Tile.tileManager;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author User
 */
public class gamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    
    int lvl = 1;
    int tempScore = -1;
    int playerScore = 0;
    int fpsCount = 0;
    int AILastMoveTime=0;
    
    boolean gameEnd = false;
    // FPS
    int FPS = 60;
    tileManager tileM = new tileManager(this);
    keyHandler keyH = new keyHandler();
    Thread gameThread;
    public collisionChecker cChecker = new collisionChecker(this);
    player playerB = new player(this, keyH);
    

    
    public gamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(153,102,0));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run(){
        
        double drawInterval = 1000000000/FPS; // 0.01666 seconds for one frame (60PFS/S)
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        
        while(gameThread != null){
            // update
            update();
            // draw
            repaint();
            fpsCount++;
            
            try{
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long)remainingTime);
                
                nextDrawTime += drawInterval;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
    public void update(){
        playerB.update();
        
        
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        tileM.draw(g2);
        playerB.draw(g2);
        drawUI(g);
        AI(fpsCount/60);
        playerProtection();
        try {
            nextLevelorLose();
        } catch (InterruptedException ex) {
            Logger.getLogger(gamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        g2.dispose();
    }
    public void drawUI(Graphics g){
        Font font = new Font("Arial", Font.BOLD, 20); 
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Need:"+Integer.toString(tileM.needBasketNum), 35, 35);
        // need basket num to pass
        if (tempScore == -1){
            tempScore = tileM.needBasketNum;
        }
        if(tempScore != tileM.needBasketNum){
            tempScore = tileM.needBasketNum;
            playerScore++;
        }
        g.drawString("Score:"+Integer.toString(playerScore), 120, 35);
        // player score
        g.drawString("Time:"+Integer.toString(fpsCount/60)+"s", 205, 35);
        // timer
        g.drawString("HP:"+Integer.toString(playerB.hp), 300, 35);
        // Hp
        g.drawString("lvl:"+Integer.toString(lvl), 390, 35);
        // lvl
        g.dispose();
    }

    public void AI(int time){
        for(int i = 0; i < tileM.hunterXList.size();i++){
            int dirIndex = getRandomNumber(1,4);
            if(time % 2 == 0 && AILastMoveTime != time){
                switch(dirIndex){
                    case 1:
                        if(tileM.hunterYList.get(i)-1 > 0){
                            if(tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)-1]==0){
                                tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)-1] = 4;
                                tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)] = 0;
                                tileM.hunterXList.set(i,tileM.hunterXList.get(i));
                                tileM.hunterYList.set(i,(tileM.hunterYList.get(i)-1));
                            }
                        }
                        break;
                    case 2:
                        if(tileM.hunterYList.get(i)+1 < 11){
                            if(tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)+1]==0){
                                tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)+1] = 4;
                                tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)] = 0;
                                tileM.hunterXList.set(i,tileM.hunterXList.get(i));
                                tileM.hunterYList.set(i,(tileM.hunterYList.get(i)+1));
                            }
                        }
                        break;
                    case 3:
                        if(tileM.hunterXList.get(i)-1 > 0){
                            if(tileM.mapTileNum[tileM.hunterXList.get(i)-1][tileM.hunterYList.get(i)]==0){
                                tileM.mapTileNum[tileM.hunterXList.get(i)-1][tileM.hunterYList.get(i)] = 4;
                                tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)] = 0;
                                tileM.hunterXList.set(i,tileM.hunterXList.get(i)-1);
                                tileM.hunterYList.set(i,(tileM.hunterYList.get(i)));
                            }
                        }
                        break;
                    case 4:
                        if(tileM.hunterXList.get(i)+1 < 15){
                            if(tileM.mapTileNum[tileM.hunterXList.get(i)+1][tileM.hunterYList.get(i)]==0){
                                tileM.mapTileNum[tileM.hunterXList.get(i)+1][tileM.hunterYList.get(i)] = 4;
                                tileM.mapTileNum[tileM.hunterXList.get(i)][tileM.hunterYList.get(i)] = 0;
                                tileM.hunterXList.set(i,tileM.hunterXList.get(i)+1);
                                tileM.hunterYList.set(i,(tileM.hunterYList.get(i)));
                            }
                        }
                        break;
                }     
            }
        }
        AILastMoveTime = time;
    }
    public void playerProtection(){
        if(playerB.protection == true){
            playerB.protection = false;
        }
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
    public void nextLevelorLose() throws InterruptedException{
        if(tileM.needBasketNum == 0){
            lvl++;
            switch(lvl){
                case 2:
                    tileM.loadMap("../resource/map/lvl2.txt");
                    break;
                case 3:
                    tileM.loadMap("../resource/map/lvl3.txt");
                    break;
                case 4:
                    tileM.loadMap("../resource/map/lvl4.txt");
                    break;
                case 5:
                    tileM.loadMap("../resource/map/lvl5.txt");
                    break;
                case 6:
                    tileM.loadMap("../resource/map/lvl6.txt");
                    break;
                case 7:
                    tileM.loadMap("../resource/map/lvl7.txt");
                    break;
                case 8:
                    tileM.loadMap("../resource/map/lvl8.txt");
                    break;
                case 9:
                    tileM.loadMap("../resource/map/lvl9.txt");
                    break;
                case 10:
                    tileM.loadMap("../resource/map/lvl10.txt");
                    break;
                case 11:
                    gameEnd = true;
                    gameThread = null;
                    this.removeAll();
                    repaint();
                    this.setVisible(false);
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    topFrame.setVisible(false);
                    String m = JOptionPane.showInputDialog("You Win! write ur name to save your score");
                    System.out.println(m);
                    System.out.println(playerScore);
                    BufferedWriter out = null;
                    try {
                            FileWriter fstream = new FileWriter("src/resource/table/scoreInfo.txt", true); //true tells to append data.
                            out = new BufferedWriter(fstream);
                            if(m != null){
                                String newInfo = "";
                                newInfo = "\n" + m + " " +Integer.toString(playerScore);
                                out.write(newInfo);
                            }
                            out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(gamePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    topFrame.remove(this);
                    topFrame.setVisible(true);
                    
                    
            }
            playerScore = playerScore -1;
            playerB.x=100;
            playerB.y=100;
            playerB.hp = 3;
            
            tileM.hunterXList.clear();
            tileM.hunterYList.clear();
            fpsCount = 0;
            tileM.isNextLvl = true;
        }
        if(playerB.hp == 0){
                    
                    gameThread = null;
                    gameEnd = true;
                    this.removeAll();
                    repaint();
                    this.setVisible(false);
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    topFrame.setVisible(false);
                    String m = JOptionPane.showInputDialog("You Lose! write ur name to save your score");
                    System.out.println(m);
                    System.out.println(playerScore);
                    BufferedWriter out = null;
                    try {
                            FileWriter fstream = new FileWriter("src/resource/table/scoreInfo.txt", true); //true tells to append data.
                            out = new BufferedWriter(fstream);
                            if(m != null){
                                String newInfo = "";
                                newInfo = "\n" + m + " " +Integer.toString(playerScore);
                                out.write(newInfo);
                            }
                            out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(gamePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    topFrame.remove(this);
                    topFrame.setVisible(true);
                    
        }
    }
    

}
