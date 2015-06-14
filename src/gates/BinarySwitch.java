package gates;

import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 5/31/2015.
 */
public class BinarySwitch implements Gate
{

    private int output;
    private Gate outputTo;
    private String nameID;
    private final String TYPE = "OOD";
    private final String GATE = "BinarySwitch";

    public BinarySwitch(){
        setOutputTo(null);
        setGateID(nameID);
        setOutput(0);



    }

    public BinarySwitch(String nameID){
        setOutputTo(null);
        setGateID(nameID);
        setOutput(0);
    }


    public void setOutput(int output){
        this.output = output;
        evaluateGate();

    }
    public void setOutputTo(Gate gate){
        this.outputTo = gate;
    }
    @Override
    public void evaluateGate() {
        Gate pointer = getOutputTo();
        int i =0;
        while(pointer != null){
            pointer.evaluateGate();
            if(pointer.getOutputTo() != null) {
                pointer = pointer.getOutputTo();
            }
            else{
                break;
            }
        }
        //Update all gates connected to this switch
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
    public Gate getOutputTo() {
        return this.outputTo;
    }

    public void remove(){
        this.outputTo = null;
    }







    @Override
    public Gate getInput1From() {
        return null;
    }
    @Override
    public void setInput1From(Gate input1From) {

    }
    @Override
    public Gate getInput2From() {
        return null;
    }
    @Override
    public void setInput2From(Gate input2From) {

    }

}
