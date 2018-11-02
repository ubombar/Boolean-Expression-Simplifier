import ExpressionTree.Node;
import ExpressionTree.Operator;

/**
 * Main class
 * @author Ufuk Bombar
 * @version 1.0
 * Takes an expression from the user.
 * Parses it to boolean expression tree.
 * Simplifies the expression.
 */
class Main
{
    public static void main(String[] args)
    {
        boolean[] table = new boolean[] {true, true, true};
        ExpressionTree tree = new ExpressionTree(3);

        System.out.println("Expression: Y = A and (B or not C) and W is the compiler version");
        System.out.println();
        System.out.println("A  B  C  |  Y  W");
        System.out.println("----------------");
        for (int i = 0; i < 8; i++)
        {
            boolean[] tab = {(i) % 2 != 0, (i / 2) % 2 != 0, (i / 4) % 2 != 0};
            boolean bb = tab[2] && (tab[1] || !tab[0]);

            System.out.println((tab[2]?1:0) + "  " + (tab[1]?1:0) + "  " + (tab[0]?1:0) + "  |  " + (tree.execute(tab)?1:0) + "  " + (bb?1:0));
        }
    }
}