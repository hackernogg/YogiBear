/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Main.gamePanel;
import Main.keyHandler;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class player extends entity{
    gamePanel gp;
    keyHandler keyH;
    public player(gamePanel gp, keyHandler keyH){
        this.gp = gp;
        this.keyH =keyH;
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        try{
            pic1 = ImageIO.read(getClass().getResource("../resource/img/bear.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void update(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        if (keyH.upPressed == true ||keyH.downPressed == true ||
                keyH.leftPressed == true||keyH.rightPressed == true ){
        if(keyH.upPressed == true){
            direction = "up";
            
        }
        else if(keyH.downPressed == true){
            direction = "down";
            
        }
        else if(keyH.leftPressed == true){
            direction = "left";
            
        }
        else if(keyH.rightPressed == true){
            direction = "right";
            
        }
        //check tile collision
        collisionOn = false;
        gp.cChecker.checkTile(this);
        
        // if collision
        
        if(collisionOn == false){
            switch(direction){
                case "up":
                    y -= speed;
                    break;
                case "down":
                    y += speed;
                    break;
                case "left":
                    x -= speed;
                    break;
                case "right":
                    x += speed;
                    break;
            }
        }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = pic1;
        g2.drawImage(image, x,y, gp.tileSize,gp.tileSize,null);
    }
}
