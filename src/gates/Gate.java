package gates;

import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 5/10/2015.
 */
public interface Gate {

    /**
     * this method first evaluates(using the evaluateGate method) the gate object and then returns its updated binary state(Integer).
     * @return the updated state of the gate object.
     */
    int getOutput();

    /**
     * This method returns a pointer to the gate object that this gate outputs to.
     * @return a pointer of the gate object this gate outputs to.
     */
    ArrayList<Gate> getOutputTo();

    /**
     * This method sets/connects the "output" of this gate to the gate passed into the parameters.
     * @param outputTo the gate to output to. (think of it as a "next" pointer in a link)
     */
    void setOutputTo(Gate outputTo);


    /**
     * This method returns this gates FIRST gate object input.
     * @return the first gate object that inputs to this gate (think of it as a "previous" pointer in a link)
     */
    Gate getInput1From();

    /**
     * This method sets the gate object that points to this object as this objects first input.
     * @param input1From the first input gate to this object.
     */
    void setInput1From(Gate input1From);

    /**
     * This method returns this gates SECOND gate object input.
     * @return the second gate object that inputs to this gate (think of it as a "previous" pointer in a link)
     */
    Gate getInput2From();

    /**
     * This method sets the gate object that points to this object as this objects second input.
     * @param input2From the second input gate to this object.
     */
    void setInput2From(Gate input2From);

    /**
     * This method evaluates the entire board up to this gate giving the correct and up-to date binary state
     * of this gate.
     */
    void evaluateGate();

    /**
     * This method gets the user defined ID of gate.
     * @return the user defined ID of gate.
     */
    String getGateID();

    /**
     * This method sets the gate objects ID based on user definition
     * @param nameID the ID of gate defined by user.
     */
    void setGateID(String nameID);

    /**
     * Gets the type of device(3 types) of gate object:
     *
     *  IOD(Input Only Device) - only has input capabilities, cannot output to other gate objects.
     *      Binary probe
     *      hex display
     *
     *  OOD(Output Only Device)- only has output capabilities, cannot input other gate objects to self.
     *      Binary Switch
     *      Hex keypad
     *
     *
     *  IAOD(Input And Output Device)- Can input gates to self, as well as connect output to other inputs of gate objects.
     *      AND
     *      OR
     *      NOT
     *      XOR
     *      .
     *      .
     *      .
     * @return the type of device of gate object
     */
    String getDeviceType();

    /**
     * Gets the type of gate of gate object,Ex: AND, OR, NOT, etc.
     * @return String representation of gate type of gate object.
     */
    String getGateType();

    /**
     * Set the output binary state of the gate object.
     * @param output binary state to set gate to (0 = LOW, 1 = HIGH, 2 = INVALID)
     */
    void setOutput(int output);

    /**
     * Sets gates pointers to null, thus removing ability to access object later in code, therfore
     * garbage collector will take care of it.
     */
    void remove();

    int findInput(Gate input);


    /** FOR LogicBoard object use only!!!!
     * This method returns this gates FIRST gate object input.
     * @return the list of gate objects that inputs to this gate/Logic Block (think of it as a "previous" pointer in a link)
     */
    ArrayList<Gate> getBlockInputFrom();

    /** FOR LogicBoard object use only!!!!
     * This method sets the gate objecst that points to this gate/Logic Block as this gate's/Logic Block's inputs.
     * @param input1From the list of input gates to this gate/Logic Block.
     */
    void setBlockInputFrom(ArrayList<Gate> input1From);

    ArrayList<Gate> getSysOut();

    ArrayList<Gate> getInputs();

    ArrayList<Gate> getLogicBoard();

}
