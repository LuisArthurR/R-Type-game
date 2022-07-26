package game;

import javax.swing.JFrame;

public class Main{
    
    public static void main(String[] args) {

        // Configurações da tela
        int originalTileSize = 42; // 42x42 tile
        int scale = 3;
        int tileSize = originalTileSize * scale; // Aumentar a tamanho dos sprites (96x96)
        int maxScreenCol = 12;
        int maxScreenRow = 7;
        int screenWidth = tileSize * maxScreenCol;  // 1512px
        int screenHeight = tileSize * maxScreenRow; // 882px
        int buttonWidth = 336;
        int buttonHeight = 84;


        JFrame window= new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("R-Type");
        Menu menu = new Menu(originalTileSize, screenWidth, screenHeight, buttonWidth, buttonHeight);
        window.add(menu);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        menu.startGameThread();

        while(menu.isShowing()){
        }
        window.dispose();

        JFrame window2 = new JFrame();
        window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window2.setResizable(false);
        window2.setTitle("R-Type");
        GamePanel gamePanel = new GamePanel(tileSize, screenWidth, screenHeight);
        window2.add(gamePanel);
        
        window2.pack();
        window2.setLocationRelativeTo(null);
        window2.setVisible(true);
        gamePanel.startGameThread();
        
    }
}
