package game;

import java.awt.Dimension;
import javax.swing.JPanel;

import entity.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GamePanel extends JPanel implements Runnable{
    
    // Configurações da tela
    final int originalTileSize = 42; // 42x42 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // Aumentar a tamanho dos sprites (96x96)
    final int maxScreenCol = 12;
    final int maxScreenRow = 7;
    final int screenWidth = tileSize * maxScreenCol;  // 1512px
    final int screenHeight = tileSize * maxScreenRow; // 882px

    KeyHandle keyH = new KeyHandle();
    Thread gameThread; // Quando essa thread iniciar, vai continuar o programa rodando até você fechar.

    // FPS
    int FPS = 60;

    //Posição inicial do player e velocidade
    private Player player = new Player(this, keyH);
    int playerX = player.getX();
    int playerY = player.getY();

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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // imprime o objeto na tela

        Graphics2D g2 = (Graphics2D) g; //mais controle sobre a geometria, coordenada e cor.

        //Criando o persoangem
        player.draw(g2);
        g2.dispose();
    }
}
