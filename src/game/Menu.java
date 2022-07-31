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
    private double actionRate;
    private double nextAction;
    private KeyHandle keyH;
    private BufferedImage ImagemSair;
    private BufferedImage ImagemPlay;
    private BufferedImage ImagemScores;
    private BufferedImage ImagemSeta;
    private Thread gameThread;
    private Background background;
    private int screenWidth;
    private int screenHeight;
    private Tela tela;
    private int originalTileSize;
    private int DistanciaDoisbotoes;


    public Menu(int tileSize, int screenWidth, int screenHeight, int buttonWidth, int buttonHeight, int originalTileSize){

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.originalTileSize = originalTileSize;
        this.tileSize = tileSize;
        this.playWidth = (screenWidth-buttonWidth)/2;
        this.playHeight = screenWidth/8;
        this.DistanciaDoisbotoes = 2*buttonHeight;
        this.setaHeight = playHeight+originalTileSize/2;
        this.actionRate = 150000000f;
        this.nextAction = 0.5f;
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

        this.tela = new Tela(this);
    }
    public void startGameThread() {

        gameThread = new Thread(this); // Passando o GamePanel para a thread
        
        gameThread.start();
    }
    public void update(){
        if (keyH.getDownPressed() == true){
            float time = System.nanoTime();
            System.out.println("ola");
            if (time > nextAction){
                nextAction = time + actionRate;
                if (this.setaHeight > playHeight+2*DistanciaDoisbotoes){
                    this.setaHeight = playHeight+originalTileSize/2;            
                }else
                    this.setaHeight += DistanciaDoisbotoes; 
           }
        }
        if (keyH.getUpPressed() == true){
            
            float time = System.nanoTime();
            if (time > nextAction){
                nextAction = time + actionRate;
                if (this.setaHeight-(DistanciaDoisbotoes) < playHeight){
                    this.setaHeight = playHeight+4*buttonHeight+originalTileSize/2;            
                }else
                    this.setaHeight -= DistanciaDoisbotoes; 
            }       
        }
        if (keyH.getEnterPressed() == true)
        {
            if (setaHeight == playHeight+originalTileSize/2){
                tela.setVisible(false);     
                new GamePanel(tileSize, screenWidth, screenHeight);
                gameThread = null;
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
        g2.drawImage(this.ImagemSeta, playWidth-2*originalTileSize, setaHeight, originalTileSize, originalTileSize, null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
        g2.drawImage(this.ImagemPlay, playWidth, playHeight, buttonWidth, buttonHeight, null);
        g2.drawImage(this.ImagemScores, playWidth, playHeight+DistanciaDoisbotoes, buttonWidth, buttonHeight, null); 
        g2.drawImage(this.ImagemSair, playWidth, playHeight+2*DistanciaDoisbotoes, buttonWidth, buttonHeight, null); 
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // imprime o objeto na tela

        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.
        background.draw(g2);
        draw(g2);
        
        g2.dispose();
    }

}