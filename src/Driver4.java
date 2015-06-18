import gates.Gate;

import java.util.ArrayList;

/**
 * Created by CptAmerica on 6/14/15.
 */
public class Driver4 {

    public static void main(String[] args){


        String[] switches1 = {"A", "B","C"};
        String[] switches2 = {"A"};
        LogicBoard board1 = new LogicBoard(switches1, "main");
        //LogicBoard board2 = new LogicBoard(switches2,"notter");


        board1.addGate(LogicBoard.OR, "OR1", "A", "B");
        board1.addGate(LogicBoard.NOT,"NOT1","OR1",null);
        board1.addGate(LogicBoard.NOT,"NOT2","OR1",null);

        //board2.addGate(LogicBoard.NOT, "NOT1", "A", null, 0, 0);
        //ArrayList<String> out = new ArrayList<String>();
        //out.add("NOT1");
        //board2.setSystemOutputs(out);
        //ArrayList<String> in = new ArrayList<String>();
        //in.add("OR1");
        //board1.insertLogicBlock(in, board2);
        //board1.addGate(LogicBoard.AND, "AND1","notter", "C",0, 0);
        board1.updateLogicBoard();
        System.out.println("-----Truth Table Before removing OR1-----");
        System.out.println(board1.generateTruthTable());

        System.out.println();
        System.out.println();
        System.out.println("-----Truth Table After removing OR1-----");

        board1.removeGate("OR1");
        System.out.println(board1.generateTruthTable());

        board1.replaceGhost(LogicBoard.AND,"AND1", "GhostOf_OR1_0", "A", "B");
        System.out.println(board1.generateTruthTable());



        //board1.printGates();



        //0 0    0       1
        //0 1    1       0
        //1 0    1       0
        //1 1    1       0


    }
}
