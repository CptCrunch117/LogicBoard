import Exceptions.*;
import gates.And;
import gates.*;
import gates.Gate;

import java.util.ArrayList;


/**
 * Created by CptAmerica on 6/2/15.
 */
public class LogicBoard implements LogicBoardADT {
    static final String AND = "and";
    static final String NOT = "not";
    static final String OR = "or";

    private ArrayList<Gate> logicBoard;             //Keeps track of System inputs
    private ArrayList<Gate> openInputs;             //Keeps track of gates with null inputs
    private ArrayList<Gate> openOutputs;            //Keeps track of gates with null OutputTo's
    private ArrayList<Gate> iods;                   //keeps track of open input only devices
    private ArrayList<Gate> allGates;       //List of all existing gates in logicboard object.


    public LogicBoard(){
        logicBoard = new ArrayList<Gate>();
        openOutputs = new ArrayList<Gate>();
        openInputs = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        iods = new ArrayList<Gate>();

        this.openOutputs = findOpenOutputs();
        updateIODList();
    }


    public LogicBoard(String[] inputIDs){
        this.logicBoard = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        openOutputs = new ArrayList<Gate>();
        openInputs = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        iods = new ArrayList<Gate>();

        for(int i=0; i < inputIDs.length; i++){
            BinarySwitch bSwitch = new BinarySwitch(inputIDs[i]);
            addOOD(bSwitch);
            this.allGates.add(bSwitch);
        }
        this.openOutputs = findOpenOutputs();
        updateIODList();
    }

