import java.util.ArrayList;
import java.util.HashMap;

/**
 * ExpressionTree class
 * @author Ufuk Bombar
 * @version 1.0
 * Representatino of an boolean expression.
 */
class ExpressionTree
{
    // Cost of nodes in order of {'AND', 'OR', 'NOT', 'TRUE', 'FALSE'}
    private static final int[] COST = {1, 1, 1, 1, 1};
    private final Node ROOT = new Node(Operator.OUTPUT)
    {
        /**
         * Added if statement prevents exception on an unconstructed tree.
         */
        @Override
        public boolean execute(boolean[] inputTable)
        {
            if (childs.size() == 0)
                return false;

            return operator.execute(inputTable, this);
        }
    };
    private int numberOfVariables;

    /**
     * Creates an expression tree. In start it is empty. If no child
     * is added execution would result as false.
     * Default expression is Y = 0
     * 
     * @param numberOfVariables
     * @param parse
     */
    public ExpressionTree(int numberOfVariables, String parse)
    {
        this.numberOfVariables = numberOfVariables;

        // Build node here!
        // TODO:

        Node A = new Node(Operator.INPUT).setInputIndex(2);
        Node B = new Node(Operator.INPUT).setInputIndex(1);
        Node C = new Node(Operator.INPUT).setInputIndex(0);
        Node AND = new Node(Operator.AND);
        Node OR = new Node(Operator.OR);
        Node NOT = new Node(Operator.NOT);

        
        
        ROOT.add(AND.add(A).add(OR.add(B).add(NOT.add(C))));
    }




    /**
     * Parses the expression in a recursive way.
     * @param str
     * @param node
     * @param pd
     */
    public Node parse(StringBuilder sb, Node parent, ParserData pd)
    {
        Node tmp = new Node(null, null);

        // Test for basic base cases
        if (sb == null || node == null || pd == null)
            throw Exception("Could not parse!");
        
        String tmpStr = sb.toString();
        if (tmpStr.startsWith(pd.LEFTP) && tmpStr.endsWith(pd.RIGHTP)) // Base case 1: Evaluate paranthesis first.
        {
            // In that case we first evaluate inside pharenthesis.
            int leftIndex = 0, rightIndex = 0;

            for (int i = 0; i < sb.length(); i++)
                if (sb.charAt(i) == pd.LEFTP)
                {
                    leftIndex = i;
                    break;
                }
            
            for (int i = sb.length() - 1; i >= 0; i--)
                if (sb.charAt(i) == pd.RIGHTP)
                {
                    rightIndex = i;
                    break;
                }

            
            tmp.add(parse(new StringBuilder(tmpStr.substring(leftIndex, rightIndex)), parent, pd));
        }
        

        return tmp;
    }
    /* There are problems!!!
    private void parse(String str, Node current, ParserData pd)
    {
        // First checks base points
        if (str == null || str.length() == 0)
            return;

        if (str.length() % 2 == 0) // We want us, string to have odd length
            str.concat(" ");

        // Half point of the string
        int half = 1 + (str.length() / 2);
        
        // Length - 1 of the string
        int full = half * 2 - 2;
        

        // Pharenthesis count of left side
        int lP = 0;

        // Pharenthesis count of right side
        int rP = 0;

        for (int i = 0; i <= half; i++)
        {
            // l := left index
            int l = i;

            // r := right index
            int r = full - i;

            // cl := char at left index
            char cl = str.charAt(i);

            // cr := char at right index
            char cr = str.charAt(r);

            if (cl == pd.LEFTP)
                lP++;
            else if (cl == pd.RIGHTP)
                lP--;

            if (cr == pd.LEFTP)
                rP--;
            else if (cr == pd.RIGHTP)
                rP++;
            
            if (lP < 0 || rP < 0) // Then there is a mistake on string. Maybe throw an error??
                return;
            
            

        }

    }
    */


    /**
     * Responsible for encapsulating parsing data.
     */
    private class ParserData
    {
        public final String AND = "&";
        public final String OR = "|";
        public final String NOT = "~";
        public final String LEFTP = "(";
        public final String RIGHTP = ")";

        public HashMap<String, Integer> inputs = new HashMap();
    }

    private class Node
    {
        public Node parent;
        public ArrayList<Node> childs;
        public Operator operator;
        public int inputIndex = -1;

        /**
         * Super constructor.
         * @param parent
         * @param operator
         */
        public Node(Node parent, Operator operator)
        {
            this.operator = operator;
            this.parent = parent;
            childs = new ArrayList<>();
        }

        /**
         * Minimal constructor.
         * @param operator
         */
        public Node(Operator operator)
        {
            this(null, operator);
        }

        /**
         * Every node is a potential input node. If the operator is changed to
         * a INPUT operator and inputIndex is changed to a positive integer in
         * integral [0, inputSize) node can be used a sa n input node.
         * 
         * @param index
         * @return the node itself for one line coding.
         */
        public Node setInputIndex(int index)
        {
            this.inputIndex = index;
            return this;
        }

        /**
         * Executes the node's operator. Operator executes all the child nodes
         * to obtain a value.
         * @return boolean result
         */
        public boolean execute(boolean[] inputTable)
        {
            return operator.execute(inputTable, this);
        }

