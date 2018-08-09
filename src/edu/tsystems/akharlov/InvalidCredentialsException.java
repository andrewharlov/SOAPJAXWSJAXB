package edu.tsystems.akharlov;

public class InvalidCredentialsException extends Exception{
    private String errorDetails;

    public InvalidCredentialsException(String reason, String errorDetails){
        super(reason);
        this.errorDetails = errorDetails;
    }

    public String getFaultInfo(){
        return errorDetails;
    }
}

