package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import game.Dimensoes;

public class Enemy extends Entity{

    private List <Tiro> tirosEnemy;
    float fireRate;
    float nextFire;
    
    public Enemy(int x, int y, int speed, Dimensoes dm) {
        super(x, y, speed, "idle","/res/enemySprites/enemy_up.png", "/res/enemySprites/enemy_down.png", "/res/enemySprites/enemy_idle.png", "/res/enemySprites/enemy_right.png", dm);
        this.fireRate = 1800000000f;
        this.nextFire = 0.5f;
        this.tirosEnemy = new ArrayList<>();
    }

    
    public void update() {
        
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
        g2.drawImage(image, getX(), getY(), getDimensoes().getTileSize(),getDimensoes().getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }

    public List <Tiro> getTirosEnemy(){
        return tirosEnemy;
    }

    
}