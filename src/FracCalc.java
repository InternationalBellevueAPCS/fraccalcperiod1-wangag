import java.util.*;

public class FracCalc {

    /**
     * Prompts user for input, passes that input to produceAnswer, then outputs the result.
     * @param args - unused
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("input: ");
        String input = console.nextLine();
        
        /* Runs the program until the user types "quit". */
        while (!input.equals("quit")) {
        	String answer = produceAnswer(input);
            System.out.println(answer);
        	System.out.print("input: ");
            input = console.nextLine();
        }
        console.close();
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
    	String answer = calculate(operand1, operator, operand2);

    	while (sc.hasNext()) {
    		operator = sc.next();
    		operand2 = sc.next();
        	answer = calculate(answer, operator, operand2);
    	}
    	
    	sc.close();
        return answer;
    }
    /*
     * Performs the desired calculation using the given operands and operator. 
     */
    public static String calculate(String operand1, String operator, String operand2) {
    	int[] parts1 = parseOperand(operand1);
    	int[] parts2 = parseOperand(operand2);
    	int[] fraction1 = makeImproper(parts1);
    	int[] fraction2 = makeImproper(parts2);
    	return evaluate(operator, fraction1, fraction2);
    }
    
    /*
     * Splits user input into a whole number, numerator, and denominator based
     * on expected delimiters and saves these values into an array.
     */
    public static int[] parseOperand(String operand) {
    	int[] parts = new int[3];	// stores whole, numerator, and denominator
    	for (int i = 0; i < operand.length(); i++) {
    		if (operand.charAt(i) == '_') {
    			// whole number precedes underscore
    			parts[0] = Integer.parseInt(operand.substring(0, i));
    		}
    		if (operand.charAt(i) == '/') {
    			if (operand.indexOf('_') != -1) {
    				// when numerator follows an underscore
    				parts[1] = Integer.parseInt(operand.substring(operand.indexOf('_') + 1, i));
    			} else {
    				// when no whole number is entered
    				parts[1] = Integer.parseInt(operand.substring(0, i));
    			}
    			// part after slash is the denominator
    			parts[2] = Integer.parseInt(operand.substring(i+1));
    		}
    	}
    	if (operand.indexOf('/') == -1) {
    		// lack of '/' indicates operand was a whole number
    		parts[0] = Integer.parseInt(operand);
    	}
    	return parts;
    }
    
    /*
     * Changes a mixed number into an improper fraction.
     */
    public static int[] makeImproper(int[] parts) {
    	int[] fraction = new int[2];
    	fraction[0] = parts[1];
		fraction[1] = parts[2];
    	if (parts[0] < 0 || parts[2] < 0) {
    		// applies any existing negative signs to numerator
    		fraction[0] *= -1;
    	}

		if (fraction[0] == 0) {
			// if a whole number, denominator is set to 1
			fraction[1] = 1;
		}
		fraction[0] += parts[0] * fraction[1];	// adds whole to the fraction
    	return fraction;
    }
    
    /*
     * Evaluates the equation, taking in both fractions as well as the operator
     * to determine what operation needs to be performed.
     */
    public static String evaluate(String operator, int[] fraction1, int[] fraction2) {
    	int numerator = 0;
    	int denominator = 0;
    	if (operator.equals("+")) {
    		int lcm = leastCommonMultiple(fraction1[1], fraction2[1]);
    		fraction1 = setEqualDenom(fraction1, lcm);
    		fraction2 = setEqualDenom(fraction2, lcm);
    		numerator = fraction1[0] + fraction2[0];
    		denominator = fraction1[1];
    	} else if (operator.equals("-")) {
    		int lcm = leastCommonMultiple(fraction1[1], fraction2[1]);
    		fraction1 = setEqualDenom(fraction1, lcm);
    		fraction2 = setEqualDenom(fraction2, lcm);
    		numerator = fraction1[0] - fraction2[0];
    		denominator = fraction1[1];
    	} else if (operator.equals("*")) {
    		numerator = fraction1[0] * fraction2[0];
    		denominator = fraction1[1] * fraction2[1];
    	} else if (operator.equals("/")){
    		// multiplies first operand by reciprocal of second
    		numerator = fraction1[0] * fraction2[1];
    		denominator = fraction1[1] * fraction2[0];
    		if (denominator < 0) {
    			// moves negative sign in front of fraction
    			denominator *= -1;
    			numerator *= -1;
    		}
    	}
    	String result = reduce(numerator, denominator);
    	return result;
    }
    
    /*
     * Sets the operands to have the same denominator so they can be easily
     * added or subtracted.
     */
    public static int[] setEqualDenom(int[] fraction, int lcm) {
    	if (fraction[1] != lcm) {
    		int multiplier = lcm / fraction[1];
    		for (int i = 0; i < fraction.length; i++) {
    			fraction[i] *= multiplier;
    		}
    	}
    	return fraction;
    }
    
    /*
     * Reduces the fraction and changes improper fractions to mixed numbers.
     */
    public static String reduce(int num, int denom) {
    	if (denom == 1 || num == 0) {
    		// in case the result is a whole number
    		return num + "";
    	}
    	
    	int divisor = greatestCommonDivisor(num, denom);
    	num /= divisor;
    	denom /= divisor;
    	
		if (Math.abs(num) >= denom) {
			// changes improper fraction to mixed number
			int whole = num / denom;
			num = Math.abs(num % denom);
			denom = Math.abs(denom);
			if (num != 0) {
				return whole + "_" + num + "/" + denom;
			}
			return whole + "";
		}
    	return num + "/" + denom;
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
