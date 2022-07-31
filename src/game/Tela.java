package game;

import javax.swing.JFrame;

public class Tela extends JFrame{


    public Tela(Menu menu){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("R-Type");
        add(menu);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);  
        menu.startGameThread();
    }
    public Tela(GamePanel gp){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("R-Type");
        add(gp);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);  
        gp.startGameThread();
    }

    public static void main(String[] args) {
        
        int originalTileSize =  36; // 42x42 tile
        int scale = 3;
        int tileSize = originalTileSize * scale; // Aumentar a tamanho dos sprites (96x96)
        int maxScreenCol = 12;
        int maxScreenRow = 7;
        int screenWidth = tileSize * maxScreenCol;  // 1512px
        int screenHeight = tileSize * maxScreenRow; // 882px
        int buttonWidth = originalTileSize * 8;
        int buttonHeight = originalTileSize * 2;

        new Menu(tileSize, screenWidth, screenHeight, buttonWidth, buttonHeight, originalTileSize);
    }
}
