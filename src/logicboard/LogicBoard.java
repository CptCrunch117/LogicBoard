package logicboard;

import logicboard.Exceptions.*;
import logicboard.gates.*;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by Kyle Ferguson on 6/2/15.
 */
public class LogicBoard implements LogicBoardADT, Serializable, Gate {
    public static final String AND = "and";
    public static final String NOT = "not";
    public static final String OR = "or";
    public static final String STANDARD_LIB = "standard_lib";
    public static final String USER_LIB = "user_lib";

    //Gate variables
    ArrayList<Gate> inputs;
    ArrayList<Gate> outputs;
    boolean logicLock;

    final String TYPE = "LogicBoard";

    //LogicBoard Variables
    private String logicBoardName;
    private ArrayList<String> sysExpressions;
    private String gateID;
    private ArrayList<Gate> logicBoard;             //Keeps track of System inputs
    private ArrayList<Gate> openInputs;             //Keeps track of gates with null inputs
    private ArrayList<Gate> openOutputs;            //Keeps track of gates with null OutputTo's
    private ArrayList<Gate> allGates;       //List of all existing gates in logicboard object.
    private ArrayList<Gate> sysOut;
    private ArrayList<LogicBoard> blocks;
    String objectFile;

    public LogicBoard(){
        this.logicBoard = new ArrayList<Gate>();
        this.openOutputs = new ArrayList<Gate>();
        this.openInputs = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        this.sysOut = new ArrayList<Gate>();
        this.blocks = new ArrayList<LogicBoard>();
        this.openOutputs = findOpenOutputs();
        this.logicLock = false;
        this.inputs = new ArrayList<Gate>();
        this.outputs = new ArrayList<Gate>();
        this.blocks = new ArrayList<LogicBoard>();
    }


    public LogicBoard(String[] inputIDs, String logicBoardName){
        this.logicBoardName = logicBoardName;
        this.gateID = this.logicBoardName;
        this.logicBoard = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        this.openOutputs = new ArrayList<Gate>();
        this.openInputs = new ArrayList<Gate>();
        this.allGates = new ArrayList<Gate>();
        this.sysOut = new ArrayList<Gate>();
        this.inputs = new ArrayList<Gate>();
        this.outputs = new ArrayList<Gate>();
        for(int i=0; i < inputIDs.length; i++){
            BinarySwitch bSwitch = new BinarySwitch(inputIDs[i]);
            addOOD(bSwitch);
            this.allGates.add(bSwitch);
        }
        this.openOutputs = findOpenOutputs();
        this.logicLock = false;
        this.blocks = new ArrayList<LogicBoard>();
        this.sysExpressions = new ArrayList<>();
    }

