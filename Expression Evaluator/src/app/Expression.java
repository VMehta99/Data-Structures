package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple nums, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all nums and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The nums array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static void
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        StringTokenizer st = new StringTokenizer(expr, delims);

        while(st.hasMoreTokens()) {
            String str = st.nextToken();

            try {
                Double.parseDouble(str);
            } catch(NumberFormatException e) {
                if((expr.indexOf(str) + str.length()) < expr.length() &&
                        expr.charAt(expr.indexOf(str) + str.length()) == '[' &&
                        !arrays.contains(new Array(str)))
                    arrays.add(new Array(str));
                else if(!vars.contains(new Variable(str)))
                    vars.add(new Variable(str)); 
            }
        }
    }
    
    /**
     * Loads values for nums and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The nums array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;
                }
            }
        }
    }

    /**
     * Evaluates the expression.
     *
     * @param vars The nums array list, with values for all nums in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */

    private static int getValue(String name, ArrayList<Variable> vars) {
        for(Variable v : vars)
            if(v.name.equals(name))
                return v.value;

        return 0;
    }

    private static int[] getArray(String name, ArrayList<Array> arrays) {
        for(Array v : arrays)
            if(v.name.equals(name))
                return v.values;

        return null;
    }

    private static boolean parenBeforeExp(String value) {
        return (!value.contains("[")) ||
                (value.indexOf("(") < value.indexOf("["));
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    private static double applyOp(double a, double b, char op) {
        switch(op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return (a == 0) ? 0 : b / a;
        }

        return 0;
    }

    public static float
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        Stack<Double> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();

        expr = expr.replaceAll(" ", "").replaceAll("\t", "");

        while(!expr.equals("")) {
            double num = 0;
            char op = ' ';

            String str = "";
            String[] strArray = expr.split("[\\*\\+\\-\\/]");

            for (String s : strArray)
                if (!s.equals("")) {
                    str = s;
                    break;
                }

            try {
                num = Double.parseDouble(str);

                if (expr.indexOf(str) > 0)
                    op = expr.charAt(0);

                expr = expr.substring(expr.indexOf(str) + str.length());
            } catch (NumberFormatException e) {
                if (str.contains("(")) {
                    if (parenBeforeExp(expr)) {
                        int start = expr.indexOf("("), end = start + 1, numParen = 1;

                        for (; end < expr.length(); end++) {
                            if (expr.charAt(end) == '(')
                                numParen++;
                            else if (expr.charAt(end) == ')')
                                numParen--;

                            if (numParen <= 0)
                                break;
                        }

                        if (end >= expr.length())
                            end--;

                        num = (double) evaluate(expr.substring(start + 1, end), vars, arrays);

                        if (expr.indexOf(str) > 0)
                            op = expr.charAt(expr.indexOf(str) - 1);

                        expr = (end < expr.length() - 1) ? expr.substring(end + 1) : "";
                    }
                } else if (str.contains("[")) {
                    int start = expr.indexOf("["), end = start + 1, numbrackets = 1;

                    for (; end < expr.length() && numbrackets > 0; end++) {
                        if (expr.charAt(end) == '[')
                            numbrackets++;
                        else if (expr.charAt(end) == ']')
                            numbrackets--;
                    }

                    if (end >= expr.length() ||
                            expr.charAt(end) != ']')
                        end--;

                    num = (double) getArray(expr.substring((expr.charAt(0) == '+' || expr.charAt(0) == '-' || expr.charAt(0) == '*' || expr.charAt(0) == '/') ? 1 : 0, start), arrays)[(int) evaluate(expr.substring(start + 1, end), vars, arrays)];

                    if (expr.indexOf(str) > 0)
                        op = expr.charAt(expr.indexOf(str) - 1);

                    expr = expr.substring(end + 1);
                } else {
                    num = (double) getValue(str, vars);

                    if (expr.indexOf(str) > 0)
                        op = expr.charAt(expr.indexOf(str) - 1);

                    expr = expr.substring(expr.indexOf(str) + str.length());
                }
            }

            nums.push(num);

            if (op == '*')
                nums.push(nums.pop() * nums.pop());
            else if (op == '/')
                nums.push((1 / nums.pop()) * nums.pop());
            else if(op != ' ')
                ops.push(op);
        }

        // reverse stacks, then execute in reading order
        Stack<Double> reverseNums = new Stack<>();
        Stack<Character> reverseOps = new Stack<>();

        while(!ops.isEmpty())
            reverseOps.push(ops.pop());

        while(!nums.isEmpty())
            reverseNums.push(nums.pop());

        nums = reverseNums;
        ops = reverseOps;

        while(!ops.isEmpty() &&
                nums.size() >= 2) {
            if (ops.peek() == '+') {
                nums.push(nums.pop() + nums.pop());
                ops.pop();
            } else if (ops.peek() == '-') {
                nums.push(nums.pop() - nums.pop());
                ops.pop();
            } else if (ops.peek() == '*') {
                nums.push(nums.pop() * nums.pop());
                ops.pop();
            } else {
                nums.push(nums.pop() / nums.pop());
                ops.pop();
            }
        }

        if(ops.size() == 1 &&
                ops.peek() == '-' &&
                nums.size() == 1)
            return (float) (-1 * Double.parseDouble(nums.pop().toString()));

    	return (float)Double.parseDouble(nums.pop().toString());
    }
}
