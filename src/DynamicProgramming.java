import java.util.ArrayList;

/**
 * @author Zach Johnson
 * @author Brett Wilhelm
 */

public class DynamicProgramming {
	private static Cell[][] scoreTable;
	private final static int $ = 4;//cost of padding for string alignment
	private final static int match = 0;//cost of match for string alignment
	private final static int mismatch = 2;//cost of non-matching chars for string alignment
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
		
		for(int i = 0; i < M[0].length; i++) {
			cut.add(i);
			cut.add(1);
		}
		
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
		initializeScoreTable();
		fillInScoreTable(x,y);
		String[] alignments = (String[]) getTraceback(x,y);
		return alignments[1];
	}
	//stringAlignment helper methods
	private static Cell getInitialPointer(int r, int c) {
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
	
	private static int getInitalScore(int r, int c) {
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
	
	private static void initializeScoreTable() {
		for(int i = 0; i < scoreTable.length; i++) {
			for(int j = 0; j < scoreTable.length; j++) {
				scoreTable[i][j] = new Cell(i, j);
				scoreTable[i][j].setPenalty(getInitalScore(i, j));
				scoreTable[i][j].setPrevious(getInitialPointer(i, j));
			}
		}
	}
	
	private static void fillInScoreTable(String s1, String s2){
		for(int i = 1; i < scoreTable.length; i++) {
			for(int j = 1; j < scoreTable.length; j++) {
				Cell cur = scoreTable[i][j];
				Cell cellAbove = scoreTable[i-1][j];
				Cell cellLeft = scoreTable[i][j-1];
				Cell cellAboveLeft = scoreTable[i-1][j-1];
				fillInCell(cur, cellAbove, cellLeft, cellAboveLeft, s1, s2);
			}
		}
	}
	
	private static void fillInCell(Cell cur, Cell cellAbove, Cell cellLeft, Cell cellAboveLeft, String s1, String s2) {
		int row$Score = cellAbove.getPenalty() + $;
		int col$Score = cellLeft.getPenalty() + $;
		int isMatchScore = cellAboveLeft.getPenalty();
		
		if(s2.charAt(cur.getRow() - 1) == s1.charAt(cur.getCol()-1)){
			isMatchScore += match;
		}
		else {
			isMatchScore += mismatch;
		}
		
		if(row$Score >= col$Score) {
			if(isMatchScore >= row$Score) {
				cur.setPenalty(isMatchScore);
				cur.setPrevious(cellAboveLeft);
			} else {
				cur.setPenalty(row$Score);
				cur.setPrevious(cellAbove);
			}
		} else {
			if(isMatchScore >= col$Score) {
				cur.setPenalty(isMatchScore);
				cur.setPrevious(cellAboveLeft);
			} else {
				cur.setPenalty(col$Score);
				cur.setPrevious(cellLeft);
			}
		}
	}
	
	private static Object getTraceback(String s1, String s2) {
		StringBuffer align1Buf = new StringBuffer();
		StringBuffer align2Buf = new StringBuffer();
		Cell cur = scoreTable[scoreTable.length - 1][scoreTable[0].length -1];
		while(cur.getPrevious() != null) {
			if(cur.getRow() - cur.getPrevious().getRow() == 1) {
				align2Buf.insert(0, s2.charAt(cur.getRow()-1));
			}else {
				align2Buf.insert(0,'$');
			}
			if(cur.getCol() - cur.getPrevious().getCol() == 1) {
				align1Buf.insert(0, s1.charAt(cur.getCol() -1));
			}else {
				align1Buf.insert(0,'$');
			}
			cur = cur.getPrevious();
		}
		String[] alignments = new String[] { align1Buf.toString(), align2Buf.toString() };
		return alignments;
	}
	
}