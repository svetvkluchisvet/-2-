
package com.company;

        import java.util.Scanner;
        import java.util.Stack;

public class Main {
    public static int Priority(char sign) {
        return switch (sign) {
            case '(', ')' -> 1;
            case '-', '+' -> 2;
            case '*', '/', '^' -> 3;
            default -> -1;
        };
    }

    public static double Operation(double a, double b, char sign) {
        return switch (sign) {
            case '+' -> a + b;
            case '-' -> b - a;
            case '*' -> a * b;
            case '/' -> b / a;
            case '^' -> Math.pow(b, a);
            default -> -1;
        };
    }

    public static String PrefixNotation(String expression) {
        Stack<Character> signs = new Stack<>();
        StringBuilder prefixNotation = new StringBuilder();
        for (char symbol : expression.toCharArray()) {
            if ((symbol >= '0' && symbol <= '9') || symbol == ' ') {
                if (symbol != ' ')
                    prefixNotation.append(symbol);
            }
            else
            {
                prefixNotation.append(" ");
                //Если ввели не число и не знак действия
                if (Priority(symbol) == -1) return("!");
                else {
                    if (signs.isEmpty() || Priority(symbol) > Priority(signs.peek())) signs.push(symbol);
                    else {
                        prefixNotation.append(" ");
                        switch (symbol) {
                            case '(' -> signs.push(symbol);
                            case ')' -> {
                                while (signs.peek() != '(') {
                                    prefixNotation.append(signs.peek().toString());
                                    signs.pop();
                                    //Проверка на наличие открывающей скобки
                                    if (signs.isEmpty()) return ("!");
                                }
                                signs.pop();
                            }
                            default -> {
                                while (!signs.isEmpty() && Priority(signs.peek()) >= Priority(symbol)) {
                                    prefixNotation.append(signs.peek().toString());
                                    signs.pop();
                                }
                                signs.push(symbol);
                            }
                        }
                        prefixNotation.append(" ");
                    }
                }
            }
        }
        prefixNotation.append(" ");
        while (!signs.isEmpty())
        {
            prefixNotation.append(signs.peek().toString());
            signs.pop();
        }
        return prefixNotation.toString();
    }

    public static void Calculate(String expression) {
        Stack<Double> numbers = new Stack<>();
        double tmp = 0;
        boolean flag = false;
        for (char symbol : expression.toCharArray()) {
            if (symbol >= '0' && symbol <= '9') {
                tmp = tmp * 10 + Character.getNumericValue(symbol);
                flag = true;
            } else {
                if (symbol == ' ') {
                    if (flag) {
                        numbers.push(tmp);
                        tmp = 0;
                        flag = false;
                    }
                } else {
                    if (numbers.isEmpty()) {
                        System.out.println("Incorrect input");
                        return;
                    }
                    double tmp1 = numbers.peek();
                    numbers.pop();
                    if (numbers.isEmpty()) {
                        System.out.println("Incorrect input");
                        return;
                    }
                    double tmp2 = numbers.peek();
                    numbers.pop();
                    numbers.push(Operation(tmp1, tmp2, symbol));
                }
            }
        }
        double answer = numbers.peek();
        numbers.pop();
        if (!numbers.isEmpty()) {
            System.out.println("Incorrect input");
            return;
        }
        System.out.println(answer);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String expression = scan.nextLine();
        String pref = PrefixNotation(expression);
        //Отладочный вывод
        System.out.println(pref);
        if (pref.equals("!"))
            System.out.println("Incorrect input");
        else {
            Calculate(pref);
        }
    }
}
