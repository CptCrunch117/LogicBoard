package gates;

import Exceptions.NoSuchGateException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 5/31/2015.
 */
public class Not implements Gate, Serializable {


    private int output;
    private ArrayList<Gate> outputTos;
    private Gate input1From;
    private String nameID;
    private final String TYPE = "IAOD";
    private final String GATE = "NOT";

    public Not(){
        this.outputTos = new ArrayList<Gate>();
        setGateID(null);
        setGateID(null);
        evaluateGate();
    }

    public Not(Gate input1){
        this.outputTos = new ArrayList<Gate>();
        setInput1From(input1);
        setGateID(null);
        evaluateGate();
    }

    public Not(Gate input1From, String nameID){
        this.outputTos = new ArrayList<Gate>();
        setInput1From(input1From);
        setGateID(nameID);
        evaluateGate();
    }

    public void evaluateGate(){

        if (getInput1() == 1){
            setOutput(0);
        }
        else if(getInput1() == 0){
            setOutput(1);
        }
        else if(getInput1() == 2){
            setOutput(2);
        }
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

    public void remove() {
        this.outputTos = null;
        boolean oneRemove = this.input1From.getOutputTo().remove(this);
        this.input1From = null;
        this.outputTos = null;
    }
    @Override
    public Gate getOutputTo(String gateID) {
        Gate found = null;
        for(int i=0; i < getOutputTo().size(); i++){
            if(getOutputTo().get(i).getGateID().equalsIgnoreCase(gateID)){
                found = getOutputTo().get(i);
            }
        }

        return found;
    }

    @Override
    public void removeOutputTo(String gateID) {
        Gate found = null;
        for(Gate g : this.getOutputTo()){
            if(g.getGateID().equalsIgnoreCase(gateID)){
                found = g;
            }
        }
        if(found == null){
            throw new NoSuchGateException(gateID);
        }
        else{
            this.getOutputTo().remove(found);
        }
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
        input1.setOutputTo(this);
        this.input1From = input1;
    }



    //  N/A  \\
    public Gate getInput2From() {
        return null;
    }
    public void setInput2From(Gate input2) {

    }
}
