import java.awt.Color;

/**
 * @author Zach Johnson
 * @author Brett Wilhelm
 */

public class ImageProcessor {
	Picture p;
	
	/**
	 * Constructor
	 * @param imageFile Name of the image to be manipulated
	 */
	public ImageProcessor(String imageFile) {
		p = new Picture(0, 0);
	}
	
	/**
	 * Compresses the width of the image to a smaller (given) dimension
	 * @param x Width of the file post-modification
	 * @return The picture after the width has been reduced/modified
	 */
	public Picture reduceWidth(double x) {
		Picture p = new Picture((int)x, 0);
		
		
		
		return p;
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
		
		pC = this.p.get(p[0], p[1]);
		qC = this.p.get(q[0], q[1]);
		distance = Math.pow(pC.getRed()-qC.getRed(), 2) + Math.pow(pC.getGreen()-qC.getGreen(), 2) + Math.pow(pC.getBlue()-qC.getBlue(), 2);
		
		return (int)Math.floor(distance);
	}
	
	/**
	 * Method for finding the YImportance value of a pixel
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
	 * Method for finding the XImportance value of a pixel
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
}