    @Override
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
    }//END OF openOuputs METHOD

    @Override
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
                    if( (gate.getOutputTo() == null && !gate.getDeviceType().equalsIgnoreCase("IOD")) || gate.getGateType().equalsIgnoreCase("Ghost") ) {
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
    }//END OF openOuputs METHOD

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
    public void addGate(String gate, String gateID, String input1_nameID, String input2_nameID) {

        for(Gate g : this.allGates){
            if(g.getGateID().equalsIgnoreCase(gateID)){
                throw new GateIDExistsException(gateID);
            }
        }

        Gate inputOne = findAvGate(input1_nameID);
        Gate inputTwo = findAvGate(input2_nameID);



            if (gate.equalsIgnoreCase("And")) {
                if(inputOne.getGateType().equalsIgnoreCase("ghost") || inputTwo.getGateType().equalsIgnoreCase("ghost")){
                    replaceGhost("and", gateID, input1_nameID, input2_nameID);
                }else {
                    createGate("and", gateID, inputOne, inputTwo);
                }
            }
            else if (gate.equalsIgnoreCase("Or")) {
                if(inputOne.getGateType().equalsIgnoreCase("ghost") || inputTwo.getGateType().equalsIgnoreCase("ghost")){
                    replaceGhost("or", gateID, input1_nameID, input2_nameID);
                }else {
                    createGate("or", gateID, inputOne, inputTwo);
                }
            }
            else if (gate.equalsIgnoreCase("Not")) {
                if(inputOne.getGateType().equalsIgnoreCase("ghost")){
                    replaceGhost("not", gateID, input1_nameID, input2_nameID);

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

            Ghost ghost1 = new Ghost(remove.getInput1From(), remove.getOutputTo(), "GhostOf_"+remove.getGateID()+"_input1");
            Ghost ghost2 = new Ghost(remove.getInput2From(), remove.getOutputTo(), "GhostOf_"+remove.getGateID()+"_input2");
            if(remove.getOutputTo() != null){
                if(remove.getOutputTo().getInput1From() != null) {
                    if (remove.getOutputTo().getInput1From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                        remove.getOutputTo().setInput1From(ghost1);
                        remove.getOutputTo().setInput1From(ghost2);
                    }
                    else if(remove.getOutputTo().getInput2From() != null){
                        if(remove.getOutputTo().getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())){
                            remove.getOutputTo().setInput2From(ghost1);
                            remove.getOutputTo().setInput2From(ghost2);
                        }
                    }
                }
                else if(remove.getOutputTo().getInput2From() != null){
                    if(remove.getOutputTo().getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())){
                        remove.getOutputTo().setInput2From(ghost1);
                        remove.getOutputTo().setInput2From(ghost2);
                    }
                }
            }
            allGates.remove(remove);
            allGates.add(ghost1);
            allGates.add(ghost2);
            remove.remove();
        }
        else if(remove.getGateType().equalsIgnoreCase("NOT")){
            Ghost ghost = new Ghost(remove.getInput1From(), remove.getOutputTo(), "GhostOf_"+remove.getGateID()+"_input1");
            if(remove.getOutputTo() != null){
                if(remove.getOutputTo().getInput1From() != null) {
                    if (remove.getOutputTo().getInput1From().getGateID().equalsIgnoreCase(remove.getGateID())) {
                        remove.getOutputTo().setInput1From(ghost);
                    }
                    else if(remove.getOutputTo().getInput2From() != null){
                        if(remove.getOutputTo().getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())){
                            remove.getOutputTo().setInput2From(ghost);
                        }
                    }
                }
                else if(remove.getOutputTo().getInput2From() != null){
                    if(remove.getOutputTo().getInput2From().getGateID().equalsIgnoreCase(remove.getGateID())){
                        remove.getOutputTo().setInput2From(ghost);
                    }
                }
            }

            allGates.remove(remove);
            allGates.add(ghost);

            remove.remove();
        }
        this.openOutputs = findOpenOutputs();

    }


    public void replaceGhost(String gate, String gateID, String input1_nameID, String input2_nameID){
        Gate input1 = findAvGate(input1_nameID);
        Gate input2 = findAvGate(input2_nameID);

        if(gate.equalsIgnoreCase("and")){

            //if input1 == ghost and input2 != ghost
            if (input1.getGateType().equalsIgnoreCase("ghost") && !input2.getGateType().equalsIgnoreCase("ghost")) {
                //if AND
                //  find input1 ghost, and replace its input1from to new gates input1from and (if ghost has output-to) output-to
                //  Find input2 gate, since not equal to ghost search for it in available outputs list
                //  check if input1 ghost has sibling ghost(another ghost pointing to same gate)
                //if so, delete the sibling ghost.
                And and = new And(input1.getInput1From(),input2, gateID);
                this.allGates.add(and);

                //Find sibling ghost(if exists) and remove it
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
                //----------
                if(input1.getOutputTo() != null){
                    if(input1.getOutputTo().getInput1From().getGateType().equalsIgnoreCase("ghost")){
                        input1.getOutputTo().setInput1From(and);
                    }
                    else if(input1.getInput2From().getGateType().equalsIgnoreCase("ghost")){
                        input1.getOutputTo().setInput2From(and);
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
                And and = new And(input1,input2.getInput1From(), gateID);
                this.allGates.add(and);

                //Find sibling ghost(if exists) and remove it
                if(input2.getOutputTo() != null) {

                    Gate siblingGhost = null;
                    for (Gate g : this.allGates) {
                        if (g.getGateType().equalsIgnoreCase("ghost")) {
                            if (g.getOutputTo().getGateID().equalsIgnoreCase(input2.getOutputTo().getGateID())) {
                                siblingGhost = g;
                            }
                        }
                    }
                    if (siblingGhost != null) {
                        this.allGates.remove(siblingGhost);
                        siblingGhost.remove();
                    }
                }
                //----------
                if(input2.getOutputTo() != null){
                    if(input1.getOutputTo().getInput1From().getGateType().equalsIgnoreCase("ghost")){
                        input1.getOutputTo().setInput1From(and);
                    }
                    else if(input1.getInput2From().getGateType().equalsIgnoreCase("ghost")){
                        input1.getOutputTo().setInput2From(and);
                    }
                }
                allGates.remove(input2);
                input2.remove();
                and.evaluateGate();

            }


            else if(input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {
                // check if both ghosts point to same output
                // if both are siblings then replace input1From on new gate with input1's input1from
                // then replace input2From on new gate with input2's input1From
                // if outputTo is not equal to null set new gates outputTo using the outputTo pointer from ONE
                //of the ghost siblings.
                if (input1.getOutputTo().getGateID().equalsIgnoreCase(input2.getOutputTo().getGateID())) {

                    And and = new And(input1.getInput1From(), input2.getInput1From(), gateID);

                    if (input1.getOutputTo() != null) {
                        if(input1.getOutputTo().getInput1From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().setInput1From(and);
                        }
                        else if(input1.getInput2From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().setInput2From(and);
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
                Or or = new Or(input1.getInput1From(),input2, gateID);
                this.allGates.add(or);

                //Find sibling ghost(if exists) and remove it
                if (input1.getOutputTo() != null) {
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
                //----------
                if(input1.getOutputTo() != null){
                    if(input1.getOutputTo().getInput1From() != null) {
                        if (input1.getOutputTo().getInput1From().getGateType().equalsIgnoreCase("ghost")) {
                            input1.getOutputTo().setInput1From(or);
                        }
                        else if(input1.getOutputTo().getInput2From() != null){
                            if(input1.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                                input1.getOutputTo().setInput2From(or);
                            }
                        }
                    }
                    else if(input2.getOutputTo().getInput2From() != null){
                        if(input1.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().setInput2From(or);
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
                Or or = new Or(input1,input2.getInput1From(), gateID);
                this.allGates.add(or);

                //Find sibling ghost(if exists) and remove it
                if(input1.getOutputTo() != null) {

                    Gate siblingGhost = null;
                    for (Gate g : this.allGates) {
                        if (g.getGateType().equalsIgnoreCase("ghost")) {
                            if (g.getOutputTo().getGateID().equalsIgnoreCase(input2.getOutputTo().getGateID())) {
                                siblingGhost = g;
                            }
                        }
                    }
                    if (siblingGhost != null) {
                        this.allGates.remove(siblingGhost);
                        siblingGhost.remove();
                    }
                }
                //----------
                if(input2.getOutputTo() != null){
                    if(input2.getOutputTo().getInput1From() != null) {
                        if (input2.getOutputTo().getInput1From().getGateType().equalsIgnoreCase("ghost")) {
                            input2.getOutputTo().setInput1From(or);
                        }
                        else if(input2.getOutputTo().getInput2From() != null){
                            if(input2.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                                input2.getOutputTo().setInput2From(or);
                            }
                        }
                    }
                    else if(input2.getOutputTo().getInput2From() != null){
                        if(input2.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                            input2.getOutputTo().setInput2From(or);
                        }
                    }
                }
                allGates.remove(input2);
                input2.remove();
                or.evaluateGate();
            }
            else if(input1.getGateType().equalsIgnoreCase("ghost") && input2.getGateType().equalsIgnoreCase("ghost")) {
                Or or = new Or(input1.getInput1From(), input2.getInput1From(), gateID);

                if (input1.getOutputTo() != null) {
                    if(input1.getOutputTo().getInput1From() != null) {
                        if (input1.getOutputTo().getInput1From().getGateType().equalsIgnoreCase("ghost")) {
                            input1.getOutputTo().setInput1From(or);
                        }
                        else if(input1.getOutputTo().getInput2From() != null){
                            if(input1.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                                input1.getOutputTo().setInput2From(or);
                            }
                        }
                    }
                    else if(input2.getOutputTo().getInput2From() != null){
                        if(input1.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().setInput2From(or);
                        }
                    }
                }

                allGates.add(or);
                allGates.remove(input1);
                allGates.remove(input2);
                input1.remove();
                input2.remove();
                or.evaluateGate();
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
                Not not = new Not(input1.getInput1From(), gateID);
                this.allGates.add(not);

                //Find sibling ghost(if exists) and remove it
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
                //----------
                if(input1.getOutputTo() != null){
                    if(input1.getOutputTo().getInput1From() != null) {
                        if (input1.getOutputTo().getInput1From().getGateType().equalsIgnoreCase("ghost")) {
                            input1.getOutputTo().setInput1From(not);
                        }
                        else if(input1.getOutputTo().getInput2From() != null){
                            if(input1.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                                input1.getOutputTo().setInput2From(not);
                            }
                        }
                    }
                    else if(input2.getOutputTo().getInput2From() != null){
                        if(input1.getOutputTo().getInput2From().getGateType().equalsIgnoreCase("ghost")){
                            input1.getOutputTo().setInput2From(not);
                        }
                    }                }
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
            if(g.getOutputTo() != null){System.out.println("\toutputTo: "+g.getOutputTo().getGateID());}
            System.out.println();
        }
        System.out.println("------------------------------------------------");
    }

    public void updateLogicBoard(){
        for(Gate g : this.allGates){
            g.evaluateGate();
        }

    }
}
