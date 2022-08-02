package entity;

import game.Dimensoes;

public class Enemy2 extends Enemy{

    private float actionRate = 400000000f;    //private float actionRate = 1000000000f;
    private float nextAction = 0;
    private int posY;

    public Enemy2(int x, int y, int speed, Dimensoes dm) {
        super(x, y, speed,dm);
    }

    public void update() {

        if(getX() < -100)
            setIsVisivel(false);  

        if(getX() >= 1000) {
            plusX(-getSpeed());//  x -= speed; 
            setDirection("right");
        }

        if(getX() <= 1000) {

            float time = System.nanoTime();

            if(time > nextAction){
                nextAction = time + actionRate;
                posY = (int)(Math.random() * 520 + 40);
            }

            if((this.getY() - 100) > posY){
                plusY(-getSpeed()); // y -=  speed
                setDirection("up");
            }
            else if((this.getY() + 100) < posY){
                plusY(+getSpeed());//  y += speed;
                setDirection("down");
            }
            else{
                setDirection("idle");
            }
        }

        float time = System.nanoTime();

        if (time > nextFire){
            nextFire = time + fireRate;
            this.getTirosEnemy().add(new Tiro(getX()-30, getY() + 10, getDimensoes().getScreenWidth(), getDimensoes().getTileSize(), 8));
        }
    }
}