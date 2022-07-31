package game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandle implements KeyListener{

    private boolean upPressed, downPressed, leftPressed, rightPressed, jPressed, enterPressed, escPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Pega o endereço da tecla pressionada

        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            this.upPressed = true;
            this.downPressed = false;
        }
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            this.downPressed = true;
            this.upPressed = false;
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            this.leftPressed = true;
            this.rightPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            this.rightPressed = true;
            this.leftPressed = false;
        }
        if(code == KeyEvent.VK_J) {
            this.jPressed = true;
        }
        if(code == KeyEvent.VK_ENTER){
            this.enterPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            this.escPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode(); // Pega o endereço da tecla pressionada

        if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            this.upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            this.downPressed = false;
        }
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            this.leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            this.rightPressed= false;
        }
        if(code == KeyEvent.VK_J) {
            this.jPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            this.enterPressed = false;
        }
        if(code == KeyEvent.VK_ENTER){
            this.escPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }    

    public boolean getUpPressed() {
        return upPressed;
    }

    public boolean getDownPressed() {
        return downPressed;
    }

    public boolean getLeftPressed() {
        return leftPressed;
    }

    public boolean getRightPressed() {
        return rightPressed;
    }
    
    public boolean getJPressed() {
        return jPressed;
    }

    public boolean getEnterPressed(){
        return enterPressed;
    }

    public boolean getEscPressed(){
        return escPressed;
    }

    
}
