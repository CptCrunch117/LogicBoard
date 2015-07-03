import logicboard.LogicBoard;
import logicboard.boardbuilder.BoardTreeBuilder;

/**
 * Created by CptAmerica on 7/2/15.
 */
public class exprsDriver {

    public static void main(String[] args){


        //In 4 lines a boolean expression can be broken down, and auto build a logic board that satisfies the
        // expression!

            //Example 1: XOR gate
            String xor_expression = "( ( A ' ) * B ) + ( A * ( B ' ) )";
            String[] inputs = {"A", "B", "C"};
            BoardTreeBuilder builder = new BoardTreeBuilder();
            LogicBoard xor = builder.buildTree(xor_expression, inputs, "XOR");


        //Adding another expression to the same board!
        String expression = "( ( A * B ) + ( A * B ) ) * C";
        LogicBoard multExprsBoard = builder.buildTree(expression, xor);

        //Print results of xor LogicBoard
        xor.printGates();
        System.out.println(xor.generateTruthTable());

        //Print results of multExprsBoard LogicBoard
        multExprsBoard.printGates();
        System.out.println(multExprsBoard.generateTruthTable());
    }

}
