import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.scene.input.KeyCode;

 


public class GameDisplay extends JPanel implements KeyListener {
	private int width = 300; 
	private int height = 600;  
	private Color backgroundColor = new Color(11,41,91);  
	private int nextBlock = -1; 
	private Block currBlock = null; 
	private Table table;  
	private boolean running;  
	private double FPS;  
	private double inputFPS;
	private long time;  
	private long inputTime; 
	private int score = 0; 
	private int nextLevel = 400;
	private ScoreBoard scoreBoard;  
	private Random ran;   
	private NextBoard nextBoard; 
	private int currHoldInput = -1;  
	private Runnable inputAssist; 
	
	public GameDisplay() {
		this.setPreferredSize(new Dimension(width, height));    
		ran = new Random();
		table = new Table(); 
		currBlock = new Block(table.getTable(), ran.nextInt(7));  
		running = false;  
		FPS = 3.00;   
		inputFPS = 10.00; 
		time = (long) (1/FPS * 1000);   
		inputTime = (long) (1/inputFPS * 1000);  
		addKeyListener(this); 
		inputAssist = new Runnable() {

			@Override
			public void run() { 
				while(true) {  
					long startTime = System.currentTimeMillis();    

					if(currHoldInput == 0) { 
						synchronized(currBlock) {
						currBlock.moveLeft();  
						repaint(); 
						}
					} 
					
					if(currHoldInput == 1) { 
						synchronized(currBlock) {
						currBlock.moveRight();  
						repaint();
						}
					} 
					
					if(currHoldInput == 2) { 
						if(!currBlock.hasLanded()) {
							synchronized(currBlock) {
								currBlock.fall();
								repaint();
							} 
					}
				} 
				
					long elaspedTime = System.currentTimeMillis() - startTime;    
					try { 
						Thread.sleep(inputTime - elaspedTime);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					} 
					
				
			}
			
		
	}  
		} ;  
		
		Thread inputThread = new Thread(inputAssist); 
		inputThread.start();
	}
	
	
	public void runGame() {  
		requestFocus();
		running = true; 
		while(running) {   
			
			
			long startTime = System.currentTimeMillis(); 
		 
			
			if(nextBlock == -1) { 
				nextBlock =  ran.nextInt(7);  
				nextBoard.updateNextBoard(nextBlock);
			}
			
			
			if(!currBlock.hasLanded()) { 
				synchronized(currBlock) {
			currBlock.fall();    
				}  
				
				
			} 
			else { 
				if(checkAndRunGameOver()) {
				
				ArrayList<Integer> FullRows = checkRows(); 
				if(FullRows.size() > 0) {
				flashRows(FullRows);
				score += FullRows.size() * FullRows.size() * 100;   
				scoreBoard.setScore(score); 
				if(score >= nextLevel) {
					FPS += 0.5; 
					nextLevel += 400;  
					time = (long) (1/FPS * 1000); 
				}  
				for(int row: FullRows) {
					collaspeRow(row);
				} 
				}
				
				currBlock = new Block(table.getTable(), nextBlock); 
				nextBlock = -1; 
			} 
			}
			
			
			
			
		
			try { 
				System.out.println("FPS " + FPS);
				Thread.sleep(time );
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} 
			
			this.repaint();
		} 
		 
		JOptionPane.showMessageDialog(this, "GameOver Score: " + score, "GameOver", JOptionPane.PLAIN_MESSAGE);
	}
	
	public ArrayList<Integer> checkRows() { 
		
		ArrayList<Integer> rows = new ArrayList<Integer>(); 
		
		for(int y = 0; y < 20; y++) { 
			boolean full = true; 
			for(int x = 0; x < 10; x++) {
				if(table.getTable()[x][y].getIsEmpty() == true) {
					full = false;
				}
			} 
			if(full) {
				rows.add(y);
			}
		} 
		
		return rows; 
	} 
	
	public void collaspeRow(int row) {
		for( int y = row -1; y >= 0; y--) { 
			for(int x = 0; x< 10; x++) {
				table.getTable()[x][y+1].updateCell(table.getTable()[x][y].getColor(), table.getTable()[x][y].getIsEmpty() );  
				table.getTable()[x][y].updateCell(backgroundColor, true);
			}
		} 
		
		repaint();
	} 
	
	public boolean checkAndRunGameOver() { 
	
		for(int x = 0; x< 10; x++) {
			if(table.getTable()[x][0].getIsEmpty() == false) {
				running = false; 
				return false;
			}
		}  
		
		return true;
	
	} 
	
	public void flashRows(ArrayList<Integer> rows)  { 
		Color white = new Color(255, 255, 255); 
		for(int n = 1; n < 6; n++) { 
		
		for(int x = 0; x < 10; x++) { 
			if(n %2 != 0) { 
			for(int row: rows) {
			table.getTable()[x][row].updateCell(white, true);		   
			}
			} 
			if(n %2 == 0) { 
				for(int row: rows) {
				table.getTable()[x][row].updateCell(backgroundColor, true); 
				}
			}
			}   
		repaint(); 
		
		try {
		Thread.sleep(100);} 
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		}
	} 
	
	public void setScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard  = scoreBoard;
	} 
	
	public void setNextBoard(NextBoard nextBoard) {
		this.nextBoard = nextBoard; 
	}
	@Override 
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;  
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, width, height); 
		 
	 
		 
		
		//draw curr blocks
		for(int x = 0; x < table.getTable().length; x++) {   
			for(int y = 0; y < table.getTable()[0].length; y++) {
				g2.setColor(table.getTable()[x][y].getColor()); 
				g2.fillRect(x * 30, y * 30, 30, 30); 
			
			} 
		}
				
		//make grid 
		g2.setColor(new Color(0,0,0)); 
		for(int x = 0; x <= width; x += width/10) {
		g2.drawLine(x, 0, x, height); 
		} 
		for(int y = 0; y <= height; y +=height/20) {
			g2.drawLine(0, y, width, y);
		}  
		
	
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {  
			currHoldInput = 0; 
		
		}  
		
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {  
			currHoldInput = 1; 
	
		}  
		
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN) {  
			currHoldInput = 2; 
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) { 
		currHoldInput = -1; 
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			synchronized(currBlock) {
				currBlock.rotate(); 
				repaint();
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
