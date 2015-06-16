import gates.Gate;

import java.util.ArrayList;

/**
 * Created by Kyle Ferguson on 6/2/15.
 * This methods constructor should initialize it's arraylist with all OOD (output only devices) more OODs can be
 * added later however!
 */
public interface LogicBoardADT {




    /**
     * This method adds gates to an existing board!
     * @param gate This is the gate that will be added to the circuit board, can be of type IAOD (in and output device)
     *              or IOD (input only device Ex: binary probe)
     * @param gateID This is the name of the gate, this is allows the data structure to find this gate
     * @param input1 this is what links the gates to each-other, also the input for the gate
     * @param input2 (Optional) If only one input simply pass null and the method will handle the rest!
     */
    void addGate(String gate, String gateID, String input1, String input2, int in1_in, int in2_in);

    /**
     * This method adds inputs to the logicboard (this helps the data determine the inputs when generating a truthTable)
     * @param gate the OOD (output only device to add to the logicboard)
     */
    void addOOD(Gate gate);

    /**
     * This method finds all of the open outputs that exist in this LogicBoard
     * @return the arrayList of pointers to gates with an open output
     */
    ArrayList<Gate> findOpenOutputs();

    /**
     * Finds the target gate based on the gateID passed in
     * @param gateID
     */
    Gate findGate(String gateID);


    ArrayList<Gate> getAvailableOutputs();
    /**
     * This method grabs all of the inputs and tests all possible cases and shows outputs of all open outputs based
     * on each test case, thus generating the truthTable.
     * @return
     */
    String generateTruthTable();

    void removeGate(String gateID);


    void setSystemOutputs(ArrayList<String> gateIDs);

    ArrayList<Gate> getSystemOutputs();

    void insertLogicBlock(ArrayList<String> gateIN, LogicBoard block);
}
