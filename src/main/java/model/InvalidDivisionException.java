package model;

public class InvalidDivisionException extends Exception{
    //construcor-ba visszadob 1 message-t.
    public InvalidDivisionException(String message) {
        super(message);
    }
}
