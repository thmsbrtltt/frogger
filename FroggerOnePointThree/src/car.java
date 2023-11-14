import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class car extends spriteObjects implements Runnable {
    private JLabel carLabel;
    private int direction;
    private double speedAdjust;
    private frogger frog;
	private boolean moving;

    public car(int x, int y, int width, int height, String image, int direction, double speedAdjust, frogger frog) {
        super(x, y, width, height, image);
        this.direction = direction;
        this.speedAdjust = speedAdjust;
        this.frog = frog;

        carLabel = new JLabel();
        ImageIcon carImageIcon = new ImageIcon(getClass().getResource("sprites/" + image));
        carLabel.setIcon(carImageIcon);
        carLabel.setSize(width, height);
        carLabel.setLocation(x, y);
        hBox = new Rectangle(x, y, width, height);
    }

    public JLabel getCarLabel() {
        return carLabel;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getDirection() {
        return direction;
    }

    public void startThread() {
        Thread t = new Thread(this);
        t.start();
    }
    
	public void stopThread() {
		this.moving = false;
	}


    @Override
    public void run() {
        while (moving) {
            move();
            detectCollision();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void move() {
        if (direction == 0) {
            x -= (int) (gameProperties.CAR_STEP * speedAdjust);
        } else {
            x += gameProperties.CAR_STEP;
        }

        if (x + width < 0) {
            x = gameProperties.SCREEN_WIDTH;
        } else if (x >= gameProperties.SCREEN_WIDTH) {
            x = -width;
        }
        
        carLabel.setLocation(x, y);
        hBox.setLocation(x, y);
    }

    private void detectCollision() {
        if (hBox.intersects(frog.getRectangle())) {
            System.out.println("Collision!");
            this.stopThread();
        }
    }
    
    public void printHBoxBounds() {
        System.out.println("Car hBox - X: " + hBox.getX() + ", Y: " + hBox.getY() +
                ", Width: " + hBox.getWidth() + ", Height: " + hBox.getHeight());
	}
}
