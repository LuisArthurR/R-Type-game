package scenario;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class Background {
    
    private BufferedImage backgroundMountains_1, backgroundMountains_2, backgroundSky, scenarioTrees;
    private int screenWidth;
    private int screenHeight;
    private int bx; 
    private int bx2;
    private int fx; 
    private int fx2;
    private int mx; 
    private int mx2;
    private int velocidadefx;
    private int velocidadebx;
    private int velocidademx;

    public Background(int screenWidth, int screenHeight, String str1,String str2, String str3, String str4) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.bx = 0;
        this.bx2 = screenWidth;
        this.fx = 0;
        this.fx2 = screenWidth;
        this.mx = 0;
        this.mx2 = screenWidth;
        this.velocidadebx = 2;
        this.velocidadefx = 7;
        this.velocidademx = 12;

        try{
            backgroundMountains_1 = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/"+str1));     //Lê o arquivo de imagem e aloca em um BufferedImage
            backgroundMountains_2 = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/"+str2));
            backgroundSky = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/"+str3));
            scenarioTrees = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/"+str4));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void draw(Graphics2D g2) {
        // Desenha cada layer na tela
        int t = 4;
        g2.drawImage(backgroundSky, 0, 0, screenWidth, screenHeight, null);

        g2.drawImage(backgroundMountains_1, bx, 0, screenWidth + t, screenHeight, null);
        g2.drawImage(backgroundMountains_1, bx2, 0, screenWidth+ t, screenHeight, null); //Cria uma cópia dessa layer que vai aparecer logo depois da primeira

        g2.drawImage(backgroundMountains_2, fx, 0, screenWidth+ t, screenHeight, null);
        g2.drawImage(backgroundMountains_2, fx2, 0, screenWidth + t, screenHeight, null);  //Cria uma cópia dessa layer que vai aparecer logo depois da primeira

        g2.drawImage(scenarioTrees, mx, 0, screenWidth+ t, screenHeight, null);
        g2.drawImage(scenarioTrees, mx2, 0, screenWidth + t, screenHeight, null);  //Cria uma cópia dessa layer que vai aparecer logo depois da primeira

        // Move cada layer na tela

        bx-=velocidadebx;
        fx-=velocidadefx;
        mx -=velocidademx;
        
        bx2-=velocidadebx;
        fx2-=velocidadefx;
        mx2-=velocidademx;

        //Move a Layer para o início depois de percorrer toda a tela

        //Farly montains
        if(bx < (-screenWidth)) {
            bx = screenWidth;
        }
        if(bx2 < (-screenWidth)) {
            bx2 = screenWidth;
        }

        //Nearly montains
        if(fx < (-screenWidth)) {
            fx = screenWidth;
        }
        if(fx2 < (-screenWidth)) {
            fx2 = screenWidth;
        }

        //Trees
        if(mx < (-screenWidth)) {
            mx = screenWidth;
        }
        if(mx2 < (-screenWidth)) {
            mx2 = screenWidth;
        }
    }
}