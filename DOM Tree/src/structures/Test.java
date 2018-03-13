import java.lang.RuntimeException;
import java.util.Scanner;

public class Test {
	public static void expect(Object a, Object b)
	throws RuntimeException {
		if (a == null && b != null || a != null && b == null) {
			throw new RuntimeException("Expected " + a + " to equal " + b);
		}

		if (a == null && b == null) {
			return;
		}

		if (!a.equals(b)) {
			throw new RuntimeException("Expected " + a + " to equal " + b);
		}
	}

	public static void expectNot(Object a, Object b)
	throws RuntimeException {
		try {
			expect(a, b);
		} catch (RuntimeException e) {
			return;
		}
		throw new RuntimeException("Expected " + a + " to not equal " + b);
	}

	public static void testTreeBuild() throws RuntimeException {
		System.out.println("TESTING Test.build");

		Tree tree;

		tree = new Tree(new Scanner(
			""));
		tree.build();
		expect(tree.root.tag, "");
		expect(tree.root.firstChild, null);
		expect(tree.root.sibling, null);

		tree = new Tree(new Scanner(
			"Hi"));
		tree.build();
		expect(tree.root.tag, "Hi");
		expect(tree.root.firstChild, null);
		expect(tree.root.sibling, null);

		tree = new Tree(new Scanner(
			"<p>Hi</p>"));
		tree.build();
		expect(tree.root.tag, "p");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");
		expect(tree.root.firstChild.firstChild, null);
		expect(tree.root.firstChild.sibling, null);

		tree = new Tree(new Scanner(
			"<b>Sup</b>"));
		tree.build();
		expect(tree.root.tag, "b");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Sup");
		expect(tree.root.firstChild.firstChild, null);
		expect(tree.root.firstChild.sibling, null);

		tree = new Tree(new Scanner(
			"<html></html>"));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.firstChild, null);
		expect(tree.root.sibling, null);

