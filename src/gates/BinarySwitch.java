package gates;

import Exceptions.NoSuchGateException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 5/31/2015.
 */
public class BinarySwitch implements Gate, Serializable
{

    private int output;
    private ArrayList<Gate> outputTos;
    private String nameID;
    private final String TYPE = "OOD";
    private final String GATE = "BinarySwitch";
    private Gate input1;

    public BinarySwitch(){
        this.outputTos = new ArrayList<Gate>();
        setGateID(nameID);
        setOutput(0);



    }

    public BinarySwitch(String nameID){
        this.outputTos = new ArrayList<Gate>();
        setGateID(nameID);
        setOutput(1);
    }


    public void setOutput(int output){
        this.output = output;
        evaluateGate();

    }
    public void setOutputTo(Gate outputTo){
        this.outputTos.add(outputTo);
    }
    @Override
    public void evaluateGate() {

        if(this.input1 != null) {
            this.input1.evaluateGate();
            this.output = this.input1.getOutput();
        }
          /*  Gate pointer = getOutputTo();
            int i = 0;
            while (pointer != null) {
                pointer.evaluateGate();
                if (pointer.getOutputTo() != null) {
                    pointer = pointer.getOutputTo();
                } else {
                    break;
                }
            }*/
        if(this.outputTos != null) {
            updateOutput();
        }
    }


    private void updateOutput() {
        for(Gate g : this.outputTos){
           if(g != null) {
               cascade(g);
           }
        }
    }

    private void cascade(Gate g){
        ArrayList<Gate> temp = new ArrayList<Gate>();

        //Base case-1
        if(g == null){
            return;
        }
        //Base case-2
        if(g.getOutputTo() == null){
            return;
        }
        else{
            for(Gate gate : g.getOutputTo()){
                temp.add(gate);
            }
            for(Gate gate : temp){
                cascade(gate);
            }
        }
        return;
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
    public String getGateType() {
        return this.GATE;
    }


    @Override
    public int getOutput() {
        return this.output;
    }
    @Override
    public ArrayList<Gate> getOutputTo() {
        return this.outputTos;
    }

    public void remove(){
        this.outputTos = null;
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
    public Gate getInput1From() {
        return this.input1;
    }
    @Override
    public void setInput1From(Gate input){
        input.setOutputTo(this);
        this.input1 = input;

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

    @Override
    public Gate getInput2From() {
        return null;
    }
    @Override
    public void setInput2From(Gate input2From) {

    }

}
