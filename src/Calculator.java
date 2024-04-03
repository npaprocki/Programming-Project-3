import java.util.Scanner;
import java.util.Stack;

/**
 * Class: Calculator
 * @author natepaprocki
 * @version 1.0
 * Course: CSE 274 Spring 2024
 * Written: April 5, 2024
 * 
 * Purpose: This class is intended to solve expressions that contain integers operands, basic operators,
 * 			and multiple levels of parentheses.
 * 
 */

public class Calculator {

	/**
	 * Method to determine precedence of operators
	 * 
	 * Note:Tthis method was modifed from https://www.geeksforgeeks.org/convert-infix-expression-to-postfix-expression/
	 * 
	 * @param c	Passed Operand
	 * @return returns 1 if plus or minus and returns 2 if division or multiplication
	 */
	public static int prec(char c) {

		if (c == '/' || c == '*') {

			return 2;

		} else if (c == '+' || c == '-') {

			return 1;

		} else {

			return -1;

		}
	}

	/**
	 * Method to convert an infix expression to postfix expression
	 * 
	 * Note: This method was modifed from https://www.geeksforgeeks.org/convert-infix-expression-to-postfix-expression/
	 * 
	 * @param passedExpression	Passed infix expression
	 * @return postfix expression
	 */
	
	public static String convertPostFix(String passedExpression) {

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

						break;

					}

				}

				//adjust value of j back 1 when char.At(j) is not a number
				j--;

				operand = n;

				result.append(operand  + " ");

			} else {
				
				//handle open parathesis

				if (ch == '(') {

					stack.push(ch);

				} else {

					//handle close parathesis
					
					if (ch == ')') {

						while (!stack.isEmpty() && stack.peek() != '(') {

							result.append(stack.pop() + " ");

						}

						stack.pop(); // Pop '('

					} else {
						
						//handle operators

						if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {

							while (!stack.isEmpty() && (prec(ch) < prec(stack.peek())
									|| prec(ch) == prec(stack.peek()))) {

								result.append(stack.pop()  + " ");

							}
							stack.push(ch);

						}

					}

				}

			}
		}

		//output to string
		while (!stack.isEmpty()) {

			result.append(stack.pop());

		}

		return result.toString();

	}

	/**
	 * Method to evaulate a postfix expression
	 * 
	 * Note: This method was modifed from https://www.geeksforgeeks.org/evaluation-of-postfix-expression/
	 * 
	 * @param passedPostExpression passed postfix expression 
	 * @return value of postfix expression
	 */
	
	public static double evaulate(String passedPostExpression) {
		
		// Create a stack
        Stack<Double> stack = new Stack<>();
 
        // Scan all characters one by one
        for (int i = 0; i < passedPostExpression.length(); i++) {
            char c = passedPostExpression.charAt(i);
 
            if (c == ' ')
                continue;
 
            // If the scanned character is an operand
            // (number here),extract the number
            // Push it to the stack.
            else if (Character.isDigit(c)) {
                
            	int n = 0;


				while (Character.isDigit(c)) {

					n = n * 10 + (int) (c - '0');

					i++;
					
					if (i < passedPostExpression.length()) {

						c = passedPostExpression.charAt(i);

					} else {

						break;

					}
				
				}

                // Push the number in stack
                stack.push((double) n);
            }
 
            // If the scanned character is an operator, pop
            // two elements from stack apply the operator
            else {
                
            	double val1 = stack.pop();
            	double val2 = stack.pop();
 
                switch (c) {
                case '+':
                    stack.push(val2 + val1);
                    break;
                case '-':
                    stack.push(val2 - val1);
                    break;
                case '/':
                    stack.push(val2 / val1);
                    break;
                case '*':
                    stack.push(val2 * val1);
                    break;
                }
            }
        }
        
        return stack.pop();
    }
	
	/**
	 * Main method
	 * 
	 * @param args Command line argument, not used
	 */
	
	public static void main(String[] args) {
		//create scanner
		Scanner input = new Scanner(System.in);

		//prompt and take input
		System.out.println("Enter Expression: ");

		String expression = input.nextLine();

		//convert to postfix
		String postFixExp = convertPostFix(expression);
		
		//evaulate postFix
		System.out.println(expression + " = " + evaulate(postFixExp));
		
		//close scanner
		input.close();

	}

}
