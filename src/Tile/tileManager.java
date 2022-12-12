/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tile;

import Main.gamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class tileManager {
    gamePanel gp;
    public tile[] tile;
    public int mapTileNum[][];
    public int needBasketNum;
    boolean isHunterStartPosStored = false;
    public boolean isNextLvl = false;
    public ArrayList<Integer> hunterXList = new ArrayList<Integer>();
    public ArrayList<Integer> hunterYList = new ArrayList<Integer>();
    
    
    public tileManager(gamePanel gp){
        this.gp = gp;
        tile = new tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("../resource/map/lvl1.txt");
    }
    public void getTileImage(){
        try{
            tile[1] = new tile();
            tile[1].image = ImageIO.read(getClass().getResource("../resource/img/mountain.png"));
            tile[1].collision = true;
            
            tile[7] = new tile();
            tile[7].image = ImageIO.read(getClass().getResource("../resource/img/tree.png"));
            tile[7].collision = true;
            
            tile[8] = new tile();
            tile[8].image = ImageIO.read(getClass().getResource("../resource/img/basket.png"));
            tile[8].collision = true;
            tile[8].isBasket = true;
            
            tile[4] = new tile();
            tile[4].image = ImageIO.read(getClass().getResource("../resource/img/hunter.png"));
            tile[4].collision = true;
            tile[4].isHunter = true;
            
            tile[0] = new tile();
            
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while(col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();
                
                while(col < gp.maxScreenCol){
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[col][row] =num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;        
                }
            }
            br.close();
        }catch(Exception e){
            
        }
    }
    
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        int basketCnt = 0;
        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNum[col][row];
            if(tileNum == 0 ){
                g2.setColor(new Color(153,102,0));
                g2.fillRect(x, y, gp.tileSize, gp.tileSize);
            }
            else{
                g2.drawImage(tile[tileNum].image,x,y,gp.tileSize, gp.tileSize,null);
            }
            if(tileNum == 4 && (isHunterStartPosStored == false||isNextLvl == true)){
                hunterXList.add(col);
                hunterYList.add(row);
            }
            col++;
            x += gp.tileSize;
            if(tileNum == 8){
                basketCnt++;
            }
            if(col == gp.maxScreenCol){
                col = 0;
                x=0;
                row++;
                y+=gp.tileSize;
            }
            
        }
        isHunterStartPosStored = true;
        isNextLvl = false;
        needBasketNum=basketCnt;
        

        
        
    }

}
