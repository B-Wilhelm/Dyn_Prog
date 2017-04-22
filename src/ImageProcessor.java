import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Brett Wilhelm
 * @author Zach Johnson
 */

public class ImageProcessor {
	public Picture p;
	private int[][] I;
	
	/**
	 * Constructor
	 * @param imageFile Name of the image to be manipulated
	 */
	public ImageProcessor(String imageFile) {
		p = new Picture(imageFile);
		System.out.println("Picture Created!");
	}
	
	/**
	 * Compresses the width of the image to a smaller (given) dimension
	 * @param x Width of the file post-modification
	 * @return The picture after the width has been reduced/modified
	 */
	public Picture reduceWidth(double x) {
		Picture P = new Picture((int)Math.ceil(x), p.height());
		Picture comp = new Picture(p);
		ArrayList<Integer> toRemove;
		
		for(int i = 0; i < (p.width()-(int)x); i++) {
			I = new int[p.height()][p.width()-i];
			computeMatrix();
			toRemove = DynamicProgramming.minCostVC(I);
			P = makeImage(P, toRemove);
		}
		return P;
	}
	
	/**
	 * Copies the first picture into the second, but removes pixels to satisfy reduceWidth()
	 * @param P The picture to be edited
	 * @param toRemove	The ArrayList of coordinates to remove to minimize cost
	 * @return The picture with reduced width
	 */
	private Picture makeImage(Picture P, ArrayList<Integer> toRemove) {
		int i, j, k, l;
		for(i = 0, k = 0; i < P.height(); i++, k++) {
			for(j = 0, l = 0; j < P.width(); j++, l++) {
				if(toRemove.get(k*2).equals(k) && (toRemove.get(k*2+1).equals(l))) {
					i--;
					j--;
				}
				else {
					P.set(j, i, p.get(l, k));
				}
			}
		}
		
		return P;
	}
	
	/**
	 * Fills the matrix I with values made from calling importance() on every pixel in the Picture
	 */
	private void computeMatrix() {
		if(I == null) {
			return;
		}
		
		for(int i = 0; i < p.height(); i++) {
			for(int j = 0; j < p.width(); j++) {
				I[i][j] = importance(i, j);
			}
		}
	}
	
	/**
	 * Method for finding the Importance value of a pixel, calls upon xImportance() and yImportance()
	 * @param pixel Coordinates of pixel to be checked
	 * @return Importance value of pixel argument
	 */
	private int importance(int i, int j) {
		int[] pixel = {i, j};
		int importance = xImportance(pixel) + yImportance(pixel);
		
		return importance;
	}
	
	/**
	 * Method for finding the YImportance value of a pixel, calls upon dist()
	 * @param pixel Coordinates of pixel to be checked
	 * @return YImportance value of pixel argument
	 */
	private int yImportance(int[] pixel) {
		int pHeight = this.p.height();
		int firstVal[] = new int[2];
		int secVal[] = new int[2];
		int i = pixel[0];
		int j = pixel[1];
		
		firstVal[1] = j;
		secVal[1] = j;
		
		if(i < 0 && i > pHeight) {
			return -1;
		}
		
		if(i == 0) {
			firstVal[0] = pHeight-1;
			secVal[0] = i+1;
		}
		else if(i == pHeight-1) {
			firstVal[0] = i-1;
			secVal[0] = 0;
		}
		else {
			firstVal[0] = i-1;
			secVal[0] = i+1;
		}
		return dist(firstVal, secVal);
	}
	
	/**
	 * Method for finding the XImportance value of a pixel, calls upon dist()
	 * @param pixel Coordinates of pixel to be checked
	 * @return XImportance value of pixel argument
	 */
	private int xImportance(int[] pixel) {
		int firstVal[] = new int[2];
		int secVal[] = new int[2];
		int i = pixel[0], j = pixel[1];
		int pWidth = this.p.width();
		
		firstVal[0] = i;
		secVal[0] = i;
		
		if(j < 0 && j > pWidth) {
			return -1;
		}
		
		if(j == 0) {
			firstVal[1] = pWidth-1;
			secVal[1] = j+1;
		}
		else if(j == pWidth-1) {
			firstVal[1] = 0;
			secVal[1] = j-1;
		}
		else {
			firstVal[1] = j-1;
			secVal[1] = j+1;
		}
		return dist(firstVal, secVal);
	}
	
	/**
	 * Finds the color distance value for two pixels, used for finding the XImportance and YImportance values.
	 * @param p Coordinates of 1st pixel in Picture
	 * @param q	Coordinates of 2nd pixel in Picture
	 * @return	Difference between color values of two pixels
	 */
	private int dist(int[] p, int[] q) {
		double distance = 0;
		Color pC, qC;
		
		pC = this.p.get(p[1], p[0]);
		qC = this.p.get(q[1], q[0]);
		distance = Math.pow(pC.getRed()-qC.getRed(), 2) + Math.pow(pC.getGreen()-qC.getGreen(), 2) + Math.pow(pC.getBlue()-qC.getBlue(), 2);
		
		return (int)Math.floor(distance);
	}
}