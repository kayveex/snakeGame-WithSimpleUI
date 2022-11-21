package snake2d;

import java.awt.geom.Ellipse2D;
import java.util.Random;

public class SnakeFood {
    private Ellipse2D.Double shape;

    public SnakeFood () {
        generateFood();
    }

    public void generateFood() {
        Random random = new Random();
        int x, y;
        do {
            x = (int) (random.nextInt(39));
            y = (int) (random.nextInt(30));
        } while (x == 0 || y == 0 || x == 38 || y == 29 );
        x = x * 16 + 227;
        y = y * 16 + 127;

        shape = new Ellipse2D.Double(x,y,16,16);
    }

    public Ellipse2D.Double getFood () {
        return shape;
    }
}
