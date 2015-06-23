package Exceptions;

/**
 * Created by CptAmerica on 6/22/15.
 */
public class ReplaceGhostException extends RuntimeException {

    public ReplaceGhostException(String ghost){
        System.out.println("Failed to replace ghost: "+ghost);
    }

}
