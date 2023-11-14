import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class log extends spriteObjects implements Runnable {
    private JLabel logLabel;
    private int direction;
    private double speedAdjust;
	private boolean moving;
	private frogger frog;

    public log(int x, int y, int width, int height, String image, int direction, double speedAdjust, frogger frog) {
        super(x, y, width, height, image);
        this.direction = direction;
        this.speedAdjust = speedAdjust;
        this.frog = frog;
        boolean moving = false;

        logLabel = new JLabel();
        ImageIcon logImageIcon = new ImageIcon(getClass().getResource("sprites/" + image));
        logLabel.setIcon(logImageIcon);
        logLabel.setSize(width, height);
        logLabel.setLocation(x, y);
    }

    public JLabel getLogLabel() {
        return logLabel;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void startThread() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (moving) {
            move();
            detectCollision();
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void move() {
        if (direction == 0) {
            x -= (int) (gameProperties.LOG_STEP * speedAdjust);
        } else {
            x += gameProperties.LOG_STEP;
        }

        if (x + width < 0) {
            x = gameProperties.SCREEN_WIDTH;
        } else if (x >= gameProperties.SCREEN_WIDTH) {
            x = -width;
        }

        logLabel.setLocation(x, y);
    }
    
    private void detectCollision() {
        if (hBox.intersects(frog.getRectangle())) {
            System.out.println("Collision!");
            moving = false;
        }
    }
}


