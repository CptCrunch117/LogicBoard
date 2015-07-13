import logicboard.LogicBoard;
import logicboard.boardbuilder.BoardTreeBuilder;
import logicboard.gates.Gate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by CptAmerica on 7/6/15.
 */
public class TwoBitCompExample {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //BUILD LOGICBOARD PHASE

        BoardTreeBuilder builder = new BoardTreeBuilder();
        //Build main logic for 2BitComparator
        String[] inp = {"A1", "A0", "B1", "B0"};
        BoardTreeBuilder build = new BoardTreeBuilder();
        //A>B
        LogicBoard jonnyHWK = build.buildTree("( ( A1 * ( B1 ' ) ) + ( ( A0 * ( B1 ' ) ) * ( B0 ' ) ) ) + ( ( A1 * A0 ) * ( B0 ' ) )", inp, "2BitComp");
        //A<B
        jonnyHWK = builder.buildTree("( ( ( A1 ' ) * B1 ) + ( ( ( A1 ' ) * ( A0 ' ) ) * B0 ) ) + ( ( ( A0 ' ) * B1 ) * B0 )", jonnyHWK);
        //-----------------------------------------------------------

        //Grab an XOR gate from library

        LogicBoard xorGate = LogicBoard.getBoard("","XOR");
        //------------------------------------------------------
        //Add XOR gate
        String[] xorInputs = {jonnyHWK.getOpenOutputs().get(0).getGateID(),jonnyHWK.getOpenOutputs().get(1).getGateID()};
        jonnyHWK.addGate(xorGate, "XOR1", xorInputs);
        //-----------------------------------------------------------
        //Negate XOR logic
        jonnyHWK.addGate(LogicBoard.NOT, "XNOR", "XOR_AB'+A'B", null);
        //-----------------------------------------------------------


        //FINALIZE LOGICBOARD PHASE
        //Set System Outputs
        ArrayList<String> set = new ArrayList<>();
        set.add("(((A1and(B1not))or((A0and(B1not))and(B0not)))or((A1andA0)and(B0not)))");
        set.add("XNOR");
        set.add("((((A1not)andB1)or(((A1not)and(A0not))andB0))or(((A0not)andB1)andB0))");
        jonnyHWK.setSystemOutputs(set);
        //-----------------------------------------------------------
        //Rename System Outputs
        ArrayList<String> rename = new ArrayList<>();
        rename.add("A>B");
        rename.add("A=B");
        rename.add("A<B");
        jonnyHWK.renameSystemOutput(jonnyHWK.getSystemOutputs().get(0).getGateID(),"A>B");
        jonnyHWK.renameSystemOutput(jonnyHWK.getSystemOutputs().get(1).getGateID(),"A=B");
        jonnyHWK.renameSystemOutput(jonnyHWK.getSystemOutputs().get(2).getGateID(),"A<B");
        //-----------------------------------------------------------
		for(String s : jonnyHWK.getExpression()){
			System.out.println(s);
		}

        //Print Stuffs Phase
        System.out.println();
        System.out.println("Gate Relations:");
        jonnyHWK.printGates();
        System.out.println("List of all gates in LogicBoard:");
        System.out.println("--------------------------------");
        for(Gate g : jonnyHWK.getAllGates()){
            System.out.println(g.getGateID());
        }
        System.out.println();
        System.out.println("Truth table of 2BitComp LogicBoard:");
        System.out.println("-----------------------------------");
        System.out.println(jonnyHWK.generateTruthTable());

