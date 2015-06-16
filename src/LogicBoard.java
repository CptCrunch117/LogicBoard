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


    /*@Override
    public Gate findGate(String nameID){
        //CircuitBoard list only hold the circuits inputs
        //if input outputTo is null, add the switch to the available openOutputs list
        //Otherwise the switch is connected to a gate, thus loop thtough gate link until
        //a null output is reached. Once there add that gate to available list.
        Gate found = null;
        boolean isFound = false;
        for(int i=0; i < this.logicBoard.size(); i++){

            if(this.logicBoard.get(i).getGateID().equalsIgnoreCase(nameID)){
                found = this.logicBoard.get(i);
                break;
            }//END OF CHECK NULL OUT
            else{
                Gate gates = this.logicBoard.get(i).getOutputTo();
                while(gates != null){
                    if(gates.getGateID().equalsIgnoreCase(nameID)) {
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
    }//END OF openOuputs METHOD*/

    /*@Override
    public ArrayList<Gate> findOpenOutputs(){
        ArrayList<Gate> openOuputGates = new ArrayList<Gate>();
        //CircuitBoard list only hold the circuits inputs
        //if input outputTo is null, add the switch to the available openOutputs list
        //Otherwise the switch is connected to a gate, thus loop through gate link until
        //a null output is reached. Once there add that gate to available list.
        for(Gate switches : this.logicBoard ){

            if(switches.getOutputTo() == null){
                openOuputGates.add(switches);
            }//END OF CHECK NULL OUT
            else{
                Gate gate = switches.getOutputTo();
                while(gate != null){
                    //Last condition with the 2 &&'s is expiremental!!!!!!!!!!!!!!
                    if( (gate.getOutputTo() == null && !gate.getDeviceType().equalsIgnoreCase("IOD")) || gate.getGateType().equalsIgnoreCase("Ghost")
                    || (gate.getDeviceType().equalsIgnoreCase("OOD") && gate.getInput1From() != null && gate.getOutputTo() == null )) {
                        boolean trulyAva = false;
                        if(!openOuputGates.isEmpty()) {
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
    }//END OF openOuputs METHOD*/



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

    private Gate retriever(Gate find, String gateID){
        ArrayList<Gate> temp = new ArrayList<Gate>();
        Gate found = null;


        if(find.getOutputTo().isEmpty() || find.getOutputTo() == null){
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
                retriever(gate, gateID);
            }
        }


        return found;
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

                found = retriever(this.logicBoard.get(i), gateID);
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
     * This method finds a target gate that "should" exist from available gates (gates with null outputTo's), based off of state of
     * openOutputs arraylist.
     * @param gateID target gate (target gates user defined ID).
     * @return Gate pointer of target gate.
     */
    private Gate findAvGate(String gateID){
        Gate found = null;

        for(int g=0; g < this.openOutputs.size(); g++){
            if(this.openOutputs.get(g).getGateID().equalsIgnoreCase(gateID)){
                found = this.openOutputs.get(g);
                break;
            }
        }
        return found;
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
    public void addGate(String gate, String gateID, String input1_nameID, String input2_nameID, int in1_in, int in2_in) {



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
                if(inputOne.getGateType().equalsIgnoreCase("ghost") || inputTwo.getGateType().equalsIgnoreCase("ghost")){
                    replaceGhost("and", gateID, input1_nameID, input2_nameID, in1_in, in2_in);
                }else {
                    createGate("and", gateID, inputOne, inputTwo);
                }
            }
            else if (gate.equalsIgnoreCase("Or")) {
                if(inputOne.getGateType().equalsIgnoreCase("ghost") || inputTwo.getGateType().equalsIgnoreCase("ghost")){
                    replaceGhost("or", gateID, input1_nameID, input2_nameID, in1_in, in2_in);
                }else {
                    createGate("or", gateID, inputOne, inputTwo);
                }
            }
            else if (gate.equalsIgnoreCase("Not")) {
                if(inputOne.getGateType().equalsIgnoreCase("ghost")){
                    replaceGhost("not", gateID, input1_nameID, input2_nameID, in1_in, in2_in);

                }else {
                    createGate("not", gateID, inputOne, null);
                }
            }
            else if (gate.equalsIgnoreCase("binaryprobe")) {
                if(inputOne.getGateType().equalsIgnoreCase("ghost")){

                }else {
                    createGate("binaryprobe", gateID, inputOne, null);
                }
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
            Ghost ghost1 = new Ghost(remove.getOutputTo().get(i).getInput1From(),remove.getOutputTo().get(i).getInput2From(), remove.getOutputTo().get(i), "GhostOf_"+remove.getGateID()+"_input"+i);
            if(remove.getOutputTo() != null){
                if(remove.getOutputTo().get(i).getInput1From() != null) {
                    if (remove.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                        remove.getOutputTo().get(i).setInput1From(ghost1);
                    } else if (remove.getOutputTo().get(i).getInput2From() != null) {
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
                }
                allGates.add(ghost1);
            }
            allGates.remove(remove);
            remove.remove();
        }
        else if(remove.getGateType().equalsIgnoreCase("NOT")){

            for(int i=0; i < remove.getOutputTo().size(); i++) {

                Ghost ghost = new Ghost(remove.getOutputTo().get(i).getInput1From(), remove.getOutputTo().get(i), "GhostOf_" + remove.getGateID() + "_input"+i);
                if (remove.getOutputTo() != null) {
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
                    }
                }
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


    public void replaceGhost(String gate, String gateID, String input1_nameID, String input2_nameID, int in1_in, int in2_in){

        Gate input1 = findGate(input1_nameID);
        Gate input2 = findGate(input2_nameID);

        if(gate.equalsIgnoreCase("and")){

            //if input1 == ghost and input2 != ghost
            if (input1.getGateType().equalsIgnoreCase("ghost") && !input2.getGateType().equalsIgnoreCase("ghost")) {
                //if AND
                //  find input1 ghost, and replace its input1from to new gates input1from and (if ghost has output-to) output-to
                //  Find input2 gate, since not equal to ghost search for it in available outputs list
                //  check if input1 ghost has sibling ghost(another ghost pointing to same gate)
                //if so, delete the sibling ghost.
                And and = null;
                if(in1_in == 0) {
                    and = new And(input1.getInput1From(), input2, gateID);
                }
                else if(in1_in == 1) {
                    if(input1.getInput2From() != null) {
                        and = new And(input1.getInput2From(), input2, gateID);
                    }
                    else{
                        and = new And(input1.getInput1From(), input2, gateID);
                    }
                }
                this.allGates.add(and);

                /*//Find sibling ghost(if exists) and remove it
                if(input1.getOutputTo() != null) {

                    Gate siblingGhost = null;
                    for (Gate g : this.allGates) {
                        if (g.getGateType().equalsIgnoreCase("ghost")) {
                            for(int i=0; i < g.getOutputTo().size(); i++) {
                               boolean valid = false;
                                for(int j=0; j < input1.getOutputTo().size(); j++) {
                                    if (g.getOutputTo().get(i).getGateID().equalsIgnoreCase(input1.getOutputTo().get(j).getGateID())) {
                                        siblingGhost = g;
                                        valid = true;
                                        break;
                                    }
                                }
                                if(valid){break;}
                            }
                        }
                    }
                    if (siblingGhost != null) {
                        this.allGates.remove(siblingGhost);
                        siblingGhost.remove();
                    }
                }
                //----------*/
                if (input1.getOutputTo() != null) {
                    for (int i = 0; i < input1.getOutputTo().size(); i++) {

                        if (input1.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input1.getOutputTo().get(i).setInput1From(and);
                        } else if (input1.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input1.getOutputTo().get(i).setInput2From(and);
                        }

                    }

                }
                allGates.remove(input1);
                input1.remove();
                and.evaluateGate();
            }

            else if(!input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {
                //if AND
                //  find input2 ghost, and replace its input1from to new gates input1from and (if ghost has output-to) output-to
                //  Find input1 gate, since not equal to ghost search for it in available outputs list
                //  check if input2 ghost has sibling ghost(another ghost pointing to same gate)
                //if so, delete the sibling ghost.
                And and = null;
                if(in2_in == 0) {
                    and = new And(input1, input2.getInput1From(), gateID);
                }
                else if(in2_in == 1) {
                    if(input2.getInput2From() != null) {
                        and = new And(input1, input2.getInput2From(), gateID);
                    }
                    else{
                        and = new And(input1, input2.getInput1From(), gateID);
                    }
                }
                this.allGates.add(and);

                /*//Find sibling ghost(if exists) and remove it
                if(input1.getOutputTo() != null) {

                    Gate siblingGhost = null;
                    for (Gate g : this.allGates) {
                        if (g.getGateType().equalsIgnoreCase("ghost")) {
                            for(int i=0; i < g.getOutputTo().size(); i++) {
                               boolean valid = false;
                                for(int j=0; j < input1.getOutputTo().size(); j++) {
                                    if (g.getOutputTo().get(i).getGateID().equalsIgnoreCase(input1.getOutputTo().get(j).getGateID())) {
                                        siblingGhost = g;
                                        valid = true;
                                        break;
                                    }
                                }
                                if(valid){break;}
                            }
                        }
                    }
                    if (siblingGhost != null) {
                        this.allGates.remove(siblingGhost);
                        siblingGhost.remove();
                    }
                }
                //----------*/
                if (input2.getOutputTo() != null) {
                    for (int i = 0; i < input2.getOutputTo().size(); i++) {//should be only size 1

                        if (input2.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input2.getOutputTo().get(i).setInput1From(and);
                        } else if (input2.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input2.getOutputTo().get(i).setInput2From(and);
                        }

                    }

                }
                allGates.remove(input2);
                input1.remove();
                and.evaluateGate();
            }


            else if(input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {
                // check if both ghosts point to same output
                // if both are siblings then replace input1From on new gate with input1's input1from
                // then replace input2From on new gate with input2's input1From
                // if outputTo is not equal to null set new gates outputTo using the outputTo pointer from ONE
                //of the ghost siblings.
                And and = null;

                for(int i=0; i < input1.getOutputTo().size(); i++){//should only be size one
                    if (input1.getOutputTo().get(i).getGateID().equalsIgnoreCase(input2.getOutputTo().get(i).getGateID())) {
                        Gate in1 = null;
                        Gate in2 = null;

                        if(in1_in == 0) {in1 = input1.getInput1From();}
                        else if(in1_in == 1){in1 = input1.getInput2From();}

                        if(in2_in == 0){in2 = input2.getInput1From();}
                        else if(in2_in == 1){in2 = input2.getInput2From();}

                        if(in1 != null && in2 != null) {
                            and = new And(in1, in2, gateID);
                        }

                    }

                    if (input1.getOutputTo() != null) {
                        if(input1.getOutputTo().get(i).getInput1From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().get(i).setInput1From(and);
                        }
                        else if(input1.getInput2From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().get(i).setInput2From(and);
                        }
                    }

                    allGates.add(and);
                    allGates.remove(input1);
                    allGates.remove(input2);
                    input1.remove();
                    input2.remove();
                    and.evaluateGate();
                }
            }
            else{

                //input1 != ghost and input2 != ghost
                //Throw exception
            }
        }//END OF AND
        else if (gate.equalsIgnoreCase("or")){

            if (input1.getGateType().equalsIgnoreCase("ghost") && !input2.getGateType().equalsIgnoreCase("ghost")) {
                //if OR
                //  find input1 ghost, and replace its input1from to new gates input1from and (if ghost has output-to) output-to
                //  Find input2 gate, since not equal to ghost search for it in available outputs list
                //  check if input1 ghost has sibling ghost(another ghost pointing to same gate)
                //if so, delete the sibling ghost.
                //if so, delete the sibling ghost.
                Or or = null;
                if(in1_in == 0) {
                    or = new Or(input1.getInput1From(), input2, gateID);
                }
                else if(in1_in == 1) {
                    if(input1.getInput2From() != null) {
                        or = new Or(input1.getInput2From(), input2, gateID);
                    }
                    else{
                        or = new Or(input1.getInput1From(), input2, gateID);
                    }
                }
                this.allGates.add(or);

                /*//Find sibling ghost(if exists) and remove it
                if(input1.getOutputTo() != null) {

                    Gate siblingGhost = null;
                    for (Gate g : this.allGates) {
                        if (g.getGateType().equalsIgnoreCase("ghost")) {
                            for(int i=0; i < g.getOutputTo().size(); i++) {
                               boolean valid = false;
                                for(int j=0; j < input1.getOutputTo().size(); j++) {
                                    if (g.getOutputTo().get(i).getGateID().equalsIgnoreCase(input1.getOutputTo().get(j).getGateID())) {
                                        siblingGhost = g;
                                        valid = true;
                                        break;
                                    }
                                }
                                if(valid){break;}
                            }
                        }
                    }
                    if (siblingGhost != null) {
                        this.allGates.remove(siblingGhost);
                        siblingGhost.remove();
                    }
                }
                //----------*/
                if (input1.getOutputTo() != null) {
                    for (int i = 0; i < input1.getOutputTo().size(); i++) {

                        if (input1.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input1.getOutputTo().get(i).setInput1From(or);
                        } else if (input1.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input1.getOutputTo().get(i).setInput2From(or);
                        }

                    }

                }
                allGates.remove(input1);
                input1.remove();
                or.evaluateGate();

            }
            else if (!input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {
                //if OR
                //  find input2 ghost, and replace its input1from to new gates input1from and (if ghost has output-to) output-to
                //  Find input1 gate, since not equal to ghost search for it in available outputs list
                //  check if input2 ghost has sibling ghost(another ghost pointing to same gate)
                //if so, delete the sibling ghost.
                Or or= null;
                if(in2_in == 0) {
                    or = new Or(input1, input2.getInput1From(), gateID);
                }
                else if(in2_in == 1) {
                    if(input2.getInput2From() != null) {
                        or = new Or(input1, input2.getInput2From(), gateID);
                    }
                    else{
                        or = new Or(input1, input2.getInput1From(), gateID);
                    }
                }
                this.allGates.add(or);

                /*//Find sibling ghost(if exists) and remove it
                if(input1.getOutputTo() != null) {

                    Gate siblingGhost = null;
                    for (Gate g : this.allGates) {
                        if (g.getGateType().equalsIgnoreCase("ghost")) {
                            for(int i=0; i < g.getOutputTo().size(); i++) {
                               boolean valid = false;
                                for(int j=0; j < input1.getOutputTo().size(); j++) {
                                    if (g.getOutputTo().get(i).getGateID().equalsIgnoreCase(input1.getOutputTo().get(j).getGateID())) {
                                        siblingGhost = g;
                                        valid = true;
                                        break;
                                    }
                                }
                                if(valid){break;}
                            }
                        }
                    }
                    if (siblingGhost != null) {
                        this.allGates.remove(siblingGhost);
                        siblingGhost.remove();
                    }
                }
                //----------*/
                if (input2.getOutputTo() != null) {
                    for (int i = 0; i < input2.getOutputTo().size(); i++) {//should be only size 1

                        if (input2.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input2.getOutputTo().get(i).setInput1From(or);
                        } else if (input2.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                            input2.getOutputTo().get(i).setInput2From(or);
                        }

                    }

                }
                allGates.remove(input2);
                input1.remove();
                or.evaluateGate();
            }
            else if(input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {
                Or or = null;

                for(int i=0; i < input1.getOutputTo().size(); i++){//should only be size one
                    if (input1.getOutputTo().get(i).getGateID().equalsIgnoreCase(input2.getOutputTo().get(i).getGateID())) {
                        Gate in1 = null;
                        Gate in2 = null;

                        if(in1_in == 0) {in1 = input1.getInput1From();}
                        else if(in1_in == 1){in1 = input1.getInput2From();}

                        if(in2_in == 0){in2 = input2.getInput1From();}
                        else if(in2_in == 1){in2 = input2.getInput2From();}

                        if(in1 != null && in2 != null) {
                            or = new Or(in1, in2, gateID);
                        }

                    }

                    if (input1.getOutputTo() != null) {
                        if(input1.getOutputTo().get(i).getInput1From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().get(i).setInput1From(or);
                        }
                        else if(input1.getInput2From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().get(i).setInput2From(or);
                        }
                    }

                    allGates.add(or);
                    allGates.remove(input1);
                    allGates.remove(input2);
                    input1.remove();
                    input2.remove();
                    or.evaluateGate();
                }
            }
            else{
                //input1 != ghost and input2 != ghost
                //Throw exception
            }

        }//END OF OR
        else if (gate.equalsIgnoreCase("not")){
            if (input1.getGateType().equalsIgnoreCase("ghost")) {
                //if NOT
                //  find input1 ghost, and replace its input1from to new gates input1from and (if ghost has output-to) output-to
                //  Find input2 gate, since not equal to ghost search for it in available outputs list
                //  check if input1 ghost has sibling ghost(another ghost pointing to same gate)
                //if so, delete the sibling ghost.
                //if so, delete the sibling ghost.
                Not not = null;
                if(in1_in == 0) {
                   not = new Not(input1.getInput1From(), gateID);
                }
                else if(in1_in == 1) {
                    if(input1.getInput2From() != null) {
                        not = new Not(input1.getInput2From(), gateID);
                    }
                    else{
                        not = new Not(input1.getInput1From(), gateID);
                    }
                }
                this.allGates.add(not);

                /*//Find sibling ghost(if exists) and remove it
                if(input1.getOutputTo() != null) {
                    Gate siblingGhost = null;
                    for (Gate g : this.allGates) {
                        if (g.getGateType().equalsIgnoreCase("ghost")) {
                            if (g.getOutputTo().getGateID().equalsIgnoreCase(input1.getOutputTo().getGateID())) {
                                siblingGhost = g;
                            }
                        }
                    }
                    if (siblingGhost != null) {
                        this.allGates.remove(siblingGhost);
                        siblingGhost.remove();
                    }
                }
                //----------*/


                if(input1.getOutputTo() != null){
                    for (int i = 0; i < input1.getOutputTo().size(); i++) {//should be only size 1

                        if (input1.getOutputTo().get(i).getInput1From() != null) {
                            if (input1.getOutputTo().get(i).getInput1From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                                input1.getOutputTo().get(i).setInput1From(not);
                            } else if (input1.getOutputTo().get(i).getInput2From() != null) {
                                if (input1.getOutputTo().get(i).getInput2From().getGateID().equalsIgnoreCase(input1.getGateID())) {
                                    input1.getOutputTo().get(i).setInput2From(not);
                                }
                            }
                        } else if (input2.getOutputTo().get(i).getInput2From() != null) {
                            if (input2.getOutputTo().get(i).getInput2From().getGateType().equalsIgnoreCase("ghost")) {
                                input1.getOutputTo().get(i).setInput2From(not);
                            }
                        }
                    }
                }
                allGates.remove(input1);
                input1.remove();
                not.evaluateGate();
            }
            else{
                //input1 != ghost and input2 != ghost
                //Throw exception
            }

        }//END of not
        else if (gate.equalsIgnoreCase("binaryprobe")){

            if (input1.getGateType().equalsIgnoreCase("ghost") && !input2.getGateType().equalsIgnoreCase("ghost")) {


            }
            else if (!input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {

            }
            else if(input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {

            }
            else{
                //input1 != ghost and input2 != ghost
                //Throw exception
            }

        }//END of binaryprobe


        this.openOutputs = findOpenOutputs();
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
