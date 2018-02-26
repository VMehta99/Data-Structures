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
        StringTokenizer st = new StringTokenizer(expr, " \t*+-/()");

        while(st.hasMoreTokens()) {
            String str = st.nextToken();

            // if it's an array - account for possibility of nested arrays
            try {
                Double.parseDouble(str);
            } catch(NumberFormatException e) {
                if (str.contains("[")) {
                    String[] splitter = str.split("\\[");

                    for (int i = 0; i < splitter.length; i++) {
                        if (isNumeric(splitter[i])) // a number is not a variable
                            continue;
                        else if (i == (splitter.length - 1) &&
                                !vars.contains(new Variable(splitter[i])))
                            vars.add(new Variable(splitter[i]));
                        else if (!arrays.contains(new Array(splitter[i])))
                            arrays.add(new Array(splitter[i]));
                    }
                }

                // add variable if it's not already there
                else if (!vars.contains(new Variable(str.replaceAll("]", ""))))
                    vars.add(new Variable(str.replaceAll("]", "")));
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

    public static int getValue(String name, ArrayList<Variable> vars) {
        for(Variable v : vars)
            if(v.name.equals(name))
                return v.value;

        return 0;
    }

    public static int[] getArray(String name, ArrayList<Array> arrays) {
        for(Array v : arrays)
            if(v.name.equals(name))
                return v.values;

        return null;
    }

    public static boolean parenBeforeExp(String value) {
        return (!value.contains("[")) ||
                (value.indexOf("(") < value.indexOf("["));
    }

    public static double applyOp(double a, double b, char op) {
        switch(op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return b / a;
        }

        return 0;
    }

    public static float
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        Stack<Double> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();

        expr = expr.replaceAll(" ", "").replaceAll("\t", "");

        while(!expr.equals("")) {
            String str = "";
            String[] strArray = expr.split("[\\*\\+\\-\\/]");

            for(String s : strArray)
                if(!s.equals("")) {
                    str = s;
                    break;
                }

            try {
                nums.push(Double.parseDouble(str));

                if(expr.indexOf(str) != 0)
                    ops.push(expr.charAt(0));

                expr = expr.substring(expr.indexOf(str) + str.length());
           } catch(NumberFormatException e) {
                if(str.contains("(")) {
                    if(parenBeforeExp(expr)) {
                        int start = expr.indexOf("("), end = start + 1, numParen = 1;

                        for(; end < expr.length() && numParen > 0; end++) {
                            if(expr.charAt(end) == '(')
                                numParen++;
                            else if(expr.charAt(end) == ')')
                                numParen--;
                        }

                        if(end >= expr.length())
                            end--;

                        nums.push((double) evaluate(expr.substring(start + 1, end), vars, arrays));

                        if(expr.indexOf(str) > 0)
                            ops.push(expr.charAt(expr.indexOf(str) - 1));

                        expr = (end < expr.length() - 1) ? expr.substring(end + 1) : "";
                    }
                } else if(str.contains("[")) {
                    int start = expr.indexOf("["), end = start + 1, numBrackets = 1;

                    for (; end < expr.length() && numBrackets > 0; end++) {
                        if (expr.charAt(end) == '[')
                            numBrackets++;
                        else if (expr.charAt(end) == ']')
                            numBrackets--;
                    }

                    if(end == expr.length()) end--;

                    nums.push((double) getArray(str.substring(0, start), arrays)[(int) evaluate(expr.substring(start + 1, end), vars, arrays)]);

                    if(expr.indexOf(str) > 0)
                        ops.push(expr.charAt(expr.indexOf(str) - 1));

                    expr = expr.substring(end + 1);
                } else {
                    nums.push((double) getValue(str, vars));

                    if(expr.indexOf(str) > 0)
                        ops.push(expr.charAt(expr.indexOf(str) - 1));

                    expr = expr.substring(expr.indexOf(str) + str.length());
                }
            }
        }

        Stack<Double> newNums = new Stack<>();
        Stack<Character> newOps = new Stack<>();

        // evaluate all multiplication-precedence operations
        while(!ops.isEmpty()) {
            if(ops.peek() == '*' ||
                    ops.peek() == '/')
                newNums.push(applyOp(nums.pop(), nums.pop(), ops.pop()));

            else {
                newNums.push(nums.pop());
                newOps.push(ops.pop());
            }
        }

        while(!nums.isEmpty())
            newNums.push(nums.pop());

        nums = newNums;
        ops = newOps;

        while(!ops.isEmpty())
            nums.push(applyOp((nums.size() == 1) ? 0 : nums.pop(), nums.pop(), ops.pop()));

    	return (float)Double.parseDouble(nums.pop().toString());
    }
}