        String[] input2 = {"A","B","C","D","E","F","G","H"};
        LogicBoard compTest = new LogicBoard(input2,"compTest");
        compTest.addGate(LogicBoard.AND,"AB","A","B");
        compTest.addGate(LogicBoard.AND,"CD","C","D");
        compTest.addGate(LogicBoard.AND,"EF","E","F");
        compTest.addGate(LogicBoard.AND,"GH","G","H");
        String[] in3 = {"AB","CD","EF","GH"};
        compTest.addGate(jonnyHWK,"comparator",in3);
        ArrayList<String> set2 = new ArrayList<>();
        set2.add("2BitComp_A>B");
        set2.add("2BitComp_A=B");
        set2.add("2BitComp_A<B");
        compTest.setSystemOutputs(set2);
        for(Gate g : compTest.getAllGates()){
                System.out.println(g.getGateID());
        }
        System.out.println(compTest.generateTruthTable());
		for(String s : compTest.getExpression()){
			System.out.println(s);
		}
    }



}
/*

When program is ran a 2 Bit comparator is generated.

These Exception messages tell us that these gates already exist, in this scenario
instead of making another gate with the same logic but slightly different name
it just reuses that gate which already exists, this saves work for the program
and gates used in the test simulator, its a win win!

The gateID: (A1not) already exists in the circuit-board.
The gateID: (B1not) already exists in the circuit-board.
The gateID: (A0not) already exists in the circuit-board.
The gateID: (B0not) already exists in the circuit-board.


Again when this program is ran, specifically this class of course we want a
simple way to see the relations, at least for this testing version right?
Well below we can see all the relations of all gates that exist in this LogicBoard Object.

Gate Relations:
------------------------------------------------
Gate A1:
	outputTo: (A1not)

	outputTo: ((A0not)andA1)

	outputTo: ((B0not)andA1)

Gate A0:
	outputTo: (A0and(A1not))

	outputTo: (A0andB0)

	outputTo: (A0not)

Gate B1:
	outputTo: (B1not)

	outputTo: (((A0not)and(B0not))andB1)

	outputTo: (((B0not)andA1)andB1)

Gate B0:
	outputTo: (B0and(A1not))

	outputTo: (A0andB0)

	outputTo: (B0not)

Gate (A1not):
	input1: A1
	outputTo: (A0and(A1not))

	outputTo: (B0and(A1not))

Gate (A0and(A1not)):
	input1: A0
	input2: (A1not)
	outputTo: ((A0and(A1not))or((B0and(A1not))and(B1not)))

Gate (B0and(A1not)):
	input1: B0
	input2: (A1not)
	outputTo: ((B0and(A1not))and(B1not))

Gate (B1not):
	input1: B1
	outputTo: ((B0and(A1not))and(B1not))

	outputTo: ((A0andB0)and(B1not))

Gate ((B0and(A1not))and(B1not)):
	input1: (B0and(A1not))
	input2: (B1not)
	outputTo: ((A0and(A1not))or((B0and(A1not))and(B1not)))

Gate ((A0and(A1not))or((B0and(A1not))and(B1not))):
	input1: (A0and(A1not))
	input2: ((B0and(A1not))and(B1not))
	outputTo: A<B

Gate (A0andB0):
	input1: A0
	input2: B0
	outputTo: ((A0andB0)and(B1not))

Gate ((A0andB0)and(B1not)):
	input1: (A0andB0)
	input2: (B1not)
	outputTo: A<B

Gate A<B:
	input1: ((A0and(A1not))or((B0and(A1not))and(B1not)))
	input2: ((A0andB0)and(B1not))
	outputTo: A

	outputTo: A

Gate (A0not):
	input1: A0
	outputTo: ((A0not)andA1)

	outputTo: ((A0not)and(B0not))

Gate ((A0not)andA1):
	input1: (A0not)
	input2: A1
	outputTo: (((A0not)andA1)or(((A0not)and(B0not))andB1))

Gate (B0not):
	input1: B0
	outputTo: ((A0not)and(B0not))

	outputTo: ((B0not)andA1)

Gate ((A0not)and(B0not)):
	input1: (A0not)
	input2: (B0not)
	outputTo: (((A0not)and(B0not))andB1)

Gate (((A0not)and(B0not))andB1):
	input1: ((A0not)and(B0not))
	input2: B1
	outputTo: (((A0not)andA1)or(((A0not)and(B0not))andB1))

Gate (((A0not)andA1)or(((A0not)and(B0not))andB1)):
	input1: ((A0not)andA1)
	input2: (((A0not)and(B0not))andB1)
	outputTo: A>B

Gate ((B0not)andA1):
	input1: (B0not)
	input2: A1
	outputTo: (((B0not)andA1)andB1)

Gate (((B0not)andA1)andB1):
	input1: ((B0not)andA1)
	input2: B1
	outputTo: A>B

Gate A>B:
	input1: (((A0not)andA1)or(((A0not)and(B0not))andB1))
	input2: (((B0not)andA1)andB1)
	outputTo: B

	outputTo: B

Gate XOR:
	Inputs: A<B

	Inputs: A>B

	outputTo: A=B

Gate A=B:
	input1: XOR_AB'+A'B
------------------------------------------------
List of all gates in LogicBoard:
--------------------------------
A1
A0
B1
B0
(A1not)
(A0and(A1not))
(B0and(A1not))
(B1not)
((B0and(A1not))and(B1not))
((A0and(A1not))or((B0and(A1not))and(B1not)))
(A0andB0)
((A0andB0)and(B1not))
A<B
(A0not)
((A0not)andA1)
(B0not)
((A0not)and(B0not))
(((A0not)and(B0not))andB1)
(((A0not)andA1)or(((A0not)and(B0not))andB1))
((B0not)andA1)
(((B0not)andA1)andB1)
A>B
XOR
A=B

Truth table of 2BitComp LogicBoard:
-----------------------------------
A1	A0	B1	B0	|	A<B	A=B	A>B
0	0	0	0		0	1	0
0	0	0	1		1	0	0
0	0	1	0		0	0	1
0	0	1	1		0	1	0
0	1	0	0		1	0	0
0	1	0	1		1	0	0
0	1	1	0		1	0	0
0	1	1	1		1	0	0
1	0	0	0		0	0	1
1	0	0	1		0	0	1
1	0	1	0		0	0	1
1	0	1	1		0	0	1
1	1	0	0		0	1	0
1	1	0	1		1	0	0
1	1	1	0		0	0	1
1	1	1	1		0	1	0
 */