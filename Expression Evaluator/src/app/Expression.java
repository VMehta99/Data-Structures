package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]";
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */

    public static void
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        StringTokenizer st = new StringTokenizer(expr, " \t*+-/()");

        while(st.hasMoreTokens()) {
            String str = st.nextToken();

            // if it's an array - account for possibility of nested arrays
            if(str.contains("[")) {
                String[] splitter = str.split("\\[");

                for(int i = 0; i < splitter.length; i++) {
                    if(i == (splitter.length - 1) &&
                            !vars.contains(new Variable(splitter[i])))
                        vars.add(new Variable(splitter[i]));

                    else if(!arrays.contains(new Array(splitter[i])))
                        arrays.add(new Array(splitter[i]));
                }
            }

            // add variable if it's not already there
            else if(!vars.contains(new Variable(str.replaceAll("]", ""))))
                vars.add(new Variable(str.replaceAll("]", "")));
        }
    }
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
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
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */

    public static int getValue(String name) {
        for(Variable v : vars)
            if(v.name.equals(name))
                return v.value;

        return 0;
    }

    public static int[] getArray(String name) {
        for(Array v : arrays)
            if(v.name.equals(name))
                return v.values;

        return null;
    }

    public static float
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        Stack<Integer> values = new Stack<Integer>();
        Stack<Character> ops = new Stack<Character>();

        expr = expr.replaceAll(" ", "");
        StringTokenizer st = new StringTokenizer(expr, delims;

        for(int i = 0; i < expr.length(); i++) {
            String instance = st.nextToken();
            int value = 0;
            char op = ' ';

            if(expr.indexOf(instance) > 0 &&
                    expr.charAt(expr.indexOf(instance) - 1) == '(') {
                int start = expr.indexOf(instance), end = start + 1, numParentheses = 1;

                // check for end of subexpression
                for(; end < expr.length() && numParentheses > 0; end++) {
                    if(expr.charAt(end) == '(')
                        numParentheses++;
                    else if(expr.charAt(end) == ')')
                        numParentheses--;
                }

                value = Integer.valueOf((int)evaluate(expr.substring(start + 1, end), vars, arrays));

                if(end < expr.length() - 1)
                    op = expr.charAt(end + 1);

                expr = expr.substring(end + 1);
            } else if(expr.charAt(expr.indexOf(instance) + instance.length()) == '[') {
                int start = expr.indexOf(instance) + instance.length(), end = start + 1, numBrackets = 1;

                for(; end < expr.length() && numBrackets > 0; end++) {
                    if(expr.charAt(end) == '[')
                        numBrackets++;
                    else if(expr.charAt(end) == ']')
                        numBrackets--;
                }

                value = Integer.valueOf(getArray(instance)[(int)evaluate(expr.substring(start + 1, end), vars, arrays)]);

                if(end < expr.length() - 1)
                    op = expr.charAt(end + 1);
            } else {
                value = getValue(instance);
                int someMeaningfulVariableName = expr.indexOf(instance) + instance.length();

                if(someMeaningfulVariableName < expr.length())
                    op = expr.charAt(someMeaningfulVariableName);

                expr = expr.substring(someMeaningfulVariableName - 1);
            }

            if((op == '*' || op == '/') &&
                    (ops.peek().equals('+') || ops.peek().equals('-'))) {

            }
        }

    	return 0;
    }
}
