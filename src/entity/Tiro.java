package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Tiro {
    
    private BufferedImage ImagemTiro;
    private int x, y;
    private boolean isVisivel;
    private int velocidade;
    private int largura;
    private int tileSize;

    public Tiro(int x, int y, int largura, int tileSize) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.tileSize = tileSize;
        this.velocidade = 12;
        this.isVisivel = true;
        try{
            ImagemTiro = ImageIO.read(this.getClass().getResourceAsStream("/res/playerSprites/tiro.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }
      
    public void draw(Graphics2D g2) {
        g2.drawImage(this.ImagemTiro, x, y, tileSize, tileSize, null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }
    
    public void update(String direction) {

        if(direction == "right")
            this.x += velocidade;

        else if(direction == "left")
            this.x -= velocidade;

        if (this.x > largura){
            isVisivel = false;
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,42,42);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setIsVisivel(boolean isVisivel){
        this.isVisivel = isVisivel;
    }
    public boolean getIsVisivel() {
        return isVisivel;
    }  
     
}
