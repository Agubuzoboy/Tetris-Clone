import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class NextBoard extends JPanel {
private int width = 120; 
private int height = 60;   
private Color backgroundColor;    
private Color blackColor; 
private Cell[][] nextTable; 


public NextBoard() {
	this.setPreferredSize(new Dimension(width, height));  
	backgroundColor = new Color(11,41,91);  
	blackColor = new Color(0,0,0);
	nextTable = new Cell[4][2];  
	
	//for()
	
	//validate();
} 

public void updateNextBoard(int nextBlock) { 
	
	for(int x = 0; x < nextTable.length; x++) {
		for(int y = 0; y < nextTable[0].length; y++) { 
			nextTable[x][y] = null;
		}
	}
	
	switch(nextBlock) {
	case 0:  {
		Cell cell = new Cell(new Color(0,204,255)); 
		nextTable[0][1] = cell; 
		nextTable[1][1] = cell; 
		nextTable[2][1] = cell; 
		nextTable[3][1] = cell;  
		
		repaint(); 
		break;   
	}
		
	case 1:{
		Cell cell = new Cell( new Color(0,0,255));  
		nextTable[3][0] = cell; 
		nextTable[3][1] = cell; 
		nextTable[2][1] = cell; 
		nextTable[1][1] = cell;   
		
		repaint(); 
		break;  
	}  
	
	case 2:  {
		Cell cell = new Cell(new Color(255,128,0)); 
		nextTable[0][0] = cell; 
		nextTable[0][1] = cell; 
		nextTable[1][1] = cell; 
		nextTable[2][1] = cell;  
		
		repaint(); 
		break;   
	}
		
	case 3:{
		Cell cell = new Cell( new Color(255,255,0));  
		nextTable[1][0] = cell; 
		nextTable[2][0] = cell; 
		nextTable[2][1] = cell; 
		nextTable[1][1] = cell;   
		
		repaint(); 
		break;  
	}  
	case 4:  {
		Cell cell = new Cell(new Color(64,255,0)); 
		nextTable[3][0] = cell; 
		nextTable[2][0] = cell; 
		nextTable[2][1] = cell; 
		nextTable[1][1] = cell;  
		
		repaint(); 
		break;   
	}
		
	case 5:{
		Cell cell = new Cell( new Color(191,0,255));  
		nextTable[0][0] = cell; 
		nextTable[1][0] = cell; 
		nextTable[2][0] = cell; 
		nextTable[1][1] = cell;   
		
		repaint(); 
		break;  
	}  
	
	case 6:  {
		Cell cell = new Cell(new Color(255,0,0)); 
		nextTable[1][0] = cell; 
		nextTable[2][0] = cell; 
		nextTable[2][1] = cell; 
		nextTable[3][1] = cell;  
		
		
		repaint(); 
		break;   
	}
		
		
	}
	
	
}

@Override 
public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;  
	g2.setColor(backgroundColor); 
	g.fillRect(0, 0, width, height); 
	
	
	//draw cells 
	
	for(int x = 0; x < nextTable.length; x++) {
		for(int y = 0; y < nextTable[0].length; y++) { 
			if(nextTable[x][y] != null) { 
			g2.setColor(nextTable[x][y].color);
			g2.fillRect(x * 30, y * 30, 30, 30);}
		}
	} 
	
	//draw table
		g2.setColor(blackColor);
		for(int x = 0; x < nextTable.length; x++) {
			g2.drawLine(x *30, 0, x * 30, height);
		} 
		for(int y = 0; y < nextTable[0].length; y++) {
			g2.drawLine(0, y *30, width, y * 30);
		} 
		
} 





public class Cell{
	private Color color; 
	
	private Cell(Color color){ 
		this.color = color;
	} 
	
}
}
