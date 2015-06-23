package gates;

import Exceptions.NoSuchGateException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 6/3/15.
 */
public class Ghost implements Gate, Serializable {

    private final String TYPE = "IAOD";
    private final String GATE = "GHOST";
    private String gateID;
    private Gate input1From;
    private Gate input2From;
    private ArrayList<Gate> outputTos;


   public Ghost(Gate input1, Gate input2, Gate outputTo, String gateID){
       this.outputTos = new ArrayList<Gate>();
       setInput1From(input1);
       setInput2From(input2);
       setOutputTo(outputTo);
       this.gateID = gateID;

   }


    public Ghost(Gate input1, Gate outputTo, String gateID){
        this.outputTos = new ArrayList<Gate>();
        setInput1From(input1);
        setOutputTo(outputTo);
        this.gateID = gateID;

    }

    @Override
    public int getOutput() {
        return 2;
    }

    @Override
    public ArrayList<Gate> getOutputTo() {
        return this.outputTos;
    }

    @Override
    public void setOutputTo(Gate outputTo) {
        this.outputTos.add(outputTo);
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
    public String getDeviceType() {
        return this.TYPE;
    }

    @Override
    public String getGateType() {
        return this.GATE;
    }


    @Override
    public Gate getInput2From() {
        return this.input2From;
    }

    @Override
    public void setInput2From(Gate input2) {
        input2.setOutputTo(this);
        this.input2From = input2;
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

    @Override
    public String getGateID() {
        return this.gateID;
    }

    public void remove(){
        this.outputTos = null;
        boolean oneRemove = this.input1From.getOutputTo().remove(this);
        boolean twoRemove = this.input2From.getOutputTo().remove(this);
        this.input1From = null;
        this.input2From = null;
    }
        // N/A \\


    @Override
    public void evaluateGate() {

    }


    @Override
    public void setGateID(String nameID) {

    }

    @Override
    public void setOutput(int output) {

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
