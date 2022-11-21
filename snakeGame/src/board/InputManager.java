package board;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener{
    private GameBoardPanel gameBoard;
    
    public InputManager (GameBoardPanel gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void keyTyped (KeyEvent e) {

    }
    @Override
    public void keyReleased (KeyEvent e) {

    }
    @Override 
    public void keyPressed (KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) { //UP ARROW KEY
            gameBoard.changeSnakeDirection(1);
        }else if (key == KeyEvent.VK_DOWN) {
            gameBoard.changeSnakeDirection(2);
        }else if (key == KeyEvent.VK_RIGHT) {
            gameBoard.changeSnakeDirection(3);
        }else if (key == KeyEvent.VK_LEFT) {
            gameBoard.changeSnakeDirection(4);
        }else if (key == KeyEvent.VK_SPACE) {
            if (gameBoard.isGameRunning()) {
                gameBoard.pauseGame();
            }else {
                gameBoard.startGame();
            }
        }else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
}
