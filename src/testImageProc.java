import java.util.ArrayList;

/**
 * @author Brett Wilhelm
 */

public class testImageProc {

	public static void main(String[] args) {
		ImageProcessor ip = new ImageProcessor("images/original.jpg");
//		ip.p.show();
		
		Picture p = ip.reduceWidth(276);
		p.show();
	}
}