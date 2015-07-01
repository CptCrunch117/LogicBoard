package logicboard.boardbuilder.exceptions;

/**
 * Created by CptAmerica on 7/1/15.
 */
public class NoInputExistsException extends RuntimeException {

    public NoInputExistsException(String gateType){
        System.out.println("No possible inputs exist for the gate type: "+gateType);
    }
}
