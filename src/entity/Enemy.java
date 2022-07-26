package entity;

import game.GamePanel;
import java.awt.Rectangle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity{

    private List <Tiro> tirosEnemy;
    float fireRate;
    float nextFire;
    
    public Enemy(GamePanel gp, int x, int y, int speed) {
        super(x, y, speed, "idle", gp,"/res/enemySprites/enemy_up.png", "/res/enemySprites/enemy_down.png", "/res/enemySprites/enemy_idle.png", "/res/enemySprites/enemy_right.png");
        this.tirosEnemy = new ArrayList<>();
        this.fireRate = 1800000000f;
        this.nextFire = 0.5f;
    }

    
    public void update() {
        
    }

    public Rectangle getBounds(){
        return new Rectangle(getX(),getY(),15,42);
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(getDirection()) {
            case "up":
                image = getUp();
                break;
            
            case "down":
                image = getDown();
                break;

            case "idle":
                image = getIdle();
                break;

            case "right":
                image = getRight();
                break;
        }
        g2.drawImage(image, getX(), getY(), getGp().getTileSize(), getGp().getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }

    public List <Tiro> getTirosEnemy(){
        return tirosEnemy;
    }
}