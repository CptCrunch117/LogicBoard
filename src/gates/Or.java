package gates;

/**
 * Created by Kyle Ferguson on 5/31/2015.
 */
public class Or implements Gate {


    private int output;
    private Gate outputTo;
    private Gate input1From;
    private Gate input2From;
    private String nameID;
    private final String TYPE = "IAOD";
    private final String GATE = "OR";
    /**
     * Args Constructor, Takes in the to initial inputs and determines output
     * @param input1From
     * @param input2From
     */
    public Or(Gate input1From, Gate input2From){
        setGateID(null);
        setOutputTo(null);
        setInput1From(input1From);
        setInput2From(input2From);
        evaluateGate();
    }

    public Or(Gate input1From, Gate input2From, String nameID){
        setGateID(nameID);
        setOutputTo(null);
        setInput1From(input1From);
        setInput2From(input2From);
        evaluateGate();
    }
    /**
     * No-Args constructor, when an AND gate is created with  no inputs.
     */
    public Or(){
        setGateID(null);
        setOutputTo(null);
        setInput1From(null);
        setInput2From(null);
        evaluateGate();
    }

    public Or(String nameID){
        setGateID(nameID);
        setOutputTo(null);
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

    public Gate getInput2From() {
        return input2From;
    }
    public void setInput2From(Gate input2) {
        input2.setOutputTo(this);
        this.input2From = input2;
    }

    public void remove(){
        this.outputTo = null;
        this.input1From = null;
        this.input2From = null;
    }
}
