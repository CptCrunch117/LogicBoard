package gates;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kyle Ferguson on 6/1/2015.
 */
public class Driver2 {


    public static void main(String[] agrs){
        Driver2 drive = new Driver2();
        Scanner scan = new Scanner(System.in);
        String input;
        System.out.println("Enter how many inputs are in your circuit board:");
        input = scan.nextLine();
        int numOfSwitches = Integer.valueOf(input);

        ArrayList<Gate> switches = new ArrayList<Gate>();

        //Set name and create all inputs for circuit
        for(int i = 0; i < numOfSwitches; i++){
            System.out.print("Set name for switch "+i+": ");
            input = scan.nextLine();
            switches.add(new BinarySwitch(input));
        }


        int sentinel = 1;
        do {
            System.out.println("Enter a number with the gate to create:");
            System.out.println("1.)\tAND gate\n" +
                    "2.)\tOR gate\n" +
                    "3.)\tNot gate\n");
            input = scan.nextLine();

            boolean addGate = (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("2") || input.equalsIgnoreCase("3")) ? true : false;

            if(addGate){
                System.out.println("Enter which inputs switches to add ");
                ArrayList<Gate> available = drive.openOutputs(switches);

                //Display open outputs available
                int tempCount = 0;
                for(Gate g : available){
                    System.out.println(tempCount+".)\t"+g.getGateID());
                    tempCount+=1;
                }

                //Add AND gate to board
                if(input.equalsIgnoreCase("1")){
                    System.out.print("Enter name of AND gate:\n>> ");
                    input = scan.nextLine();

                    System.out.println("enter input1's nameID");
                    String input1 = scan.nextLine();
                    Gate inputOne = drive.FindGate(switches, input1);
                    System.out.println("enter input2's nameID");
                    String input2 = scan.nextLine();
                    Gate inputTwo = drive.FindGate(switches, input2);

                    //Integrate new And gate into Circuitboard
                    And tempAnd = new And(inputOne, inputTwo, input);

                }
                //Add OR gate to board
                else if(input.equalsIgnoreCase("2")){
                    System.out.print("Enter name of OR gate:\n>> ");
                    input = scan.nextLine();

                    System.out.println("enter input1's nameID");
                    String input1 = scan.nextLine();
                    Gate inputOne = drive.FindGate(switches, input1);
                    System.out.print("enter input2's nameID:\n>> ");
                    String input2 = scan.nextLine();
                    Gate inputTwo = drive.FindGate(switches, input2);

                    //Integrate new And gate into Circuitboard
                    Or tempOr = new Or(inputOne, inputTwo, input);
                }
                //Add NOT gate to board
                else if(input.equalsIgnoreCase("3")){
                    System.out.print("Enter name of NOT gate:\n>> ");
                    input = scan.nextLine();

                    System.out.print("enter input1's nameID:\n>> ");
                    String input1 = scan.nextLine();
                    Gate inputOne = drive.FindGate(switches, input1);

                    Not tempNot = new Not(inputOne, input);
                }



                System.out.print("Would you like to add another gate?(y/n)\n>> ");
                input = scan.nextLine();
                sentinel = (input.equalsIgnoreCase("y")) ? 0 : 1;

            }//END OF ADD GATE TO CIRCUIT BOARD
            else{
                sentinel = 0;
            }


        }while(sentinel == 0);

        drive.fourInputTester(switches);

/*
        int sentinelPlayCircuit = 1;
        do{
            System.out.println("Choose an OOD to change:\n");

            int count = 0;
            for(Gate g : switches){
                System.out.println(count+".)\t"+g.getGateID());
                count++;
            }
            System.out.println("-------------------");
            System.out.print("Enter name of the input that you want to change the output to:\n>> ");
            String name = scan.nextLine();

            System.out.print("Enter the value you want to change it to:\n>> ");
            String state = scan.nextLine();

            Gate pointer = drive.FindGate(switches, name);
            if(state.equalsIgnoreCase("1")){
                pointer.setOutput(1);
            }
            else if(state.equalsIgnoreCase("0")){
                pointer.setOutput(0);
            }
            else{
                pointer.setOutput(2);
            }


            System.out.println(pointer.getGateID()+" Changed to: "+pointer.getOutput());
            System.out.print("Would you like to change another input?(y/n)\n>> ");
            input = scan.nextLine();

            ArrayList<Gate> outputs = drive.openOutputs(switches);
            System.out.println("-------------------------");
            System.out.println("Outputs of open gates:\n");

            for(Gate g : outputs){
                System.out.println(g.getGateID()+": "+g.getOutput());
            }

            System.out.println("-------------------------");
            sentinelPlayCircuit = (input.equalsIgnoreCase("y")) ? 1 : 0;

        }while(sentinelPlayCircuit == 1);
*/

    }//END OF MAIN METHOD

