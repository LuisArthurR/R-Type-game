package game;

import java.awt.Dimension;
import javax.swing.JPanel;

import entity.Player;
import scenario.Background;
import entity.Enemy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList; /////////
import java.util.List;      /////////

public class GamePanel extends JPanel implements Runnable{
    
    // Configurações da tela
    final int originalTileSize = 42; // 42x42 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // Aumentar a tamanho dos sprites (96x96)
    final int maxScreenCol = 12;
    final int maxScreenRow = 7;
    final int screenWidth = tileSize * maxScreenCol;  // 1512px
    final int screenHeight = tileSize * maxScreenRow; // 882px

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    KeyHandle keyH = new KeyHandle();
    Thread gameThread; // Quando essa thread iniciar, vai continuar o programa rodando até você fechar.

    // FPS
    int FPS = 60;

    //Posição inicial do player e velocidade
    private Player player = new Player(this, keyH, 100, 100, 5);
    int playerX = player.getX();
    int playerY = player.getY();

    //Criação da fase e seu cenário
    private Background background = new Background(this);

    //Invocando os inimigos
    private List<Enemy> enemy = new ArrayList<Enemy>();
    int quantidade = 4;

    public void spawnEnemies() {

        quantidade = 4;

        for(int i = 0; i < quantidade; i++) {
            int posX = (int)(Math.random() * 2000 + 1512);
            int posY = (int)(Math.random() * 370 + 40);

            enemy.add(new Enemy(this, posX, posY, 5));
        }
    }

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);     // Para componentes que são repintados com muita frequência ou têm gráficos particularmente complexos para exibir.
        this.addKeyListener(keyH);
        this.setFocusable(true);
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

                //Draw: desenhar na tela as atualizações informações
                repaint(); // Chamando a paintComponent function

                delta--;
            }
        }
    }

    public void update() {
        
        player.update();

        //System.out.println(enemy.size());

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
                if(enemy.get(i).getX() < -100) {
                    enemy.remove(i);
                    quantidade--;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // imprime o objeto na tela

        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.

        //Desenhando o cenário
        background.draw(g2);

        //Criando o inimigo
        if(enemy.size() > 0) {
            for(int i = 0; i < quantidade; i++) {
                enemy.get(i).draw(g2);
            }
        }

        //Criando o personagem
        player.draw(g2);

        g2.dispose();
    }
}
