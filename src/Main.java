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
        boolean[] table = new boolean[] {true, true};
        ExpressionTree tree = new ExpressionTree(2);

        System.out.println("Expression: Y = A and B");
        System.out.println("(A, B) = (1, 1), Y = " + tree.execute(table));
    }
}