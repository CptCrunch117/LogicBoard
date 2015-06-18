import Exceptions.*;
import gates.And;
import gates.*;
import gates.Gate;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Kyle Ferguson on 6/2/15.
 */
public class LogicBoard implements LogicBoardADT, Serializable {
    static final String AND = "and";
    static final String NOT = "not";
    static final String OR = "or";

    private String logicBoardName;
    private ArrayList<Gate> logicBoard;             //Keeps track of System inputs
    private ArrayList<Gate> openInputs;             //Keeps track of gates with null inputs
    private ArrayList<Gate> openOutputs;            //Keeps track of gates with null OutputTo's
    private ArrayList<Gate> iods;                   //keeps track of open input only devices
    private ArrayList<Gate> allGates;       //List of all existing gates in logicboard object.
    private ArrayList<Gate> sysOut;
    private ArrayList<LogicBoard> blocks;

    public LogicBoard(){
        this.logicBoard = new ArrayList<Gate>();
        this.openOutputs = new ArrayList<Gate>();
        this.openInputs = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        this.iods = new ArrayList<Gate>();
        this.sysOut = new ArrayList<Gate>();
        this.blocks = new ArrayList<LogicBoard>();
        this.openOutputs = findOpenOutputs();
        updateIODList();
    }


    public LogicBoard(String[] inputIDs, String logicBoardName){
        this.logicBoardName = logicBoardName;
        this.logicBoard = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        this.openOutputs = new ArrayList<Gate>();
        this.openInputs = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        this.iods = new ArrayList<Gate>();
        this.sysOut = new ArrayList<Gate>();

        for(int i=0; i < inputIDs.length; i++){
            BinarySwitch bSwitch = new BinarySwitch(inputIDs[i]);
            addOOD(bSwitch);
            this.allGates.add(bSwitch);
        }
        this.openOutputs = findOpenOutputs();
        updateIODList();
    }






    @Override
    public ArrayList<Gate> findOpenOutputs() {
        ArrayList<Gate> find = new ArrayList<Gate>();


        for(Gate g : this.logicBoard){
           if(g.getOutputTo().isEmpty()){
               if(!find.contains(g)){
                   find.add(g);
               }
           }
           else {
               seeker(g, find);
           }
        }


        return find;
    }

    private void seeker(Gate g, ArrayList<Gate> find){
        ArrayList<Gate> temp = new ArrayList<Gate>();

        if(g.getGateType().equalsIgnoreCase("ghost")){
            if(!find.contains(g)){
                find.add(g);
            }
        }

        //Base case-2
        if(g.getOutputTo().isEmpty()){
            if(!find.contains(g)){
                find.add(g);
            }
            return;
        }
        else{
            for(Gate gate : g.getOutputTo()){
                if(gate != null) {
                    temp.add(gate);
                }
            }
            for(Gate gate : temp){
                seeker(gate, find);
            }
        }
        return;
    }
    public Gate findGate(String gateID){
        Gate found = null;
        boolean isFound = false;

        //First check if gate is binary switch
        for(int i=0; i < this.logicBoard.size(); i++){
            if(this.logicBoard.get(i).getGateID().equalsIgnoreCase(gateID)){
                found = this.logicBoard.get(i);
                isFound = true;
                break;
            }
        }
        //if not switch gate found should be null and isFound should be false.
        //
        if(!isFound){
            for(int i=0; i < this.logicBoard.size(); i++) {
                Gate g = this.logicBoard.get(i);
                found = retriever(g, gateID);
                if(found != null){
                    break;
                }

            }
        }



        return found;
    }

    private Gate retriever(Gate find, String gateID){
        ArrayList<Gate> temp = new ArrayList<Gate>();
        Gate found = null;


        if(find.getOutputTo().isEmpty()){
            if(find.getGateID().equalsIgnoreCase(gateID)){
                    found = find;
                    return found;
            }
            return null;
        }
        //Base case-2
        if (find.getGateID().equalsIgnoreCase(gateID)){
                found = find;
        }
        else{
            for(Gate gate : find.getOutputTo()){
                temp.add(gate);
            }
            for(Gate gate : temp){
                found = retriever(gate, gateID);
                if(found != null){
                    break;
                }
            }
        }


        return found;
    }

