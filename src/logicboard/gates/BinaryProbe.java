package logicboard.gates;

import logicboard.Exceptions.SwapFailureException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 6/2/15.
 */
public class BinaryProbe implements Gate, Serializable{

    private final String TYPE = "IOD";
    private final String GATE = "BinaryProbe";
    private String nameID;
    private Gate input1From;
    private int output;



    public BinaryProbe(){}

    public BinaryProbe(String nameID){}

    public BinaryProbe(Gate input1From, String nameID){
        setInput1From(input1From);
        setGateID(nameID);
        setOutput(0);
        evaluateGate();
        
    }



    @Override
    public int getOutput() {
        evaluateGate();
        return this.output;
    }

    @Override
    public ArrayList<Gate> getOutputTo() {
        return null;
    }

    @Override
    public Gate getInput1From() {
        return this.input1From;
    }
    @Override
    public void setInput1From(Gate input1) {
        input1.setOutputTo(this);
        this.input1From = input1;
    }



    @Override
    public void evaluateGate() {
        this.input1From.evaluateGate();
        setOutput(getInput1From().getOutput());
    }

    @Override
    public String getGateID() {
        return this.nameID;
    }
    @Override
    public void setGateID(String nameID){
        this.nameID = nameID;
    }

    @Override
    public String getDeviceType() {
        return this.TYPE;
    }
    @Override
    public String getGateType() {
        return this.GATE;
    }
    @Override
    public void setOutput(int output) {
        this.output = output;
    }
    public void remove(){
        this.input1From = null;
    }


    @Override
    public int findInput(Gate input) {
        int in = 0;
        if(getInput1From().getGateID().equalsIgnoreCase(input.getGateID())){
            in = 1;
        }
        else if(getInput2From().getGateID().equalsIgnoreCase(input.getGateID())){
            in = 2;
        }
        return in;
    }




    // N/A  \\

    @Override
    public Gate getInput2From() {
        //Not applicable
        return null;
    }
    @Override
    public void setInput2From(Gate input2From) {
        //Not applicable
    }
    @Override
    public void setOutputTo(Gate outputTo) {
        //Not applicable
    }
    @Override
    public ArrayList<Gate> getBlockInputFrom() {
        //Not applicable
        return null;
    }

    @Override
    public void setBlockInputFrom(ArrayList<Gate> input1From) {
        //Not applicable
    }

    @Override
    public ArrayList<Gate> getSysOut() {
        return null;
    }

    @Override
    public ArrayList<Gate> getInputs() {
        return null;
    }

    @Override
    public ArrayList<String> getExpression() {
        return null;
    }
    @Override
    public void swapInput(int inputPos, Gate switchWith) {
        if(inputPos == 0){
            boolean check = getInput1From().getOutputTo().remove(this) ? true : false;
            if(!check) throw new SwapFailureException(this.getGateID());
            else setInput1From(switchWith);

        }
        else if(inputPos == 1){
            boolean check = getInput2From().getOutputTo().remove(this) ? true : false;
            if(!check) throw new SwapFailureException(this.getGateID());
            else setInput2From(switchWith);
        }
    }

    @Override
    public String generateExpression() {
        return null;
    }

}