    /**
     *
     * @param lib
     * @param fileName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static LogicBoard getBoard(String lib, String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(lib+"/"+fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        LogicBoard temp = (LogicBoard) ois.readObject();
        fis.close();
        ois.close();
        return temp;
    }


    public LogicBoard cloneBoard(String gateID){
        LogicBoard clone = null;

        try{

            FileInputStream fileIn = new FileInputStream(this.objectFile);
            ObjectInputStream objectInput = new ObjectInputStream(fileIn);
            clone = (LogicBoard) objectInput.readObject();
            objectInput.close();
            fileIn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(clone != null){
            clone.setGateID(gateID);
            ArrayList<String> grab = new ArrayList<String>();
            for(Gate g : clone.getSysOut()){
                grab.add(g.getGateID());
            }
            ArrayList<String> reset = new ArrayList<String>();
            for(String s : grab){
                int i =0;
                for(String ss : s.split("_")){
                    if(i == 1){
                        reset.add(ss);
                        i =0;
                    }
                    else if(i == 0){
                        i += 1;
                    }
                }
            }
            clone.setSystemOutputs(reset);
        }

        return clone;
    }


    public void saveBoard(String directory){

        if(directory.equalsIgnoreCase(LogicBoard.STANDARD_LIB)){
            File standir = new File(LogicBoard.STANDARD_LIB);
            if(!standir.exists()){
                try{
                    standir.mkdir();
                }
                catch (SecurityException e){
                    //figure it out biiiiiiitch
                }
            }//END IF
            try {
                this.objectFile = LogicBoard.STANDARD_LIB+"/"+this.logicBoardName+".ser";
                FileOutputStream fileOut = new FileOutputStream(this.objectFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
                fileOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//END IF STANDARD_LIB
        else if(directory.equalsIgnoreCase(LogicBoard.USER_LIB)){
            File usedir = new File(LogicBoard.USER_LIB);
            if(!usedir.exists()){
                try{
                    usedir.mkdir();
                }
                catch (SecurityException e){
                    //figure it out biiiiiiitch
                }

            }//END IF
            try {
                this.objectFile = LogicBoard.USER_LIB+"/"+this.logicBoardName+".ser";
                FileOutputStream fileOut = new FileOutputStream(this.objectFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(this);
                out.close();
                fileOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//END IF USER_LIB


        /*try {
            this.objectFile = this.logicBoardName+".ser";
            FileOutputStream fileOut = new FileOutputStream(this.objectFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public ArrayList<Gate> getAllGates(){
        ArrayList<Gate> allgat = new ArrayList<>();
        for(Gate g : this.allGates){
            try {
                LogicBoard b = (LogicBoard) g;
                for(Gate gg : b.getSysOut()){
                    allgat.add(gg);
                }
            }catch(ClassCastException e){
               allgat.add(g);
            }
        }

        return allgat;
    }


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

    /**
     * This method is used by findOutputs method. This is a recursive method that adds all objects
     * in the linked Gate list that either don't output to any gates and/or adds any ghost gates.
     * @param g the gate to check, the arrayList for found gates, the same arrayList is passed through
     *          each iteration of recursion to keep a list of all found gates throughout recusive process
     * @param find the arraylist that holds all found gates.
     */
    private void seeker(Gate g, ArrayList<Gate> find){
        ArrayList<Gate> temp = new ArrayList<Gate>();

        //LogicBoard gates have n outputs and each have n outputTos
        if(g.getDeviceType().equalsIgnoreCase("logicboard")){
            for(Gate gl : g.getSysOut()){
                if(gl.getOutputTo().isEmpty()){
                    if(!find.contains(gl)){
                        find.add(gl);
                    }
                }
                else {
                    seeker(gl, find);
                }
            }

        }
        //if gate is not a logicBoard Block.
        else {

            //A case (not a base case as ghosts of gates have outputs! (only 1 but still))
            if (g.getGateType().equalsIgnoreCase("ghost")) {
                if (!find.contains(g)) {
                    find.add(g);
                }
            }

            //Base case-1
            if (g.getOutputTo().isEmpty()) {
                if (!find.contains(g)) {
                    find.add(g);
                }
                return;
            } else {
                for (Gate gate : g.getOutputTo()) {
                    if (gate != null) {
                        temp.add(gate);
                    }
                }
                for (Gate gate : temp) {
                    seeker(gate, find);
                }
            }
        }
        return;
    }


    /**
     * This method utilizes a different flavor of the seeker recursion method.
     * @param gateID the gateID of target gate object.
     * @return the target gate object (with same gateID passed of course), or, if not found, null
     */
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

    /**
     * A spin on the seeker method. This method is used by the findGate method. This recursive method returns a
     * Gate object when the gateID(a string) that is passed into each iteration of every recursion matches the gates
     * gateID.
     *
     * @param find the next gate in the linked Gate list.
     * @param gateID the gateID of the target Gate object
     * @return the target gate object (with same gateID passed of course), or, if not found, null
     */
    private Gate retriever(Gate find, String gateID){
        ArrayList<Gate> temp = new ArrayList<Gate>();
        Gate found = null;
        boolean isFound = false;

        if(find.getDeviceType().equalsIgnoreCase("logicboard")){

            for(int i=0; i < find.getSysOut().size(); i++){
                if(find.getSysOut().get(i).getGateID().equalsIgnoreCase(gateID)){
                    found = find.getSysOut().get(i);
                    isFound = true;
                    break;
                }
            }

            if(!isFound){
                for(int i=0; i < find.getSysOut().size(); i++) {
                    Gate g = find.getSysOut().get(i);
                    found = retriever(g, gateID);
                    if(found != null){
                        break;
                    }
                }
            }
        }
        else {
            if (find.getOutputTo().isEmpty()) {
                if (find.getGateID().equalsIgnoreCase(gateID)) {
                    found = find;
                    return found;
                }
                return null;
            }
            //Base case-2
            if (find.getGateID().equalsIgnoreCase(gateID)) {
                found = find;
            } else {
                for (Gate gate : find.getOutputTo()) {
                    temp.add(gate);
                }
                for (Gate gate : temp) {
                    found = retriever(gate, gateID);
                    if (found != null) {
                        break;
                    }
                }
            }
        }

        return found;
    }

