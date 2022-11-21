package board;

import javax.swing.JPanel;
import javax.swing.Timer;

import snake2d.Snake;
import snake2d.SnakeFood;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class GameBoardPanel extends JPanel implements ActionListener 
{
    // Membuat new instance dari GameBoard
    private Snake snake;
    private SnakeFood snakeFood;
    private InputManager inputManager;
    private Timer gameThread;
    private Timer timerThread;
    private boolean gameOver;
    private int timer;
    private int playerScore;

    public GameBoardPanel (int level) {
        this.setBackground(Color.GREEN);
        this.setFocusable(true);
        this.gameOver = false;
        this.timer = 0;
        this.playerScore = 0;
        this.snake = new Snake();
        this.snakeFood = new SnakeFood();
        this.inputManager = new InputManager(this);
        this.gameThread = new Timer(getDelay(level), this);
        this.timerThread = new Timer(1000,  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGameOver()) {
                    stopGame();
                }
                timer++;
            }
        });
        this.addKeyListener(inputManager);
        }
        private int getDelay(int level) {
            int delay = 0;
            if (level == 1) {
                delay = 140;
            }else if (level == 2) {
                delay = 70;
            }else if (level == 3) {
                delay = 40;
            }
            return delay;
        }
        
        @Override
        public void paintComponent (Graphics g) {
            super.paintComponent(g);
            this.doDrawing(g);
        }

        public void doDrawing(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            if (isGameRunning()) {
                this.snake.move();
                this.checkCollision();
                this.drawSnakeFood(g2);
            }

            this.drawStatusbar (g2);
            this.drawBoundary(g2);
            this.drawSnake(g2);
        }
        
        public void drawBoundary(Graphics2D g2) {
            for (int i = 0; i < 17; i++) {
                Rectangle2D.Double rect = new Rectangle2D.Double (227 - i, 127 - i, 624, 480);
                g2.setColor(Color.black);
                g2.draw(rect);
            }
        }

        public void drawSnake (Graphics2D g2) {
            for (int i = 0; i < snake.getLength(); i++) {
                if (i == 0) {
                    g2.setColor(Color.magenta);
                    g2.fill(snake.getBody().get(i)); //diwarnai
                }else {
                    g2.setColor(Color.black);
                    g2.draw(snake.getBody().get(i));
                }
            }
        }

        public void drawSnakeFood (Graphics2D g2) {
            g2.setColor(new Color (138, 43, 226)); //biru violet
            g2.fill(snakeFood.getFood());
        }

        public void drawStatusbar (Graphics2D g2) {
            g2.setColor(Color.black);
            g2.setFont(new Font ("Courier New", Font.BOLD,35));
            g2.drawString ("Snake Game", 390, 50);

            g2.setFont(new Font ("Courier New", Font.BOLD,15));
            g2.setColor(Color.black);
            g2.drawString("Press ESC for EXIT!",  5, 20);
            g2.drawString("Press Spacebar for PAUSE !",  5, 50);

            g2.setFont(new Font("Courier New", Font.BOLD, 20));
            g2.setColor(Color.black);
            g2.drawString("Time: ", 210, 100);
            g2.drawString("Your Score: ", 680, 100);
            g2.drawString("" +this.playerScore, 810, 100);
            g2.drawString("" +this.timer, 270, 100);

            if (isGameOver()) {
                g2.setColor(Color.red);
                g2.setFont(new Font ("Arial Black", Font.BOLD, 30));
                g2.drawString("GAME OVER GUYS !!", 440, 350);
            }else if (!isGameRunning()) {
                g2.setColor(Color.black);
                g2.drawString("Press Spacebar to Start the Game!", 400, 500);
            }
        }
        
        public void changeSnakeDirection(int direction) {
            this.snake.setDirection(direction);
        }

        public void checkCollision() {
            if (isSelfCollisioned() || isBoundaryCollisioned()) {
                this.gameOver = true;
                this.stopGame();
            }
            
            if (isFoodCollisioned()) {
                this.snake.eat();
                this.snakeFood = new SnakeFood();
                this.playerScore += 5;
            }
    }

    public boolean isBoundaryCollisioned() {
        if (this.snake.getDirection() == 1) { //upside
            double centerY = this.snake.getBody().get(0).getMinX();
            return centerY < 127;     
        } else if (this.snake.getDirection() == 2) { //downside
            double centerY = this.snake.getBody().get(0).getMaxY();
            return centerY > 608;
        } else if (this.snake.getDirection() == 3) { //right side
            double centerX = this.snake.getBody().get(0).getMaxX();
            return centerX > 836;
        } else if (this.snake.getDirection() == 4) { //left side
            double centerX = this.snake.getBody().get(0).getMinX();
            return centerX < 227;
        }
        return false;
    }

    public boolean isSelfCollisioned() {
        if (this.snake.getDirection() == 1) {
            for (int i = 1; i < this.snake.getLength(); i++) {
                if ((this.snake.getBody().get(0).getMinY() == this.snake.getBody().get(i).getMaxY() 
                && (this.snake.getBody().get(0).getCenterX() == this.snake.getBody().get(i).getCenterX()))) {
                    return true;
                }
            }
        }else if (this.snake.getDirection() == 2) {
            for (int i = 1; i < this.snake.getLength(); i++) {
                if ((this.snake.getBody().get(0).getMaxY() == this.snake.getBody().get(i).getMinY() 
                && (this.snake.getBody().get(0).getCenterX() == this.snake.getBody().get(i).getCenterX()))) {
                    return true;
                }
            }
        }else if (this.snake.getDirection() == 3) {
            for (int i = 1; i < this.snake.getLength(); i++) {
                if ((this.snake.getBody().get(0).getMaxX() == this.snake.getBody().get(i).getMinX() 
                && (this.snake.getBody().get(0).getCenterY() == this.snake.getBody().get(i).getCenterY()))) {
                    return true;
                }
            }
        }else if (this.snake.getDirection() == 4) {
            for (int i = 1; i < this.snake.getLength(); i++) {
                if ((this.snake.getBody().get(0).getMinX() == this.snake.getBody().get(i).getMaxX() 
                && (this.snake.getBody().get(0).getCenterY() == this.snake.getBody().get(i).getCenterY()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFoodCollisioned() {
        boolean collisionedWithFood = false;
        int direction = this.snake.getDirection();
        Ellipse2D.Double head = this.snake.getHead();

        if (direction == 1) {
            if ((head.getCenterY() == this.snakeFood.getFood().getCenterY()
            && (head.getCenterX() == this.snakeFood.getFood().getCenterX()))) {
                collisionedWithFood = true;
            }else {
                collisionedWithFood = false;
            }
        }else if (direction == 2) {
            if ((head.getCenterY() == this.snakeFood.getFood().getCenterY()
            && (head.getCenterX() == this.snakeFood.getFood().getCenterX()))) {
                collisionedWithFood = true;
            }else {
                collisionedWithFood = false;
            }
        }else if (direction == 3) {
            if ((head.getCenterX() == this.snakeFood.getFood().getCenterX()
            && (head.getCenterY() == this.snakeFood.getFood().getCenterY()))) {
                collisionedWithFood = true;
            }else {
                collisionedWithFood = false;
            }
        }else if (direction == 4) {
            if ((head.getCenterX() == this.snakeFood.getFood().getCenterX()
            && (head.getCenterY() == this.snakeFood.getFood().getCenterY()))) {
                collisionedWithFood = true;
            }else {
                collisionedWithFood = false;
            }
        }
        return collisionedWithFood;
    }

    public void startGame() {
        if (this.gameThread.isRunning()) {
            this.gameThread.restart();
            this.timerThread.restart();
        }else {
            this.gameThread.start();
            this.timerThread.start();
        }
    }

    public void pauseGame() {
        this.gameThread.stop();
        this.timerThread.stop();
        this.repaint();
    }

    public void stopGame() {
        this.gameThread.stop();
        this.timerThread.stop();
    }

    public boolean isGameRunning () {
        return gameThread.isRunning() && !isGameOver();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void actionPerformed (ActionEvent arg0) {
        this.repaint();
    }
}


