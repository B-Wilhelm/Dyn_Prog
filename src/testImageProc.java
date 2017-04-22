/**
 * @author Brett Wilhelm
 */

public class testImageProc {

	public static void main(String[] args) {
		ImageProcessor ip = new ImageProcessor("images/original.jpg");
		
		Picture p = ip.reduceWidth(300);
		p.show();
	}

}