    /**
     * Finds A gate in
     * @param gateID
     * @return
     */
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




    private void createGate(String gate, String gateID, Gate input1, Gate input2){


        if (gate.equalsIgnoreCase("AND")) {

            if(gateID == null || input1 == null || input2 == null){
                throw new GateCreationException(this.gateID);
            }
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
        if (gate.equalsIgnoreCase("OR")) {
            if(gateID == null || input1 == null || input2 == null){
                throw new GateCreationException(this.gateID);
            }

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

        } else if (gate.equalsIgnoreCase("NOT")) {
            if(gateID == null || input1 == null ){
                throw new GateCreationException(this.gateID);
            }
            //add not gate
            Not not = new Not(input1, gateID);
            allGates.add(not);
            not.evaluateGate();


        } else if (gate.equalsIgnoreCase("BinaryProbe")) {

            BinaryProbe probe = new BinaryProbe(input1, gateID);
            allGates.add(probe);
            probe.setOutputTo(null);


        }

    }



    public void addGate(String gate, String gateID, String input1_nameID, String input2_nameID) {
        //Update openOuput list after adding
        this.openOutputs = findOpenOutputs();


        for(Gate g : this.allGates){
            if(g.getGateID().equalsIgnoreCase(gateID)){
                throw new GateIDExistsException(gateID);
            }
        }


        Gate inputOne;
        Gate inputTwo = null;

        if(gate.equalsIgnoreCase("and") || gate.equalsIgnoreCase("or")) {
            inputOne = findGate(input1_nameID);
            inputTwo = findGate(input2_nameID);
        }/*
        else if(this.blocks.contains(input1_nameID)){
                for(int i=0; i<this.blocks.size();i++){
                    if(blocks.get(i).logicBoardName.equalsIgnoreCase(input1_nameID)){
                        inputOne = blocks.get(i).sysOut.get(i);
                    }
                }
            }*/
        else{
            inputOne = findGate(input1_nameID);
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




        //Update openOuput list after adding
        this.openOutputs = findOpenOutputs();
    }


    public void addGate(LogicBoard block, String gateID, String[] input){
        this.blocks.add(block);

        ArrayList<String> inputs = new ArrayList<>();
        for(int i=0; i<input.length;i++){
            inputs.add(input[i]);
        }
        //Reference board as a gate object.
        Gate gate = block;

        //
        if(gate.getDeviceType().equalsIgnoreCase("logicboard")) {
            if (inputs.size() == gate.getInputs().size()) {
                ArrayList<Gate> gates = new ArrayList<Gate>();

                //find and compile array of gates for the input to the logic block
                for(int i=0; i < inputs.size();i++){
                    Gate found = findGate(inputs.get(i));
                    if(found != null) {
                        gates.add(found);
                    }else{
                        throw new NoSuchGateException(inputs.get(i));
                    }
                }
                //set inputs for logic block
                gate.setBlockInputFrom(gates);
            }
            this.allGates.add(gate);
        }

        //Update openOuput list after adding
        this.openOutputs = findOpenOutputs();
    }


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

    public ArrayList<Gate> getOpenOutputs(){
        return findOpenOutputs();
    }

    public String generateTruthTable() {
        ArrayList<Gate> gen;
        if(this.sysOut.size() > 0){
            gen = this.sysOut;
        }
        else{
            gen = this.openOutputs;
        }
        double inputSize = this.logicBoard.size();
        int possibilities = (int)Math.pow(2, inputSize);
        int [][] truthTable = new int[possibilities][this.logicBoard.size()+gen.size()];
        updateLogicBoard();
        ArrayList<String> labels = new ArrayList<String>();
        if(this.logicBoard.size() > 0) {
            int in=0;
            while(in<this.logicBoard.size()){
                labels.add(this.logicBoard.get(in).getGateID());
                in++;
            }
        }
        if(gen.size() > 0) {
            int out=0;
            while(out<gen.size()){
                labels.add(gen.get(out).getGateID());
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
            for(Gate g : gen){
                g.evaluateGate();
                for(LogicBoard blk : this.blocks){
                    blk.updateLogicBoard();
                }
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

        for(Gate g : gen){
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

                allGates.add(ghost);
            }
            allGates.remove(remove);
            remove.remove();
        }


        this.openOutputs = findOpenOutputs();

    }

    private void setSystemExpressions(){
        for(Gate g : this.sysOut){
            this.sysExpressions.add(g.getExpression().get(0));
        }
    }

    public void setSystemOutputs(ArrayList<String> gateIDs) {
        int count = 0;
        boolean isSet = true;

        for(String s : gateIDs){
            Gate gate = findGate(s);

            if(gate != null) {
                gate.setGateID(this.gateID+"_"+gate.getGateID());
                this.sysOut.add(gate);
                count++;

            }
            else{
                isSet = false;
                break;
            }
        }
        //LockGate from manipulation.
        if(isSet){
            setSystemExpressions();
            this.logicLock = true;
        }
    }

    public boolean getLogicLock(){
        return this.logicLock;
    }

    public ArrayList<Gate> getSystemOutputs() {
        return this.sysOut;
    }


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

                    Gate input2;
                    try {
                        input2 = findGate(input2_nameID);
                    }catch(NoSuchGateException e){
                        throw new ReplaceGhostException(ghost.getGateID());
                    }
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

                    Gate input1;
                    try{
                        input1 = findGate(input1_nameID);
                    }catch(NoSuchGateException e){
                        throw new ReplaceGhostException(ghost.getGateID());
                    }

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

                    Gate input1;
                    Gate input2;
                    try{
                        input1 = findGate(input1_nameID);
                        input2 = findGate(input2_nameID);
                    }catch(NoSuchGateException e){
                        throw new ReplaceGhostException(ghost.getGateID());
                    }


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
                    Gate input1;
                    try{
                        input1 = findGate(input1_nameID);
                    }catch(NoSuchGateException e){
                        throw new ReplaceGhostException(ghost.getGateID());
                    }

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
        for(Gate g : this.allGates) {
            System.out.println("Gate " + g.getGateID() + ": ");

            //If Gate g is a logicboard Gate do this stuffs:
            if (g.getDeviceType().equalsIgnoreCase("logicboard")) {

                //If inputs are not null print em out
                if (g.getInputs() != null) {
                    for (Gate in : g.getInputs()) {
                        System.out.println("\tInputs: " + in.getInput1From().getGateID());
                        System.out.println();
                    }
                }

                //if Outputs are not null print em out
                if (g.getSysOut() != null) {
                    for (Gate gg : g.getSysOut()) {
                        for (int i = 0; i < gg.getOutputTo().size(); i++) {
                            System.out.println("\toutputTo: " + gg.getOutputTo().get(i).getGateID());
                            System.out.println();
                        }
                    }
                }
            }
            //Otherwise do this stuffs:
            else {
                if (g.getInput1From() != null) {
                    System.out.println("\tinput1: " + g.getInput1From().getGateID());
                }
                if (g.getInput2From() != null) {
                    System.out.println("\tinput2: " + g.getInput2From().getGateID());
                }
                if (!g.getOutputTo().isEmpty()) {
                    for (int i = 0; i < g.getOutputTo().size(); i++) {
                        System.out.println("\toutputTo: " + g.getOutputTo().get(i).getGateID());
                        System.out.println();
                    }
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

    public void renameSystemOutput(String currentID, String newID){
        Gate rename = null;
        for(int i=0; i < this.sysOut.size();i++){
            if(this.sysOut.get(i).getGateID().equalsIgnoreCase(currentID)){
                this.sysOut.get(i).setGateID(this.gateID+"_"+newID);
                break;
            }
        }

    }


    /**
     * This method utilizes a different flavor of the findGate recursion method. Allows one to update the boolean expressions
     * of all the gates in the LogicBoard object.*/
    public void expressionsUpdate(){

        //First check if gate is binary switch
        for(int i=0; i < this.logicBoard.size(); i++){
            this.logicBoard.get(i).generateExpression();
        }

        //if not switch gate found should be null and isFound should be false.
        //
        for(int i=0; i < this.logicBoard.size(); i++) {
            Gate g = this.logicBoard.get(i);
            updater(g);
        }
    }

    /**
     * Allows one to update the expressions of all gates in a logic board, good practice if Inputs have been swapped.
     * This will keep the expressions current relative to the status of relationships of the other gates within the
     * LogicBoard object.
     *
     * @param find the next gate in the linked Gate list.
     * @return the target gate object (with same gateID passed of course), or, if not found, null
     */
    private void updater(Gate find){
        ArrayList<Gate> temp = new ArrayList<Gate>();

        if(find.getDeviceType().equalsIgnoreCase("logicboard")){

            for(int i=0; i < find.getSysOut().size(); i++){
                    find.getSysOut().get(i).generateExpression();
            }

            for(int i=0; i < find.getSysOut().size(); i++) {
                Gate g = find.getSysOut().get(i);
                updater(g);
            }

        }
        else {
            if (find.getOutputTo().isEmpty()) {
                find.generateExpression();
                return;
            }
            //Base case-2
            else {
                for (Gate gate : find.getOutputTo()) {
                    temp.add(gate);
                }
                for (Gate gate : temp) {
                    updater(gate);
                }
            }
        }

        return;
    }

    //----------GATE IMPLEMENTATION----------\\


    public void evaluateGate() {

        //Update input values
        for(Gate g : this.inputs){
            g.evaluateGate();
        }
        //Set updated input values to binary switches
        for(int in=0; in < this.logicBoard.size(); in++){
            this.logicBoard.get(in).setOutput(this.inputs.get(in).getOutput());
            this.logicBoard.get(in).evaluateGate();
        }

        for(Gate g : this.sysOut){
            g.evaluateGate();
        }

        //Update this gate
        this.updateLogicBoard();
    }

    @Override
    public void swapInput(int inputPos, Gate switchWith) {
            boolean check = getInputs().get(inputPos).getInput1From().getOutputTo().remove(this);
            if(!check) throw new SwapFailureException(this.getGateID());
            else getInputs().get(inputPos).setInput1From(switchWith);
    }

    @Override
    public String generateExpression() {
        return null;
    }

    public String getGateID() {
        return this.gateID;
    }


    public void setGateID(String nameID) {
        this.gateID = nameID;
    }


    public String getDeviceType() {
        return this.TYPE;
    }


    public String getGateType() {
        return this.logicBoardName;
    }

    public void remove() {

        //remove reference of the outputs
        this.outputs = null;
        //remove reference to this gate from other gates
        for(Gate g : this.inputs){
            g.getOutputTo().remove(this);
        }
        //remove reference of other gates that reference this gate
        this.inputs = null;
        //NOTE: GC will pick up soon as no reference to this object exists and it is not being used.
    }


    public int findInput(Gate input) {

        for(Gate g : this.inputs){

            if(g.getGateID().equalsIgnoreCase(input.getGateID())){

            }

        }

        return 0;
    }


    public ArrayList<Gate> getBlockInputFrom() {
        return this.inputs;
    }


    public void setBlockInputFrom(ArrayList<Gate> input1From) {
        if(this.logicBoard.size() == input1From.size()){

            for(int i=0; i < this.logicBoard.size();i++){
                this.logicBoard.get(i).setInput1From(input1From.get(i));
                this.inputs.add(input1From.get(i));
            }
            for(int i=0; i < input1From.size();i++){
                input1From.get(i).setOutputTo(this.logicBoard.get(i));
            }

        }
        else{
            throw new IncompatibleInputSizeException(this.gateID);
        }

    }

    public ArrayList<Gate> getSysOut(){
        return this.sysOut;
    }


    public ArrayList<Gate> getInputs() {
        return this.logicBoard;
    }

    @Override
    public ArrayList<String> getExpression() {
        return this.sysExpressions;
    }


    //-----NA-----\\

    public Gate getInput2From() {
        return null;
    }


    public void setInput2From(Gate input2From) {

    }

    public void setOutput(int output) {

    }



    public Gate getInput1From() {
        return null;
    }


    public void setInput1From(Gate input1From) {

    }

    public void setOutputTo(Gate outputTo) {


    }

    public ArrayList<Gate> getOutputTo() {
        return null;
    }

    public int getOutput() {
        return 0;
    }

}
