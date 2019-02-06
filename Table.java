import java.awt.Color;

public class Table {  
	
	
	private Color backgroundColor;  
	private Cell[][] table; 
	
	public Table() {  
		backgroundColor = new Color(11,41,91);
		//make 2d array table
		table = new Cell[10][20];  
		for(int x = 0; x < table.length; x++) {
			for(int y = 0; y< table[x].length; y++) {
				Cell cell = new Cell(x , y);  
				table[x][y] = cell; 
			}
		} 
		
	} 
	
	public Cell[][] getTable(){
		return table;
	}
	
	
	public class Cell{
		private int x; 
		private int y; 
		private Color color; 
		private boolean isEmpty; 
		
		private Cell(int x, int y){ 
			this.x = x; 
			this.y = y;
			color = backgroundColor; 
			isEmpty = true; 
		} 
		
		public void updateCell(Color color, boolean isEmpty) {
			this.color = color; 
			this.isEmpty = isEmpty; 
		} 
		
		public int[] getPos() {
			int[] pos = new int[2]; 
			pos[0] = x; 
			pos[1] =y; 
			return pos; 
		} 
		
		public Color getColor() {
			return color; 
		} 
		
		public boolean getIsEmpty() {
			return isEmpty; 
		}
	}

}
