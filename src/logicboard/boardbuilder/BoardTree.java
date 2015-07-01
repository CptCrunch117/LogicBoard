package logicboard.boardbuilder;

import logicboard.LogicBoard;
import sun.rmi.runtime.Log;

import java.util.ArrayList;

/**
 * Created by CptAmerica on 7/1/15.
 */
public class BoardTree<T> extends LinkedBinaryTree<String> {

    private LogicBoard board;
    private ArrayList<String> expressions;

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

        //Holds all expressions for board to evaluate and build.
        //NOTE: All expressions can only use board inputs, no gates
        //      can be used, this will cause an InValidExpressionBuildException.
        this.expressions = new ArrayList<String>();
    }//END OF BOARD-TREE CONSTRUCTOR


    /**
     * Calls the recursive method evaluateNode, which builds board based
     * on tree. Returns the name(gateID) of the last gate which is the output
     * of the expression.
     */
    public String evaluateTree(){
        String expressionName = evaluateNode(root);
        return expressionName;
    }

    /**
     * Reursive method, returns name of gate built, otherwise traverses
     * tree till next gate can be built.
     * @param root the BinaryNode to traverse.
     * @return the name of the gate built.
     */
    public String evaluateNode(BinaryNode root){
        String result = null, in1, in2;
        String gate = "";
        if(root == null){
            result = null;
        }
        else{
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

        return result;//CHANGE
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

        }
        else if(gate.equalsIgnoreCase(LogicBoard.OR)){

        }
        else if(gate.equalsIgnoreCase(LogicBoard.NOT)){

        }


        return gateID;//CHANGE
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









}//END OF CLASS DEFINITION

