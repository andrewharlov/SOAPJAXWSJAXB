package edu.tsystems.akharlov;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "AndrewHarlov.com")
public interface ShopAPI {
    @WebMethod
    @WebResult(name = "user")
    public abstract User authenticate(@WebParam(name = "username") String username,
                            @WebParam(name = "password") String password) throws InvalidCredentialsException;
}