    public Gate getAvailableGate(String gateID){
        Gate found = null;
        for(int i=0; i < this.allGates.size(); i++){
            if(this.allGates.get(i).getGateID().equalsIgnoreCase(gateID)){
                found = this.allGates.get(i);
                break;
            }
        }

        return found;
    }

    /**
     * This method updates the list of Input Only Devices
     */
    private void updateIODList(){
        ArrayList<Gate> iods = new ArrayList<Gate>();

        for(Gate g : this.openOutputs){
            if(g.getGateType().equalsIgnoreCase("IOD")){
                iods.add(g);
            }
        }
        this.iods = iods;
    }


    /**
     * Compiles a list of Input only devi
     * @return
     */
    public ArrayList<Gate> getAvailableOutputs(){
        ArrayList<Gate> available = new ArrayList<Gate>();
        for(Gate g : this.openOutputs){
            for(Gate check : this.iods){
                if(!(g.getGateType().equalsIgnoreCase(check.getGateType()))){
                    available.add(g);
                    break;
                }
            }
        }

        return available;
    }


    private void createGate(String gate, String gateID, Gate input1, Gate input2){


        if (gate.equalsIgnoreCase("AND")) {
                //add and gate
                And and = new And(input1, input2, gateID);
                allGates.add(and);
                and.evaluateGate();
                    /*
                    gate.setGateID(gateID);
                    gate.setInput1From(inputOne);
                    gate.setInput2From(inputTwo);
                    gate.evaluateGate();
                    */
        }
        if (gate.equalsIgnoreCase("OR") ) {


                //add and gate
                Or or = new Or(input1, input2, gateID);
                allGates.add(or);
                or.evaluateGate();
                    /*
                    gate.setGateID(gateID);
                    gate.setInput1From(inputOne);
                    gate.setInput2From(inputTwo);
                    gate.evaluateGate();
                    */

        }

        else if (gate.equalsIgnoreCase("NOT")) {

                //add not gate
                Not not = new Not(input1, gateID);
                allGates.add(not);
                not.evaluateGate();


        }

        if (gate.equalsIgnoreCase("BinaryProbe")){

                BinaryProbe probe = new BinaryProbe(input1, gateID);
                allGates.add(probe);
                probe.setOutputTo(null);


        }
}

    @Override
    public void addGate(String gate, String gateID, String input1_nameID, String input2_nameID) {



        for(Gate g : this.allGates){
            if(g.getGateID().equalsIgnoreCase(gateID)){
                throw new GateIDExistsException(gateID);
            }
        }


        Gate inputOne;
        Gate inputTwo = null;

        if(gate.equalsIgnoreCase("and") || gate.equalsIgnoreCase("or")) {
            inputOne = getAvailableGate(input1_nameID);
            inputTwo = getAvailableGate(input2_nameID);
        }/*
        else if(this.blocks.contains(input1_nameID)){
                for(int i=0; i<this.blocks.size();i++){
                    if(blocks.get(i).logicBoardName.equalsIgnoreCase(input1_nameID)){
                        inputOne = blocks.get(i).sysOut.get(i);
                    }
                }
            }*/
        else{
            inputOne = getAvailableGate(input1_nameID);
        }


            if (gate.equalsIgnoreCase("And")) {
                    createGate("and", gateID, inputOne, inputTwo);
            }
            else if (gate.equalsIgnoreCase("Or")) {
                    createGate("or", gateID, inputOne, inputTwo);
            }
            else if (gate.equalsIgnoreCase("Not")) {
                    createGate("not", gateID, inputOne, null);
            }
            else if (gate.equalsIgnoreCase("binaryprobe")) {
                    createGate("binaryprobe", gateID, inputOne, null);
            }



        //Update openOuput list after adding
        this.openOutputs = findOpenOutputs();
        updateIODList();
    }


