package logicboard.boardbuilder.exceptions;

/**
 * Created by CptAmerica on 7/1/15.
 */
public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String datStruct){
        System.out.println("Element could not be found in: "+datStruct);
    }
}
