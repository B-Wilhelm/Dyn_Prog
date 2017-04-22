import java.util.ArrayList;

/**
 * @author Zach Johnson
 * @author Brett Wilhelm
 */

public class DynamicProgramming {
	private static Cell[][] scoreTable;
	private final int $ = 4;//cost of padding for string alignment
	private final int match = 0;//cost of match for string alignment
	private final int mismatch = 2;//cost of non-matching chars for string alignment
	/**
	 * 
	 */
	public DynamicProgramming() {
		
	}
	
	/**
	 * 
	 * @param M
	 * @returns
	 */
	public static ArrayList<Integer> minCostVC(int[][] M) {
		ArrayList<Integer> cut = new ArrayList<Integer>();
		
		return cut;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static String stringAlignment(String x, String y) {
		scoreTable = new Cell[y.length()+1][x.length()+1];
		
		return null;
	}
	//stringAlignment helper methods
	protected Cell getInitialPointer(int r, int c) {
		if(r == 0 && c != 0) {
			return scoreTable[r][c-1];
		}
		else if(c == 0 && r != 0) {
			return scoreTable[r-1][c];
		}
		else {
			return null;
		}
	}
	
	protected int getInitalScore(int r, int c) {
		if(r == 0 && c != 0) {
			return c*$;
		}
		else if(c == 0 && r != 0) {
			return r*$;
		}
		else {
			return 0;
		}
	}
	
	protected void fillInCell(Cell cur, Cell cellAbove, Cell cellLeft, Cell CellAboveLeft, String s1, String s2) {
		int row$Score = cellAbove.getPenalty() + $;
		int col$Score = cellLeft.getPenalty() + $;
		//TODO
	}
	
}