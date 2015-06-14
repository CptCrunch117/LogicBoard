package Exceptions;

/**
 * Created by Kyle Ferguson on 6/2/15.
 */
public class InvalidOODException extends RuntimeException{

    public InvalidOODException(String gateID) {
        System.out.println("Error: "+gateID+" is not a OOD type gate.");
    }



}