    public ArrayList<Gate> openOutputs(ArrayList<Gate> circuitBoard){
        ArrayList<Gate> openOuputGates = new ArrayList<Gate>();
        //CircuitBoard list only hold the circuits inputs
        //if input outputTo is null, add the switch to the available openOutputs list
        //Otherwise the switch is connected to a gate, thus loop thtough gate link until
        //a null output is reached. Once there add that gate to available list.
        for(Gate switches : circuitBoard ){

            if(switches.getOutputTo() == null){
                openOuputGates.add(switches);
            }//END OF CHECK NULL OUT
            else{
                Gate gate = switches.getOutputTo();
                while(gate != null){
                    if(gate.getOutputTo() == null) {
                        boolean trulyAva = false;
                        if(!openOuputGates.isEmpty()) {
                           // System.out.println("openOutputGates size: "+ openOuputGates.size());
                            for (Gate open : openOuputGates) {
                                if (!open.getGateID().equalsIgnoreCase(gate.getGateID())) {
                                    trulyAva = true;
                                } else {
                                    trulyAva = false;
                                    break;
                                }
                            }//End of check if gate already exists in the availability list.
                            if (trulyAva) {
                                openOuputGates.add(gate);
                            }
                        }else if(openOuputGates.isEmpty()){
                            openOuputGates.add(gate);
                        }
                    }
                    gate = gate.getOutputTo();
                }

            }
        }//END DATA LOOP
        return openOuputGates;
    }//END OF openOuputs METHOD


    public Gate FindGate(ArrayList<Gate> circuit, String inputID){
        //CircuitBoard list only hold the circuits inputs
        //if input outputTo is null, add the switch to the available openOutputs list
        //Otherwise the switch is connected to a gate, thus loop thtough gate link until
        //a null output is reached. Once there add that gate to available list.
        Gate found = null;
        boolean isFound = false;
        for(int i=0; i < circuit.size(); i++){

            if(circuit.get(i).getGateID().equalsIgnoreCase(inputID)){
                    found = circuit.get(i);
                    break;
            }//END OF CEHCK NULL OUT
            else{
                Gate gates = circuit.get(i).getOutputTo();
                while(gates != null){
                    if(gates.getGateID().equalsIgnoreCase(inputID)) {
                            found = gates;
                            isFound = true;
                            break;
                    }
                    gates = gates.getOutputTo();
                }
                if(isFound){break;}
            }
        }//END DATA LOOP
        return found;
    }//END OF openOuputs METHOD


    public void fourInputTester(ArrayList<Gate> inputList){

        int[][] truthTable = new int[16][5];
        Gate finaler = null;
        ArrayList<Gate> outputs = openOutputs(inputList);
        for(int i=0; i < outputs.size(); i++){
            finaler = outputs.get(i);
            break;
        }

        for(int count = 0; count < 16; count++){

            switch (count){
                case 0:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();


                    break;
                case 1:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 2:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 3:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 4:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 5:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 6:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 7:
                    inputList.get(0).setOutput(0);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 8:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 9:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 10:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 11:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(0);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 12:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 13:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(0);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 14:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(0);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;
                case 15:
                    inputList.get(0).setOutput(1);
                    inputList.get(1).setOutput(1);
                    inputList.get(2).setOutput(1);
                    inputList.get(3).setOutput(1);
                    truthTable[count][0] = inputList.get(0).getOutput();
                    truthTable[count][1] = inputList.get(1).getOutput();
                    truthTable[count][2] = inputList.get(2).getOutput();
                    truthTable[count][3] = inputList.get(3).getOutput();

                    truthTable[count][4] = finaler.getOutput();
                    break;

            }

        }
        System.out.println("A\tB\tC\tD\t|\tZ");
        for(int i=0; i < truthTable.length; i++){
            System.out.println();
            for(int j = 0; j < truthTable[0].length; j++){
                if(j == 3){
                    System.out.print(truthTable[i][j] + "\t\t");
                }else {
                    System.out.print(truthTable[i][j] + "\t");
                }
            }
        }

    }//END OF FOURINPUTTESTER METHOD

}//END OF CLASS DEFINITION
