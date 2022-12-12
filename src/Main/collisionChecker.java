/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Entity.entity;

/**
 *
 * @author User
 */
public class collisionChecker {
    gamePanel gp;
    
    public collisionChecker(gamePanel gp){
        this.gp = gp;
    }
    public void checkTile(entity entity){
        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;
        
        int entityLeftCol = entityLeftX/gp.tileSize;
        int entityRightCol = entityRightX/gp.tileSize;
        int entityTopRow = entityTopY/gp.tileSize;
        int entityBottomRow = entityBottomY/gp.tileSize;
        
        int tileNum1, tileNum2;
        
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isBasket == true){
                    gp.tileM.mapTileNum[entityLeftCol][entityTopRow] = 0;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isBasket == true){
                    gp.tileM.mapTileNum[entityRightCol][entityTopRow] = 0;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isHunter == true && entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isHunter == true&& entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isBasket == true){
                    gp.tileM.mapTileNum[entityLeftCol][entityBottomRow] = 0;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isBasket == true){
                    gp.tileM.mapTileNum[entityRightCol][entityBottomRow] = 0;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isHunter == true&& entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isHunter == true&& entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }                
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isBasket == true){
                    gp.tileM.mapTileNum[entityLeftCol][entityTopRow] = 0;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isBasket == true){
                    gp.tileM.mapTileNum[entityLeftCol][entityBottomRow] = 0;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isHunter == true&& entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isHunter == true&& entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }                
                break;
            case "right":
                entityRightCol = (entityRightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isBasket == true){
                    gp.tileM.mapTileNum[entityRightCol][entityTopRow] = 0;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isBasket == true){
                    gp.tileM.mapTileNum[entityRightCol][entityBottomRow] = 0;
                }
                if(gp.tileM.tile[tileNum1].collision == true && gp.tileM.tile[tileNum1].isHunter == true&& entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }
                if(gp.tileM.tile[tileNum2].collision == true && gp.tileM.tile[tileNum2].isHunter == true&& entity.protection== false){
                    entity.hp--;
                    entity.x = 100;
                    entity.y = 100;
                    entity.protection = true;
                }                
                break;
        }
        
    }
}
