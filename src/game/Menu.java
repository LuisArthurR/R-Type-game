package game;
import scenario.Background;

import java.awt.Dimension;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends JPanel implements Runnable{
    private int buttonWidth;
    private int buttonHeight;
    private int tileSize;
    private int playWidth;
    private int playHeight;
    private int setaHeight;
    private KeyHandle keyH;
    private BufferedImage ImagemSair;
    private BufferedImage ImagemPlay;
    private BufferedImage ImagemScores;
    private BufferedImage ImagemSeta;
    private Thread gameThread;
    private Background background;


    public Menu(int tileSize, int screenWidth, int screenHeight, int buttonWidth, int buttonHeight){
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.tileSize = tileSize;
        this.playWidth = (screenWidth-buttonWidth)/2;
        this.playHeight = (screenHeight-8*buttonHeight);
        this.setaHeight = playHeight+tileSize/2;
        this.background  = new Background(screenWidth,screenHeight, "ScenarioMountains.png", "ScenarioMountains2.png", "ScenarioSky.png", "ScenarioTrees.png");
        this.keyH = new KeyHandle();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.cyan);
        this.setDoubleBuffered(true);     // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.addKeyListener(keyH);
        this.setFocusable(true);
        try{
            ImagemPlay = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/Play.png"));
            ImagemScores = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/Scores.png"));
            ImagemSair = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/Sair.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
            ImagemSeta = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/Seta.png"));     //Lê o arquivo de imagem e aloca em um BufferedImage
            
        }
        catch(IOException e){
            e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
    }
    public void startGameThread() {

        gameThread = new Thread(this); // Passando o GamePanel para a thread
        
        gameThread.start();
    }
    public void update(){
        if (keyH.getDownPressed() == true){
            if (this.setaHeight > playHeight+4*buttonHeight){
                this.setaHeight = playHeight+tileSize/2;            
            }else
                this.setaHeight += playHeight-tileSize; 
                
        }
        if (keyH.getUpPressed() == true){
            if (this.setaHeight-(playHeight-tileSize) < playHeight){
                this.setaHeight = playHeight+4*buttonHeight+tileSize/2;            
            }else
                this.setaHeight -= playHeight-tileSize; 
                
        }
        if (keyH.getEnterPressed() == true)
        {
            if (setaHeight == playHeight+tileSize/2){
                setVisible(false);
            }
            else
                System.exit(0);
        }
        
    }
    public void run(){
        double drawInterval = 1000000000/60; // 0.01666666sec (tempo em que a função vai ser chamada novamente)
        double delta = 0;
        long lastTime = System.nanoTime(); // retorna um valor da máquina em nanosegundo
        long currentTime;

        while(gameThread != null) {
            //System.out.println("is running");
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {

                update();
                
                //Draw: desenhar na tela as atualizações informações
                repaint(); // Chamando a paintComponent function

                delta--;
            }
        }    

    }
    public void draw(Graphics2D g2) {
        g2.drawImage(this.ImagemSeta, playWidth-2*tileSize, setaHeight, tileSize, tileSize, null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
        g2.drawImage(this.ImagemPlay, playWidth, playHeight, buttonWidth, buttonHeight, null);
        g2.drawImage(this.ImagemScores, playWidth, playHeight+2*buttonHeight, buttonWidth, buttonHeight, null); 
        g2.drawImage(this.ImagemSair, playWidth, playHeight+4*buttonHeight, buttonWidth, buttonHeight, null); 
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // imprime o objeto na tela

        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.
        background.draw(g2);
        draw(g2);
        
        g2.dispose();
    }

}