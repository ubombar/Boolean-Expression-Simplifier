import java.util.ArrayList;

/**
 * ExpressionTree class
 * @author Ufuk Bombar
 * @version 1.0
 * Takes an expression from the user.
 * Parses it to boolean expression tree.
 * Simplifies the expression.
 */
class ExpressionTree
{
    private final Node ROOT = new Node();

    private class Node
    {
        public Node parent;
        public ArrayList<Node> childs;

        public Node(Node parent)
        {
            this.parent = parent;
            childs = new ArrayList<>();
        }
    }
}