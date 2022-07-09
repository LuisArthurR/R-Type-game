package entity;

import game.GamePanel;
import game.KeyHandle;

import java.awt.Graphics2D;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    
    GamePanel gp;
    KeyHandle keyH;

    public Player(GamePanel gp, KeyHandle keyH, int x, int y, int speed) {
        super(x, y, speed);
        direction = "idle";
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImage();
    }

    public void getPlayerImage() {
        /* 
        Usar try é uma maneira mais robusta de tratar possíveis erros no momento da conversão, 
        com o try podemos evitar uma queda brusca e então tratar o erro da melhor forma.
        */
        try{
            up = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_up.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
            down = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_down.png"));
            idle = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_idle.png"));
            right = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/player_right.png"));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void update() {

        if(keyH.upPressed == true){
            direction = "up";
            y -= speed;
        }

        if(keyH.downPressed == true){
            direction = "down";
            y += speed;
        }

        if(keyH.leftPressed == true){
            
            if(keyH.downPressed == true) {
                direction = "down";
                y += speed/2;
                x -= speed/2;
            }
            else if(keyH.upPressed == true) {
                direction = "up";
                y -= speed/2;
                x -= speed/2;
            }
            else if(keyH.rightPressed == true) {
                direction = "idle";
            }
            else{
                direction = "idle";
                x -= speed;
            }
        }

        if(keyH.rightPressed == true){

            if(keyH.downPressed == true) {
                direction = "down";
                y += speed/2;
                x += speed/2;
            }
            else if(keyH.upPressed == true) {
                direction = "up";
                y -= speed/2;
                x += speed/2;
            }
            else{
                direction = "right";
                x += speed;
            }
        }
        
        if(keyH.upPressed == false && keyH.downPressed == false && keyH.leftPressed == false && keyH.rightPressed == false)
            direction = "idle";
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