		tree = new Tree(new Scanner(
			"<html><p>Hi</p></html>"));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling, null);

		tree = new Tree(new Scanner(
			"<html><p>Hi</p><b>Hey</b></html>"));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "b");
		expect(tree.root.firstChild.sibling.firstChild.tag, "Hey");
		expect(tree.root.firstChild.sibling.firstChild.sibling, null);
		expect(tree.root.firstChild.sibling.firstChild.firstChild, null);
		expect(tree.root.firstChild.firstChild.firstChild, null);

		tree = new Tree(new Scanner(
			"<html><p>Hi<b>Sup</b></p><b>Hey</b></html>"));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "b");
		expect(tree.root.firstChild.sibling.firstChild.tag, "Hey");
		expect(tree.root.firstChild.sibling.firstChild.sibling, null);
		expect(tree.root.firstChild.sibling.firstChild.firstChild, null);
		expect(tree.root.firstChild.firstChild.sibling.tag, "b");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "Sup");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.sibling, null);
		expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild, null);
		expect(tree.root.firstChild.firstChild.firstChild, null);

		tree = new Tree(new Scanner(
			"<html><p>Hi<b>Sup</b></p><b>Hey</b><p>Waddup</p></html>"));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "b");
		expect(tree.root.firstChild.sibling.firstChild.tag, "Hey");
		expect(tree.root.firstChild.sibling.firstChild.sibling, null);
		expect(tree.root.firstChild.sibling.sibling.tag, "p");
		expect(tree.root.firstChild.sibling.sibling.firstChild.tag, "Waddup");
		expect(tree.root.firstChild.sibling.sibling.firstChild.sibling, null);
		expect(tree.root.firstChild.sibling.sibling.firstChild.firstChild, null);
		expect(tree.root.firstChild.sibling.sibling.sibling, null);
		expect(tree.root.firstChild.sibling.firstChild.firstChild, null);
		expect(tree.root.firstChild.firstChild.sibling.tag, "b");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "Sup");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.sibling, null);
		expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild, null);
		expect(tree.root.firstChild.firstChild.firstChild, null);

		tree = new Tree(new Scanner(
			"<p>A<em>new</em>paragraph.</p>"));
		tree.build();
		expect(tree.root.tag, "p");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "A");
		expect(tree.root.firstChild.sibling.tag, "em");
		expect(tree.root.firstChild.sibling.firstChild.tag, "new");
		expect(tree.root.firstChild.sibling.sibling.tag, "paragraph.");

		// official example 1
		final String ex1 =
			"<html><body><p>A line of non-tagged text.</p><p>A<em>new</em>paragraph.</p><table><tr><td><em>R1C1</em></td><td>R1C2</td></tr><tr><td>R2C1</td><td>R2C2</td></tr></table></body></html>";
		tree = new Tree(new Scanner(ex1));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.firstChild.tag, "body");
		expect(tree.root.firstChild.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.firstChild.tag, "A line of non-tagged text.");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild, null);
		expect(tree.root.firstChild.firstChild.firstChild.sibling, null);
		expect(tree.root.firstChild.firstChild.sibling.tag, "p");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "A");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.sibling.tag, "em");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.sibling.firstChild.tag, "new");
		expectNot(tree.root.firstChild.firstChild.sibling.firstChild.sibling.sibling, null);
		expect(tree.root.firstChild.firstChild.sibling.firstChild.sibling.sibling.tag, "paragraph.");
		expect(tree.root.firstChild.firstChild.sibling.sibling.tag, "table");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.tag, "tr");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.firstChild.tag, "td");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.firstChild.firstChild.tag, "em");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.firstChild.firstChild.firstChild.tag, "R1C1");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.firstChild.sibling.tag, "td");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.firstChild.sibling.firstChild.tag, "R1C2");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.sibling.tag, "tr");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.sibling.firstChild.tag, "td");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.sibling.firstChild.firstChild.tag, "R2C1");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.sibling.firstChild.sibling.tag, "td");
		expect(tree.root.firstChild.firstChild.sibling.sibling.firstChild.sibling.firstChild.sibling.firstChild.tag, "R2C2");
		expect(tree.root.firstChild.sibling, null);
		expect(tree.root.sibling, null);

		// official example 2
		final String ex2 =
			"<html><body><ol><li>First item</li><li>Second item</li></ol><ul><li>An item</li><li>Another item</li></ul></body></html>";
		tree = new Tree(new Scanner(ex2));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.firstChild.tag, "body");
		expect(tree.root.firstChild.firstChild.tag, "ol");
		expect(tree.root.firstChild.firstChild.firstChild.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "First item");
		expect(tree.root.firstChild.firstChild.firstChild.sibling.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.sibling.firstChild.tag, "Second item");
		expect(tree.root.firstChild.firstChild.sibling.tag, "ul");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "li");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild.tag, "An item");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.sibling.tag, "li");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.sibling.firstChild.tag, "Another item");

		final String ex3 =
			"<html><body><ol><li>First item<ul><li>Sub item</li><li>Another sub item which has 3 numbered items<ol><li>Item one</li><li>Item two</li><li>Item three</li></ol></li></ul></li><li>Second item</li></ol></body></html>";
		tree = new Tree(new Scanner(ex3));
		tree.build();
		expect(tree.root.tag, "html");
		expect(tree.root.firstChild.tag, "body");
		expect(tree.root.firstChild.firstChild.tag, "ol");
		expect(tree.root.firstChild.firstChild.firstChild.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "First item");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.tag, "ul");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.firstChild.tag, "Sub item");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.tag, "Another sub item which has 3 numbered items");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.sibling.tag, "ol");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.sibling.firstChild.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.sibling.firstChild.firstChild.tag, "Item one");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.sibling.firstChild.sibling.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.sibling.firstChild.sibling.firstChild.tag, "Item two");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.sibling.firstChild.sibling.sibling.tag, "li");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.sibling.firstChild.sibling.firstChild.sibling.firstChild.sibling.sibling.firstChild.tag, "Item three");
	}

	public static void testTreeReplaceTag() throws RuntimeException {
		System.out.println("TESTING Tree.replaceTag");

		Tree tree;

		tree = new Tree(new Scanner("sup"));
		tree.build();
		tree.replaceTag("sup", "waddup");
		expect(tree.root.tag, "waddup");

		tree = new Tree(new Scanner("sup"));
		tree.build();
		tree.replaceTag("p", "waddup");
		expect(tree.root.tag, "sup");

		tree = new Tree(new Scanner("<b>Hey!</b>"));
		tree.build();
		tree.replaceTag("b", "em");
		expect(tree.root.tag, "em");
		expect(tree.root.firstChild.tag, "Hey!");

		tree = new Tree(new Scanner("<b><p><b>Hey!</b></p></b>"));
		tree.build();
		tree.replaceTag("b", "em");
		expect(tree.root.tag, "em");
		expect(tree.root.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.tag, "em");
		expect(tree.root.firstChild.firstChild.firstChild.tag, "Hey!");

		tree = new Tree(new Scanner("<b><p><b>Hey!</b></p><em>Mohammad</em></b>"));
		tree.build();
		tree.replaceTag("b", "em");
		expect(tree.root.tag, "em");
		expect(tree.root.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.tag, "em");
		expect(tree.root.firstChild.firstChild.firstChild.tag, "Hey!");
		expect(tree.root.firstChild.sibling.tag, "em");
		expect(tree.root.firstChild.sibling.firstChild.tag, "Mohammad");

		tree = new Tree(new Scanner("<html><b><p><b>Hey!</b></p><em>Mohammad</em></b><b>p</b></html>"));
		tree.build();
		tree.replaceTag("b", "em");
		expect(tree.root.tag, "html");
		expect(tree.root.firstChild.tag, "em");
		expect(tree.root.firstChild.firstChild.tag, "p");
		expect(tree.root.firstChild.firstChild.firstChild.tag, "em");
		expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "Hey!");
		expect(tree.root.firstChild.firstChild.sibling.tag, "em");
		expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "Mohammad");
		expect(tree.root.firstChild.sibling.tag, "em");
		expect(tree.root.firstChild.sibling.firstChild.tag, "p");
	}

	public static void testTreeRemoveTag() throws RuntimeException {
		System.out.println("TESTING Tree.removeTag");

		Tree tree;

		tree = new Tree(new Scanner("<p>Hi</p>"));
		tree.build();
		tree.removeTag("p");
		expect(tree.root.tag, "Hi");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild, null);

		tree = new Tree(new Scanner("<html><p>Hi</p></html>"));
		tree.build();
		tree.removeTag("p");
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");

		tree = new Tree(new Scanner("<html><p>Hi</p><b>Whaddup</b></html>"));
		tree.build();
		tree.removeTag("p");
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "b");
		expect(tree.root.firstChild.sibling.firstChild.tag, "Whaddup");

		tree = new Tree(new Scanner("<html><p>Hi</p><b>Whaddup</b></html>"));
		tree.build();
		tree.removeTag("p");
		tree.removeTag("b");
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "Whaddup");

		tree = new Tree(new Scanner("<html><p>Hi</p><p>Muhhamad</p><b>Whaddup</b></html>"));
		tree.build();
		tree.removeTag("p");
		tree.removeTag("b");
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "Muhhamad");
		expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");

		tree = new Tree(new Scanner("<html><p>Hi</p><p>Muhhamad</p><p>Whaddup</p></html>"));
		tree.build();
		tree.removeTag("p");
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "Muhhamad");
		expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");

		tree = new Tree(new Scanner("<html><p>Hi</p><p>Muhhamad</p><p>Whaddup<p>Emoji</p></p></html>"));
		tree.build();
		tree.removeTag("p");
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "Muhhamad");
		expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");
		expect(tree.root.firstChild.sibling.sibling.sibling.tag, "Emoji");

		tree = new Tree(new Scanner("<html><p>Hi</p><p>Muhhamad</p><p>Whaddup<p><b></b>Emoji</p></p></html>"));
		tree.build();
		tree.removeTag("p");
		expect(tree.root.tag, "html");
		expect(tree.root.sibling, null);
		expect(tree.root.firstChild.tag, "Hi");
		expect(tree.root.firstChild.sibling.tag, "Muhhamad");
		expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");
		expect(tree.root.firstChild.sibling.sibling.sibling.tag, "b");
		expect(tree.root.firstChild.sibling.sibling.sibling.sibling.tag, "Emoji");
	}

	public static void testTree() throws RuntimeException {
		System.out.println("TESTING Tree");
		testTreeBuild();
		testTreeReplaceTag();
		testTreeRemoveTag();
	}

	public static void main(String[] args) throws RuntimeException {
		System.out.println("TESTING DOM Tree project");
		testTree();
	}
}
