import java.awt.Rectangle;

import javax.swing.JLabel;

public class frogger extends spriteObjects {
	
	public frogger() {
		super();
	}

	public frogger(int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
	}

	public void setFroggerLabel(JLabel frogger) {
		
	}

	public Rectangle getRectangle() {
		return hBox;
	}
}
