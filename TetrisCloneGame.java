import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TetrisCloneGame extends JFrame implements ActionListener{
	private int height = 700;
	private int width = 700;  
	private JPanel mainGame; 
	private JPanel menu;  
	private GameDisplay gameDisplay; //also a jpanel  
	private ScoreBoard scoreBoard; 
	private NextBoard nextBoard;
	private Thread gameThread; 
	
	
	public TetrisCloneGame() { 
		//frame setup 
		this.setTitle("Tetris Clone"); 
		this.setSize(new Dimension(width, height));
	    this.setResizable(false); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		this.setLocationRelativeTo(null);
		
		 
		//menu panel
		menu = new JPanel();   
		menu.setLayout(new GridBagLayout());
		this.add(menu);  
		
		//menu button setup
		JButton playButton = new JButton("PLAY"); 
		playButton.setPreferredSize(new Dimension(112, 75));  
		playButton.setMinimumSize(new Dimension(112, 75));  
		playButton.setMaximumSize(new Dimension(112, 75));   
		playButton.addActionListener(this);
		menu.add(playButton); 
		
		this.setVisible(true); 
	} 
	
	public static void main(String[] args) {
		TetrisCloneGame game = new TetrisCloneGame();  
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {  
		makeNewGame();
		
	gameThread = new Thread(new Runnable() {
			public void run() {
				gameDisplay.runGame();
			}
		}); 
		
		gameThread.start(); 
	} 
	
	 void makeNewGame() {
		mainGame = new JPanel();   
		gameDisplay = new GameDisplay();  
		scoreBoard = new ScoreBoard(); 
		nextBoard = new NextBoard();  
		gameDisplay.setScoreBoard(scoreBoard);  
		gameDisplay.setNextBoard(nextBoard);
		mainGame.add(nextBoard); 
		mainGame.add(Box.createHorizontalStrut(20));
		mainGame.add(gameDisplay); 
		mainGame.add(Box.createHorizontalStrut(20));
		mainGame.add(scoreBoard);
		this.remove(menu); 
		this.add(mainGame);   
		this.validate();  
	}

}
