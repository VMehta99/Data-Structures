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

            if(str.contains("[")) {
                String[] splitter = str.split("\\[");

                for(int i = 0; i < splitter.length; i++) {
                    if(i == (splitter.length - 1))
                        vars.add(new Variable(splitter[i]));

                    else
                        arrays.add(new Array(splitter[i]));
                }
            }

            else
                vars.add(new Variable(str.replaceAll("]", "")));
        }

        System.out.println("Variables: " + Arrays.toString(vars.toArray()) + "\nArrays: " + Arrays.toString(arrays.toArray()));
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
    private static int findValue(String name, ArrayList<Variable> vars) {
        for(Variable v : vars)
            if(v.name.equals(name))
                return v.value;

        return Integer.MIN_VALUE;
    }

    private static int[] findArray(String name, ArrayList<Array> arrays) {
        for(Array a : arrays)
            if(a.name.equals(name))
                return a.values;

        return null;
    }

    public static float
    evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {

    	return 0;
    }
}