    @Override
    public void addOOD(Gate gate) {
        if(gate.getDeviceType().equalsIgnoreCase("OOD")) {
            this.logicBoard.add(gate);
            //this.allGates.add(gate);
        }
        else{
            throw new InvalidOODException(gate.getGateID());
        }
    }


    public ArrayList<Gate> getOutputs(){
    return this.openOutputs;
}


    @Override
    public String generateTruthTable() {

        double inputSize = this.logicBoard.size();
        int possibilities = (int)Math.pow(2, inputSize);
        int [][] truthTable = new int[possibilities][this.logicBoard.size()+this.openOutputs.size()];
        updateLogicBoard();
        ArrayList<String> labels = new ArrayList<String>();
        if(this.logicBoard.size() > 0) {
            int in=0;
            while(in<this.logicBoard.size()){
                labels.add(this.logicBoard.get(in).getGateID());
                in++;
            }
        }
        if(this.openOutputs.size() > 0) {
            int out=0;
            while(out<this.openOutputs.size()){
                labels.add(this.openOutputs.get(out).getGateID());
                out++;

            }
        }

        for(int i=0; i < possibilities; i++){
            //Convert i into binary
            int[] binarylist = new int[this.logicBoard.size()];
            String binary = binaryValue(i);


            int adjust = binarylist.length - binary.length();

            int leftOff = 0;
            int b_pos = binarylist.length-1;
            for(int j=binary.length()-1; j >= 0; j--){
                leftOff=j;
                binarylist[b_pos] = Integer.parseInt(String.valueOf(binary.charAt(j)));
                b_pos--;

            }

            //If binary equivalent of i is not have the same number of bits as output, set
            //remaining outputs with 0. (the remaining MSBs).
            if(!(adjust == 0)) {
                for (int j = 0; j < adjust; j++) {
                    binarylist[j] = 0;
                }
            }


            //------------------------------
            //------------------------------

            //Set binary states of inputs and Add their new state to the truth table.
            for(int j=0; j < binarylist.length;j++ ){
                //Set each gates state
                this.logicBoard.get(j).setOutput(binarylist[j]);

                //enter it into the truthTable
                truthTable[i][j] = this.logicBoard.get(j).getOutput();
            }
            //------------------------------

            //Get binary states of outputs and Add their state to the truth table.
            int outputIterate = (this.logicBoard.size());
            for(Gate g : this.openOutputs){
                truthTable[i][outputIterate] = g.getOutput();
                outputIterate++;
            }
            //------------------------------

        }
        //------------------------------

        String table = "";

        for(Gate g : this.logicBoard){
            table += (g.getGateID() +"\t");
        }

        table += ("|\t");

        for(Gate g : this.openOutputs){
            table += (g.getGateID()+"\t");
        }


        for(int i=0; i < truthTable.length; i++){
                table += "\n";

            int labelInc=0;
            for(int j = 0; j < truthTable[0].length; j++){
                if(j == this.logicBoard.size()-1){
                    table += (truthTable[i][j] + "\t\t");
                }else {
                    int tab = 1;
                    if(labelInc+1 < labels.size()){
                        tab = labels.get(labelInc+1).length()/3;
                    }
                    if(tab == 0){
                        tab = 1;
                    }
                    table += (truthTable[i][j]);
                    for(int d=0; d < tab; d++){
                        table += "\t";
                    }
                    labelInc++;
                }
            }
        }


        return table;
    }

