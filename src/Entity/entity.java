/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author User
 */
public class entity {
    
    public int x,y;
    public int speed;
    public int hp = 3;
    public BufferedImage pic1;
    public String direction;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public boolean protection = false;
    
}
