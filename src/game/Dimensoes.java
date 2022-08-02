package game;

public class Dimensoes{

    private int originalTileSize =  36; // tile
    private int scale = 3;
    private int tileSize = originalTileSize * scale; // Aumentar o tamanho dos sprites 
    private int maxScreenCol = 12;
    private int maxScreenRow = 7;
    private int screenWidth = tileSize * maxScreenCol;  
    private int screenHeight = tileSize * maxScreenRow; 
    private int buttonWidth = originalTileSize * 8;
    private int buttonHeight = originalTileSize * 2;

    public int getOriginalTileSize(){
        return this.originalTileSize;
    }

    public int getTileSize(){
        return this.tileSize;
    }

    public int getScreenWidth(){
        return this.screenWidth;
    }

    public int getScreenHeight(){
        return this.screenHeight;
    }

    public int getButtoWidth(){
        return buttonWidth;
    }

    public int getButtoHeight(){
        return buttonHeight;
    }
}