    @Override
    public void removeGate(String gateID) {
        Gate remove = findGate(gateID);


        if(remove.getGateType().equalsIgnoreCase("AND") || remove.getGateType().equalsIgnoreCase("OR")){

            for(int i=0; i < remove.getOutputTo().size(); i++){
            Ghost ghost1 = new Ghost(remove.getInput1From(),remove.getInput2From(), remove.getOutputTo().get(i), "GhostOf_"+remove.getGateID()+"_"+i);
                int inputPos = remove.getOutputTo().get(i).findInput(remove);
                if(inputPos == 1){
                    remove.getOutputTo().get(i).setInput1From(ghost1);
                }
                else if(inputPos == 2){
                    remove.getOutputTo().get(i).setInput2From(ghost1);
                }
                /*if(remove.getOutputTo() != null){
                    if(remove.getOutputTo().get(i).getInput1From() != null) {
                        if (remove.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                            remove.getOutputTo().get(i).setInput1From(ghost1);
                        }
                        else if (remove.getOutputTo().get(i).getInput2From() != null) {
                            if (remove.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                                remove.getOutputTo().get(i).setInput2From(ghost1);
                            }
                        }
                    }
                }
                else if(remove.getOutputTo().get(i).getInput2From() != null){
                    if(remove.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())){
                        remove.getOutputTo().get(i).setInput2From(ghost1);
                    }
                }*/
                allGates.add(ghost1);
            }
            allGates.remove(remove);
            remove.remove();
        }
        else if(remove.getGateType().equalsIgnoreCase("NOT")){

            for(int i=0; i < remove.getOutputTo().size(); i++) {

                Ghost ghost = new Ghost(remove.getOutputTo().get(i).getInput1From(), remove.getOutputTo().get(i), "GhostOf_" + remove.getGateID() + "_input"+i);
                int inputPos = remove.getOutputTo().get(i).findInput(remove);
                if(inputPos == 1){
                    remove.getOutputTo().get(i).setInput1From(ghost);
                }
                else if(inputPos == 2){
                    remove.getOutputTo().get(i).setInput2From(ghost);
                }

               /* if (remove.getOutputTo() != null) {
                    if (remove.getOutputTo().get(i).getInput1From() != null) {
                        if (remove.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                            remove.getOutputTo().get(i).setInput1From(ghost);
                        } else if (remove.getOutputTo().get(i).getInput2From() != null) {
                            if (remove.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                                remove.getOutputTo().get(i).setInput2From(ghost);
                            }
                        }
                    } else if (remove.getOutputTo().get(i).getInput2From() != null) {
                        if (remove.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                            remove.getOutputTo().get(i).setInput2From(ghost);
                        }
                    }*/
                allGates.add(ghost);
                }
            allGates.remove(remove);
            remove.remove();
            }


        this.openOutputs = findOpenOutputs();

    }

    @Override
    public void setSystemOutputs(ArrayList<String> gateIDs) {
        for(String s : gateIDs){
            Gate gate = findGate(s);
            this.sysOut.add(gate);
        }
    }

    @Override
    public ArrayList<Gate> getSystemOutputs() {
        return this.sysOut;
    }

    @Override
    public void insertLogicBlock(ArrayList<String> gateIN, LogicBoard block) {
        //temp arrayList to hold all found open input gates
        ArrayList<Gate> outputs = new ArrayList<Gate>();

        //Iterate over all gateIds passed in and add to temp arrayList
        for(String gateID: gateIN) {
            //find gate matching id passed in
            Gate gate = getAvailableGate(gateID);
            //If gate not found throw no SuchGateException
            if(gate == null){
                throw new NoSuchGateException(gateID);
            }
            //Otherwise if found add gate to temp ArrayList.
            else{
                outputs.add(gate);
            }
        }//End of gateID searching...

        //if the outputs list size is equal to the input switch size of the logic block
        //A "Connection" is established and linking of outputs to logic block can begin!
        if(outputs.size() == block.logicBoard.size()){
            //System.out.println("Connection Made...");
            int index = 0;
            //the gateIn should arrange the order from MSB to LSB to match desired connections
            //EX:
            //
            //     3       2       1        0 <- Digit-place
            //     A       B       C        D  <-inputs of block being inserted
            //     G0      G1      G2       G3 <-in order of index of gateID ArrayList from parameter {G0, G1, G2, G3}


            for(Gate g : outputs){
                //connect the corresponding outputs to binary switch (binary switch will now simply pass on the value of gate connected to it!)
                System.out.println(block.logicBoard.get(index).getGateID());
                System.out.println(block.logicBoard.get(index).getInput1From());
                block.logicBoard.get(index).setInput1From(g);
                System.out.println(block.logicBoard.get(index).getInput1From().getGateID());
                //System.out.println(g.get(index).getInput1From().getGateID());

            }
            //Update openOuput list after adding
            this.openOutputs = findOpenOutputs();
        }



    }