        /**
         * Adds a child.
         * @param node
         */
        public Node add(Node node)
        {
            assert operator.isChildCountOkay(childs.size() + 1) : "Operator does not supports " + (childs.size() + 1) + " child/s.";
            node.parent = this;
            childs.add(node);
            return this;
        }

        /**
         * Calculates the cost of each child note and itself.
         */
        public int cost(int[] cost)
        {
            int costs = 0;

            if (operator == ExpressionTree.Operator.AND)
                costs += cost[0];
            else if (operator == ExpressionTree.Operator.OR)
                costs += cost[1];
            else if (operator == ExpressionTree.Operator.NOT)
                costs += cost[2];
            else if (operator == ExpressionTree.Operator.TRUE)
                costs += cost[3];
            else if (operator == ExpressionTree.Operator.FALSE)
                costs += cost[4];

            for (Node child : childs)
                costs += child.cost(cost);

            return costs;
        }

        /**
         * Removes the child.
         * @param index
         */
        public void remove(int index)
        {
            childs.get(index).parent = null;
            childs.remove(index);
        }
    }

    /**
     * Representation of an operator.
     * @author Ufuk Bombar
     */
    private enum Operator
    {
        /**
         * INPUT is gathered from the input table. It is also a
         * leaf so it cannot contain a child node.
         */
        INPUT()
        {
            /**
             * Plugs in the value of the variable from inputTable.
             */
            @Override
            boolean execute(boolean[] inputs, Node curNode)
            {
                return inputs[curNode.inputIndex];
            }

            /**
             * This operator cannot have a child.
             */
            @Override
            boolean isChildCountOkay(int count) 
            {
                return count == 0;
            }
        },
        /**
         * OUTPUT is the operator type of the ROOT node. Only usage
         * is as an operator of the ROOT node.
         */
        OUTPUT()
        {
            /**
             * Executes the child node to get the value.
             */
            @Override
            boolean execute(boolean[] inputs, Node curNode)
            {
                return curNode.childs.get(0).execute(inputs);
            }

            @Override
            boolean isChildCountOkay(int count) 
            {
                return count == 1;
            }
        },
        /**
         * NOT takes the complement of the value.
         */
        NOT()
        {
            /**
             * Executes the child node to get the value.
             */
            @Override
            boolean execute(boolean[] inputs, Node curNode)
            {
                
                return !curNode.childs.get(0).execute(inputs);
            }

            @Override
            boolean isChildCountOkay(int count) 
            {
                return count == 1;
            }
        },
        /**
         * AND operator, cannot have less than 2 childs.
         */
        AND()
        {
            /**
             * For the result to be true, all the values of childs should be true.
             */
            @Override
            boolean execute(boolean[] inputs, Node curNode)
            {
                for (Node child : curNode.childs)
                    if (!child.execute(inputs))
                        return false;
                return true;
            }

            @Override
            boolean isChildCountOkay(int count) 
            {
                return count > 1;
            }
        },
        /**
         * OR operator, cannot have less than 2 childs.
         */
        OR()
        {
            /**
             * For the result to be true, all the values of childs should be true.
             */
            @Override
            boolean execute(boolean[] inputs, Node curNode)
            {
                for (Node child : curNode.childs)
                    if (child.execute(inputs))
                        return true;
                return false;
            }

            @Override
            boolean isChildCountOkay(int count) 
            {
                return count > 1;
            }
        },
        /**
         * Constant FALSE, Cannot have a child.
         */
        FALSE()
        {
            @Override
            boolean execute(boolean[] inputs, Node curNode)
            {
                return false;
            }

            @Override
            boolean isChildCountOkay(int count) 
            {
                return count == 0;
            }
        },
        /**
         * Constant TRUE, Cannot have a child.
         */
        TRUE()
        {
            @Override
            boolean execute(boolean[] inputs, Node curNode)
            {
                return true;
            }

            @Override
            boolean isChildCountOkay(int count) 
            {
                return count == 0;
            }
        };

        /**
         * Executes all the sub trees to find the value of itself.
         * 
         * @return boolean
         */
        abstract boolean execute(boolean[] inputs, Node curNode);

        /**
         * If an operator requires some number of childs to operate
         * and if that number is satisfied, returns true else false.
         * 
         * @return true if the child count is adaquete
         */
        abstract boolean isChildCountOkay(int count);
    }

    // Expression manipulation methods.

    // Chapter 1: Building Methods

    // Chapter 3: Execution Methods

    /**
     * Ow ow ow easy there, not implemented yet.
     * Simplifys the expression.
     */
    public void simplify(){}

    public boolean execute(boolean[] inputTable)
    {
        assert inputTable.length == this.numberOfVariables : "There more/less variables than it specified before.";

        return ROOT.execute(inputTable);            
    }

    /**
     * Finds the cost of the tree. Costs can be specified by the int array argument.
     * @param cost
     * @return total cost
     */
    public int cost(int[] cost)
    {
        return ROOT.cost(cost);
    }

    /**
     * Default version of {@link cost(int[] cost)} function, all default costs per operators are initalized.
     * AND, OR, NOT, TRUE, FALSE = 1, 1, 1, 1, 1
     * @return total cost od the tree
     */
    public int cost()
    {
        return ROOT.cost(COST);
    }
    
}