
public class TestStringAlign {
	public static void main(String[] args) {
		//DynamicProgramming dp = new DynamicProgramming();
		String s1 = "GCCCTAGCG";
		String s2 = "GCGCAATG";
		
		String test = DynamicProgramming.stringAlignment(s1, s2);
		System.out.println("expected: GCGC$AATG\n Actual:  " + test);
	}
}
