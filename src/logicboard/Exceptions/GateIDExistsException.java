package logicboard.Exceptions;

/**
 * Created by Kyle Ferguson on 6/10/15.
 */
public class GateIDExistsException extends RuntimeException {

    public GateIDExistsException(String gateID){
        System.out.println("The gateID: "+gateID+" already exists in the circuit-board.");
    }
}
