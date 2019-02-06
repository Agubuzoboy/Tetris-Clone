import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreBoard extends JPanel {
	private int score = 0;  
	private JLabel scoreWord = new JLabel();  
	private int height = 100; 
	private int width = 120; 

	public ScoreBoard() { 
		this.setPreferredSize(new Dimension (width, height)); 
		this.setLayout(new BorderLayout());
		scoreWord.setText("Score: 0");   
		scoreWord.setHorizontalAlignment(JLabel.CENTER); 
		scoreWord.setFont(new Font("Serif", Font.BOLD, 17));
		this.add(scoreWord,BorderLayout.CENTER);
		validate(); 
	}
	
	public void setScore(int newScore) {
		score = newScore; 
		scoreWord.setText( "Score: " + score);  
		repaint(); 
	} 
	
	@Override 
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g; 
		g.setColor(new Color(255,255,255)); 
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	
	
}
