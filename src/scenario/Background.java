package scenario;

import game.GamePanel;
import java.awt.Graphics2D;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class Background {
    
    GamePanel gp;
    BufferedImage backgroundMountains_1, backgroundMountains_2, backgroundSky, scenarioTrees;

    int bx = 0; 
    int bx2 = 1512;
    int fx = 0; 
    int fx2 = 1512;
    int mx = 0; 
    int mx2 = 1512;

    public Background(GamePanel gp) {

        this.gp = gp;
        getBackgroundImage();
    }

    public void getBackgroundImage() {
        /* 
        Usar try é uma maneira mais robusta de tratar possíveis erros no momento da conversão, 
        com o try podemos evitar uma queda brusca e então tratar o erro da melhor forma.
        */
        try{
            backgroundMountains_1 = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/ScenarioMountains.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
            backgroundMountains_2 = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/ScenarioMountains2.png"));
            backgroundSky = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/ScenarioSky.png"));
            scenarioTrees = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/ScenarioTrees.png"));
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }

    public void draw(Graphics2D g2) {

        int width = gp.getScreenWidth();

        // Desenha cada layer na tela

        g2.drawImage(backgroundSky, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

        g2.drawImage(backgroundMountains_1, bx, 0, gp.getScreenWidth() + 4, gp.getScreenHeight(), null);
        g2.drawImage(backgroundMountains_1, bx2, 0, gp.getScreenWidth() + 4, gp.getScreenHeight(), null); //Cria uma cópia dessa layer que vai aparecer logo depois da primeira

        g2.drawImage(backgroundMountains_2, fx, 0, gp.getScreenWidth() + 4, gp.getScreenHeight(), null);
        g2.drawImage(backgroundMountains_2, fx2, 0, gp.getScreenWidth() + 4, gp.getScreenHeight(), null);  //Cria uma cópia dessa layer que vai aparecer logo depois da primeira

        g2.drawImage(scenarioTrees, mx, 0, gp.getScreenWidth() + 4, gp.getScreenHeight(), null);
        g2.drawImage(scenarioTrees, mx2, 0, gp.getScreenWidth() + 4, gp.getScreenHeight(), null);  //Cria uma cópia dessa layer que vai aparecer logo depois da primeira

        // Move cada layer na tela

        bx--;
        fx-=4;
        mx -=7;
        
        bx2--;
        fx2-=4;
        mx2-=7;

        //Move a Layer para o início depois de percorrer toda a tela

        //Farly montains
        if(bx < (-1 * width)) {
            bx = width;
        }
        if(bx2 < (-1 * width)) {
            bx2 = width;
        }

        //Nearly montains
        if(fx < (-1 * width)) {
            fx = width;
        }
        if(fx2 < (-1 * width)) {
            fx2 = width;
        }

        //Trees
        if(mx < (-1 * width)) {
            mx = width;
        }
        if(mx2 < (-1 * width)) {
            mx2 = width;
        }
    }
}