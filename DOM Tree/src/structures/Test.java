package structures;

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
        System.out.println("TESTING Tree.build");

        Tree tree;

        tree = new Tree(new Scanner(
                ""));
        tree.build();
        expect(tree.root.tag, "");
        expect(tree.root.firstChild, null);
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner(
                " hi"));
        tree.build();
        expect(tree.root.tag, " hi");
        expect(tree.root.firstChild, null);
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner(
                " hi "));
        tree.build();
        expect(tree.root.tag, " hi ");
        expect(tree.root.firstChild, null);
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner(
                "google.com "));
        tree.build();
        expect(tree.root.tag, "google.com ");
        expect(tree.root.firstChild, null);
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner(
                "Hi"));
        tree.build();
        expect(tree.root.tag, "Hi");
        expect(tree.root.firstChild, null);
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner(
                "<p>\nHi\n</p>"));
        tree.build();
        expect(tree.root.tag, "p");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.firstChild.firstChild, null);
        expect(tree.root.firstChild.sibling, null);

        tree = new Tree(new Scanner(
                "<b>\nSup\n</b>"));
        tree.build();
        expect(tree.root.tag, "b");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Sup");
        expect(tree.root.firstChild.firstChild, null);
        expect(tree.root.firstChild.sibling, null);

        tree = new Tree(new Scanner(
                "<html>\n</html>"));
        tree.build();
        expect(tree.root.tag, "html");
        expect(tree.root.firstChild, null);
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner(
                "<html>\n<p>\nHi\n</p>\n</html>"));
        tree.build();
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "p");
        expect(tree.root.firstChild.firstChild.tag, "Hi");
        expect(tree.root.firstChild.sibling, null);

        tree = new Tree(new Scanner(
                "<html>\n<p>\nHi\n</p>\n<b>\nHey\n</b>\n</html>"));
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
                "<html>\n<p>\nHi\n<b>\nSup\n</b>\n</p>\n<b>\nHey\n</b>\n</html>"));
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
                "<html>\n<p>\nHi\n<b>\nSup\n</b>\n</p>\n<b>\nHey\n</b>\n<p>\nWaddup\n</p>\n</html>"));
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
                "<p>\nA\n<em>\nnew\n</em>\nparagraph.\n</p>"));
        tree.build();
        expect(tree.root.tag, "p");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "A");
        expect(tree.root.firstChild.sibling.tag, "em");
        expect(tree.root.firstChild.sibling.firstChild.tag, "new");
        expect(tree.root.firstChild.sibling.sibling.tag, "paragraph.");

        // official example 1
        final String ex1 =
                "<html>\n<body>\n<p>\nA line of non-tagged text.\n</p>\n<p>\nA\n<em>\nnew\n</em>\nparagraph.\n</p>\n<table>\n<tr>\n<td>\n<em>\nR1C1\n</em>\n</td>\n<td>\nR1C2\n</td>\n</tr>\n<tr>\n<td>\nR2C1\n</td>\n<td>\nR2C2\n</td>\n</tr>\n</table>\n</body>\n</html>";
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
                "<html>\n<body>\n<ol>\n<li>\nFirst item\n</li>\n<li>\nSecond item\n</li>\n</ol>\n<ul>\n<li>\nAn item\n</li>\n<li>\nAnother item\n</li>\n</ul>\n</body>\n</html>";
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
                "<html>\n<body>\n<ol>\n<li>\nFirst item\n<ul>\n<li>\nSub item\n</li>\n<li>\nAnother sub item which has 3 numbered items\n<ol>\n<li>\nItem one\n</li>\n<li>\nItem two\n</li>\n<li>\nItem three\n</li>\n</ol>\n</li>\n</ul>\n</li>\n<li>\nSecond item\n</li>\n</ol>\n</body>\n</html>";
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

        tree = new Tree(new Scanner("<b>\nHey!\n</b>"));
        tree.build();
        tree.replaceTag("b", "em");
        expect(tree.root.tag, "em");
        expect(tree.root.firstChild.tag, "Hey!");

        tree = new Tree(new Scanner("<b>\n<p>\n<b>\nHey!\n</b>\n</p>\n</b>"));
        tree.build();
        tree.replaceTag("b", "em");
        expect(tree.root.tag, "em");
        expect(tree.root.firstChild.tag, "p");
        expect(tree.root.firstChild.firstChild.tag, "em");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "Hey!");

        tree = new Tree(new Scanner("<b>\n<p>\n<b>\nHey!\n</b>\n</p>\n<em>\nMohammad\n</em>\n</b>"));
        tree.build();
        tree.replaceTag("b", "em");
        expect(tree.root.tag, "em");
        expect(tree.root.firstChild.tag, "p");
        expect(tree.root.firstChild.firstChild.tag, "em");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "Hey!");
        expect(tree.root.firstChild.sibling.tag, "em");
        expect(tree.root.firstChild.sibling.firstChild.tag, "Mohammad");

        tree = new Tree(new Scanner("<html>\n<b>\n<p>\n<b>\nHey!\n</b>\n</p>\n<em>\nMohammad\n</em>\n</b>\n<b>\np\n</b>\n</html>"));
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

        tree = new Tree(new Scanner("<p>\nHi\n</p>"));
        tree.build();
        tree.removeTag("p");
        expect(tree.root.tag, "Hi");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild, null);

        tree = new Tree(new Scanner("<html>\n<p>\nHi\n</p>\n</html>"));
        tree.build();
        tree.removeTag("p");
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");

        tree = new Tree(new Scanner("<html>\n<p>\nHi\n</p>\n<b>\nWhaddup\n</b>\n</html>"));
        tree.build();
        tree.removeTag("p");
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.firstChild.sibling.tag, "b");
        expect(tree.root.firstChild.sibling.firstChild.tag, "Whaddup");

        tree = new Tree(new Scanner("<html>\n<p>\nHi\n</p>\n<b>\nWhaddup\n</b>\n</html>"));
        tree.build();
        tree.removeTag("p");
        tree.removeTag("b");
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.firstChild.sibling.tag, "Whaddup");

        tree = new Tree(new Scanner("<html>\n<p>\nHi\n</p>\n<p>\nMuhhamad\n</p>\n<b>\nWhaddup\n</b>\n</html>"));
        tree.build();
        tree.removeTag("p");
        tree.removeTag("b");
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.firstChild.sibling.tag, "Muhhamad");
        expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");

        tree = new Tree(new Scanner("<html>\n<p>\nHi\n</p>\n<p>\nMuhhamad\n</p>\n<p>\nWhaddup\n</p>\n</html>"));
        tree.build();
        tree.removeTag("p");
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.firstChild.sibling.tag, "Muhhamad");
        expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");

        tree = new Tree(new Scanner("<html>\n<p>\nHi\n</p>\n<p>\nMuhhamad\n</p>\n<p>\nWhaddup\n<p>\nEmoji\n</p>\n</p>\n</html>"));
        tree.build();
        tree.removeTag("p");
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.firstChild.sibling.tag, "Muhhamad");
        expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");
        expect(tree.root.firstChild.sibling.sibling.sibling.tag, "Emoji");

        tree = new Tree(new Scanner("<html>\n<p>\nHi\n</p>\n<p>\nMuhhamad\n</p>\n<p>\nWhaddup\n<p>\n<b>\n</b>\nEmoji\n</p>\n</p>\n</html>"));
        tree.build();
        tree.removeTag("p");
        expect(tree.root.tag, "html");
        expect(tree.root.sibling, null);
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.firstChild.sibling.tag, "Muhhamad");
        expect(tree.root.firstChild.sibling.sibling.tag, "Whaddup");
        expect(tree.root.firstChild.sibling.sibling.sibling.tag, "b");
        expect(tree.root.firstChild.sibling.sibling.sibling.sibling.tag, "Emoji");


        tree = new Tree(new Scanner("<ul>\n<li>\nHi\n</li>\n</ul>"));
        tree.build();
        tree.removeTag("ul");
        expect(tree.root.tag, "p");
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner("<ol>\n<li>\nHi\n</li>\n</ol>"));
        tree.build();
        tree.removeTag("ol");
        expect(tree.root.tag, "p");
        expect(tree.root.firstChild.tag, "Hi");
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner("<ol>\n<li>\nHi\n</li>\n</ol>"));
        tree.build();
        tree.removeTag("ul");
        expect(tree.root.tag, "ol");
        expect(tree.root.firstChild.tag, "li");
        expect(tree.root.firstChild.firstChild.tag, "Hi");
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner("<ol>\n<li>\nHi, \n<b>\nSup!\n</b>\n</li>\n</ol>"));
        tree.build();
        tree.removeTag("ul");
        expect(tree.root.tag, "ol");
        expect(tree.root.firstChild.tag, "li");
        expect(tree.root.firstChild.firstChild.tag, "Hi, ");
        expect(tree.root.firstChild.firstChild.sibling.tag, "b");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "Sup!");
        expect(tree.root.sibling, null);

        tree = new Tree(new Scanner("<html>\n<ol>\n<li>\nHi, \n<b>\nSup!\n</b>\n</li>\n</ol>\n<ul>\n<li>\nHey\n</li>\n</ul>\n</html>"));
        tree.build();
        tree.removeTag("ul");
        expect(tree.root.tag, "html");
        expect(tree.root.firstChild.tag, "ol");
        expect(tree.root.firstChild.firstChild.tag, "li");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "Hi, ");
        expect(tree.root.firstChild.firstChild.firstChild.sibling.tag, "b");
        expect(tree.root.firstChild.firstChild.firstChild.sibling.firstChild.tag, "Sup!");
        expect(tree.root.firstChild.sibling.tag, "p");
        expect(tree.root.firstChild.sibling.firstChild.tag, "Hey");
    }

    public static void testTreeAddTag() throws RuntimeException {
        System.out.println("TESTING Tree.addTag");

        Tree tree;

        // "hi, how are you?" => "<b>hi,</b> how are you?"
        tree = new Tree(new Scanner("hi, how are you?"));
        tree.build();
        tree.addTag("hi", "b");
        expect(tree.root.tag, "b");
        expect(tree.root.firstChild.tag, "hi,");
        expect(tree.root.sibling.tag, " how are you?");

        // "hi, how are you?" => "hi, how <b>are</b> you?"
        tree = new Tree(new Scanner("hi, how are you?"));
        tree.build();
        tree.addTag("are", "b");
        expect(tree.root.tag, "hi, how ");
        expect(tree.root.sibling.tag, "b");
        expect(tree.root.sibling.firstChild.tag, "are");
        expect(tree.root.sibling.sibling.tag, " you?");

        // "<html>hi, how are you?</html>" => "<html>hi, <b>how</b> are you?</html>"
        tree = new Tree(new Scanner("<html>\nhi, how are you?\n</html>"));
        tree.build();
        tree.addTag("how", "b");
        expect(tree.root.tag, "html");
        expect(tree.root.firstChild.tag, "hi, ");
        expect(tree.root.firstChild.sibling.tag, "b");
        expect(tree.root.firstChild.sibling.firstChild.tag, "how");
        expect(tree.root.firstChild.sibling.sibling.tag, " are you?");

        tree = new Tree(new Scanner("<html>\nhi, how are you?\n</html>"));
        tree.build();
        tree.addTag("you", "b");
        expect(tree.root.tag, "html");
        expect(tree.root.firstChild.tag, "hi, how are ");
        expect(tree.root.firstChild.sibling.tag, "b");
        expect(tree.root.firstChild.sibling.firstChild.tag, "you?");

        tree = new Tree(new Scanner("<html>\nDo you know html?\n</html>"));
        tree.build();
        tree.addTag("you", "b");
        expect(tree.root.tag, "html");
        expect(tree.root.firstChild.tag, "Do ");
        expect(tree.root.firstChild.sibling.tag, "b");
        expect(tree.root.firstChild.sibling.firstChild.tag, "you");
        expect(tree.root.firstChild.sibling.sibling.tag, " know html?");

        tree = new Tree(new Scanner("<html>\nDo you know html?\n</html>"));
        tree.build();
        tree.addTag("html", "b");
        expect(tree.root.tag, "html");
        expect(tree.root.firstChild.tag, "Do you know ");
        expect(tree.root.firstChild.sibling.tag, "b");
        expect(tree.root.firstChild.sibling.firstChild.tag, "html?");
    }

    public static void testTreeBoldRow() throws RuntimeException {
        System.out.println("Testing Tree.boldRow");

        Tree tree;

        tree = new Tree(new Scanner("<html>\n<table>\n<tr>\n<td>\nHi\n</tr>\n</td>\n</table>\n</html>"));
        tree.build();
        tree.boldRow(1);
        expect(tree.root.tag, "html");
        expect(tree.root.firstChild.tag, "table");
        expect(tree.root.firstChild.firstChild.tag, "tr");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "b");
        expect(tree.root.firstChild.firstChild.firstChild.firstChild.firstChild.tag, "Hi");

        tree = new Tree(new Scanner("<table>\n<tr>\n<td>\nHey\n</td>\n</tr>\n</table>"));
        tree.build();
        tree.boldRow(1);
        expect(tree.root.tag, "table");
        expect(tree.root.firstChild.tag, "tr");
        expect(tree.root.firstChild.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "b");
        expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "Hey");

        tree = new Tree(new Scanner("<table>\n<tr>\n<td>\nHey\n</td>\n<td>\nSup\n</td>\n</tr>\n</table>"));
        tree.build();
        tree.boldRow(1);
        expect(tree.root.tag, "table");
        expect(tree.root.firstChild.tag, "tr");
        expect(tree.root.firstChild.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "b");
        expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "Hey");
        expect(tree.root.firstChild.firstChild.sibling.tag, "td");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "b");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild.tag, "Sup");

        tree = new Tree(new Scanner("<table>\n<tr>\n<td>\nHey\n</td>\n<td>\nSup\n</td>\n</tr>\n<tr>\n<td>\nWaddup\n</td>\n</tr>\n</table>"));
        tree.build();
        tree.boldRow(2);
        expect(tree.root.tag, "table");
        expect(tree.root.firstChild.tag, "tr");
        expect(tree.root.firstChild.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "Hey");
        expect(tree.root.firstChild.firstChild.sibling.tag, "td");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "Sup");
        expect(tree.root.firstChild.sibling.tag, "tr");
        expect(tree.root.firstChild.sibling.firstChild.tag, "td");
        expect(tree.root.firstChild.sibling.firstChild.firstChild.tag, "b");
        expect(tree.root.firstChild.sibling.firstChild.firstChild.firstChild.tag, "Waddup");

        tree = new Tree(new Scanner("<html>\n<table>\n<tr>\n<td>\nHey\n</td>\n<td>\nSup\n</td>\n</tr>\n<tr>\n<td>\nWaddup\n</td>\n</tr>\n</table>\n</html>"));
        tree.build();
        tree.boldRow(2);
        expect(tree.root.firstChild.tag, "table");
        expect(tree.root.firstChild.firstChild.tag, "tr");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "Hey");
        expect(tree.root.firstChild.firstChild.firstChild.sibling.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.sibling.firstChild.tag, "Sup");
        expect(tree.root.firstChild.firstChild.sibling.tag, "tr");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild.tag, "b");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild.firstChild.tag, "Waddup");

        tree = new Tree(new Scanner("<html>\n<table>\n<tr>\n<td>\nHey\n</td>\n<td>\nSup\n</td>\n</tr>\n<tr>\n<td>\nWaddup\n</td>\n<td>\n<i>\nMohammad\n</i>\n</td>\n</tr>\n</table>\n</html>"));
        tree.build();
        tree.boldRow(2);
        expect(tree.root.firstChild.tag, "table");
        expect(tree.root.firstChild.firstChild.tag, "tr");
        expect(tree.root.firstChild.firstChild.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.firstChild.tag, "Hey");
        expect(tree.root.firstChild.firstChild.firstChild.sibling.tag, "td");
        expect(tree.root.firstChild.firstChild.firstChild.sibling.firstChild.tag, "Sup");
        expect(tree.root.firstChild.firstChild.sibling.tag, "tr");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "td");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild.tag, "b");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.firstChild.firstChild.tag, "Waddup");
        expect(tree.root.firstChild.firstChild.sibling.firstChild.tag, "td");
    }

    public static void testTree() throws RuntimeException {
        System.out.println("TESTING Tree");
        testTreeBuild();
        testTreeReplaceTag();
        testTreeRemoveTag();
        testTreeBoldRow();
        testTreeAddTag();

        System.out.println("DONE!");
    }

    public static void main(String[] args) throws RuntimeException {
        System.out.println("TESTING DOM Tree project");
        testTree();
    }
}

