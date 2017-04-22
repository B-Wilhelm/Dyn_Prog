/**
 * 
 * @author Zach Johnson
 * 
 */
public class Cell {
	private Cell prev;
	private int penalty, row, col;
	
	public Cell(int r, int c){
		row = r;
		col = c;
	}
	
	public void setPenalty(int p){
		penalty = p;
	}
	
	public void setPrevious(Cell p) {
		prev = p;
	}
	
	public int getPenalty() { return penalty;}
	public int getRow() { return row; }
	public int getCol() { return col; }
	public Cell getPrevious() { return prev; }
	
}
