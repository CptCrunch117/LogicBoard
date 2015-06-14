package Exceptions;

/**
 * Created by CptAmerica on 6/10/15.
 */
public class GateIDExistsException extends RuntimeException {

    public GateIDExistsException(String gateID){
        System.out.println("The gateID: "+gateID+" already exists in the circuit-board.");
    }
}
