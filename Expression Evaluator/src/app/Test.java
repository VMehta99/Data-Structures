package app;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Test {
	public static void expect(Object a, Object b) throws Exception {
		if (!a.equals(b)) {
			throw new Exception("Expected " + a + " to equal " + b);
		}
	}

	public static void testExpression() throws Exception {
		System.out.println("TESTING Expression");

		ArrayList<Variable> vars = new ArrayList<>();
		ArrayList<Array> arrays = new ArrayList<>();

		expect(Expression.evaluate("1", vars, arrays), (float) 1);
		expect(Expression.evaluate("(1)", vars, arrays), (float) 1);
		expect(Expression.evaluate("1.2", vars, arrays), (float) 1.2);
		expect(Expression.evaluate("1+1", vars, arrays), (float) 2.0);
		expect(Expression.evaluate("4-2", vars, arrays), (float) 2.0);
		expect(Expression.evaluate("2*5", vars, arrays), (float) 10.0);
		expect(Expression.evaluate("6/4", vars, arrays), (float) 1.5);
		expect(Expression.evaluate("18/9", vars, arrays), (float) 2.0);
		expect(Expression.evaluate("1+1+1", vars, arrays), (float) 3.0);
		expect(Expression.evaluate("4-3-2", vars, arrays), (float) -1.0);
		expect(Expression.evaluate("3-3-(3-2/1)", vars, arrays), (float) -1.0);
		expect(Expression.evaluate("-1", vars, arrays), (float) -1.0);
		// expect(Expression.evaluate("4--1", vars, arrays), (float) 5.0);
		// expect(Expression.evaluate("1/-1", vars, arrays), (float) -1.0);
		expect(Expression.evaluate("1/(-1)", vars, arrays), (float) -1.0);
		expect(Expression.evaluate("1/(3-3-(3-2/1))", vars, arrays), (float) -1.0);
		expect(Expression.evaluate("4/(3-3-(3-2/1))", vars, arrays), (float) -4.0);
		expect(Expression.evaluate(" 4 / (3 - 3 - 3 * (3 - 2 / 1))*3", vars, arrays), (float) -4.0);
		expect(Expression.evaluate("3*3*3", vars, arrays), (float) 27.0);
		expect(Expression.evaluate("2", vars, arrays), (float) 2.0);
		expect(Expression.evaluate("3/2", vars, arrays), (float) 1.5);
		expect(Expression.evaluate("48/4/3/2", vars, arrays), (float) 2.0);
		expect(Expression.evaluate("1+1*2", vars, arrays), (float) 3.0);
		expect(Expression.evaluate("1+1*2-4", vars, arrays), (float) -1.0);
		expect(Expression.evaluate("4*1+72", vars, arrays), (float) 76);
		expect(Expression.evaluate("4*(1+72)", vars, arrays), (float) 292);
		expect(Expression.evaluate("2+3*(2+78)", vars, arrays), (float) 242);
		expect(Expression.evaluate("3*(4/3)", vars, arrays), (float) 4);

		Expression.makeVariableLists("a+b+A[0]+B[0]+d", vars, arrays);
		expect(vars.get(0).name, "a");
		expect(vars.get(1).name, "b");
		expect(vars.get(2).name, "d");
		expect(arrays.get(0).name, "A");
		expect(arrays.get(1).name, "B");
		expect(vars.size(), 3);
		expect(arrays.size(), 2);
		Expression.loadVariableValues(new Scanner(new File("/home/hamoor/Data-Structures/Expression Evaluator/etest1.txt")), vars, arrays);
		expect(Expression.evaluate("a", vars, arrays), (float) 3);
		expect(Expression.evaluate("b", vars, arrays), (float) 2);
		expect(Expression.evaluate("d", vars, arrays), (float) 56);
		expect(Expression.evaluate("a+b", vars, arrays), (float) 5);
		expect(Expression.evaluate("(a+b)/2", vars, arrays), (float) 2.5);
		expect(Expression.evaluate("a+b*(d/b)", vars, arrays), (float) 59);
		expect(Expression.evaluate("A[0]", vars, arrays), (float) 0);
		expect(Expression.evaluate("A[2]", vars, arrays), (float) 3);
		expect(Expression.evaluate("B[2]", vars, arrays), (float) 1);
		expect(Expression.evaluate("A[2]+B[2]", vars, arrays), (float) 4);
		expect(Expression.evaluate("A[2]*(B[2]+a)", vars, arrays), (float) 12);
		expect(Expression.evaluate("a+A[b]", vars, arrays), (float) 6);
		expect(Expression.evaluate("A[a-1]", vars, arrays), (float) 3);
		expect(Expression.evaluate("A[a+b-1]", vars, arrays), (float) 5);
		expect(Expression.evaluate("a+2*A[a+b-1]", vars, arrays), (float) 13);
		expect(Expression.evaluate("a+2*A[a+b-1]", vars, arrays), (float) 13);
		expect(Expression.evaluate("(a+2)*A[a+b-1]", vars, arrays), (float) 25);
		expect(Expression.evaluate("(a+ 2)*A[ a+		b- 1]", vars, arrays), (float) 25);

		System.out.println("PASSED Expression");
	}

	public static void main(String[] args) throws Exception {
		testExpression();

		final boolean manualDebug = false;
		if (manualDebug) {
			final Scanner stdin = new Scanner(System.in);
			for (;;) {
				System.out.print("Input expression (kill the process to exit): ");
				final String expr = stdin.nextLine();
				System.out.print("Var/arr file location (leave blank if n/a): ");
				final String filename = stdin.nextLine();

				ArrayList<Variable> vars = new ArrayList<>();
				ArrayList<Array> arrays = new ArrayList<>();
				Expression.makeVariableLists("a+b+A[0]+B[0]+d", vars, arrays);
				Expression.loadVariableValues(new Scanner(new File(filename)), vars, arrays);
				final String output = ""+ Expression.evaluate(expr, vars, arrays);
				System.out.println("=> " + output);
			}
		}
	}
}
