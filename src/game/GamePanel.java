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
    
    private Dimensoes dm;
    private int FPS;
    private KeyHandle keyH = new KeyHandle();
    private Thread gameThread; // Quando essa thread iniciar, vai continuar o programa rodando até você fechar.
    private Player player;
    private Background background;
    private List<Enemy> enemy;
    private List<Tiro> tirosP;
    private List<List<Tiro>> tirosE;
    private int quantidade;
    private long spawnRate;
    private long nextSpawn;
    private boolean emJogo;
    private BufferedImage endgame;
    //private Tela tela;

    public GamePanel(Dimensoes dm) {

        this.dm = dm;
        this.background  = new Background(dm.getScreenWidth(),dm.getScreenHeight(), "ScenarioMountains.png", "ScenarioMountains2.png", "ScenarioSky.png", "ScenarioTrees.png");
        this.enemy = new ArrayList<>();
        this.spawnRate = 1500000000;
        this.nextSpawn = 0;
        this.quantidade = 0;
        this.FPS = 60;
        this.player = new Player(this, keyH, 100, 100, 5,dm);
        this.tirosE = new ArrayList<>();
        this.tirosP = player.getTirosPlayer();
        this.setPreferredSize(new Dimension(dm.getScreenWidth(), dm.getScreenHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.addKeyListener(keyH);
        this.setFocusable(true);

        try{
            endgame = ImageIO.read(this.getClass().getResourceAsStream("/res/scenarioImages/GameOver2.png"));  
        }catch(IOException e){
                e.printStackTrace(); //imprime o throwable junto com outros detalhes como o número da linha e o nome da classe onde ocorreu a exceção.
        }
        emJogo = true;

        new Tela(this);
        
    }

    public void spawnEnemies() {

        int posX = (int)(Math.random() * 2000 + 1512);
        int posY = (int)(Math.random() * 370 + 40);

        enemy.add(new Enemy2(posX, posY, 4,dm));
        tirosE.add(enemy.get(quantidade).getTirosEnemy());

        enemy.add(new Enemy1(posX, posY, 5, player,dm));
        tirosE.add(enemy.get(quantidade+1).getTirosEnemy());
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
                
                //Draw: desenhar na tela as atualizações informações
                repaint(); // Chamando a paintComponent function

                delta--;
            }
        }
    }
    public void verificaTiros(Iterator<Tiro> iter, String direction){
        while(iter.hasNext()){
            Tiro aux = iter.next();
            if (aux.getIsVisivel()){
                aux.update(direction);
            }
            else
                iter.remove();
        }
    }
    public void update() {
        
        if (emJogo == false){
            if (keyH.getEnterPressed() == true){
                System.exit(0);
            }
        }

        player.update();
       
        colisao();
        //verificaTiros(tirosSumidos.iterator(), "left");
        Iterator<List<Tiro>> iter = tirosE.iterator();
        while(iter.hasNext()){
            verificaTiros(iter.next().iterator(), "left");
        }
        verificaTiros(tirosP.iterator(), "right");

        if(enemy.size() < 1) {
            spawnEnemies();
        } 

        if(enemy.size() > 0) {
            for(int i = 0; i < quantidade; i++) {
                enemy.get(i).update();
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
                   // player.DiminuirVida(player.getLife());
                    tempEnemy.setIsVisivel(false);
                    if (player.getLife() == 0)
                        emJogo = false;
                }
        }

        for (int i = 0; i < tirosP.size();i++){
            formaTiro = tirosP.get(i).getBounds();

            for (int j = 0; j < enemy.size();j++){

                formaEnemy = enemy.get(j).getBounds();
                if (formaTiro.intersects(formaEnemy)){
                    tirosP.get(i).setIsVisivel(false);
                    enemy.get(j).setIsVisivel(false);
                }
            }
        }

    }

    public void draw(Graphics2D g2){
        g2.drawImage(this.endgame, dm.getScreenWidth()/2-dm.getTileSize(), dm.getScreenHeight()/2-dm.getTileSize(), 2*dm.getTileSize(), 2*dm.getTileSize(), null); // "null" é para o imageObserver (notifica o aplicativo sobre atualizações em uma imagem carregada)
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
                }
            }

            for (int i = 0; i < tirosP.size(); i++){
                tirosP.get(i).draw(g2, "right");
            }
            for (int i = 0; i < tirosE.size(); i++){
                List<Tiro> aux = tirosE.get(i);
                for (int j = 0; j< aux.size(); j++){
                    aux.get(j).draw(g2, "left");
                }
            }
            
            player.draw(g2);
        }
        else{
            background.draw(g2);
            draw(g2);
        }
        
        g2.dispose();
    }
}
