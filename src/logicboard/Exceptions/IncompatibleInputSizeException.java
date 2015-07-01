package logicboard.Exceptions;

/**
 * Created by CptAmerica on 6/18/15.
 */
public class IncompatibleInputSizeException extends RuntimeException {

    public IncompatibleInputSizeException(String board){
        System.out.println("the input size did not match the number of inputs in this logic board.");
    }

}
