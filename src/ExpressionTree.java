import java.util.ArrayList;

/**
 * ExpressionTree class
 * @author Ufuk Bombar
 * @version 1.0
 * Representatino of an boolean expression.
 */
class ExpressionTree
{
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
     */
    public ExpressionTree(int numberOfVariables)
    {
        this.numberOfVariables = numberOfVariables;

        // Build node here!
        // TODO:

        Node A = new Node(Operator.INPUT).setInputIndex(0);
        Node B = new Node(Operator.INPUT).setInputIndex(1);
        Node AND = new Node(Operator.AND);

        AND.add(A).add(B);
        
        ROOT.add(AND);
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
             * Executes all the child nodes to get the value.
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
    
}