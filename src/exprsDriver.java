import logicboard.LogicBoard;
import logicboard.boardbuilder.BoardTreeBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by CptAmerica on 7/2/15.
 */
public class exprsDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        //In 4 lines a boolean expression can be broken down, and auto build a logic board that satisfies the
        // expression!

            //Example 1: XOR gate
            String xor_expression = "( ( A ' ) * B ) + ( A * ( B ' ) )";
            String[] inputs = {"A", "B", "C"};
            BoardTreeBuilder builder = new BoardTreeBuilder();
            LogicBoard xor = builder.buildTree(xor_expression, inputs, "XOR");

		//Print results of xor LogicBoard
		System.out.println();
		System.out.println("Gate relationships/connections of xor LogicBoard:");
		xor.printGates();

		System.out.println();
		System.out.println("Truth table of xor LogicBoard:");
		System.out.println("------------------------------");
		System.out.println();
		System.out.println(xor.generateTruthTable());

        //Adding another expression to the same board!
        //String expression = "( ( A * B ) + ( A * B ) ) * C";
		String expression = "C '";
        LogicBoard multExprsBoard = builder.buildTree(expression, xor);



        //Print results of multExprsBoard LogicBoard
        System.out.println();
        System.out.println("Gate relationships/connections of multExprsBoard LogicBoard:");
        multExprsBoard.printGates();

        System.out.println();
        System.out.println("Truth table of multExprsBoard LogicBoard:");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println(multExprsBoard.generateTruthTable());


    }

}

/* Output of this class when ran:

Gate relationships/connections of xor LogicBoard:
------------------------------------------------
Gate A:
	outputTo: (Anot)

	outputTo: (Aand(Bnot)1)

	outputTo: (AandB0)

	outputTo: (AandB1)

Gate B:
	outputTo: ((Anot)andB0)

	outputTo: (Bnot)

	outputTo: (AandB0)

	outputTo: (AandB1)

Gate C:
	outputTo: (((AandB0)or(AandB1)0)andC2)

Gate (Anot):
	input1: A
	outputTo: ((Anot)andB0)

Gate ((Anot)andB0):
	input1: (Anot)
	input2: B
	outputTo: (((Anot)andB0)or(Aand(Bnot)1)0)

Gate (Bnot):
	input1: B
	outputTo: (Aand(Bnot)1)

Gate (Aand(Bnot)1):
	input1: A
	input2: (Bnot)
	outputTo: (((Anot)andB0)or(Aand(Bnot)1)0)

Gate (((Anot)andB0)or(Aand(Bnot)1)0):
	input1: ((Anot)andB0)
	input2: (Aand(Bnot)1)
Gate (AandB0):
	input1: A
	input2: B
	outputTo: ((AandB0)or(AandB1)0)

Gate (AandB1):
	input1: A
	input2: B
	outputTo: ((AandB0)or(AandB1)0)

Gate ((AandB0)or(AandB1)0):
	input1: (AandB0)
	input2: (AandB1)
	outputTo: (((AandB0)or(AandB1)0)andC2)

Gate (((AandB0)or(AandB1)0)andC2):
	input1: ((AandB0)or(AandB1)0)
	input2: C
------------------------------------------------

Truth table of xor LogicBoard:
------------------------------

A	B	C	|	(((Anot)andB0)or(Aand(Bnot)1)0)	(((AandB0)or(AandB1)0)andC2)
0	0	0		0										0
0	0	1		0										0
0	1	0		1										0
0	1	1		1										0
1	0	0		1										0
1	0	1		1										0
1	1	0		0										0
1	1	1		0										1

Gate relationships/connections of multExprsBoard LogicBoard:
------------------------------------------------
Gate A:
	outputTo: (Anot)

	outputTo: (Aand(Bnot)1)

	outputTo: (AandB0)

	outputTo: (AandB1)

Gate B:
	outputTo: ((Anot)andB0)

	outputTo: (Bnot)

	outputTo: (AandB0)

	outputTo: (AandB1)

Gate C:
	outputTo: (((AandB0)or(AandB1)0)andC2)

Gate (Anot):
	input1: A
	outputTo: ((Anot)andB0)

Gate ((Anot)andB0):
	input1: (Anot)
	input2: B
	outputTo: (((Anot)andB0)or(Aand(Bnot)1)0)

Gate (Bnot):
	input1: B
	outputTo: (Aand(Bnot)1)

Gate (Aand(Bnot)1):
	input1: A
	input2: (Bnot)
	outputTo: (((Anot)andB0)or(Aand(Bnot)1)0)

Gate (((Anot)andB0)or(Aand(Bnot)1)0):
	input1: ((Anot)andB0)
	input2: (Aand(Bnot)1)
Gate (AandB0):
	input1: A
	input2: B
	outputTo: ((AandB0)or(AandB1)0)

Gate (AandB1):
	input1: A
	input2: B
	outputTo: ((AandB0)or(AandB1)0)

Gate ((AandB0)or(AandB1)0):
	input1: (AandB0)
	input2: (AandB1)
	outputTo: (((AandB0)or(AandB1)0)andC2)

Gate (((AandB0)or(AandB1)0)andC2):
	input1: ((AandB0)or(AandB1)0)
	input2: C
------------------------------------------------

Truth table of multExprsBoard LogicBoard:
-----------------------------------------

A	B	C	|	(((Anot)andB0)or(Aand(Bnot)1)0)	(((AandB0)or(AandB1)0)andC2)
0	0	0		0										0
0	0	1		0										0
0	1	0		1										0
0	1	1		1										0
1	0	0		1										0
1	0	1		1										0
1	1	0		0										0
1	1	1		0										1

Process finished with exit code 0
*/