import java.util.Scanner;
import java.util.Stack;

public class Calculator {

	// Function to return precedence of operators
	public static int prec(char c) {

		if (c == '/' || c == '*') {

			return 2;

		} else if (c == '+' || c == '-') {

			return 1;

		} else {

			return -1;

		}
	}

	// Function to return associativity of operators
	public static char associativity(char c) {

		if (c == '^') {

			return 'R';

		}

		return 'L';
	}

	public static String parse(String passedExpression) {

		StringBuilder result = new StringBuilder();

		Stack<Character> stack = new Stack<>();

		for (int j = 0; j < passedExpression.length(); j++) {

			char ch = passedExpression.charAt(j);
			int operand = 0;

			// if a number- might be multidigits so get all
			if (Character.isDigit(ch)) {

				int n = 0;

				while (Character.isDigit(ch)) {

					n = n * 10 + (int) (ch - '0');

					j++;

					if (j < passedExpression.length()) {

						ch = passedExpression.charAt(j);

					} else {

						// break looop
						ch = 'a';

					}
				}

				j--;

				operand = n;

				result.append(operand);

			} else {
				
				if (ch == '(') {
					
					stack.push(ch);
				
				} else {
					
					if (ch == ')') {
				

						while (!stack.isEmpty() && stack.peek() != '(') {
	
							result.append(stack.pop());
	
						}
	
						stack.pop(); // Pop '('
				
					} else {

						if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
							
						
							while (!stack.isEmpty() && (prec(ch) < prec(stack.peek())
									|| prec(ch) == prec(stack.peek()) && associativity(ch) == 'L')) {
								
								result.append(stack.pop());
							
							}
							stack.push(ch);
						
					}

			}

		}
			
		}
		}

		while (!stack.isEmpty()) {
			
			result.append(stack.pop());
			
		}

		return result.toString();

	}

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		System.out.println("Enter Expression: ");

		String expression = input.nextLine();

		String postFixExp = parse(expression);

		System.out.println(postFixExp);

		input.close();

	}

}
