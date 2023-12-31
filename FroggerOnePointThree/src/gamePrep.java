import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class gamePrep extends JFrame implements KeyListener, ActionListener{

	private frogger frogger;
	private car[][] cars;
	private log[][] logs;    

	//gui variables
	private Container content;
	private JLabel froggerLabel;
	private ImageIcon froggerImage;
	private JLabel backgroundLabel;
	private ImageIcon backgroundImage;
	private JLabel instructLabel; //temp title and instructions label
	
	//buttons
	private JButton startButton;
	private JButton restartButton;
	
	//boolean for game state 
	private boolean gameStarted = false;
	private volatile boolean collisionOccurred = false;

	
	//2d array initialization
    private void carLogInit() {
    	
    	for(int i=0; i<cars.length; i++) {
    		for(int j=0; j<cars[i].length; j++) {
    			//last parameter changes direction of odd numbered car rows using ternary operator 
    			cars[i][j] = new car(40 + j * 150, 295 + i * 60, 60, 60, "carLeft.png", (i % 2 == 0) ? 0 : 1, 0.5, frogger); 
    			add(cars[i][j].getCarLabel());
    		}
    	}
    	
    	for (int i = 0; i < logs.length; i++) {
            for (int j = 0; j < logs[i].length; j++) {
                logs[i][j] = new log(40 + j * 150, 90 + i * 60, 80, 80, "log.png", (i % 2 == 0) ? 0 : 1, 0.5, frogger);
                add(logs[i][j].getLogLabel());
            }
        }
    }
    
    //win condition
    private void winCondition() {
       
    	gameStarted = false;
    	
        System.out.println("you made it! press Restart to play again");
    }
    
    private void resetObjects() {
        
    	//set frogger to start position
    	updateFroggerPosition(275, 475);
    	
    	//stop cars and logs
        stopCarsAndLogs();
        
        //reset cars and logs
        resetCarsAndLogs();  
    }
    
    private void stopCarsAndLogs() {
        //stop cars
        for (car[] carRow : cars) {
        	for (car car : carRow) {
        		car.setMoving(false);
            }
        }

        //stop logs
        for (log[] logRow : logs) {
            for (log log : logRow) {
                log.setMoving(false);
            }
        }
    }
    
    //reset car and log positions
    private void resetCarsAndLogs() {
    	
    	for (int i = 0; i < cars.length; i++) {
    		for (int j = 0; j < cars[i].length; j++) {
    			int initialX = 40 + j * 150;
    			int initialY = 255 + i * 60;
    			cars[i][j].getCarLabel().setLocation(initialX, initialY);
    		}
    	}

    	for (int i = 0; i < logs.length; i++) {
    		for (int j = 0; j < logs[i].length; j++) {
    			int initialX = 40 + j * 150;
    			int initialY = 60 + i * 60;
    			logs[i][j].getLogLabel().setLocation(initialX, initialY);
    		}
    	}
    }
    
    //start thread for cars and logs
    private void startCarsAndLogs() {
        
    	for (car[] carRow : cars) {
    	    for (car car : carRow) {
    	        car.setMoving(true);
    	        Thread carThread = new Thread(car);
    	        carThread.start();
    	    }
    	}

        for (log[] logRow : logs) {
            for (log log : logRow) {
                log.setMoving(true);
                log.startThread();
                
            }
        }
       
    }
    
    //update frogger position
    private void updateFroggerPosition(int x, int y) {
	    frogger.setX(x);
	    frogger.setY(y);
	    froggerLabel.setLocation(frogger.getX(), frogger.getY());
	}
  
    private void printCarsHBoxBounds() {
        for (car[] carRow : cars) {
            for (car car : carRow) {
                car.printHBoxBounds();
            }
        }
    }

//    private void printLogsHBoxBounds() {
//        for (log[] logRow : logs) {
//            for (log log : logRow) {
//                log.printHBoxBounds();
//            }
//        }
//    }
	public gamePrep() {
		 
		//gui code setup
		frogger = new frogger(100, 200, 56, 56, "frogger.png");
		cars = new car[3][4]; 
		logs= new log[3][4];
		
		
		 //load background image
        backgroundImage = new ImageIcon(getClass().getResource("sprites/background.png"));

        //create JLabel to hold the background image
        backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundImage);
        backgroundLabel.setBounds(0, 0, gameProperties.SCREEN_WIDTH, gameProperties.SCREEN_HEIGHT);
        setSize(gameProperties.SCREEN_WIDTH, gameProperties.SCREEN_HEIGHT);
        content = getContentPane();
        
        instructLabel = new JLabel("FROGGER - Click 'Start' To Play!");
        instructLabel.setBounds(200, 0, 300, 50); 
        content.add(instructLabel);
        
		//frogger setup
		frogger.setX(275);
		frogger.setY(475);
		frogger.setWidth(50);
		frogger.setHeight(50);
		frogger.setImage("frogger.png");
		
		froggerLabel = new JLabel();
		froggerImage = new ImageIcon(getClass().getResource("sprites/" + frogger.getImage()));
				
		froggerLabel.setIcon(froggerImage);
		froggerLabel.setSize(frogger.getWidth(),frogger.getHeight());
		froggerLabel.setLocation(frogger.getX(),frogger.getY());
		frogger.setFroggerLabel(froggerLabel);
		add(froggerLabel); 
		
		//add car and log arrays
		carLogInit();
		
		frogger.printHBoxBounds();
		printCarsHBoxBounds();
		
		//start button
		startButton = new JButton("Start");
		startButton .setSize(80, 50);
		startButton .setLocation(gameProperties.SCREEN_WIDTH - 190, gameProperties.SCREEN_HEIGHT - 85);
		startButton .setFocusable(false);
					
		//restart button
		restartButton = new JButton("Restart");
		restartButton .setSize(80, 50);
		restartButton .setLocation(gameProperties.SCREEN_WIDTH - 100, gameProperties.SCREEN_HEIGHT - 85);
		restartButton .setFocusable(false);
		
		//add labels to screen
		startButton.addActionListener(this);
		add(startButton);		
		restartButton.addActionListener(this);
		add(restartButton);
        add(backgroundLabel);
		
        content.addKeyListener(this); //adds key listener to screen window
		content.setFocusable(true); //wants screen to have focus, allows keys to work in window
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //frequently forgotten - without this the program stays running - ensures program closes
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		gamePrep myGame = new gamePrep(); //calling constructor
		myGame.setVisible(true);
		});
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("key pressed");
		
		if (!gameStarted) {
	        System.out.println("Press the Start button to begin the game.");
	        return;
	    }

	    // get current position
	    int x = frogger.getX();
	    int y = frogger.getY();

	    // detect direction and modify coordinates
	    if (e.getKeyCode() == KeyEvent.VK_UP) { // detecting up arrow press
	        y -= gameProperties.FROGGER_STEP;
	        // creates scrolling effect when leaving bottom screen boundary
	        if (y + frogger.getHeight() <= 0) {
	            y = gameProperties.SCREEN_HEIGHT;
	        }

	    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) { // detecting down arrow press
	        y += gameProperties.FROGGER_STEP;
	        // creates scrolling effect when leaving top screen boundary
	        if (y >= gameProperties.SCREEN_HEIGHT) {
	            y = -1 * frogger.getHeight();
	        }

	    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) { // detecting left arrow press
	        x -= gameProperties.FROGGER_STEP;

	        if (x + frogger.getWidth() < 0) {
	            x = gameProperties.SCREEN_WIDTH;
	        }

	    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // detecting right arrow press
	        x += gameProperties.FROGGER_STEP;

	        if (x >= gameProperties.SCREEN_WIDTH) {
	            x = -1 * frogger.getWidth();
	        }

	    } else {
	        System.out.println("Invalid Operation");
	        return;
	    }

	    // creates boundary on the right edge of the screen
	    // if( x > gameProperties.SCREEN_WIDTH - frogger.getWidth()) {
	    // x = gameProperties.SCREEN_WIDTH - frogger.getWidth();
	    //}

	    updateFroggerPosition(x, y);
	    froggerLabel.setLocation(frogger.getX(), frogger.getY());
	    
	    System.out.println("Frogger position - X: " + frogger.getX() + ", Y: " + frogger.getY());

	    // check for collision only when the frogger moves
	    if (checkCollision()) {
	        handleCollision();
	    }

	    // win condition
	    if (y <= 0) {
	        winCondition();
	        stopCarsAndLogs();
	    }
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        
		//start button
		if (e.getSource() == startButton) {    
        	
			instructLabel.setVisible(false);
        	gameStarted = true;
        	startCarsAndLogs();
        	startButton.setEnabled(false); 
        }	
		
		//restart button
		if (e.getSource()== restartButton) {
			 
			resetObjects();
			startCarsAndLogs();
			gameStarted = true;
		}
	}
	
	// Check for collision
    private boolean checkCollision() {
        for (car[] carRow : cars) {
            for (car car : carRow) {
                if (car.hBox.intersects(frogger.getRectangle())) {
                    return true;
                }
            }
        }

        return false;
    }

    // Handle collision
    private void handleCollision() {
        System.out.println("Collision!");
        froggerLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        for (int i = 0; i < cars.length; i++) {
            for (int j = 0; j < cars[i].length; j++) {
                if (cars[i][j].hBox.intersects(frogger.getRectangle())) {
                 
                    System.out.println("Frogger collided with car at Row: " + i + ", Column: " + j);
                    cars[i][j].getCarLabel().setOpaque(true);
                    cars[i][j].getCarLabel().setBackground(Color.RED);
                    break;
                }
            }
        }
        
        for (car[] carRow : cars) {
            for (car car : carRow) {
                car.getCarLabel().setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
        }
        collisionOccurred = true;
        gameStarted = false;
        stopCarsAndLogs();
    }
}
