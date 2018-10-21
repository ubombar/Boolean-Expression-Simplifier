import java.util.ArrayList;
import java.util.List;

/**
 * Expression class
 * Computer representation of exlpilcit boolean expression.
 * Y = f(A, B, C.. )
 * @author Ufuk Bombar
 * @version 1.0
 * 
 */
@SuppressWarnings("unused")
class Expression
{
    private final ExpressionNode root = new ExpressionNode(null, this, OperatorType.OUT);
    protected boolean inputTable[];
    public String parseText;

    private enum OperatorType
    {
        IN(1)
        {
            @Override
            public boolean execute(ExpressionNode node)
            {
                return node.expression.inputTable[getInputIndex()];
            }
        },

        OUT(1)
        {

        },
        AND(Integer.MAX_VALUE)
        {
            @Override
            public boolean execute(ExpressionNode node)
            {
                super.execute(node);
                boolean op = true;

                for (ExpressionNode chid : node.childs)
                    op &= chid.execute();

                return op;
            }
        },
        OR(Integer.MAX_VALUE)
        {
            @Override
            public boolean execute(ExpressionNode node)
            {
                super.execute(node);
                boolean op = false;
                
                for (ExpressionNode chid : node.childs)
                    op |= chid.execute();

                return op;
            }
        },
        NOT(1)
        {
            @Override
            public boolean execute(ExpressionNode node)
            {
                super.execute(node);
                assert node.childs.size() != 1 : "Not operation does not exactly one child expression.";

                return !node.childs.get(0).execute();
            }
        };

        private int inputIndex;
        private int maxSupChilds; // maximum supproted childs

        private OperatorType(int maxSupChilds)
        {
            
            this.maxSupChilds = maxSupChilds;
            this.inputIndex = -1;
        }

        public OperatorType setInputIndex(int inputIndex)
        {
            this.inputIndex = inputIndex;
            return this;
        }

        public int getInputIndex()
        {
            return this.inputIndex;
        }

        public boolean execute(ExpressionNode node)
        {
            assert node.childs.size() > 0 : "Operation node does not have a child expression.";

            return false;
        }
    }

    private class ExpressionNode
    {
        public Expression expression;
        public boolean hold;
        public ArrayList<ExpressionNode> childs;
        public ExpressionNode parent;
        public OperatorType type;

        public ExpressionNode(ExpressionNode parent, Expression expression, OperatorType type)
        {
            this.parent = parent;
            this.expression = expression;
            this.type = type;
            childs = new ArrayList<>();
        }

        public ExpressionNode addChild(OperatorType type)
        {
            assert !(type instanceof OperatorType.OUT) : "Tree cannot have more than one root node.";
            assert (childs.size() + 1 > this.type.maxSupChilds) : "This operator is not allowed to take more than" + (childs.size() + 1) + " childs.";
            
            // Create a new expression but also make link to Expression class.
            ExpressionNode eNode = new ExpressionNode(this, this.expression,  type);
            childs.add(eNode);
            return eNode;
        }

        public boolean execute()
        {
            // Executes the exression operation's execute funcion.
            // That function executes the child nodes execute function and makes the operation.
            return type.execute(this);
        }

        public void mutate(OperatorType type)
        {
            this.type = type;
        }
    }   

    public Expression(int inputSize, String parseString)
    {
        inputTable = new ExpressionNode[inputSize];
        this.parseText = parseString;
    }

    // TODO: Implement manipulation functions.
    // Ex; addChild, addVariable, getChild, deleteChild, setInputTable, execute, findSubtree, simplify


    public Expression simplify()
    {
        // Must do the trick!
        return this;
    }

}