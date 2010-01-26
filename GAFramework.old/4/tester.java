public class tester
{
	public static void main (String [] args) 
	{
		MathEvaluator me = new MathEvaluator("((((A + A+A)+A + A^A)%(A + A+A)+A + A%A)^((A + A+A)+A + A^A)%(A + A+A)+A + A-A)+(((A + A+A)+A + A^A)%(A + A+A)+A + A%A)^((A + A+A)+A + A^A)%(A + A+A)+A + A");
		me.addVariable("A",1.5);
		System.out.println(me.getValue());
 	}
}
