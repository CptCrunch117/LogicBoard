package gates;

import java.io.Serializable;
import java.math.BigInteger;
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
    public Gate getOutputTo(String gateID) {
        return null;
    }

    @Override
    public void removeOutputTo(String gateID) {

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
}
