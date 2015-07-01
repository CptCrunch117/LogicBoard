package logicboard.boardbuilder.exceptions;

/**
 * Created by CptAmerica on 7/1/15.
 */
public class NoSuchLogicOperationException extends RuntimeException {

    public NoSuchLogicOperationException(String invalidOerator){
        System.out.println("The operator: "+invalidOerator+" is not valid!");
    }

}
