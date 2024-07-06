package com.example.calculate;
import java.util.Stack;

public class RPNCalculator {
    public String convertToRPN(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder rpn = new StringBuilder();
        boolean decimalFlag = false;
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                if (c == '.' && decimalFlag) {
                    currentNumber.append(c);
                } else if (c == '.' && !decimalFlag) {
                    currentNumber.append(c);
                    decimalFlag = true;
                } else {
                    currentNumber.append(c);
                }
                if (i + 1 >= expression.length() || !Character.isDigit(expression.charAt(i + 1)) && expression.charAt(i + 1) != '.') {
                    rpn.append(currentNumber).append(" ");
                    currentNumber = new StringBuilder();
                    decimalFlag = false;
                }
            } else {
                if (decimalFlag) {
                    rpn.append(" ");
                    decimalFlag = false;
                }
                if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        rpn.append(stack.pop()).append(" ");
                    }
                    stack.pop();
                } else {
                    while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                        rpn.append(stack.pop()).append(" ");
                    }
                    stack.push(c);
                }
            }
        }
        while (!stack.isEmpty()) {
            rpn.append(stack.pop()).append(" ");
        }

        return rpn.toString().trim();
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    public double performOperation(String stringRPN) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = stringRPN.split(" ");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double y = stack.pop();
                double x = stack.pop();
                double result;

                switch (token) {
                    case "+":
                        result = x + y;
                        break;
                    case "-":
                        result = x - y;
                        break;
                    case "*":
                        result = x * y;
                        break;
                    case "/":
                        result = x / y;
                        break;
                    case "^":
                        result = Math.pow(x, y);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid operation: " + token);
                }

                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: no result found");
        }

        return stack.pop();
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
