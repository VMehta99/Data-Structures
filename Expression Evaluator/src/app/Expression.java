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
        for(int i = 0; i < expr.toCharArray().length; i++) {
            if(delims.contains(Character.toString(expr.toCharArray()[i])))
                continue;

            else if(i < (expr.length() - 1) && expr.charAt(i + 1) == '[') {
                // find last space
                int j = i;

                while(j > 0 && expr.toCharArray()[j] != ' ')
                    j--;

                if(!arrays.contains(new Array(expr.substring(j + 1, i + 1))))
                    arrays.add(new Array(expr.substring(j + 1, i + 1)));
            }

            else if(i == (expr.length() - 1) || expr.charAt(i + 1) == ' ') {
                int j = i;

                while(j > 0 && expr.toCharArray()[j] != ' ')
                    j--;

                if(!vars.contains(new Variable(expr.substring(j + 1, i + 1))))
                    vars.add(new Variable(expr.substring(j + 1, i + 1)));
            }
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
    private static int findValue(String name) {
        for(Variable v : vars)
            if(v.name.equals(name))
                return v.value;

        return Integer.MIN_VALUE;
    }

    private static int[] findArray(String name) {
        for(Array a : arrays)
            if(a.name.equals(name))
                return a.value;

        return null;
    }

    public static float
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        Stack<String> operations = new Stack<String>();
        Stack<Integer> data = new Stack<Integer>();

        String ops = "+-*/";
        expr = expr.replaceAll(" ", "");

        while(expr.length() != 0) {
            int i = 0;

            while(expr.charAt(i) != '(' &&
                    expr.charAt(i) != '[' &&
                    !ops.contains(Character.toString(i)))
                i++;

            if(expr.charAt(i) == '[') {
                data.push(findArray(expr.substring(i))[(int) evaluate(expr.substring(i + 1, expr.indexOf("]")), vars, arrays)]);
                expr = expr.substring(expr.indexOf("]") + 1);
            }

            else if(ops.contains(Character.toString(expr.charAt(i)))) {
                data.push(findValue(expr.substring(i - 1)));
                operations.push(Character.toString(expr.charAt(i)));
            }

            else {
                // code to evaluate normal cases
            }

        }

        // evaluate stacks

    	return 0;
    }
}
