package logicboard.gates;

import logicboard.gates.Gate;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 5/31/2015.
 */
public class Or implements Gate, Serializable {


    private int output;
    private ArrayList<Gate> outputTos;
    private Gate input1From;
    private Gate input2From;
    private String nameID;
    private final String TYPE = "IAOD";
    private final String GATE = "OR";
    private ArrayList<String> expression;
    /**
     * Args Constructor, Takes in the to initial inputs and determines output
     * @param input1From
     * @param input2From
     */
    public Or(Gate input1From, Gate input2From){
        this.outputTos = new ArrayList<Gate>();
        setGateID(null);
        setInput1From(input1From);
        setInput2From(input2From);
        this.expression = new ArrayList<>();
        String term1;
        String term2;
        if(input1From.getGateType().equalsIgnoreCase("BinarySwitch")){term1 = input1From.getExpression().get(0);}
        else{term1 ="( "+input1From.getExpression().get(0)+" )";}
        if(input2From.getGateType().equalsIgnoreCase("BinarySwitch")){term2 = input2From.getExpression().get(0);}
        else{term2 ="( "+input2From.getExpression().get(0)+" )";}
        this.expression.add(term1+" + "+term2);
        evaluateGate();
    }

    public Or(Gate input1From, Gate input2From, String nameID){
        this.outputTos = new ArrayList<Gate>();
        setGateID(nameID);
        setInput1From(input1From);
        setInput2From(input2From);
        this.expression = new ArrayList<>();
        String term1;
        String term2;
        if(input1From.getGateType().equalsIgnoreCase("BinarySwitch")){term1 = input1From.getExpression().get(0);}
        else{term1 ="( "+input1From.getExpression().get(0)+" )";}
        if(input2From.getGateType().equalsIgnoreCase("BinarySwitch")){term2 = input2From.getExpression().get(0);}
        else{term2 ="( "+input2From.getExpression().get(0)+" )";}
        this.expression.add(term1+" + "+term2);         evaluateGate();
    }
    /**
     * No-Args constructor, when an AND gate is created with  no inputs.
     */
    public Or(){
        this.outputTos = new ArrayList<Gate>();
        this.expression = new ArrayList<>();
        setGateID(null);
        setInput1From(null);
        setInput2From(null);
        evaluateGate();
    }

    public Or(String nameID){
        this.outputTos = new ArrayList<Gate>();
        this.expression = new ArrayList<>();
        setGateID(nameID);
        setInput1From(null);
        setInput2From(null);
        evaluateGate();
    }
    public void evaluateGate(){

        if(getInput1() == 2 || getInput2() == 2 ){
            setOutput(2);
        }
        else if(getInput1() == 1 || getInput2()== 1){
            setOutput(1);
        }
        else{
            setOutput(0);
        }
    }

    public ArrayList<String> getExpression(){
        return this.expression;
    }
    @Override
    public String getGateID() {
        return this.nameID;
    }

    @Override
    public void setGateID(String nameID) {
        this.nameID = nameID;
    }

    @Override
    public String getDeviceType() {
        return this.TYPE;
    }
    @Override
    public String getGateType(){
        return this.GATE;
    }


    @Override
    public synchronized int getOutput() {
        evaluateGate();
        return output;
    }
    public synchronized void setOutput(int output) {
        this.output = output;
    }


    private synchronized int getInput1() {
        int input1 = 0;
        if(getInput1From() != null){
            input1 = getInput1From().getOutput();
        }
        else{
            input1 = 2;
        }

        return input1;
    }
    private synchronized int getInput2() {
        int input2 = 0;
        if(getInput2From() != null){
            input2 = getInput2From().getOutput();
        }
        else{
            input2 = 2;
        }

        return input2;
    }

    @Override
    public ArrayList<Gate> getOutputTo() {
        return this.outputTos;
    }
    public void setOutputTo(Gate outputTo) {
        this.outputTos.add(outputTo);
    }

    public Gate getInput1From() {
        return input1From;
    }
    public void setInput1From(Gate input1) {
        if(input1 != null) {
            input1.setOutputTo(this);
            this.input1From = input1;
        }
        else{

        }
    }

    public Gate getInput2From() {
        return input2From;
    }
    public void setInput2From(Gate input2) {
        input2.setOutputTo(this);
        this.input2From = input2;
    }

    @Override
    public int findInput(Gate input){
        int in = 0;
        if(getInput1From().getGateID().equalsIgnoreCase(input.getGateID())){
            in = 1;
        }
        else if(getInput2From().getGateID().equalsIgnoreCase(input.getGateID())){
            in = 2;
        }
        return in;
    }


    public void remove(){
        this.outputTos = null;
        boolean oneRemove = this.input1From.getOutputTo().remove(this);
        boolean twoRemove = this.input2From.getOutputTo().remove(this);
        this.input1From = null;
        this.input2From = null;
    }



    //-----Not Applicable-----\\

    @Override
    public ArrayList<Gate> getBlockInputFrom() {
        return null;
    }

    @Override
    public void setBlockInputFrom(ArrayList<Gate> input1From) {

    }

    @Override
    public ArrayList<Gate> getSysOut() {
        return null;
    }

    @Override
    public ArrayList<Gate> getInputs() {
        return null;
    }



}
