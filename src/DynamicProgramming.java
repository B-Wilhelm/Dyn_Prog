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
		//ArrayList<Integer> tempList = new ArrayList<Integer>(); 
		int row, col;//loop guards
		int TC[][] = new int[M.length][M[0].length+1];
		TC[0][0] = M[0][0];
		for(col = 1; col <= M[0].length; col++) {
			TC[0][col] = TC[0][col-1] + ((M[0][col] < M[1][col])? M[0][col] : M[1][col]);
			TC[M.length -1][col] = TC[M.length -1][col-1] + ((M[0][col] < M[1][col])? M[M.length -1][col] : M[M.length - 2][col]);
		}
		for(row = 1; row < M.length; row++){
			for(col = 1; col <= M[0].length; col++){
				TC[row][col] = getMin(TC[row-1][col-1], TC[row-1][col], TC[row-1][col+1]) + M[row][col];
			}
		}
		int min = M[M.length-1][0];
		int temp = 0;
		for(row = 1; row <= M.length; row++)	{
			if(M[row][M[0].length] < min){
				min = TC[row][col];
				temp = row;
			}
		}
		row = temp;
		cut.add(M[row][M.length]);
		for(col = M[0].length; col > 0; col--){
			if(row == 0) {
				if(TC[row][col -1] > TC[row+1][col -1]){
					row++;
				} 
			} else if (col == M.length){
				if(TC[row][col -1] > TC[row-1][col -1]){
					row--;
				} 
			} else {
				min = getMin(TC[row -1][col -1], TC[row-1][col], TC[row-1][col+1]);
				if(min == TC[row -1][col -1]){
					row--;
				}else if(min == TC[row+1][col -1]){
					row++;
				}
			}
			cut.add(0,M[row][col]);
		}
		
		return cut;
	}
		private static int getMin(int x, int y, int z) {
			if( x< y)
			{
				return (x < z)? x :z;
			}
			else 
			{
				return(y < z)? y : z;
			}
		}
	/**
	 * 
	 * @param x the target string assumed longer than y
	 * @param y the string to be modified to align with x
	 * @return the best possible alignment of y  with regards to x
	 */
	public static String stringAlignment(String x, String y) {
		scoreTable = new Cell[y.length()+1][x.length()+1];
		initializeScoreTable();
		fillInScoreTable(x,y);
		return getTraceback(x,y);
	}
	//stringAlignment helper methods
	/**
	 * 
	 * @param r the row of the cell to get
	 * @param c the column of the cell to get
	 * @return returns a cell based on the default cell link process
	 */
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
	/**
	 * 
	 * @param r the row of the cell
	 * @param c the column of the cell 
	 * @return sets the score of the outermost row/column to max  and all other cells to 0
	 */
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
	/**
	 *initializes the table holding the penalty(Score) of manipulating a the string
	 */
	private static void initializeScoreTable() {
		for(int i = 0; i < scoreTable.length; i++) {
			for(int j = 0; j < scoreTable[0].length; j++) {
				scoreTable[i][j] = new Cell(i, j);//creating the cells
			}
		}
		for(int i = 0; i < scoreTable.length; i++) {
			for(int j = 0; j < scoreTable[0].length; j++) {
				scoreTable[i][j].setPenalty(getInitalScore(i, j));//initializes the penalties
				scoreTable[i][j].setPrevious(getInitialPointer(i, j));//initializes the links between cells
			}
		}
	}
	/**
	 *  iterates through the scoreTable to fill in each cell
	 * @param s1 the string we are trying to align to
	 * @param s2 the string we are modifying
	 */
	private static void fillInScoreTable(String s1, String s2){
		for(int i = 1; i < scoreTable.length; i++) {
			for(int j = 1; j < scoreTable[0].length; j++) {
				Cell cur = scoreTable[i][j];
				Cell cellAbove = scoreTable[i-1][j];
				Cell cellLeft = scoreTable[i][j-1];
				Cell cellAboveLeft = scoreTable[i-1][j-1];
				fillInCell(cur, cellAbove, cellLeft, cellAboveLeft, s1, s2);
			}
		}
	}
	/**
	 * determines what the minimum penalty for a character at cur
	 * @param cur the cell to be filled
	 * @param cellAbove a cell in the same column 1 row above
	 * @param cellLeft a cell in the same row one column to the left
	 * @param cellAboveLeft the cell 1 row and 1 column previous to cur
	 * @param s1 the string we are aligning to
	 * @param s2 the string we are modifying 
	 */
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
		
		if(row$Score <= col$Score) {
			if(isMatchScore <= row$Score) {
				cur.setPenalty(isMatchScore);
				cur.setPrevious(cellAboveLeft);
			} else {
				cur.setPenalty(row$Score);
				cur.setPrevious(cellAbove);
			}
		} else {
			if(isMatchScore <= col$Score) {
				cur.setPenalty(isMatchScore);
				cur.setPrevious(cellAboveLeft);
			} else {
				cur.setPenalty(col$Score);
				cur.setPrevious(cellLeft);
			}
		}
	}
	
	/**
	 * traces through the completed scoresTable finding the optimal character at each index.
	 * @param s1 the string we are aligning to used only for its length
	 * @param s2 the string we are modifying, used only for its length
	 * @return the modified string s2 that most aligns with s1
	 */
	private static String getTraceback(String s1, String s2) {
		
		StringBuffer alignBuf = new StringBuffer();
		Cell cur = scoreTable[s2.length()][s1.length()];
		while(cur.getPrevious() != null) {
			if(cur.getRow() - cur.getPrevious().getRow() == 1) {
				alignBuf.insert(0, s2.charAt(cur.getRow()-1));
			}else {
				alignBuf.insert(0,'$');
			}
			cur = cur.getPrevious();
		}
		String alignments = alignBuf.toString();
		return alignments;
	}
	
}