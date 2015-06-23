package Exceptions;

/**
 * Created by CptAmerica on 6/22/15.
 */
public class GateCreationException extends RuntimeException {


    public GateCreationException(String board){

        System.out.println("A gate creation attempt was made passing in null parameters in this logicBoard: "+board);

    }
}
