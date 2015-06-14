package gates;

/**
 * Created by Kyle Ferguson on 5/31/2015.
 */
public class Not implements Gate {


    private int output;
    private Gate outputTo;
    private Gate input1From;
    private String nameID;
    private final String TYPE = "IAOD";
    private final String GATE = "NOT";

    public Not(){
        setGateID(null);
        setOutputTo(null);
        setGateID(null);
        evaluateGate();
    }

    public Not(Gate input1){
        setOutputTo(null);
        setInput1From(input1);
        setGateID(null);
        evaluateGate();
    }

    public Not(Gate input1From, String nameID){
        setOutputTo(null);
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

    public void remove(){
        this.outputTo = null;
        this.input1From = null;
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
    public Gate getOutputTo() {
        return outputTo;
    }
    public void setOutputTo(Gate outputTo) {
        this.outputTo = outputTo;
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
