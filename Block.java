import java.awt.Color;
import java.util.Random;



public class Block { 
	private Table.Cell[][] table; 
	private Table.Cell[] prevCells; 
	private Table.Cell[] currCells; 
	private Color color; 
	private BlockType blockType;  
	private Color backgroundColor = new Color(11,41,91);  
	private boolean landed = false; 
	private int rotationalState = 0;  
	private int blockNum; 
	 
	public Block(Table.Cell[][] table, int blockNum) {  
		this.table = table; 
		prevCells = new Table.Cell[4]; 
		for(int i = 0; i < prevCells.length; i++) {
			prevCells[i] = null; 
		}    
		
		this.blockNum = blockNum;
		
		assignBlockType(); 
		
		//intital block sqaure
		currCells = new Table.Cell[4]; 
		currCells[0] = this.table[5][0];  
		
		//set cells of block
		switch (blockType) {
			case I:  
				currCells[1] = this.table[6][0]; 
				currCells[2]= this.table[7][0]; 
				currCells[3] = this.table[8][0];   
			break; 
			
			case J: 
				currCells[3] = this.table[5][1]; 
				currCells[2] = this.table[4][1];  
				currCells[1] = this.table[3][1]; 
			break;  
			
			case O: 
				currCells[1] = this.table[6][0]; 
				currCells[2] = this.table[5][1];  
				currCells[3] = this.table[6][1]; 
			break;  
			
			case L: 
				currCells[1] = this.table[5][1]; 
				currCells[2] = this.table[6][1];  
				currCells[3] = this.table[7][1]; 
			break;  
			 
			case S: 
				currCells[1] = this.table[4][0]; 
				currCells[3] = this.table[4][1];  
				currCells[2] = this.table[3][1]; 
			break;  
			
			case T: 
				currCells[1] = this.table[4][1]; 
				currCells[2] = this.table[5][1];  
				currCells[3] = this.table[6][1]; 
			break;  
			
			case Z: 
				currCells[1] = this.table[6][0]; 
				currCells[2] = this.table[6][1];  
				currCells[3] = this.table[7][1]; 
			break; 
				
		}   
		
		updateCells(); 
		
		
	}   
	
	public boolean hasLanded() { 
		for(int i =0; i < 4; i++) {
			if(currCells[i].getPos()[1] +1 == 20 ) {
				landed = true; 
				updateCells();
			}  
			else if(!(table[currCells[i].getPos()[0]][currCells[i].getPos()[1] +1].getIsEmpty())) {
				landed = true;  
				updateCells(); 
			}
		} 
		
		
		return landed; 
	}
	
	public void fall(){   
	
		for(int i = 0; i < 4; i++) {  
			prevCells[i] = currCells[i];
			currCells[i] = table[currCells[i].getPos()[0]][currCells[i].getPos()[1] +1];  
			
			
		}   
		updateCells();
	} 
	
	public void moveLeft() { 
		boolean moveable = true; 
		for(int i =0; i < 4; i++) {
			if(currCells[i].getPos()[0] == 0) {
				moveable = false;
			} 
			else if(table[currCells[i].getPos()[0]-1][currCells[i].getPos()[1]].getIsEmpty() == false) {
				moveable = false; 
			}
		} 
		if(moveable) {
		for(int i =0; i < 4; i++) {
			prevCells[i] = currCells[i];
			currCells[i] = table[currCells[i].getPos()[0] -1] [currCells[i].getPos()[1]];  
		}  
		}
		
		updateCells();
	}  
	
	
	public void moveRight() { 
		
		boolean moveable = true; 
		for(int i =0; i < 4; i++) {
			if(currCells[i].getPos()[0] == 9) {
				moveable = false;
			} 
			else if(table[currCells[i].getPos()[0]+1][currCells[i].getPos()[1]].getIsEmpty() == false) {
				moveable = false; 
			}
		} 
		
		if(moveable) {
		for(int i =0; i < 4; i++) {
			prevCells[i] = currCells[i];
			currCells[i] = table[currCells[i].getPos()[0] +1][currCells[i].getPos()[1]];  
		} 
		} 
		
		updateCells();
	}
	
