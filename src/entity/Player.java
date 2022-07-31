package entity;

import game.Dimensoes;
import game.GamePanel;
import game.KeyHandle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity{
     
    private KeyHandle keyH;
    private List <Tiro> tirosPlayer;
    private float fireRate;
    private float nextFire;
    private Boolean isVisivel;

    public Player(GamePanel gp, KeyHandle keyH, int x, int y, int speed,  Dimensoes dm) {

        super(x, y, speed, "idle", "/res/playerSprites/player_up.png",  "/res/playerSprites/player_down.png",  "/res/playerSprites/player_idle.png",  "/res/playerSprites/player_right.png", dm);
        this.fireRate = 150000000f;
        this.nextFire = 0.5f;
        this.keyH = keyH;
        this.tirosPlayer = new ArrayList<>();
        isVisivel = true;

    }
    
    public void update() {
        if (keyH.getEscPressed() == true)
            System.exit(0);
            
        if(keyH.getUpPressed() == true && getY() >= -getDimensoes().getOriginalTileSize()){
                setDirection("up");
                plusY(-getSpeed()); // y -=  speed
        }

        if(keyH.getDownPressed() == true && getY() <= getDimensoes().getScreenHeight() - 2*getDimensoes().getOriginalTileSize()){
                setDirection("down");
                plusY(getSpeed()); // y +=  speed
            
        }
        
        if(keyH.getLeftPressed() == true && getX() >= -getDimensoes().getOriginalTileSize()){
            
            if(keyH.getDownPressed() == true && getY() <= getDimensoes().getScreenHeight() - 2*getDimensoes().getOriginalTileSize())  {
                setDirection("down");
                plusY(getSpeed()/2);  // y += speed/2;
                plusX(-getSpeed()/2); // x -= speed/2;
            }
            else if(keyH.getUpPressed() == true && getY() >= -getDimensoes().getOriginalTileSize()) {
                setDirection("up");
                plusY(-getSpeed()/2);  // y -= speed/2;
                plusX(-getSpeed()/2); // x -= speed/2;
            }
            else if(keyH.getRightPressed() == true && getX() <= getDimensoes().getScreenWidth() - 2*getDimensoes().getOriginalTileSize()) {
                setDirection("idle");
            }
            else{
                setDirection("idle");
                plusX(-getSpeed());//x -= speed;
            }
        }

        if(keyH.getRightPressed() == true && getX() <= getDimensoes().getScreenWidth() - 2*getDimensoes().getOriginalTileSize()){

            if(keyH.getDownPressed() == true && getY() <= getDimensoes().getScreenHeight() - 2*getDimensoes().getOriginalTileSize()) {
                setDirection("down");  //  direction = "down";
                plusY(getSpeed()/2); // y += speed/2;
                plusX(getSpeed()/2); // x += speed/2;
            }
            else if(keyH.getUpPressed() == true && getY() >= -getDimensoes().getOriginalTileSize()) {
                setDirection("up");  //  direction = "up";
                plusY(-getSpeed()/2); // y -= speed/2;
                plusX(getSpeed()/2); // x += speed/2;
            }
            else{
                setDirection("right");  //  direction = "right";
                plusX(getSpeed()); // x += speed;
            }
        }
        if(keyH.getUpPressed() == false && keyH.getDownPressed() == false && keyH.getLeftPressed() == false && keyH.getRightPressed() == false)
            setDirection("idle"); // direction = "idle";
        
        if(keyH.getJPressed() == true){
        
            float time = System.nanoTime();

            if (time > nextFire){
                nextFire = time + fireRate;
                this.tirosPlayer.add(new Tiro(getX()+74, getY() + 10, getDimensoes().getScreenWidth(), getDimensoes().getTileSize()));
            }
        }
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
        g2.drawImage(image, getX(), getY(), getDimensoes().getTileSize(), getDimensoes().getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }

    public List <Tiro> getTirosPlayer(){
        return tirosPlayer;
    }
    
    public boolean isVisivel(){
        return isVisivel;
    }
    public void setIsVisivel (boolean isVisivel){
        this.isVisivel = isVisivel;
    }

    
}