    private void transferOutputTo(Gate tTO, Gate tFROM){
        if(!tFROM.getOutputTo().isEmpty()){
            //for(int i =0; i < tFROM.getOutputTo().size();i++){
                tTO.setOutputTo(tFROM.getOutputTo().get(0));
            //}
            int inputPos = tFROM.getOutputTo().get(0).findInput(tFROM);
            if(inputPos == 1){
                tFROM.getOutputTo().get(0).setInput1From(tTO);
            }
            else if(inputPos == 2){
                tFROM.getOutputTo().get(0).setInput2From(tTO);
            }
        }
    }

    private void updateReplace(Gate replacer, Gate ghost){
        if(replacer != null && ghost != null) {
            this.allGates.add(replacer);
            this.allGates.remove(ghost);
            ghost.remove();
        }
    }

    public void replaceGhost(String gate, String gateID,String ghostID, String input1_nameID, String input2_nameID){

        Gate ghost = findGate(ghostID);
        if(ghost != null) {

            //in1 is ghost's in1 and in2 is ghost's in2
            if (ghost.getInput2From() != null) {
                if (ghost.getInput1From().getGateID().equalsIgnoreCase(input1_nameID) && ghost.getInput2From().getGateID().equalsIgnoreCase(input2_nameID)) {

                    if (gate.equalsIgnoreCase("and")) {
                        And and = new And(ghost.getInput1From(), ghost.getInput2From(), gateID);
                        transferOutputTo(and, ghost);
                        updateReplace(and, ghost);
                    } else if (gate.equalsIgnoreCase("or")) {
                        Or or = new Or(ghost.getInput1From(), ghost.getInput2From(), gateID);
                        transferOutputTo(or, ghost);
                        updateReplace(or, ghost);
                    }
                }

                //if in1 is ghost's in2 and in2 is ghost's in1, then execute same procedure as above logic
                else if (ghost.getInput1From().getGateID().equalsIgnoreCase(input2_nameID) &&
                        ghost.getInput2From().getGateID().equalsIgnoreCase(input1_nameID)) {
                    if (gate.equalsIgnoreCase("and")) {
                        And and = new And(ghost.getInput1From(), ghost.getInput2From(), gateID);
                        transferOutputTo(and, ghost);
                        updateReplace(and, ghost);
                    } else if (gate.equalsIgnoreCase("or")) {
                        Or or = new Or(ghost.getInput1From(), ghost.getInput2From(), gateID);
                        transferOutputTo(or, ghost);
                        updateReplace(or, ghost);
                    }

                }
                //if in1 is same as ghost's in1 and in2 is not ghost's in2
                else if (ghost.getInput1From().getGateID().equalsIgnoreCase(input1_nameID) &&
                        !ghost.getInput2From().getGateID().equalsIgnoreCase(input2_nameID)) {

                    Gate input2 = findGate(input2_nameID);
                    if (gate.equalsIgnoreCase("and")) {
                        And and = new And(ghost.getInput1From(), input2, gateID);
                        transferOutputTo(and, ghost);
                        updateReplace(and, ghost);
                    } else if (gate.equalsIgnoreCase("or")) {
                        Or or = new Or(ghost.getInput1From(), input2, gateID);
                        transferOutputTo(or, ghost);
                        updateReplace(or, ghost);
                    }
                }

                //if in1 is not same as ghost's in1 and in2 is same as ghost's in2
                else if (!ghost.getInput1From().getGateID().equalsIgnoreCase(input1_nameID) &&
                        ghost.getInput2From().getGateID().equalsIgnoreCase(input1_nameID)) {

                    Gate input1 = findGate(input1_nameID);

                    if (gate.equalsIgnoreCase("and")) {
                        And and = new And(input1, ghost.getInput2From(), gateID);
                        transferOutputTo(and, ghost);
                        updateReplace(and, ghost);
                    } else if (gate.equalsIgnoreCase("or")) {
                        Or or = new Or(input1, ghost.getInput2From(), gateID);
                        transferOutputTo(or, ghost);
                        updateReplace(or, ghost);
                    }
                }
                //Else if neither of the inputs are the same gates as the ghosts inputs
                else if (!ghost.getInput1From().getGateID().equalsIgnoreCase(input1_nameID) &&
                        !ghost.getInput2From().getGateID().equalsIgnoreCase(input1_nameID)){

                    Gate input1 = findGate(input1_nameID);
                    Gate input2 = findGate(input2_nameID);

                    if (gate.equalsIgnoreCase("and")) {
                        And and = new And(input1, input2, gateID);
                        transferOutputTo(and, ghost);
                        updateReplace(and, ghost);
                    } else if (gate.equalsIgnoreCase("or")) {
                        Or or = new Or(input1, input2, gateID);
                        transferOutputTo(or, ghost);
                        updateReplace(or, ghost);
                    }
                }
            }
            else if(ghost.getGateType().equalsIgnoreCase("not")){
                if(ghost.getInput1From().getGateID().equalsIgnoreCase(input1_nameID)){
                    Not not = new Not(ghost.getInput1From(),gateID);
                    transferOutputTo(not, ghost);
                    updateReplace(not, ghost);
                }
                else if(!ghost.getInput1From().getGateID().equalsIgnoreCase(input1_nameID)){
                    Gate input1 = findGate(input1_nameID);
                    Not not = new Not(input1, gateID);
                    transferOutputTo(not, ghost);
                    updateReplace(not, ghost);
                }
            }
        }

        this.openOutputs = findOpenOutputs();
        updateLogicBoard();
    }


