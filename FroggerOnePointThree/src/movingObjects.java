import javax.swing.JLabel;

public class movingObjects extends spriteObjects implements Runnable{
	private Boolean moving;
	private Thread t;
	private JLabel carLabel;
	private JLabel logLabel;
	private JLabel froggerLabel;
	private frogger frogger;
	private log log;
	private car car;
	
	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean moving) {
		this.moving = moving;
	}

	public movingObjects() {
		super();
	}
	
	public movingObjects(Boolean moving) {
		super();
		this.moving = moving;
	}
	
	public movingObjects(Boolean moving, int x, int y, int height, int width, String image) {
		super(x, y, height, width, image);
		this.moving = moving;
	}

	@Override
	public void run() {

		while (this.moving) {
			// moving code goes here
			int x = this.x;

			// increase x
			x += gameProperties.FROGGER_STEP;

			// boundary check (loop around to other side of screen)
			if (x >= gameProperties.SCREEN_WIDTH) {
				x = -1 * this.width;
			}

			this.x = x;
			
			this.setX(x); // this is required to update rectangle movement
			
			carLabel.setLocation(this.x, this.y);
			logLabel.setLocation(this.x, this.y);
			
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace(); // shows problem in console
			}
		} 
	}
	
	
	//start thread 
	public void startThread() {
		if( !this.moving) {
			this.moving = true;	
		}
	}
	
	//stop thread
	public void stopThread() {
		this.moving = false;
	}

	// method to pass the original JLabel for car
	// into the class so we can effect the original label

	public void setcarLabel(JLabel temp) {
		carLabel = temp;
	}
	
	public void setCar(car temp) {
		car = temp;
	}

	public void setFroggerLabel(JLabel temp) {
		froggerLabel = temp;
	}
	
	public void setLogLabel(JLabel temp) {
		logLabel = temp;
	}

	public void setFrogger(frogger temp) {
		frogger = temp;
	}

	public void setLog(log temp) {
		log = temp;
	}

}
