package game;

import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entity.Player;
import entity.Tiro;
import java.awt.Rectangle;
import scenario.Background;
import java.awt.image.BufferedImage;
import java.io.IOException;

import entity.Enemy;
import entity.Enemy1;
import entity.Enemy2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{
    
    private int tileSize;
    private int screenHeight;
    private int screenWidth;
    private static int FPS = 60;
    private KeyHandle keyH = new KeyHandle();
    private Thread gameThread; // Quando essa thread iniciar, vai continuar o programa rodando até você fechar.
    // FPS
    
    //Posição inicial do player e velocidade
    private Player player;
    //Criação da fase e seu cenário
    private Background background;
    //Invocando os inimigos
    private List<Enemy> enemy;
    private List<Tiro> tiros;
    private int quantidade;
    private long spawnRate;
    private long nextSpawn;
    private boolean emJogo;
    private BufferedImage endgame;

    public GamePanel(int tileSize, int screenWidth, int screenHeight) {

        this.tileSize = tileSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.background  = new Background(screenWidth,screenHeight, "ScenarioMountains.png", "ScenarioMountains2.png", "ScenarioSky.png", "ScenarioTrees.png");
        this.enemy = new ArrayList<Enemy>();
        this.spawnRate = 1500000000;
        this.nextSpawn = 0;
        this.quantidade = 0;
        this.player = new Player(this, keyH, 100, 100, 5);
        this.tiros = player.getTirosPlayer();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try{
            endgame = ImageIO.read(this.getClass().getResourceAsStream("/res/fimdejogo.png"));  
        }catch(IOException e){
                e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
        emJogo = true;
    }

    public void spawnEnemies() {

        int posX = (int)(Math.random() * 2000 + 1512);
        int posY = (int)(Math.random() * 370 + 40);

        enemy.add(new Enemy2(this, posX, posY, 4));

        enemy.add(new Enemy1(this, posX, posY, 5, player));
        quantidade++;
    }

    public void startGameThread() {

        gameThread = new Thread(this); // Passando o GamePanel para a thread
        gameThread.start();
    }

    /*
        Usando para criar uma thread. Essa thread vai manter o GamePanel Rodando, 
        mesmo quer outros threas se iniciam durante a execução
    */
    @Override
    public void run() {
        //Game Loop() (metódo delta)

        double drawInterval = 1000000000/FPS; // 0.01666666sec (tempo em que a função vai ser chamada novamente)
        double delta = 0;
        long lastTime = System.nanoTime(); // retorna um valor da máquina em nanosegundo
        long currentTime;

        while(gameThread != null) {
            //System.out.println("is running");
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {

                //Update: atualizar as infomrações da tela, assim como a posição do jogador
                update();
                if(currentTime > nextSpawn) {
                    nextSpawn = currentTime + spawnRate;
                    spawnEnemies();
                }
                

                //checarColisoes();
                //Draw: desenhar na tela as atualizações informações
                repaint(); // Chamando a paintComponent function

                delta--;
            }
        }
    }

    public void update() {
        
        player.update();
       
        colisao();
        Iterator<Tiro>iter=tiros.iterator();
        while(iter.hasNext()){
            Tiro aux = iter.next();
            if (aux.getIsVisivel()){
                aux.update("right");
            }
            else
                iter.remove();
        }

        if(enemy.size() < 1) {
            spawnEnemies();
        } 

        if(enemy.size() > 0) {
            for(int i = 0; i < quantidade; i++) {
                enemy.get(i).update();
            }
        }
        for(int i = 0; i < enemy.size(); i++) {
            Iterator<Tiro> iterN = enemy.get(i).getTirosEnemy().iterator();

            while(iterN.hasNext()) {
            Tiro aux = iterN.next();
            if (aux.getIsVisivel())
                aux.update("left");

            else
                iterN.remove();
            }
        }
        if(enemy.size() > 0) {
            for(int i = 0; i < quantidade; i++) {
                if (enemy.get(i).getIsVisivel()== false){
                    enemy.remove(i);
                    quantidade--;
                }
            }
           
        }

    }

    public void colisao(){
        Rectangle formaEnemy;
        Rectangle formaTiro;
        Rectangle formaNave = player.getBounds();
        
        for (int i = 0; i < enemy.size(); i++){
            Enemy tempEnemy = enemy.get(i);
            formaEnemy = tempEnemy.getBounds();
                if(formaNave.intersects(formaEnemy)){
                    player.setIsVisivel(false);
                    tempEnemy.setIsVisivel(false);
                    emJogo = false;
                }
        }

        for (int i = 0; i < tiros.size();i++){
            formaTiro = tiros.get(i).getBounds();

            for (int j = 0; j < enemy.size();j++){

                formaEnemy = enemy.get(j).getBounds();
                if (formaTiro.intersects(formaEnemy)){
                    tiros.get(i).setIsVisivel(false);
                    enemy.get(j).setIsVisivel(false);
                }
            }
        }
        
    }

    public void draw(Graphics2D g2){
        g2.drawImage(this.endgame, screenWidth/2, screenHeight/2, screenWidth/2, screenHeight/2, null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // imprime o objeto na tela

        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.
        if (emJogo == true){
                //Desenhando o cenário
            background.draw(g2);
            //Criando o inimigo
        
            if(enemy.size() > 0) {
                for(int i = 0; i < quantidade; i++) {
                    enemy.get(i).draw(g2);
                    List<Tiro> aux = enemy.get(i).getTirosEnemy();
    
                    for(int j = 0; j < aux.size(); j++){
                        aux.get(j).draw(g2);
                    }
                }
            }

            //Criando o personagem
            for (int i = 0; i < tiros.size(); i++){
                tiros.get(i).draw(g2);
            }
            player.draw(g2);
        }
        else{ //OLHAR PQ NAO ESTA MOSTRANDO O PNG GAMEOVER DEPOIS DA COLISAO
            draw(g2);
        }
        
        g2.dispose();
    }

    public int getTileSize (){
        return tileSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