    private String binaryValue(int num){
        //Temporary variables
        final int IBINARY = 2;

        int remainder;

        String binaryCode = "";
        String reverse = "";

// Create binary code using short division until reaching zero
// if there is a remainder turn on (1)
// if no remainder turn off (0)
        int counter = 0;
        do{
            counter += 1;
            remainder = num % IBINARY;
            num /= IBINARY;
            //println("num after division "+counter+":"+Integer.toString(num));
            //println("remainder after division "+counter+":"+Integer.toString(remainder));

            if(remainder != 0){
                binaryCode += "1";
            }
            else if(remainder == 0){
                binaryCode += "0";
            }
        }while(num != 0);
        counter = 0;

        /////////////////////////////////////////
        //Binary was added backwards, simply use for loop to reverse/flip the chars in binaryCode variable
        int length = binaryCode.length();

        for(int i = length - 1; i >= 0; i--){
            reverse += binaryCode.charAt(i);
        }
        ////////////////////////////////////////
        //Return reversed code for actual code
        return reverse;
    }

    //Prints all existing gates in Logic board object
    public void printGates(){
        System.out.println("------------------------------------------------");
        for(Gate g : this.allGates){
            System.out.println("Gate "+g.getGateID()+": ");
            if(g.getInput1From() != null){System.out.println("\tinput1: "+g.getInput1From().getGateID());}
            if(g.getInput2From() != null){System.out.println("\tinput2: "+g.getInput2From().getGateID());}
            if(!g.getOutputTo().isEmpty()) {
                for (int i = 0; i < g.getOutputTo().size(); i++) {
                    System.out.println("\toutputTo: " + g.getOutputTo().get(i).getGateID());
                    System.out.println();
                }
            }
        }
        System.out.println("------------------------------------------------");
    }

    public void updateLogicBoard(){
        for(Gate g : this.allGates){
            g.evaluateGate();
        }

    }


}
