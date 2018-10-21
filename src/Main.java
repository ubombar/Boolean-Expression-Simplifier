
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
        System.out.println("Hello World!");

        // Usage:

        String expression = "not (not A and not B)";
        Expression e = BEParser.parse(expression).simplify();

    }
}