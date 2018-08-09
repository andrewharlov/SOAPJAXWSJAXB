package edu.tsystems.akharlov;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.nio.charset.Charset;
import java.util.*;

@WebService(endpointInterface = "edu.tsystems.akharlov.ShopAPI",
        serviceName = "FoqusStore", portName = "FoqusStorePort")
public class Store implements ShopAPI{

    @Resource
    private WebServiceContext webServiceContext;

    private static final List<User> users;

    static {
        users = new ArrayList<User>();
        users.add(new User("antonio", "123",
                "Antonio", "Spurs",
                "TX", "San Antonio", "spurs@gmail.com", "1-541-754-3010"));
        users.add(new User("alex", "1234",
                "Alex", "Karras",
                "MI", "Detroit", "alexkarras@gmail.com", "1-973-850-6330"));
    }

    public User authenticate(String username, String password) throws InvalidCredentialsException {
        User user;
        User foundUser = null;
        Iterator<User> usersIterator = users.iterator();

        MessageContext messageContext = webServiceContext.getMessageContext();
        Map requestHeader = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);

        //System.out.println(Arrays.toString(requestHeader.entrySet().toArray()));

        String usernameBasicAuth = "";
        String passwordBasicAuth = "";
        List authorization = (List) requestHeader.get("authorization");
        String authorizationValue = authorization.get(0).toString();
        if (authorizationValue != null && authorizationValue.startsWith("Basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = authorizationValue.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));
            // credentials = username:password
            String[] values = credentials.split(":",2);
            usernameBasicAuth = values[0];
            passwordBasicAuth = values[1];
        } else {
            throw new InvalidCredentialsException("Invalid credentials", "Invalid basic authentication");
        }

        if ("andrew".equals(usernameBasicAuth) && "8989".equals(passwordBasicAuth)){
            while (usersIterator.hasNext()) {
                user = usersIterator.next();
                if (user.getUsername().equals(username)){
                    if (user.getPassword().equals(password)){
                        foundUser = user;
                    } else {
                        throw new InvalidCredentialsException("Invalid credentials", "Invalid password");
                    }
                }
            }
        } else {
            throw new InvalidCredentialsException("Invalid credentials", "Invalid basic authentication (wrong username or password)");
        }

        if (foundUser == null){
            throw new InvalidCredentialsException("Invalid credentials", "Invalid username");
        } else {
            return foundUser;
        }
    }
}
