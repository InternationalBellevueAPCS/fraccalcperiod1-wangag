import java.util.Scanner;

public class FracCalc {

    /**
     * Prompts user for input, passes that input to produceAnswer, then outputs the result.
     * @param args - unused
     */
    public static void main(String[] args) {
        // TODO: Read the input from the user and call produceAnswer with an equation
        // Checkpoint 1: Create a Scanner, read one line of input, pass that input to produceAnswer, print the result.
        Scanner console = new Scanner(System.in);
        System.out.print("input: ");
        String input = console.nextLine();
        
        while (!input.equals("quit")) {
        	String answer = produceAnswer(input);
            System.out.println(answer);
        	System.out.print("input: ");
            input = console.nextLine();
        }
        console.close();
    	// Checkpoint 2: Accept user input multiple times.
    }
    
    /**
     * produceAnswer - This function takes a String 'input' and produces the result.
     * @param input - A fraction string that needs to be evaluated.  For your program, this will be the user input.
     *      Example: input ==> "1/2 + 3/4"
     * @return the result of the fraction after it has been calculated.
     *      Example: return ==> "1_1/4"
     */
    public static String produceAnswer(String input) {
    	Scanner sc = new Scanner(input);
    	String operand1 = sc.next();
    	String operator = sc.next();
    	String operand2 = sc.next();
    	sc.close();
    	
    	String parts1 = parseOperand(operand1);
    	String parts2 = parseOperand(operand2);
    	
        // Checkpoint 2: Return the second operand as a string representing each part.
        //               Example "4/5 * 1_2/4" returns "whole:1 numerator:2 denominator:4".
        // Checkpoint 3: Evaluate the formula and return the result as a fraction.
        //               Example "4/5 * 1_2/4" returns "6/5".
        //               Note: Answer does not need to be reduced, but it must be correct.
        // Final project: All answers must be reduced.
        //               Example "4/5 * 1_2/4" returns "1_1/5".
        
        return parts2;
    }
    
    public static String parseOperand(String operand) {
    	String whole = "0";
    	String numerator = "0";
    	String denominator = "1";
    	for (int i = 0; i < operand.length(); i++) {
    		if (operand.charAt(i) == '_') {
    			whole = operand.substring(0, i);
    		}
    		if (operand.charAt(i) == '/') {
    			if (operand.indexOf('_') != -1) {
    				numerator = operand.substring(operand.indexOf('_') + 1, i);
    			} else {
    				numerator = operand.substring(0, i);
    			}
    			denominator = operand.substring(i+1);
    		}
    	}
    	if (operand.indexOf('/') == -1) {
    		whole = operand;
    	}
    	return "whole:" + whole + " numerator:" + numerator + " denominator:" + denominator;
    }
    
    /**
     * greatestCommonDivisor - Find the largest integer that evenly divides two integers.
     *      Use this helper method in the Final Checkpoint to reduce fractions.
     *      Note: There is a different (recursive) implementation in BJP Chapter 12.
     * @param a - First integer.
     * @param b - Second integer.
     * @return The GCD.
     */
    public static int greatestCommonDivisor(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        while (min != 0) {
            int tmp = min;
            min = max % min;
            max = tmp;
        }
        return max;
    }
    
    /**
     * leastCommonMultiple - Find the smallest integer that can be evenly divided by two integers.
     *      Use this helper method in Checkpoint 3 to evaluate expressions.
     * @param a - First integer.
     * @param b - Second integer.
     * @return The LCM.
     */
    public static int leastCommonMultiple(int a, int b) {
        int gcd = greatestCommonDivisor(a, b);
        return (a*b)/gcd;
    }
}
