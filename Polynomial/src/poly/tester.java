package poly;

public class tester {
    public static void main(String[] args) {
        Node p = new Node(1, 0, new Node(3, 1, new Node(2, 2, null))), n = new Node(3, 0, new Node(2, 1, new Node(3, 2, null)));
        System.out.println(Polynomial.toString(n) + "\n" + Polynomial.toString(p) + "\n\n");

        System.out.println(Polynomial.toString(Polynomial.multiply(n, p)));
        // System.out.println(Polynomial.toString(Polynomial.multiply(n, p)));

        /*
        Node n1 = new Node(3, 2, new Node(4, 3, new Node(5, 4, new Node(6, 5, null)))), n2 = new Node(3, 4, null);

        System.out.println(Polynomial.toString(n1));
        System.out.println(Polynomial.toString(n2) + "\n");

        Node x = Polynomial.distribute(n1, n2);
        System.out.println("\n" + Polynomial.toString(x));
        */
    }
}
