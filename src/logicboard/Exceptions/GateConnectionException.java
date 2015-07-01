package logicboard.Exceptions;

/**
 * Created by CptAmerica on 6/22/15.
 */
public class GateConnectionException extends RuntimeException {

    public GateConnectionException(String gate){
        System.out.println("connection attempt with a null object was made with on the input of this gate: "+gate);
    }
}
