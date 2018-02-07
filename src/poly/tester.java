package poly;

public class tester {
    public static void main(String[] args) {
        Node n = new Node(0, 1, new Node(0, 3, new Node(0, 4, new Node(1, 6, null))));
        System.out.println(Polynomial.toString(n));

        /*
        Node n1 = new Node(3, 2, new Node(4, 3, new Node(5, 4, new Node(6, 5, null)))), n2 = new Node(3, 4, null);

        System.out.println(Polynomial.toString(n1));
        System.out.println(Polynomial.toString(n2) + "\n");

        Node x = Polynomial.distribute(n1, n2);
        System.out.println("\n" + Polynomial.toString(x));
        */
    }
}
