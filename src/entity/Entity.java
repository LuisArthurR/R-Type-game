package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;

public class Entity {
    private GamePanel gp;
    private int x, y;
    private int speed;
    //public int Life;

    private BufferedImage up, down, idle, right; // para guardar arquivos de imagem
    private String direction;
    private boolean isVisivel;

    public Entity(int x, int y, int speed, String direction, GamePanel gp, String str1, String str2, String str3, String str4 ) {
        this.x = x; 
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.isVisivel = true;
        this.gp = gp;

        try{
            up = ImageIO.read(this.getClass().getResourceAsStream(str1));     //Lê o arquivo de imagem e aloca em um BufferedImage
            down = ImageIO.read(this.getClass().getResourceAsStream(str2));
            idle = ImageIO.read(this.getClass().getResourceAsStream(str3));
            right = ImageIO.read(this.getClass().getResourceAsStream(str4));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void setIsVisivel(boolean isVisivel){
        this.isVisivel = isVisivel;
    }
    public boolean getIsVisivel() {
        return isVisivel;
    }  
    
    public void plusX(int s){
        this.x += s;
    }

    public int  getX() {
        return x;
    }

    public void plusY(int s){
        this.y += s;
    }

    public int  getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
   
    public String getDirection() {
        return direction;
    }

    public void setY(int y) {
        this.y = y;
    }
   
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BufferedImage getUp (){
        return this.up;
    }
    public BufferedImage getDown (){
        return this.down;
    }
    public BufferedImage getIdle (){
        return this.idle;
    }
    public BufferedImage getRight (){
        return this.right;
    }
    public GamePanel getGp(){
        return this.gp;
    }

}