	public void rotate() { 
		for(int i =0; i < 4; i++) {
			prevCells[i] = currCells[i]; 
		}
		switch(blockType) {
		case I: 
			if(rotationalState == 0) { 
				if(checkRotSafe(new int[] {currCells[0].getPos()[0] +1, currCells[0].getPos()[1] -1}, new int[] {currCells[2].getPos()[0]-1,currCells[2].getPos()[1] +1}, 
						new int[] {currCells[3].getPos()[0]-2, currCells[3].getPos()[1] +2} )) {
					
				
				currCells[0] = table[currCells[0].getPos()[0]+1][currCells[0].getPos()[1] -1];  
				currCells[2] = table[currCells[2].getPos()[0]-1][currCells[2].getPos()[1] +1];  
				currCells[3] = table[currCells[3].getPos()[0]-2][currCells[3].getPos()[1] +2];   
				
				rotationalState +=1; 
				}
			} 
			else if(rotationalState == 1) {  
				if(checkRotSafe(new int[] { currCells[0].getPos()[0]-1, currCells[0].getPos()[1] +1}, new int[] {currCells[2].getPos()[0]+1, currCells[2].getPos()[1] -1},
						new int[] {currCells[3].getPos()[0]+2, currCells[3].getPos()[1] -2})){
				currCells[0] = table[currCells[0].getPos()[0]-1][currCells[0].getPos()[1] +1];  
				currCells[2] = table[currCells[2].getPos()[0]+1][currCells[2].getPos()[1] -1];  
				currCells[3] = table[currCells[3].getPos()[0]+2][currCells[3].getPos()[1] -2];  
				rotationalState -=1; 
				}
				
			} 
			break; 
			
		case J: 
			if(rotationalState == 0) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0]+1, currCells[1].getPos()[1] -1}, new int[] {currCells[3].getPos()[0]-1, currCells[3].getPos()[1] +1},
						new int[] {currCells[0].getPos()[0], currCells[0].getPos()[1] +2})) {
				 currCells[1] = table[currCells[1].getPos()[0]+1][currCells[1].getPos()[1] -1];   
				 currCells[3] = table[currCells[3].getPos()[0]-1][currCells[3].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0]][currCells[0].getPos()[1] +2]; 
				 
				 rotationalState +=1;
			}   
			}
			else if(rotationalState == 1) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0]+1, currCells[1].getPos()[1] +1}, new int[] {currCells[3].getPos()[0]-1, currCells[3].getPos()[1] -1}, 
						new int[] {currCells[0].getPos()[0] -2, currCells[0].getPos()[1]} ) ) {
				 currCells[1] = table[currCells[1].getPos()[0]+1][currCells[1].getPos()[1] +1];   
				 currCells[3] = table[currCells[3].getPos()[0]-1][currCells[3].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0] -2][currCells[0].getPos()[1]];  
				 
				 rotationalState +=1;
			} 
			}
			
			else if(rotationalState == 2) { 
				if(checkRotSafe( new int[] {currCells[1].getPos()[0]-1, currCells[1].getPos()[1] +1}, new int[] {currCells[3].getPos()[0]+1, currCells[3].getPos()[1] -1}, 
						new int[] {currCells[0].getPos()[0],currCells[0].getPos()[1]-2  })) {
				 currCells[1] = table[currCells[1].getPos()[0]-1][currCells[1].getPos()[1] +1];   
				 currCells[3] = table[currCells[3].getPos()[0]+1][currCells[3].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0] ][currCells[0].getPos()[1]-2];  
				 
				 rotationalState +=1;
			} }
			
			else if(rotationalState == 3) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0]-1, currCells[1].getPos()[1] -1 }, new int[] {currCells[3].getPos()[0]+1, currCells[3].getPos()[1] +1}, 
						new int[] {	currCells[0].getPos()[0] +2, currCells[0].getPos()[1] })) {
					
				 currCells[1] = table[currCells[1].getPos()[0]-1][currCells[1].getPos()[1] -1];   
				 currCells[3] = table[currCells[3].getPos()[0]+1][currCells[3].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0] +2 ][currCells[0].getPos()[1]];   
				 
				 rotationalState = 0;
			}  
			}
			break;  
			
		case L:  
			if(rotationalState == 0) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] +1, currCells[1].getPos()[1] -1}, new int[] {currCells[3].getPos()[0]-1, currCells[3].getPos()[1] +1}, 
						new int[] {currCells[0].getPos()[0] +2, currCells[0].getPos()[1]})) {
				 currCells[1] = table[currCells[1].getPos()[0] +1][currCells[1].getPos()[1] -1];   
				 currCells[3] = table[currCells[3].getPos()[0]-1][currCells[3].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0] +2 ][currCells[0].getPos()[1]]; 
				 
				 rotationalState +=1; 
			}  
			}
			
			else if(rotationalState == 1) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] +1, currCells[1].getPos()[1] +1}, new int[] {currCells[3].getPos()[0]-1, currCells[3].getPos()[1] -1}, 
						new int[] {currCells[0].getPos()[0], currCells[0].getPos()[1]+2}  )) {
				 currCells[1] = table[currCells[1].getPos()[0] +1][currCells[1].getPos()[1] +1];   
				 currCells[3] = table[currCells[3].getPos()[0]-1][currCells[3].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0]  ][currCells[0].getPos()[1]+2]; 
				 
				 rotationalState +=1; 
			}  
			}
			else if(rotationalState == 2) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] -1, currCells[1].getPos()[1] +1}, new int[] {currCells[3].getPos()[0]+1, currCells[3].getPos()[1] -1}, 
						new int[] { currCells[0].getPos()[0] -2 , currCells[0].getPos()[1]})) {
				 currCells[1] = table[currCells[1].getPos()[0] -1][currCells[1].getPos()[1] +1];   
				 currCells[3] = table[currCells[3].getPos()[0]+1][currCells[3].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0] -2 ][currCells[0].getPos()[1]]; 
				 
				 rotationalState +=1; 
			} 
				}
			else if(rotationalState == 3) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] -1, currCells[1].getPos()[1] -1}, new int[] {currCells[3].getPos()[0]+1, currCells[3].getPos()[1] +1},
						new int[] {currCells[0].getPos()[0], currCells[0].getPos()[1] -2})) {
				 currCells[1] = table[currCells[1].getPos()[0] -1][currCells[1].getPos()[1] -1];   
				 currCells[3] = table[currCells[3].getPos()[0]+1][currCells[3].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0]  ][currCells[0].getPos()[1] -2]; 
				 
				 rotationalState = 0; 
			}
			}
			break; 
			
		case S: 
			if(rotationalState == 0) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] +1, currCells[1].getPos()[1] +1}, new int[] {currCells[2].getPos()[0]+1, currCells[2].getPos()[1] -1}, 
						new int[] {currCells[0].getPos()[0], currCells[0].getPos()[1] +2})) {
				 currCells[1] = table[currCells[1].getPos()[0] +1][currCells[1].getPos()[1] +1];   
				 currCells[2] = table[currCells[2].getPos()[0]+1][currCells[2].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0] ][currCells[0].getPos()[1] +2]; 
				 
				 rotationalState +=1; 
			}  
			}
			else if(rotationalState == 1) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] -1, currCells[1].getPos()[1] -1}, new int[] { currCells[2].getPos()[0]-1, currCells[2].getPos()[1] +1}, new int[] {
						currCells[0].getPos()[0], currCells[0].getPos()[1] -2
				})) 
				{
				 currCells[1] = table[currCells[1].getPos()[0] -1][currCells[1].getPos()[1] -1];   
				 currCells[2] = table[currCells[2].getPos()[0]-1][currCells[2].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0] ][currCells[0].getPos()[1] -2]; 
				 
				 rotationalState =0; 
			}
			}
			
			break; 
			
		case Z:
			if(rotationalState == 0) { 
				if(checkRotSafe(new int[] { currCells[1].getPos()[0] +1, currCells[1].getPos()[1] +1}, new int[] { currCells[3].getPos()[0]-1, currCells[3].getPos()[1] +1}, 
						new int[] {currCells[0].getPos()[0] +2 , currCells[0].getPos()[1]} )) {
				 currCells[1] = table[currCells[1].getPos()[0] +1][currCells[1].getPos()[1] +1];   
				 currCells[3] = table[currCells[3].getPos()[0]-1][currCells[3].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0] +2 ][currCells[0].getPos()[1] ]; 
				 
				 rotationalState +=1; 
			}  
			}
			else if(rotationalState == 1) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] -1, currCells[1].getPos()[1] -1}, new int[] {currCells[3].getPos()[0]+1, currCells[3].getPos()[1] -1}, 
						new int[] {currCells[0].getPos()[0] -2 , currCells[0].getPos()[1] })) {
				 currCells[1] = table[currCells[1].getPos()[0] -1][currCells[1].getPos()[1] -1];   
				 currCells[3] = table[currCells[3].getPos()[0]+1][currCells[3].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0] -2 ][currCells[0].getPos()[1] ]; 
				 
				 rotationalState =0; 
			}  
			}
			
			break; 
			
		case T:  
			if(rotationalState == 0) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] +1, currCells[1].getPos()[1] -1}, new int[] { currCells[3].getPos()[0]-1, currCells[3].getPos()[1] +1}, 
			new int[] {currCells[0].getPos()[0] +1 , currCells[0].getPos()[1] +1})) {
				 currCells[1] = table[currCells[1].getPos()[0] +1][currCells[1].getPos()[1] -1];   
				 currCells[3] = table[currCells[3].getPos()[0]-1][currCells[3].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0] +1 ][currCells[0].getPos()[1] +1]; 
				 
				 rotationalState +=1;  
				}
			} 
			
			else if(rotationalState == 1) { 
					if(checkRotSafe(new int[] {currCells[1].getPos()[0] +1, currCells[1].getPos()[1] +1}, new int[] {currCells[1].getPos()[0] +1, currCells[1].getPos()[1] +1}, 
							new int[] {currCells[0].getPos()[0] -1, currCells[0].getPos()[1] +1})) {
				 currCells[1] = table[currCells[1].getPos()[0] +1][currCells[1].getPos()[1] +1];   
				 currCells[3] = table[currCells[3].getPos()[0]-1][currCells[3].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0] -1 ][currCells[0].getPos()[1] +1]; 
				 
				 rotationalState +=1; 
			}   
			}
			
			else if(rotationalState == 2) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] -1, currCells[1].getPos()[1] +1}, new int[] {currCells[3].getPos()[0]+1, currCells[3].getPos()[1] -1 }, 
						new int[] { currCells[0].getPos()[0] -1, currCells[0].getPos()[1] -1})) {
				 currCells[1] = table[currCells[1].getPos()[0] -1][currCells[1].getPos()[1] +1];   
				 currCells[3] = table[currCells[3].getPos()[0]+1][currCells[3].getPos()[1] -1];    
				 currCells[0] = table[currCells[0].getPos()[0] -1 ][currCells[0].getPos()[1] -1]; 
				 
				 rotationalState +=1; 
			} 
			}
			
			else if(rotationalState == 3) { 
				if(checkRotSafe(new int[] {currCells[1].getPos()[0] -1, currCells[1].getPos()[1] -1} , new int[] {currCells[3].getPos()[0]+1, currCells[3].getPos()[1] +1},
						new int[] {currCells[0].getPos()[0] +1 , currCells[0].getPos()[1] -1})) {
				 currCells[1] = table[currCells[1].getPos()[0] -1][currCells[1].getPos()[1] -1];   
				 currCells[3] = table[currCells[3].getPos()[0]+1][currCells[3].getPos()[1] +1];    
				 currCells[0] = table[currCells[0].getPos()[0] +1 ][currCells[0].getPos()[1] -1]; 
				 
				 rotationalState =0; 
			}  
			}
			
			break;
			
	
		case O: 
			
			break;
			
		} 
		
		updateCells(); 
		// currCells[1].updateCell(new Color(0,0,0), true);
	} 
	
	public Table.Cell[] getCurrCells() {
		return currCells;
	}
	
	private void assignBlockType() {  
		
		
		switch (blockNum) {
			case 0: blockType = BlockType.I;  
			color = new Color(0,204,255);
		break;  
			case 1: blockType = BlockType.J; 
			color = new Color(0,0,255);
		break; 
			case 2: blockType = BlockType.L;  
			color = new Color(255,128,0);
		break; 
			case 3: blockType = BlockType.O;  
			color = new Color(255,255,0);
		break; 
			case 4: blockType = BlockType.S;  
			color = new Color(64,255,0);
		break; 
			case 5: blockType = BlockType.T;  
			color = new Color(191,0,255);
		break; 
			case 6: blockType = BlockType.Z;  
			color = new Color(255,0,0); 
		break;
		}  
		
	
		
	
		}  
	

	
	private void updateCells() { 
		
		for(int i = 0; i < 4; i++) { 
			if(prevCells[i] != null) {
			prevCells[i].updateCell(backgroundColor,  true); 
			}
		} 
		
		for(int i = 0; i < 4; i++) { 
			if(landed) {
			currCells[i].updateCell(color, false); 
			} 
			else {
				currCells[i].updateCell(color, true); 
			}
		} 
		
		
		
	} 
	
	private boolean checkRotSafe(int[] cell1, int[] cell2, int[] cell3) { 
		if(cell1[0] < 0 || cell1[0] > 9) { // passes x boundries
			return false;
		}
		if(cell1[1] < 0 || cell1[1] > 19) { //checks for y boundires
			return false; 
		} 
		
		Table.Cell cell = table[cell1[0]][cell1[1]]; // checks for filled blocks
		if(!cell.getIsEmpty()) {
			return false; 
		}  
		
		
		
		if(cell2[0] < 0 || cell2[0] > 9) { // passes x boundries
			return false;
		}
		if(cell2[1] < 0 || cell2[1] > 19) { //checks for y boundires
			return false; 
		} 
		
		Table.Cell cellb = table[cell2[0]][cell2[1]]; // checks for filled blocks
		if(!cellb.getIsEmpty()) {
			return false; 
		} 
		
		
		if(cell3[0] < 0 || cell3[0] > 9) { // passes x boundries
			return false;
		}
		if(cell3[1] < 0 || cell3[1] > 19) { //checks for y boundires
			return false; 
		} 
		
		Table.Cell cellc = table[cell3[0]][cell3[1]]; // checks for filled blocks
		if(!cellc.getIsEmpty()) {
			return false; 
		}
		
		return true;
	}
	
	
	private enum BlockType{
		I, J, L, O, S, T, Z;  
				
			}
		
	}

