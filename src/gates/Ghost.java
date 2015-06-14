package gates;

import java.util.Random;

/**
 * Created by CptAmerica on 6/3/15.
 */
public class Ghost implements Gate{

    private final String TYPE = "IAOD";
    private final String GATE = "GHOST";
    private String gateID;
    private Gate input1From;
    private Gate outputTo;


   public Ghost(Gate input1, Gate outputTo, String gateID){
       setInput1From(input1);
       setOutputTo(outputTo);
       this.gateID = gateID;

   }



    @Override
    public int getOutput() {
        return 2;
    }

    @Override
    public Gate getOutputTo() {
        return this.outputTo;
    }

    @Override
    public void setOutputTo(Gate outputTo) {
        this.outputTo = outputTo;
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
        return null;
    }

    @Override
    public void setInput2From(Gate input2) {

    }



        // N/A \\


    @Override
    public void evaluateGate() {

    }

    @Override
    public String getGateID() {
        return this.gateID;
    }

    @Override
    public void setGateID(String nameID) {

    }

    @Override
    public void setOutput(int output) {

    }

    public void remove(){
        this.outputTo = null;
        this.input1From = null;
    }
}
