

import java.awt.Rectangle;

import javax.swing.JLabel;

public class frogger extends spriteObjects {
	
	public frogger() {
		super();
	}

	public frogger(int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
		hBox = new Rectangle(x, y, width, height);
	}


	 public void setX(int x) {
	        this.x = x;
	        hBox.setLocation(x, y);
	    }

	    public void setY(int y) {
	        this.y = y;
	        hBox.setLocation(this.x, y);
	    }
	   
	public void setFroggerLabel(JLabel frogger) {
		
	}
	
	public void printHBoxBounds() {
        System.out.println("Frogger hBox - X: " + hBox.getX() + ", Y: " + hBox.getY() +
                ", Width: " + hBox.getWidth() + ", Height: " + hBox.getHeight());
	}
}
