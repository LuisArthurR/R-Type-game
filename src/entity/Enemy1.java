package entity;

import game.GamePanel;

public class Enemy1 extends Enemy{

    private Player player;
    
    public Enemy1(GamePanel gp, int x, int y, int speed, Player player) {
        super(gp, x, y, speed);
        this.player = player;
    }

    public void update() {

        plusX(-getSpeed());//  x -= speed;

        if(getX() >= 1000) 
            plusX(-getSpeed());//  x -= speed;   

        if(getX() < -100)
            setIsVisivel(false);
  
        if(this.getY() > player.getY() + 20){
            plusY(-getSpeed()); // y -=  speed
            setDirection("up");
        }
        else if(this.getY() < player.getY() - 20){
            plusY(+getSpeed());//  y += speed;
            setDirection("down");
        }
        else{
            setDirection("right");
        } 
        
        float time = System.nanoTime();

        if (time > nextFire){
            nextFire = time + fireRate;
            this.getTirosEnemy().add(new Tiro(getX()-74, getY() + 10, getGp().getScreenWidth(), getGp().getTileSize()));
        }
    }
}