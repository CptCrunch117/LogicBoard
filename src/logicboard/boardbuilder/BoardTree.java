package logicboard.boardbuilder;

import logicboard.Exceptions.GateIDExistsException;
import logicboard.LogicBoard;
import logicboard.boardbuilder.exceptions.NoInputExistsException;
import logicboard.boardbuilder.exceptions.NoSuchLogicOperationException;
import sun.rmi.runtime.Log;

import java.util.ArrayList;

/**
 * Created by CptAmerica on 7/1/15.
 */
public class BoardTree<T> extends LinkedBinaryTree<String> {

    private LogicBoard board;
    private ArrayList<String> expressions;
    private int andTally;
    private int orTally;
    private int notTally;

    /**
     * Constructor Sets up a LinkedBinaryTree with String type and
     * Instantiates a new LogicBoard based on argruments passed into
     * constuctors parameters.
     * @param inputs String array of inputs for the logic board.
     * @param name name of logicBoard to generate from expressions.
     */
    public BoardTree(String[] inputs, String name){
        super();
        this.board = new LogicBoard(inputs, name);
        andTally = 0;
        orTally = 0;
        notTally = 0;

        //Holds all expressions for board to evaluate and build.
        //NOTE: All expressions can only use board inputs, no gates
        //      can be used, this will cause an InValidExpressionBuildException.
        this.expressions = new ArrayList<String>();
    }//END OF BOARD-TREE CONSTRUCTOR

    public BoardTree(LogicBoard bd){
        this.board = bd;
        this.expressions = new ArrayList<String>();

    }

    /**
     * Calls the recursive method evaluateNode, which builds board based
     * on tree. Returns the name(gateID) of the last gate which is the output
     * of the expression.
     */
    public void evaluateTree(){
        evaluateNode(this.root);
    }

    /**
     * Reursive method, returns name of gate built, otherwise traverses
     * tree till next gate can be built.
     * @param root the BinaryNode to traverse.
     * @return the name of the gate built.
     */
    public String evaluateNode(BinaryNode<String> root){
        String result = null, in1, in2;
        String gate = "";
        if(root == null){
            result = null;
        }
        else if(root != null){
            if(!(root.getElement().equalsIgnoreCase("*") || root.getElement().equalsIgnoreCase("+") || root.getElement().equalsIgnoreCase("'"))){
                result = root.getElement();
            }
        }
        if (root != null){
            boolean isOperator = false;
            if(root.getElement().equals("+")){
                gate = LogicBoard.OR;
                isOperator = true;
            }
            else if(root.getElement().equals("*")){
                gate = LogicBoard.AND;
                isOperator = true;
            }
            else if(root.getElement().equals("'")){
                gate = LogicBoard.NOT;
                isOperator = true;
            }

            if(isOperator){
                in1 = evaluateNode(root.getLeftChild());
                in2 = evaluateNode(root.getRightChild());
                result = buildGate(gate, in1,in2);
            }
        }

        return result;
    }

    /**
     * Uses the LogicBoard function to build gates based on arguments
     * passed in the parameter.
     * @param gate the gate type to build. (1 of the 3 basic types: *,+,')
     * @param in1 the name of the first input gate
     * @param in2 the name of the second input gate.
     * @return the name of the successfully built gate, otherwise method throws error.
     */
    private String buildGate(String gate, String in1, String in2){
        String gateID = null;
        if(gate.equalsIgnoreCase(LogicBoard.AND)){
            gateID = "("+in1+gate+in2+""+andTally+")";
            andTally++;
            try {
                this.board.addGate(LogicBoard.AND, gateID, in1, in2);
            }catch(GateIDExistsException e){

            }
        }
        else if(gate.equalsIgnoreCase(LogicBoard.OR)){
            gateID = "("+in1+gate+in2+""+orTally+")";
            orTally++;
            try {
                this.board.addGate(LogicBoard.OR, gateID, in1, in2);
            }catch(GateIDExistsException e){

            }
        }
        else if(gate.equalsIgnoreCase(LogicBoard.NOT)){
            if(in1 == null){
                if(in2 != null){
                    gateID = "("+in2+gate+""+notTally+")";
                    notTally++;
                    try {
                        this.board.addGate(LogicBoard.NOT, gateID, in2, in1);
                    }catch(GateIDExistsException e){

                    }

                }
                else{
                    throw new NoInputExistsException(gate);
                }
            }
            else {
                gateID = "(" + in1 + gate + ")";
                this.board.addGate(LogicBoard.NOT, gateID, in1, in2);
            }
        }
        else{
            throw new NoSuchLogicOperationException(gate);
        }


        return gateID;
    }

    /**
     * Returns the height of the tree.
     * @return the integer value of the height of the binary tree.
     */

    public int getHeight(){

        return heightOfBinaryTree(this.root);
    }

    /**
     * This method computes the height of the binary tree.
     * @param node The node to start at for determining the height from this point down.
     * @return the integer value of the height of the tree.
     */
    private int heightOfBinaryTree(BinaryNode<String> node) {
        if (node == null)
        {
            return 0;
        }
        else
        {
            return 1 +
                    Math.max(heightOfBinaryTree(node.getLeftChild()),
                            heightOfBinaryTree(node.getRightChild()));
        }
    }


    public LogicBoard getBoard(){
        return this.board;
    }

    public void setBoard(LogicBoard bd){
        this.board = bd;
    }


    public void buildTree(String exprs){
        BoardTree<String> tree = new BoardTree<String>(this.board);
        BinaryNode<String> temp = tree.root;


        for(String s : exprs.split(" ")){
            if(s.equalsIgnoreCase("(")){
                if(temp.getLeftChild() == null){
                    //Instantiate right child, pass in reference to it's parent
                    temp.setLeftChild(new BinaryNode(temp));
                    temp = temp.getLeftChild();
                }
                else{
                    //Instantiate right child, pass in reference to it's parent
                    temp.setRightChild(new BinaryNode(temp));
                    temp = temp.getRightChild();
                }
            }
            else if(s.equalsIgnoreCase(")")){
                temp = temp.getParent();
            }

            //IF AN OPERATOR:    (AND == *, OR == +, NOT == ')
            else if(s.equalsIgnoreCase("+")){
                temp.setElement("+");
            }
            else if(s.equalsIgnoreCase("*")){
                temp.setElement("*");
            }
            else if(s.equalsIgnoreCase("'")){
                temp.setElement("'");
            }

            //IF GateID
            else{
                if(temp.getLeftChild() == null){
                    temp.setLeftChild(new BinaryNode(temp));
                    temp.getLeftChild().setElement(s);
                }
                else{
                    if(temp.getRightChild() == null){
                        temp.setLeftChild(new BinaryNode(temp));
                        temp.getRightChild().setElement(s);
                    }
                }
            }
        }


    }




public void printLogicBoard(){
    this.board.printGates();
}


}//END OF CLASS DEFINITION

