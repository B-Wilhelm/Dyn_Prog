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
	
	private int dist(int[] p, int[] q) {
		int distance = 0;
		
		distance = Math.pow(p[0]-q[0], 2) + Math.pow(p[1]-q[1], 2) + Math.pow(p[2]-q[2], 2);
		
		return distance;
	}
}