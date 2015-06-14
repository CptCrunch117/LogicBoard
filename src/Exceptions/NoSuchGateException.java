package Exceptions;

/**
 * Created by CptAmerica on 6/10/15.
 */
public class NoSuchGateException extends RuntimeException {

    public NoSuchGateException(String gateID){
        System.out.println("No gate with an ID of: "+gateID+" exists in the Logic-Board data structure.");
    }

}
