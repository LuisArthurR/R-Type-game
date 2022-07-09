package entity;

import game.GamePanel;

import java.io.IOException;

import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Enemy extends Entity{
    
    GamePanel gp;

    public Enemy(GamePanel gp, int x, int y, int speed) {
        super(x, y, speed);
        direction = "idle";
        this.gp = gp;
        getEnemyImage();
    }

    public void getEnemyImage() {
        
        try{
            up = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/enemy_up.png"));         //Lê o arquivo de imagem e aloca em um BufferedImage
            down = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/enemy_down.png"));
            idle = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/enemy_idle.png"));
            right = ImageIO.read(this.getClass().getResourceAsStream("/res/enemySprites/enemy_right.png"));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void update() {

        x -= speed;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                image = up;
                break;
            
            case "down":
                image = down;
                break;

            case "idle":
                image = idle;
                break;

            case "right":
                image = right;
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayerSpeed() {
        return speed;
    }
}
