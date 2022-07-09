package entity;

import java.awt.image.BufferedImage;

public class Entity {

    public int x, y;
    public int speed;
    //public int Life;

    public BufferedImage up, down, idle, right; // para guardar arquivos de imagem
    public String direction;

    public Entity(int x, int y, int speed) {
        this.x = x; 
        this.y = y;
        this.speed = speed;
    }
}

